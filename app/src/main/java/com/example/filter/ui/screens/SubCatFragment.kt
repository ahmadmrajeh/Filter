package com.example.filter.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filter.databinding.FragmentSubCatBinding

import com.example.datascource.realm.category.SubCatRealm
import com.example.filter.R
import com.example.filter.ui.adapters.sub.SubCategoryAdapter
import com.example.filter.ui.screens.viewmodel.MainViewModel
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubCatFragment : Fragment() {
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var mAdapter: SubCategoryAdapter
    private lateinit var binding: FragmentSubCatBinding
    private lateinit var rlmRsltList: RealmList<SubCatRealm>
    private val args: SubCatFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubCatBinding.inflate(inflater)



        requireActivity().title="Select Subcategory"

        sharedViewModel.readOfflineCacheCategoriesAndSub()
        observeData()

        return binding.root

    }

    private fun observeData() {
        sharedViewModel.result.observe(viewLifecycleOwner) { resultCatRealm ->
            resultCatRealm.items[args.id]?.let {
                rlmRsltList = it.subCategories
            }
            setUpRecyclerView()
        }
    }


    private fun setUpRecyclerView() {
        if (rlmRsltList.isNotEmpty()) {
            mAdapter = SubCategoryAdapter(rlmRsltList) { id ->
                  findNavController().navigate(
                       SubCatFragmentDirections.actionSubCatFragmentToFilterFragment2(id) )
            }


            lifecycleScope.launch(Dispatchers.Main) {
                binding.subCategoryRecycler.adapter = mAdapter
                binding.subCategoryRecycler.layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
}