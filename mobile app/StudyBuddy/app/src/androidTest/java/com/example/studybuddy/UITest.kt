package com.example.studybuddy

import android.content.Context
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.studybuddy.domain.network.ApiServiceImpl
import com.example.studybuddy.domain.network.ApiServiceProvider
import com.example.studybuddy.domain.repository.UserRepository
import com.example.studybuddy.domain.room.database.StudyBuddyDatabase
import com.example.studybuddy.view.MyApp
import com.example.studybuddy.view.navigation.Navigation
import com.example.studybuddy.view.navigation.NavigationRoutes
import com.example.studybuddy.view.screens.auth.Auth
import com.example.studybuddy.view.screens.disciplines.Disciplines
import com.example.studybuddy.view.screens.exams.Exams
import com.example.studybuddy.view.screens.login.Login
import com.example.studybuddy.view.screens.login.LoginViewModel
import com.example.studybuddy.view.screens.register.Register
import com.example.studybuddy.view.screens.register.RegisterViewModel
import com.example.studybuddy.view.screens.schedule.Schedule
import com.example.studybuddy.view.screens.splash.Splash
import com.example.studybuddy.view.screens.tasks.Tasks
import com.example.studybuddy.view.screens.tasks.TasksViewModel
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//AndroidJUnit4 – Это аннотация, которая указывает, что тесты в данном классе должны выполняться с использованием AndroidJUnit4 тестового раннера.
@RunWith(AndroidJUnit4::class)
class UITest {

    val client = HttpClient(Android) {
        expectSuccess = true
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(
                Json {
                    encodeDefaults = false
                    ignoreUnknownKeys = true
                    isLenient = true
                    useAlternativeNames = false
                })
            register(
                ContentType.Text.Html, KotlinxSerializationConverter(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            )
        }
    }

    lateinit var service: ApiServiceImpl
    lateinit var database: StudyBuddyDatabase
    lateinit var context: Context
    lateinit var loginViewModel: LoginViewModel
    lateinit var registViewModel: RegisterViewModel

    @get:Rule
    val composeTestRule =
        createComposeRule() //это функция, которая создает правило для тестирования Jetpack Compose UI компонентов.

    @Test
    fun UseAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.studybuddy", appContext.packageName)
    }

    @Test
    fun Auth_AuthScreenLaunch_IsDisplayed() {
        composeTestRule.setContent {
            Auth(rememberNavController())
        }
        composeTestRule.onNodeWithText("Умное планирование учёбы").assertIsDisplayed()
        composeTestRule.onNodeWithText("Здесь ты сможешь узнавать расписание, готовится к экзаменам, отслеживать дедлайны и добавлять информацию о дисциплинах")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("ВОЙТИ").assertIsDisplayed()
        composeTestRule.onNodeWithText("СОЗДАТЬ ПРОФИЛЬ").assertIsDisplayed()
    }

    @Test
    fun Login_TryLoginEmptyTextFields_ShowError() {
        composeTestRule.setContent {
            context = LocalContext.current
            database = Room.databaseBuilder(
                context,
                StudyBuddyDatabase::class.java,
                "tasks"
            ).allowMainThreadQueries().build()
            service = ApiServiceImpl(client, database)
            loginViewModel = LoginViewModel(service, context)
            TestNavigation(NavigationRoutes.LOGIN)
        }
        composeTestRule.onNodeWithText("ВОЙТИ").performClick()
        composeTestRule.onNodeWithText("Не все поля заполнены").isDisplayed()
    }

    @Test
    fun Login_TryLoginWithNotPatternEmail_ShowError() {
        composeTestRule.setContent {
            context = LocalContext.current
            database = Room.databaseBuilder(
                context,
                StudyBuddyDatabase::class.java,
                "tasks"
            ).allowMainThreadQueries().build()
            service = ApiServiceImpl(client, database)
            loginViewModel = LoginViewModel(service, context)
            loginViewModel.stateValue =
                loginViewModel.stateValue.copy(email = "erwer", password = "12345678")
            TestNavigation(NavigationRoutes.LOGIN)
        }
        composeTestRule.onNodeWithText("ВОЙТИ").performClick()
        composeTestRule.onNodeWithText("Неверный формат почты").isDisplayed()
    }

    @Test
    fun Regist_TryRegistUncorrectNickname_ShowError() {
        composeTestRule.setContent {
            context = LocalContext.current
            database = Room.databaseBuilder(
                context,
                StudyBuddyDatabase::class.java,
                "tasks"
            ).allowMainThreadQueries().build()
            service = ApiServiceImpl(client, database)
            registViewModel = RegisterViewModel(service, context)
            registViewModel.stateValue = registViewModel.stateValue.copy(
                email = "user@email.com", password = "12345678", confirmPassword = "12345678",
                nickname = "NiCk0Name1"
            )
            TestNavigation(NavigationRoutes.REGIST)
        }
        composeTestRule.onNodeWithText("СОЗДАТЬ ПРОФИЛЬ").performClick()
        composeTestRule.onNodeWithText("В никнейме недопустимые символы").isDisplayed()
    }

    @Test
    fun Regist_TryRegistPasswordDontMatch_ShowError() {
        composeTestRule.setContent {
            context = LocalContext.current
            database = Room.databaseBuilder(
                context,
                StudyBuddyDatabase::class.java,
                "tasks"
            ).allowMainThreadQueries().build()
            service = ApiServiceImpl(client, database)
            registViewModel = RegisterViewModel(service, context)
            registViewModel.stateValue = registViewModel.stateValue.copy(
                email = "user@email.com", password = "12345678", confirmPassword = "1345678",
                nickname = "NiCkname"
            )
            TestNavigation(NavigationRoutes.REGIST)
        }
        composeTestRule.onNodeWithText("СОЗДАТЬ ПРОФИЛЬ").performClick()
        composeTestRule.onNodeWithText("Пароли не совпадают").isDisplayed()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TestNavigation(start: String) {
        UserRepository.init(context)
        val tasksViewModel = TasksViewModel(service, context, database)
        val barsIsVisible = remember { mutableStateOf(false) }
        val pullToRefreshState = rememberPullToRefreshState()
        val controller = rememberNavController()
        NavHost(
            navController = controller,
            startDestination = start,
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 0)) }) {

            composable(NavigationRoutes.AUTH) {
                barsIsVisible.value = false
                Auth(controller)
            }

            composable(NavigationRoutes.LOGIN) {
                Login(controller, loginViewModel)
            }

            composable(NavigationRoutes.REGIST) {
                Register(controller, registViewModel)
            }

            composable(NavigationRoutes.TASKS) {
                Tasks(controller, pullToRefreshState, tasksViewModel)
                barsIsVisible.value = true
            }

        }
    }

}
