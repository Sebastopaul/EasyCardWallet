package com.isitechproject.easycardwallet.model.service

interface PermissionRequestService {
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    )
}