package com.womeiyouyuming.android.yuereadacg.fragment

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import com.womeiyouyuming.android.yuereadacg.R
import com.womeiyouyuming.android.yuereadacg.util.CONTENT
import com.womeiyouyuming.android.yuereadacg.util.GlideImageGetter
import kotlinx.android.synthetic.main.fragment_discuss.*

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val start =
            "<html><head></head><body>"
        val end = "</body></html>"

        val body = CONTENT

        val html = start + body + end

        val s = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT, GlideImageGetter(textView), Html.TagHandler { opening, tag, output, xmlReader ->  })
        } else {
            Html.fromHtml(html, GlideImageGetter(textView), Html.TagHandler { opening, tag, output, xmlReader ->  })
        }

        textView.text = s


    }

}
