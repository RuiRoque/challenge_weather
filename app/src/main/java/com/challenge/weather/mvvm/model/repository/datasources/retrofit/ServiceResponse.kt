package com.challenge.weather.mvvm.model.repository.datasources.retrofit

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import retrofit2.HttpException
import java.io.Serializable

@Parcelize
open class ServiceResponse(private val throwable: Throwable?) : Serializable, Parcelable {
    open fun isValid() = throwable == null
    open fun notFound(): Boolean {
        return if (throwable is HttpException) {
            throwable.code() == 404
        } else {
            false
        }
    }
}
