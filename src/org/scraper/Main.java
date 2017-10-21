package org.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Document doc;
        String title;

        // Prints Hello World
        System.out.println("Hello, World!");

        // Creates an HTML as String and parses into document
        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body></html>";
        doc = Jsoup.parse(html);
        System.out.println("something: " + doc);
        // Prints title of unimelb subject page
        try {
            doc = Jsoup.connect("https://handbook.unimelb.edu.au/2017/subjects/").get();
            title = doc.title();

            System.out.println("title of html is: " + title);
            //System.out.println(doc);
        } catch (IOException e){
            e.printStackTrace();
        }

        // Prints link and name of each link in 2016 archived subjects page
        try {
            doc = Jsoup.connect("http://archive.handbook.unimelb.edu.au/2016-subjects/").get();
            Elements links = doc.select("a[href]");
            for (Element link : links){
                System.out.println("\nlink : " + link.attr("href"));
                System.out.println("text : " + link.text());
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
