package org.bag.OpenApiDoc.Utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.annotation.processing.Filer;
import javax.tools.DocumentationTool.Location;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.bag.OpenApiDoc.Annatation.OpenApiInfo;
import org.bag.OpenApiDoc.Liner.Document;
import org.bag.OpenApiDoc.Liner.Line;
import org.bag.OpenApiDoc.Object.EndPath;
import org.bag.OpenApiDoc.Object.Entity;
import org.bag.OpenApiDoc.Object.Files.IWriteYML;

public final class DocUtils {

	private static void printer(IWriteYML wr, PrintWriter write) {
		for(Line str: wr.getBlock()) {
			for(int i = 0; i < str.getTab(); i++)
				write.print("  ");
			write.println(str.getValue());
		}
	}
	
	private static void printer(IWriteYML wr) {
		for(Line str: wr.getBlock()) {
			for(int i = 0; i < str.getTab(); i++)
				System.out.print("  ");
			System.out.println(str.getValue());
		}
	}
	
	
	public static void generateDoc(Document doc, Filer f) throws IOException {
		FileObject file = f.createResource(StandardLocation.SOURCE_OUTPUT, "", "doc.yml");
		PrintWriter print = new PrintWriter(file.openWriter());
		
		printer(doc, print);
		
//		print.close();
	}
	
	
	public static Document build(OpenApiInfo opInfo, Collection<EndPath> paths, Collection<Entity> ents) {
		 Document doc;
		 if(opInfo != null)
		 	doc = new Document(opInfo.title(), opInfo.version());
		 else 
			doc = new Document("PLEAS INSERT TAG", "ALPHA");
		 doc.setEntitys(ents);
		 doc.setPaths(paths);
		 
		 return doc;
	 }
	
}
