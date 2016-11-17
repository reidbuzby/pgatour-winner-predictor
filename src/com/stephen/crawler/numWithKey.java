package com.stephen.crawler;

public class numWithKey implements Comparable<numWithKey> {
	
	private int num;
	private int key;
	
	public numWithKey(int num, int key) {
		this.num=num;
		this.key=key;
	}
	
	public int key() {
		return key;
	}
	
	public int num() {
		return num;
	}
	
	public void setNum(int n) {
		num=n;
	}
	
	public int compareTo(numWithKey o) {
		if(this.num>o.num) {
			return 1;
		}
		else if(this.num<o.num) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
}
