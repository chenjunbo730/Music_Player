package com.example.mymusicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // 歌曲列表 Dallista
    private val songList = listOf(
        R.raw.song1,
        R.raw.song2,
        R.raw.song3
    )

    private var currentSongIndex = 0
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPlayPause = findViewById<Button>(R.id.btn_play_pause)
        val btnPrevious = findViewById<Button>(R.id.btn_previous)
        val btnNext = findViewById<Button>(R.id.btn_next)
        val btnRegisterLogin = findViewById<Button>(R.id.btn_register_login)

        initMediaPlayer()

        // 点击播放/暂停 Kattintson a lejátszáshoz/szünethez
        btnPlayPause.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                // 如果正在播放，则暂停 Ha játszik, szünet
                mediaPlayer?.pause()
            } else {
                // 如果暂停或初始，则开始播放 Ha szünetel vagy inicializálva van, kezdje el a lejátszást
                mediaPlayer?.start()
            }
        }

        // 上一首 Előző dal
        btnPrevious.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null

            // 往前一首，如果已经是第一个，就到最后一个 Ugrás az előző dalra, ha ez az első, akkor lépjen az utolsóra.
            currentSongIndex = if (currentSongIndex > 0) {
                currentSongIndex - 1
            } else {
                songList.size - 1
            }

            // 重新初始化并开始播放 Inicializálja újra és kezdje el a játékot
            initMediaPlayer()
            mediaPlayer?.start()
        }

        // 下一首 Következő dal
        btnNext.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null

            // 往后一首，如果已经是最后一个，就回到第一个 Ugrás a következő dalra, ha az utolsó, menjen vissza az elsőhöz.
            currentSongIndex = (currentSongIndex + 1) % songList.size

            // 重新初始化并开始播放 Inicializálja újra és kezdje el a játékot
            initMediaPlayer()
            mediaPlayer?.start()
        }

        // 注册/登录按钮 Regisztráció/Bejelentkezés gomb
        btnRegisterLogin.setOnClickListener {
            Toast.makeText(this, "Ömmm, ez a funkció egyelőre nincs megvalósítva.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, songList[currentSongIndex])
        mediaPlayer?.setOnCompletionListener {
            // 播放完自动下一首 A következő dal automatikus lejátszása lejátszás után
            btnNext()
        }
    }

    private fun btnNext() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        currentSongIndex = (currentSongIndex + 1) % songList.size
        initMediaPlayer()
        mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null


    }
}
