package pe.edu.pe.di

import com.russhwolf.settings.Settings
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

import pe.edu.pe.Datas.local.SessionManager
import pe.edu.pe.Datas.remote.api.ApiClient
import pe.edu.pe.Datas.repository.AlmacenRepositoryImpl
import pe.edu.pe.Datas.repository.EncomiendaRepositoryImpl
import pe.edu.pe.Datas.repository.EntregaRecepcionRepositoryImpl
import pe.edu.pe.Datas.repository.MovilidadRepositoryImpl
import pe.edu.pe.Datas.repository.PersonaRepositoryImpl
import pe.edu.pe.Datas.repository.RutaRepositoryImpl
import pe.edu.pe.Datas.repository.TarifaRepositoryImpl
import pe.edu.pe.Datas.repository.TrackingRepositoryImpl

import pe.edu.pe.data.repository.AuthRepositoryImpl
import pe.edu.pe.data.repository.SedeRepositoryImpl
import pe.edu.pe.domain.UseCase.EntregaRecepcionUseCase
import pe.edu.pe.domain.UseCase.GetPersonaByDniUseCase
import pe.edu.pe.domain.UseCase.GetPersonasUseCase

import pe.edu.pe.domain.repository.AuthRepository
import pe.edu.pe.domain.repository.SedeRepository

import pe.edu.pe.domain.UseCase.LoginUseCase
import pe.edu.pe.domain.UseCase.GetSedesUseCase
import pe.edu.pe.domain.UseCase.GetTarifasUseCase
import pe.edu.pe.domain.UseCase.MovilidadUseCase
import pe.edu.pe.domain.UseCase.RegistrarEncomiendaUseCase
import pe.edu.pe.domain.UseCase.RutaUseCase
import pe.edu.pe.domain.UseCase.SavePersonaUseCase
import pe.edu.pe.domain.UseCase.SaveTarifaUseCase
import pe.edu.pe.domain.repository.AlmacenRepository
import pe.edu.pe.domain.repository.EncomiendaRepository
import pe.edu.pe.domain.repository.EntregaRecepcionRepository
import pe.edu.pe.domain.repository.MovilidadRepository
import pe.edu.pe.domain.repository.PersonaRepository
import pe.edu.pe.domain.repository.RutaRepository
import pe.edu.pe.domain.repository.TarifaRepository
import pe.edu.pe.domain.repository.TrackingRepository
import pe.edu.pe.domain.usecase.AlmacenUseCase
import pe.edu.pe.domain.usecase.SaveSedeUseCase
import pe.edu.pe.domain.usecase.TrackingUseCase
import pe.edu.pe.presentation.viewmodel.AlmacenViewModel

import pe.edu.pe.presentation.viewmodel.AuthViewModel
import pe.edu.pe.presentation.viewmodel.EncomiendaViewModel
import pe.edu.pe.presentation.viewmodel.EntregaRecepcionViewModel
import pe.edu.pe.presentation.viewmodel.MovilidadViewModel
import pe.edu.pe.presentation.viewmodel.PersonaViewModel
import pe.edu.pe.presentation.viewmodel.RutaViewModel
import pe.edu.pe.presentation.viewmodel.SedeViewModel
import pe.edu.pe.presentation.viewmodel.TarifaViewModel
import pe.edu.pe.presentation.viewmodel.TrackingViewModel


val appModule = module {

    // Cliente HTTP
    single {
        ApiClient.httpClient
    }

    // Almacenamiento local
    single {
        Settings()
    }
    single<TarifaRepository> {
        TarifaRepositoryImpl(
            get(),
            get()
        )
    }

    // Sesión compartida para guardar JWT y usuario
    single {
        SessionManager(
            get()
        )
    }

    // Repository de autenticación
    single<AuthRepository> {
        AuthRepositoryImpl(
            get(),
            get()
        )
    }

    // Repository de sedes
    single<SedeRepository> {
        SedeRepositoryImpl(
            get(),
            get()
        )
    }
    // Repository warehouse
    single<AlmacenRepository> {
        AlmacenRepositoryImpl(
            client = get(),
            sessionManager = get()
        )
    }
    single<PersonaRepository> {

        PersonaRepositoryImpl(
            get(),
            get()
        )
    }
    single<EncomiendaRepository> {

        EncomiendaRepositoryImpl(
            get(),
            get()
        )
    }
    single<RutaRepository> {
        RutaRepositoryImpl(
            client = get(),
            sessionManager = get()
        )
    }
    single<MovilidadRepository> {

        MovilidadRepositoryImpl(
            client =
                get(),

            sessionManager =
                get()
        )
    }
    single<EntregaRecepcionRepository> {

        EntregaRecepcionRepositoryImpl(

            client =
                get(),

            sessionManager =
                get()
        )
    }
    single<TrackingRepository> {

        TrackingRepositoryImpl(
            client =
                get(),

            sessionManager =
                get()
        )
    }


    // Login
    factory {
        LoginUseCase(
            get()
        )
    }

    // Obtener sedes
    factory {
        GetSedesUseCase(
            get()
        )
    }
    factory {
        SaveTarifaUseCase(
            get()
        )
    }
    factory {
        TarifaViewModel(
            get(),
            get()
        )
    }
    factory {

        MovilidadUseCase(
            repository =
                get()
        )
    }



    factory {
        GetTarifasUseCase(
            get()
        )
    }

    // Crear sede
    factory {
        SaveSedeUseCase(
            get()
        )
    }

    // ViewModel de autenticación
    factory {
        AuthViewModel(
            get()
        )
    }

    // ViewModel de sedes
    factory {
        SedeViewModel(
            get(),
            get()
        )
    }
    factory {

        GetPersonasUseCase(
            get()
        )
    }


    factory {

        GetPersonaByDniUseCase(
            get()
        )
    }


    factory {

        SavePersonaUseCase(
            get()
        )
    }


// ViewModel de personas
    factory {

        PersonaViewModel(
            get(),
            get(),
            get()
        )
    }
    factory {

        RegistrarEncomiendaUseCase(
            get()
        )
    }


// ViewModel
    factory {

        EncomiendaViewModel(
            get()
        )
    }
    // ViewModel
    factory {
        AlmacenUseCase(
            repository = get()
        )
    }

    factory {
        AlmacenViewModel(
            almacenUseCase = get(),
            sedeRepository = get(),
            sessionManager = get()
        )
    }
    factory {
        RutaUseCase(
            repository = get()
        )
    }

    factory {
        RutaViewModel(
            rutaUseCase = get(),
            sedeRepository = get()
        )
    }

   factory  {

        MovilidadViewModel(
            movilidadUseCase =
                get()
        )
    }
    factory {

        EntregaRecepcionUseCase(

            repository =
                get()
        )
    }


    factory {

        EntregaRecepcionViewModel(

            useCase =
                get()
        )
    }
    factory {

        TrackingUseCase(
            repository =
                get()
        )
    }
    factory {

        TrackingViewModel(
            useCase =
                get()
        )
    }
}

