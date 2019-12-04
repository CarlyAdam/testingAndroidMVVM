package com.example.myapplication.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.ApiService
import com.example.myapplication.data.pojo.person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.devbizne.bizne.utils.Coroutines

class PersonRepository(
    private val api: ApiService

) {

    private val personList = ArrayList<person>()

    private val _personLiveData = MutableLiveData<List<person>>()
    val personLiveData : LiveData<List<person>> get() = _personLiveData

    private val _resultLiveData = MutableLiveData<Int>()
    val resultLiveData: LiveData<Int> get() = _resultLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    suspend fun getPersons(): LiveData<List<person>> {
        return withContext(Dispatchers.IO) {
            fetchPersons()
        }
    }

    suspend fun postLogin(username:String,password:String) {
        return withContext(Dispatchers.IO) {
            login(username,password)
        }
    }


    private suspend fun login(username:String,password:String)  {
        _isLoading.postValue(true)
        try {
            val response = api.login(username,password)
            if (response.isSuccessful) {
                _isLoading.postValue(false)
            } else {
                _isLoading.postValue(false)
            }

        } catch (e: Exception) {
            _isLoading.postValue(false)
            e.printStackTrace()
        }

    }

    private suspend fun fetchPersons() :LiveData<List<person>> {
        _isLoading.postValue(true)
        try {
            val response = api.getPerson()
            if (response.isSuccessful) {
                _isLoading.postValue(false)
                personList.clear()
                personList.addAll(response.body()!!.persons!!)
                postData(personList,response.code())
                Log.i("Response", personList.size.toString())
            } else {
                _isLoading.postValue(false)
                postDataError(response.code())
                Log.i("Response", response.message())
            }

        } catch (e: Exception) {
            _isLoading.postValue(false)
            postDataError(500)
            e.printStackTrace()
        }

        return personLiveData

    }

    private fun postData(personList:List<person>,code: Int) {
        Coroutines.io {
            _personLiveData.postValue(personList)
            _resultLiveData.postValue(code)
        }
    }

    private fun postDataError(code: Int) {
        Coroutines.io {
            _personLiveData.postValue(personList)
            _resultLiveData.postValue(code)
        }
    }



}