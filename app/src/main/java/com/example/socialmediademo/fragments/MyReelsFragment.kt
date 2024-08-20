package com.example.socialmediademo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.socialmediademo.Models.Post
import com.example.socialmediademo.Models.Reel
import com.example.socialmediademo.R
import com.example.socialmediademo.adapters.MyPostRvAdapter
import com.example.socialmediademo.adapters.MyReelAdapter
import com.example.socialmediademo.databinding.FragmentMyReelsBinding
import com.example.socialmediademo.utils.REEL
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class MyReelsFragment : Fragment() {
    private lateinit var binding:FragmentMyReelsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
    binding= FragmentMyReelsBinding.inflate(inflater, container, false)
        var reelList=ArrayList<Reel>()
        var adapter= MyReelAdapter(requireContext(), reelList)
        binding.rv.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter=adapter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REEL).get().addOnSuccessListener {
            var tempList= arrayListOf<Reel>()
            for (i in it.documents){
                var reel: Reel =i.toObject<Reel>()!!
                tempList.add(reel)
            }
            reelList.addAll(tempList)
            adapter.notifyDataSetChanged()

        }

        return binding.root
    }

    companion object {

    }
}