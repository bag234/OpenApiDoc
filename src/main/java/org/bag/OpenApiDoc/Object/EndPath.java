package org.bag.OpenApiDoc.Object;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;

import org.bag.OpenApiDoc.Liner.Line;
import org.bag.OpenApiDoc.Liner.Liner;
import org.bag.OpenApiDoc.Object.Files.IWriteYML;

public class EndPath implements IWriteYML{

	String path;
	
	Collection<EndPoint> ends = new HashSet<EndPoint>();
	
	Collection<Parameter> pathPars = new LinkedHashSet<Parameter>();
	
	public EndPath(String path) {
		this.path = path;
	}
	
	public EndPath addEndPoint(EndPoint point) {
		ends.add(point);
		return this;
	}
	
	public EndPath addPathParam(Parameter p) {
		pathPars.add(p);
		return this;
	}

	@Override
	public Liner getBlock() {
		Liner liner = new Liner();
		liner.addLine(new Line(path + ":"));
		for(EndPoint e: ends) {
			liner.addChild(e);
		}
		return liner;
	}

	@Override
	public String toString() {
		return "EndPath [path=" + path + ", ends=" + ends + ", pathPars=" + pathPars + "]";
	}
	
	
	
}
