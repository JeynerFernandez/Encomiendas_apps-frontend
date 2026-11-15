package pe.edu.pe.Datas.remote.mapper

import pe.edu.pe.Datas.remote.dto.RutaDto
import pe.edu.pe.Datas.remote.dto.RutaSedeDto
import pe.edu.pe.domain.model.Ruta
import pe.edu.pe.domain.model.RutaSede

fun RutaSedeDto.toDomain():
        RutaSede {

    return RutaSede(
        id = id,
        sedeId = sede.id,
        sedeNombre = sede.name,
        orden = orden
    )
}

fun RutaDto.toDomain():
        Ruta {

    val sedesOrdenadas =
        sedes.sortedBy {
            it.orden
        }

    val sedesDomain =
        mutableListOf<RutaSede>()

    for (
    sede in sedesOrdenadas
    ) {

        sedesDomain.add(
            sede.toDomain()
        )
    }

    return Ruta(
        id = id,
        name = name,
        active = active,
        sedes = sedesDomain
    )
}