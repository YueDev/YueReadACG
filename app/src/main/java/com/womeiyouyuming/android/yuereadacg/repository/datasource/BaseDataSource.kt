package com.womeiyouyuming.android.yuereadacg.repository.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.womeiyouyuming.android.yuereadacg.network.NetworkState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Yue on 2020/8/6.
 *
 *  按页数加载的DataSource基类
 *
 *
 */
abstract class BaseDataSource<T> : PageKeyedDataSource<Int, T>() {


    abstract val maxPage: Int

    abstract suspend fun loadData(page: Int): List<T>


    //网络状态信息
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    //加载错误的重试
    private var retry: (() -> Unit)? = null

    fun retryFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {


        CoroutineScope(Dispatchers.IO).launch {
            _networkState.postValue(NetworkState.FIRST_LOADING)
            retry = null

            try {
                val list = loadData(1)
                _networkState.postValue(NetworkState.SUCCESS)
                callback.onResult(list, null, 2)
            } catch (e: Exception) {
                _networkState.postValue(NetworkState.FAILED)
                retry = { loadInitial(params, callback) }
            }

        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {

        if (params.key > maxPage) return

        CoroutineScope(Dispatchers.IO).launch {
            _networkState.postValue(NetworkState.LOADING)
            retry = null

            try {
                val list = loadData(params.key)
                _networkState.postValue(NetworkState.SUCCESS)
                callback.onResult(list, params.key + 1)
            } catch (e: Exception) {
                _networkState.postValue(NetworkState.FAILED)
                retry = { loadAfter(params, callback) }
            }

        }


    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        //用不到
    }
}

