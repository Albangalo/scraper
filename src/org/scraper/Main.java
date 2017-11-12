package org.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws FileNotFoundException{
        Document doc;
        String title;

        SubjectListScraper subScraper = SubjectListScraper.getScraper();
        subScraper.writeSubjectsToCSV();

        // Creates an HTML as String and parses into document
        /*String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body></html>";
        doc = Jsoup.parse(html);
        System.out.println("something: " + doc);*/

        // Prints link and name of each link in 2016 archived subjects page
        /*try {
            //doc = Jsoup.connect("http://archive.handbook.unimelb.edu.au/2016-subjects/").get();
            doc = Jsoup.connect("http://handbook.unimelb.edu.au/subjects/").get();

            Elements links = doc.select("a[href]");
            for (Element link : links){
                //if ()
                System.out.println("\nlink : " + link.attr("href"));
                System.out.println("text : " + link.text());
            }
        } catch (IOException e){
            e.printStackTrace();
        }*/

        // Prints meta keywords and description of javatpoint homepage
        /*try{
            doc = Jsoup.connect("http://www.javatpoint.com").get();
            String keywords = doc.select("meta[name=keywords]").first().attr("content");
            System.out.println("Meta keywords : " + keywords);
            String desc = doc.select("meta[name=description]").get(0).attr("content");
            System.out.println("Meta description : " + desc);
        }
        catch (IOException e){
            e.printStackTrace();
        }*/


    }


}
