package org.sniffsnirr.testbankhlynov.presentation.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.sniffsnirr.testbankhlynov.R
import org.sniffsnirr.testbankhlynov.databinding.FragmentBiographyBinding

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


            biographySearchButton.setOnClickListener {
                if (!searchText.text.isNullOrBlank()) {
                    viewModel.getArtistBoigraphy(searchText.text.toString())
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {// загрузка всего контента для HomeFragment
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.artistBiographyInfo.collect {
                    with(binding) {
                        artistHeader.text = it?.name
                        artistText.text = it?.summary
                        super.onViewCreated(view, savedInstanceState)
                        Glide
                            .with(photo.context)
                            .load(it?.imageUrl)
                            .into(photo)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {// прогрессбар загрузки
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect {
                    if (it) {
                        binding.progressbar.visibility = View.VISIBLE
                    } else {
                        binding.progressbar.visibility = View.GONE
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}