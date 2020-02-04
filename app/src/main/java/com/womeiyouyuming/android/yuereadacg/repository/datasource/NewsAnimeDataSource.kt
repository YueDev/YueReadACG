package com.womeiyouyuming.android.yuereadacg.repository.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.womeiyouyuming.android.yuereadacg.model.News
import com.womeiyouyuming.android.yuereadacg.network.NetworkState
import com.womeiyouyuming.android.yuereadacg.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Created by Yue on 2020/2/4.
 */
class NewsAnimeDataSource(private val newsRepository: NewsRepository) :
    PageKeyedDataSource<Int, News>() {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private var retry: (() -> Unit)? = null

    fun retryFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, News>
    ) {

        CoroutineScope(Dispatchers.IO).launch {
            _networkState.postValue(NetworkState.FIRST_LOADING)
            retry = null

            try {
                newsRepository.getNewsAnime().string()


            } catch (e: Exception) {
                _networkState.postValue(NetworkState.FAILED)

                retry = {
                    loadInitial(params, callback)
                }

                e.printStackTrace()
            }

        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {

    }
}