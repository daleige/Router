package com.chenyangqi.router.processor

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import com.sun.xml.internal.fastinfoset.util.StringArray
import javax.lang.model.element.Modifier

/**
 * @author : ChenYangQi
 * date   : 2021/8/15 22:38
 * desc   :
 */
object JavaPoetTest {

    fun test() {
        val main: MethodSpec = MethodSpec.methodBuilder("main")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(TypeName.VOID::class.java)
            .addParameter(StringArray::class.java, "args")
            .addStatement("\$T.out.println(\$S)", System::class.java, "Hello, JavaPoet!")
            .build()

        val helloWorld = TypeSpec.classBuilder("HelloWorld")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .addMethod(main)
            .build()

        val javaFile = JavaFile.builder("com.chenyangqi.javapoet", helloWorld).build()
        println("Javapoet创建HelloWorld")
        javaFile.writeTo(System.out)
    }
}