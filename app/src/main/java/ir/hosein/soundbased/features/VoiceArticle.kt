package ir.hosein.soundbased.features

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.android.material.slider.Slider
import ir.hosein.soundbased.ApiManager.model.ArticleData
import ir.hosein.soundbased.R
import ir.hosein.soundbased.databinding.ActivityVoiceArticleBinding
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

@Suppress("DEPRECATION")
class VoiceArticle : AppCompatActivity() {
    lateinit var binding: ActivityVoiceArticleBinding
    lateinit var dataThisArticle: ArticleData.ArticleDataItem
    lateinit var mediaPlayer: MediaPlayer
    lateinit var timer: Timer
    var isPlaying = true
    var isUserChanging = false
    var isMute = false
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoiceArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        dataThisArticle = intent.getParcelableExtra("dataToSend")!!
        initUi()


    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    private fun initUi() {
        binding.layoutAbout.txtAboutArticle.text = dataThisArticle.text
        binding.layoutInfo.txtNameArticle.text = dataThisArticle.subject.split(',')[0]
        binding.layoutInfo.txtDateArticle.text = dataThisArticle.subject.split(',')[1]
        binding.layoutInfo.txtAutherName.text = dataThisArticle.subject.split(',')[2]
        binding.layoutInfo.txtNumberArticle.text = "Article" + dataThisArticle.subject.split(',')[3]

        Glide
            .with(this)
            .load(dataThisArticle.picUrl)
            .into(binding.layoutInfo.imgMain)

        prepareMusic()
        binding.layoutBottom.btPlayPause.setOnClickListener {
            configureMusic()
        }
        binding.layoutBottom.btGoAfter.setOnClickListener {
            goAfterMusic()
        }
        binding.layoutBottom.btGoBefore.setOnClickListener {
            goBeforeMusic()
        }
        binding.layoutBottom.btVolume.setOnClickListener {
            volumeMusic()
        }

        binding.layoutSlider.sliderMain.addOnChangeListener { slider, value, fromUser ->

            binding.layoutSlider.txtLeft.text = convertMillisToString(value.toLong())
            isUserChanging = fromUser
        }

        binding.layoutSlider.sliderMain.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {

            }

            override fun onStopTrackingTouch(slider: Slider) {
                mediaPlayer.seekTo(slider.value.toInt())
            }

        })


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun volumeMusic() {
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        isMute = if (isMute) {

            audioManager.adjustVolume(AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI)
            binding.layoutBottom.btVolume.setImageResource(R.drawable.ic_volume_on)
            false


        } else {

            audioManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI)
            binding.layoutBottom.btVolume.setImageResource(R.drawable.ic_volume_off)
            true

        }
    }

    private fun goBeforeMusic() {
        val now = mediaPlayer.currentPosition
        val newValue = now - 15000
        mediaPlayer.seekTo(newValue)
    }

    private fun goAfterMusic() {
        val now = mediaPlayer.currentPosition
        val newValue = now + 15000
        mediaPlayer.seekTo(newValue)
    }

    private fun configureMusic() {

        mediaPlayer.start()


        if (isPlaying) {
            mediaPlayer.pause()
            binding.layoutBottom.btPlayPause.setImageResource(R.drawable.ic_play)
            isPlaying = false
        } else {

            mediaPlayer.start()
            binding.layoutBottom.btPlayPause.setImageResource(R.drawable.ic_pause)
            isPlaying = true
        }
    }

    private fun prepareMusic() {
        val voiceUrl = dataThisArticle.voiceUrl
        mediaPlayer = MediaPlayer.create(this, Uri.parse(voiceUrl))

        isPlaying = false
        binding.layoutBottom.btPlayPause.setImageResource(R.drawable.ic_play)

        binding.layoutSlider.sliderMain.valueTo = mediaPlayer.duration.toFloat()
        binding.layoutSlider.txtRight.text = convertMillisToString(mediaPlayer.duration.toLong())


        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    binding.layoutSlider.sliderMain.value = mediaPlayer.currentPosition.toFloat()
                }
            }
        }, 1000, 1000)

    }

    private fun convertMillisToString(duration: Long): String {

        val second = duration / 1000 % 60
        val minute = duration / (1000 * 60) % 60

        return java.lang.String.format(Locale.US, "%02d:%02d", minute, second)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true

    }

    override fun onBackPressed() {
        super.onBackPressed()
        mediaPlayer.pause()
    }

}