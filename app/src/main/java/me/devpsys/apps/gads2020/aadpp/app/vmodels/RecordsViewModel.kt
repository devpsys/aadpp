package me.devpsys.apps.gads2020.aadpp.app.vmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import me.devpsys.apps.gads2020.aadpp.app.api.RetrofitClient
import me.devpsys.apps.gads2020.aadpp.app.models.Record
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class RecordsViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val index: LiveData<Int> = _index

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading

    private val _learningLeaders = MutableLiveData<List<Record>>().apply { value = ArrayList() }
    val learningLeaders: LiveData<List<Record>> = _learningLeaders

    private val _skillIQLeaders = MutableLiveData<List<Record>>().apply { value = ArrayList() }
    val skillIQLeaders: LiveData<List<Record>> = _skillIQLeaders

    private var retrofitClient = RetrofitClient.getInstance()

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun records(): LiveData<List<Record>> =
        if (index.value?.toInt() == 1) learningLeaders else skillIQLeaders

    fun size(): Int {
        val sizeOne: Int = learningLeaders.value?.let { return@let it.size }!!
        val sizeTwo: Int = skillIQLeaders.value?.let { return@let it.size }!!
        val index: Int = index.value!!

        return if (index == 1) sizeOne else sizeTwo
    }

    fun loadRecords() {
        toggleLoading(true)
        val index: Int = index.value!!
        if (index == 1)
            retrofitClient.hours().enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    toggleLoading(false)
                    try {
                        val records = ArrayList<Record>()
                        val array = JSONArray(response.body())
                        for (i in 0 until array.length()) {
                            val record = Gson().fromJson(array.getString(i), Record::class.java)

                            record.score = -1
                            record.hours = array.getJSONObject(i).getInt("hours")
                            records.add(record)
                        }
                        records.sortByDescending { it.hours }

                        _learningLeaders.value = records
                    } catch (e: Exception) {
                        Log.d("Response", "Exception: ${e.message}")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    toggleLoading(false)
                    Log.e("Response", "Error: ${t.message}")
                }
            })
        else
            retrofitClient.skilliq().enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    toggleLoading(false)
                    try {
                        val records = ArrayList<Record>()
                        val array = JSONArray(response.body())
                        for (i in 0 until array.length()) {
                            val record = Gson().fromJson(array.getString(i), Record::class.java)

                            records.add(record)
                        }
                        records.sortByDescending { it.score }

                        _skillIQLeaders.value = records
                    } catch (e: Exception) {
                        Log.d("Response", "Exception: ${e.message}")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    toggleLoading(false)
                    Log.e("Response", "Error: ${t.message}")
                }
            })
    }

    private fun toggleLoading(value: Boolean) {
        _loading.value = value
    }
}