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

public class SubjectListScraper {

    private static SubjectListScraper scraper;
    private static String subjectURLext = "/subjects/";
    private static String subURLpageExt = "/subjects?page=";
    private String subURL;

    private SubjectListScraper(String subURL){
        this.subURL = subURL;
    }

    public static SubjectListScraper getScraper(String subURL){
        if (scraper == null){
            scraper = new SubjectListScraper(subURL);
        }
        return scraper;
    }

    /**
     *     Prints link and name of each link in 2018 subjects page
     */
    public void writeSubjectsToCSV() throws FileNotFoundException {

        // get number of last page
        int lastpage = getLastPage(subURL + subjectURLext);
        System.out.println("last page is: " + lastpage);

        // writes subjects of first page to csv
        PrintWriter pw = new PrintWriter(new File("subjects.csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("code");
        sb.append(',');
        sb.append("link");
        sb.append("\n");
        // print contents of each page
        for (int i = 1; i <= lastpage; i++){
            printSubs(subURL, subURLpageExt + i, sb);
        }
        pw.write(sb.toString());
        pw.close();
        System.out.println("printed to subject.csv");


    }

    private static StringBuilder printSubs(String url, String page, StringBuilder sb){
        Document doc;
        StringBuilder newSB = sb;
        try {
            doc = Jsoup.connect(url + page).get();

            Elements links = doc.select("a[href]");
            for (Element link : links){
                String input = link.text();
                if(input.matches(".*[A-Z]{4}[0-9]{5}$")){
                    String code = (input.substring(link.text().length() - 9));
                    String lk = link.attr("href");
                    newSB.append(code);
                    newSB.append(',');
                    newSB.append(lk);
                    newSB.append('\n');
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return newSB;
    }

    // gets the last page of the handbook and returns as integer
    private static int getLastPage(String url){
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
