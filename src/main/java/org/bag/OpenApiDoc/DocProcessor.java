package org.bag.OpenApiDoc;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import org.bag.OpenApiDoc.Annatation.OpenApiDoc;
import org.bag.OpenApiDoc.Annatation.OpenApiInfo;
import org.bag.OpenApiDoc.Annatation.OpenApiObj;
import org.bag.OpenApiDoc.Liner.Document;
import org.bag.OpenApiDoc.Liner.Line;
import org.bag.OpenApiDoc.Liner.Liner;
import org.bag.OpenApiDoc.Object.EndPath;
import org.bag.OpenApiDoc.Object.Entity;
import org.bag.OpenApiDoc.Utils.DocUtils;
import org.bag.OpenApiDoc.Utils.ParsingUtils;


@SupportedAnnotationTypes({
	"org.bag.OpenApiDoc.Annatation.OpenApiObj",
	"org.bag.OpenApiDoc.Annatation.OpenApiDoc",
	"org.bag.OpenApiDoc.Annatation.OpenApiInfo"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DocProcessor extends AbstractProcessor {

	Collection<Entity> ents = new LinkedHashSet<Entity>();
	
	Collection<EndPath> paths = new LinkedHashSet<EndPath>();
	
	OpenApiInfo opInfo = null;
	
	private void logMess(Kind k,String format, Object... obj) {
		processingEnv.getMessager().printMessage(k, String.format(format, obj));
	}
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		out: 
		for (TypeElement tele: annotations) {
			
			if (ParsingUtils.isThatAnn(tele, OpenApiObj.class)) {
				for(Element ele: roundEnv.getElementsAnnotatedWith(tele)) {
					ents.add(ParsingUtils.parseEntity(ele));
				}
				continue out;
			}
			
			if(ParsingUtils.isThatAnn(tele, OpenApiDoc.class)) {
				OpenApiDoc a_doc;
				for(Element ele: roundEnv.getElementsAnnotatedWith(tele)) {
					a_doc = ele.getAnnotation(OpenApiDoc.class);
					
					Collection<EndPath> path = ParsingUtils.parsePath(a_doc.path(), ele);
					if (path != null)
						paths.addAll(path);
				}
			}
			if(ParsingUtils.isThatAnn(tele, OpenApiInfo.class)) {
				Collection<Element> inAnn = (Collection<Element>) roundEnv.getElementsAnnotatedWith(tele);
				if(inAnn.size() == 0) {
					logMess(Kind.WARNING, "Annotation @%s not presented, pleass add his in one exemplar!", OpenApiInfo.class);
					continue out;
				}
				opInfo = inAnn.iterator().next().getAnnotation(OpenApiInfo.class);
				if(inAnn.size() > 1)
					logMess(Kind.WARNING, "Annotation @%s presented most one, pleass add his in one exemplar!", OpenApiInfo.class);
				
			}
		}
		
		if(opInfo == null)
			logMess(Kind.WARNING, "Annotation @%s not psresented, pleass add his in one exemplar!", OpenApiInfo.class);
		
		try {
			DocUtils.generateDoc(DocUtils.build(opInfo, paths, ents), processingEnv.getFiler());
		} catch (IOException e) {
			logMess(Kind.WARNING, "err: %s", e);
		}
		return true;
	}
	
	

}
