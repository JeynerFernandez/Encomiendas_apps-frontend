package pe.edu.pe.domain.repository

import pe.edu.pe.domain.model.RegistroEncomienda


interface EncomiendaRepository {

    suspend fun registrarEncomienda(

        remitenteId: Int,

        destinatarioId: Int,

        sedeOrigenId: Int,

        sedeDestinoId: Int,

        altoCm: Double,

        anchoCm: Double,

        largoCm: Double,

        pesoKg: Double,

        modalidadPago: String,

        observacion: String?

    ): Result<RegistroEncomienda>
}