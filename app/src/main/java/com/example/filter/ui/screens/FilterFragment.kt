package com.example.filter.ui.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datascource.realm.category.SubCatRealm
import com.example.datascource.realm.filter.FieledRealm
import com.example.filter.databinding.FragmentFilterBinding
import com.example.filter.ui.MainViewModel
import com.example.filter.ui.adapters.nestead.ParentAdapter
import com.example.filter.ui.adapters.sub.SubCategoryAdapter
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FilterFragment : Fragment() {
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentFilterBinding
    private lateinit var mAdapter: ParentAdapter
    private lateinit var rlmRsltList: RealmList<FieledRealm>
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

            try {
                rlmRsltList= it.fieldsList
                setUpRecyclerView()

            } catch (e: Exception) {
                Log.e("WORKINDB", "no data" + e.message)
            }
        }
    }

    private fun setUpRecyclerView() {
        if (rlmRsltList.isNotEmpty()) {
            mAdapter = ParentAdapter(rlmRsltList) { id ->
              //handle Selected Items

            }


            lifecycleScope.launch(Dispatchers.Main) {
                binding.RcyclerFilter.adapter = mAdapter
                binding.RcyclerFilter .layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }



}
