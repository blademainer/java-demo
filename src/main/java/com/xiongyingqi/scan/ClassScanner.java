package com.xiongyingqi.scan;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author <a href="http://xiongyingqi.com">qi</a>
 * @version 2015-11-11 10:02
 */
public class ClassScanner {
    public static void main(String[] args) {
        Set<Class<?>> classes = ClassLookupHelper.getClasses(ObjectMapper.class.getPackage(), true,
                new ClassLookupHelper.ClassFileFilter() {
                    @Override
                    public boolean accept(String klassName, File file, ClassLoader loader) {
                        System.out.println(
                                "klassName ========= " + klassName + "  loader ======= " + loader);
                        return true;
                    }

                    @Override
                    public boolean accept(String klassName, JarFile jar, JarEntry entry,
                                          ClassLoader loader) {
                        return true;
                    }
                });
        System.out.println(classes);
    }
}
