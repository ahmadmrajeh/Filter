package com.example.filter.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filter.databinding.FragmentMainBinding


class MainFragment : Fragment() {
private lateinit var binding:FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding= FragmentMainBinding.inflate(inflater)
        viewModel.catJsonToKotlin( requireActivity().applicationContext)
        viewModel.readOfflineCache()
        observeData()
        return binding.root

    }
    private fun observeData() {

        viewModel.result.observe(viewLifecycleOwner) {


           // rlmRsltList = it


            //setUpRecyclerView()

        }
    }




}