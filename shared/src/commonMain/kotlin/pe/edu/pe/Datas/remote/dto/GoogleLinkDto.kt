package pe.edu.pe.Datas.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GoogleLinkDto(

    val idToken: String,

    val password: String

)