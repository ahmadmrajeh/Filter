package com.example.filter.ui.screens


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.filter.databinding.FragmentSplashScreenBinding
import com.example.filter.ui.screens.viewmodel.MainViewModel


class SplashScreenFragment : Fragment() {
lateinit var binding: FragmentSplashScreenBinding
    private val sharedViewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (sharedViewModel.appLunched) {
            requireActivity().finish()
        } else{
            binding = FragmentSplashScreenBinding.inflate(inflater)
            sharedViewModel.catJsonToKotlin(requireActivity().applicationContext)
            sharedViewModel.appLunched = true
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(
                SplashScreenFragmentDirections.actionSplashScreenFragmentToMainFragment()
             )
           }, 1000)
         }
    return binding.root
    }
}