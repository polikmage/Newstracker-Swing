package org.mpo.newstracker.util;

import org.mpo.newstracker.entity.Article;

import java.util.Comparator;

public class DateComparator implements Comparator<Article> {
    @Override
    public int compare(Article o1, Article o2) {
        //pokud arg1 je null je starsi a proto ma jit na konec
        //clanky bez data se radi na konec seznamu jako nejstarsi


        if (o1.getPublishedAt().isBefore(o2.getPublishedAt()))
            return 1;
        else if (o1.getPublishedAt().isEqual(o2.getPublishedAt()))
            return 0;
        else
            return -1;

    }

}
