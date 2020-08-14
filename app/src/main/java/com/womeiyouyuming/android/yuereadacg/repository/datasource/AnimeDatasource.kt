package com.womeiyouyuming.android.yuereadacg.repository.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.womeiyouyuming.android.yuereadacg.model.Anime
import com.womeiyouyuming.android.yuereadacg.repository.AnimeRepository

/**
 * Created by Yue on 2020/8/6.
 */
class AnimeDataSource(private val animeRepository: AnimeRepository) : BaseDataSource<Anime>() {

    override val maxPage = 6

    override suspend fun loadData(page: Int) = animeRepository.getAnimeList(page)

}

class AnimeDataSourceFactory(private val animeRepository: AnimeRepository) : DataSource.Factory<Int, Anime>() {

    private val _source = MutableLiveData<AnimeDataSource>()
    val source: LiveData<AnimeDataSource> = _source

    override fun create() = AnimeDataSource(animeRepository).also {
        _source.postValue(it)
    }


}