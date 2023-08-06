package com.oguzhancetin.core_database.entity.mapper

import com.oguzhancetin.core_database.entity.CatImageEntity
import com.oguzhancetin.core_model.model.CatImage

object CatEntityMapper : EntityMapper<CatImage, CatImageEntity> {
    override fun asEntity(domain: CatImage): CatImageEntity {
        return CatImageEntity(
            id = domain.id,
            url = domain.url,
            width = domain.width,
            height = domain.height
        )
    }

    override fun asDomain(entity: CatImageEntity): CatImage {
        return CatImage(
            id = entity.id,
            url = entity.url,
            width = entity.width,
            height = entity.height
        )
    }

}

fun List<CatImageEntity>.asDomain(): List<CatImage> {
    return this.map { CatEntityMapper.asDomain(it) }
}

fun List<CatImage>.asEntity(): List<CatImageEntity> {
    return this.map { CatEntityMapper.asEntity(it) }
}