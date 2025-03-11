package org.bag.OpenApiDoc.Liner;

import org.bag.OpenApiDoc.Object.Files.IWriteYML;

public class Document implements IWriteYML{

	static final String O_VER= "3.0.0";
	
	
	String title; 
	
	String ver;
	
	public Document(String title, String ver) {
		this.title = title;
		this.ver = ver;
	}
	
	
	
	@Override
	public Liner getBlock() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
