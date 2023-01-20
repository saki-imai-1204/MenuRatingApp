package hu.ait.dininghallmenu.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import hu.ait.dininghallmenu.R
import hu.ait.dininghallmenu.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
//            Toast.makeText(context, "Selected position: ${position}",
//                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        super.onCreate(savedInstanceState)
        val homeViewModel =
            ViewModelProvider(this).get(MenuViewModel::class.java)
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val myViewPagerAdapter = ViewPagerAdapter(this, 3)
        binding.mainViewPager.adapter = myViewPagerAdapter

        binding.mainViewPager.registerOnPageChangeCallback(pageChangeCallback)

        var pageNames: Array<String> = resources.getStringArray(R.array.tab_names)
        TabLayoutMediator(binding.tabLayout, binding.mainViewPager) { tab, position ->
            tab.text = pageNames[position]
        }.attach()

        //mainViewPager.setPageTransformer(ZoomOutPageTransformer())
        //mainViewPage.setPageTransformer(DepthPageTransformer())
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //binding.mainViewPager.unregisterOnPageChangeCallback(pageChangeCallback)
        _binding = null
    }
}