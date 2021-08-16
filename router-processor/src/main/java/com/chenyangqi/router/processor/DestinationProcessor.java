package com.chenyangqi.router.processor;

import com.chenyangqi.router.annotations.Destination;
import com.google.auto.service.AutoService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * @author : ChenYangQi
 * date   : 6/20/21 12:01
 * desc   :Destination的注解处理器
 */
@AutoService(Processor.class)
public class DestinationProcessor extends AbstractProcessor {
    private final String TAG = getClass().getSimpleName();

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (roundEnvironment.processingOver()) {
            System.out.println(TAG + "  >>> processingOver...");
            return false;
        }

        System.out.println(TAG + "  >>> start process...");
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(Destination.class);
        System.out.println(TAG + " >>> all elementsAnnotatedWith count size=" + elementsAnnotatedWith.size());
        if (elementsAnnotatedWith.size() < 1) {
            return false;
        }

        //生产自动生产的类名
        ClassName stringClassName = ClassName.get("java.lang", "String");
        ClassName map = ClassName.get("java.util", "Map");
        ClassName hashMap = ClassName.get("java.util", "HashMap");
        TypeName mapOfString = ParameterizedTypeName.get(map, stringClassName, stringClassName);

        MethodSpec.Builder builder = MethodSpec.methodBuilder("get")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(mapOfString)
                .addStatement("$T mapping = new $T<>()", mapOfString, hashMap);

        final JsonArray destinationJsonArray = new JsonArray();

        for (Element element : elementsAnnotatedWith) {
            final TypeElement typeElement = (TypeElement) element;
            final Destination destination = typeElement.getAnnotation(Destination.class);
            if (destination == null) {
                continue;
            }
            final String url = destination.url();
            final String description = destination.description();
            final String realPath = typeElement.getQualifiedName().toString();

            builder.addStatement("mapping.put($S, $S)", url, realPath);

            JsonObject item = new JsonObject();
            item.addProperty("url", url);
            item.addProperty("description", description);
            item.addProperty("realPath", realPath);
            destinationJsonArray.add(item);
        }

        builder.addStatement("return mapping");
        MethodSpec getMethod = builder.build();

        TypeSpec routerMapping = TypeSpec.classBuilder(Constants.ROUTER_MAPPING_CLASSNAME + System.currentTimeMillis())
                .addModifiers(Modifier.PUBLIC)
                .addMethod(getMethod)
                .build();

        JavaFile javaFile = JavaFile.builder(Constants.ROUTER_MAPPING_PACKAGE, routerMapping)
                .build();
        try {
            System.out.println(javaFile.toString());
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //写入json到本地文件中
        String rootDir = processingEnv.getOptions().get("root_project_dir");
        System.out.println(" >>> rootDir:" + rootDir);
        final File rootDirFile = new File(rootDir);
        if (!rootDirFile.exists()) {
            throw new RuntimeException("root_project_dir not exist");
        }
        //创建子目录和mapping文件
        File routerFileDir = new File(rootDirFile, "router_mapping");
        if (!routerFileDir.exists()) {
            routerFileDir.mkdir();
        }
        File mappingFile = new File(routerFileDir, "mapping_" + System.currentTimeMillis() + ".json");
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(mappingFile));
            out.write(destinationJsonArray.toString());
            out.flush();
            out.close();
        } catch (Throwable throwable) {
            throw new RuntimeException("Error when writing json", throwable);
        }
        System.out.println(TAG + " >>> end process...");
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        System.out.println(TAG + " >>> run fun getSupportedAnnotationTypes() ");
        return Collections.singleton(Destination.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        System.out.println(TAG + " >>> run fun getSupportedSourceVersion() ");
        return SourceVersion.RELEASE_8;
    }
}
