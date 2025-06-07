package com.example.planerslubny

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.planerslubny.databinding.FragmentTwoBinding

class FragmentTwo : Fragment() {

    private lateinit var binding: FragmentTwoBinding
    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_two, container, false)
        var arrayAdapter: ArrayAdapter<*>
        val args = FragmentTwoArgs.fromBundle(requireArguments())
        var guests = args.guestList?.toList()
        var users: Array<String> = arrayOf()
        listView = binding.lista


        if (guests != null) {
            for (i in guests) {
                users += i
                arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, users)
                listView.adapter = arrayAdapter
                Log.d("wiadomosc","${i}")
            }
        }

        binding.button6.setOnClickListener{
            var name = binding.editTextText.text.toString()
            var surname = binding.editTextText2.text.toString()
            users += "${name} ${surname}"

            if(name.isNotBlank() and surname.isNotBlank()){
                arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, users)
                listView.adapter = arrayAdapter

            }
        }

        binding.button4.setOnClickListener{
            findNavController().navigate(FragmentTwoDirections.actionFragmentTwoToFragmentFirst(users))
        }

        return binding.root    }
}