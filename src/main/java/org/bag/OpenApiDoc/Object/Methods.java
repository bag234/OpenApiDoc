package org.bag.OpenApiDoc.Object;

import org.bag.OpenApiDoc.Liner.Liner;
import org.bag.OpenApiDoc.Object.Files.IWriteYML;

public enum Methods implements IWriteYML{

	GET, 
	POST;

	@Override
	public Liner getBlock() {
		return new Liner(this.name() +":");
	}
	
}
