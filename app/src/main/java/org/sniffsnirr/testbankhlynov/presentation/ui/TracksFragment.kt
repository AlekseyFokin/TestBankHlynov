package org.sniffsnirr.testbankhlynov.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.sniffsnirr.testbankhlynov.R
import org.sniffsnirr.testbankhlynov.databinding.FragmentTracksBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.GridLayoutManager
import org.sniffsnirr.testbankhlynov.presentation.ui.trackrv.ArtistTopTracksAdapter

@AndroidEntryPoint
class TracksFragment : Fragment() {

    private var _binding: FragmentTracksBinding? = null
    val binding get() = _binding!!

    private val viewModel: TracksViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)
    lateinit var atristTopTrackAdapter: ArtistTopTracksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            searchText.requestFocus()

            backButton.setOnClickListener {
                findNavController().popBackStack(R.id.mainFragment, false)
            }
            tracksSearchButton.setOnClickListener {
                if (!searchText.text.isNullOrBlank()) {
                    viewModel.changeSearchString(searchText.text.toString())
                    viewModel.getArtistBoigraphy(searchText.text.toString())
                }
            }

            trackRv.setHasFixedSize(true)
            trackRv.layoutManager =
                LinearLayoutManager(requireContext(), GridLayoutManager.VERTICAL, false)
            atristTopTrackAdapter = ArtistTopTracksAdapter()
            trackRv.adapter = atristTopTrackAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {// загрузка всего контента
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.artistTopTracks.collect {
                    atristTopTrackAdapter.setData(it.toList())
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}