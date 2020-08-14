package com.womeiyouyuming.android.yuereadacg.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.womeiyouyuming.android.yuereadacg.R

/**
 * Created by Yue on 2020/8/12.
 */
class TestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.test, container, false)
    }
}