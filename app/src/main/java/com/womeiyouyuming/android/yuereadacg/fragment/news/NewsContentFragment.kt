package com.womeiyouyuming.android.yuereadacg.fragment.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.webkit.WebView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController

import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.util.formatUrl
import com.womeiyouyuming.android.yuereadacg.viewmodel.NewsContentViewModel
import kotlinx.android.synthetic.main.fragment_news_content.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * A simple [Fragment] subclass.
 *
 * 资讯的详细内容页
 *
 */

class NewsContentFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolBar.title = resources.getString(R.string.lab_news_content)

        toolBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


        val url = requireArguments().getString("url")
        url?.let {
            initWebView(url)
        }



    }



    private fun initWebView(url: String) {

        val formattedUrl = formatUrl(url)

        val author = requireArguments().getString("author")

        val newsContentViewModel by viewModels<NewsContentViewModel> {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>) =
                    modelClass.getConstructor(String::class.java).newInstance(formattedUrl)
            }
        }



        val innerUrl = "file:///android_asset/"

        newsContentViewModel.newsContentLiveData.observe(viewLifecycleOwner) {
            val htmlText = it.replace("<span class=\"author\">178动漫原创</span>", "<span class=\"author\">${author}</span>")
            newsContentWebView.loadDataWithBaseURL(innerUrl, htmlText, "text/html", "UTF-8", null)
            progressBar.visibility = View.GONE
        }

        newsContentWebView.setOnLongClickListener {
            val result = newsContentWebView.hitTestResult ?: return@setOnLongClickListener false
            if (result.type == WebView.HitTestResult.IMAGE_TYPE) {
                val extra = result.extra ?: return@setOnLongClickListener false
                Log.d("YUEDEVTAG", extra)
                val bundle = bundleOf("imgUrl" to extra)
                findNavController().navigate(R.id.action_nav_news_content_to_nav_photo, bundle)
                return@setOnLongClickListener true
            } else return@setOnLongClickListener false
        }

    }

}
