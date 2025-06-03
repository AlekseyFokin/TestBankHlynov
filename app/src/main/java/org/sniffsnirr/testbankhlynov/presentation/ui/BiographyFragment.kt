package org.sniffsnirr.testbankhlynov.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.sniffsnirr.testbankhlynov.R
import org.sniffsnirr.testbankhlynov.databinding.FragmentBiographyBinding

@AndroidEntryPoint
class BiographyFragment : Fragment() {
    private var _binding: FragmentBiographyBinding? = null
    val binding get() = _binding!!

    private val viewModel: BiographyViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)

    var currentArtistText = ""

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
            searchText.requestFocus()

            backButton.setOnClickListener {
                findNavController().popBackStack(R.id.mainFragment, false)
            }

            biographySearchButton.setOnClickListener {
                if (!searchText.text.isNullOrBlank()) {
                    viewModel.changeSearchString(searchText.text.toString())
                    viewModel.getArtistBoigraphy(searchText.text.toString())
                    contentLayout.visibility = View.GONE
                    photo.visibility = View.GONE
                }
            }

            artistText.setOnClickListener {
                if (currentArtistText.isNotEmpty()) {
                    if (artistText.text == currentArtistText) {
                        // Если текст уже полный - сворачиваем
                        artistText.text =
                            if (currentArtistText.length > MAX_LENGTH) {
                                currentArtistText.substring(
                                    0,
                                    MAX_LENGTH
                                ) + "..."
                            } else {
                                currentArtistText
                            }
                    } else {
                        // Показываем полный текст
                        artistText.text = currentArtistText
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {// загрузка всего контента
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.artistBiographyInfo.collect {
                        with(binding) {
                            if (it != null) {
                                contentLayout.visibility = View.VISIBLE
                                photo.visibility = View.VISIBLE
                                    // обработка различных ответов сервера - по факту это нет данных
                                artistHeader.text = it.name ?: NO_DATA
                                ((it.summary == null) || (it.imageUrl == ""))
                                currentArtistText =
                                    if ((it.summary == null) || (it.imageUrl == "") || (it.summary.contains(
                                            NO_DATA_SIGN
                                        ))
                                    ) {
                                        NO_DATA
                                    } else {
                                        it.summary.substring(0, it.summary.lastIndexOf(". "))
                                    }
                                textTruncation(currentArtistText)
                                super.onViewCreated(view, savedInstanceState)
                                Glide
                                    .with(photo.context)
                                    .load(it.imageUrl)
                                    .error(R.drawable.outline_no_photography_24)
                                    .into(photo)
                            }
                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {// прогрессбар загрузки
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.isLoading.collect {
                        with(binding) {
                            if (it) {
                                progressbar.visibility = View.VISIBLE
                            } else {
                                progressbar.visibility = View.GONE
                            }
                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {// строка поиска
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.searchStringState.collect {
                        binding.searchText.setText(it)
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {// ожидание ошибки
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.error.collect { mess ->
                        val bundle = Bundle()
                        bundle.putString(BottomSheetErrorFragment.ERROR_MESS, mess)
                        val bottomSheetErrorFragment = BottomSheetErrorFragment()
                        bottomSheetErrorFragment.arguments = bundle
                        bottomSheetErrorFragment.show(parentFragmentManager, "Error_Tag")
                    }
                }
            }
        }
    }

    fun textTruncation(fullText: String) {
        binding.artistText.text = if (fullText.length > MAX_LENGTH) {
            fullText.substring(0, MAX_LENGTH) + "..."
        } else {
            fullText
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        const val MAX_LENGTH = 150
        const val NO_DATA="нет данных"
        const val NO_DATA_SIGN="+noredirect"
    }
}