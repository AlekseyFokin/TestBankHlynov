package org.sniffsnirr.testbankhlynov.domain.usecase

import org.sniffsnirr.testbankhlynov.data.retrofit.AudioscrobberRepository
import org.sniffsnirr.testbankhlynov.domain.entity.ArtistBiography
import org.sniffsnirr.testbankhlynov.util.NetworkResult
import javax.inject.Inject

class GetArtistBiographiInfoUseCase @Inject constructor(
    private val audioscrobberRepository: AudioscrobberRepository
) {
    suspend operator fun invoke(artistName: String): ArtistBiography {
        val biographyData =
            audioscrobberRepository.loadBiography(artistName)

        when (biographyData) {
            is NetworkResult.Success -> {
                val artistBiography = ArtistBiography(
                    name = biographyData.data?.artist?.name ?: "",
                    imageUrl = biographyData.data?.artist?.image[1]?.text?:"",
                    summary = biographyData.data?.artist?.bio?.summary ?: "",
                    error = null)
                return artistBiography
            }

            is NetworkResult.Error<*> -> {
                return latestCurrencyRateResponse.message
            }

            is NetworkResult.Loading<*> -> {
                return null
            }
        }
    }

}