package pe.edu.pe.Datas.remote.mapper

import pe.edu.pe.Datas.remote.dto.ConductorMovilidadDto
import pe.edu.pe.Datas.remote.dto.DestinatarioMovilidadDto
import pe.edu.pe.Datas.remote.dto.DetalleEncomiendaMovilidadDto
import pe.edu.pe.Datas.remote.dto.EncomiendaMovilidadDto
import pe.edu.pe.Datas.remote.dto.ListaTrasladosMovilidadDto
import pe.edu.pe.Datas.remote.dto.ResumenGeneralMovilidadDto
import pe.edu.pe.Datas.remote.dto.ResumenTrasladoMovilidadDto
import pe.edu.pe.Datas.remote.dto.RutaMovilidadDto
import pe.edu.pe.Datas.remote.dto.SedeMovilidadDto
import pe.edu.pe.Datas.remote.dto.SedeRutaMovilidadDto
import pe.edu.pe.Datas.remote.dto.TrasladoMovilidadDto
import pe.edu.pe.Datas.remote.dto.VehiculoMovilidadDto

import pe.edu.pe.domain.model.ConductorMovilidad
import pe.edu.pe.domain.model.DestinatarioMovilidad
import pe.edu.pe.domain.model.DetalleEncomiendaMovilidad
import pe.edu.pe.domain.model.EncomiendaMovilidad
import pe.edu.pe.domain.model.ListaTrasladosMovilidad
import pe.edu.pe.domain.model.ResumenGeneralMovilidad
import pe.edu.pe.domain.model.ResumenTrasladoMovilidad
import pe.edu.pe.domain.model.RutaMovilidad
import pe.edu.pe.domain.model.SedeMovilidad
import pe.edu.pe.domain.model.SedeRutaMovilidad
import pe.edu.pe.domain.model.TrasladoMovilidad
import pe.edu.pe.domain.model.VehiculoMovilidad


fun ListaTrasladosMovilidadDto.toDomain():
        ListaTrasladosMovilidad {

    return ListaTrasladosMovilidad(

        conductor =
            conductor.toDomain(),

        resumen =
            resumen.toDomain(),

        traslados =
            traslados.map {
                it.toDomain()
            }
    )
}


fun ConductorMovilidadDto.toDomain():
        ConductorMovilidad {

    return ConductorMovilidad(

        id =
            id,

        name =
            name
    )
}


fun ResumenGeneralMovilidadDto.toDomain():
        ResumenGeneralMovilidad {

    return ResumenGeneralMovilidad(

        total =
            total,

        programados =
            programados,

        enCurso =
            enCurso,

        finalizados =
            finalizados,

        cancelados =
            cancelados
    )
}


fun TrasladoMovilidadDto.toDomain():
        TrasladoMovilidad {

    var salida =
        ""

    if (
        salidaAt != null
    ) {

        salida =
            salidaAt
    }


    var llegada =
        ""

    if (
        llegadaAt != null
    ) {

        llegada =
            llegadaAt
    }


    var created =
        ""

    if (
        createdAt != null
    ) {

        created =
            createdAt
    }


    var updated =
        ""

    if (
        updatedAt != null
    ) {

        updated =
            updatedAt
    }


    return TrasladoMovilidad(

        id =
            id,

        estado =
            estado,

        salidaAt =
            salida,

        llegadaAt =
            llegada,

        createdAt =
            created,

        updatedAt =
            updated,

        ruta =
            ruta.toDomain(),

        vehiculo =
            vehiculo.toDomain(),

        conductor =
            conductor.toDomain(),

        resumen =
            resumen.toDomain(),

        encomiendas =
            encomiendas.map {
                it.toDomain()
            }
    )
}


fun RutaMovilidadDto.toDomain():
        RutaMovilidad {

    return RutaMovilidad(

        id =
            id,

        name =
            name,

        recorrido =
            recorrido.map {
                it.toDomain()
            }
    )
}


fun SedeRutaMovilidadDto.toDomain():
        SedeRutaMovilidad {

    return SedeRutaMovilidad(

        orden =
            orden,

        sede =
            sede.toDomain()
    )
}


fun SedeMovilidadDto.toDomain():
        SedeMovilidad {

    return SedeMovilidad(

        id =
            id,

        name =
            name
    )
}


fun VehiculoMovilidadDto.toDomain():
        VehiculoMovilidad {

    return VehiculoMovilidad(

        id =
            id,

        placa =
            placa,

        descripcion =
            descripcion,

        capacidadKg =
            capacidadKg
    )
}


fun ResumenTrasladoMovilidadDto.toDomain():
        ResumenTrasladoMovilidad {

    return ResumenTrasladoMovilidad(

        totalEncomiendas =
            totalEncomiendas,

        pendientesDescarga =
            pendientesDescarga,

        descargadas =
            descargadas
    )
}


fun DetalleEncomiendaMovilidadDto.toDomain():
        DetalleEncomiendaMovilidad {

    var fechaDescarga =
        ""

    if (
        descargadoAt != null
    ) {

        fechaDescarga =
            descargadoAt
    }


    return DetalleEncomiendaMovilidad(

        detalleId =
            detalleId,

        descargadoAt =
            fechaDescarga,

        encomienda =
            encomienda.toDomain()
    )
}


fun EncomiendaMovilidadDto.toDomain():
        EncomiendaMovilidad {

    return EncomiendaMovilidad(

        id =
            id,

        codigoRastreo =
            codigoRastreo,

        observacion =
            observacion,

        altoCm =
            altoCm,

        anchoCm =
            anchoCm,

        largoCm =
            largoCm,

        pesoKg =
            pesoKg,

        estado =
            estado,

        destinatario =
            destinatario.toDomain(),

        sedeDestino =
            sedeDestino.toDomain(),

        ultimaSede =
            ultimaSede.toDomain()
    )
}


fun DestinatarioMovilidadDto.toDomain():
        DestinatarioMovilidad {

    return DestinatarioMovilidad(

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