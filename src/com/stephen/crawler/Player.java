package com.stephen.crawler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Player implements Comparable<Player> {
	
	private String name;
	private int SGP;
	private int SGT2G;
	private int distance;
	private int accuracy;
	private int scrambling;
	private double avgFinishesTourney;
	private double avgRecentFinishes;
	private float SGPx;
	private float SGT2Gx;
	private float distancex;
	private float accuracyx;
	private float scramblingx;
	private int incompleteStats = 0;
	private int OWGR = 0;
	private int salary;
	private double overall;
	
	public Player() {
		
	}
	
	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getSalary() {
		return this.salary;
	}
	
	public double getOverall() {
		return overall;
	}
	
	public void setSalary() throws IOException {
		File file = new File("OWGR.txt");
		Scanner fileReader = new Scanner(file);
		int count =1;
		boolean running = true;
		while(fileReader.hasNextLine()&&running) {
			String next = fileReader.nextLine();
			if(next.equals(this.name)) {
				this.OWGR=count;
				running = false;
			}
			else {
				count++;
			}
		}
		if(OWGR==0) {
			this.salary=4500;
		}
		else {
			this.salary=13530-(OWGR*30);
		}
	}
	
	
	
	public void setVariables(long[] rankings) {
		float total = 0;
		for(long x: rankings) {
			total = total+x;
		}
		SGPx = (rankings[0]/total)+1;
		accuracyx=(rankings[1]/total)+1;
		distancex=(rankings[2]/total)+1;
		SGT2Gx=(rankings[3]/total)+1;
		scramblingx=(rankings[4]/total)+1;
	}
	
	public void setOverall() {
		overall = (1620-(this.SGP*1.15*SGPx+this.distance*.7*distancex+this.accuracy*.5*accuracyx+this.scrambling*1.17*scramblingx+this.SGT2G*1.68*SGT2Gx+this.avgFinishesTourney*1.5+this.avgRecentFinishes*1.4))/1620;
		overall=Math.round(overall*1000d)/1000d;
	}
	
	public String toString() {
		return this.name + " | Overall: "+overall+"\nSGP: "+SGP+" SGT2G: "+this.SGT2G+" distance: "+this.distance+ " accuracy: "+accuracy +" scrambling: "+this.scrambling+" avg finishes tourney: "+this.avgFinishesTourney+" avg finishes recent: "+this.avgRecentFinishes;
	}
	
	public void check() {
		if(SGP==0) {
			incompleteStats++;
		}
		if(distance==0) {
			incompleteStats++;
		}
		if(accuracy==0) {
			incompleteStats++;
		}
		if(scrambling==0) {
			incompleteStats++;
		}
		if(avgFinishesTourney==0) {
			incompleteStats++;
		}
		if(avgRecentFinishes==0) {
			incompleteStats++;
		}
		
	}
	
	public int getHasStats() {
		return incompleteStats;
	}
	
	public int compareTo(Player other) {
		if(this.SGP*1.15*SGPx+this.distance*.7*distancex+this.accuracy*.5*accuracyx+this.scrambling*1.17*scramblingx+this.SGT2G*1.68*SGT2Gx+this.avgFinishesTourney*1.5+this.avgRecentFinishes*1.4>other.SGP*1.15*SGPx+other.accuracy*.5*accuracyx+other.distance*.7*distancex+other.scrambling*1.17*scramblingx+other.SGT2G*1.68*SGT2Gx+other.avgFinishesTourney*1.5+other.avgRecentFinishes*1.4) {
			return 1;
		}
		if(this.SGP*1.15*SGPx+this.distance*.7*distancex+this.accuracy*.5*accuracyx+this.scrambling*1.17*scramblingx+this.SGT2G*1.68*SGT2Gx+this.avgFinishesTourney*1.5+this.avgRecentFinishes*1.4<other.SGP*1.15*SGPx+other.accuracy*.5*accuracyx+other.distance*.7*distancex+other.scrambling*1.17*scramblingx+other.SGT2G*1.68*SGT2Gx+other.avgFinishesTourney*1.5+other.avgRecentFinishes*1.4) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	public void setSGP() throws IOException {
		File file = new File("Strokes Gained Putting.txt");
		Scanner fileReader = new Scanner(file);
		int count =1;
		boolean running = true;
		while(fileReader.hasNextLine()&&running) {
			String next = fileReader.nextLine();
			if(next.equals(this.name)) {
				this.SGP=count;
				running = false;
			}
			else {
				count++;
			}
		}
	}
	
	public void setSGT2G() throws IOException {
		File file = new File("Strokes Gained t2g.txt");
		Scanner fileReader = new Scanner(file);
		int count =1;
		boolean running = true;
		while(fileReader.hasNextLine()&&running) {
			String next = fileReader.nextLine();
			if(next.equals(this.name)) {
				this.SGT2G=count;
				running = false;
			}
			else {
				count++;
			}
		}
	}
	
	public void setDistance() throws IOException {
		File file = new File("Driving Distance.txt");
		Scanner fileReader = new Scanner(file);
		int count =1;
		boolean running = true;
		while(fileReader.hasNextLine()&&running) {
			String next = fileReader.nextLine();
			if(next.equals(this.name)) {
				this.distance=count;
				running = false;
			}
			else {
				count++;
			}
		}
	}
	
	public void setAccuracty() throws IOException {
		File file = new File("Driving Accuracy.txt");
		Scanner fileReader = new Scanner(file);
		int count =1;
		boolean running = true;
		while(fileReader.hasNextLine()&&running) {
			String next = fileReader.nextLine();
			if(next.equals(this.name)) {
				this.accuracy=count;
				running = false;
			}
			else {
				count++;
			}
		}
	}
	
	public void setScrambling() throws IOException {
		File file = new File("Scrambling.txt");
		Scanner fileReader = new Scanner(file);
		int count =1;
		boolean running = true;
		while(fileReader.hasNextLine()&&running) {
			String next = fileReader.nextLine();
			if(next.equals(this.name)) {
				this.scrambling=count;
				running = false;
			}
			else {
				count++;
			}
		}
	}
	
	public int getFinishAt(String filename) throws IOException {
		File file = new File(filename);
		Scanner fileReader = new Scanner(file);
		int count = 1;
		while(fileReader.hasNextLine()) {
			if(fileReader.nextLine().equals(this.name)) {
				break;
			}
			count++;
		}
		return count;
	}
	
	public void setFinishes() throws FileNotFoundException {//sets past finishes at given tournament past 6 years
		int[] finishes = new int[6];
		for(int i=1;i<4;i++) {
			File file = new File("Finishes "+i+".txt");
			Scanner fileReader = new Scanner(file);
			int count = 1;
			while(fileReader.hasNextLine()) {
				if(fileReader.nextLine().equals(this.name)) {
					finishes[i-1] = count;
					}
				count++;
			}
		}
		double average = 0;
		int count = 0;
		for(int x: finishes) {
			if(x==0) {
				count++;
			}
			else {
			average = average + x;
			}
		}
		average = average/(finishes.length-count);
		this.avgFinishesTourney=Math.round(average*100d)/100d;
	}
	
	public void setRecentFinishes() throws FileNotFoundException {//sets recent finishes that year, last 10 tournaments
		int[] finishes = new int[10];
		for(int i=1; i<11;i++) {
			File file = new File("RecentFinishes " +i+ ".txt");
			Scanner fileReader = new Scanner(file);
			int count = 1;
			while(fileReader.hasNextLine()) {
				if(fileReader.nextLine().equals(this.name)) {
					finishes[i-1] = count;
				}
				count++;
			}
		}
		double average = 0;
		int count = 0;
		for(int x: finishes) {
			if(x==0) {
				count++;
			}
			else {
				average = average + x;
			}
		}
		average = average/(finishes.length-count);
		this.avgRecentFinishes=Math.round(average*100d)/100d;
	}
	
	public numWithKey[] getBestStats() {
		numWithKey[] stats = new numWithKey[5];
		stats[0]=new numWithKey(SGP, 0);
		stats[1]=new numWithKey(accuracy, 1);
		stats[2]=new numWithKey(distance, 2);
		stats[3]=new numWithKey(SGT2G, 3);
		stats[4]=new numWithKey(scrambling, 4);
		return stats;
	}
}
