package org.bag.OpenApiDoc.Object;

import org.bag.OpenApiDoc.Liner.Line;
import org.bag.OpenApiDoc.Liner.Liner;
import org.bag.OpenApiDoc.Object.Files.IWriteYML;

public class Parameter implements IWriteYML{

	final static String REF_R = "$ref: '#/components/schemas/%s'";
	
	String name; 
	
	Types type;
	
	Entity ent;
	
	boolean isAbs = true;
	
	boolean isArr = false;
	
	public Parameter(String name, Types type) {
		this.name = name;
		this.type = type;
	}
	
	public Parameter(String name, Entity ent) {
		this(name, Types.OBJECT);
		this.ent = ent; 
	}
	
	public Parameter setMinor() {
		isAbs = false;
		return this;
	}
	
	public Parameter setArray() {
		isArr = true;
		return this;
	}
	
	public boolean isAbs() {
		return isAbs;
	}
	
	public boolean isEntity() {
		return ent != null;
	}
	
	public Entity getEntity() {
		return ent;
	}
	
	public String getName() {
		return name;
	}
	
	public Types getType() {
		return type;
	}

	@Override
	public Liner getBlock() {
		Liner liner = new Liner();
		
		liner.addLine(new Line(name + ":"));
		if(isEntity()) {
			if(isArr) {
				liner.addChild(Types.ARRAY);
				
				liner.addChild(new Line("items:"));
				liner.addChild(new Line("type: %s", type.toString().toLowerCase()).addTab(1));
				liner.addChild(new Line(REF_R, ent.getName()).addTab(1));
			}
			else
				liner.addChild(new Line(REF_R, ent.getName()));
		}
		else {
			if(isArr) {
				liner.addChild(new Line("items:"));
				liner.addChild(new Line("type: %s", type.toString().toLowerCase()).addTab(1));
			}
			else			
				liner.addChild(type);
		}

		return liner;
	}
	
	@Override
	public String toString() {
		return "[name=" + name + ", type=" + type + ", ent=" + ent + ", isAbs=" + isAbs + ", isArr=" + isArr
				+ "]\n";
	}
	
}
