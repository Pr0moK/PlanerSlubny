package com.example.planerslubny

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.planerslubny.databinding.FragmentThreeBinding


class FragmentThree : Fragment() {

    private lateinit var binding: FragmentThreeBinding
    private lateinit var spinner: Spinner
    private lateinit var spinner2: Spinner


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_three, container, false)

        var arrayAdapter: ArrayAdapter<*>
        // Inflate the layout for this fragment
        val args = FragmentThreeArgs.fromBundle(requireArguments())
        var guestdata = args.guestList?.toList()
        var guestarray: Array<String> = arrayOf()
        var tablearray: Array<String> = arrayOf("Stół 1")
        spinner = binding.spinner
        spinner2 = binding.spinner2


        if (guestdata != null) {
            for(i in guestdata){
                guestarray += i
                Log.d("wiadomosc", "${i}")
            }
        }

        arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, guestarray)
        spinner.adapter = arrayAdapter

        arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, tablearray)
        spinner2.adapter = arrayAdapter

        binding.button8.setOnClickListener {
            var index = tablearray.size
            tablearray += "Stół ${index+1}"
            Log.d("wiadomosc","${tablearray.toList().toString()}")
            arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, tablearray)
            spinner2.adapter = arrayAdapter
        }

        binding.button9.setOnClickListener {
            Log.d("chuj", "${tablearray is Array<*>}")
            if(tablearray.size > 1) {
                tablearray = tablearray.dropLast(1).toTypedArray()
                Log.d("wiadomosc", "${tablearray.toList().toString()}")
                arrayAdapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, tablearray)
                spinner2.adapter = arrayAdapter
            }
        }

        binding.button5.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentThree_to_fragmentFirst)
        }

        return binding.root    }
}