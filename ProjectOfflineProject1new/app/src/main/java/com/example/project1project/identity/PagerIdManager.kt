package com.example.project1project.identity

import android.content.Context
import java.util.UUID

object PagerIdManager{

    private const val PREF_NAME="pager_identity"
    private const val KEY_PAGER_ID="pager_id"

    fun getOrCreatePagerId(context:Context):String{
        val prefs= context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val existing = prefs.getString(KEY_PAGER_ID,null)
        if(existing!=null) return existing

        val newId=UUID.randomUUID()
            .toString()
            .substring(0,6)
            .uppercase()

        prefs.edit().putString(KEY_PAGER_ID,newId).apply()
        return newId
    }
}