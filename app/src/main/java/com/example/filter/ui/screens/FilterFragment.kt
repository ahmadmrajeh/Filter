package com.example.filter.ui.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.filter.databinding.FragmentFilterBinding
import com.example.filter.ui.MainViewModel


class FilterFragment : Fragment() {
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentFilterBinding
    private val args: FilterFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragmentFilterBinding.inflate(inflater)
        Log.e("WORKINDB", args.id.toString())
        sharedViewModel.subFlowJsonToKotlin(requireContext().applicationContext, args.id)
        sharedViewModel.readOfflineCacheFields(args.id)
        observeData()
        return binding.root
    }


    private fun observeData() {

        sharedViewModel.resultFilter.observe(viewLifecycleOwner) {
               Log.e("WORKINDB", "here")
            try {


            } catch (e: Exception) {
                Log.e("WORKINDB", "no data" + e.message)
            }

        }
    }

}
