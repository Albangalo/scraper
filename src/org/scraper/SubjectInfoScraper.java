package org.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        /*Document doc;
        try {
            doc = Jsoup.connect(subURL + subURLExt).get();

            Elements links = doc.select("a[href]");
            for (Element link : links){
                System.out.println("\nlink : " + link.attr("href"));
                System.out.println("text : " + link.text());
            }
        } catch (IOException e){
            e.printStackTrace();
        }*/

        System.out.println("timetable link for the subject is " + getTimetableURL(subURL + subURLExt));
    }

    /**
     * gets link to the timetable of input subject
     * @param url
     * @return
     */
    private static String getTimetableURL(String url){
        Document doc;
        String ttLink = "";

        try {
            doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");
            for (Element link : links){
                String input = link.text();
                if (input.matches(".*Timetable.*")){
                    ttLink = link.attr("href");
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return ttLink;
    }

    
}
