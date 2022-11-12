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


class DialogListFragment(obj: RealmList<RealmOption>) : DialogFragment() {
   lateinit var binding : FragmentDialogBinding
    private lateinit var mAdapter: AdapterDialog
    private var rlmRsltList: RealmList< RealmOption>? = obj


    companion object {
        const val TAG = "onDialog"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragmentDialogBinding.inflate(inflater)


        controlVisibelity()
        numericButtonsColor()


        setUpRecyclerView()


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun numericButtonsColor() {
        binding.button5.foreground = ContextCompat
            .getDrawable(
                requireContext(),
                R.drawable.blu_white
            )
    }

    private fun controlVisibelity() {
        binding.button4.visibility = View.VISIBLE

        binding.button5.visibility = View.VISIBLE
    }
    private fun setUpRecyclerView() {

        if (rlmRsltList?.isNotEmpty() == true) {
            Log.e("2tt","there is data")
            mAdapter = AdapterDialog(rlmRsltList) { id ->

            }

            lifecycleScope.launch(Dispatchers.Main) {
                binding.recyclerDialig .adapter = mAdapter
                binding.recyclerDialig.layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }


}