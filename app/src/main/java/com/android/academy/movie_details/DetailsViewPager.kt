package com.android.academy.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.android.academy.model.MoviesContent
import com.android.academy.R

class DetailsViewPager : Fragment() {

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

    private lateinit var mViewPager: ViewPager2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.view_pager_details, container, false)
        mViewPager = view.findViewById(R.id.details_pager)
        mViewPager.adapter = MovieDetailsPagerAdapter(this)

        val currentIndex: Int? = arguments?.getInt(INDEX_BUNDLE_KEY)
        currentIndex?.let {
            mViewPager.setCurrentItem(it, false)
        }

        return view
    }

    fun moveToItem(index: Int) {
        mViewPager.setCurrentItem(index, true)
    }

    private inner class MovieDetailsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = MoviesContent.getCount()

        override fun createFragment(position: Int): Fragment =
            DetailsFragment.newInstance(MoviesContent.getMovie(position))

    }

}