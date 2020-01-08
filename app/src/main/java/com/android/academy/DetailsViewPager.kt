package com.android.academy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class DetailsViewPager: Fragment() {

    companion object {

        private const val INDEX_BUNDLE_KEY = "unique_index_key"

        fun newInstance(index: Int): DetailsViewPager {
            val fragment = DetailsViewPager()
            val args = Bundle()
            args.putInt(INDEX_BUNDLE_KEY, index)
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.view_pager_details, container, false)
        val viewPager: ViewPager2 = view.findViewById(R.id.details_pager)
        viewPager.adapter = MovieDetailsPagerAdapter(this)

        val currentIndex: Int? = arguments?.getInt(INDEX_BUNDLE_KEY)
        currentIndex?.let {
            viewPager.setCurrentItem(it, false)
        }

        return view
    }

    private inner class MovieDetailsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = MoviesContent.getCount()

        override fun createFragment(position: Int): Fragment = DetailsFragment.newInstance(MoviesContent.getMovie(position))

    }

}