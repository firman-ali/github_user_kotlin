package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    companion object{
        private val TAB_TITLES = arrayOf(
            "${R.string.tab_text_1}",
            "${R.string.tab_text_2}"
        )
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return UserConnectionFragment.newInstance(TAB_TITLES[position])
    }
}