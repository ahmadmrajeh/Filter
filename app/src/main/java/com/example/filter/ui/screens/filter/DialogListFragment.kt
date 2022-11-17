package com.example.filter.ui.screens.filter

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datascource.realm.filter.FieledRealm
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.FragmentDialogBinding
import com.example.filter.ui.adapters.nestead.dialogs.AdapterDialog
import com.example.filter.ui.screens.viewmodel.MainViewModel
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DialogListFragment(obj: RealmList<RealmOption>, type: String) : DialogFragment() {
    private lateinit var realmLiveOptions: RealmList<RealmOption>
    private val sharedViewModel: MainViewModel by activityViewModels()
    private var dataType = type
    lateinit var binding: FragmentDialogBinding
    private lateinit var mAdapter: AdapterDialog
    private var rlmRsltList: RealmList<RealmOption>? = obj

    companion object {
        const val TAG = "onDialog"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogBinding.inflate(inflater)

        if (dataType == "numericFrom" || dataType == "numericTo") controlVisibility()
        else {
            binding.cancel.setOnClickListener {
                dismiss()
            }
            binding.reset.setOnClickListener {
                dismiss()
            }
            binding.done.setOnClickListener {
                dismiss()
            }
        }


        sharedViewModel.selectedOptions.observe(viewLifecycleOwner) { selected: RealmList<RealmOption> ->
            realmLiveOptions = selected
            setUpRecyclerView()
        }
        return binding.root

   }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun numericButtonsColor() {
        if (dataType == "numericFrom") fromClicked()
        else toClicked()
        binding.From.setOnClickListener {
            fromClicked()
        }
        binding.To.setOnClickListener {
            toClicked()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun toClicked() {
        binding.To.foreground = ContextCompat
            .getDrawable(requireContext(), R.drawable.blu_btn)
        binding.From.foreground = ContextCompat
            .getDrawable(requireContext(), R.drawable.blu_white)
        dataType ="numericTo"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun fromClicked() {
        binding.From.foreground = ContextCompat
            .getDrawable(requireContext(), R.drawable.blu_btn)
        binding.To.foreground = ContextCompat
            .getDrawable(requireContext(), R.drawable.blu_white)
        dataType ="numericFrom"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun controlVisibility() {
        binding.From.visibility = View.VISIBLE
        binding.To.visibility = View.VISIBLE
        binding.cancel.visibility = View.GONE
        binding.reset.visibility = View.GONE
        binding.done.text = getString(R.string.cancel)
        binding.done.setOnClickListener {
            dismiss()
        }
        numericButtonsColor()
    }

    private fun setUpRecyclerView() {
        if (rlmRsltList?.isNotEmpty() == true) {

            mAdapter = AdapterDialog(rlmRsltList!!, dataType)
            lifecycleScope.launch(Dispatchers.Main) {
                binding.recyclerDialig.adapter = mAdapter
                binding.recyclerDialig.layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun AdapterDialog(data: RealmList<RealmOption>, comingFrom: String): AdapterDialog {
        return AdapterDialog(data, comingFrom, listOf(
            { obj -> // icon
                handleOptionsSelected(obj)

            }, { obj -> //text
                handleOptionsSelected(obj)
            }, {
                    obj -> //numeric
                handleNumericChanged(obj)
                dismiss()
            }
        ),
            realmLiveOptions)
    }

    private fun handleNumericChanged(obj: List<Any>) {
        val option = obj[0] as RealmOption
        val temp = sharedViewModel.selectedOptions.value?.find {
            it.field_id == option.field_id && it.whereFrom == obj[1]

        }
        temp?.let {
            sharedViewModel.selectedOptions.value?.remove(it)
            sharedViewModel.updateOption(option, false, obj[1] as String, obj[2] as FieledRealm)
        }
        sharedViewModel.updateOption(option, true, obj[1] as String, obj[2] as FieledRealm)
        sharedViewModel.selectedOptions.value?.add(option)
    }

    private fun handleOptionsSelected(obj: List<Any>) {
        if ( obj[1] == true/*insert */) {
            sharedViewModel.updateOption(obj[0] as RealmOption, true, "", obj[2] as FieledRealm)
            sharedViewModel.selectedOptions.value?.add(obj[0] as RealmOption)
        } else if (obj[1] == false) {
            sharedViewModel.updateOption(obj[0] as RealmOption, false, "", obj[2] as FieledRealm)
            sharedViewModel.selectedOptions.value?.remove(obj[0] as RealmOption)
        }
    }
}