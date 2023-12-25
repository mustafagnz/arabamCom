package com.test.arabamcom.ui


import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.arabamcom.api.ApiService
import com.test.arabamcom.api.PostModel
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {



    private val _post = MutableLiveData<List<PostModel>>()
    val post: LiveData<List<PostModel>>
        get() = _post

    init {
        fetchPost()
    }

    private val _hasError = MutableLiveData<Boolean>()
    val hasError: LiveData<Boolean>
        get() = _hasError


    private val _selectedPost = MutableLiveData<PostModel>()

    val selectedPost: LiveData<PostModel> get() = _selectedPost

    fun setSelectedPost(post: PostModel) {
        if (post != null) {
            _selectedPost.value = post
            Log.d(TAG, "setSelectedPost: LiveData dolu")

        } else {
            Log.d(TAG, "setSelectedPost: LiveData boş")
        }
    }

    // MainViewModel
    fun fetchPost() {
        viewModelScope.launch {
            try {
                val response = ApiService.api.fetchPost()
                if (response.isSuccessful) {
                    Log.d(TAG, "fetchPost: API'den veri geldi")

                    response.body()?.let { postList ->
                        _post.value = postList
                        _hasError.value = false
                    } ?: run {
                        _hasError.value = true
                        Log.e(TAG, "fetchPost: API'den null veri döndü")
                    }
                } else {
                    _hasError.value = true
                    Log.e(TAG, "fetchPost: API'den başarısız cevap alındı. HTTP kodu: ${response.code()}")
                }
            } catch (e: Exception) {
                // Hata durumu
                _hasError.value = true
                Log.e(TAG, "fetchPost error: ${e.message}", e)
            }
        }
    }





}


