package pe.edu.pe.Datas.remote.mapper

import pe.edu.pe.domain.model.User
import pe.edu.pe.Datas.remote.dto.AuthResponse
import pe.edu.pe.Datas.remote.dto.UserDto


fun AuthResponse.toDomain(): User{
    return User(
        id = this.user.id,
        name = this.user.name,
        email = this.user.email,
        role = this.user.role.name,
        sedeId = this.user.sedeId,
        token = this.accessToken

    )
}
fun UserDto.toDomain(): User {
    return User(
        id = this.id,
        name = this.name,
        email = this.email,
        role = this.role.name,
        sedeId = this.sedeId
    )
}