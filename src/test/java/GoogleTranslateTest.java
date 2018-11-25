import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;

public class GoogleTranslateTest {
    public static void main(String[] args) throws IOException {


        String  text = "  Skodovka ";
          String urlEncd = URLEncoder.encode(text, "UTF-8");
        System.out.println(urlEncd);
/*        System.out.println("length of cz encoded text: "+ urlEncd.length());
        System.out.println("length of not encoded text: "+ text.length());
        for (int i = 0; i < 100 ; i++) {

        }*/
        String result = translate("cs", "en", text.replaceAll(":"," ").replaceAll("“"," "));
        System.out.println("length: "+ text.length()+"|  Translated text: " + result);
        String [] outputArray = result.replaceAll("&#39;", "'").split("#");
        System.out.println("vystup **************** kolik radku: " + outputArray.length);
        Arrays.stream(outputArray).forEach(System.out::println);
    }

    private static String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        String urlStr = "INSERT YOU URL HERE" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;

        System.out.println(urlStr);

        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("UserDao-Agent", "Mozilla/5.0");
        //BufferedReader in;
        try(BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))){

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        //in.close();
        return response.toString();
        }
    }

}
