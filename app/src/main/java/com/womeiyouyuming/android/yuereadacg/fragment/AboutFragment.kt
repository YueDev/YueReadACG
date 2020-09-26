package com.womeiyouyuming.android.yuereadacg.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.womeiyouyuming.android.yuereadacg.R
import kotlinx.android.synthetic.main.fragment_about.*
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

        readmeTextView.setOnClickListener {
            mailToMe()
        }

        emailTextView.setOnClickListener {
            mailToMe()
        }
    }


    private fun mailToMe() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("yuedev.cn@gmail.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "悦读ACG的反馈")
        startActivity(Intent.createChooser(intent, "Mail to Yue Read ACG..."))
    }


}

