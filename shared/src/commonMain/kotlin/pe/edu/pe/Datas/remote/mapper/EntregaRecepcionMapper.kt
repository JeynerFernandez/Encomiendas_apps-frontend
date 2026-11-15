package pe.edu.pe.Datas.remote.mapper

import pe.edu.pe.Datas.remote.dto.EncomiendaPendienteEntregaDto
import pe.edu.pe.Datas.remote.dto.ListaPendientesEntregaDto
import pe.edu.pe.Datas.remote.dto.PersonaEntregaDto
import pe.edu.pe.Datas.remote.dto.SedeEntregaDto

import pe.edu.pe.domain.model.EncomiendaPendienteEntrega
import pe.edu.pe.domain.model.ListaPendientesEntrega
import pe.edu.pe.domain.model.PersonaEntrega
import pe.edu.pe.domain.model.SedeEntrega


fun ListaPendientesEntregaDto.toDomain():
        ListaPendientesEntrega {

    return ListaPendientesEntrega(

        sedeId =
            sede.id,

        total =
            total,

        encomiendas =
            encomiendas.map {
                it.toDomain()
            }
    )
}


fun EncomiendaPendienteEntregaDto.toDomain():
        EncomiendaPendienteEntrega {

    return EncomiendaPendienteEntrega(

        id =
            id,

        codigoRastreo =
            codigoRastreo,

        estado =
            estado,

        pesoKg =
            pesoKg,

        altoCm =
            altoCm,

        anchoCm =
            anchoCm,

        largoCm =
            largoCm,

        observacion =
            observacion,

        remitente =
            remitente.toDomain(),

        destinatario =
            destinatario.toDomain(),

        sedeOrigen =
            sedeOrigen.toDomain(),

        sedeDestino =
            sedeDestino.toDomain(),

        ultimaSede =
            ultimaSede.toDomain()
    )
}


fun PersonaEntregaDto.toDomain():
        PersonaEntrega {

    return PersonaEntrega(

        dni =
            dni,

        nombres =
            nombres,

        apellidoPaterno =
            apellidoPaterno,

        apellidoMaterno =
            apellidoMaterno
    )
}


fun SedeEntregaDto.toDomain():
        SedeEntrega {

    return SedeEntrega(

        id =
            id,

        name =
            name
    )
}