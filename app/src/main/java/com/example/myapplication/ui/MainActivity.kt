package com.example.myapplication.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.viewmodel.PersonViewModel
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_main.*
import mx.devbizne.bizne.utils.Coroutines
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    val personViewModel: PersonViewModel by inject()
    var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .build()


        personViewModel.isLoading.observe(this, Observer {
            if (it) {
                dialog!!.show()
            } else {
                dialog!!.dismiss()
            }

        })
        buttonLogin.setOnClickListener{
            login(editTextUsername.text.toString(),editTextPassword.text.toString())
        }

    }

    private fun setData() = Coroutines.main {
        personViewModel.getPerson().observe(this, Observer {
            //textViewName.text = it[0].name
        })

    }

    private fun login(username:String,password:String) = Coroutines.main {
        personViewModel.login(username,password)

    }
}
