package com.example.multimedia.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri

/**
 * 权限工具类
 * 提供请求并获取权限的接口
 */
object PermissionUtil {

    // 请求码
    private const val REQUEST_CODE_PERMISSIONS = 1001
    private const val REQUEST_CODE_MANAGE_STORAGE = 1002

    // 权限请求回调
    private var permissionCallback: PermissionCallback? = null
    private var manageStorageCallback: (() -> Unit)? = null

    /**
     * 权限回调接口
     */
    interface PermissionCallback {
        /**
         * 所有权限已授予
         */
        fun onAllGranted()

        /**
         * 部分权限被拒绝
         * @param deniedPermissions 被拒绝的权限列表
         * @param permanentlyDenied 永久拒绝的权限列表（用户选择了"不再询问"）
         */
        fun onDenied(deniedPermissions: List<String>, permanentlyDenied: List<String>)
    }

    /**
     * 检查单个权限是否已授予
     */
    fun checkPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 检查多个权限是否都已授予
     */
    fun checkPermissions(context: Context, permissions: List<String>): Boolean {
        return permissions.all { checkPermission(context, it) }
    }

    /**
     * 请求多个权限
     * @param activity 当前Activity
     * @param permissions 要请求的权限列表
     * @param callback 权限回调
     */
    fun requestPermissions(
        activity: Activity,
        permissions: List<String>,
        callback: PermissionCallback
    ) {
        // 过滤掉已授予的权限
        val deniedPermissions = permissions.filterNot {
            checkPermission(activity, it)
        }

        if (deniedPermissions.isEmpty()) {
            // 所有权限都已授予
            callback.onAllGranted()
            return
        }

        permissionCallback = callback
        ActivityCompat.requestPermissions(
            activity,
            deniedPermissions.toTypedArray(),
            REQUEST_CODE_PERMISSIONS
        )
    }

    /**
     * 请求单个权限
     */
    fun requestPermission(
        activity: Activity,
        permission: String,
        callback: PermissionCallback
    ) {
        requestPermissions(activity, listOf(permission), callback)
    }

    /**
     * 检查是否应该显示权限说明（用于向用户解释为什么需要这个权限）
     */
    fun shouldShowRationale(activity: Activity, permissions: List<String>): Boolean {
        return permissions.any { permission ->
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
        }
    }

    /**
     * 检查单个权限是否应该显示说明
     */
    fun shouldShowRationale(activity: Activity, permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }

    /**
     * 处理权限请求结果
     * 在Activity的onRequestPermissionsResult中调用此方法
     */
    fun handlePermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != REQUEST_CODE_PERMISSIONS) {
            return
        }

        val deniedPermissions = mutableListOf<String>()
        val permanentlyDenied = mutableListOf<String>()

        for (i in permissions.indices) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i])
                // 检查是否永久拒绝
