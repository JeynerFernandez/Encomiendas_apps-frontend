package pe.edu.pe.domain.usecase

import pe.edu.pe.domain.model.RastreoPedido
import pe.edu.pe.domain.repository.TrackingRepository


class TrackingUseCase(
    private val repository:
    TrackingRepository
) {


    suspend fun rastrear(
        codigoRastreo: String,
        clave: String
    ): Result<RastreoPedido> {

        val codigo =
            codigoRastreo
                .trim()


        if (
            codigo.isBlank()
        ) {

            return Result.failure(
                Exception(
                    "Ingrese el código de rastreo"
                )
            )
        }


        if (
            clave.length != 4
        ) {

            return Result.failure(
                Exception(
                    "La clave debe contener 4 dígitos"
                )
            )
        }


        for (
        caracter in clave
        ) {

            if (
                !caracter.isDigit()
            ) {

                return Result.failure(
                    Exception(
                        "La clave debe contener únicamente números"
                    )
                )
            }
        }


        return repository.rastrear(
            codigoRastreo =
                codigo,

            clave =
                clave
        )
    }
}