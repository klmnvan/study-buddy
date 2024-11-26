package com.example.studybuddy

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studybuddy.view.MainActivity
import com.example.studybuddy.view.screens.auth.Auth
import com.example.studybuddy.view.screens.login.Login
import com.example.studybuddy.view.screens.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

//AndroidJUnit4 – Это аннотация, которая указывает, что тесты в данном классе должны выполняться с использованием AndroidJUnit4 тестового раннера.
@HiltAndroidTest
@CustomTestApplication(MainActivity::class)
@RunWith(AndroidJUnit4::class)
class UITest {

    @get:Rule
    val composeTestRule = createComposeRule() //это функция, которая создает правило для тестирования Jetpack Compose UI компонентов.

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun AuthScreenIsDisplayed() {
        composeTestRule.setContent {
            Auth(rememberNavController())
        }
        composeTestRule.onNodeWithText("Умное планирование учёбы").assertIsDisplayed()
        composeTestRule.onNodeWithText("Здесь ты сможешь узнавать расписание, готовится к экзаменам, отслеживать дедлайны и добавлять информацию о дисциплинах").assertIsDisplayed()
        composeTestRule.onNodeWithText("ВОЙТИ").assertIsDisplayed()
        composeTestRule.onNodeWithText("СОЗДАТЬ ПРОФИЛЬ").assertIsDisplayed()
    }

    @Test
    fun testMyComponent() {
        composeTestRule.setContent {
            loginViewModel = hiltViewModel()
            Login(rememberNavController())
        }
        composeTestRule.onNodeWithText("Авторизация").assertIsDisplayed()
    }

}

/*
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest1 {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.studybuddy", appContext.packageName)
    }
}*/
