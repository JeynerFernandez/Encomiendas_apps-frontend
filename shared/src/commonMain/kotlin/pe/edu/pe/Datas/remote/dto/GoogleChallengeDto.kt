
package pe.edu.pe.Datas.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GoogleChallengeDto(

    val nonce: String,

    val expiresIn: Int

)
