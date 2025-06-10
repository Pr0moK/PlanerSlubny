package com.example.planerslubny

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.planerslubny.databinding.FragmentThreeBinding
import androidx.recyclerview.widget.LinearLayoutManager



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
        val args = FragmentThreeArgs.fromBundle(requireArguments())
        var guestdata = args.guestList?.toList()
        var tabledata = args.tableList?.toList()
        var tablecount = args.tableCount
        var savedguest = args.sortedGuest
        var guestarray: Array<String> = arrayOf()
        var tablearray: Array<String> = arrayOf("Stół 1")
        val sittedguest = mutableMapOf<String, Int>()
        var sortedguests: Array<String?> = arrayOfNulls(tablecount)
        Log.d("Count","${sortedguests.size}")
        var adapter = GoscAdapter.GoscAdapter(sortedguests)


        spinner = binding.spinner
        spinner2 = binding.spinner2


        if (guestdata != null) {
            for(i in guestdata){
                guestarray += i
                Log.d("wiadomosc", "${i}")
            }
        }
        if (tablearray.size != tablecount){
            for(i in (tablearray.size+1..tablecount)){
                tablearray += "Stół ${i}"
                Log.d("count", "Liczymy ${i}")

            }
        }

        if(savedguest != null){
            val map = savedguest.associate {
                val (key, value) = it.split(",")
                key to value.toInt()
            }
            sittedguest.putAll(map)
            Log.d("msg","${map.toMap()}")
        }


        tabledata = tabledata?.map { ((if (it == "null") null else it).toString())}

        if (tabledata != null) {
            sortedguests = arrayOfNulls(tablearray.size)
            for (i in tabledata.indices) {
                if(tabledata[i] != "null") {
                    sortedguests[i] = tabledata[i]
                    Log.d("wiadomosc", "Załadowano przypisanych gości do stołów: ${tabledata[i]}")
                }
            }
            Log.d("wiadomosc", "Załadowano przypisanych gości do stołów: ${sortedguests.toList()}")

            adapter = GoscAdapter.GoscAdapter(sortedguests)
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = adapter
        }

        adapter = GoscAdapter.GoscAdapter(sortedguests)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, guestarray)
        spinner.adapter = arrayAdapter

        arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, tablearray)
        spinner2.adapter = arrayAdapter


        binding.button8.setOnClickListener {
            var index = tablearray.size
            tablearray += "Stół ${index+1}"
            sortedguests += null
            Log.d("wiadomosc","${tablearray.toList().toString()}")
            arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, tablearray)
            spinner2.adapter = arrayAdapter
        }

        binding.button9.setOnClickListener {
            if(tablearray.size > 1) {
                var found = 0
                for((key, value ) in sittedguest){
                    if(value == tablearray.lastIndex){
                        Toast.makeText(requireContext(),"Nie można usunąć. Goście znajdują się przy stole",
                            Toast.LENGTH_SHORT).show()
                        found = 1
                        break
                    }
                }
                if(found == 0) {
                    Toast.makeText(requireContext(),"Usunięto stół ${tablearray.lastIndex+1}",
                        Toast.LENGTH_SHORT).show()
                    tablearray = tablearray.dropLast(1).toTypedArray()
                    Log.d("wiadomosc", "${tablearray.toList().toString()}")
                    arrayAdapter =
                        ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, tablearray)
                    spinner2.adapter = arrayAdapter
                }
            }
        }

        binding.button7.setOnClickListener{
            Log.d("wiadomosc","${binding.spinner.selectedItemId}")
            if(binding.spinner.selectedItemId >= 0){
                var guestid = binding.spinner.selectedItemId
                var tableid = binding.spinner2.selectedItemId
                sortedguests = arrayOfNulls(tablearray.size)

               sittedguest.put("${guestarray[guestid.toInt()].toString()}", tableid.toInt())

                for((key, value) in sittedguest){

                    if(sortedguests[value] == null){
                        sortedguests[value] = "${key};"
                        Log.d("wiadomosc", "NULL ${key} a następnie ${value} , ${sortedguests.size}")
                        Log.d("wiadomosc", "NULL Posortowano ${sortedguests.toList().toString()}")
                        Toast.makeText(requireContext(),"Dodano gościa ${key} do stołu ${value+1}",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        sortedguests[value] += "${key};"
                        Log.d("wiadomosc", "${key} a następnie ${value} , ${sortedguests.size}")
                        Log.d("wiadomosc", "Posortowano ${sortedguests.toList().toString()}")
                        Toast.makeText(requireContext(),"Dodano gościa ${key} do stołu ${value+1}",
                            Toast.LENGTH_SHORT).show()
                    }
            }
                adapter = GoscAdapter.GoscAdapter(sortedguests)
               binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
               binding.recyclerView.adapter = adapter
            }
        }

        binding.button5.setOnClickListener{
            var array = sittedguest.map { "${it.key},${it.value}" }.toTypedArray()
            var sorttedguestnotnull = sortedguests.map { it ?: "null" }.toTypedArray()

            findNavController().navigate(FragmentThreeDirections.actionFragmentThreeToFragmentFirst(guestarray, sorttedguestnotnull,tablearray.size,array))

        }

        return binding.root    }
}