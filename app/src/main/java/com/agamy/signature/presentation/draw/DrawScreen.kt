package com.agamy.signature.presentation.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agamy.signature.R

@Composable
fun DrawScreen(viewModel: DrawViewModel = hiltViewModel()) {

    val paths by viewModel.paths.collectAsState()
    val brushColor by viewModel.brushColor.collectAsState()
    val brushSize by viewModel.brushSize.collectAsState()

    // var currentPoints by rememberSaveable { mutableStateOf<List< Offset>>(emptyList()) }
    // var currentPath by remember { mutableStateOf(Path()) }
    var currentPoints by rememberSaveable { mutableStateOf<List<Offset>>(emptyList()) }


    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val backgroundColor = if (isDarkMode) Color.Black else Color.White
    val toolbarColor = if (isDarkMode) Color.DarkGray else Color.LightGray
    val iconTint = if (isDarkMode) Color.White else Color.Black

    Column(Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(toolbarColor)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { viewModel.clearCanvas() }) {
                Image(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear"
                )
            }

            IconButton(onClick = { viewModel.toggleTheme() }) {
                Image(
                    painter = if (isDarkMode)
                        painterResource(id = R.drawable.baseline_wb_sunny_24)
                    else
                        painterResource(id = R.drawable.baseline_dark_mode_24),
                    contentDescription = "Toggle Theme",
                    modifier = Modifier.padding(4.dp),
                    colorFilter = ColorFilter.tint(iconTint)
                )
            }

            IconButton(onClick = {
                //viewModel.saveDrawing()
            }) {
                Image(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save Drawing"
                )
            }

        }
        Canvas(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(backgroundColor)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { currentPoints = listOf(it) },
                        onDrag = { change, _ -> currentPoints = currentPoints + change.position },
                        onDragEnd = {
                            viewModel.addPath(currentPoints, brushColor, brushSize)
                            currentPoints = emptyList()
                        }
                    )
                }
        ) {
            // رسم المسارات القديمة
            for (path in paths) {
                val drawPath = Path().apply {
                    path.points.forEachIndexed { index, point ->
                        if (index == 0) moveTo(point.x, point.y)
                        else lineTo(point.x, point.y)
                    }
                }
                drawPath(
                    drawPath,
                    color = path.color,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = path.strokeWidth)
                )
            }

            // رسم المسار الجاري
            val currentPath = Path().apply {
                currentPoints.forEachIndexed { index, point ->
                    if (index == 0) moveTo(point.x, point.y)
                    else lineTo(point.x, point.y)
                }
            }
            drawPath(
                currentPath,
                color = Color.Gray,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 8f)
            )
        }

        // التحكم
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(toolbarColor)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ColorPalette { color ->
                viewModel.changeColor(color)
            }
        }

        BrushSizeSlider(
            brushSize = brushSize,
            onValueChange = { viewModel.changeBrushSize(it) },
            isDarkMode = isDarkMode
        )
        /*   Text(
               text = "Brush size: ${brushSize.toInt()}",
               style = MaterialTheme.typography.bodyMedium,
               color = if (isDarkMode) Color.White else Color.Black,
               modifier = Modifier.padding(start = 16.dp, top = 8.dp)
           )

           Slider(
               value = brushSize,
               onValueChange = { viewModel.changeBrushSize(it) },
               valueRange = 2f..20f,
               modifier = Modifier.background(toolbarColor)
                   .padding(horizontal = 16.dp)
                   .fillMaxWidth(),
               colors = SliderDefaults.colors(
                   thumbColor = Color(0xFF6200EE), // بنفسجي مودرن
                   activeTrackColor = Color(0xFFBB86FC), // لون مضيء للجزء الفعال
                   inactiveTrackColor = Color(0xFFBDBDBD), // الجزء الغير مفعل
                   activeTickColor = Color.Transparent,
                   inactiveTickColor = Color.Transparent
               )
           )*/
    }


}


@Composable
fun ColorPalette(
    onColorSelected: (Color) -> Unit
) {
    val colors = listOf(
        Color.Black,
        Color.Red,
        Color.Blue,
        Color.Green,
        Color.Yellow,
        Color.Magenta,
        Color.White,
        Color.Cyan
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(color = color, shape = CircleShape)
                    .clickable { onColorSelected(color) }
                    .padding(4.dp)
            )
        }
    }
}


@Composable
fun BrushSizeSlider(
    brushSize: Float,
    onValueChange: (Float) -> Unit,
    isDarkMode: Boolean
) {
    val trackColor = if (isDarkMode) Color(0xFF6F6D70) else Color(0xFF111111)
    val thumbColor = if (isDarkMode) Color(0xFF6F6F72) else Color(0xFF050505)
    val backgroundColor = if (isDarkMode) Color(0xFF2C2C2C) else Color(0xFF6E6E6E)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isDarkMode) Color.DarkGray else Color.LightGray)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Brush size: ${brushSize.toInt()}",
            style = MaterialTheme.typography.bodyMedium,
            color = if (isDarkMode) Color.White else Color.Black,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .shadow(4.dp, RoundedCornerShape(50))
                .background(backgroundColor, RoundedCornerShape(50))
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Slider(
                value = brushSize,
                onValueChange = onValueChange,
                valueRange = 2f..20f,
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = thumbColor,
                    activeTrackColor = trackColor,
                    inactiveTrackColor = trackColor.copy(alpha = 0.3f)
                )
            )
        }
    }
}