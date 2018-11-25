package org.mpo.newstracker.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mpo.newstracker.entity.Article;
import org.mpo.newstracker.entity.CountryObject;
import org.mpo.newstracker.entity.NewsEntity;
import org.mpo.newstracker.entity.Source;
import org.mpo.newstracker.exception.BackendException;
import org.mpo.newstracker.exception.CountryNotFoundException;
import org.mpo.newstracker.exception.NoArticlesFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
public class GoogleNewsArticleExtractor {
    private final Logger log = LoggerFactory.getLogger(GoogleNewsArticleExtractor.class);
    /**
     * bude mit funkcionalitu extrakce clanku z googleNews a vraceni objektu NewsEntity
     * metoda getArticles(String q, int maxItems) vrati NewsEntity
     */
    @Value("${google-web-news.host-url}")
    private String hostUrl;
    @Value("${google-web-news.search-endpoint}")
    private String searchEndpoint;


    //Connection.Response response;
    private NewsEntity newsEntity;

    public GoogleNewsArticleExtractor() {
        newsEntity = new NewsEntity();
    }

    /**
     *
     * @param keyWords must be not empty
     * @param countryObject must be must be one of these: CZ,SK,AT,HU,PL
     * @param page default is 0
     * @param pageSize default is 20
     * @return
     * @throws CountryNotFoundException
     * @throws BackendException
     * @throws NoArticlesFoundException
     */

    public NewsEntity getWebNewsArticles(String keyWords, CountryObject countryObject, int page, int pageSize) throws CountryNotFoundException, BackendException, NoArticlesFoundException {
        log.info("getWebNewsArticles invoked");
        if(countryObject==null){
            log.warn("countryObject is null, must be one of these: CZ,SK,AT,HU,PL,DE,GB,IT,WORLD");
            throw new CountryNotFoundException("Country sign doesnt exist, must be one of these: CZ,SK,AT,HU,PL,DE,GB,IT,WORLD",new Throwable("CountryNotFoundException"),404);
        }
        try {

            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(hostUrl + searchEndpoint)
                    .queryParam("q", keyWords)
                    .queryParam("hl", countryObject.getHl())
                    .queryParam("gl", countryObject.getGl())
                    .queryParam("ceid", countryObject.getCeid());


            String completeRequestUrl = builder.toUriString();//hostUrl+searchEndpoint+"?"+sj.toString();
            System.out.println("request url: " + completeRequestUrl);


            Document doc = Jsoup.connect(completeRequestUrl).timeout(10000).get();


            Elements elements = doc.select("*");
            List<Article> articles = new LinkedList<>();

            //Google news cssQuery tags
            String cssQueryLink = "article > div > div > h3 > a[href]";
            String cssQueryShortDesc = "article > div > div > p";
            String cssQueryTitle = "article > div > div > h3 > a[href]";
            String cssQuerySource = "article > div > div > div > span";
            String cssQueryDateTime = "article > div > div > time";

            int counter = 0;
            //String formattedTime = "";
            for (Element element : elements) {


                if (element.tagName().contains("article")) {
                    String linkSuffix = element.select(cssQueryLink).attr("href");
                    String articleName = element.select(cssQueryTitle).text();
                    String shortDescription = element.select(cssQueryShortDesc).text();
                    String source = element.select(cssQuerySource).text();

                    String[] arrDateOfPublishing = element.select(cssQueryDateTime).attr("datetime").split(" ");
                    //System.out.println("arrdate: "+ Arrays.toString(arrDateOfPublishing) + " " + i);

                    long epochTime = 0;
                    LocalDateTime datetime = LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime),ZoneId.systemDefault());

                    if (arrDateOfPublishing.length > 1) {
                        String strDateOfPublishing = arrDateOfPublishing[1].trim();
                        Instant instant = Instant.ofEpochSecond(Long.parseLong(strDateOfPublishing));
                        datetime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

                        //formattedTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(datetime);
                    }
                    if (linkSuffix.equals("")) {
                        System.out.println("WARN Google suffix link empty: " + linkSuffix + "id " + counter);
                    }
                    String fullLink = hostUrl + linkSuffix;

       /*             if(log.isDebugEnabled()) {
                        log.debug("*****element number:" + counter + " | link: " + fullLink);
                        log.debug("*****element number:" + counter + " | Short description: " + shortDescription);
                        log.debug("*****element number:" + counter + " | Title " + articleName);
                        log.debug("*****element number:" + counter + " | Source " + source);
                        log.debug("*****element number:" + counter + " | datetime: " + datetime);

                    }*/
                    Article article = new Article(new Source(null, source, null),
                            null, articleName, null, shortDescription, null, fullLink, null, datetime);


                    articles.add(article);

                    counter++;
                }
            }

            //sort results newest first
            if(articles.isEmpty()){
                throw new NoArticlesFoundException("No articles found for this keywords: "+keyWords,new Throwable("No articles found for this keywords: "+keyWords),404);
            }

            DateComparator dateComparator = new DateComparator();
            Article[] articlesSorted = articles.stream()
                    .filter(article -> article.getPublishedAt() != null)
                    .sorted(dateComparator)
                    .toArray(Article[]::new);

            // RETURNS maximum articles extracted from GoogleNews Site which is approx 100
            //TODO funguje nahovno, pokazdy kdyz kliknu na frontendu na posun o stranku tak to znova extraktuje vsechny clanky
            //tzn je nutny bud vratit vsechny vyhledany clanky a listovat ve frontendu, nebo ty clanky najit jen jednou a pak vracet po pageich
            if (articlesSorted.length < pageSize) {
                pageSize = articlesSorted.length;
            }

            //Return only maximum one page of articles
            int actualSize = page * pageSize;
            if (actualSize > articlesSorted.length - pageSize) {
                actualSize = articlesSorted.length - pageSize;
            }
            Article[] pagedArticles = new Article[pageSize];
            for (int i = 0; i < pageSize; i++) {
                pagedArticles[i] = articlesSorted[i + actualSize];

            }
            //System.out.println(articles);
            newsEntity = new NewsEntity("OK", articles.size(), pagedArticles);
            //moreElements.forEach(m ->System.out.println(m.text()));
           /* }
            else{
                newsEntity = new NewsEntity("No articles found", 0, null);
            }*/


        } catch(UnknownHostException e){
            throw new BackendException("Unknown host exception: " + e.getMessage(),e,500);
        } catch (IOException e) {
            e.printStackTrace();
            newsEntity = new NewsEntity(e.getMessage(), 0, null);
            throw new BackendException(e.getMessage(),e,500);
        }

 /*       Arrays.stream(newsEntity.getArticles())
                .forEach(article -> System.out.println(
                        article.getTitle() + "\n" + article.getMessage()
                                + "\n" + article.getUrl() + "\n" + article.getPublishedAt()
                                + "\n" + article.getSource().getUsername() + "\n"+"id = "+ article.getSource().getId() + "\n"));*/
        return newsEntity;
    }
}
