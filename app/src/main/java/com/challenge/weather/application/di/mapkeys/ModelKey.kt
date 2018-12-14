package com.challenge.weather.application.di.mapkeys

import com.challenge.weather.mvvm.model.ModelContract
import dagger.MapKey
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ModelKey(val value: KClass<out ModelContract>)
