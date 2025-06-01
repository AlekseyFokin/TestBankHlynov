package org.sniffsnirr.testbankhlynov.util

import retrofit2.Response

abstract class CommonRetrofitResponse {

    suspend fun <T> safeApiCall(api: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = api()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let { return NetworkResult.Success(data = body) }
                    ?: return getError("Network response was received, but body is empty, errorbody: ${response.errorBody()}")
            } else {
                return getError("Response with exception: ${response.code()} - ${response.message()}")
            }

        } catch (e: Exception) {
            return getError(e.message.toString())
        }
    }

    private fun <T> getError(e: String): NetworkResult.Error<T> =NetworkResult.Error(data = null, message = "Network exception: $e")
}