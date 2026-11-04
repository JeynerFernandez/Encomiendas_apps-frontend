package pe.edu.pe.Datas.remote.mapper

import pe.edu.pe.Datas.remote.dto.SedeDto
import pe.edu.pe.domain.model.Sede

fun SedeDto.toDomain(): Sede {
    return Sede(
        id = this.id,
        name = this.name,
        city = this.city,
        address = this.address,
        latitude = this.latitude,
        longitude = this.longitude,
        active = this.active,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}