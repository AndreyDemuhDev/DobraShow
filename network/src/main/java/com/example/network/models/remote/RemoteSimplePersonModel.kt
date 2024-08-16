package com.example.network.models.remote

import com.example.network.models.domain.DomainPersonEntity
import com.example.network.models.domain.DomainSimplePersonEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteSimplePersonElement(
    val id: Long?,
    val name: String?,
    val image: RemotePersonModel.ImagePerson? = null,
)

fun RemoteSimplePersonElement.toDomainSimplePerson(): DomainSimplePersonEntity {
    return DomainSimplePersonEntity(
        id = id ?: 0,
        name = name ?: "unknow name",
        image = DomainPersonEntity.ImagePerson(
            medium = image?.medium ?: "unknow image person",
            original = image?.original ?: "unknown image person"
        )
    )
}
