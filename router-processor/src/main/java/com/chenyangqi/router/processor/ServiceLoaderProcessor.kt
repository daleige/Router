package com.chenyangqi.router.processor

import com.chenyangqi.router.annotations.ServiceLoader
import com.google.auto.service.AutoService
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types


@AutoService(Processor::class)
class ServiceLoaderProcessor : AbstractProcessor() {
    private val TAG = "ServiceLoaderProcessor"
    private var mElements: Elements? = null
    private var mTypeUtils: Types? = null
    private var mMessager: Messager? = null
    private var mFiler: Filer? = null

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        mElements = processingEnv?.elementUtils
        mTypeUtils = processingEnv?.typeUtils
        mMessager = processingEnv?.messager
        mFiler = processingEnv?.filer
    }

    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnvironment: RoundEnvironment
    ): Boolean {
        if (roundEnvironment.processingOver()) {
            println("$TAG  *** processingOver...")
            return false
        }
        println("$TAG  *** start processor...")
        val elements = roundEnvironment.getElementsAnnotatedWith(
            ServiceLoader::class.java
        )
        println("$TAG  *** elements size:${elements.size}")
        if (elements.size <= 0) {
            return false
        }

        elements.forEach {
            println("$TAG  *** elements 11111")
            val typeElement = it as TypeElement
            println("$TAG  *** elements 2")
            val serviceLoader = typeElement.getAnnotation(ServiceLoader::class.java)
            serviceLoader ?: let {
                return@forEach
            }
            println("$TAG  *** elements 3")

            val interfacesClazzs = serviceLoader.interfaces
            val defaultImpl = serviceLoader.defaultImpl
            val singleton = serviceLoader.singleton
            //val keys = serviceLoader.key

            println("$TAG  *** elements 4")

            println("$TAG  *** interfacesClazzs=$interfacesClazzs")
            println("$TAG  *** defaultImpl=$defaultImpl")
            println("$TAG  *** singleton=$singleton")
            //println("$TAG  *** keys=$keys")
        }

        println("$TAG *** end processor...")
        return false
    }

    override fun getSupportedAnnotationTypes(): Set<String?>? {
        println("$TAG *** run fun getSupportedAnnotationTypes() ")
        return setOf(ServiceLoader::class.java.canonicalName)
    }

    override fun getSupportedSourceVersion(): SourceVersion? {
        println("$TAG *** run fun getSupportedSourceVersion() ")
        return SourceVersion.RELEASE_8
    }
}