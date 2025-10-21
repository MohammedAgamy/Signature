package com.agamy.signature.presentation.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agamy.signature.domain.model.DrawModelPath
import com.agamy.signature.domain.usecase.ClearPathsUseCase
import com.agamy.signature.domain.usecase.GetPathsUseCase
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

    fun changeBrushSize(size: Float) {
        _brushSize.value = size
    }

    fun toggleTheme() {
        _isDarkMode.value = !_isDarkMode.value
    }
}