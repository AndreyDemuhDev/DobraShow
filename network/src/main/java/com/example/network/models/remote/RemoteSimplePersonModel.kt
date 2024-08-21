package com.example.network.models.remote

import com.example.network.models.domain.DomainPersonEntity
import com.example.network.models.domain.DomainSimplePersonEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteSimplePersonElement(
    @SerialName("id") val id: Long?,
    @SerialName("name") val name: String?,
    @SerialName("image") val image: RemotePersonModel.ImagePerson? = null,
)

fun RemoteSimplePersonElement.toDomainSimplePerson(): DomainSimplePersonEntity {
    return DomainSimplePersonEntity(
        id = id ?: 0,
        name = name ?: "unknown name",
        image = DomainPersonEntity.ImagePerson(
            medium = image?.medium ?: "unknown image person",
            original = image?.original ?: "unknown image person"
        )
    )
}
