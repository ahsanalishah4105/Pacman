/*
 * Copyright (c) 2022 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.yourcompany.android.pacmanapp


import android.graphics.Typeface
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourcompany.android.pacmanapp.ui.theme.GraphicsInJetpackComposeTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      GraphicsInJetpackComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          CanvasApp()
        }
      }
    }
  }
}


@Composable
fun CanvasApp() {

  Canvas(modifier = Modifier
    .fillMaxHeight()
    .fillMaxWidth()

  ) {
    val canvasWidth = size.width
    val canvasHeight = size.height

    drawBlackScreen(scope = this, canvasWidth, canvasHeight)

    drawBlueLines(scope = this, canvasWidth, canvasHeight)

    val pacmanXPos = canvasWidth / 3
    val pacmanYPos = canvasHeight / 2
    val pacmanOffset = Offset(pacmanXPos, pacmanYPos)

    drawPacman(scope = this, pacmanOffset)

    drawPointsLine(scope = this, pacmanOffset)

    drawImmuneCircle(scope = this, pacmanOffset, size.minDimension / 20)

    drawGhost(scope = this, canvasWidth, canvasHeight)

    drawScore(scope = this, canvasWidth, canvasHeight)

    drawPacmanLives(scope = this, pacmanOffset)

  }
}


fun drawBlackScreen(scope: DrawScope, canvasWidth: Float, canvasHeight: Float) {
  // 1. Use the drawReact method
  scope.drawRect(Color.Black,
    size= Size(canvasWidth, canvasHeight)
  )
}


@OptIn(ExperimentalGraphicsApi::class)
fun drawBlueLines(scope: DrawScope, canvasWidth: Float, canvasHeight: Float) {
  val blue = Color.hsv(225f, 1f, 1f)

  val contentSpace = 400.dp.value
  val lineSpace = 20.dp.value
  val initialLine = canvasHeight / 2 - (canvasHeight / 16)

  val topLine = initialLine
  val topLine2 = initialLine + lineSpace
  val bottomLine = initialLine + contentSpace
  val bottomLine2 = initialLine + contentSpace + lineSpace

  val lines = arrayListOf(topLine, topLine2, bottomLine, bottomLine2)
  for (line in lines) {
    // 2. Use the drawLine method
    scope.drawLine(
      blue, // 1
      Offset(0f, line), // 2
      Offset(canvasWidth, line), // 2
      strokeWidth = 8.dp.value // 3
    )
  }
}


fun drawPacman(scope: DrawScope, pacmanOffset: Offset) {
  // 5. Use the drawArc method
  scope.drawArc(
    Color.Yellow, // 1
    45f, // 2
    270f, // 3
    true, // 4
    pacmanOffset,
    Size(200.dp.value, 200.dp.value)
  )
}


@OptIn(ExperimentalGraphicsApi::class)
fun drawPointsLine(scope: DrawScope, pacmanOffset: Offset) {
  val dotYPos = pacmanOffset.y + 100.dp.value
  val points = listOf(
    Offset(pacmanOffset.x + 200.dp.value, dotYPos),
    Offset(pacmanOffset.x + 300.dp.value, dotYPos),
    Offset(pacmanOffset.x + 400.dp.value, dotYPos),
    Offset(pacmanOffset.x + 500.dp.value, dotYPos)
  )
  val purple = Color.hsl(300f,1f,0.5f)
  // 4. Use the drawPoints method
  scope.drawPoints(points, // 1
    PointMode.Points, // 2
    purple, // 3
    strokeWidth = 16.dp.value) // 4
}


@OptIn(ExperimentalGraphicsApi::class)
fun drawImmuneCircle(scope: DrawScope, pacmanOffset: Offset, radius: Float) {
  val purple = Color.hsl(300f,1f,0.5f)
  val dotYPos = pacmanOffset.y + 100.dp.value
  // 3. Use the drawCircle method
  scope.drawCircle(purple, // 1
    center = Offset(pacmanOffset.x + 600.dp.value, dotYPos), // 2
    radius = radius) // 3
}

fun drawGhost(scope: DrawScope, canvasWidth: Float, canvasHeight: Float) {
  // 6. Draw a complex shape
  
    val ghostXPos = canvasWidth / 4
    val ghostYPos = canvasHeight / 2
    val threeBumpsPath = Path().let {
      it.arcTo( // 1
        Rect(
          Offset(ghostXPos - 50.dp.value, ghostYPos + 175.dp.value),
          Size(50.dp.value, 50.dp.value)
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 180f,
        forceMoveTo = true
      )
      it.arcTo( // 2
        Rect(
          Offset(ghostXPos - 100.dp.value, ghostYPos + 175.dp.value),
          Size(50.dp.value, 50.dp.value)
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 180f,
        forceMoveTo = true
      )
      it.arcTo( // 3
        Rect(
          Offset(ghostXPos - 150.dp.value, ghostYPos + 175.dp.value),
          Size(50.dp.value, 50.dp.value)
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 180f,
        forceMoveTo = true
      )
      it.close()
      it
    }
    scope.drawPath( // 4
      path = threeBumpsPath,
      Color.Red,
      style = Fill
    )
    scope.drawRect(
      Color.Red,
      Offset(ghostXPos - 150.dp.value, ghostYPos + 120.dp.value),
      Size(150.dp.value, 82.dp.value)
    )
    scope.drawArc(
      Color.Red,
      startAngle = 180f,
      sweepAngle = 180f,
      useCenter = false,
      topLeft = Offset(ghostXPos - 150.dp.value, ghostYPos + 50.dp.value),
      size = Size(150.dp.value, 150.dp.value)
    )
    scope.drawCircle(
      Color.White,
      center = Offset(ghostXPos - 100.dp.value, ghostYPos + 100.dp.value),
      radius = 20f
    )
    scope.drawCircle(
      Color.Black,
      center = Offset(ghostXPos - 90.dp.value, ghostYPos + 100.dp.value),
      radius = 10f
    )

}


fun drawScore(scope: DrawScope, canvasWidth: Float, canvasHeight: Float) {
  val textPaint = Paint().asFrameworkPaint().apply {
    isAntiAlias = true
    textSize = 80.sp.value
    color = android.graphics.Color.WHITE
    typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
    textAlign = android.graphics.Paint.Align.CENTER
  }
  // 7. Draw a text
  scope.drawIntoCanvas {
    it.nativeCanvas.drawText( // 1
      "HIGH SCORE", // 2
      canvasWidth / 2, // 3
      canvasHeight / 3, // 3
      textPaint // 4
    )
    it.nativeCanvas.drawText( // 1
      "360", // 2
      canvasWidth / 2, // 3
      canvasHeight / 3 + 100.dp.value, // 3
      textPaint // 4
    )}
}


fun drawPacmanLives(scope: DrawScope, pacmanOffset: Offset) {
  // 8. Draw and transform pacmans
  scope.withTransform({
    scale(0.4f)
    translate(top=1200.dp.value, left=-1050.dp.value)
    rotate(180f)
  }) {
    drawArc(
      Color.Yellow,
      45f,
      270f,
      true,
      pacmanOffset,
      Size(200.dp.value, 200.dp.value)
    )
  }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  GraphicsInJetpackComposeTheme {
    CanvasApp()
  }
}
