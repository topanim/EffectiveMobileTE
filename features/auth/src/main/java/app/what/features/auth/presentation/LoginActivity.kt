package app.what.features.auth.presentation

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import app.what.features.auth.databinding.ActivityLoginBinding
import app.what.features.auth.domain.LoginViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.what.core.R
import app.what.features.main.presentation.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupInputs()
        setupListeners()
        observeState()
        setupTextButtons()
    }

    private fun setupTextButtons() {
        val fullText1 = "Забыл пароль"
        val spannable1 = SpannableString(fullText1)
        spannable1.setSpan(
            ForegroundColorSpan( ContextCompat.getColor(this, R.color.Green)),
            0,
            fullText1.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val clickableSpan1 = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@LoginActivity, fullText1, Toast.LENGTH_LONG).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.isFakeBoldText = true
            }
        }

        val fullText2 = "Нету аккаунта? Регистрация"
        val spannable2 = SpannableString(fullText2)

        val start = fullText2.indexOf("Регистрация")
        val end = start + "Регистрация".length

        spannable2.setSpan(
            ForegroundColorSpan( ContextCompat.getColor(this, R.color.Green)),
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val clickableSpan2 = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@LoginActivity, "Регистрация", Toast.LENGTH_LONG).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.isFakeBoldText = true
            }
        }

        spannable1.setSpan(clickableSpan1, 0, fullText1.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable2.setSpan(clickableSpan2, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvForgotPassword.text = spannable1
        binding.tvRegister.text = spannable2

        binding.tvForgotPassword.movementMethod = LinkMovementMethod.getInstance()
        binding.tvForgotPassword.highlightColor = Color.TRANSPARENT

        binding.tvRegister.movementMethod = LinkMovementMethod.getInstance()
        binding.tvRegister.highlightColor = Color.TRANSPARENT
    }

    private fun setupInputs() {
        val emailFilter = InputFilter { source, start, end, _, _, _ ->
            for (i in start until end) {
                val char = source[i]
                val isAllowed = (char in 'a'..'z') ||
                        (char in 'A'..'Z') ||
                        (char in '0'..'9') ||
                        "@._-".contains(char)

                if (!isAllowed) {
                    return@InputFilter ""
                }
            }
            null
        }

        binding.etEmail.filters = arrayOf(emailFilter)
    }

    private fun setupListeners() {
        binding.etEmail.doAfterTextChanged { viewModel.onEmailChanged(it.toString()) }
        binding.etPassword.doAfterTextChanged { viewModel.onPasswordChanged(it.toString()) }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnVk.setOnClickListener { openUrl("https://vk.com/") }
        binding.btnOk.setOnClickListener { openUrl("https://ok.ru/") }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.isLoginButtonEnabled.collect { isEnabled ->
                binding.btnLogin.isEnabled = isEnabled
                binding.btnLogin.alpha = if (isEnabled) 1.0f else 0.5f
            }
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }
}