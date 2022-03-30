package com.example.communitymessages.presentation.ui.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.communitymessages.domain.model.asUiModel
import com.example.communitymessages.domain.model.local.Message
import com.example.communitymessages.domain.repository.ContentRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    contentRepository: ContentRepository
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean?>()
    val isLoading: LiveData<Boolean?> = _isLoading

    private val _networkError = MutableLiveData<Boolean?>()
    val networkError: LiveData<Boolean?> = _networkError

    private val _navigateToMessageDetails = MutableLiveData<Message?>()
    val navigateToMessageDetails: LiveData<Message?> = _navigateToMessageDetails

    private val currentId: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val timeline = currentId.switchMap { id ->
        contentRepository.getTimeline(id).map { timelineResponse ->
            timelineResponse.map { it.asUiModel() }
        }.cachedIn(viewModelScope)
    }

    fun getId(id: String) {
        currentId.value = id
    }

    fun openMessageDetails(message: Message) {
        _navigateToMessageDetails.postValue(message)
    }

    fun openMessageDetailsComplete() {
        _navigateToMessageDetails.postValue(null)
    }
}