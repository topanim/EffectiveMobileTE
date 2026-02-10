package app.what.features.auth.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class LoginViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    private val emailRegex = Regex("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")

    val isLoginButtonEnabled: StateFlow<Boolean> = combine(_email, _password) { email, pass ->
        val isEmailValid = emailRegex.matches(email)
        val isPasswordValid = pass.isNotBlank()
        isEmailValid && isPasswordValid
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun onEmailChanged(text: String) { _email.value = text }
    fun onPasswordChanged(text: String) { _password.value = text }
}