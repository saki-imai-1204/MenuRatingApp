package hu.ait.dininghallmenu.ui.menu

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.ait.dininghallmenu.ui.menu.breakfast.BreakfastFragment
import hu.ait.dininghallmenu.ui.menu.lunch.LunchFragment
import hu.ait.dininghallmenu.ui.menu.dinner.DinnerFragment

// An equivalent ViewPager2 adapter class
class ViewPagerAdapter(fa: Fragment, val itemsCount: Int) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            BreakfastFragment.newInstance()
        } else if (position == 1) {
            LunchFragment.newInstance()
        } else {
            DinnerFragment.newInstance()
        }
    }
}