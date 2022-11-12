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
import com.example.filter.ui.screens.viewmodel.MainViewModel
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Suppress("UNCHECKED_CAST")
class FilterFragment : Fragment() {
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
            requireContext().applicationContext, args.id)
        sharedViewModel.readOfflineCacheFields(args.id)
        observeData()
        return binding.root
    }

   private fun observeData() {

        sharedViewModel.resultFilter.observe(viewLifecycleOwner) {

            try {
                rlmRstList= it.fieldsList
                setUpRecyclerView()

            } catch (e: Exception) {
                Log.e("WORKINGS", "no data" + e.message)
            }
        }
    }

    private fun setUpRecyclerView() {

        if (rlmRstList.isNotEmpty()) {
            mAdapter = parentAdapterInstance(rlmRstList)



            lifecycleScope.launch(Dispatchers.Main) {
                binding.RcyclerFilter.adapter = mAdapter
                binding.RcyclerFilter.
                layoutManager= LinearLayoutManager(requireContext())

            }
        }
    }

    private fun parentAdapterInstance(data: RealmList<FieledRealm>):
            ParentAdapter {
      return  ParentAdapter(data , listOf(
            {
                //grid

            }, { obj ->
              // numeric

              DialogListFragment(obj as RealmList< RealmOption>).show(
                  childFragmentManager, DialogListFragment.TAG
              )


          } ,{
                //text

            },{
                // icon

            }
        )

        )
    }


}
