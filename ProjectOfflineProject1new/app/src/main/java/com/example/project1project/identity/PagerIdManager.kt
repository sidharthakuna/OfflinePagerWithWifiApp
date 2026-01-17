package com.example.project1project.identity

import android.content.Context
import java.util.UUID

object PagerIdManager{

    private const val PREF_NAME="pager_identity"
    private const val KEY_PAGER_ID="pager_id"

    fun getPagerId(context:Context):String? {
        val prefs=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        return prefs.getString(KEY_PAGER_ID,null)
    }
    fun setPagerId(context:Context,pagerId:String){
        val prefs = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_PAGER_ID,pagerId).apply()
    }
}