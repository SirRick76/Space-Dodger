package ipca.games.spacedodger

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity

class GameView : SurfaceView, Runnable {

    var playing = false
    lateinit var gameThread : Thread
    var hp = 3
    var score = 0

    var screenHeight =0
    lateinit var player : Player
    var stars = arrayListOf<Star>()
    var enemies = arrayListOf<Enemy>()
    var enemiesToRemove = arrayListOf<Enemy>()


    var canvas : Canvas? = null
    lateinit var paint: Paint

    constructor(context: Context?, screenWidth : Int, screenHeight:Int) : super(context){
        init( context, screenWidth, screenHeight )
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    fun init (context : Context?, screenWidth : Int, screenHeight:Int ) {
        this.screenWidth = screenWidth
        this.screenHeight = screenHeight
        player  = Player(context!!, screenWidth, screenHeight)

        paint = Paint()

        for( index in 0..99){
            stars.add(Star(context!!, screenWidth, screenHeight))
        }

        startTimeCounter()
    }


    override fun run() {
        while (playing){
            update()
            draw()
            control()
        }
    }

    fun resume() {
        playing = true
        gameThread = Thread(this)
        gameThread.start()
    }

    fun pause() {
        playing = false
        gameThread.join()
    }

    fun update() {


        player.updatel()
        for (s in stars){
            s.update()
        }

        for (e in enemies){
            e.update()
            if (e.detectColision.intersect(player.detectColision)){
                enemiesToRemove.add(e)
                hp -= 1
                if (hp == 0)
                {
                    context.startActivity(Intent(context, MainActivity::class.java))
                }

            }
        }
        removeEnemy()
    }
    fun draw() {
        if(holder.surface.isValid){
            canvas = holder.lockCanvas()
            canvas?.drawColor(Color.BLACK)

            paint.color = Color.YELLOW
            for ( s in stars) {
                paint.strokeWidth = s.starWidth()
                canvas?.drawPoint(s.x, s.y, paint)
            }

            paint.strokeWidth = 1.0F
            paint.style = Paint.Style.STROKE

            for (e in enemies){
                canvas?.drawBitmap(e.bitmap, e.x.toFloat(), e.y.toFloat(), paint)
                paint.color = Color.GREEN

            }

            canvas?.drawBitmap(player.bitmap, player.x, player.y, paint)

            holder.unlockCanvasAndPost(canvas)
        }
    }
    fun control() {
        Thread.sleep(17)
    }

    var screenWidth = 0

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {

            when (it.action.and(MotionEvent.ACTION_MASK)) {
                MotionEvent.ACTION_UP -> {
                        player.boostingl = false
                        player.boostingr = false
                }
                else
                -> {
                    if( it.x > 0 && it.x < screenWidth/2.0){
                        player.boostingl = true}
                    if (it.x>screenWidth/2.0 && it.x< screenWidth){
                        player.boostingr = true
                    }

                }

            }
        }
        return true
    }

    fun newEnemy() {
        var x = (50..screenWidth-100).random()
        val enemi = Enemy(context!!, screenWidth, screenHeight,x)
        enemies.add(enemi)
    }

    fun startTimeCounter() {
        object : CountDownTimer(500000, 3000) {
            override fun onTick(millisUntilFinished: Long) {
                newEnemy()
            }
            override fun onFinish() {

            }
        }.start()
    }

    fun removeEnemy()
    {
        for(e in enemies)
        {
            if(e.y > screenHeight-100)
            {

                if(!enemiesToRemove.contains(e))
                {
                    enemiesToRemove.add(e)
                    score+=10
                }
            }
        }
            enemies.removeAll(enemiesToRemove)
    }
}