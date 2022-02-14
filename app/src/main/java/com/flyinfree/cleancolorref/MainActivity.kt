package com.flyinfree.cleancolorref

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.SeekBar
import androidx.annotation.ColorInt
import com.flyinfree.cleancolorref.databinding.ActivityMainBinding
import java.util.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private lateinit var binding: ActivityMainBinding

    private var redComponent: Int = 0x66
        set(value) {
            field = value
            currentColor = Color.argb(alphaComponent, value, greenComponent, blueComponent)
        }

    private var greenComponent: Int = 0xCC
        set(value) {
            field = value
            currentColor = Color.argb(alphaComponent, redComponent, value, blueComponent)
        }

    private var blueComponent: Int = 0xFF
        set(value) {
            field = value
            currentColor = Color.argb(alphaComponent, redComponent, greenComponent, value)
        }

    private var alphaComponent: Int = 0xFF
        set(value) {
            field = value
            currentColor = Color.argb(value, redComponent, greenComponent, blueComponent)
        }

    @ColorInt
    private var currentColor: Int =
        Color.argb(alphaComponent, redComponent, greenComponent, blueComponent)
        set(value) {
            field = value
            binding.colorView.setBackgroundColor(currentColor)
            binding.argbColorLabel.text = String
                .format(Locale.US, "#%02x%02x%02x%02x",
                    alphaComponent, redComponent, greenComponent, blueComponent)
                .uppercase(Locale.US)
            binding.rgbaColorLabel.text = String
                .format(Locale.US, "RGBA: %02x%02x%02x%02x",
                    redComponent, greenComponent, blueComponent, alphaComponent)
                .uppercase(Locale.US)
        }

    private fun updateSeekBars() {
        binding.redSeekBar.setProgress((redComponent.toFloat() / 255.0 * 100.0).roundToInt(), true)
        binding.greenSeekBar.setProgress((greenComponent.toFloat() / 255.0 * 100.0).roundToInt(), true)
        binding.blueSeekBar.setProgress((blueComponent.toFloat() / 255.0 * 100.0).roundToInt(), true)
        binding.alphaSeekBar.setProgress((alphaComponent.toFloat() / 255.0 * 100.0).roundToInt(), true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentColor = currentColor // Trigger UI init by calling setter
        updateSeekBars()

        binding.redSeekBar.setOnSeekBarChangeListener(this)
        binding.greenSeekBar.setOnSeekBarChangeListener(this)
        binding.blueSeekBar.setOnSeekBarChangeListener(this)
        binding.alphaSeekBar.setOnSeekBarChangeListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when (seekBar) {
            binding.redSeekBar -> redComponent = (progress.toFloat() / 100.0 * 255.0).roundToInt()
            binding.greenSeekBar -> greenComponent = (progress.toFloat() / 100.0 * 255.0).roundToInt()
            binding.blueSeekBar -> blueComponent = (progress.toFloat() / 100.0 * 255.0).roundToInt()
            binding.alphaSeekBar -> alphaComponent = (progress.toFloat() / 100.0 * 255.0).roundToInt()
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}