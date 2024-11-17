package com.example.studybuddy.domain.network

import android.util.Log
import com.example.studybuddy.data.dto.CreateDiscDto
import com.example.studybuddy.data.dto.CreateExamDto
import com.example.studybuddy.data.dto.CreateNoteDto
import com.example.studybuddy.data.dto.CreateReqDto
import com.example.studybuddy.data.dto.CreateTaskDto
import com.example.studybuddy.data.dto.CreateTeacherDto
import com.example.studybuddy.data.dto.LoginDto
import com.example.studybuddy.data.dto.RegisterDto
import com.example.studybuddy.data.dto.UserDto
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.ExamEnt
import com.example.studybuddy.data.entityes.NoteEnt
import com.example.studybuddy.data.entityes.RequirementEnt
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.data.entityes.TeacherEnt
import com.example.studybuddy.data.modelsitreshalo.Values
import com.example.studybuddy.data.modelsitreshalo.ValuesSchedule
import com.example.studybuddy.data.responses.DefaultResp
import com.example.studybuddy.data.responses.ExamsResp
import com.example.studybuddy.data.responses.TasksResp
import com.example.studybuddy.data.responses.AuthResp
import com.example.studybuddy.data.responses.DisciplinesResp
import com.example.studybuddy.data.responses.ScheduleResp
import com.example.studybuddy.domain.room.database.StudyBuddyDatabase
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

