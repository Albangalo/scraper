package org.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        try {
            Document doc = Jsoup.connect("https://handbook.unimelb.edu.au/2017/subjects/").get();
            String sub = doc.title();
            System.out.println("title of html is: " + sub);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
