package org.bag.OpenApiDoc;

import org.bag.OpenApiDoc.Liner.Line;
import org.bag.OpenApiDoc.Liner.Liner;
import org.bag.OpenApiDoc.Object.EndPath;
import org.bag.OpenApiDoc.Object.EndPoint;
import org.bag.OpenApiDoc.Object.Entity;
import org.bag.OpenApiDoc.Object.Methods;
import org.bag.OpenApiDoc.Object.Parameter;
import org.bag.OpenApiDoc.Object.TypeContent;
import org.bag.OpenApiDoc.Object.Types;

public class Index {
	
	private static void printer(Liner line) {
		for(Line str: line) {
			for(int i = 0; i < str.getTab(); i++)
				System.out.print("**");
			System.out.println(str.getValue());
		}
	}
	
	public static void main(String[] args) {
		Entity d = new Entity("Rate");
		d.addParametr(new Parameter("count", Types.NUMBER));
		
		Entity en = new Entity("Curence");
		en.addParametr(new Parameter("Name", Types.STRING));
		en.addParametr(new Parameter("Abbre", Types.STRING));
		en.addParametr(new Parameter("Scale", Types.NUMBER));
		en.addParametr(new Parameter("rates", d).setArray().setMinor());
		
		printer(en.getBlock());
		
		EndPath end = new EndPath("/api/provider/");
		end.addEndPoint(new EndPoint(Methods.GET).setCode(201).setName("Basic ApI").setTypeContent(TypeContent.JSON).setParmetr(new Parameter("schema", en)));
		printer(end.getBlock());
	}

}
