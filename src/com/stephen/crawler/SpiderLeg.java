package com.stephen.crawler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.io.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderLeg
{
	public String html2text(String html) {
	    return Jsoup.parse(html).text();
	}
	
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new LinkedList<String>();
    private Document htmlDocument;


    /**
     * This performs all the work. It makes an HTTP request, checks the response, and then gathers
     * up all the links on the page. Perform a searchForWord after the successful crawl
     * 
     * @param url
     *            - The URL to visit
     * @return whether or not the crawl was successful
     */
    public boolean crawl(String url)
    {
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code
                                                          // indicating that everything is great.
            {
                //System.out.println("\n**Visiting** Received web page at " + url);
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                //System.out.println("**Failure** Retrieved something other than HTML");
                return false;
            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            //System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage)
            {
                this.links.add(link.absUrl("href"));
            }
            return true;
        }
        catch(IOException ioe)
        {
            // We were not successful in our HTTP request
            return false;
        }
    }
    
    public void crawlRankings(String filename) throws IOException {
    	Elements names = this.htmlDocument.getElementsByClass("name");
    	String outfile = filename + ".txt";
    	BufferedWriter out = new BufferedWriter(new FileWriter(outfile));
    	for(int i=52;i<names.size();i++) {
    		out.write(names.get(i).text()+"\n");
    	}
    	this.crawl("http://www.owgr.com/en/Ranking.aspx?pageNo=2&pageSize=100&country=All");
    	Elements names2 = this.htmlDocument.getElementsByClass("name");
    	for(int i=52;i<names2.size();i++) {
    		out.write(names2.get(i).text()+"\n");
    	}
    	this.crawl("http://www.owgr.com/en/Ranking.aspx?pageNo=3&pageSize=100&country=All");
    	Elements names3 = this.htmlDocument.getElementsByClass("name");
    	for(int i=52;i<names3.size();i++) {
    		out.write(names3.get(i).text()+"\n");
    	}
    	out.close();
    }


   
    public void crawlStatsPage(String statName) throws IOException {
    	Elements names = this.htmlDocument.getElementsByClass("player-name");
    	String outfile = statName + ".txt";
    	BufferedWriter out = new BufferedWriter(new FileWriter(outfile));
    	for(Element name: names) {
    		if(name.text().equals("PLAYER NAME")) {
    		}
    		else {
    			String[] both = new String[2];
    			both = name.text().split(" ");
    			out.write(both[0]+" "+both[1]);
    			out.write("\n");
    		}
    	}
    	out.close();
    }
    	
    public void crawlFinishesPage(String fileName) throws IOException {
    	String outfile = fileName + ".txt";
    	BufferedWriter out = new BufferedWriter(new FileWriter(outfile));
    	Elements elements = this.htmlDocument.getElementsByClass("player");
    	for(Element x: elements) {
    		out.write(x.text()+"\n");
    	}
    	out.close();
    }
    
    public void crawlFieldPage(String fileName) throws IOException {
    	SpiderLeg sl = new SpiderLeg();
    	String outfile = fileName + ".txt";
    	BufferedWriter out = new BufferedWriter(new FileWriter(outfile));
    	Elements elements = this.htmlDocument.getElementsByClass("wrap");
    	for(Element x: elements) {
    		Elements nexts = x.child(0).child(6).child(4).child(0).child(0).child(1).child(1).child(1).children();
    		for(Element y: nexts) {
    			String text = y.text();
    			for(int i=0;i<text.length();i++) {
    				if(text.charAt(i)==',') {
    					String last = text.substring(0, i);
    					String first = text.substring(i+2, text.length());
    		    		String plain = sl.html2text(first+" "+last);
    					out.write(plain+"\n");
    				}
    			}
    		}
    		}
    	out.close();
    }
    
    public String[] setUpFinishes() {//returns url id's for past finishes in given tourney
		Element years = this.htmlDocument.getElementById("prev-years");
    	String[] finishes = new String[5];
    	for(int i=0;i<finishes.length;i++) {
    		finishes[i] = years.child(i+2).val();
    	}
    	return finishes;
	}
    
    public String[] setUpRecentFinishes() {//returns url id's for recent finishes that year
    	Element tourneys = this.htmlDocument.getElementById("all-tournaments");
    	String[] finishes = new String[9];
    	for(int i=0;i<finishes.length;i++) {
    		finishes[i] = tourneys.child(i+3).val();
    	}
    	return finishes;
    }



    public List<String> getLinks()
    {
        return this.links;
    }

}