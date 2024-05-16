package com.example.expanceapp.view.mainGroup.mainScreen

import androidx.lifecycle.ViewModel
import com.example.expanceapp.data.remote.ExpanseAppApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val expanseAppApi: ExpanseAppApi,
) : ViewModel() {

}