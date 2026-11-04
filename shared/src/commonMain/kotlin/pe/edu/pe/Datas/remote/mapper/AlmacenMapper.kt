package pe.edu.pe.Datas.remote.mapper

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import pe.edu.pe.Datas.remote.dto.EncomiendaAlmacenDto
import pe.edu.pe.Datas.remote.dto.IngresoAlmacenResponseDto
import pe.edu.pe.Datas.remote.dto.ListaEncomiendasAlmacenDto
import pe.edu.pe.Datas.remote.dto.SedeAlmacenDto
import pe.edu.pe.domain.model.EncomiendaAlmacen
import pe.edu.pe.domain.model.IngresoAlmacen
import pe.edu.pe.domain.model.ListaEncomiendasAlmacen
import pe.edu.pe.domain.model.SedeAlmacen
import pe.edu.pe.domain.model.TrasladoAsignadoAlmacen

fun SedeAlmacenDto.toDomain(): SedeAlmacen {

    return SedeAlmacen(
        id = id,
        name = name
    )
}

fun EncomiendaAlmacenDto.toDomain(): EncomiendaAlmacen {

    val remitenteCompleto =
        "${remitente.nombres} ${remitente.apellidoPaterno} ${remitente.apellidoMaterno}"
            .trim()

    val destinatarioCompleto =
        "${destinatario.nombres} ${destinatario.apellidoPaterno} ${destinatario.apellidoMaterno}"
            .trim()

    var modalidadPago: String? =
        null

    var estadoPago: String? =
        null

    val pagoGuardado =
        pago

    if (
        pagoGuardado != null
    ) {

        modalidadPago =
            pagoGuardado.modalidad

        estadoPago =
            pagoGuardado.estado
    }

    var trasladoDomain:
            TrasladoAsignadoAlmacen? =
        null

    val trasladoGuardado =
        trasladoAsignado

    if (
        trasladoGuardado != null
    ) {

        val recorrido =
            mutableListOf<String>()

        for (
        sede in trasladoGuardado.ruta.recorrido
        ) {

            if (
                sede.isNotBlank()
            ) {

                recorrido.add(
                    sede.trim()
                )
            }
        }

        trasladoDomain =
            TrasladoAsignadoAlmacen(
                id =
                    trasladoGuardado.id,

                estado =
                    trasladoGuardado.estado,

                vehiculoId =
                    trasladoGuardado.vehiculo.id,

                placa =
                    trasladoGuardado.vehiculo.placa,

                vehiculoDescripcion =
                    trasladoGuardado.vehiculo.descripcion,

                conductorId =
                    trasladoGuardado.conductor.id,

                conductorNombre =
                    trasladoGuardado.conductor.name,

                rutaId =
                    trasladoGuardado.ruta.id,

                rutaNombre =
                    trasladoGuardado.ruta.name,

                recorrido =
                    recorrido
            )
    }

    return EncomiendaAlmacen(
        id =
            id,

        codigoRastreo =
            codigoRastreo,

        estado =
            estado,

        remitente =
            remitenteCompleto,

        destinatario =
            destinatarioCompleto,

        sedeOrigenId =
            sedeOrigen.id,

        sedeOrigen =
            sedeOrigen.name,

        sedeDestinoId =
            sedeDestino.id,

        sedeDestino =
            sedeDestino.name,

        pesoKg =
            pesoKg.toDoubleSafe(),

        costoTotal =
            costoTotal.toDoubleSafe(),

        observacion =
            observacion,

        modalidadPago =
            modalidadPago,

        estadoPago =
            estadoPago,

        trasladoAsignado =
            trasladoDomain
    )
}

fun ListaEncomiendasAlmacenDto.toDomain():
        ListaEncomiendasAlmacen {

    val lista =
        mutableListOf<EncomiendaAlmacen>()

    for (
    encomienda in encomiendas
    ) {

        lista.add(
            encomienda.toDomain()
        )
    }

    return ListaEncomiendasAlmacen(
        filtro =
            filtro,

        sedeOrigen =
            sedeOrigen.toDomain(),

        sedeDestino =
            sedeDestino.toDomain(),

        total =
            total,

        encomiendas =
            lista
    )
}

fun IngresoAlmacenResponseDto.toDomain():
        IngresoAlmacen {

    return IngresoAlmacen(
        message =
            message,

        id =
            encomienda.id,

        codigoRastreo =
            encomienda.codigoRastreo,

        estado =
            encomienda.estado,

        sedeActual =
            encomienda.sedeActual.toDomain()
    )
}

private fun JsonElement.toDoubleSafe():
        Double {

    if (
        this is JsonPrimitive
    ) {

        val contenido =
            content.trim()

        if (
            contenido.isNotBlank()
        ) {

            val numero =
                contenido.toDoubleOrNull()

            if (
                numero != null
            ) {

                return numero
            }
        }
    }

    return 0.0
}