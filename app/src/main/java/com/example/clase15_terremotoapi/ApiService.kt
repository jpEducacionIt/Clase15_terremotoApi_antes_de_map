package com.example.clase15_terremotoapi

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(value = "significant_month.geojson")
    suspend fun getSignificantMonth() : Response<Features>

    @GET(value = "all_week.geojson")
    suspend fun getAllWeek() : Response<Features>
}