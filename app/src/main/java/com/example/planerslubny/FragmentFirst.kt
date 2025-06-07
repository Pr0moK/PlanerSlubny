package com.example.planerslubny

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.planerslubny.databinding.FragmentFirstBinding

class FragmentFirst : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args = FragmentFirstArgs.fromBundle(requireArguments())
        var guests = args.guestList

        guests = guests?.plus("dupa")
        Log.d("wiadomosc", "${guests?.toList().toString()}")



        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        binding.button.setOnClickListener {
            findNavController().navigate(FragmentFirstDirections.actionFragmentFirstToFragmentTwo(guests))
        }

        binding.button2.setOnClickListener {
            findNavController().navigate(FragmentFirstDirections.actionFragmentFirstToFragmentThree(guests))
        }

        return binding.root
        }
}