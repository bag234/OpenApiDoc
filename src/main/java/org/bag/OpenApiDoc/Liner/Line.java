package org.bag.OpenApiDoc.Liner;

public class Line {

	int tab_index; 
	
	String value;
	
	public Line(String value) {
		this.value = value;
		tab_index = 0;
	}
	
	public Line(int tab, String value) {
		this.value = value;
		tab_index = tab;
	}
	
	public Line setTab(int tab) {
		tab_index = tab;
		return this;
	}
	
	public Line addTab(int tab) {
		tab_index += tab;
		return this;
	}
	
	public int getTab() {
		return tab_index;
	}
	
	public String getValue() {
		return value;
	}
}
