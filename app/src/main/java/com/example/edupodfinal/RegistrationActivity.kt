package com.example.edupodfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.edupodfinal.adapters.PagerAdapter
import com.example.edupodfinal.databinding.ActivityRegistrationBinding
import com.example.edupodfinal.registration_fragments.PrincipleRegFragment
import com.example.edupodfinal.registration_fragments.StudentRegFragment
import com.example.edupodfinal.registration_fragments.TeacherRegFragmet01
import com.google.android.material.tabs.TabLayoutMediator

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragments = ArrayList<Fragment>()
        fragments.add(TeacherRegFragmet01())
        fragments.add(StudentRegFragment())
        fragments.add(PrincipleRegFragment())

        val titles = ArrayList<String>()
        titles.add("Teachers")
        titles.add("Students")
        titles.add("Principles")

        val resultBundle = Bundle()

        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )

        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()


    }
}