package pe.edu.pe.Datas.remote.mapper

import pe.edu.pe.Datas.remote.dto.EncomiendaRastreoDto
import pe.edu.pe.Datas.remote.dto.HistorialRastreoDto
import pe.edu.pe.Datas.remote.dto.MovilidadRastreoDto
import pe.edu.pe.Datas.remote.dto.ProgresoRastreoDto
import pe.edu.pe.Datas.remote.dto.SedeRastreoDto
import pe.edu.pe.Datas.remote.dto.TrackingResponseDto

import pe.edu.pe.domain.model.EncomiendaRastreo
import pe.edu.pe.domain.model.HistorialRastreo
import pe.edu.pe.domain.model.MovilidadRastreo
import pe.edu.pe.domain.model.ProgresoRastreo
import pe.edu.pe.domain.model.RastreoPedido
import pe.edu.pe.domain.model.SedeRastreo


fun TrackingResponseDto.toDomain():
        RastreoPedido {

    return RastreoPedido(
        encomienda =
            encomienda.toDomain(),

        progreso =
            progreso.map {
                it.toDomain()
            },

        historial =
            historial.map {
                it.toDomain()
            },

        movilidad =
            movilidad.toDomain()
    )
}


fun EncomiendaRastreoDto.toDomain():
        EncomiendaRastreo {

    var fechaEntrega =
        ""

    if (
        entregadoAt != null
    ) {
        fechaEntrega =
            entregadoAt
    }


    return EncomiendaRastreo(
        id =
            id,

        codigoRastreo =
            codigoRastreo,

        estado =
            estado,

        mensajeEstado =
            mensajeEstado,

        origen =
            origen.toDomain(),

        destino =
            destino.toDomain(),

        sedeActual =
            sedeActual.toDomain(),

        pesoKg =
            pesoKg,

        createdAt =
            createdAt,

        updatedAt =
            updatedAt,

        entregadoAt =
            fechaEntrega
    )
}


fun SedeRastreoDto.toDomain():
        SedeRastreo {

    return SedeRastreo(
        id =
            id,

        name =
            name
    )
}


fun ProgresoRastreoDto.toDomain():
        ProgresoRastreo {

    return ProgresoRastreo(
        estado =
            estado,

        completado =
            completado,

        actual =
            actual
    )
}


fun HistorialRastreoDto.toDomain():
        HistorialRastreo {

    var sedeDomain =
        SedeRastreo()


    if (
        sede != null
    ) {
        sedeDomain =
            sede.toDomain()
    }


    return HistorialRastreo(
        id =
            id,

        estado =
            estado,

        sede =
            sedeDomain,

        mensaje =
            mensaje,

        fecha =
            fecha
    )
}


fun MovilidadRastreoDto.toDomain():
        MovilidadRastreo {

    var fechaUbicacion =
        ""


    if (
        updatedAt != null
    ) {
        fechaUbicacion =
            updatedAt
    }


    return MovilidadRastreo(
        disponible =
            disponible,

        enCurso =
            enCurso,

        conductor =
            conductor,

        vehiculo =
            vehiculo,

        placa =
            placa,

        ruta =
            ruta,

        tieneUbicacion =
            tieneUbicacion,

        latitud =
            latitud,

        longitud =
            longitud,

        updatedAt =
            fechaUbicacion
    )
}