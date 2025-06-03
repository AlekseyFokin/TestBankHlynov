package org.sniffsnirr.testbankhlynov.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.sniffsnirr.testbankhlynov.domain.entity.ArtistTopTrack
import org.sniffsnirr.testbankhlynov.domain.usecase.GetArtistTopTrackListUseCase
import javax.inject.Inject

@HiltViewModel
class TracksViewModel @Inject constructor(private val getArtistTopTracksListUseCase: GetArtistTopTrackListUseCase) :
    ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = Channel<String>() // для передачи ошибки соединения с сервисом поиска
    val error = _error.receiveAsFlow()

    private val _artistTopTracks = MutableStateFlow<List<ArtistTopTrack>>(listOf<ArtistTopTrack>())
    val artistTopTracks = _artistTopTracks.asStateFlow()

    private val _searchStringState = MutableStateFlow<String>("")
    val searchStringState = _searchStringState.asStateFlow()

    fun getArtistBoigraphy(artistName: String) {
        viewModelScope.launch(Dispatchers.IO) {// Запуск загрузки всего контента
            kotlin.runCatching {
                _isLoading.value = true
                getArtistTopTracksListUseCase(artistName)
            }.fold(
                onSuccess = { _artistTopTracks.value = it },
                onFailure = {

                    if (it.message!!.contains(NO_DATA_SIGN)) {
                        _artistTopTracks.value = listOf<ArtistTopTrack>()
                    } else {
                        _isLoading.value = false
                        _error.send(
                            it.message ?: ""
                        )  // показывать диалог с ошибкой - где onFailure
                    }
                }
            )
            _isLoading.value = false
        }
    }

    fun changeSearchString(newSearchString: String) {
        _searchStringState.value = newSearchString
    }

    companion object{
        const val NO_DATA_SIGN="null object reference"
    }
}