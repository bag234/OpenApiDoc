package org.bag.OpenApiDoc.Liner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.bag.OpenApiDoc.Object.Files.IWriteYML;

public class Liner implements Iterable<Line> {

	Collection<Line> lin = new ArrayList<Line>();
	
	int tab_idex;
	
	@Override
	public Iterator<Line> iterator() {
		return lin.iterator();
	}
	
	public Liner() {
	}
	
	protected void addLine(Liner liner, int tab) {
		for (Line l: liner) {
			lin.add(l.addTab(tab));
		}
	}

	@Deprecated
	public Liner(String line) {
		Arrays.asList(line.split("\n")).stream().forEach(str -> {lin.add(new Line(str));});
	}
	
	@Deprecated
	public Liner(String... lines) {
		Arrays.asList(lines).stream().forEach(str -> {lin.add(new Line(str));});
	}
	
	public void addLine(Line line) {
		lin.add(line);
	}
	
	public void addChild(Line line) {
		lin.add(line.addTab(+1));
	}
	
	public void addLiner(Liner liner) {
		addLine(liner, 0);
	}
	
	public void addLiner(IWriteYML yml) {
		addLine(yml.getBlock(), 0);
	}
	
	public void addChild(Liner liner) {
		addLine(liner, 1);
	}
	
	public void addChild(IWriteYML yml) {
		addLine(yml.getBlock(), 1);
	}
	
	public void addChild(Liner liner, int tab) {
		addLine(liner, tab);
	}
	
	public void addChild(IWriteYML yml, int tab) {
		addLine(yml.getBlock(), tab);
	}
	
	
}
