package com.notchdev.chessdsa.ui.About

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.notchdev.chessdsa.R
import com.notchdev.chessdsa.databinding.DialogAboutBinding

class AboutDialog : DialogFragment() {

    private var _binding: DialogAboutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAboutBinding.inflate(inflater, container, false)
        setSpannableString()
        return binding.root
    }


    private fun setSpannableString() {
        val projectSpan = SpannableString(getString(R.string.git_link))
        val userSpan = SpannableString(getString(R.string.git_user))
        val projectClickSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://github.com/ksog66/ChessDsa")
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = ds.linkColor

            }
        }
        val userClickSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://github.com/ksog66")
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText  = false
                ds.color = ds.linkColor
            }
        }
        userSpan.setSpan(userClickSpan, userSpan.length - 12,userSpan.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        projectSpan.setSpan(projectClickSpan, projectSpan.length - 10, projectSpan.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.gitLinkTv.apply {
            movementMethod = LinkMovementMethod.getInstance()
            text = projectSpan
        }
        binding.nameTv.apply {
            movementMethod = LinkMovementMethod.getInstance()
            text = userSpan
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}