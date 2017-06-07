package com.bessttcom.xmenw.kotlinz.UI.Main.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log

/**
 * Created by xmenw on 24.05.2017.
 */
class ViewPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    private val mFragmentTitleList: ArrayList<String> = ArrayList()
    private val map: HashMap<Int, Fragment> = HashMap()

    override fun getItem(p0: Int): Fragment {
        return map[p0]!!
    }

    override fun getCount(): Int {
        return map.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mFragmentTitleList[position]
    }

    fun addFragment(position: Int, fragment: Fragment, title: String) {
        map.put(position, fragment)
        mFragmentTitleList.add(title)
    }

    fun replace (position: Int, fragment: Fragment) {
        Log.d("QWERT", "asdfgh")
        map.put(position, fragment)
        notifyDataSetChanged()
    }
}