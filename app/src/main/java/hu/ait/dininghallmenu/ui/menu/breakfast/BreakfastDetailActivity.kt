package hu.ait.dininghallmenu.ui.menu.breakfast

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.taufiqrahman.reviewratings.BarLabels
import hu.ait.dininghallmenu.databinding.ActivityBreakfastDetailBinding
import kotlin.random.Random

class BreakfastDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBreakfastDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreakfastDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var ratingReviews = binding.ratingReviews

        var colors = IntArray(5)
        colors[0] = Color.parseColor("#0e9d58")
        colors[1] = Color.parseColor("#bfd047")
        colors[2] = Color.parseColor("#ffc105")
        colors[3] = Color.parseColor("#ef7e14")
        colors[4] = Color.parseColor("#d36259")

        var raters = IntArray(5)
        raters[0] = Random.nextInt(100)
        raters[1] = Random.nextInt(100)
        raters[2] = Random.nextInt(100)
        raters[3] = Random.nextInt(100)
        raters[4] =Random.nextInt(100)

        ratingReviews.createRatingBars(100, BarLabels.STYPE1, colors, raters)

    }
}