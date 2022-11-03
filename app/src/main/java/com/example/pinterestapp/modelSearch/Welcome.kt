package com.example.pinterestapp.modelSearch

data class Welcome (
    val total: Long,
    val totalPages: Long,
    val results: List<Result>? = null
)