package com.challenge.weather.application.di.mapkeys

import com.challenge.weather.mvvm.viewmodel.UseCaseContract
import dagger.MapKey
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class UseCaseKey(val value: KClass<out UseCaseContract>)
