package org.mpo.newstracker.automation;

import org.mpo.newstracker.config.CountryWrapper;
import org.mpo.newstracker.entity.Article;
import org.mpo.newstracker.entity.CountryObject;
import org.mpo.newstracker.entity.NewsEntity;
import org.mpo.newstracker.entity.dao.UserDao;
import org.mpo.newstracker.entity.dao.WatchdogDao;
import org.mpo.newstracker.exception.BackendException;
import org.mpo.newstracker.exception.CountryNotFoundException;
import org.mpo.newstracker.exception.NoArticlesFoundException;
import org.mpo.newstracker.exception.TranslatorException;
import org.mpo.newstracker.mock.DbMock;
import org.mpo.newstracker.repository.UserRepository;
import org.mpo.newstracker.service.ApiNewsService;
import org.mpo.newstracker.service.EmailService;
import org.mpo.newstracker.service.WebNewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.ExecutionException;

@Component
public class WatchdogAutomatedArticleProcessor {
    private static final Logger log = LoggerFactory.getLogger(WatchdogAutomatedArticleProcessor.class);
    private LocalDateTime taskTime;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    ApiNewsService apiNewsService;

    @Autowired
    WebNewsService webNewsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    private CountryWrapper countryWrapper;

   //todo dodelat volani service dle country,

    // there will be articleOwl dto endpoint
    //@Scheduled(fixedRate = 2000)

    //@Scheduled(cron = "*/10 * * * * *") //0 10 * * *
    @Scheduled(cron = "0 00 * * * *")
    public void sheduledWatchdogTask() throws InterruptedException, ExecutionException, CountryNotFoundException, IOException, BackendException, TranslatorException, NoArticlesFoundException {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        //log.info("sheduledWatchdogTask: The time is now {}", dateFormat.format(new Date()));
        taskTime =  LocalDateTime.now();//LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedTaskTime = taskTime.format(formatter);

        System.out.println("After : " + formattedTaskTime);

        log.info("sheduledWatchdogTask: The time is now {}", formattedTaskTime);
        NewsEntity newsEntity;

        //todo take data from DB
        UserDao[] userDaos = DbMock.mockGetAllUsersFromDb();

        if (userDaos==null){
            log.info("No Watchdog was set");
            return;
        }
        for (UserDao userDao:userDaos) {
            List<WatchdogDao> watchdogDaos = userDao.getWatchdogs();
            if(watchdogDaos==null){continue;}

            for (WatchdogDao watchdogDao:watchdogDaos) {

                //if no email go continue
                if (watchdogDao.getEmail()==null) {continue;}

                //if WORLD call api news service
                //todo pokud se objevi za den vice nez 20 clanku k tematu tak vratit vsetky
                if(watchdogDao.getCountry().equalsIgnoreCase("WORLD")) {
                    CountryObject countryObject = countryWrapper.getCountryObject(watchdogDao.getCountry());
                    newsEntity = apiNewsService.getApiArticles(20,watchdogDao.getKeywords(), countryObject.getHl(), 0).getBody();
                    sendEmailMessage(newsEntity,userDao,watchdogDao,formattedTaskTime);
                }
                else {
                    newsEntity = webNewsService.getWebNewsArticles(20, watchdogDao.getKeywords(), watchdogDao.getCountry(), 0,watchdogDao.isTranslate()).getBody();
                    sendEmailMessage(newsEntity,userDao,watchdogDao,formattedTaskTime);
                }
            }
        }

    }

    private Article [] getWatchdogSuitableArticles(NewsEntity newsEntity, LocalDateTime taskTime){

        //Predicate<Article> p = article -> article.getPublishedAt().isAfter(taskTime.minusDays(1));
        Article [] articles= Arrays.stream(newsEntity.getArticles()).filter(article -> article.getPublishedAt().isAfter(taskTime.minusDays(1))).toArray(Article[]::new);
        return articles;
    }

    /**
     * Method takes article objects and put each object into string delimited by newlines and each object is then inserted to sAll string and returned
     * @param articles
     * @param country
     * @return
     */
    private String prepareTextForEmail(Article [] articles,String country){

        StringJoiner sAll = new StringJoiner("\n");
        String underscore = "-----------------------------------------------------------------------------------------------------------------------------------";
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (country.toUpperCase().equals("CZ") || country.toUpperCase().equals("SK")||country.toUpperCase().equals("WORLD") ) {

            for (Article article : articles) {
                StringJoiner sOne = new StringJoiner("\n");
                sOne.add(article.getTitle()).add(article.getDescription()).add(article.getUrl()).add(article.getPublishedAt().format(formatter)).add(underscore);
                sAll.add(sOne.toString());

            }
        }
        else{
            for (Article article : articles) {
                StringJoiner sOne = new StringJoiner("\n");
                sOne.add(article.getTitleEn()).add(article.getDescriptionEn()).add(article.getUrl()).add(article.getPublishedAt().format(formatter)).add(underscore);
                sAll.add(sOne.toString());

            }
        }

        return sAll.toString();

    }

    /**
     *
     * @param newsEntity
     * @param actualUserDao
     * @param actualWatchdogDao
     * @param formattedTaskTime
     */

    private void sendEmailMessage(NewsEntity newsEntity,UserDao actualUserDao, WatchdogDao actualWatchdogDao,String formattedTaskTime){
        Article[] articles = getWatchdogSuitableArticles(newsEntity, taskTime);
        log.info("Last day articles: " + Arrays.toString(articles) + " for user: "
                + actualUserDao.getUsername() + " for keywords: " + actualWatchdogDao.getKeywords()
                + " sent to: " + actualWatchdogDao.getEmail());
        String preparedTextForEmail = prepareTextForEmail(articles, actualWatchdogDao.getCountry());
        // send email :]
        emailService.sendEmailMessage(actualWatchdogDao.getEmail(), "NewsTracker daily report: " + formattedTaskTime, preparedTextForEmail);
    }
}
