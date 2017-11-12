package org.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


        // Prints link and name of each link in 2018 subjects page
        String subURL = "http://handbook.unimelb.edu.au/subjects/";
        String subsURL = "https://handbook.unimelb.edu.au/subjects?page=";

        // get number of last page
        int lastpage = getLastPage(subURL);
        System.out.println("last page is: " + lastpage);

        // print contents of each page
        for (int i = 1; i <= lastpage; i++){
            printSubs(subsURL + i);
        }



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
        try{
            doc = Jsoup.connect("http://www.javatpoint.com").get();
            String keywords = doc.select("meta[name=keywords]").first().attr("content");
            System.out.println("Meta keywords : " + keywords);
            String desc = doc.select("meta[name=description]").get(0).attr("content");
            System.out.println("Meta description : " + desc);
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

    static void printSubs(String url){
        Document doc;
        try {
            doc = Jsoup.connect(url).get();

            Elements links = doc.select("a[href]");
            for (Element link : links){
                String input = link.text();
                if(input.matches(".*[A-Z]{4}[0-9]{5}$")){
                    String s = (input.substring(link.text().length() - 9));
                    System.out.println("\nlink : " + link.attr("href"));
                    System.out.println("text : " + s);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // gets the last page of the handbook and returns as integer
    static int getLastPage(String url){
        Document doc;
        Pattern lastPagePattern = Pattern.compile("page=(.*?)$");
        int last = 1;

        try {
            doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");
            for (Element link : links){
                String input = link.text();
                    if (input.matches(".*Last.*")){
                        String l = link.attr("href");
                        Matcher m = lastPagePattern.matcher(l);
                        while (m.find()) {
                            String s1 = m.group(1);
                            // s1 should now contain the page number as a string
                            last = Integer.parseInt(s1);
                        }
                    }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return last;
    }
}
