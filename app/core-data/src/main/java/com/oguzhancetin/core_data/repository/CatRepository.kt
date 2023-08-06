package com.oguzhancetin.core_data.repository

import com.oguzhancetin.core_model.model.CatImage
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun fetchCatImages(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<CatImage>>
}