package org.mpo.newstracker.service;

import org.mpo.newstracker.config.CountryWrapper;
import org.mpo.newstracker.entity.CountryObject;
import org.mpo.newstracker.entity.NewsEntity;
import org.mpo.newstracker.exception.BackendException;
import org.mpo.newstracker.exception.CountryNotFoundException;
import org.mpo.newstracker.exception.NoArticlesFoundException;
import org.mpo.newstracker.exception.TranslatorException;
import org.mpo.newstracker.util.GoogleNewsArticleExtractor;
import org.mpo.newstracker.util.GoogleTranslator;
import org.mpo.newstracker.util.GTextProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;


@Service
public class WebNewsService {

    @Autowired
    private GoogleNewsArticleExtractor googleNewsArticleExtractor;

    @Autowired
    private CountryWrapper countryWrapper;

    @Autowired
    private Executor asyncExecutor;

    /*@Value("${news-admin.exposed-header}")
    private String SESSION_HEADER;*/

    private static Logger log = LoggerFactory.getLogger(WebNewsService.class);
    //todo pridat logy misto System.out.println

    private NewsEntity newsEntity;
    //private static final String SESSION_HEADER = "Session-Id";

    @Autowired
    private GoogleTranslator googleTranslator;

    @Async
    private CompletableFuture<String> getTranslationAsync(CountryObject countryObject, String preparedText) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return googleTranslator.translate(countryObject.getHl(), "en", preparedText);
                    } catch (TranslatorException | BackendException e) {
                        throw new CompletionException(e);
                    }
                    //e.printStackTrace();
                }, asyncExecutor);
    }

    /**
     *
     * @param pageSize pageSize default is 20
     * @param keyWords keywords must be non empty
     * @param country country must be non empty one of these CZ,SK,AT,HU,PL
     * @param page  page default value is 0
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws BackendException
     * @throws CountryNotFoundException
     * @throws NoArticlesFoundException
     * @throws IOException
     * @throws TranslatorException
     *
     */
    public ResponseEntity<NewsEntity> getWebNewsArticles(Integer pageSize, String keyWords, String country, int page, boolean translate)
            throws ExecutionException, InterruptedException, BackendException,
            CountryNotFoundException, NoArticlesFoundException, IOException, TranslatorException {

        CountryObject countryObject = countryWrapper.getCountryObject(country);

        if (translate==false) {
            newsEntity = googleNewsArticleExtractor.getWebNewsArticles(keyWords, countryObject, page, pageSize);
            return ResponseEntity.status(200).body(newsEntity);
        }
        //zavola se sluzba ktera vraci clanky na dane tema
        NewsEntity newsEntityToTranslate = googleNewsArticleExtractor.getWebNewsArticles(keyWords, countryObject, page, pageSize);
        //text se pripravi na preklad
        GTextProcessor textProcessor = new GTextProcessor(newsEntityToTranslate);
        String[] preparedTextArray = textProcessor.prepareTextForTranslation();

        CompletableFuture<String>[] futureTranslations = new CompletableFuture[preparedTextArray.length];

        System.out.println("*********Prepared text array ->");
        for (int i = 0; i < preparedTextArray.length; i++) {
            System.out.println("article: " + i + " | " + preparedTextArray[i]);
        }

        Long startTime = System.nanoTime();

        //preklada se jeden clanek za druhym async

        for (int i = 0; i < futureTranslations.length; i++) {
            futureTranslations[i] = getTranslationAsync(countryObject, preparedTextArray[i]);
        }

        // Wait until they are all done
        try {
            //System.out.println("Exception will be thrown");
            CompletableFuture.allOf(futureTranslations).join();
        } catch (CompletionException e) {
            try {
                System.out.println("Exception thrown " + e.getMessage());
                throw e.getCause();
            } catch (BackendException | TranslatorException possible) {
                throw possible;
            } catch (Throwable impossible) {
                throw new AssertionError(impossible);
            }
        }
        Long endTime = System.nanoTime();

        System.out.println("elapsed time by translation in [s]: " + (endTime - startTime) / 1e9);

        String[] translatedTexts = new String[futureTranslations.length];
        for (int i = 0; i < translatedTexts.length; i++) {
            translatedTexts[i] = futureTranslations[i].get();
/*            if(translatedTexts[i].contains("IOException")){
                System.out.println("IOEX THROW!");
                throw new BackendException(translatedTexts[i].substring(12),new Throwable(),400);
            }
            if(translatedTexts[i].contains("TranslatorException")){
                System.out.println("IOEX THROW!");
                throw new BackendException(translatedTexts[i].substring(20),new Throwable(),400);
            }*/
        }

        System.out.println("********** Translated text array -> ");
        for (int i = 0; i < translatedTexts.length; i++) {
            System.out.println("article: " + i + " | " + translatedTexts[i]);
        }


        //NewsEntity translatedNRE = textProcessor.getTranslatedNewsEntity(translatedTexts);
        //Check if text was translated without exception

        newsEntity = textProcessor.getTranslatedNewsEntity(translatedTexts);

        return ResponseEntity.status(200).body(newsEntity);

    }


}
