package com.example.studybuddy.domain.repository

import android.content.Context
import android.content.SharedPreferences

object CachedData {

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
        get() = spAct.getString("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzNDVoMDk4YmI4cmViZXJid3I0dnZiODk0NSIsImp0aSI6IjkxZjY1MDU1LTRhNDctNDAxYi1iM2ZiLTlhZGQ3NzU1Mzk0NSIsImlhdCI6IjE3MjM3ODkyMDkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6ImRkMzhmMzJkLTc1ODYtNGM1Yi05ZGExLTM4YTYxY2MxN2VhYyIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWUiOiJuZXNrbG1udmFuQGdtYWlsLmNvbSIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL2VtYWlsYWRkcmVzcyI6Im5lc2tsbW52YW5AZ21haWwuY29tIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoi0J_RgNC10L_QvtC00LDQstCw0YLQtdC70YwiLCJleHAiOjE3MjM4NzU2MDksImlzcyI6IkV4YW1wbGVJc3N1ZXIiLCJhdWQiOiJWYWxpZEF1ZGllbmNlIn0.PHyENhl-1FYCOhPoAYUD5gJ-7a4ClmOnzS_cZ3HlApc")!!
        set(value) = spAct.edit().putString("token", value).apply()

}