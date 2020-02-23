package com.example.svea_assignment.model
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetPlacesApi {

    @GET("meappid")
    fun getAllPlaces(
        @Query("meAppId") id: Int = 50,
        @Query("records") records: Int = 500
    ): Call<Places>
}
