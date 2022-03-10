package com.example.lifediary.utils.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

class FourSourceLiveData<T, K, F, J, S>(
    source1: LiveData<T>,
    source2: LiveData<K>,
    source3: LiveData<F>,
    source4: LiveData<J>,
    private val combine: (data1: T?, data2: K?, data3: F?, data4: J?) -> S
) : MediatorLiveData<S>() {
    private var data1: T? = null
    private var data2: K? = null
    private var data3: F? = null
    private var data4: J? = null

    init {
        super.addSource(source1) {
            data1 = it
            value = combine(data1, data2, data3, data4)
        }

        super.addSource(source2) {
            data2 = it
            value = combine(data1, data2, data3, data4)
        }

        super.addSource(source3) {
            data3 = it
            value = combine(data1, data2, data3, data4)
        }

        super.addSource(source4) {
            data4 = it
            value = combine(data1, data2, data3, data4)
        }
    }

    override fun <S : Any?> addSource(source: LiveData<S>, onChanged: Observer<in S>) {
        throw UnsupportedOperationException()
    }

    override fun <S : Any?> removeSource(toRemote: LiveData<S>) {
        throw UnsupportedOperationException()
    }
}