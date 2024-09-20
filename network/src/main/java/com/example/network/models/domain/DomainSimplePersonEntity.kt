package com.example.network.models.domain

data class DomainSimplePersonEntity(
    val id: Long,
    val name: String,
    val image: DomainPersonEntity.ImagePerson? = null,
)
