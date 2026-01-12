package com.example.basic_beauty_cam

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FileUtil {
    private const val TAG = "FileUtil"
    private const val IMAGE_PREFIX = "beauty_cam_"
    private const val IMAGE_SUFFIX = ".jpg"
    private val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.getDefault())


    fun bitmapToBytes(bitmap: Bitmap): ByteArray {
        val output = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, output)
        return output.toByteArray()
    }

    fun bitmapToRawBytes(bitmap: Bitmap): ByteArray {
        val buffer = ByteBuffer.allocate(bitmap.byteCount)
        bitmap.copyPixelsToBuffer(buffer)
        return buffer.array()
    }

    fun saveBitmapToCache(context: Context, bitmap: Bitmap?): String? {
        if (bitmap == null) {
            Log.e(TAG, "Bitmap is null, cannot save")
            return null
        }

        return try {
            val cacheDir = context.cacheDir

            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }

            val timestamp = dateFormat.format(Date())
            val fileName = "${timestamp}${IMAGE_SUFFIX}"
            val imageFile = File(cacheDir, fileName)

            val fos = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()

            val filePath = imageFile.absolutePath
            Log.d(TAG, "Bitmap saved successfully: $filePath")
            Log.d(TAG, "File size: ${imageFile.length()} bytes")

            filePath
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save bitmap: ${e.message}", e)
            null
        }
    }

    fun saveBitmapToCache(context: Context, bitmap: Bitmap?, fileName: String): String? {
        if (bitmap == null) {
            Log.e(TAG, "Bitmap is null, cannot save")
            return null
        }

        return try {
            val cacheDir = context.cacheDir

            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }

            // ���t��
            val fullFileName = if (fileName.endsWith(IMAGE_SUFFIX)) {
                fileName
            } else {
                "$fileName$IMAGE_SUFFIX"
            }

            val imageFile = File(cacheDir, fullFileName)

            val fos = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()

            val filePath = imageFile.absolutePath
            Log.d(TAG, "Bitmap saved successfully: $filePath")
            Log.d(TAG, "File size: ${imageFile.length()} bytes")

            filePath
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save bitmap: ${e.message}", e)
            null
        }
    }

    fun saveBitmapToCache(context: Context, bitmap: Bitmap?, quality: Int): String? {
        if (bitmap == null) {
            Log.e(TAG, "Bitmap is null, cannot save")
            return null
        }

        return try {
            val cacheDir = context.cacheDir

            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }

            val timestamp = dateFormat.format(Date())
            val fileName = "${IMAGE_PREFIX}${timestamp}${IMAGE_SUFFIX}"
            val imageFile = File(cacheDir, fileName)

            val fos = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality.coerceIn(0, 100), fos)
            fos.flush()
            fos.close()

            val filePath = imageFile.absolutePath
            Log.d(TAG, "Bitmap saved successfully: $filePath")
            Log.d(TAG, "File size: ${imageFile.length()} bytes")

            filePath
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save bitmap: ${e.message}", e)
            null
        }
    }

    fun clearCacheImages(context: Context): Int {
        return try {
            val cacheDir = context.cacheDir
            if (!cacheDir.exists()) {
                return 0
            }

            val files = cacheDir.listFiles { file ->
                file.isFile && file.name.startsWith(IMAGE_PREFIX)
            } ?: emptyArray()

            var deletedCount = 0
            files.forEach { file ->
                if (file.delete()) {
                    deletedCount++
                    Log.d(TAG, "Deleted cache file: ${file.name}")
                }
            }

            Log.d(TAG, "Cleared $deletedCount image files from cache")
            deletedCount
        } catch (e: Exception) {
            Log.e(TAG, "Failed to clear cache: ${e.message}", e)
            0
        }
    }

    fun getCacheImageFiles(context: Context): List<File> {
        return try {
            val cacheDir = context.cacheDir
            if (!cacheDir.exists()) {
                return emptyList()
            }

            cacheDir.listFiles { file ->
                file.isFile && file.name.startsWith(IMAGE_PREFIX)
            }?.toList() ?: emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get cache files: ${e.message}", e)
            emptyList()
        }
    }

}
