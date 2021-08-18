package com.chenyangqi.router.processor.test;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Random;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

public class JavaPoetTest {
    private static final String packageName = "com.chenyangqi.app.javapoet";

    public static void test(Filer filer) {
        System.out.println("开始生产 Helloworld------");
        String className = "HelloWorld_" + System.currentTimeMillis();

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

    /**
     * 判断类是否存在
     *
     * @param name
     * @return
     */
    public static boolean isExitClass(String name) {
        try {
            Thread.currentThread().getContextClassLoader().loadClass(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }


    /*
    *
import com.chenyangqi.router.processor.ServiceLoaderBean;

import java.util.HashMap;
import java.util.Map;

public class ServiceLoader_1111 {

    public static Map<String, ServiceLoaderBean> get() {
        Map<String, ServiceLoaderBean> mapping = new HashMap<>();

        ServiceLoaderBean bean1 = new ServiceLoaderBean("111", "222", "33", true);
        mapping.put(bean1.getInterfaceName(), bean1);

        ServiceLoaderBean bean2 = new ServiceLoaderBean("1112", "2223", "334", true);
        mapping.put(bean1.getInterfaceName(), bean2);

        return mapping;
    }
}
    * */
    public static void test2(Filer filer) {
        try {

            ClassName stringClassName = ClassName.get("java.lang", "String");
            ClassName serviceLoaderBean = ClassName.get("com.chenyangqi.router.processor", "ServiceLoaderBean");
            ClassName map = ClassName.get("java.util", "Map");
            ClassName hashMap = ClassName.get("java.util", "HashMap");
            TypeName mapOfString = ParameterizedTypeName.get(map, stringClassName, serviceLoaderBean);

            MethodSpec.Builder builder = MethodSpec.methodBuilder("get")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(mapOfString)
                    .addStatement("$T mapping = new $T<>()", mapOfString, hashMap);

            for (int i = 0; i < 4; i++) {
                builder.addStatement("$T bean$L = new $T($S, $S, $S, $L)", serviceLoaderBean, i, serviceLoaderBean, "11", "11", "11", true);
                builder.addStatement("mapping.put(bean$L.getInterfaceName(), bean$L)", i, i);
            }

            builder.addStatement("return mapping");
            MethodSpec getMethod = builder.build();

            String className = "ServiceLoader_" + new Random().nextInt(999999) + "_" + System.currentTimeMillis();
            TypeSpec ServiceLoaderInfo = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(getMethod)
                    .build();

            JavaFile javaFile = JavaFile.builder(packageName, ServiceLoaderInfo)
                    .build();
            try {
                System.out.println(javaFile.toString());
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
