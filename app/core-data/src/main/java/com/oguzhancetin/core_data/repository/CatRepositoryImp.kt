package com.oguzhancetin.core_data.repository

import android.util.Log
import com.oguzhancetin.core_database.CatDao
import com.oguzhancetin.core_database.entity.mapper.asDomain
import com.oguzhancetin.core_database.entity.mapper.asEntity
import com.oguzhancetin.core_model.model.CatImage
import com.oguzhancetin.core_network.CatAppDispatchers
import com.oguzhancetin.core_network.Dispatcher
import com.oguzhancetin.core_network.service.CatService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CatRepositoryImp @Inject constructor(
    private val catService: CatService,
    private val catDao: CatDao,
    @Dispatcher(CatAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : CatRepository {
    override fun fetchCatImages(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {

        val catImages = catDao.getAllCatImages()
        if (catImages.isEmpty().not())
            emit(catImages.asDomain())

        catService.fetchPokemonInfo()
            .suspendOnSuccess {
                Log.d("İSTEK", this.response.body().toString())
                Log.d("İSTEK", this.response.code().toString())
                this.response.body()?.let {
                    catDao
                    catDao.insertCatImageList(it.asEntity())
                }
            }.onFailure {
                onError(this.message())
            }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)
}