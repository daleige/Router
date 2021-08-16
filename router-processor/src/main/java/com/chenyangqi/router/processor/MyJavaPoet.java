package com.chenyangqi.router.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.tools.JavaFileObject;

public class MyJavaPoet {
    private static final String packageName = "com.chenyangqi.app.javapoet";

    public static void test(Filer filer) {
        System.out.println("开始生产 Helloworld------");
        String className = "HelloWorld_" + System.currentTimeMillis();

        if (isExitClass(className)) {
            System.out.println("已存在" + className);
            return;
        } else {
            System.out.println("不存在" + className);
        }

        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder(packageName, helloWorld)
                .build();
        try {
            javaFile.writeTo(filer);

//            JavaFileObject source = filer.createSourceFile(packageName + "." + className);
//            Writer writer = source.openWriter();
//            writer.write(javaFile.toString());
//            writer.flush();
//            writer.close();
        } catch (IOException e) {
            System.out.println("写入时发生异常！");
            e.printStackTrace();
        }

        createRouterMapping(filer);
    }


    /* public class RouterMapping_1629095571610 {

         public static Map<String, String> get() {
             Map<String, String> mapping = new HashMap<>();
             mapping.put("router://page_login", "com.chenyangqi.business.LoginActivity");
             return mapping;
         }
     }*/
    public static void createRouterMapping(Filer filer) {
        ClassName stringClassName = ClassName.get("java.lang", "String");
        ClassName map = ClassName.get("java.util", "Map");
        ClassName hashMap = ClassName.get("java.util", "HashMap");
        TypeName mapOfString = ParameterizedTypeName.get(map, stringClassName, stringClassName);

        MethodSpec.Builder builder = MethodSpec.methodBuilder("get")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(mapOfString)
                .addStatement("$T mapping = new $T<>()", mapOfString, hashMap);
        for (int i = 0; i < 10; i++) {
            builder.addStatement("mapping.put(\"router://page_login\", \"com.chenyangqi.business.LoginActivity\")");
        }
        builder.addStatement("return mapping");
        MethodSpec getMethod = builder.build();

        String className = "RouterMapping_New_" + System.currentTimeMillis();
        TypeSpec routerMapping = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(getMethod)
                .build();

        JavaFile javaFile = JavaFile.builder(packageName, routerMapping)
                .build();
        try {
            System.out.println(javaFile.toString());
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isExitClass(String name) {
        try {
            Thread.currentThread().getContextClassLoader().loadClass(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
