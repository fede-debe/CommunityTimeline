package com.example.communitymessages.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.communitymessages.domain.model.response.Resource.Status.*
import com.example.communitymessages.domain.repository.ContentRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessageViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val _preference = MutableLiveData<Boolean>()
    val preference: LiveData<Boolean> = _preference

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun addToInterest(id: String) = viewModelScope.launch {
        contentRepository.addToInterest(id).collect {
            when (it.status) {
                SUCCESS -> {
                    _preference.postValue(true)
                }
                ERROR -> {
                    it.errorResponse?.message?.let { msg -> _errorMessage.postValue(msg) }
                }
                LOADING -> {
                    _preference.postValue(false)
                }
            }
        }
    }
}