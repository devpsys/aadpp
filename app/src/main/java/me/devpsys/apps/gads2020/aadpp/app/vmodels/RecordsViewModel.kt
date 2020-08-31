package me.devpsys.apps.gads2020.aadpp.app.vmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.devpsys.apps.gads2020.aadpp.app.models.Record

class RecordsViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val index: LiveData<Int> = _index

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading

    private val _learningLeaders = MutableLiveData<List<Record>>().apply { value = ArrayList() }
    val learningLeaders: LiveData<List<Record>> = _learningLeaders

    private val _skillIQLeaders = MutableLiveData<List<Record>>().apply { value = ArrayList() }
    val skillIQLeaders: LiveData<List<Record>> = _skillIQLeaders

    init {
        val records = ArrayList<Record>()

        records.add(Record())
        records.add(Record())
        records.add(Record())
        records.add(Record())
        records.add(Record())
        records.add(Record())
        records.add(Record())

        _learningLeaders.value = records
        _skillIQLeaders.value = records
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun records(): LiveData<List<Record>> =
        if (index.value == 1) learningLeaders else skillIQLeaders

    fun size(): Int {
        index.value?.let {
            if (it == 1) return learningLeaders.value?.size!! else skillIQLeaders.value?.size
        }
        return 0
    }
}