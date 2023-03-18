package com.lifeisadream.whistleapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var mediaplayer: MediaPlayer? = null
    private lateinit var handler:Handler
    private lateinit var runnable:Runnable
    private lateinit var seekbar: SeekBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekbar = findViewById(R.id.seekBar)
        handler=Handler(Looper.getMainLooper())
        val pButton = findViewById<FloatingActionButton>(R.id.playbtnn)
        pButton.setOnClickListener {
            if (mediaplayer == null) {
                mediaplayer = MediaPlayer.create(this, R.raw.lifeisadream)
            }
            mediaplayer?.start()
            intilialiseSeekBar()

        }
        val paubutton = findViewById<FloatingActionButton>(R.id.pausebtnn)
        paubutton.setOnClickListener {
            mediaplayer?.pause()
        }
        val sbutton = findViewById<FloatingActionButton>(R.id.stopbtnn)
        sbutton.setOnClickListener {
            mediaplayer?.stop()
            mediaplayer?.reset()
            mediaplayer?.release()
            mediaplayer = null
            handler.removeCallbacks(runnable)
            seekbar.progress=0

        }


    }

    private fun intilialiseSeekBar() {
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) mediaplayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        }
        )
        val tvprog=findViewById<TextView>(R.id.tv_prog)
        val tvduee=findViewById<TextView>(R.id.tv_due)

        seekbar.max=mediaplayer!!.duration
        runnable=Runnable{
            seekbar.progress=mediaplayer!!.currentPosition
            val progtime=mediaplayer!!.currentPosition/1000
            tvprog.text="$progtime sec"
            val dur=mediaplayer!!.duration/1000
            val due=dur-progtime
            tvduee.text="$due sec"
            handler.postDelayed(runnable,1000)

        }
        handler.postDelayed(runnable,1000)
    }
}
