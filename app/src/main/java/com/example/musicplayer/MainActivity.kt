package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer ?= null
    private lateinit var seekBar: SeekBar
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playButton = findViewById<FloatingActionButton>(R.id.playButton)
        val pauseButton = findViewById<FloatingActionButton>(R.id.pauseButton)
        val stopButton = findViewById<FloatingActionButton>(R.id.stopButton)
        seekBar = findViewById(R.id.seekBar)
        handler = Handler(Looper.getMainLooper())

        playButton.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.song)
                handleSeekBar()
            }
            mediaPlayer?.start()
        }

        pauseButton.setOnClickListener {
            mediaPlayer?.pause()
        }

        stopButton.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
            handler.removeCallbacks(runnable)
            seekBar.progress = 0
        }
    }

    private fun handleSeekBar() {
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        val playedTime = findViewById<TextView>(R.id.playTime)
        val dueTime = findViewById<TextView>(R.id.dueTime)
        seekBar.max = mediaPlayer!!.duration

        runnable = Runnable {
            // !! -> checks not null
            seekBar.progress = mediaPlayer!!.currentPosition

            val time = (mediaPlayer!!.currentPosition) / 60000.0
            val timeText = String.format("%.2f", time)
            playedTime.text = "$timeText min"

            val remainTime = (mediaPlayer!!.duration / 60000.0) - time
            val remainText = String.format("%.2f", remainTime)
            dueTime.text = "$remainText min"

            handler.postDelayed(runnable, 500)
        }
        handler.postDelayed(runnable, 500)
    }
}