import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mpo.newstracker.entity.Article;
import org.mpo.newstracker.entity.NewsEntity;
import org.mpo.newstracker.entity.Source;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class GoogleNewsTestApi {
    public static void main(String[] args) throws IOException {


        //https://news.google.com/search?q=Anicka%20a%20Letadylko&hl=cs&gl=CZ&ceid=CZ%3Acs
        String hostUrl = "https://news.google.com";

        String searchEndpoint = "/search";



        //http://hledej.idnes.cz/clanky.aspx?q=Isl%e1m&i=Months1&strana=1
        //String text = ArticleExtractor.INSTANCE.getText(url);
        //System.out.println(text);




/*        //URL oracle = new URL("http://www.oracle.com/");
        //BufferedReader in;
        //try(BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"cp1250"))) {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
           InputSource is = new InputSource(in);

            // parse the document into boilerpipe's internal data structure
            TextDocument doc = new BoilerpipeSAXInput(is).getTextDocument();

            // perform the extraction/classification process on "doc"
            ArticleExtractor.INSTANCE.process(doc);

            // iterate over all blocks (= segments as "ArticleExtractor" sees them)


            for (TextBlock block : doc.getTextBlocks()) {
                //System.out.println(block.getLabels().toString());
                //System.out.println(block.getText());

                // block.isContent() tells you if it's likely to be content or not
                // block.getText() gives you the block's text
            }


        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (BoilerpipeProcessingException e) {
            e.printStackTrace();
        }*/

        //Document doc;

        Connection.Response response;
        NewsEntity newsEntity = new NewsEntity();

        //String url = hostUrl+searchEndpoint+"?q="+q+"&hl=cs&gl=CZ&ceid=CZ:cs";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(hostUrl + searchEndpoint)
                .queryParam("q", "Skoda")
                .queryParam("hl", "cs")
                .queryParam("gl", "CZ")
                .queryParam("ceid", "CZ:cs");

/*
        StringJoiner sj = new StringJoiner("&");
        sj
                .add("h1=cs")
                .add("g1=CZ")
                .add("q="+q)
                .add("ceid=CZ%3Acs");
        System.out.println(sj);*/

        String completeRequestUrl = builder.toUriString();//hostUrl+searchEndpoint+"?"+sj.toString();
        System.out.println(builder.toUriString());
        System.out.println(completeRequestUrl);

        /*Long startTime = System.nanoTime();
        URL oracle = new URL(completeRequestUrl);
        URLConnection yc = oracle.openConnection();
        Long endTime = System.nanoTime();
        System.out.println("URLconnection execution time [s] = " + (endTime-startTime)/1e9);*/

        /*BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();*/


        try {
            /*Long startTime = System.nanoTime();
            response = Jsoup.connect(completeRequestUrl).followRedirects(false).timeout(10*1000).execute();
            Long endTime = System.nanoTime();
            System.out.println("Jsoup first connect execution time [s] = " + (endTime-startTime)/1e9);*/
            //if(response.statusCode()==200) {
            // need http protocol
            Long startTimeAll = System.nanoTime();


            Long startTime = System.nanoTime();
            Document doc = Jsoup.connect(completeRequestUrl).timeout(10000).get();
            Long endTime = System.nanoTime();
            System.out.println("Jsoup second connect execution time [s] = " + (endTime - startTime) / 1e9);


            // get page title
            //String title = doc.title();
            //System.out.println("Title of the web page : " + title);


            // get all links, descriptions and dates and mediums
            Elements links = doc.select("article > div > div > h3 > a[href]");
            Elements shortDescriptions = doc.select("article > div > div > p");
            Elements dates = doc.select("article > div > div > time");
            //Elements sources = doc.select("article > div > div > div > span");


            System.out.println("all elements extract:" + (endTime - startTime) / 1e9);

            //find which array has smallest number of elements
            int linksSize = links.size();
            int shortDescSize = shortDescriptions.size();
            int datesSize = dates.size();
            //int sourcesSize = sources.size();

            List<Integer> elementsSizes = Arrays.asList(linksSize, shortDescSize, datesSize, 333);

            Collections.sort(elementsSizes);

            System.out.println("pocet linku = " + links.size() + " pocet shorDesc " + shortDescriptions.size() + " pocet dates " + datesSize + " pocet sources " + 333);
            int smallestArraySize = elementsSizes.get(0);
            System.out.println(smallestArraySize);
            //filter links so that only relevant stays
            List<Element> relevantLinks = new ArrayList<>();
            relevantLinks = links.stream()
                    .filter(link -> link.attr("href").contains("./articles"))
                    .collect(Collectors.toList());

            //todo osetrit aby listy links a short
            //todo descriptions mely stejny pocet prvku, co kdyz se to rozjede?
            //todo zjistit  autora-medium
            //int i=0;


            //Article[] articles = new Article[smallestArraySize];
            Article[] articles = new Article[smallestArraySize];
            for (int i = 0; i < smallestArraySize; i++) {

                // get the value from href attribute
                String googleSuffixLink = relevantLinks.get(i).attr("href");
                //System.out.println("\nlink + "+ googleSuffixLink);
                //if (googleSuffixLink.contains("./articles")) {
                String articleName = relevantLinks.get(i).text();
                String fullLink = hostUrl + googleSuffixLink;
                String shortDescription = shortDescriptions.get(i).text();
                //String source = sources.get(i).text();
                   /* System.out.println("title : " + articleName);
                    System.out.println("full link : " + fullLink);
                    System.out.println("short description: " + shortDescription);*/
                //System.out.println("source: " + source);

                //DATE processing
                String[] arrDateOfPublishing = dates.get(i).attr("datetime").split(" ");
                String strDateOfPublishing = arrDateOfPublishing[1].trim();
                Instant instant = Instant.ofEpochSecond(Long.parseLong(strDateOfPublishing));


                LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                String formatted = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(dateTime);
                // System.out.println(formatted+ "\n");

                //System.out.println(Arrays.toString(arrDateOfPublishing));

                Article article = new Article(new Source(articleName+":"+formatted, null,null),
                        null, articleName, null, shortDescription,null, fullLink, null, dateTime);


                articles[i] = article;

                //System.out.println("link whole text"+ link.wholeText());
                //}

            }
            Comparator<Article> myComparator = (arg1, arg2)
                    -> {
                if (arg1.getPublishedAt().isBefore(arg2.getPublishedAt()))
                    return 1;
                else if (arg1.getPublishedAt().isEqual(arg2.getPublishedAt()))
                    return 0;
                else
                    return -1;
            };

            Article[] articlesByDate = Arrays.stream(articles)
                    .filter(article -> article.getPublishedAt() != null)
                    .sorted(myComparator)
                    .toArray(Article[]::new);

            newsEntity = new NewsEntity("OK", smallestArraySize, articlesByDate);
            //moreElements.forEach(m ->System.out.println(m.text()));
            Long endTimeAll = System.nanoTime();
            System.out.println("cas celeho kodu: " + (endTimeAll - startTimeAll) / 1e9);
        } catch (IOException e) {
            e.printStackTrace();
            newsEntity = new NewsEntity("NOK", -1, null);
        }

        Arrays.stream(newsEntity.getArticles())
                .forEach(article -> System.out.println(article.getTitle() + "\n" + article.getDescription() + "\n" + article.getUrl() + "\n" + article.getPublishedAt() + "\n"));
    }

}
