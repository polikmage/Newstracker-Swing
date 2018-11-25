package org.mpo.newstracker.service;

import org.mpo.newstracker.entity.NewsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class ApiNewsService {


    private static Logger log = LoggerFactory.getLogger(ApiNewsService.class);

    @Value("${google-api-news.host-url}")
    private String hostUrl;
    @Value("${google-api-news.api-key}")
    private String apiKey;

    //private MultiValueMap<String,String> queryParamMap;

    private final RestTemplate restTemplate;
    //private static final String SESSION_HEADER = "Session-Id";
    /*@Value("${news-admin.exposed-header}")
    private String SESSION_HEADER;*/

    public ApiNewsService() {
        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        //queryParamMap = new LinkedMultiValueMap<>();
    }

    /**
     *
     * @param pageSize
     * @param keyWords
     * @param language
     * @param page default is 0
     * @return
     */
    public ResponseEntity<NewsEntity> getApiArticles(Integer pageSize, String keyWords, String language, int page)  {
        //List<Article> articles = new ArrayList<Article>();
        //System.out.println("KEYWORDS: "+ keyWords);
        String urlEncodedKeywords = "";
        try {
            urlEncodedKeywords = URLEncoder.encode(keyWords,"UTF-8" );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(hostUrl + "/v2/everything")
                //.queryParam("pageSize", maxItems.toString()) // pageSize is default 20
                .queryParam("language", language)
                .queryParam("q", urlEncodedKeywords)
                .queryParam("apiKey", apiKey)
                .queryParam("sortBy", "publishedAt")
                .queryParam("page",page+1);

        //log.info("calling api news endpoint" + );
        //System.out.println(builder.encode().toUriString()+"&q="+keyWords);
        log.info("Google API URL :" +builder.encode().toUriString());
        NewsEntity newsEntity;

        newsEntity = restTemplate.getForObject(
                builder.toUriString(), NewsEntity.class);
       /*
        DateComparator dateComparator = new DateComparator();
        Article[] articlesByDate = Arrays.asList(newsApiResponseEntity.getArticles()).stream()
                //.filter(article -> article.getPublishedAt() != null)
                //.sorted(dateComparator)
                .toArray(Article[]::new);
       */

        //newsApiResponseEntity = new NewsEntity(newsApiResponseEntity.getStatus(), newsApiResponseEntity.getTotalResults(), articlesByDate);
        //moreElements.forEach(m ->System.out.println(m.text()));
        //pocet aktualne stazenych clanku
        //prozatim reseno na frontendu
        //NewsEntity newsApiResponseEntity = restTemplate.getForObject(hostUrl+"/v2/everything",NewsEntity.class,queryParamMap);
        return ResponseEntity.status(200)
                //.header(SESSION_HEADER, "12345")
                .body(newsEntity);
    }


}
