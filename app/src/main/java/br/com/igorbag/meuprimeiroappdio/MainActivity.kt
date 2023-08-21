package br.com.igorbag.meuprimeiroappdio

import android.animation.ValueAnimator
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import br.com.igorbag.meuprimeiroappdio.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var textToType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        textToType = resources.getString(R.string.hello_world)

        startTypingAnimation()

        startBlinkingCursor()

    }

    private fun startTypingAnimation() {
        val textView = binding.textHello
        val customFont = ResourcesCompat.getFont(this, R.font.arapey)
        textView.typeface = customFont
        val cursorTextView = binding.cursorTextView

        val textToTypeLength = textToType.length

        val animator = ValueAnimator.ofInt(0, textToTypeLength)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = (textToTypeLength * 300).toLong()

        animator.addUpdateListener { animation ->
            val currentTextLength = animation.animatedValue as Int
            textView.text = textToType.substring(0, currentTextLength)
            if (currentTextLength == textToTypeLength) {
                cursorTextView.visibility = View.VISIBLE
                startBlinkingCursor()
            }
        }

        animator.start()
    }

    private fun startBlinkingCursor() {
        val cursorTextView = binding.cursorTextView
        val customFont = ResourcesCompat.getFont(this,R.font.arapey)
        cursorTextView.typeface = customFont

        val cursorTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (cursorTextView.text == "|") {
                    cursorTextView.text = " "
                } else {
                    cursorTextView.text = "|"
                }
            }

            override fun onFinish() {
            }
        }
        cursorTimer.start()
    }
}