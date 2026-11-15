package pe.edu.pe.Datas.remote.mapper

import kotlinx.serialization.json.jsonPrimitive
import pe.edu.pe.Datas.remote.dto.CreateEncomiendaResponseDto
import pe.edu.pe.domain.model.RegistroEncomienda


fun CreateEncomiendaResponseDto.toDomain():
        RegistroEncomienda {

    return RegistroEncomienda(

        id =
            encomienda.id,

        codigoRastreo =
            encomienda.codigoRastreo,

        claveRastreo =
            claveRastreo,

        costoTotal =
            encomienda
                .costoTotal
                .jsonPrimitive
                .content
                .toDoubleOrNull()
                ?: 0.0,

        estado =
            encomienda.estado,

        remitenteDni =
            encomienda.remitente.dni,

        remitenteNombres =
            encomienda.remitente.nombres,

        remitenteApellidos =
            encomienda.remitente.apellidos,

        destinatarioDni =
            encomienda.destinatario.dni,

        destinatarioNombres =
            encomienda.destinatario.nombres,

        destinatarioApellidos =
            encomienda.destinatario.apellidos,

        origen =
            encomienda.origen,

        destino =
            encomienda.destino,

        pagoModalidad =
            pago?.modalidad
    )
}