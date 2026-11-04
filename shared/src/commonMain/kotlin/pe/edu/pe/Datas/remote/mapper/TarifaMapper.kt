package pe.edu.pe.Datas.remote.mapper

import pe.edu.pe.Datas.remote.dto.TarifaDto
import pe.edu.pe.domain.model.Tarifa


// Convierte la respuesta del backend al modelo del dominio
fun TarifaDto.toDomain(): Tarifa {

    return Tarifa(

        id =
            id,

        sedeOrigenId =
            origen.id,

        sedeDestinoId =
            destino.id,

        sedeOrigen =
            origen.name,

        sedeDestino =
            destino.name,

        precioBase =
            precioBase,

        precioKgExcedente =
            precioKgExcedente,

        active =
            active
    )
}