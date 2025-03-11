package org.bag.OpenApiDoc.Object;

public enum TypeContent {

	JSON("application/json"),
	TEXT("text");
	
	String cont;
	
	private TypeContent(String cont) {
		this.cont = cont;
	}
	
	public String getContent() {
		return cont;
	}
}
