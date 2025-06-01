package org.sniffsnirr.testbankhlynov.data.retrofit

import org.sniffsnirr.testbankhlynov.domain.entity.ArtistBiography
import org.sniffsnirr.testbankhlynov.util.toArtistBiography
import javax.inject.Inject

class AudioscrobberRepository @Inject constructor(
    private val audioscrobblerApi: AudioscrobblerApi
) {
    suspend fun loadBiography(artistName: String): ArtistBiography {
        return audioscrobblerApi.getArtistInfo(artist = artistName).toArtistBiography()
    }
}