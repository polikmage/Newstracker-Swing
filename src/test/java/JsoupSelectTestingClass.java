import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.mpo.newstracker.entity.NewsEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

public class JsoupSelectTestingClass {



    public static void main(String[] args) throws IOException {
        String hostUrl = "https://news.google.com";
        String searchEndpoint = "/search";




        String paramValueHL="cs";
        String paramValueGL="CZ";
        String paramValueCeid = "CZ:cs";
        String paramValueQ = "Skoda";

        Connection.Response response;
        NewsEntity newsEntity = new NewsEntity();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(hostUrl + searchEndpoint)
                .queryParam("q", paramValueQ)
                .queryParam("hl", paramValueHL)
                .queryParam("gl", paramValueGL)
                .queryParam("ceid", paramValueCeid);

        String completeRequestUrl = builder.toUriString();

        System.out.println(completeRequestUrl);



        Document doc = Jsoup.connect(completeRequestUrl).timeout(10000).get();
        Elements elements = doc.select("*");
        int elementsSize = elements.size();
        int counter =0;
        //for (Element element:elements) {
        for (int i = 0; i < elementsSize; i++) {

            if(elements.get(i).tagName().contains("article")){
                //System.out.println(elements.get(i).tagName("article")+ " element: "+i);
                System.out.println("*****element number:"+i+" | Title "+ elements.get(i).select("article > div > div > h3 > a[href]").text());
                System.out.println("*****element number:"+i+" | Short description: "+ elements.get(i).select("article > div > div > p").text());
                System.out.println("*****element number:"+i+" | link: "+ elements.get(i).select("article > div > div > h3 > a[href]").attr("href"));
                System.out.println("*****element number:"+i+" | Source "+ elements.get(i).select("article > div > div > div > span").text());
                System.out.println("*****element number:"+i+" | datetime: "+ elements.get(i).select("article > div > div > time").attr("datetime"));
                counter++;

            }
        }
        System.out.println("number of articles with dates: " + counter);

    }
}
