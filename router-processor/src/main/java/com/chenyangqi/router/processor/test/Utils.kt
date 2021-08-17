package com.chenyangqi.router.processor.test

import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeMirror

object Utils {

    private fun getEventTypeAnnotationMirror(typeElement: TypeElement, clazz: Class<*>): AnnotationMirror? {
        val clazzName = clazz.name
        for (m in typeElement.annotationMirrors) {
            if (m.annotationType.toString() == clazzName) {
                return m
            }
        }
        return null
    }

    private fun getAnnotationValue(annotationMirror: AnnotationMirror, key: String): AnnotationValue? {
        for ((key1, value) in annotationMirror.elementValues) {
            if (key1!!.simpleName.toString() == key) {
                return value
            }
        }
        return null
    }

    fun getMyValue(foo: TypeElement, clazz: Class<*>, key: String): TypeMirror? {
        val am = getEventTypeAnnotationMirror(foo, clazz) ?: return null
        val av = getAnnotationValue(am, key)
        return if (av == null) {
            null
        } else {
            av.value as TypeMirror
        }

    }
}