package com.stephen.crawler;

public class Group implements Comparable<Group> {
	
	public String names;
	public double overall;
	
	public Group(String names, double overall) {
		this.names=names;
		this.overall=overall/3;
	}
	
	public String toString() {
		return names + " | Overall: " + overall;
	}
	
	public int compareTo(Group o) {
		if(this.overall<o.overall) {
			return 1;
		}
		if (this.overall>o.overall) {
			return -1;
		}
		else {
			return 0;
		}
	}
}
