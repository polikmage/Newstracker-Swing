package org.mpo.newstracker.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mpo.newstracker.entity.CountryObject;
import org.mpo.newstracker.exception.BackendException;
import org.mpo.newstracker.exception.CountryNotFoundException;
import org.mpo.newstracker.exception.NoArticlesFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("GoogleNewsArticleExtractorTest")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class GoogleNewsArticleExtractorTest {

@Autowired
GoogleNewsArticleExtractor googleNewsArticleExtractor;
/*
    @Value("${google-web-news.host-url}")
    private String hostUrl;
    @Value("${google-web-news.search-endpoint}")
    private String searchEndpoint;
*/

    @BeforeAll
    static void setup() {
        System.out.println("@BeforeAll - executes once before all newstracker methods in this class");


    }

    @BeforeEach
    void init() {
        System.out.println("@BeforeEach - executes before each newstracker method in this class");
    }

    @Test
    void testGetWebNewsArticles() throws BackendException, CountryNotFoundException, NoArticlesFoundException {

    //GoogleNewsArticleExtractor googleNewsArticleExtractor = new GoogleNewsArticleExtractor();
    CountryObject countryObject = new CountryObject("Czechia","cs","gl","CZ:cs");
    String keyWords = "BMW";
    int page = 0;
    int pageSize = 20;

    assertNotNull(googleNewsArticleExtractor.getWebNewsArticles(keyWords,countryObject,page,pageSize));

    }
}