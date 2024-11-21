package com.dag.nexq_app.blockchain.data

import kotlinx.serialization.Serializable

@Serializable
data class GoogleSearchResponse(
    val kind: String,
    val url: Url,
    val items: List<Item>?
)

@Serializable
data class Url(
    val type: String,
    val template: String
)

@Serializable
data class Item(
    val kind: String,
    val title: String,
    val htmlTitle: String,
    val link: String,
    val displayLink: String,
    val formattedUrl: String,
    val htmlFormattedUrl: String,
    val mime: String?,
    val fileFormat: String?
)