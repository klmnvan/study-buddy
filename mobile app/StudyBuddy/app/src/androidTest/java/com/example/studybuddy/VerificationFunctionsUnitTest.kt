package com.example.studybuddy

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studybuddy.view.verification.AuthVerification.isEmailValid
import com.example.studybuddy.view.verification.AuthVerification.isNicknameValid
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

//Способ именования методов: [Тестируемый метод]_[Сценарий]_[Ожидаемое поведение].
@RunWith(AndroidJUnit4::class)
class VerificationFunctionsUnitTest {

    @Test
    fun isEmailValid_CheckCorrectTestEmail_ReturnedTrue() {
        // Arrange
        val inputCorrectEmail = "user@mail.ru" //почта, подходящая под маску
        val expectedResultAfterCheckEmail = true //ожидаемый результат: true - строка соответствует маске

        //Act
        val actualResultAfterCheck = inputCorrectEmail.isEmailValid() //функция проверки почты на соответствие маске

        //Assert
        Assert.assertEquals(
            expectedResultAfterCheckEmail,
            actualResultAfterCheck
        ) //проверка равенства ожидаемого и получаемого результата
    }

    @Test
    fun isEmailValid_CheckUncorrectTestEmail_ReturnedFalse() {
        // Arrange
        val inputCorrectEmail = "usermail.ru" //почта, не подходящая под маску
        val expectedResultAfterCheckEmail = false //ожидаемый результат: false - строка соответствует маске

        //Act
        val actualResultAfterCheck = inputCorrectEmail.isEmailValid() //функция проверки почты на соответствие маске

        //Assert
        Assert.assertEquals(
            expectedResultAfterCheckEmail,
            actualResultAfterCheck
        ) //проверка равенства ожидаемого и получаемого результата
    }

    @Test
    fun isNicknameValid_CheckCorrectNickname_ReturnedTrue() {
        // Arrange
        val inputCorrectNickname = "NiCkНеЙм" //никнейм, содержащий только символы латиницы и кириллицы верхнего и нижнего регистра
        val expectedResultAfterCheckNickname = true //ожидаемый результат: true - строка соответствует маске

        //Act
        val actualResultAfterCheck = inputCorrectNickname.isNicknameValid() //функция проверки никнейма на соответствие маске

        //Assert
        Assert.assertEquals(
            expectedResultAfterCheckNickname,
            actualResultAfterCheck
        ) //проверка равенства ожидаемого и получаемого результата
    }

    @Test
    fun isNicknameValid_CheckUncorrectNickname_ReturnedFalse() {
        // Arrange
        val inputCorrectNickname = "_(NiCkНеЙм)1" //никнейм, содержащий не только символы латиницы и кириллицы верхнего и нижнего регистра
        val expectedResultAfterCheckNickname = false //ожидаемый результат: false - строка не соответствует маске

        //Act
        val actualResultAfterCheck = inputCorrectNickname.isNicknameValid() //функция проверки никнейма на соответствие маске

        //Assert
        Assert.assertEquals(
            expectedResultAfterCheckNickname,
            actualResultAfterCheck
        ) //проверка равенства ожидаемого и получаемого результата
    }
}