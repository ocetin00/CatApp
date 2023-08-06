package com.oguzhancetin.core_network.service

import com.oguzhancetin.core_model.model.CatImage
import com.oguzhancetin.core_network.model.CatImageResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface CatService {
    @GET("images/search?limit=10")
    suspend fun fetchPokemonInfo(): ApiResponse<List<CatImage>>
}