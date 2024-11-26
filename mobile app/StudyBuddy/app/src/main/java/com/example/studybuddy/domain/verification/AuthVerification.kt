package com.example.studybuddy.domain.verification

import android.text.TextUtils

/** Объект с глобальными функциями, проверяющими данные для авторизации и регистрации */
object AuthVerification {

    fun String.isEmailValid () : Boolean {
        return !TextUtils.isEmpty(this)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
                    .matches()
    }

    fun String.isNicknameValid () : Boolean {
        val regex = Regex("^[a-zA-Zа-яА-ЯёЁ\\s]+\$");
        //Никнейм может состоять только из симолов латиницы или кирилицы и пробелов
        return regex.matches(this)
    }

}