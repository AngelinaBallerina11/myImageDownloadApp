package com.angelinaandronova.myimagedownloadapplication.ui.main

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.angelinaandronova.myimagedownloadapplication.MyApplication
import com.angelinaandronova.myimagedownloadapplication.R
import com.angelinaandronova.myimagedownloadapplication.di.modules.NetworkModule
import com.angelinaandronova.myimagedownloadapplication.di.modules.ViewModelFactory
import kotlinx.android.synthetic.main.main_fragment.*
import java.security.MessageDigest
import javax.inject.Inject


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.component.inject(this)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button.setOnClickListener {
            password.error = null
            username.error = null

            if (TextUtils.isEmpty(username.text)) {
                username.error = activity!!.resources.getString(R.string.please_fill_in_username)
                username.requestFocus()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password.text)) {
                password.error = activity!!.resources.getString(R.string.please_fill_in_password)
                password.requestFocus()
                return@setOnClickListener
            }

            saveUserPasswordToLocalStorage()

            viewModel.downloadImage(username.text.toString().trim().toLowerCase())
            progress_bar.visibility = View.VISIBLE
        }

        viewModel.image.observe(this, android.arch.lifecycle.Observer { imageDownloadResult ->
            imageDownloadResult?.let {
                progress_bar.visibility = View.GONE
                when (it) {
                    is DownloadSuccessful -> displayImage(it)
                    is DownloadFailed -> showError(it)
                }
            }
        })
    }

    private fun saveUserPasswordToLocalStorage() {
        sharedPreferences.edit().putString(NetworkModule.TOKEN, password.text.toString().toSha1()).apply()
    }

    private fun showError(it: DownloadFailed) {
        showToast(
            when (it.throwable) {
                is General -> R.string.download_failed
                is Unauthorized -> R.string.error_401
                is Forbidden -> R.string.error_403
                is NotFound -> R.string.error_404
                is InternalServerError -> R.string.server_error
                else -> R.string.download_failed
            }
        )
    }

    private fun showToast(messageResource: Int) {
        Toast.makeText(activity, activity!!.resources.getString(messageResource), Toast.LENGTH_SHORT).show()
    }

    private fun displayImage(imageDownloadResult: DownloadSuccessful) {
        imageView.setImageBitmap(imageDownloadResult.base64EncodedString.decode())
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity!!.currentFocus
        if (view == null) view = View(activity)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

private fun String.decode(): Bitmap {
    val decodedString: ByteArray = android.util.Base64.decode(this, android.util.Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}

private fun String.toSha1(): String? {
    val HEX_CHARS = "0123456789ABCDEF"
    val bytes = MessageDigest
        .getInstance("SHA-1")
        .digest(this.toByteArray())
    val result = StringBuilder(bytes.size * 2)

    bytes.forEach {
        val i = it.toInt()
        result.append(HEX_CHARS[i shr 4 and 0x0f])
        result.append(HEX_CHARS[i and 0x0f])
    }
    return result.toString()
}



