package com.example.filter.ui.screens.filter

import android.os.Bundle
import android.util.Log
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
import com.example.filter.ui.adapters.nestead.childs.ChildMembersAdapter
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
        Log.e("WORKINGS", args.id.toString())
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
       Log.e("LIV1212","live data is on")
        }


    }

    private fun setUpRecyclerView() {
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
            {
                //grid

            }, { obj ->
                // numeric
                DialogListFragment(obj[0] as RealmList<RealmOption>, obj[1] as String).show(
                    childFragmentManager, DialogListFragment.TAG
                )

            }, { obj ->
                if (obj[1]=="horizontal"&& obj[2] == true/* */){
                    sharedViewModel.updateOption(obj[0] as RealmOption, true)
                    sharedViewModel.selectedOptions.value?.add(obj[0] as RealmOption) }

                else if (obj[1]=="horizontal"&& obj[2] ==false){

                    sharedViewModel.updateOption(obj[0] as RealmOption, false)
                    sharedViewModel.selectedOptions.value?.remove(obj[0] as RealmOption)
                }

                Log.e("listvv", sharedViewModel.selectedOptions.value.toString())

         /*       else

                )*/

            }, { obj ->

                //Log.e("listvv", obj[0].toString())

                if (obj[1]=="horizontal"&& obj[2] == true/* */){
                    sharedViewModel.updateOption(obj[0] as RealmOption, true)
                sharedViewModel.selectedOptions.value?.add(obj[0] as RealmOption) }

                else if (obj[1]=="horizontal"&& obj[2] ==false){
                    sharedViewModel.updateOption(obj[0] as RealmOption, false)
                    sharedViewModel.selectedOptions.value?.remove(obj[0] as RealmOption)
                }

                Log.e("listvv", sharedViewModel.selectedOptions.value.toString())

              /*  else
                */
            } ,{  obj ->
                // iconDialog
                DialogListFragment(obj[0] as RealmList<RealmOption>, obj[1] as String).show(
                    childFragmentManager, DialogListFragment.TAG
                )


           }, {  obj ->
                //stringDia
                //textDialog
                DialogListFragment(obj[0] as RealmList<RealmOption>, obj[1] as String).show(
                    childFragmentManager, DialogListFragment.TAG)

            }
        ) , realmLiveOptions

        )
    }
}
