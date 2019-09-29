package com.aprz.savehelper.processor

import com.aprz.savehelper.annotation.*
import com.google.auto.service.AutoService
import com.google.common.collect.ImmutableSet
import com.squareup.javapoet.*
import java.io.IOException
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic

/**
 * @author by liyunlei
 *
 * write on 2019/9/28
 *
 * Class desc:
 */
@AutoService(Processor::class)
class SaveFieldProcessor : AbstractProcessor() {

    private var filer: Filer? = null
    private var messager: Messager? = null
    private var elements: Elements? = null
    private val fieldMap = HashMap<Element, Annotation>()
    private var typeElement: TypeElement? = null

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        filer = processingEnv.filer
        messager = processingEnv.messager
        elements = processingEnv.elementUtils
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {

        fieldMap.clear()

        val annotationList = listOf(
            IntField::class.java,
            LongField::class.java,
            FloatField::class.java,
            DoubleField::class.java,
            StringField::class.java
        )

        annotationList.forEach {

            roundEnv?.apply {
                for (element in getElementsAnnotatedWith(it)) {
                    if (element.kind != ElementKind.FIELD) {
                        messager?.printMessage(Diagnostic.Kind.ERROR, "该注解${it.simpleName}只能修饰字段哦.")
                        return true
                    }

                    typeElement = element.enclosingElement as TypeElement

                    if (element.modifiers.contains(Modifier.PRIVATE)) {
                        throw  IllegalAccessException("字段不能是私有的！！ + ${element.simpleName} + ${element.modifiers}")
                    }

                    fieldMap[element] = element.getAnnotation(it)
                }
            }

        }

        if (fieldMap.size > 0) {
            generateFieldSaving(fieldMap)
        }

        return false
    }

    private fun generateFieldSaving(fieldMap: java.util.HashMap<Element, Annotation>) {

        val qualifiedName = typeElement?.qualifiedName.toString()
        val packageName = qualifiedName.substring(0, qualifiedName.lastIndexOf("."))

        val fieldSavingClass = TypeSpec
            .classBuilder("${typeElement?.simpleName}_FieldSaving")
            .addSuperinterface(ClassName.get("com.aprz.savehelper.api", "SaveUnbinder"))
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)

        val fieldBuilder = FieldSpec.builder(TypeName.get(typeElement?.asType()), "target")

        val activityType = ClassName.get("android.content", "Intent")
        val bundleType = ClassName.get("android.os", "Bundle")
        val handlerType = ClassName.get("com.aprz.savehelper.api", "IntentHandlerKt")

        val constructorBuilder = MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            .addParameter(TypeName.get(typeElement?.asType()), "activity")
            .addParameter(activityType, "intent")
            .addParameter(bundleType, "bundle")

        constructorBuilder.addStatement("this.target = activity")

        fieldMap.forEach { (k, v) ->
            when (v) {
                is IntField -> constructorBuilder
                    .addStatement(
                        "this.target.${k.simpleName} = \$T.getSavedInt(intent, bundle, \$S, \$L)",
                        handlerType, v.key, v.defaultValue
                    )
                is LongField -> constructorBuilder
                    .addStatement(
                        "this.target.${k.simpleName} = \$T.getSavedLong(intent, bundle, \$S, \$LL)",
                        handlerType, v.key, v.defaultValue
                    )
                is FloatField -> constructorBuilder
                    .addStatement(
                        "this.target.${k.simpleName} = \$T.getSavedFloat(intent, bundle, \$S, \$LF)",
                        handlerType, v.key, v.defaultValue
                    )
                is DoubleField -> constructorBuilder
                    .addStatement(
                        "this.target.${k.simpleName} = \$T.getSavedDouble(intent, bundle, \$S, \$LD)",
                        handlerType, v.key, v.defaultValue
                    )
                is StringField -> constructorBuilder
                    .addStatement(
                        "this.target.${k.simpleName} = \$T.getSavedString(intent, bundle, \$S, \$S)",
                        handlerType, v.key, v.defaultValue
                    )
            }
        }


        val saveBuilder = MethodSpec.methodBuilder("save")
            .addAnnotation(ClassName.get("java.lang", "Override"))
            .addModifiers(Modifier.PUBLIC)
            .addParameter(bundleType, "outState")
            .returns(TypeName.VOID)

        fieldMap.forEach { (k, v) ->
            when (v) {
                is IntField ->
                    saveBuilder.addStatement(
                        "outState.putInt(\$S, this.target.${k.simpleName})",
                        v.key
                    )
                is LongField ->
                    saveBuilder.addStatement(
                        "outState.putLong(\$S, this.target.${k.simpleName})",
                        v.key
                    )
                is FloatField ->
                    saveBuilder.addStatement(
                        "outState.putFloat(\$S, this.target.${k.simpleName})",
                        v.key
                    )
                is DoubleField ->
                    saveBuilder.addStatement(
                        "outState.putDouble(\$S, this.target.${k.simpleName})",
                        v.key
                    )
                is StringField ->
                    saveBuilder.addStatement(
                        "outState.putString(\$S, this.target.${k.simpleName})",
                        v.key
                    )
            }
        }

        val unbindBuild = MethodSpec.methodBuilder("unbind")
            .addAnnotation(ClassName.get("java.lang", "Override"))
            .addModifiers(Modifier.PUBLIC)
            .returns(TypeName.VOID)
            .addStatement("this.target = null")

        fieldSavingClass.addField(fieldBuilder.build())
        fieldSavingClass.addMethod(constructorBuilder.build())
        fieldSavingClass.addMethod(saveBuilder.build())
        fieldSavingClass.addMethod(unbindBuild.build())

        try {
            JavaFile.builder(packageName, fieldSavingClass.build())
                .build()
                .writeTo(filer)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        return ImmutableSet.of(
            IntField::class.java.canonicalName,
            LongField::class.java.canonicalName,
            FloatField::class.java.canonicalName,
            DoubleField::class.java.canonicalName,
            StringField::class.java.canonicalName
        )
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

}