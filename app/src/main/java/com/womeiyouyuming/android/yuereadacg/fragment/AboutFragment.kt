package com.womeiyouyuming.android.yuereadacg.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.womeiyouyuming.android.yuereadacg.R
import kotlinx.android.synthetic.main.toolbar.*

/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBar.title = resources.getString(R.string.lab_about)

        toolBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
