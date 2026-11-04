package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.model.RegistroEncomienda
import pe.edu.pe.domain.repository.EncomiendaRepository


class RegistrarEncomiendaUseCase(

    private val repository:
    EncomiendaRepository

) {

    suspend operator fun invoke(

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

    ): Result<RegistroEncomienda> {

        return repository
            .registrarEncomienda(

                remitenteId =
                    remitenteId,

                destinatarioId =
                    destinatarioId,

                sedeOrigenId =
                    sedeOrigenId,

                sedeDestinoId =
                    sedeDestinoId,

                altoCm =
                    altoCm,

                anchoCm =
                    anchoCm,

                largoCm =
                    largoCm,

                pesoKg =
                    pesoKg,

                modalidadPago =
                    modalidadPago,

                observacion =
                    observacion
            )
    }
}