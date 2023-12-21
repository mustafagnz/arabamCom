package com.test.arabamcom.ui


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
        _selectedPost.value = post
    }
    // MainViewModel
    fun fetchPost(){
        viewModelScope.launch {
            val response = ApiService.api.fetchPost()
            if (response.isSuccessful){
                response.body()?.let { posts ->
                    _post.value = posts // API'den dönen PostModel listesini alıyoruz
                    _hasError.value = false
                } ?: run {
                    _hasError.value = true
                }
            } else {
                _hasError.value = true
            }
        }
    }



}