package org.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Documented;

public class SubjectInfoScraper {

    private String subURL;
    private static SubjectInfoScraper scraper;

    private SubjectInfoScraper(String subURL){
        this.subURL = subURL;
    }

    public static SubjectInfoScraper getScraper(String subURL){
        if (scraper == null){
            scraper = new SubjectInfoScraper(subURL);
        }
        return scraper;
    }

    public void printSubDetails (String subURLExt){
        Document doc;
        try {
            doc = Jsoup.connect(subURL + subURLExt).get();

            Elements links = doc.select("a[href]");
            for (Element link : links){
                //if ()
                System.out.println("\nlink : " + link.attr("href"));
                System.out.println("text : " + link.text());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
