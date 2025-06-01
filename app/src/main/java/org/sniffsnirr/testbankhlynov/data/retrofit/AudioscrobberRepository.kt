package org.sniffsnirr.testbankhlynov.data.retrofit

import RootResponseArtistBiography
import org.sniffsnirr.testbankhlynov.util.CommonRetrofitResponse
import org.sniffsnirr.testbankhlynov.util.NetworkResult
import javax.inject.Inject

class AudioscrobberRepository @Inject constructor(
    private val audioscrobblerApi: AudioscrobblerApi) : CommonRetrofitResponse(){

    suspend fun loadBiography(artistName:String):NetworkResult<RootResponseArtistBiography>{
        val result=  safeApiCall { audioscrobblerApi.getArtistInfo(artist = artistName)}
        return result
    }
}