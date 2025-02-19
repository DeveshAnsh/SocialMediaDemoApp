package com.example.socialmediademo.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.socialmediademo.Post.PostActivity
import com.example.socialmediademo.Post.ReelsActivity
import com.example.socialmediademo.R
import com.example.socialmediademo.databinding.FragmentAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class addFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)
        binding.post.setOnClickListener{

            activity?.startActivity(Intent(requireContext(),PostActivity::class.java))
            activity?.finish()


        }
        binding.reel.setOnClickListener{
            activity?.startActivity(Intent(requireContext(),ReelsActivity::class.java))
            activity?.finish()
        }



        return binding.root
    }

    companion object {

    }
}
