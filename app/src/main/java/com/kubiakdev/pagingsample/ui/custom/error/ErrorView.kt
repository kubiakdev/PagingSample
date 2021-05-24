package com.kubiakdev.pagingsample.ui.custom.error

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.withStyledAttributes
import com.kubiakdev.pagingsample.R
import com.kubiakdev.pagingsample.databinding.ViewErrorBinding
import com.kubiakdev.pagingsample.util.extension.hide
import com.kubiakdev.pagingsample.util.extension.show

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    private val binding = ViewErrorBinding.inflate(LayoutInflater.from(context), this, true)

    @DrawableRes
    private var errorImageRes: Int = R.drawable.ic_wifi_off

    @StringRes
    private var messageTextRes: Int = R.string.error_view_default_error_message

    @StringRes
    private var retryButtonTextRes: Int = R.string.error_view_default_retry_button_text

    init {
        obtainAttributes(attrs)
        updateViewComponents()
    }

    private fun obtainAttributes(attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.ErrorView) {
            errorImageRes =
                getResourceId(R.styleable.ErrorView_errorView_errorImage, errorImageRes)
            messageTextRes =
                getResourceId(R.styleable.ErrorView_errorView_messageText, messageTextRes)
            retryButtonTextRes =
                getResourceId(R.styleable.ErrorView_errorView_retryButtonText, retryButtonTextRes)
        }
    }

    private fun updateViewComponents() {
        with(binding) {
            errorImage.setImageResource(errorImageRes)
            errorMessageTextView.setText(messageTextRes)
            retryButton.setText(retryButtonTextRes)
        }
    }

    fun showOrHide(show: Boolean) {
        if (show) {
            updateViewComponents()
            show()
        } else {
            hide()
        }
    }

    fun setOnRetryButtonClick(action: () -> Unit) {
        binding.retryButton.setOnClickListener { action() }
    }

    fun setErrorImageRes(@DrawableRes imageRes: Int) {
        errorImageRes = imageRes
    }

    fun setMessageTextRes(@StringRes stringRes: Int) {
        messageTextRes = stringRes
    }

    fun setRetryButtonTextRes(@StringRes stringRes: Int) {
        retryButtonTextRes = stringRes
    }
}