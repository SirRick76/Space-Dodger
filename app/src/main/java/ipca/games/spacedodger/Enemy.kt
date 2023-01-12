package ipca.games.spacedodger

import android.content.Context
import android.graphics.*
import android.text.style.LineHeightSpan
import android.view.Gravity
import java.util.*


class Enemy {

    var bitmap: Bitmap


    var x = 0
    var y = 0

    var vy = 0

    var maxY: Float
    var maxX: Float

    var detectColision = Rect()

    constructor(context: Context, screenWidth: Int, screenHeight: Int, x: Int) {
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.urchin1)
        this.x = x
        this.y = 0
        this.maxY = screenHeight.toFloat()
        this.maxX = screenWidth.toFloat()
        this.vy = 10

        detectColision = Rect(x, y, bitmap.width, bitmap.height)
    }

    fun update() {
        y += vy

        if (y >= (maxY - 100)) {
            vy = 0
        }

        detectColision.left = x
        detectColision.top = y
        detectColision.right = x + bitmap.width
        detectColision.bottom = y+ bitmap.height
    }
}