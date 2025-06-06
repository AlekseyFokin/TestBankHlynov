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
import org.sniffsnirr.testbankhlynov.domain.entity.ArtistBiography
import org.sniffsnirr.testbankhlynov.domain.usecase.GetArtistBiographyInfoUseCase
import javax.inject.Inject

@HiltViewModel
class BiographyViewModel @Inject constructor(private val getArtistBiographyInfoUseCase: GetArtistBiographyInfoUseCase) :
    ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = Channel<String>() // для передачи ошибки соединения с сервисом поиска
    val error = _error.receiveAsFlow()

    private val _artistBiographyInfo = MutableStateFlow<ArtistBiography?>(null)
    val artistBiographyInfo = _artistBiographyInfo.asStateFlow()

    private val _searchStringState = MutableStateFlow<String>("")
    val searchStringState = _searchStringState.asStateFlow()


    fun getArtistBoigraphy(artistName:String) {
        viewModelScope.launch(Dispatchers.IO) {// Запуск загрузки всего контента
            kotlin.runCatching {
                _isLoading.value = true
                getArtistBiographyInfoUseCase(artistName)
            }.fold(
                onSuccess = { _artistBiographyInfo.value = it },
                onFailure = {
                    if (it.message!!.contains(NO_DATA_SIGN)){ // вариант когда нет данных об артисте
                        _artistBiographyInfo.value= ArtistBiography()
                    }
                    else{
                    _isLoading.value = false
                    _error.send(it.message?:"")  // показывать диалог с ошибкой
                    }
                }
            )
            _isLoading.value = false
        }
    }

    fun changeSearchString(newSearchString:String){
        _searchStringState.value=newSearchString
    }

    companion object{
        const val NO_DATA_SIGN="null object reference"
    }
}

