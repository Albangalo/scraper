package org.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.Arrays;
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

    /**
     * prints timetable of subject if it exists
     * @param subURLExt
     */
    public void printSubDetails (String subURLExt){
        System.out.println("scraping from " + subURL + subURLExt);

        String reqURL = getRequirementsURL(subURL + subURLExt);
        printSubReqs(subURL + reqURL);

        String ttURL = getTimetableURL(subURL + subURLExt);
        if (ttURL.equals("")){
            System.out.println("timetable not available yet");
        }
        else {
            System.out.println("timetable link for the subject is " + ttURL);
            printSubTimetable(ttURL);
        }


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
            //System.out.println(doc.toString());
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

    /**
     * gets link to Eligibility and Requirements of input subject
     * @param url
     * @return
     */
    private static String getRequirementsURL(String url){
        Document doc;
        String reqLink = "";

        try {
            doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");
            for (Element link : links){
                String input = link.text();
                if (input.matches(".*requirements.*")){
                    reqLink = link.attr("href");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return reqLink;
    }

    /**
     * Prints timetable of selected subject from timetable url
     * @param timeTableURL
     */
    private static void printSubTimetable(String timeTableURL){
        Document doc;
        try {
            doc = Jsoup.connect(timeTableURL).get();
            System.out.println("connecting to " + timeTableURL);

            for (Element timetable : doc.select("table.cyon_table") ) {
                for (Element row : timetable.select("tr")) {
                    Elements data = row.select("td");

                    for (Element td : data) {
                        System.out.print(td.text() + ", ");
                    }
                    System.out.println();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void printSubReqs(String requirementsURL){
        Document doc;

        try{
            doc = Jsoup.connect(requirementsURL).get();
            System.out.println("connecting to " + requirementsURL);

            /*for (Element Prereq : doc.select("prerequisites")){
                System.out.println(Prereq.text());
            }*/
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
