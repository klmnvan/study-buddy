package com.example.studybuddy.domain

import android.content.Context
import android.content.SharedPreferences

object CachedData {

    var tokenUser: String = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoidXNlckBleGFtcGxlLmNvbSIsImdpdmVuX25hbWUiOiJ1c2VyQGV4YW1wbGUuY29tIiwicm9sZSI6IlVzZXIiLCJ1bmlxdWVfbmFtZSI6InVzZXJAZXhhbXBsZS5jb20iLCJuYW1laWQiOiI0ZTFlYjc4Mi1iOWIzLTQ3ZWItYjg0Yy05MDhhZTQzZWY2YTciLCJlbWFpbCI6InVzZXJAZXhhbXBsZS5jb20iLCJuYmYiOjE3Mjk5ODI1NTQsImV4cCI6MTczMDU4NzM1NCwiaWF0IjoxNzI5OTgyNTU0LCJpc3MiOiJJc3N1ZXIiLCJhdWQiOiJBdWRpZW5jZSJ9.y_xPEutXTnLgylLtGDgB5nLPmuSXB9bK3b4dOJj3vnb9SrD5cAWPE_ci1tnJ1SZqRlSu_fysHGKTHllpvvvZpg"

    private lateinit var spAct: SharedPreferences

    fun init(context: Context) {
        spAct = context.getSharedPreferences("root", Context.MODE_PRIVATE)
    }

    /*fun checkToken() {
        if (CurrentUser.token != ""){
            val mDecode = decodeToken(CurrentUser.token)
            val exp = JSONObject(mDecode).getString("exp")
            val localDate = utcToLocalDateTime(exp.toLong())
            val now = Date()
            if (localDate.before(now)) {
                Log.d("token", "истек")
                act = 0
            } else {
                Log.d("token", "не истек")
            }
        } else {
            Log.d("token", "отсутствует")
            act = 0
        }
    }*/

    var token: String
        get() = spAct.getString("token", "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoidXNlckBleGFtcGxlLmNvbSIsImdpdmVuX25hbWUiOiJ1c2VyQGV4YW1wbGUuY29tIiwicm9sZSI6IlVzZXIiLCJ1bmlxdWVfbmFtZSI6InVzZXJAZXhhbXBsZS5jb20iLCJuYW1laWQiOiI0ZTFlYjc4Mi1iOWIzLTQ3ZWItYjg0Yy05MDhhZTQzZWY2YTciLCJlbWFpbCI6InVzZXJAZXhhbXBsZS5jb20iLCJuYmYiOjE3Mjk5ODI1NTQsImV4cCI6MTczMDU4NzM1NCwiaWF0IjoxNzI5OTgyNTU0LCJpc3MiOiJJc3N1ZXIiLCJhdWQiOiJBdWRpZW5jZSJ9.y_xPEutXTnLgylLtGDgB5nLPmuSXB9bK3b4dOJj3vnb9SrD5cAWPE_ci1tnJ1SZqRlSu_fysHGKTHllpvvvZpg")!!
        set(value) = spAct.edit().putString("token", value).apply()

}