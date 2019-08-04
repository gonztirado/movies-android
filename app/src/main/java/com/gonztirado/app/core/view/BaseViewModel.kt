package com.gonztirado.app.core.view

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.gonztirado.app.util.exception.Failure

/**
 * Base ViewModel class with default Failure handling.
 * @see ViewModel
 * @see Failure
 */
abstract class BaseViewModel : ViewModel() {

    var failure: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }
}