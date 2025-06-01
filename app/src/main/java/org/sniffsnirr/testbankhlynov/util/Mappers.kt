package org.sniffsnirr.testbankhlynov.util

import RootResponseArtistBiography
import org.sniffsnirr.testbankhlynov.domain.entity.ArtistBiography

fun RootResponseArtistBiography.toArtistBiography():ArtistBiography {
    return ArtistBiography(
        name = this.artist.name,
        imageUrl = this.artist.image[1].text,
        summary = this.artist.bio.summary,
    )
}