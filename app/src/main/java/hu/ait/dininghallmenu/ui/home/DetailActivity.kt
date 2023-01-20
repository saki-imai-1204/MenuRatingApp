package hu.ait.dininghallmenu.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.dininghallmenu.databinding.ActivityDetailBinding
import hu.ait.dininghallmenu.ui.home.data.DiningHall

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var diningHall: DiningHall


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var extras = intent.extras
        if (extras != null) {
            val value = extras.getString("key")
        }

        val queryItems = FirebaseFirestore.getInstance().collection(
            HomeFragment.COLLECTION_ITEMS
        )

        binding.webView.getSettings().setBuiltInZoomControls(true)
        binding.webView.loadUrl(
            "https://colby.cafebonappetit.com/cafe/dana/")


        //var resourceId = context?.resources?.getIdentifier(diningHall.name, "drawable", context?.packageName)
    }


}