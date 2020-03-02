package com.womeiyouyuming.android.yuereadacg.repository.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.womeiyouyuming.android.yuereadacg.model.News
import com.womeiyouyuming.android.yuereadacg.network.NetworkState
import com.womeiyouyuming.android.yuereadacg.repository.NewsRepository
import com.womeiyouyuming.android.yuereadacg.util.parseNewsAnimeList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Yue on 2020/2/4.
 *
 * 各类News分页加载的基类
 *
 */
abstract class BaseNewsDataSource(val newsRepository: NewsRepository) :
    PageKeyedDataSource<Int, News>() {


    abstract suspend fun getNewsFirstPage(): String
    abstract suspend fun getNewsByPage(page: Int): String


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
                val httpResult = getNewsFirstPage()
                val list = parseNewsAnimeList(httpResult)
                _networkState.postValue(NetworkState.SUCCESS)
                callback.onResult(list, null, 2)
            } catch (e: Exception) {
                _networkState.postValue(NetworkState.FAILED)
                retry = { loadInitial(params, callback) }
                e.printStackTrace()
            }
        }


    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {


        CoroutineScope(Dispatchers.IO).launch {

            _networkState.postValue(NetworkState.LOADING)
            retry = null

            try {
                val httpResult = getNewsByPage(params.key)
                val list = parseNewsAnimeList(httpResult)
                _networkState.postValue(NetworkState.SUCCESS)
                callback.onResult(list, params.key + 1)
            } catch (e: Exception) {
                _networkState.postValue(NetworkState.FAILED)
                retry = { loadAfter(params, callback) }
                e.printStackTrace()
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        //用不到，不处理
    }
}


class NewsAnimeDataSource(newsRepository: NewsRepository) : BaseNewsDataSource(newsRepository) {

    override suspend fun getNewsFirstPage(): String = newsRepository.getNewsAnime().string()

    override suspend fun getNewsByPage(page: Int): String =
        newsRepository.getNewsAnime(page).string()
}

class NewsExpoDataSource(newsRepository: NewsRepository) : BaseNewsDataSource(newsRepository) {

    override suspend fun getNewsFirstPage(): String = newsRepository.getNewsExpo().string()

    override suspend fun getNewsByPage(page: Int): String =
        newsRepository.getNewsExpo(page).string()

}


class NewsBeautyDataSource(newsRepository: NewsRepository) : BaseNewsDataSource(newsRepository) {

    override suspend fun getNewsFirstPage(): String = newsRepository.getNewsBeauty().string()

    override suspend fun getNewsByPage(page: Int): String =
        newsRepository.getNewsBeauty(page).string()

}


class NewsFigmaDataSource(newsRepository: NewsRepository) : BaseNewsDataSource(newsRepository) {

    override suspend fun getNewsFirstPage(): String = newsRepository.getNewsFigma().string()

    override suspend fun getNewsByPage(page: Int): String =
        newsRepository.getNewsFigma(page).string()

}

class NewsSpecialDataSource(newsRepository: NewsRepository) : BaseNewsDataSource(newsRepository) {

    override suspend fun getNewsFirstPage(): String = newsRepository.getNewsSpecial().string()

    override suspend fun getNewsByPage(page: Int): String =
        newsRepository.getNewsSpecial(page).string()

}

class NewsBaguaDataSource(newsRepository: NewsRepository) : BaseNewsDataSource(newsRepository) {

    override suspend fun getNewsFirstPage(): String = newsRepository.getNewsBagua().string()

    override suspend fun getNewsByPage(page: Int): String =
        newsRepository.getNewsBagua(page).string()

}




class NewsGameDataSource(newsRepository: NewsRepository) : BaseNewsDataSource(newsRepository) {

    override suspend fun getNewsFirstPage(): String = newsRepository.getNewsGame().string()

    override suspend fun getNewsByPage(page: Int): String =
        newsRepository.getNewsGame(page).string()

}

