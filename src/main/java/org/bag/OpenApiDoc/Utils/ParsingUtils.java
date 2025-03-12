package org.bag.OpenApiDoc.Utils;

import java.awt.Window.Type;
import java.text.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import org.bag.OpenApiDoc.Liner.Line;
import org.bag.OpenApiDoc.Liner.Liner;
import org.bag.OpenApiDoc.Object.EndPath;
import org.bag.OpenApiDoc.Object.EndPoint;
import org.bag.OpenApiDoc.Object.Entity;
import org.bag.OpenApiDoc.Object.Methods;
import org.bag.OpenApiDoc.Object.Parameter;
import org.bag.OpenApiDoc.Object.TypeContent;
import org.bag.OpenApiDoc.Object.Types;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public final class ParsingUtils {

	final static String[] COL_NAME = {"java.util.Collection", "java.util.Set"};
	
	static private Types convertTypes(TypeMirror mir) {
		if(mir.getKind().isPrimitive()) {
			switch (mir.getKind()) {
			case CHAR:
				return Types.STRING;
			case FLOAT:
				return Types.NUMBER;
			case BOOLEAN:
				return Types.BOOLEAN;
			default:
				return Types.INTEGER;
			}
		}
		if (mir.getKind() == TypeKind.ARRAY ||
				Arrays.stream(COL_NAME).filter(str -> mir.toString().contains(str)).count() > 0)
			return Types.ARRAY;
		
		if(mir.toString().contains("String") || mir.toString().contains("Char"))
			return Types.STRING;
		
		return convertTypes(mir.toString());
	}
	
	static private Types convertTypes(String name) { 
		switch (name.toLowerCase()) {
			case "boolean": 
				return Types.BOOLEAN;
			case "string":
				return Types.STRING;
			case "long":
			case "int":
			case "integer":
			case "byte":
				return Types.INTEGER;
			case "float":
			case "double":
				return Types.NUMBER;
			
			default:
				break;
		}
		
		if(name.length() > 3)
			return Types.OBJECT;
		
		return Types.NULL;
	}
	
	static private String arrTypeString(String str) {
		if(str.contains("[]"))
			return str.replace("[]", "");
		if(str.contains("<")) {
			return str.substring(str.indexOf('<')+1, str.indexOf('>'));
		}
		
		return str;
	}
	
	static private String objName(String full) {
		if (full.lastIndexOf(".") > 0)
			return full.substring(full.lastIndexOf(".")+1);
		return full;
	}
	
	static private Parameter convert(Element e) {
		
		Types type = convertTypes(e.asType());		
		if(type == Types.OBJECT) {
			return new Parameter(e.getSimpleName().toString(), 
					new Entity(objName(e.asType().toString())));
		}
		if(type == Types.ARRAY) {
			Types arrT = convertTypes(objName(arrTypeString(e.asType().toString())));
			if(arrT == Types.OBJECT) {
				return new Parameter(e.getSimpleName().toString(), 
						new Entity(arrTypeString(
								objName(e.asType().toString())))).setArray();
			}
			
			return new Parameter(e.getSimpleName().toString(), arrT).setArray();
		}
		
		return  new Parameter(e.getSimpleName().toString(), type);
	}
	
	public static void printer(Liner line) {
		for(Line str: line) {
			for(int i = 0; i < str.getTab(); i++)
				System.out.print("  ");
			System.out.println(str.getValue());
		}
	}
	
	static private Collection<Element> getSetters(Collection<Element> fun) {
		return fun.stream().filter(ele -> 
				ele.getKind() == ElementKind.METHOD && ele.getSimpleName().toString().contains("set")).toList();
	}
	
	static private Parameter configParam(Element el, Collection<Element> set) {
		Parameter p = convert(el);
		if(el.getModifiers().contains(Modifier.TRANSIENT))
			p.setMinor();
		else {
			if (set != null) {
				
				if (set.stream().filter(s->
					s.getSimpleName().toString().toLowerCase().contains(p.getName().toLowerCase())).count() == 0){
					p.setMinor();
				}
					
			}
		}
		return p;
	}
	
	private static TypeContent parseTypeContent(String[] prodes) {
		if (prodes.length > 0 )
		switch (prodes[0]) {
		
		case "application/json":
		case "json": 
			return TypeContent.JSON;
	
		default:
			return TypeContent.TEXT;
		}
		return TypeContent.TEXT;
	}
	
	
	public static boolean isThatAnn(TypeElement ele, Class<?> an) {
		if (ele.getSimpleName().toString().equalsIgnoreCase(an.getSimpleName()))
			return true;
		return false;
	}
	
	private static Parameter parseRetParm(TypeMirror mir) {
		Types t = convertTypes(mir);
		if(t == Types.OBJECT) {
			return new Parameter("schema", new Entity(objName(mir.toString())));
		}
		if(t == Types.ARRAY) {
			Types arrT = convertTypes(objName(arrTypeString(mir.toString())));
			if(arrT == Types.OBJECT) {
				return new Parameter("schema", 
						new Entity(arrTypeString(
								objName(mir.toString())))).setArray();
			}
			
			return new Parameter("schema", arrT).setArray();
		}
		return new Parameter("schema", t);
	}
	
	static public Collection<EndPath> parsePath(String root, Element element) {
		Collection<EndPath> ends = new LinkedHashSet<>();
		
		EndPoint ep = null;
//		EndPath ph;
		GetMapping gm = null;
		PostMapping pm = null;
//		RequestMapping rm = null;
		ExecutableElement exEl; 
		String[] paths = null;
		
		for (Element el: element.getEnclosedElements()) {

			
			if(el.getKind() == ElementKind.METHOD) {
				exEl = (ExecutableElement) el;
				// Можно переписать на цикличную загрузку с вышего элемента
				gm = el.getAnnotation(GetMapping.class);
				pm = el.getAnnotation(PostMapping.class);
//				rm = el.getAnnotation(RequestMapping.class); //Так проще
				
				if(gm != null) {
					ep = new EndPoint(Methods.GET).setTypeContent(parseTypeContent(gm.produces()));
					System.out.println(ep);
					paths = gm.path();
					if (paths.length == 0)
						paths = gm.value();
	
				}
					
				if(pm != null) {
					ep = new EndPoint(Methods.POST).setTypeContent(parseTypeContent(pm.produces()));
					paths = pm.path();
					if (paths.length == 0)
						paths = pm.value();
				}	
				if(ep != null) {
					System.out.println(ep);
					ep = ep.setName(el.toString()).setParmetr(parseRetParm(exEl.getReturnType()));
					if(paths.length > 0)
						for (String str: paths) {
							ends.add(new EndPath(root + str).addEndPoint(ep));
						}
					else 
						ends.add(new EndPath(root).addEndPoint(ep));
				}	
				
			}
		}
		
		System.out.println(ends);
		
		return ends;
	}
	
	@Override
	public String toString() {
		return "ParsingUtils []";
	}

	static public Entity parseEntity(Element el) {
		if (el.getKind() == ElementKind.CLASS) {
			Entity entity = new Entity(el.getSimpleName().toString());
			@SuppressWarnings("unchecked")
			Collection<Element> setters = getSetters((Collection<Element>) el.getEnclosedElements());
			
			for(Element iner: el.getEnclosedElements()) {
				if (iner.getKind() == ElementKind.FIELD) {
					entity.addParametr(configParam(iner, setters));
				}

			}
			
			printer(entity.getBlock());
			return entity;
		}
		return null;
	}
	
}
