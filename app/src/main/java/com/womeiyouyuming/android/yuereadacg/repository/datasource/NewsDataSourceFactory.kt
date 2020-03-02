package com.womeiyouyuming.android.yuereadacg.repository.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.womeiyouyuming.android.yuereadacg.model.News
import com.womeiyouyuming.android.yuereadacg.repository.NewsRepository


/**
 * Created by Yue on 2020/2/5.
 */



class NewsAnimeDataSourceFactory(private val newsRepository: NewsRepository) : DataSource.Factory<Int, News>() {

    private val _source = MutableLiveData<NewsAnimeDataSource>()
    val source: LiveData<NewsAnimeDataSource> = _source

    override fun create() = NewsAnimeDataSource(newsRepository).also {
        _source.postValue(it)
    }
}

class NewsExpoDataSourceFactory(private val newsRepository: NewsRepository) : DataSource.Factory<Int, News>() {
    private val _source = MutableLiveData<NewsExpoDataSource>()
    val source: LiveData<NewsExpoDataSource> = _source

    override fun create() = NewsExpoDataSource(newsRepository).also {
        _source.postValue(it)
    }
}


class NewsBeautyDataSourceFactory(private val newsRepository: NewsRepository) : DataSource.Factory<Int, News>() {

    private val _source = MutableLiveData<NewsBeautyDataSource>()
    val source: LiveData<NewsBeautyDataSource> = _source

    override fun create() = NewsBeautyDataSource(newsRepository).also {
        _source.postValue(it)
    }
}





class NewsFigmaDataSourceFactory(private val newsRepository: NewsRepository) : DataSource.Factory<Int, News>() {

    private val _source = MutableLiveData<NewsFigmaDataSource>()
    val source: LiveData<NewsFigmaDataSource> = _source

    override fun create() = NewsFigmaDataSource(newsRepository).also {
        _source.postValue(it)
    }
}

class NewsSpecialDataSourceFactory(private val newsRepository: NewsRepository) : DataSource.Factory<Int, News>() {

    private val _source = MutableLiveData<NewsSpecialDataSource>()
    val source: LiveData<NewsSpecialDataSource> = _source

    override fun create() = NewsSpecialDataSource(newsRepository).also {
        _source.postValue(it)
    }
}

class NewsBaguaDataSourceFactory(private val newsRepository: NewsRepository) : DataSource.Factory<Int, News>() {

    private val _source = MutableLiveData<NewsBaguaDataSource>()
    val source: LiveData<NewsBaguaDataSource> = _source

    override fun create() = NewsBaguaDataSource(newsRepository).also {
        _source.postValue(it)
    }
}


class NewsGameDataSourceFactory(private val newsRepository: NewsRepository) : DataSource.Factory<Int, News>() {

    private val _source = MutableLiveData<NewsGameDataSource>()
    val source: LiveData<NewsGameDataSource> = _source

    override fun create() = NewsGameDataSource(newsRepository).also {
        _source.postValue(it)
    }
}