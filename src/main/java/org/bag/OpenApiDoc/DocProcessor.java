package org.bag.OpenApiDoc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import org.bag.OpenApiDoc.Annatation.OpenApiDoc;


@SupportedAnnotationTypes({"org.bag.OpenApiDoc.Annatation.OpenApiDoc"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DocProcessor extends AbstractProcessor {

	Map<String, Object> revolser = new HashMap<>();
	
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for (TypeElement tele: annotations) {
			for(Element ele: roundEnv.getElementsAnnotatedWith(tele)) {
				OpenApiDoc a = ele.getAnnotation(OpenApiDoc.class);
				if(a != null) {
					processingEnv.getMessager().printMessage(Kind.NOTE, String.format("[%s]: %s\n", a.path(), ele.getEnclosingElement().getSimpleName()));
				}
			}
		}
		return true;
	}

}
