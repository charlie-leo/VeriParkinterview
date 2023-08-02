package com.veripark.interview

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

/**
 * Binding Data Class
 */
data class ServiceModel(var productDetailId: Long = 0) : BaseObservable() {


    @Bindable
    var serviceNumber: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.serviceNumber)
        }

    @Bindable
    var meterReading: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.meterReading)
        }

    @Bindable
    var consCost: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.consCost)
        }

    @Bindable
    var serviceNumberError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.serviceNumberError)
        }

    @Bindable
    var meterReadingError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.meterReadingError)
        }

    @Bindable
    var CommonError: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.CommonError)
        }

}

