package com.qk365.ocr.util;

import java.io.File;
import java.util.*;

public class ClassUtil {
    private static final String STAR = "*";
    private static Map classes = Collections.synchronizedMap(new HashMap());

    public static String convertResourceToClassName(String pResourceName) {
        return stripExtension(pResourceName).replace('/', '.');
    }

    public static String convertClassToResourcePath(String pName) {
        return pName.replace('.', '/') + ".class";
    }

    public static String stripExtension(String pResourceName) {
        int i = pResourceName.lastIndexOf(46);
        String withoutExtension = pResourceName.substring(0, i);

        return withoutExtension;
    }

    public static String toJavaCasing(String pName) {
        char[] name = pName.toLowerCase().toCharArray();
        name[0] = Character.toUpperCase(name[0]);
        return new String(name);
    }

    public static String clazzName(File base, File file) {
        int rootLength = base.getAbsolutePath().length();
        String absFileName = file.getAbsolutePath();
        int p = absFileName.lastIndexOf(46);
        String relFileName = absFileName.substring(rootLength + 1, p);

        String clazzName = relFileName.replace(File.separatorChar, '.');

        return clazzName;
    }

    public static String relative(File base, File file) {
        int rootLength = base.getAbsolutePath().length();
        String absFileName = file.getAbsolutePath();
        String relFileName = absFileName.substring(rootLength + 1);
        return relFileName;
    }

    public static String canonicalName(Class clazz) {
        StringBuilder name = new StringBuilder();

        if (clazz.isArray()) {
            name.append(canonicalName(clazz.getComponentType()));
            name.append("[]");
        } else if (clazz.getDeclaringClass() == null) {
            name.append(clazz.getName());
        } else {
            name.append(canonicalName(clazz.getDeclaringClass()));
            name.append(".");
            name.append(clazz.getName().substring(clazz.getDeclaringClass().getName().length() + 1));
        }

        return name.toString();
    }

    public static Object instantiateObject(String className) {
        return instantiateObject(className, null);
    }

    public static Object instantiateObject(String className, ClassLoader classLoader) {
        Class cls = (Class) classes.get(className);
        if (cls == null) {
            try {
                cls = Class.forName(className);
            } catch (Exception e) {
            }

            if ((cls == null) && (classLoader != null)) {
                try {
                    cls = classLoader.loadClass(className);
                } catch (Exception e) {
                }
            }

            if (cls == null) {
                try {
                    cls = ClassUtil.class.getClassLoader().loadClass(className);
                } catch (Exception e) {
                }
            }

            if (cls == null) {
                try {
                    cls = Thread.currentThread().getContextClassLoader().loadClass(className);
                } catch (Exception e) {
                }
            }

            if (cls == null) {
                try {
                    cls = ClassLoader.getSystemClassLoader().loadClass(className);
                } catch (Exception e) {
                }
            }

            if (cls != null) {
                classes.put(className, cls);
            } else {
                throw new RuntimeException("Unable to load class '" + className + "'");
            }

        }

        Object object = null;
        try {
            object = cls.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException("Unable to instantiate object for class '" + className + "'", e);
        }

        return object;
    }

    public static void addImportStylePatterns(Map<String, Object> patterns, String str) {
        if ((str == null) || ("".equals(str.trim()))) {
            return;
        }

        String[] items = str.split(" ");
        for (int i = 0; i < items.length; ++i) {
            List list;
            String qualifiedNamespace = items[i].substring(0, items[i].lastIndexOf(46)).trim();

            String name = items[i].substring(items[i].lastIndexOf(46) + 1).trim();
            Object object = patterns.get(qualifiedNamespace);
            if (object == null) {
                if ("*".equals(name)) {
                    patterns.put(qualifiedNamespace, "*");
                } else {
                    list = new ArrayList();
                    list.add(name);
                    patterns.put(qualifiedNamespace, list);
                }
            } else if ("*".equals(name)) {
                patterns.put(qualifiedNamespace, "*");
            } else {
                list = (List) object;
                if (!(list.contains(object))) {
                    list.add(name);
                }
            }
        }
    }

    public static boolean isMatched(Map<String, Object> patterns, String className) {
        String qualifiedNamespace = className.substring(0, className.lastIndexOf(46)).trim();

        String name = className.substring(className.lastIndexOf(46) + 1).trim();
        Object object = patterns.get(qualifiedNamespace);
        if (object == null) {
            return true;
        }
        if ("*".equals(object)) {
            return false;
        }
        if (patterns.containsKey("*")) {
            return true;
        }
        List list = (List) object;
        return (!(list.contains(name)));
    }

    public static String getPackage(Class<?> cls) {
        Package pkg = (cls.isArray()) ? cls.getComponentType().getPackage() : cls.getPackage();
        if (pkg == null) {
            int dotPos = cls.getName().lastIndexOf(46);
            if (dotPos > 0) {
                return cls.getName().substring(0, dotPos - 1);
            }

            return "";
        }

        return pkg.getName();
    }
}
