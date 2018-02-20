package org.scraper;

import com.opencsv.CSVReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final int SUBJECT_CODE_INDEX = 0;
    private static final int SUBJECT_LINK_INDEX = 1;

    private static String HANDBOOK_LINK = "https://handbook.unimelb.edu.au";
    private static Scanner sc;

    public static enum Command {
        HELP,
        SUBTOCSV,
        SEARCH
    }

    public static void main(String[] args) throws FileNotFoundException{
        Document doc;
        String title;

        boolean quit = false;
        sc = new Scanner(System.in);
        String input;
        Command cmd;

        while (!quit){
            System.out.println("enter a command: (HELP for commands)");
            input = sc.nextLine();
            cmd = Command.valueOf(input);

            switch (cmd){
                case HELP       : help();       break;
                case SUBTOCSV   : subToCSV();   break;
                case SEARCH     : search();     break;
                default: System.out.println("not valid command, try again");
            }
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

    /**
     * prints all commands available in program
     */
    private static void help (){
        System.out.println(Arrays.asList(Command.values()));
    }

    /**
     * scrapes all current subjects from unimelb handbook and prints to a csv
     * @throws FileNotFoundException
     */
    private static void subToCSV() throws FileNotFoundException{
        SubjectListScraper subScraper = SubjectListScraper.getScraper(HANDBOOK_LINK);
        subScraper.writeSubjectsToCSV();
    }

    /**
     * prints details of searched subject if it exists
     */
    private static void search(){
        System.out.print("enter subject code: ");
        String subCode = "";
        String subLink = "";
        CSVReader reader = null;
        boolean scan = true;


        //while ( !subCode.matches("[A-Z]{4}[0-9]{5}")){

        //
        while (scan){

            System.out.println("enter a subject code in AAAA##### format");
            subCode = sc.nextLine();

            // if subject code is in correct format, check if in subjects list
            if (subCode.matches("[A-Z]{4}[0-9]{5}")){
                try {
                    // open up subjects csv
                    reader = new CSVReader(new FileReader("subjects.csv"));
                    String[] line;
                    // scan each line of subjects list, if it is target subject then return link as string
                    while ((line = reader.readNext()) != null){
                        //System.out.println("line[0] is " + line[0] + " line[1] is " + line[1]);
                        if (line[SUBJECT_CODE_INDEX].equals(subCode)){
                            subLink = line[SUBJECT_LINK_INDEX];
                            System.out.println("subLink is " + subLink);
                            scan = false;
                            break;
                        }
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }

                if (scan){
                    System.out.println("subject was not found, try another code");
                }
            }

        }
        SubjectInfoScraper infoScraper = SubjectInfoScraper.getScraper(HANDBOOK_LINK);
        //infoScraper.printSubDetails( "/subjects/acct10001");
        infoScraper.printSubDetails(subLink);
    }
}
