package hu.ait.dininghallmenu.ui.menu.dinner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.ait.dininghallmenu.R

class DinnerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dinner, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = DinnerFragment()
    }
}