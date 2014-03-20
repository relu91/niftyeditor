/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author cris
 */
public class ClassUtils {
    
    public static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException{
        return ClassUtils.getClasses(packageName, NullPredicate.instance);
    }
    /**
 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
 *
 * @param packageName The base package
 * @return The classes
 * @throws ClassNotFoundException
 * @throws IOException
 */
public static Class[] getClasses(String packageName,Predicate<Class> predicate)
        throws ClassNotFoundException, IOException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    assert classLoader != null;
    String path = packageName.replace('.', '/');
    Enumeration<URL> resources = classLoader.getResources(path);
    List<File> dirs = new ArrayList<File>();
    while (resources.hasMoreElements()) {
        URL resource = resources.nextElement();
        dirs.add(new File(resource.getFile()));
    }
    ArrayList<Class> classes = new ArrayList<Class>();
    for (File directory : dirs) {
        classes.addAll(findClasses(directory, packageName,predicate));
    }
    return classes.toArray(new Class[classes.size()]);
}

/**
 * Recursive method used to find all classes in a given directory and subdirs.
 *
 * @param directory   The base directory
 * @param packageName The package name for classes found inside the base directory
 * @return The classes
 * @throws ClassNotFoundException
 */
private static List<Class> findClasses(File directory, String packageName, Predicate<Class> predicate) throws ClassNotFoundException {
    List<Class> classes = new ArrayList<Class>();
    if (!directory.exists()) {
        return classes;
    }
    File[] files = directory.listFiles();
    for (File file : files) {
        if (file.isDirectory()) {
            assert !file.getName().contains(".");
            classes.addAll(findClasses(file, packageName + "." + file.getName(),predicate));
        } else if (file.getName().endsWith(".class")) {
            Class<?> newClass = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
            if(predicate.apply(newClass)){
                classes.add(newClass);
            }
        }
    }
    return classes;
}

public static void main(String[] args) throws IOException, ClassNotFoundException{
    Class [] res = ClassUtils.getClasses("jada.ngeditor.model",NullPredicate.instance);
    for(Class c : res){
        System.out.println(c.getName());
    }
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

