package org.mpo.newstracker.util;


import org.mpo.newstracker.entity.Article;
import org.mpo.newstracker.entity.NewsEntity;

import java.util.StringJoiner;
//TODO sloucit GTextProcessor a GoogleTranslator
public class GTextProcessor {
    NewsEntity newsEntity;

    public GTextProcessor(NewsEntity newsEntity) {
        this.newsEntity = newsEntity;
    }

    /**
     *
     * @return prepared text trios to use with GoogleTranslator
     */
    public  String[] prepareTextForTranslation() {
        Article[] articles = newsEntity.getArticles();
        String[] stringTrios = new String[articles.length];
        for (int i=0;i<stringTrios.length;i++) {
            StringJoiner sj = new StringJoiner(" # ");
            sj
                    .add(articles[i].getTitle())
                    .add(articles[i].getDescription())
                    .add(articles[i].getSource().getName());//put ... to lesser translation faults

            String textWithoutQuotesEtc = sj.toString()
                    .replaceAll("„", " ")
                    .replaceAll("“", " ")
                    .replaceAll("”", " ")
                    .replaceAll("\"", " ")
                    .replaceAll(">", " ")
                    .replaceAll("<", " ");

            stringTrios[i]=textWithoutQuotesEtc;

        }

        return stringTrios;
    }

    /**
     *
     * @param translatedTexts
     * @return News entity with translated source, description, title
     */
    public NewsEntity getTranslatedNewsEntity(String [] translatedTexts){
        /*ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        NewsEntity deepCopiedNRE = null;*/
        //try {
            //deepCopiedNRE = objectMapper.readValue(objectMapper.writeValueAsString(newsEntity),NewsEntity.class);
            // Article [] articles = deepCopiedNRE.getArticles();
            Article [] articles = newsEntity.getArticles();
            //String [] arr = translatedTexts[i].split("#");
            for (int i = 0; i < translatedTexts.length; i++) {
                String [] arr = translatedTexts[i].split("#");
                if (arr.length<3)
                {System.out.println("debug, arr length = " + arr.length + " where "+i);}


                if(arr.length>0) {
                    articles[i].setTitleEn(arr[0]);
                }

                if(arr.length>1) {
                    articles[i].setDescriptionEn(arr[1]);
                }

                if(arr.length>2) {
                    articles[i].getSource().setNameEn(arr[2]);
                }

            }
        /*} catch (IOException e) {
            e.printStackTrace();
        }*/
     return newsEntity;
    }



    public static void main(String[] args) {
        //String [] newstracker = prepareTextForTranslation(new String[0]);
        //Arrays.stream(newstracker).forEach(System.out::println);
    }


}
