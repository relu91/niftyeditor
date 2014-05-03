/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.utils;

import jada.ngeditor.model.elements.GElement;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.jdesktop.el.impl.util.ReflectionUtil;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.FilterBuilder;

/**
 * <b>Notice<\b> This class is an early implementation , could change every time. 
 * @author cris
 */
public class ClassUtils {
	private static void log(String msg) {
		//System.out.println("ClassDiscovery: " + msg);	
	}

	private static Class<?> loadClass(String className) {
		try {
			return Class.forName(className);
		} 
		catch (ClassNotFoundException e) {
			throw new RuntimeException("Unexpected ClassNotFoundException loading class '" + className + "'");
		}
	}

	private static void processDirectory(File directory, String pkgname, ArrayList<Class<?>> classes) {
		log("Reading Directory '" + directory + "'");
		// Get the list of the files contained in the package
		String[] files = directory.list();
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i];
			String className = null;
			// we are only interested in .class files
			if (fileName.endsWith(".class")) {
				// removes the .class extension
				className = pkgname + '.' + fileName.substring(0, fileName.length() - 6);
			}
			log("FileName '" + fileName + "'  =>  class '" + className + "'");
			if (className != null) {
				classes.add(loadClass(className));
			}
			File subdir = new File(directory, fileName);
			if (subdir.isDirectory()) {
				processDirectory(subdir, pkgname + '.' + fileName, classes);
			}
		}
	}

	private static void processJarfile(URL resource, String pkgname, ArrayList<Class<?>> classes) {
		String relPath = pkgname.replace('.', '/');
		String resPath = resource.getPath();
		String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
		log("Reading JAR file: '" + jarPath + "'");
		JarFile jarFile;
		try {
			jarFile = new JarFile(jarPath);         
		} catch (IOException e) {
			throw new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e);
		}
		Enumeration<JarEntry> entries = jarFile.entries();
		while(entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			String entryName = entry.getName();
			String className = null;
			if(entryName.endsWith(".class") && entryName.startsWith(relPath) && entryName.length() > (relPath.length() + "/".length())) {
				className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
			}
			log("JarEntry '" + entryName + "'  =>  class '" + className + "'");
			if (className != null) {
				classes.add(loadClass(className));
			}
		}
	}

	public static ArrayList<Class<?>> getClassesForPackage(String pkg) {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		String pkgname = pkg;
		String relPath = pkgname.replace('.', '/');
		// Get a File object for the package
		URL resource = ClassLoader.getSystemClassLoader().getResource(relPath);
		if (resource == null) {
			throw new RuntimeException("Unexpected problem: No resource for " + relPath);
		}
		log("Package: '" + pkgname + "' becomes Resource: '" + resource.toString() + "'");

		resource.getPath();
		if(resource.toString().startsWith("jar:")) {
			processJarfile(resource, pkgname, classes);
		} else {
			processDirectory(new File(resource.getPath()), pkgname, classes);
		}

		return classes;
	}

public static  Set<Class<? extends GElement>> findAllGElements(){
     Reflections reflections = new Reflections ("jada.ngeditor.model",ClasspathHelper.forClass(GElement.class), 
    new SubTypesScanner(false));
        Set<Class<? extends GElement>> subTypesOf = reflections.getSubTypesOf(GElement.class);
        return subTypesOf;
}
public static void main(String[] args) throws IOException, ClassNotFoundException{
        ArrayList<Class<?>> classesForPackage = ClassUtils.getClassesForPackage("jada.ngeditor.model");
    for(Class c : classesForPackage){
        System.out.println(c.getName());
    }
}

 public static Class[] getClasses(String pkg){
     return getClasses(pkg,new NullPredicate());
 }

    public static Class[] getClasses(String pkg, Predicate<Class> predicate) {
        ArrayList<Class<?>> result = new ArrayList<Class<?>>();
        ArrayList<Class<?>> classesForPackage = ClassUtils.getClassesForPackage(pkg);
        for(Class c : classesForPackage){
            if(predicate.apply(c)){
                result.add(c);
            }
        }
        return (Class[]) result.toArray(new Class[0]);
    }

public static class NullPredicate implements Predicate<Class> {
       public static NullPredicate instance = new NullPredicate();
        @Override
        public boolean apply(Class object) {
            return true;
        }
    
}
public static interface Predicate<T>{
    
    public boolean apply(T object);
}


}

