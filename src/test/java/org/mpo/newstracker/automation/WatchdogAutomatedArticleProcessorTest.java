package org.mpo.newstracker.automation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mpo.newstracker.entity.Article;
import org.mpo.newstracker.entity.Source;
import org.mpo.newstracker.exception.BackendException;
import org.mpo.newstracker.exception.CountryNotFoundException;
import org.mpo.newstracker.exception.NoArticlesFoundException;
import org.mpo.newstracker.exception.TranslatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;
import java.util.concurrent.ExecutionException;


//@DisplayName("")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class WatchdogAutomatedArticleProcessorTest {

    @Autowired
    WatchdogAutomatedArticleProcessor watchdogAutomatedArticleProcessor;


    @Test
    public void testSheduledWatchdogTask() throws InterruptedException, ExecutionException, CountryNotFoundException, IOException, BackendException, TranslatorException, NoArticlesFoundException {
        watchdogAutomatedArticleProcessor.sheduledWatchdogTask();
    }
}
