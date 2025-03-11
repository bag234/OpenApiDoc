package org.bag.OpenApiDoc.Object;

import org.bag.OpenApiDoc.Liner.Line;
import org.bag.OpenApiDoc.Liner.Liner;
import org.bag.OpenApiDoc.Object.Files.IWriteYML;

public class EndPoint implements IWriteYML{

	String name;
	
	Methods method;
	
	TypeContent typeContent;
	
	int code = 200; 
	
	Parameter parmetr;
	
	public EndPoint(Methods met) {
		this.method = met;
	}
	
	public EndPoint setCode(int code) {
		this.code = code;
		return this;
	}
	
	public EndPoint setName(String name) {
		this.name = name;
		return this;
	}
	
	public EndPoint setTypeContent(TypeContent typeContent) {
		this.typeContent = typeContent;
		return this;
	}
	
	public EndPoint setParmetr(Parameter parmetr) {
		this.parmetr = parmetr;
		return this;
	}
	
	@Override
	public int hashCode() {
		return method.hashCode();
	}

	@Override
	public Liner getBlock() {
		Liner liner = new Liner();
		
		liner.addLiner(method);
		liner.addChild(new Line("summary: " + name));
		liner.addChild(new Line("responses: "));
		liner.addChild(new Line(1, "'" + code + "':"));
		liner.addChild(new Line(2, "content:"));
		liner.addChild(new Line(3, typeContent.getContent() +":"));
		liner.addChild(parmetr, 5);
		
		return liner;
	}
	
}
