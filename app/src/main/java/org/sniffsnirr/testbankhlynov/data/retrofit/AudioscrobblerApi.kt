package org.sniffsnirr.testbankhlynov.data.retrofit


import RootResponseArtistBiography
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AudioscrobblerApi {


    @GET("2.0/")
    suspend fun getArtistInfo(
        @Query("method") method: String = "artist.getInfo",
        @Query("artist") artist: String,
        @Query("api_key") apiKey: String = "YOUR_API_KEY",
        @Query("format") format: String = "json"
    ): RootResponseArtistBiography

    companion object{
           const val API_KEY = "98d047242117728d0875463cb3d36e41"
    }
}