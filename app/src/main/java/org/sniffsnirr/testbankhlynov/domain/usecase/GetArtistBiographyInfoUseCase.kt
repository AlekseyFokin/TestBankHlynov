package org.sniffsnirr.testbankhlynov.domain.usecase

import org.sniffsnirr.testbankhlynov.data.retrofit.AudioscrobberRepository
import org.sniffsnirr.testbankhlynov.domain.entity.ArtistBiography
import javax.inject.Inject

class GetArtistBiographyInfoUseCase @Inject constructor(
    private val audioscrobberRepository: AudioscrobberRepository
) {
    suspend operator fun invoke(artistName: String):ArtistBiography {
       return audioscrobberRepository.loadBiography(artistName)
    }

}