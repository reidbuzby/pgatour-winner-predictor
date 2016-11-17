package com.stephen.crawler;

import java.util.LinkedList;

public class PickGroup {
	
	private BinaryHeap groups = new BinaryHeap();
	
	public PickGroup(BinaryHeap heap) {
		int size = heap.size();
		Player[] players = new Player[size];
		for(int i=0;i<size;i++) {
			Player n = new Player();
			n = (Player) heap.remove();
			players[i]=n;
		}
		for(int i=0;i<players.length;i++) {
			for(int j=1;j<players.length;j++) {
				for(int k=2;k<players.length;k++) {
					if(i==j||i==k||j==k||players[i]==null||players[j]==null||players[k]==null) {
						//do nothing
					}
					else {
						if(players[i].getSalary()+players[j].getSalary()<16667) {
							Group g = new Group(players[i].getName()+" "+players[j].getName()+" "+players[k].getName(), players[i].getOverall()+players[j].getOverall()+players[k].getOverall());
							groups.add(g);
							players[i]=null;
							players[j]=null;
							players[k]=null;
						}
					}
				}
			}
		}
		for(int i=0;i<10;i++) {
			System.out.println(groups.remove());
		}
	}
	
	
}
