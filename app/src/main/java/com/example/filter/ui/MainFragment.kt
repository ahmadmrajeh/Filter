package com.example.filter.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filter.databinding.FragmentMainBinding
import com.example.filter.realm.CatItemRlm
import com.example.filter.ui.adapters.CategoryRecyclerViewAdapter
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainFragment : Fragment() {
    private lateinit var mAdapter: CategoryRecyclerViewAdapter
    private var rlmRsltList: RealmList<CatItemRlm>?= RealmList()
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        viewModel.catJsonToKotlin(requireActivity().applicationContext)
        viewModel.readOfflineCache()
        observeData()
        return binding.root

    }

    private fun observeData() {

        viewModel.result.observe(viewLifecycleOwner) {


             rlmRsltList = it.items


            setUpRecyclerView()

        }
    }
    private fun setUpRecyclerView() {


        if (rlmRsltList?.isNotEmpty() == true) {
            mAdapter = CategoryRecyclerViewAdapter(rlmRsltList) { id ->
              /*  findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSecondFragment(
                        id
                    )
                )
           */

            }

            lifecycleScope.launch(Dispatchers.Main) {
                binding.categoryRecycler .adapter = mAdapter
                binding.categoryRecycler.layoutManager = LinearLayoutManager(requireContext())
            }


        }


    }

}