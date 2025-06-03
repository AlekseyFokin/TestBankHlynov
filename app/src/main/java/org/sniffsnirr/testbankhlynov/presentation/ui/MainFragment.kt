package org.sniffsnirr.testbankhlynov.presentation.ui.main

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.sniffsnirr.testbankhlynov.R
import org.sniffsnirr.testbankhlynov.databinding.FragmentMainBinding

@AndroidEntryPoint

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            biographyButton.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_biographyFragment) }

            besttracksButton.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_tracksFragment) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}