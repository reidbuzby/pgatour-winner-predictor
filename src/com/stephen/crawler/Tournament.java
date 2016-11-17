package com.stephen.crawler;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Tournament {
	
	public void analyzeFinishes() throws IOException {
		for(int i=0;i<7;i++) {
			File file = new File("Finishes "+i+".txt");
			Scanner fileReader = new Scanner(file);
			
		}
	}
	
	public long[] setStats() throws IOException {
		long sgpTotal = 0;
		long accuracyTotal = 0;
		long distanceTotal = 0;
		long sgt2gTotal = 0;
		long scramblingTotal = 0;
		for(int i=1;i<7;i++) {
			File file = new File("Finishes "+i+".txt");
			Scanner fileReader = new Scanner(file);
			Scanner fileReader2 = new Scanner(file);
			while(fileReader2.hasNextLine()) {
				Player p = new Player(fileReader2.nextLine());
				p.setSGP();
				p.setAccuracty();
				p.setDistance();
				p.setScrambling();
				p.setSGT2G();
				p.setFinishes();
				p.setRecentFinishes();
				p.check();
				int playerFinish = p.getFinishAt("Finishes "+i+".txt");
				for(numWithKey x: p.getBestStats()) {
					if(x.key()==0) {
						sgpTotal += x.num()*playerFinish;
					}
					if(x.key()==1) {
						accuracyTotal += x.num()*playerFinish;
					}
					if(x.key()==2) {
						distanceTotal += x.num()*playerFinish;
					}
					if(x.key()==3) {
						sgt2gTotal += x.num()*playerFinish;
					}
					if(x.key()==4) {
						scramblingTotal += x.num()*playerFinish;
					}
				}
				
			}
		}
		System.out.println("***Player Stats Analyzed***" + " ("+ (System.currentTimeMillis()-SpiderDriver.time)/1000 + " secs)");
		SpiderDriver.time=System.currentTimeMillis();
		long[] a = {sgpTotal, accuracyTotal, distanceTotal, sgt2gTotal, scramblingTotal};
		return a;
	}

	
}
