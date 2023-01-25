package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playButton = findViewById<Button>(R.id.playButton)
        val pauseButton = findViewById<FloatingActionButton>(R.id.pauseButton)
        val stopButton = findViewById<FloatingActionButton>(R.id.stopButton)

        playButton.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.song)
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
        }
    }
}