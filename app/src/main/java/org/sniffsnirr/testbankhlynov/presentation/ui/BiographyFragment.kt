package org.sniffsnirr.testbankhlynov.presentation.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.sniffsnirr.testbankhlynov.R
import org.sniffsnirr.testbankhlynov.databinding.FragmentBiographyBinding
import org.sniffsnirr.testbankhlynov.databinding.FragmentMainBinding

class BiographyFragment : Fragment() {
    private var _binding: FragmentBiographyBinding? = null
    val binding get() = _binding!!

    private val viewModel: BiographyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBiographyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            backButton.setOnClickListener {
                findNavController().popBackStack(R.id.mainFragment, false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}