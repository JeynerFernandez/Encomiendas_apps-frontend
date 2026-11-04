package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.model.ListaTrasladosMovilidad
import pe.edu.pe.domain.repository.MovilidadRepository


class MovilidadUseCase(
    private val repository:
    MovilidadRepository
) {


    suspend fun getMisTraslados():
            Result<ListaTrasladosMovilidad> {

        return repository
            .getMisTraslados()
    }


    suspend fun iniciarTraslado(
        trasladoId: Int
    ): Result<Unit> {

        if (
            trasladoId <= 0
        ) {

            return Result.failure(
                Exception(
                    "Traslado no válido"
                )
            )
        }


        return repository
            .iniciarTraslado(
                trasladoId
            )
    }


    suspend fun finalizarTraslado(
        trasladoId: Int
    ): Result<Unit> {

        if (
            trasladoId <= 0
        ) {

            return Result.failure(
                Exception(
                    "Traslado no válido"
                )
            )
        }


        return repository
            .finalizarTraslado(
                trasladoId
            )
    }


    suspend fun retornarASedeCentral(
        trasladoId: Int
    ): Result<Unit> {

        if (
            trasladoId <= 0
        ) {

            return Result.failure(
                Exception(
                    "Traslado no válido"
                )
            )
        }


        return repository
            .retornarASedeCentral(
                trasladoId
            )
    }


    suspend fun descargarPorSede(
        trasladoId: Int,
        sedeId: Int
    ): Result<Unit> {

        if (
            trasladoId <= 0
        ) {

            return Result.failure(
                Exception(
                    "Traslado no válido"
                )
            )
        }


        if (
            sedeId <= 0
        ) {

            return Result.failure(
                Exception(
                    "Sede no válida"
                )
            )
        }


        return repository
            .descargarPorSede(
                trasladoId =
                    trasladoId,

                sedeId =
                    sedeId
            )
    }


    suspend fun actualizarUbicacion(
        trasladoId: Int,
        latitud: Double,
        longitud: Double
    ): Result<Unit> {

        if (
            trasladoId <= 0
        ) {

            return Result.failure(
                Exception(
                    "Traslado no válido"
                )
            )
        }


        if (
            latitud < -90.0 ||
            latitud > 90.0
        ) {

            return Result.failure(
                Exception(
                    "Latitud no válida"
                )
            )
        }


        if (
            longitud < -180.0 ||
            longitud > 180.0
        ) {

            return Result.failure(
                Exception(
                    "Longitud no válida"
                )
            )
        }


        return repository
            .actualizarUbicacion(
                trasladoId =
                    trasladoId,

                latitud =
                    latitud,

                longitud =
                    longitud
            )
    }
}