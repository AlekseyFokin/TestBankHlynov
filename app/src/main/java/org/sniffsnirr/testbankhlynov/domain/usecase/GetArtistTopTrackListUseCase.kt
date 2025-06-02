package org.sniffsnirr.testbankhlynov.domain.usecase

import org.sniffsnirr.testbankhlynov.data.retrofit.AudioscrobberRepository
import org.sniffsnirr.testbankhlynov.domain.entity.ArtistTopTrack
import javax.inject.Inject

class GetArtistTopTrackListUseCase @Inject constructor(
    private val audioscrobberRepository: AudioscrobberRepository
) {
    suspend operator fun invoke(artistName: String): List<ArtistTopTrack> {
        return audioscrobberRepository.loadTopTracks(artistName).shuffled()
            .take(3) // взял три случайных трека из Топ-50
    }
}