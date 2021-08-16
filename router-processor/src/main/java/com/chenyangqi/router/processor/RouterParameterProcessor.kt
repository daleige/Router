package com.chenyangqi.router.processor

import com.chenyangqi.router.annotations.AutoAssignment
import com.google.auto.service.AutoService
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

/**
 * 自动初始化路由参数注解的变量初始化
 */
@AutoService(Processor::class)
class RouterParameterProcessor : AbstractProcessor() {

    private var mElements: Elements? = null
    private var mTypeUtils: Types? = null
    private var mMessager: Messager? = null
    private var mFiler: Filer? = null

    /**
     * 用于存储[com.chenyangqi.router.annotations.AutoAssignment]注解的字段的集合
     */
    private val mTempParameterMap: MutableMap<TypeElement, List<Element>> = mutableMapOf()

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        mElements = processingEnv?.elementUtils
        mTypeUtils = processingEnv?.typeUtils
        mMessager = processingEnv?.messager
        mFiler = processingEnv?.filer
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        println("begin javapoet...")

        println("end javapoet...")
        return false
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return Collections.singleton(AutoAssignment::class.java.canonicalName)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.RELEASE_8
    }
}