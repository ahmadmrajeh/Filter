package com.example.filter.ui.screens.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datascource.realm.filter.FieledRealm
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.databinding.FragmentFilterBinding
import com.example.filter.ui.adapters.nestead.ParentAdapter
import com.example.filter.ui.screens.viewmodel.MainViewModel
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Suppress("UNCHECKED_CAST")
class FilterFragment : Fragment() {

    private   var realmLiveOptions: RealmList<RealmOption> = RealmList()
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentFilterBinding
    private lateinit var mAdapter: ParentAdapter
    private lateinit var rlmRstList: RealmList<FieledRealm>
    private val args: FilterFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(inflater)
        requireActivity().title = "Filter"
        sharedViewModel.subFlowJsonToKotlin(
            requireContext().applicationContext, args.id
        )
        sharedViewModel.readOfflineCacheFields(args.id)
        sharedViewModel.selectedOptions.value = RealmList()
        observeData()
        return binding.root
    }

    private fun observeData() {
        sharedViewModel.resultFilter.observe(viewLifecycleOwner) {
            rlmRstList = it.fieldsList
            setUpRecyclerView()
         }
       sharedViewModel.selectedOptions.observe(viewLifecycleOwner){selected: RealmList<RealmOption> ->
            realmLiveOptions = selected
        }
    }

    private fun setUpRecyclerView()    {
        if (rlmRstList.isNotEmpty()) {
            mAdapter = parentAdapterInstance(rlmRstList,realmLiveOptions)
            lifecycleScope.launch(Dispatchers.Main) {
                binding.RcyclerFilter.adapter = mAdapter
                binding.RcyclerFilter.layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun parentAdapterInstance(
        data: RealmList<FieledRealm>,
        realmLiveOptions: RealmList<RealmOption>
    ):
            ParentAdapter {
        return ParentAdapter(data, listOf(
            { obj ->
                //grid
                handleOptionPressed(obj)

            }, { obj ->
                // numeric
                showDialog(obj)

            }, { obj ->
                handleOptionPressed(obj)

            }, { obj ->
                handleOptionPressed(obj)
            } ,{  obj ->
                // iconDialog
                showDialog(obj)

           }, {  obj ->
                //textDialog
                showDialog(obj)

             } , {
                 //update circle data
                sharedViewModel.updateOptionsList(it[0] as FieledRealm)
            }
           ) , realmLiveOptions
        )
    }

    private fun showDialog(obj: List<Any>) {
        DialogListFragment(obj[0] as RealmList<RealmOption>, obj[1] as String).show(
            childFragmentManager, DialogListFragment.TAG
        )
    }

    private fun handleOptionPressed(obj: List<Any>) {
        if ( obj[1] == true/* insert*/) {
            sharedViewModel.updateOption(obj[0] as RealmOption, true, "")
             sharedViewModel.selectedOptions.value?.add(obj[0] as RealmOption)

        } else if ( obj[1] == false) {
            sharedViewModel.updateOption(obj[0] as RealmOption, false, "")
             sharedViewModel.selectedOptions.value?.remove(obj[0] as RealmOption)
        }
    }



}
