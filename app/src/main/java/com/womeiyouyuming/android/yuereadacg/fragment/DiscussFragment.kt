package com.womeiyouyuming.android.yuereadacg.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.womeiyouyuming.android.yuereadacg.R

/**
 * A simple [Fragment] subclass.
 */
class DiscussFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discuss, container, false)
    }

}
