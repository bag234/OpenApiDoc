package org.bag.OpenApiDoc.Object;

import org.bag.OpenApiDoc.Liner.Liner;
import org.bag.OpenApiDoc.Object.Files.IWriteYML;

public enum Types implements IWriteYML {

	OBJECT,
	ARRAY,
	NUMBER,
	INTEGER,
	STRING,
	BOOLEAN,
	NULL;

	private Types() {
	}
	
	@Override
	public Liner getBlock() {
		return new Liner("type: " + this.name());
	}
	
}
