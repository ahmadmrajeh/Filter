package com.example.filter.ui.screens.filter

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datascource.realm.filter.RealmOption
import com.example.filter.R
import com.example.filter.databinding.FragmentDialogBinding
import com.example.filter.ui.adapters.nestead.dialogs.AdapterDialog
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DialogListFragment(obj: RealmList<RealmOption>, type: String) : DialogFragment() {
    private val dataType = type
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
        setUpRecyclerView()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun numericButtonsColor() {
         if (dataType == "numericFrom")fromClicked()
        else   toClicked()
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
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun fromClicked() {
        binding.From.foreground = ContextCompat
            .getDrawable(requireContext(), R.drawable.blu_btn)
        binding.To.foreground = ContextCompat
            .getDrawable(requireContext(), R.drawable.blu_white)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun controlVisibility() {
        binding.From.visibility = View.VISIBLE
        binding.To.visibility = View.VISIBLE
        numericButtonsColor()
    }

    private fun setUpRecyclerView() {

        if (rlmRsltList?.isNotEmpty() == true) {
            Log.e("2tt", "there is data")
            mAdapter = AdapterDialog(rlmRsltList) {

            }
                lifecycleScope.launch(Dispatchers.Main) {
                binding.recyclerDialig.adapter = mAdapter
                binding.recyclerDialig.layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
}