package com.example.studybuddy.domain.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.studybuddy.view.ui.theme.ThemeMode
import org.json.JSONObject
import java.time.Instant
import java.time.ZoneId
import java.util.Base64
import java.util.Date

/** Хранилищие данных пользователя */
object UserRepository {

    var tokenUser: String = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoidXNlckBleGFtcGxlLmNvbSIsImdpdmVuX25hbWUiOiJ1c2VyQGV4YW1wbGUuY29tIiwicm9sZSI6IlVzZXIiLCJ1bmlxdWVfbmFtZSI6InVzZXJAZXhhbXBsZS5jb20iLCJuYW1laWQiOiI0ZTFlYjc4Mi1iOWIzLTQ3ZWItYjg0Yy05MDhhZTQzZWY2YTciLCJlbWFpbCI6InVzZXJAZXhhbXBsZS5jb20iLCJuYmYiOjE3Mjk5ODI1NTQsImV4cCI6MTczMDU4NzM1NCwiaWF0IjoxNzI5OTgyNTU0LCJpc3MiOiJJc3N1ZXIiLCJhdWQiOiJBdWRpZW5jZSJ9.y_xPEutXTnLgylLtGDgB5nLPmuSXB9bK3b4dOJj3vnb9SrD5cAWPE_ci1tnJ1SZqRlSu_fysHGKTHllpvvvZpg"

    var themes = listOf(ThemeMode.Light, ThemeMode.Dark)

    private lateinit var spAct: SharedPreferences

    fun init(context: Context) {
        spAct = context.getSharedPreferences("root", Context.MODE_PRIVATE)
    }

    fun tokenIsValid(): Boolean {
        if (token != "") {
            val mDecode = decodeToken(token)
            val exp = JSONObject(mDecode).getString("exp")
            val localDate = utcToLocalDateTime(exp.toLong())
            val now = Date()
            return if (localDate.before(now)) {
                Log.d("token", "истек")
                false
            } else {
                Log.d("token", "не истек")
                true
            }
        } else {
            Log.d("token", "отсутствует")
            return false
        }
    }

    fun utcToLocalDateTime(utcSeconds: Long): Date {
        val instant = Instant.ofEpochSecond(utcSeconds)
        val zonedDateTime = instant.atZone(ZoneId.of("UTC"))
        return Date.from(zonedDateTime.toInstant())
    }

    private fun decodeToken(jwt: String): String {
        val parts = jwt.split(".")
        return try {
            val charset = charset("UTF-8")
            val header = String(Base64.getUrlDecoder().decode(parts[0].toByteArray(charset)), charset)
            val payload = String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)
            "$header"
            "$payload"
        } catch (e: Exception) {
            "Error parsing JWT: $e"
        }
    }

    var token: String
        get() = spAct.getString("token", "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoidXNlckBleGFtcGxlLmNvbSIsImdpdmVuX25hbWUiOiJ1c2VyQGV4YW1wbGUuY29tIiwicm9sZSI6IlVzZXIiLCJ1bmlxdWVfbmFtZSI6InVzZXJAZXhhbXBsZS5jb20iLCJuYW1laWQiOiI0ZTFlYjc4Mi1iOWIzLTQ3ZWItYjg0Yy05MDhhZTQzZWY2YTciLCJlbWFpbCI6InVzZXJAZXhhbXBsZS5jb20iLCJuYmYiOjE3MzEwMTMyMzIsImV4cCI6MTczMTYxODAzMiwiaWF0IjoxNzMxMDEzMjMyLCJpc3MiOiJJc3N1ZXIiLCJhdWQiOiJBdWRpZW5jZSJ9.QdHwsNS_HRFh1sTaXEVsJ8ZZd3bds1_HYgQbHv3EekI_uolDeMgYj37V-yHTilVan9zzfNkhdsoRmZHdJV3giA")!!
        set(value) = spAct.edit().putString("token", value).apply()

    var theme: String
        get() = spAct.getString("theme", ThemeMode.Light.title)!!
        set(value) = spAct.edit().putString("theme", value).apply()

    var lastGroupName: String
        get() = spAct.getString("group name", "Не выбрано")!!
        set(value) = spAct.edit().putString("group name", value).apply()

    var lastGroupId: Int
        get() = spAct.getInt("group id", 0)
        set(value) = spAct.edit().putInt("group id", value).apply()

}