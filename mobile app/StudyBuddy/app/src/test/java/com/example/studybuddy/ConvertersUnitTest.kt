package com.example.studybuddy

import com.example.studybuddy.domain.converters.FormatDateDBToDDMMYYYY
import com.example.studybuddy.domain.converters.FormatDateDBToDMMMMYYYY
import org.junit.Test

import org.junit.Assert.*

//Способ именования методов: [Тестируемый метод]_[Сценарий]_[Ожидаемое поведение].
class ConvertersUnitTest {

    @Test
    fun FormatDateDBToDMMMMYYYY_ConvertDate_2024_11_26_Returned_26_november_2024() {
        // Arrange
        val inputDateFromDb = "2024-11-26" //дата в том формате, в котором приходит из БД
        val expectedFormattedDate = "26 ноября 2024" //ожидаемый формат даты

        //Act
        val actualFormattedDate = FormatDateDBToDMMMMYYYY(inputDateFromDb) //функция конвертациии даты

        //Assert
        assertEquals(expectedFormattedDate, actualFormattedDate) //проверка равенства ожидаемого и получаемого результата
    }

    @Test
    fun FormatDateDBToDDMMYYYY_ConvertDate_2024_11_26_Returned_26112024() {
        // Arrange
        val inputDateFromDb = "2024-11-26" //дата в том формате, в котором приходит из БД
        val expectedFormattedDate = "26.11.2024" //ожидаемый формат даты

        //Act
        val actualFormattedDate = FormatDateDBToDDMMYYYY(inputDateFromDb) //функция конвертациии даты

        //Assert
        assertEquals(expectedFormattedDate, actualFormattedDate) //проверка равенства ожидаемого и получаемого результата
    }

}