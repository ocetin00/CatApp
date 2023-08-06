package com.oguzhancetin.core_network.model

import com.oguzhancetin.core_model.model.CatImage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatImageResponse(
  @Json(name = "catImageList")
  val catImageList : List<CatImage>
)

