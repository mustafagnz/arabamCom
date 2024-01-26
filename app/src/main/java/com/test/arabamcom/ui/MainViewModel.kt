package com.test.arabamcom.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.arabamcom.api.ApiService
import com.test.arabamcom.api.PostModel
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _post = MutableLiveData<List<PostModel>>()
    val post: LiveData<List<PostModel>> = _post

    private val _hasError = MutableLiveData<Boolean>()
    val hasError: LiveData<Boolean> = _hasError

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _selectedPost = MutableLiveData<PostModel>()
    val selectedPost: LiveData<PostModel> = _selectedPost

    init {
        fetchPost()
    }

    fun setSelectedPost(post: PostModel) {
        _selectedPost.value = post
        Log.d(TAG, "setSelectedPost: LiveData dolu")
    }

    fun fetchPost() {
        viewModelScope.launch {
            try {
                val response = ApiService.api.fetchPost()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _post.value = it
                        _hasError.value = false
                    } ?: kotlin.run {
                        _hasError.value = true
                        _errorMessage.value = "API'den null veri döndü"
                        Log.e(TAG, "fetchPost: API'den null veri döndü")
                    }
                } else {
                    _hasError.value = true
                    _errorMessage.value = "API'den başarısız cevap alındı. HTTP kodu: ${response.code()}"
                    Log.e(TAG, "fetchPost: API'den başarısız cevap alındı. HTTP kodu: ${response.code()}")
                }
            } catch (e: Exception) {
                _hasError.value = true
                _errorMessage.value = "fetchPost error: ${e.message}"
                Log.e(TAG, "fetchPost error: ${e.message}", e)
            }
        }
    }
}