//                if (!shouldShowRationale(
//                        ActivityCompat.findActivity(
//                            ContextCompat.getDisplayContext(
//                                permissionCallback as Context
//                            ) as Activity
//                        ),
//                        permissions[i]
//                    )
//                ) {
//                    permanentlyDenied.add(permissions[i])
//                }
            }
        }

        permissionCallback?.let { callback ->
            if (deniedPermissions.isEmpty()) {
                callback.onAllGranted()
            } else {
                callback.onDenied(deniedPermissions, permanentlyDenied)
            }
        }

        permissionCallback = null
    }

    // ==================== 存储权限相关 ====================

    /**
     * 获取存储权限列表（根据Android版本自动适配）
     * Android 13+ (API 33+) 使用 READ_MEDIA_* 权限
     * Android 12及以下使用 READ/WRITE_EXTERNAL_STORAGE
     */
    fun getStoragePermissions(): List<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_AUDIO
            )
        } else {
            listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    /**
     * 检查存储权限是否已授予
     */
    fun checkStoragePermission(context: Context): Boolean {
        return checkPermissions(context, getStoragePermissions())
    }

    /**
     * 请求存储权限
     */
    fun requestStoragePermissions(activity: Activity, callback: PermissionCallback) {
        requestPermissions(activity, getStoragePermissions(), callback)
    }

    // ==================== 管理所有文件访问权限（特殊权限）====================

    /**
     * 检查是否已授予管理所有文件访问权限（MANAGE_EXTERNAL_STORAGE）
     */
    fun checkManageStoragePermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            true // Android 11以下不需要此权限
        }
    }

    /**
     * 请求管理所有文件访问权限
     * 注意：这是一个特殊权限，需要跳转到系统设置页面
     */
    fun requestManageStoragePermission(
        activity: Activity,
        callback: (() -> Unit)?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = "package:${activity.packageName}".toUri()
                activity.startActivityForResult(intent, REQUEST_CODE_MANAGE_STORAGE)
                manageStorageCallback = callback
            } catch (e: Exception) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                activity.startActivityForResult(intent, REQUEST_CODE_MANAGE_STORAGE)
                manageStorageCallback = callback
            }
        } else {
            // Android 11以下不需要此权限，直接回调
            callback?.invoke()
        }
    }

    /**
     * 处理管理存储权限的结果
     * 在Activity的onActivityResult中调用此方法
     */
    fun handleManageStorageResult(requestCode: Int) {
        if (requestCode == REQUEST_CODE_MANAGE_STORAGE) {
            manageStorageCallback?.invoke()
            manageStorageCallback = null
        }
    }

    // ==================== 相机权限 ====================

    /**
     * 获取相机权限列表
     */
    fun getCameraPermissions(): List<String> {
        return listOf(Manifest.permission.CAMERA)
    }

    /**
     * 检查相机权限是否已授予
     */
    fun checkCameraPermission(context: Context): Boolean {
        return checkPermission(context, Manifest.permission.CAMERA)
    }

    /**
     * 请求相机权限
     */
    fun requestCameraPermissions(activity: Activity, callback: PermissionCallback) {
        requestPermissions(activity, getCameraPermissions(), callback)
    }

    // ==================== 通知权限 ====================

    /**
     * 获取通知权限列表（Android 13+）
     */
    fun getNotificationPermissions(): List<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            emptyList()
        }
    }

    /**
     * 检查通知权限是否已授予
     */
    fun checkNotificationPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkPermission(context, Manifest.permission.POST_NOTIFICATIONS)
        } else {
            true // Android 13以下不需要请求此权限
        }
    }

    /**
     * 请求通知权限
     */
    fun requestNotificationPermissions(activity: Activity, callback: PermissionCallback) {
        val permissions = getNotificationPermissions()
        if (permissions.isEmpty()) {
            callback.onAllGranted()
        } else {
            requestPermissions(activity, permissions, callback)
        }
    }

    // ==================== 录音权限 ====================

    /**
     * 获取录音权限列表
     */
    fun getRecordAudioPermissions(): List<String> {
        return listOf(Manifest.permission.RECORD_AUDIO)
    }

    /**
     * 检查录音权限是否已授予
     */
    fun checkRecordAudioPermission(context: Context): Boolean {
        return checkPermission(context, Manifest.permission.RECORD_AUDIO)
    }

    /**
     * 请求录音权限
     */
    fun requestRecordAudioPermissions(activity: Activity, callback: PermissionCallback) {
        requestPermissions(activity, getRecordAudioPermissions(), callback)
    }

    // ==================== 位置权限 ====================

    /**
     * 获取位置权限列表
     */
    fun getLocationPermissions(): List<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

    /**
     * 检查位置权限是否已授予
     */
    fun checkLocationPermission(context: Context): Boolean {
        return checkPermissions(context, getLocationPermissions())
    }

    /**
     * 请求位置权限
     */
    fun requestLocationPermissions(activity: Activity, callback: PermissionCallback) {
        requestPermissions(activity, getLocationPermissions(), callback)
    }

    // ==================== 辅助方法 ====================

    /**
     * 打开应用设置页面（用于用户手动授予权限）
     */
    fun openAppSettings(activity: Activity) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = "package:${activity.packageName}".toUri()
        activity.startActivity(intent)
    }

    /**
     * 创建权限回调的便捷方法
     */
    fun createPermissionCallback(
        onAllGranted: () -> Unit = {},
        onDenied: ((denied: List<String>, permanentlyDenied: List<String>) -> Unit)? = null
    ): PermissionCallback {
        return object : PermissionCallback {
            override fun onAllGranted() {
                onAllGranted()
            }

            override fun onDenied(
                deniedPermissions: List<String>,
                permanentlyDenied: List<String>
            ) {
                onDenied?.invoke(deniedPermissions, permanentlyDenied)
            }
        }
    }
}

/**
 * 扩展函数：方便地在Activity中使用权限工具
 */
fun Activity.requestPermissions(
    permissions: List<String>,
    callback: PermissionUtil.PermissionCallback
) {
    PermissionUtil.requestPermissions(this, permissions, callback)
}

fun Activity.requestPermissions(
    vararg permissions: String,
    callback: PermissionUtil.PermissionCallback
) {
    PermissionUtil.requestPermissions(this, permissions.toList(), callback)
}
