package com.example.pinterestapp.relatedcollection

import com.example.pinterestapp.model.Urls

data class SinglePhoto(
    val id: String,
    val urls: Urls,
    val likes: Long,
    val related_collections: RelatedCollections,
)