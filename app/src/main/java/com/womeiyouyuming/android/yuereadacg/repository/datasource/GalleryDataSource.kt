package com.womeiyouyuming.android.yuereadacg.repository.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.womeiyouyuming.android.yuereadacg.model.Photo
import com.womeiyouyuming.android.yuereadacg.repository.GalleryRepository
import com.womeiyouyuming.android.yuereadacg.util.parseAmlyu

/**
 * Created by Yue on 2020/8/14.
 */
class GalleryDataSource(private val galleryRepository: GalleryRepository) :
    BaseDataSource<Photo>() {
    override val maxPage = 40

    override suspend fun loadData(page: Int) =
        parseAmlyu(galleryRepository.getPhotos(page).string())

}

class GalleryDataSourceFactory(private val galleryRepository: GalleryRepository) : DataSource.Factory<Int, Photo>() {

    private val _source = MutableLiveData<GalleryDataSource>()
    val source: LiveData<GalleryDataSource> = _source

    override fun create() = GalleryDataSource(galleryRepository).also {
        _source.postValue(it)
    }


}
