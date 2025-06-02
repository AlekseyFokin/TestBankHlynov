package org.sniffsnirr.testbankhlynov.util

import RootResponseArtistBiography
import org.sniffsnirr.testbankhlynov.data.dto.RootResponseArtistTopTracks
import org.sniffsnirr.testbankhlynov.domain.entity.ArtistBiography
import org.sniffsnirr.testbankhlynov.domain.entity.ArtistTopTrack

fun RootResponseArtistBiography.toArtistBiography():ArtistBiography {
    return ArtistBiography(
        name = this.artist.name,
        imageUrl = this.artist.image[1].text,
        summary = this.artist.bio.summary,
    )
}

    fun RootResponseArtistTopTracks.toArtistTopTracks():List<ArtistTopTrack>{
        val list = mutableListOf<ArtistTopTrack>()
        this.toptracks.track.map{track->
            list.add(ArtistTopTrack(track.name,track.image.get(1).text))
        }
     return   list
    }
