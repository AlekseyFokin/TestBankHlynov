package org.sniffsnirr.testbankhlynov.data.dto

import com.google.gson.annotations.SerializedName

data class RootResponseArtistTopTracks(
    @SerializedName("toptracks") val toptracks: Toptracks
)

data class Toptracks(
    @SerializedName("track") val track: List<Track>,
    @SerializedName("@attr") val attribute: Attribute
)

data class Track(
    @SerializedName("name") val name: String,
    @SerializedName("playcount") val playcount: Int,
    @SerializedName("listeners") val listeners: Int,
    @SerializedName("mbid") val mbid: String,
    @SerializedName("url") val url: String,
    @SerializedName("streamable") val streamable: Int,
    @SerializedName("artist") val artist: Artist,
    @SerializedName("image") val image: List<Image>,
    @SerializedName("@attr") val attribute: Attribute
)

data class Artist(
    @SerializedName("name") val name: String,
    @SerializedName("mbid") val mbid: String,
    @SerializedName("url") val url: String
)

data class Image(
    @SerializedName("#text") val text: String,
    @SerializedName("size") val size: String
)

data class Attribute(
    @SerializedName("artist") val artist: String,
    @SerializedName("page") val page: Int,
    @SerializedName("perPage") val perPage: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("total") val total: Int
)

