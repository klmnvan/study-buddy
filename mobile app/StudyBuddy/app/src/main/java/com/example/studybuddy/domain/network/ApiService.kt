package com.example.studybuddy.domain.network

import com.example.studybuddy.data.dto.CreateDiscDto
import com.example.studybuddy.data.dto.CreateExamDto
import com.example.studybuddy.data.dto.CreateNoteDto
import com.example.studybuddy.data.dto.CreateReqDto
import com.example.studybuddy.data.dto.CreateTaskDto
import com.example.studybuddy.data.dto.CreateTeacherDto
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.ExamEnt
import com.example.studybuddy.data.entityes.NoteEnt
import com.example.studybuddy.data.entityes.RequirementEnt
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.data.entityes.TeacherEnt
import com.example.studybuddy.data.modelsitreshalo.Values
import com.example.studybuddy.domain.responses.DefaultResp
import com.example.studybuddy.domain.responses.ExamsResp
import com.example.studybuddy.domain.responses.TasksResp
import com.example.studybuddy.domain.responses.AuthResp
import com.example.studybuddy.domain.responses.DisciplinesResp
import com.example.studybuddy.domain.responses.ScheduleResp

/** Интерфейс, в котором описаны все методы для запросов к API и создаётся объект ApiServiceImpl */
interface ApiService {

    suspend fun signIn(email: String, password: String): AuthResp
    suspend fun signUp(email: String, password: String, passwordConf: String, nickname: String): AuthResp

    suspend fun getTasks(token: String): TasksResp
    suspend fun getExams(token: String): ExamsResp
    suspend fun getTeachers(token: String): DisciplinesResp

    suspend fun updateTask(token: String, task: TaskEnt): DefaultResp
    suspend fun updateReq(token: String, req: RequirementEnt): DefaultResp
    suspend fun updateTeacher(token: String, teacher: TeacherEnt): DefaultResp
    suspend fun updateDiscipline(token: String, disc: DisciplineEnt): DefaultResp
    suspend fun updateNote(token: String, note: NoteEnt): DefaultResp
    suspend fun updateExam(token: String, exam: ExamEnt): DefaultResp

    suspend fun createTeacher(token: String, teacher: CreateTeacherDto): DisciplinesResp
    suspend fun createTask(token: String, task: CreateTaskDto): TasksResp
    suspend fun createDisc(token: String, disc: CreateDiscDto): DisciplinesResp
    suspend fun createRequirement(token: String, req: CreateReqDto): DefaultResp
    suspend fun createNote(token: String, note: CreateNoteDto): DefaultResp
    suspend fun createExam(token: String, exam: CreateExamDto): DefaultResp

    suspend fun deleteTask(token: String, task: TaskEnt): DefaultResp
    suspend fun deleteTeacher(token: String, teacher: TeacherEnt): DefaultResp
    suspend fun deleteDisc(token: String, disc: DisciplineEnt): DefaultResp
    suspend fun deleteReq(token: String, req: RequirementEnt): DefaultResp
    suspend fun deleteNote(token: String, note: NoteEnt): DefaultResp
    suspend fun deleteExam(token: String, exam: ExamEnt): DefaultResp

    //it-reshalo
    suspend fun getValues(): ScheduleResp
    suspend fun getSchedule(filterId: Int, date: Long): ScheduleResp

}