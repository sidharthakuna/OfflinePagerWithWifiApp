package com.example.project1project.identity

import android.content.Context
import java.util.UUID
import androidx.core.content.edit

object PagerIdManager{

    private const val PREF_NAME="pager_identity"
    private const val KEY_PAGER_ID="pager_id"
    private const val KEY_CHANGE_COUNT = "pager_change_count"
    private const val MAX_CHANGES=2

    fun getPagerId(context:Context):String? {
        val prefs=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        return prefs.getString(KEY_PAGER_ID,null)
    }
    fun canChangePagerId(context:Context):Boolean{
        val prefs = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        val count = prefs.getInt(KEY_CHANGE_COUNT,0)

        return count < MAX_CHANGES
    }
    fun setPagerId(context:Context,newId:String):Boolean{
        val prefs = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        val count=prefs.getInt(KEY_CHANGE_COUNT,0)
        if(count>=MAX_CHANGES) return false
        prefs.edit{
            putString(KEY_PAGER_ID,newId)
            putInt(KEY_CHANGE_COUNT,count+1)
        }
        return true
    }
    fun getOrCreatePagerId(context:Context):String{
        val prefs=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        val existing = prefs.getString(KEY_PAGER_ID,null)
        if(existing != null) return existing

        val newId = UUID.randomUUID()
            .toString()
            .substring(0,6)
            .uppercase()

        prefs.edit {
            putString(KEY_PAGER_ID, newId)
        }
        return newId
    }
}