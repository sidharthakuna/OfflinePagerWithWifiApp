package com.example.project1project.utils

import android.Manifest
import android.os.Build

object PermissionHelper{

    fun bluetoothPermissions():Array<String>{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
        else{
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

    }
}