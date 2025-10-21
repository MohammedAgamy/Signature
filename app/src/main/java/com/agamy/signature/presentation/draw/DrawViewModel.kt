package com.agamy.signature.presentation.draw

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agamy.signature.domain.model.DrawModelPath
import com.agamy.signature.domain.usecase.ClearPathsUseCase
import com.agamy.signature.domain.usecase.SavePathUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DrawViewModel @Inject constructor(
    private val useCase: SavePathUseCase,
    private val clearPathsUseCase: ClearPathsUseCase
) : ViewModel() {

    private val _paths = MutableStateFlow<List<DrawModelPath>>(emptyList())
    val paths: StateFlow<List<DrawModelPath>> = _paths


    private val _brushColor = MutableStateFlow(Color.Black)
    val brushColor: StateFlow<Color> = _brushColor

    private val _brushSize = MutableStateFlow(6f)
    val brushSize: StateFlow<Float> = _brushSize


    // 🌓 وضع الليل
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    fun addPath(points: List<Offset>, color: Color, width: Float) {
        val newPath = DrawModelPath(points, color, width)
        viewModelScope.launch {
            useCase(newPath)
            _paths.value = _paths.value + newPath
        }
    }


    fun clearCanvas() {
        viewModelScope.launch {
            clearPathsUseCase()
            _paths.value = emptyList()
        }
    }

    fun changeColor(color: Color) {
        _brushColor.value = color
    }

    fun saveDrawingToGallery(context: Context, paths: List<DrawModelPath>) {
        // 🎨 نرسم الصورة على Bitmap حقيقي باستخدام Android Canvas
        val bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bitmap)

        // 🖌️ نرسم كل المسارات المحفوظة
        val paint = android.graphics.Paint().apply {
            style = android.graphics.Paint.Style.STROKE
            strokeCap = android.graphics.Paint.Cap.ROUND
            isAntiAlias = true
        }

        paths.forEach { path ->
            paint.color = path.color.toArgb()
            paint.strokeWidth = path.strokeWidth

            val androidPath = android.graphics.Path().apply {
                path.points.forEachIndexed { index, point ->
                    if (index == 0) moveTo(point.x, point.y)
                    else lineTo(point.x, point.y)
                }
            }

            canvas.drawPath(androidPath, paint)
        }

        // 💾 نحفظ الصورة في المعرض
        val saved = saveBitmapToGallery(context, bitmap)
        if (saved) {
            Toast.makeText(context, "✅ Saved to Gallery", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "❌ Failed to save image", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveBitmapToGallery(context: Context, bitmap: Bitmap): Boolean {
        val filename = "Drawing_${System.currentTimeMillis()}.png"

        return try {
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + "/DrawApp"
                )
            }

            val imageUri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )

            imageUri?.let { uri ->
                context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                }
            }

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    fun changeBrushSize(size: Float) {
        _brushSize.value = size
    }

    fun toggleTheme() {
        _isDarkMode.value = !_isDarkMode.value
    }


}