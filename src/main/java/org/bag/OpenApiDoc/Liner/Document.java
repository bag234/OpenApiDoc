package org.bag.OpenApiDoc.Liner;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.bag.OpenApiDoc.Object.EndPath;
import org.bag.OpenApiDoc.Object.Entity;
import org.bag.OpenApiDoc.Object.Files.IWriteYML;

public class Document implements IWriteYML{

	static final String O_VER= "3.0.0";
	
	String title; 
	
	String ver;
	
	Collection<EndPath> paths; 
	
	Collection<Entity> entitys;
	
	public Document(String title, String ver) {
		this.title = title;
		this.ver = ver;
	
		paths = new LinkedHashSet<EndPath>();
		entitys = new LinkedHashSet<Entity>();
	}
	
	public void setEntitys(Collection<Entity> entitys) {
		this.entitys = entitys;
	}
	
	public void setPaths(Collection<EndPath> paths) {
		this.paths = paths;
	}
	
	public Document addEntity(Entity e) {
		entitys.add(e);
		return this;
	}
	
	public Document addPath(EndPath e) {
		paths.add(e);
		return this; 
	}
	
	
	@Override
	public Liner getBlock() {
		Liner l = new Liner();
		
		l.addLine(new Line("openapi: %s", O_VER));
		l.addLine(new Line("info:"));
		l.addChild(new Line("title: %s", title));
		l.addChild(new Line("version: %s", ver));
		l.addLine(new Line());
		
		l.addLine(new Line("paths:"));
		paths.stream().forEach(path -> {l.addChild(path); l.addLine(new Line());});
		l.addLine(new Line());
		
		l.addLine(new Line("components:"));
		l.addChild(new Line("schemas:"));
		entitys.stream().forEach(path -> {l.addChild(path, 2); l.addLine(new Line());});
		
		return l;
	}
	
}
