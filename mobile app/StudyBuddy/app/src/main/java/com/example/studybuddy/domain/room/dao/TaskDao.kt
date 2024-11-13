package com.example.studybuddy.domain.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.studybuddy.data.entityes.TaskEnt
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(tasks: List<TaskEnt>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: TaskEnt)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTask(task: TaskEnt)

    @Delete
    fun deleteTask(task: TaskEnt)

    @Query("DELETE FROM tasks")
    fun deleteAllTask()

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEnt>>

    @Query("SELECT * FROM tasks WHERE isCompleted = TRUE")
    fun getCompletedTasks(): Flow<List<TaskEnt>>

}

//OnConflictStrategy.REPLACE работает так:
//При возникновении нарушения ограничения UNIQUE или PRIMARY KEY алгоритм REPLACE
// удаляет ранее существовавшие строки, вызывающие нарушение ограничения, перед
// вставкой или обновлением текущей строки, и команда продолжает выполняться в обычном
// режиме. Если возникает нарушение ограничения NOT NULL, алгоритм разрешения конфликтов
// REPLACE заменяет значение NULL значением по умолчанию для этого столбца, а если у
// столбца нет значения по умолчанию, то используется алгоритм ABORT. Если возникает
// нарушение ограничения CHECK или ограничения внешнего ключа, алгоритм разрешения конфликтов
// REPLACE работает как ABORT.

//OnConflictStrategy.IGNORE работает так:
//При возникновении нарушения применимого ограничения алгоритм разрешения IGNORE
// пропускает одну строку, содержащую нарушение ограничения, и продолжает обработку
// последующих строк оператора SQL, как если бы ничего не произошло. Другие строки до и
// после строки, содержащей нарушение ограничения, вставляются или обновляются в обычном
// режиме. При использовании алгоритма разрешения конфликтов IGNORE не возвращается ошибка
// для ограничений уникальности, NOT NULL и UNIQUE. Однако алгоритм разрешения конфликтов
// IGNORE работает как ABORT для ограничений внешнего ключа и ошибок.

//Откуда взяла инфу: https://sqlite.org/lang_conflict.html
//https://startandroid.ru/ru/courses/architecture-components/27-course/architecture-components/531-urok-7-room-insert-update-delete-transaction.html

