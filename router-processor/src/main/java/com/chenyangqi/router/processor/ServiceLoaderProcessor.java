package com.chenyangqi.router.processor;

import com.chenyangqi.router.annotations.ServiceLoader;
import com.chenyangqi.router.processor.bean.ServiceLoaderBean;
import com.chenyangqi.router.processor.test.JavaPoetTest;
import com.chenyangqi.router.processor.test.ServiceLoader_1111;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class ServiceLoaderProcessor extends AbstractProcessor {
    private final String TAG = this.getClass().getSimpleName();
    private Elements mElementUtils;
    private Types mTypeUtils;
    private Messager mMessager;
    private Filer mFiler;

    private final Map<String, ServiceLoaderBean> serviceMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
        mTypeUtils = processingEnv.getTypeUtils();
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
    }

    /**
     * 注解处理器中无法直接获取除String和基本类型之外的数据
     * 需要通过{@link javax.lang.model.element.AnnotationMirror}}获取
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            System.out.println(TAG + "  *** processingOver...");
            return false;
        }

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ServiceLoader.class);
        System.out.println("******************** begin, size=" + elements.size());
        if (elements.size() < 1) {
            return false;
        }

        try {
            for (Element element : elements) {
                final TypeElement typeElement = (TypeElement) element;
                ServiceLoader serviceLoader = typeElement.getAnnotation(ServiceLoader.class);
                boolean singleton = serviceLoader.singleton();
                String key = serviceLoader.key();
                String realPath = typeElement.getQualifiedName().toString();
                System.out.println("--->>>> realPath" + realPath);

                List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
                for (AnnotationMirror am : annotationMirrors) {
                    Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = am.getElementValues();
                    for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> ev : elementValues.entrySet()) {
                        if (ev.getKey().toString().equals("interfaces()")) {
                            System.out.println("--->>>> interfaces=" + ev.getValue());
                            List<String> parseInterface = parseInterface(ev);
                            if (parseInterface != null && parseInterface.size() > 0) {
                                //TODO 这里暂时只考虑一个接口只有一个实现类的情况，只取第一个Class
                                ServiceLoaderBean bean = new ServiceLoaderBean(parseInterface.get(0), realPath, key, singleton);
                                serviceMap.put(bean.getInterfaceName(), bean);
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < serviceMap.size(); i++) {
                System.out.println("ServiceLoader--->" + (i + 1) + "=" + serviceMap.size());
            }

            JavaPoetTest.test2(mFiler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 解析类似这种{com.chenyangqi.base.common.IGetUserInfo.class, com.chenyangqi.business.MyTest.class}
     * 转为Class<?>[]
     *
     * @param ev
     */
    private List<String> parseInterface(Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> ev) {
        List<String> classNameList = new ArrayList<>();
        String value = ev.getValue().toString()
                .replace("{", "")
                .replace("}", "")
                .replace(",", "\n")
                .replace(" ", "");
        System.out.println("value =" + value);
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8));
        String line;
        try {
            while (((line = br.readLine()) != null)) {
                classNameList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return classNameList;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        System.out.println(TAG + " *** run fun getSupportedAnnotationTypes() ");
        return Collections.singleton(ServiceLoader.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        System.out.println(TAG + " *** run fun getSupportedSourceVersion() ");
        return SourceVersion.RELEASE_8;
    }
}
