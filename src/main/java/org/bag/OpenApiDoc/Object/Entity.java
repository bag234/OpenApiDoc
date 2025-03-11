package org.bag.OpenApiDoc.Object;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.bag.OpenApiDoc.Liner.Line;
import org.bag.OpenApiDoc.Liner.Liner;
import org.bag.OpenApiDoc.Object.Files.IWriteYML;

public class Entity implements IWriteYML {
	
	Collection<Parameter> pars; 
	
	String name;
	
	public Entity(String name) {
		this.name = name; 
		pars = new LinkedHashSet<Parameter>();
	}
	
	public String getName() {
		return name;
	}
	
	public Collection<Parameter> getParameters() {
		return pars;
	}
	
	public void addParametr(Parameter par) {
		pars.add(par);
	}

	@Override
	public Liner getBlock() {
		Liner liner = new Liner();
		
		liner.addLine(new Line(name + ":"));
		
		liner.addChild(new Line("properties:"));
		for(Parameter p: pars) {
			liner.addChild(p,2);
		}
		liner.addChild(new Line("required:"));
		for(Parameter p: pars) {
			if(p.isAbs()) {
				liner.addChild(new Line(1, "- " + p.getName()));
			}
		}
		
		return liner;
	}
	

}
