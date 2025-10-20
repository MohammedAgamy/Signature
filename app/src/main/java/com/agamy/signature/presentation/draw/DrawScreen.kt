package com.agamy.signature.presentation.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agamy.signature.domain.model.DrawModelPath

@Composable
fun DrawScreen(viewModel: DrawViewModel = hiltViewModel()) {

    val paths by viewModel.paths.collectAsState()
    val brushColor by viewModel.brushColor.collectAsState()
    val brushSize by viewModel.brushSize.collectAsState()

    // var currentPoints by rememberSaveable { mutableStateOf<List< Offset>>(emptyList()) }
    var currentPath by remember { mutableStateOf(Path()) }
    var currentPoints by rememberSaveable { mutableStateOf<List<Offset>>(emptyList()) }





    Column(Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White)
                .pointerInput(true) {
                    detectDragGestures(
                        onDragStart = { offset -> currentPath.moveTo(offset.x, offset.y) },
                        onDrag = { _, dragAmount ->
                            currentPath.relativeLineTo(dragAmount.x, dragAmount.y)
                        },
                        onDragEnd = {
                            viewModel.addPath(
                                points = currentPath ,
                                color = brushColor,
                                width = brushSize
                            )
                            currentPath = Path()
                        }
                    )
                }
        ) {
            // رسم المسارات القديمة
            paths.forEach { drawPath ->
                drawPath(
                    path = drawPath.points,
                    color = drawPath.color,
                    style = Stroke(width = drawPath.strokeWidth)
                )
            }

            // المسار الحالي
            drawPath(
                path = currentPath,
                color = brushColor,
                style = Stroke(width = brushSize)
            )
        }

        // التحكم
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = { viewModel.changeColor(Color.Red) }) { Text("Red") }
            Button(onClick = { viewModel.changeColor(Color.Blue) }) { Text("Blue") }
            Button(onClick = { viewModel.clearCanvas() }) { Text("Clear") }
        }

        Text("Brush size: ${brushSize.toInt()}")
        Slider(
            value = brushSize,
            onValueChange = { viewModel.changeBrushSize(it) },
            valueRange = 2f..20f,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }

}