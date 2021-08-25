package com.womeiyouyuming.android.yuereadacg.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import kotlinx.coroutines.*

/**
 * Created by Yue on 2020/8/18.
 */




//分区存储 利用MediaStore存储图片
//使用之前确保权限
//context ：   用于获取contentResolver
//bitmap:      图片源
//fileName：   图片名称，会查重
//showResult： 存储结果的sam函数，主线程

fun savePhotoWithBitmap(context: Context, bitmap: Bitmap, fileName: String, isPNG: Boolean, showResult: (result: String) -> Unit) {

    MainScope().launch {

        val externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val resolver = context.contentResolver

        //先进行图片查重
        val projection = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
        val selection = "${MediaStore.Images.Media.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(fileName)
        //如果图片很多是否会很慢？切到IO线程去查
        val hasPhoto = withContext(Dispatchers.IO) {
            resolver.query(externalUri, projection, selection, selectionArgs, null)?.use {
                it.count > 0
            }
        }


        if (hasPhoto == true) {
            showResult("图片已经存在！")
            return@launch
        }

        //API29以上，设置IS_PENDING状态为1，这样存储结束前，其他应用就不会处理这张图片
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, if (isPNG) "image/png" else "image/jpeg")

            val time = System.currentTimeMillis()
            put(MediaStore.Images.Media.DATE_ADDED, time / 1000)
            put(MediaStore.Images.Media.DATE_MODIFIED, time / 1000)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        //这里insertUri是nullable的，所以需要判空
        val insertUri = resolver.insert(externalUri, values) ?: let {
            showResult("出错了，请稍后再试")
            return@launch
        }

        //存储也放在IO线程，防止大图片耗时
        val result= withContext(Dispatchers.IO) {
            //use用在closeable对象，可以自动关闭它们
            resolver.openOutputStream(insertUri).use {

                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)) {

                    //api 29以上  IS_PENDING置0
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        values.clear()
                        values.put(MediaStore.Images.Media.IS_PENDING, 0)
                        resolver.update(insertUri, values, null, null)
                    }
                    "图片保存成功"

                } else {
                    "出错了，请稍后再试"
                }
            }
        }

        showResult(result)

    }

}