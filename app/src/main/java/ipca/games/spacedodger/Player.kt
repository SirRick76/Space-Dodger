package ipca.games.spacedodger

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.style.LineHeightSpan
import android.view.Gravity
import android.graphics.Rect

class Player {

    var bitmap: Bitmap
    var x: Float
    var y: Float
    var speed: Int = 0

    var boostingl = false
    var boostingr = false

    var maxX: Float
    var minX: Float


    var detectColision = Rect()

    constructor(context: Context, screenWidth: Int, screenHeight: Int) {
        x = screenWidth / 2f
        y = screenHeight - 500f
        speed = 1
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.img_3)

        minX = 0f
        maxX = (screenWidth - bitmap.width).toFloat()

        detectColision = Rect(x.toInt(), y.toInt(), bitmap.width, bitmap.height)
    }

    fun updatel() {
        if (boostingl) {
            speed = 10


            x -= speed

            if (x < minX) x = minX
            if (x > maxX) x = maxX

        }
        if (boostingr) {
            speed = 10

            x += speed

            if (x < minX) x = minX
            if (x > maxX) x = maxX

            detectColision.left = x.toInt()
            detectColision.top = y.toInt()
            detectColision.right = x.toInt() + bitmap.width
            detectColision.bottom = y.toInt() + bitmap.height
        }


    }
}







