package ipca.games.spacedodger

import android.content.Context
import android.graphics.Bitmap
import java.util.Random

class Star {

    var x : Float
    var y : Float

    var speed : Int = 0

    var maxY : Float
    var minY : Float
    var maxX : Float
    var minX : Float

    constructor(context: Context, screenWidth : Int, screenHeight : Int) {
        maxX = screenWidth.toFloat()
        minX = 0F
        maxY = screenHeight.toFloat()
        minY = 0F

        var generator = Random()
        speed = generator.nextInt(10)

        x = generator.nextFloat()*maxX
        y = generator.nextFloat()*maxY

    }

    fun starWidth() : Float {
        var generator = Random()
        return 1f + generator.nextFloat()*20
    }

    fun update () {
        y += speed


        if(y<0) {
            y = maxY
            var generator = Random()
            speed = generator.nextInt(10)
            x = generator.nextFloat()*maxX
        }

    }

}
