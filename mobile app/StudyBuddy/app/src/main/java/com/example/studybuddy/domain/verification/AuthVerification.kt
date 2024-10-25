package com.example.studybuddy.domain.verification

import android.text.TextUtils

/** Объект с глобальными функциями, проверяющими данные для авторизации и регистрации */
object AuthVerification {

    fun String.isEmailValid () : Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    fun String.isPasswordValid () : Boolean {
        return !TextUtils.isEmpty(this) && this.length >= 8
    }

}