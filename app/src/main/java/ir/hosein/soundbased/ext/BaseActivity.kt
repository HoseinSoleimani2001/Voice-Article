package ir.hosein.soundbased.ext

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import ir.hosein.soundbased.R


@Suppress("DEPRECATION")
open class BaseActivity : AppCompatActivity() {

    // on back pressed =>
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransitionExit()
    }

    // finish =>
    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }

    // start Activity =>
    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransitionEnter()
    }

    // Enter =>
    private fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    // Exit =>
    private fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

}