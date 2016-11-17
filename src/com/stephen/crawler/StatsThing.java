package com.stephen.crawler;

import java.util.LinkedList;

public class StatsThing {
	LinkedList<String> stats = new LinkedList<String>();
	
	public StatsThing() {
	}
	
	public void add(String string, int rank, int finish, int players) {
		for(int i=0;i<(5-rank)+(players-finish);i++) {
			stats.add(string);
		}
	}
	
	public long[] analyze() {
		long SGP=0;
		long accuracy=0;
		long distance=0;
		long scrambling=0;
		long SGT2G=0;
		while(stats.contains("SGP")) {
			stats.remove("SGP");
			SGP++;
		}
		while(stats.contains("accuracy")) {
			stats.remove("accuracy");
			accuracy++;
		}
		while(stats.contains("distance")) {
			stats.remove("distance");
			distance++;
		}
		while(stats.contains("scrambling")) {
			stats.remove("scrambling");
			scrambling++;
		}
		while(stats.contains("SGT2G")) {
			stats.remove("SGT2G");
			SGT2G++;
		}
		long[] finl = {SGP,accuracy,distance,scrambling,SGT2G};
		return finl;
	}
}
