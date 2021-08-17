package com.chenyangqi.router.processor;

import com.chenyangqi.router.annotations.ServiceLoader;
import com.chenyangqi.router.processor.test.Utils;
import com.google.common.collect.ImmutableList;

import org.jetbrains.annotations.Contract;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.xml.transform.Transformer;

public class ServiceLoaderProcessor extends AbstractProcessor {
    private final String TAG = this.getClass().getSimpleName();
    private Elements mElementUtils;
    private Types mTypeUtils;
    private Messager mMessager;
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
        mTypeUtils = processingEnv.getTypeUtils();
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations == null || annotations.size() <= 0) {
            return false;
        }
        if (roundEnv.processingOver()) {
            System.out.println(TAG + "  *** processingOver...");
            return false;
        }

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ServiceLoader.class);
        if (elements == null || elements.size() <= 0) {
            return false;
        }
        try {
            for (Element element : elements) {
                final TypeElement typeElement = (TypeElement) element;
                ServiceLoader serviceLoader = typeElement.getAnnotation(ServiceLoader.class);
                boolean singleton = serviceLoader.singleton();
                boolean defaultImpl = serviceLoader.defaultImpl();
                String realPath = typeElement.getQualifiedName().toString();

                List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
                for (AnnotationMirror am : annotationMirrors) {
                    Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = am.getElementValues();
                    for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> ev : elementValues.entrySet()) {
                        if (ev.getKey().toString().equals("interfaces()")) {
                            System.out.println("--->>>> interfaces=" + ev.getValue());
                        }
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public ExecutableElement getExecutableElement(final TypeElement typeElement,
                                                  final Name name) {
        TypeElement te = typeElement;
        do {
            te = (TypeElement) processingEnv.getTypeUtils().asElement(te.getSuperclass());
            if (te != null) {
                for (ExecutableElement ee : ElementFilter.methodsIn(te.getEnclosedElements())) {
                    if (name.equals(ee.getSimpleName()) && ee.getParameters().isEmpty()) {
                        return ee;
                    }
                }
            }
        } while (te != null);
        return null;
    }

    public static TypeElement findEnclosingTypeElement(Element e) {
        while (e != null && !(e instanceof TypeElement)) {
            e = e.getEnclosingElement();
        }
        return TypeElement.class.cast(e);
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
