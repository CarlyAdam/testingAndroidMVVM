package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.pojo.person
import com.example.myapplication.repo.PersonRepository

class PersonViewModel(
    var personRepository: PersonRepository
) : ViewModel() {

    lateinit var  person : LiveData<List<person>>
    var isLoading = personRepository.isLoading

    suspend fun getPerson(): LiveData<List<person>> {
        person = personRepository.getPersons()
        return person
    }

    suspend fun login(username:String,password:String) {
        personRepository.postLogin(username,password)
    }



}