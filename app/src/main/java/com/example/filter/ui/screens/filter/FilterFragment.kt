package com.example.filter.ui.screens.filter

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
        sharedViewModel.subFlowJsonToKotlin (
            requireContext().applicationContext, args.id
        )
        sharedViewModel.readOfflineCacheFields(args.id)
        sharedViewModel.selectedOptions.value = RealmList()
        observeData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
    }

    private fun observeData() {
        sharedViewModel.resultFilter.observe(viewLifecycleOwner) {
            rlmRstList = it.fieldsList
            setUpRecyclerView()
         }




        lunchForTheFirstTime()


        sharedViewModel.selectedOptions.observe(viewLifecycleOwner){selected: RealmList<RealmOption> ->
            realmLiveOptions = selected
        }
    }

    private fun lunchForTheFirstTime() {
        val prefs: SharedPreferences = requireActivity().getSharedPreferences(
            "com.example.app", Context.MODE_PRIVATE)

        if (prefs.getBoolean("firstLunch", true)) {
            Log.e("lunchc","in lunchc" )
            Handler(Looper.getMainLooper()).postDelayed({
                prefs.edit().putBoolean("firstLunch", false).apply()
                findNavController().navigate(
                    FilterFragmentDirections.actionFilterFragmentSelf(args.id)
                )

            }, 1000)
        }
    }

    private fun setUpRecyclerView() {
        if (rlmRstList.isNotEmpty()) {
            mAdapter = parentAdapterInstance(rlmRstList,realmLiveOptions)
                binding.RcyclerFilter.adapter = mAdapter
                binding.RcyclerFilter.layoutManager = LinearLayoutManager(requireContext())
           // sharedViewModel.  initializeFields()
        }

    }

    private fun parentAdapterInstance(
        data: RealmList<FieledRealm>,
        realmLiveOptions: RealmList<RealmOption>
    ): ParentAdapter {
        return ParentAdapter(data, listOf(
            { option , selection  ->
                handleOptionPressed(option,selection)

            }, {  option , selection  ->
                handleOptionPressed(option,selection )

            }, {  option , selection  ->
                handleOptionPressed(option,selection )

            }
           ) , listOf({ field, whereFrom->
                      showDialog(field,whereFrom)
        },{field, whereFrom->
            showDialog(field,whereFrom)

        },{field, whereFrom->
            showDialog(field,whereFrom)
        }), realmLiveOptions
        )
    }

    private fun showDialog(field:RealmList<RealmOption>, whereFrom:String) {
        DialogListFragment(field,whereFrom).show(
            childFragmentManager, DialogListFragment.TAG
        )
    }

    private fun handleOptionPressed(option:RealmOption, isSelected:Boolean) {
        if ( isSelected/* insert*/) {
            sharedViewModel.updateOption(option, true, "" )
             sharedViewModel.selectedOptions.value?.add(option)

        } else  {
            sharedViewModel.updateOption(option, false, "" )
             sharedViewModel.selectedOptions.value?.remove(option)
        }
    }
}
