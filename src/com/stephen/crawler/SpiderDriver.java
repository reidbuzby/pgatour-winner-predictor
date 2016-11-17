package com.stephen.crawler;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;


public class SpiderDriver {
	
	public static BinaryHeap maxBinary = new BinaryHeap();
	public static BinaryHeap others = new BinaryHeap();
	
	public static long time;

	public static void setPlayers() throws IOException {
		File file = new File("Field.txt");
		Scanner fileReader = new Scanner(file);
		Tournament t = new Tournament();
		long[] rankings = t.setStats();
		System.out.println("***Course Analyzed***"+ " ("+ (System.currentTimeMillis()-time)/1000 + " secs)" );
		time=System.currentTimeMillis();
		int count = 0;
		while(fileReader.hasNextLine()) {
			Player p = new Player(fileReader.nextLine());
			p.setVariables(rankings);
			p.setSGP();
			p.setAccuracty();
			p.setDistance();
			p.setScrambling();
			p.setSGT2G();
			p.setFinishes();
			p.setRecentFinishes();
			p.setSalary();
			p.check();
			p.setOverall();
			count++;
			if(p.getHasStats()==0) {
				maxBinary.add(p);
			}
			else if(p.getHasStats()<3){
				others.add(p);
			}
		}
		time=System.currentTimeMillis();
	}
	

	
	public static void main(String[] args) throws IOException {
		time=System.currentTimeMillis();
		SpiderLeg spider = new SpiderLeg();
		
		spider.crawl("http://www.owgr.com/ranking");
		spider.crawlRankings("OWGR");
		
		spider.crawl("http://www.pgatour.com/stats/stat.02564.html");
		spider.crawlStatsPage("Strokes Gained Putting");
		
		spider.crawl("http://www.pgatour.com/stats/stat.101.html");
		spider.crawlStatsPage("Driving Distance");
		
		spider.crawl("http://www.pgatour.com/stats/stat.102.html");
		spider.crawlStatsPage("Driving Accuracy");
		
		spider.crawl("http://www.pgatour.com/stats/stat.130.html");
		spider.crawlStatsPage("Scrambling");
		
		spider.crawl("http://www.pgatour.com/stats/stat.02674.html");
		spider.crawlStatsPage("Strokes Gained t2g");
		
		spider.crawl("http://espn.go.com/golf/leaderboard?tournamentId=2240");//previous year leaderboard
		spider.crawlFinishesPage("Finishes 1");
		int count = 2;
		for(String x: spider.setUpFinishes()) {
			spider.crawl("http://espn.go.com/golf/leaderboard?tournamentId="+x);
			spider.crawlFinishesPage("Finishes "+count);
			count++;
		}
		
		spider.crawl("http://espn.go.com/golf/leaderboard?tournamentId=2492");//most recent leaderboard
		spider.crawlFinishesPage("RecentFinishes 1");
		int count2 = 2;
		for(String x: spider.setUpRecentFinishes()) {
			spider.crawl("http://espn.go.com/golf/leaderboard?tournamentId="+x);
			spider.crawlFinishesPage("RecentFinishes "+count2);
			count2++;
		}
		
		spider.crawl("http://www.pgatour.com/competition/2016/the-players-championship/field.html");//input PGA Tour field url
		spider.crawlFieldPage("Field");
		
		System.out.println("***Web Pages Crawled***" + " ("+ (System.currentTimeMillis()-time)/1000 + " secs)");
		time=System.currentTimeMillis();
		
		SpiderDriver sd = new SpiderDriver();
		sd.setPlayers();
		
		//PickGroup check = new PickGroup(maxBinary);
		
		
		Player[] maxBinaryCopy = new Player[maxBinary.size()];

		//for(int i=0;i<maxBinary.size();i++) {
			//maxBinaryCopy[i] = (Player) maxBinary.remove();
		//}
		
		for(int i=0;i<40;i++) {
			System.out.println(maxBinary.remove());
		}
		//for(int i=0;i<30;i++) {
		//	System.out.println(maxBinaryCopy[i]);
		//}
		System.out.println("OTHERS: ");
		for(int i=0;i<others.size();i++) {
			System.out.println(others.remove());
		}
		
		while(3<5) {
			System.out.println("Search:");
			Scanner scan = new Scanner(System.in);
			String name = scan.next();
			for(int i=0;i<maxBinaryCopy.length;i++) {
				if(maxBinaryCopy[i]==null) {
					//do nothing
				}
				else if(maxBinaryCopy[i].getName().contains(name)) {
					System.out.println(maxBinaryCopy[i]+"\n");
				}
			}
			
			
		}
		
	}
}
