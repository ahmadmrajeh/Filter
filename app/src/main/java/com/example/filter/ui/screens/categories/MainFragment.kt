package com.example.filter.ui.screens.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datascource.realm.category.CatItemRlm
import com.example.filter.databinding.FragmentMainBinding
import com.example.filter.ui.adapters.categoryAdapter.CategoryRecyclerViewAdapter
import com.example.filter.ui.screens.viewmodel.MainViewModel
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private lateinit var mAdapter: CategoryRecyclerViewAdapter
    private var rlmRsltList: RealmList<CatItemRlm>? = RealmList()
    private lateinit var binding: FragmentMainBinding

    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        requireActivity().title = "Select Category"
        binding = FragmentMainBinding.inflate(inflater)
        sharedViewModel.readOfflineCacheCategoriesAndSub()
        observeData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    private fun observeData() {
        sharedViewModel.result.observe(viewLifecycleOwner) {
            rlmRsltList = it.items
            setUpRecyclerView()
        }
    }

    private fun setUpRecyclerView() {
        if (rlmRsltList?.isNotEmpty() == true) {
            mAdapter = CategoryRecyclerViewAdapter(rlmRsltList) { id ->
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToSubCatFragment(id)
                )
            }
            lifecycleScope.launch(Dispatchers.Main) {
                binding.categoryRecycler.adapter = mAdapter
                binding.categoryRecycler.layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
}