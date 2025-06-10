package com.example.planerslubny

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.planerslubny.databinding.DialogBinding
import com.example.planerslubny.databinding.FragmentTwoBinding


class FragmentTwo : Fragment() {

    private lateinit var binding: FragmentTwoBinding
    private lateinit var binding2: DialogBinding
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

            if(name.isNotBlank() and surname.isNotBlank()){
                if(binding.switch1.isChecked){
                    users += "${name} ${surname}"
                    users += "${name} ${surname}+1"
                    Toast.makeText(requireContext(),"Pomyślnie dodano gościa ${users[users.size-2]} z partnerem",Toast.LENGTH_SHORT).show()
                    arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, users)
                    listView.adapter = arrayAdapter
                } else {
                    users += "${name} ${surname}"
                    Toast.makeText(requireContext(),"Pomyślnie dodano gościa ${users[users.lastIndex]}",Toast.LENGTH_SHORT).show()
                    arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, users)
                    listView.adapter = arrayAdapter
                }


            }
        }
        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                Log.d("msg","Pozycja: ${position}")
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Usuwanie gości")
                val customLayout: View = layoutInflater.inflate(R.layout.dialog, null)
                builder.setView(customLayout)
                Log.d("msg","pozycja${position}, array ${users[position]}")

                builder.setPositiveButton("Usuń") { dialog, _ ->
                    Toast.makeText(requireContext(),"Pomyślnie usunięto gościa ${users?.get(position)}",Toast.LENGTH_SHORT).show()
                    users = users.filterIndexed { idx, _ -> idx != position }.toTypedArray()
                    Log.d("msg","pozycja${position}, array ${users?.toList()}")
                    arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, users)
                    listView.adapter = arrayAdapter
                    dialog.dismiss()
                }
                builder.setNegativeButton("Anuluj") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.create().show()


            };

        binding.button4.setOnClickListener{
            findNavController().navigate(FragmentTwoDirections.actionFragmentTwoToFragmentFirst(users))
        }

        return binding.root    }



    }