/** Реализация интерфейса, в котором описаны все методы для запросов к API + их кэшировние в локальную базу данных Room*/
class ApiServiceImpl(
    private val client: HttpClient,
    private val database: StudyBuddyDatabase,
    ): ApiService {

    override suspend fun signIn(email: String, password: String): AuthResp {
        return try {
            val response = client.post {
                url(HttpRoutes.LOGIN)
                contentType(ContentType.Application.Json)
                setBody(LoginDto(email, password))
            }
            val responseBody = response.body<UserDto>()
            AuthResp(user = responseBody)
        }
        catch (e: ClientRequestException) {
            //приходит text/plain иногда, поэтому проверять надо всегда
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return AuthResp(error = errorMessage)
            }
            AuthResp(error = e.response.body<String>())
        }
        catch (e: ServerResponseException) {
            AuthResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            AuthResp(error = e.message.toString())
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        passwordConf: String,
        nickname: String
    ): AuthResp {
        return try {
            val response = client.post {
                url(HttpRoutes.REGISTER)
                contentType(ContentType.Application.Json)
                setBody(RegisterDto(nickname,email, password, passwordConf))
            }
            val responseBody = response.body<UserDto>()
            AuthResp(user = responseBody)
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return AuthResp(error = errorMessage)
            }
            AuthResp(error = e.response.body<String>())
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            AuthResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            AuthResp(error = e.message.toString())
        }
    }

    override suspend fun getTasks(token: String): TasksResp {
        return try {
            val tasks = client.get {
                url(HttpRoutes.GET_TASKS)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            val tasksBody = tasks.body<MutableList<TaskEnt>>()
            database.taskDao.deleteAllTask()
            database.taskDao.insertTask(tasksBody)
            val disciplines = client.get {
                url(HttpRoutes.GET_DISCIPLINES)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            val disciplinesBody = disciplines.body<MutableList<DisciplineEnt>>()
            database.disciplineDao.deleteAllDisc()
            database.disciplineDao.insertDisc(disciplinesBody)
            TasksResp(listTask = tasksBody, listDisc = disciplinesBody)
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return TasksResp(error = errorMessage)
            }
            TasksResp(error = e.response.body<String>())
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            TasksResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            TasksResp(error = e.message.toString())
        }
    }

    override suspend fun getExams(token: String): ExamsResp {
        return try {
            val exams = client.get {
                url(HttpRoutes.GET_EXAMS)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            val bodyExams = exams.body<List<ExamEnt>>()
            database.examDao.deleteAllExams()
            database.examDao.insertExam(bodyExams)
            val notes = client.get {
                url(HttpRoutes.GET_NOTES)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            val bodyNotes = notes.body<List<NoteEnt>>()
            database.noteDao.deleteAllNote()
            database.noteDao.insertNote(bodyNotes)
            ExamsResp(listExams = bodyExams, listNotes = bodyNotes)
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return ExamsResp(error = errorMessage)
            }
            ExamsResp(error = e.response.body<String>())
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            ExamsResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            ExamsResp(error = e.message.toString())
        }
    }

    override suspend fun getTeachers(token: String): DisciplinesResp {
        return try {
            val exams = client.get {
                url(HttpRoutes.GET_TEACHERS)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            val body = exams.body<List<TeacherEnt>>()
            database.teacherDao.deleteAllTeacher()
            database.teacherDao.insertTeacher(body)
            val disciplines = client.get {
                url(HttpRoutes.GET_DISCIPLINES)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            val disciplinesBody = disciplines.body<List<DisciplineEnt>>()
            database.disciplineDao.deleteAllDisc()
            database.disciplineDao.insertDisc(disciplinesBody)
            val requirements = client.get {
                url(HttpRoutes.GET_REQUIREMENTS)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            val requirementsBody = requirements.body<List<RequirementEnt>>()
            database.requirementDao.deleteAllReq()
            database.requirementDao.insertReq(requirementsBody)
            DisciplinesResp(listTeachers = body, listDiscipline = disciplinesBody, listRequirements = requirementsBody)
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DisciplinesResp(error = errorMessage)
            }
            DisciplinesResp(error = e.response.body<String>())
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DisciplinesResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DisciplinesResp(error = e.message.toString())
        }
    }

    override suspend fun updateTask(token: String, task: TaskEnt): DefaultResp {
        return try {
            val response = client.put {
                url(HttpRoutes.UPDATE_TASK)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
                setBody(task)
            }
            if(response.status.isSuccess()) {
                database.taskDao.updateTask(task)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun updateReq(token: String, req: RequirementEnt): DefaultResp {
        return try {
            val response = client.put {
                url(HttpRoutes.UPDATE_REQUIREMENT)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
                setBody(req)
            }
            if(response.status.isSuccess()) {
                database.requirementDao.updateReq(req)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun updateTeacher(token: String, teacher: TeacherEnt): DefaultResp {
        return try {
            val response = client.put {
                url(HttpRoutes.UPDATE_TEACHER)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
                setBody(teacher)
            }
            if(response.status.isSuccess()) {
                database.teacherDao.updateTeacher(teacher)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                if(errorMessage == "Имя преподавателя может состоять только из симолов латиницы или кирилицы и пробелов"){
                    return DefaultResp(error = "Имя преподавателя может состоять только из симолов кирилицы и пробелов")
                }
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun updateDiscipline(token: String, disc: DisciplineEnt): DefaultResp {
        return try {
            val response = client.put {
                url(HttpRoutes.UPDATE_DISCIPLINE)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
                setBody(disc)
            }
            if(response.status.isSuccess()) {
                database.disciplineDao.updateDisc(disc)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun updateNote(token: String, note: NoteEnt): DefaultResp {
        return try {
            val response = client.put {
                url(HttpRoutes.UPDATE_NOTE)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
                setBody(note)
            }
            if(response.status.isSuccess()) {
                database.noteDao.updateNote(note)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun updateExam(token: String, exam: ExamEnt): DefaultResp {
        return try {
            val response = client.put {
                url(HttpRoutes.UPDATE_EXAM)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
                setBody(exam)
            }
            if(response.status.isSuccess()) {
                database.examDao.updateExam(exam)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun createTeacher(token: String, teacher: CreateTeacherDto): DisciplinesResp {
        return try {
            val response = client.post {
                url(HttpRoutes.CREATE_TEACHER)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
                setBody(teacher)
            }
            val body = response.body<TeacherEnt>()
            if(response.status.isSuccess()) {
                database.teacherDao.insertTeacher(body)
            }
            DisciplinesResp(teacher = body)
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DisciplinesResp(error = errorMessage)
            }
            DisciplinesResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DisciplinesResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DisciplinesResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun deleteTask(token: String, task: TaskEnt): DefaultResp {
        return try {
            val response = client.delete {
                url(HttpRoutes.DELETE_TASK + "?Id задачи=${task.idTask}")
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            if(response.status.isSuccess()) {
                database.taskDao.deleteTask(task)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun deleteTeacher(token: String, teacher: TeacherEnt): DefaultResp {
        return try {
            val response = client.delete {
                url(HttpRoutes.DELETE_TEACHER + "?Id преподавателя=${teacher.idTeacher}")
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            if(response.status.isSuccess()) {
                database.teacherDao.deleteTeacher(teacher)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun deleteDisc(token: String, disc: DisciplineEnt): DefaultResp {
        return try {
            val response = client.delete {
                url(HttpRoutes.DELETE_DISCIPLINE + "?Id предмета=${disc.idDiscipline}")
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            if(response.status.isSuccess()) {
                database.disciplineDao.deleteDisc(disc)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun deleteReq(token: String, req: RequirementEnt): DefaultResp {
        return try {
            val response = client.delete {
                url(HttpRoutes.DELETE_REQUIREMENT + "?Id требования=${req.idRequirement}")
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            if(response.status.isSuccess()) {
                database.requirementDao.deleteReq(req)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun deleteNote(token: String, note: NoteEnt): DefaultResp {
        return try {
            val response = client.delete {
                url(HttpRoutes.DELETE_NOTE + "?Id заметки=${note.idNote}")
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            if(response.status.isSuccess()) {
                database.noteDao.deleteNote(note)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun deleteExam(token: String, exam: ExamEnt): DefaultResp {
        return try {
            val response = client.delete {
                url(HttpRoutes.DELETE_EXAM + "?Id экзамена=${exam.idExam}")
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            if(response.status.isSuccess()) {
                database.examDao.deleteExam(exam)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun getValues(): ScheduleResp {
        return try {
            val values = client.get {
                url(HttpRoutes.GET_VALUES)
                contentType(ContentType.Text.Html)
            }
            val bodyValues = values.body<Values>()
            ScheduleResp(values = bodyValues)
        }
        catch (e: ClientRequestException) {
            Log.d("Error ${e.response.status}", e.message)
            ScheduleResp(error = e.response.body<String>())
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            ScheduleResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            ScheduleResp(error = e.message.toString())
        }
    }

    override suspend fun getSchedule(): ScheduleResp {
        return try {
            val values = client.get {
                url("https://api.it-reshalo.ru/schedule?filter_id=48&date=1731877200&regarding=group")
                contentType(ContentType.Text.Html)
            }
            val bodyValues = values.body<ValuesSchedule>()
            ScheduleResp(valuesSchedule = bodyValues)
        }
        catch (e: ClientRequestException) {
            Log.d("Error ${e.response.status}", e.message)
            ScheduleResp(error = e.response.body<String>())
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            ScheduleResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            ScheduleResp(error = e.message.toString())
        }
    }

    override suspend fun createTask(token: String, task: CreateTaskDto): TasksResp {
        return try {
            val response = client.post {
                url(HttpRoutes.CREATE_TASK)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
                setBody(task)
            }
            val body = response.body<TaskEnt>()
            if(response.status.isSuccess()) {
                database.taskDao.insertTask(body)
            }
            TasksResp(task = body)
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return TasksResp(error = errorMessage)
            }
            TasksResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            TasksResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            TasksResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun createDisc(token: String, disc: CreateDiscDto): DisciplinesResp {
        return try {
            val response = client.post {
                url(HttpRoutes.CREATE_DISC)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
                setBody(disc)
            }
            val body = response.body<DisciplineEnt>()
            if(response.status.isSuccess()) {
                database.disciplineDao.insertDisc(body)
            }
            DisciplinesResp(disc = body)
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DisciplinesResp(error = errorMessage)
            }
            DisciplinesResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DisciplinesResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DisciplinesResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun createRequirement(token: String, req: CreateReqDto): DefaultResp {
        return try {
            val response = client.post {
                url(HttpRoutes.CREATE_REQUIREMENT)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
                setBody(req)
            }
            val body = response.body<RequirementEnt>()
            if(response.status.isSuccess()) {
                database.requirementDao.insertReq(body)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun createNote(token: String, note: CreateNoteDto): DefaultResp {
        return try {
            val response = client.post {
                url(HttpRoutes.CREATE_NOTE)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
                setBody(note)
            }
            val body = response.body<NoteEnt>()
            if(response.status.isSuccess()) {
                database.noteDao.insertNote(body)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun createExam(token: String, exam: CreateExamDto): DefaultResp {
        return try {
            val response = client.post {
                url(HttpRoutes.CREATE_EXAM)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
                setBody(exam)
            }
            val body = response.body<ExamEnt>()
            if(response.status.isSuccess()) {
                database.examDao.insertExam(body)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

}