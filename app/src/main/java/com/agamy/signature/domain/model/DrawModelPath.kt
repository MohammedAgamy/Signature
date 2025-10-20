package com.agamy.signature.domain.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

// model class to represent a drawn path with its properties
// including points, color, and stroke width
// default values are provided for color and stroke width
// points are stored as a list of Offset objects
// to capture the x and y coordinates of the drawn path
//strokeWidth is represented as a Float value
// this class can be used to manage and render drawn paths

data class DrawModelPath (
    // using offset to store x and y coordinates
    val points: Path,
    // default color is black for drawing
    val color: Color = Color.Companion.Black,
    // default stroke width for drawing
    val strokeWidth: Float = 8f
)