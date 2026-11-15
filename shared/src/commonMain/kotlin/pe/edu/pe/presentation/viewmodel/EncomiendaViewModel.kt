package pe.edu.pe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.pe.domain.UseCase.RegistrarEncomiendaUseCase
import pe.edu.pe.domain.model.RegistroEncomienda


class EncomiendaViewModel(

    private val registrarEncomiendaUseCase:
    RegistrarEncomiendaUseCase

) : ViewModel() {


    // Registra una encomienda en el backend
    fun registrar(

        remitenteId: Int,

        destinatarioId: Int,

        sedeOrigenId: Int,

        sedeDestinoId: Int,

        altoCm: Double,

        anchoCm: Double,

        largoCm: Double,

        pesoKg: Double,

        modalidadPago: String,

        observacion: String?,

        onSuccess:
            (RegistroEncomienda) -> Unit,

        onError:
            (String) -> Unit
    ) {

        viewModelScope.launch {

            registrarEncomiendaUseCase(

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
                .onSuccess {

                    onSuccess(
                        it
                    )
                }

                .onFailure {

                    onError(
                        it.message
                            ?: "No se pudo registrar la encomienda"
                    )
                }
        }
    }
}