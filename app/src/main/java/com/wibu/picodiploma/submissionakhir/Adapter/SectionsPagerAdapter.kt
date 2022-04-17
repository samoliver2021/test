package com.wibu.picodiploma.submissionakhir.Adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wibu.picodiploma.submissionakhir.Tab.FollowerFragment
import com.wibu.picodiploma.submissionakhir.Tab.FollowingFragment
import com.wibu.picodiploma.submissionakhir.R

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private lateinit var binding: SectionsPagerAdapter
    var setUsername : String? = null



    private val TAB_TITLE = intArrayOf(R.string.tab_1, R.string.tab_2)


    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = FollowingFragment.newInstance(setUsername.toString())


            1 -> fragment = FollowerFragment.newInstance(setUsername.toString())


        }
        return fragment as Fragment


    }




    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLE[position])


    }

    fun setUsername(usernametab: String) {
        this.setUsername = usernametab

    }




}