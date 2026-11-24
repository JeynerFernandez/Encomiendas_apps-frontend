
package pe.edu.pe.di

import com.russhwolf.settings.Settings
import org.koin.dsl.module

import pe.edu.pe.Datas.local.SessionManager
import pe.edu.pe.Datas.remote.api.ApiClient
import pe.edu.pe.Datas.remote.api.NotificationsApi
import pe.edu.pe.Datas.repository.AlmacenRepositoryImpl
import pe.edu.pe.Datas.repository.EncomiendaRepositoryImpl
import pe.edu.pe.Datas.repository.EntregaRecepcionRepositoryImpl
import pe.edu.pe.Datas.repository.MovilidadRepositoryImpl
import pe.edu.pe.Datas.repository.NotificationRepositoryImpl
import pe.edu.pe.Datas.repository.RemoteNotificationsRepositoryImpl
import pe.edu.pe.Datas.repository.PersonaRepositoryImpl
import pe.edu.pe.Datas.repository.RutaRepositoryImpl
import pe.edu.pe.Datas.repository.TarifaRepositoryImpl
import pe.edu.pe.Datas.repository.TrackingRepositoryImpl

import pe.edu.pe.data.repository.AuthRepositoryImpl
import pe.edu.pe.data.repository.SedeRepositoryImpl

import pe.edu.pe.domain.UseCase.EntregaRecepcionUseCase
import pe.edu.pe.domain.UseCase.GetNotificationSettingsUseCase
import pe.edu.pe.domain.UseCase.GetCurrentUserUseCase
import pe.edu.pe.domain.UseCase.GetPersonaByDniUseCase
import pe.edu.pe.domain.UseCase.GetPersonasUseCase
import pe.edu.pe.domain.UseCase.GetRemoteNotificationsUseCase
import pe.edu.pe.domain.UseCase.GetUnreadNotificationsCountUseCase
import pe.edu.pe.domain.UseCase.GetSedesUseCase
import pe.edu.pe.domain.UseCase.GetTarifasUseCase
import pe.edu.pe.domain.UseCase.GetTotalEncomiendasUseCase
import pe.edu.pe.domain.UseCase.LoginUseCase
import pe.edu.pe.domain.UseCase.MarkAllRemoteNotificationsAsReadUseCase
import pe.edu.pe.domain.UseCase.MarkRemoteNotificationAsReadUseCase
import pe.edu.pe.domain.UseCase.GoogleChallengeUseCase
import pe.edu.pe.domain.UseCase.GoogleLoginUseCase
import pe.edu.pe.domain.UseCase.GoogleLinkUseCase
import pe.edu.pe.domain.UseCase.MovilidadUseCase
import pe.edu.pe.domain.UseCase.RegistrarEncomiendaUseCase
import pe.edu.pe.domain.UseCase.RutaUseCase
import pe.edu.pe.domain.UseCase.SavePersonaUseCase
import pe.edu.pe.domain.UseCase.SaveTarifaUseCase
import pe.edu.pe.domain.UseCase.SendTestNotificationUseCase
import pe.edu.pe.domain.UseCase.SetNotificationEnableUseCase

import pe.edu.pe.domain.repository.AlmacenRepository
import pe.edu.pe.domain.repository.AuthRepository
import pe.edu.pe.domain.repository.EncomiendaRepository
import pe.edu.pe.domain.repository.EntregaRecepcionRepository
import pe.edu.pe.domain.repository.MovilidadRepository
import pe.edu.pe.domain.repository.NotificationRepository
import pe.edu.pe.domain.repository.RemoteNotificationsRepository
import pe.edu.pe.domain.repository.PersonaRepository
import pe.edu.pe.domain.repository.RutaRepository
import pe.edu.pe.domain.repository.SedeRepository
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
import pe.edu.pe.presentation.viewmodel.NotificationSettingsViewModel
import pe.edu.pe.presentation.viewmodel.PersonaViewModel
import pe.edu.pe.presentation.viewmodel.RutaViewModel
import pe.edu.pe.presentation.viewmodel.RemoteNotificationsViewModel
import pe.edu.pe.presentation.viewmodel.ReceptionViewModel
import pe.edu.pe.presentation.viewmodel.SedeViewModel
import pe.edu.pe.presentation.viewmodel.TarifaViewModel
import pe.edu.pe.presentation.viewmodel.TrackingViewModel

val appModule = module {

    single {

        ApiClient.httpClient

    }

    single {

        Settings()

    }

    single<TarifaRepository> {

        TarifaRepositoryImpl(
            get(),
            get()
        )

    }

    single {

        SessionManager(
            get()
        )

    }

    single<NotificationRepository> {

        NotificationRepositoryImpl(
            get(),
            get()
        )

    }

    single<NotificationsApi> {
        NotificationsApi(
            client = get(),
            sessionManager = get()
        )
    }

    single<RemoteNotificationsRepository> {
        RemoteNotificationsRepositoryImpl(
            notificationsApi = get()
        )
    }

    single<AuthRepository> {

        AuthRepositoryImpl(
            get(),
            get()
        )

    }

    single<SedeRepository> {

        SedeRepositoryImpl(
            get(),
            get()
        )

    }

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
            client = get(),
            sessionManager = get()
        )

    }

    single<EntregaRecepcionRepository> {

        EntregaRecepcionRepositoryImpl(
            client = get(),
            sessionManager = get()
        )

    }

    single<TrackingRepository> {

        TrackingRepositoryImpl(
            client = get(),
            sessionManager = get()
        )

    }

    factory {

        LoginUseCase(
            get()
        )

    }

    factory {

        GoogleChallengeUseCase(
            repository = get()
        )

    }

    factory {

        GoogleLoginUseCase(
            repository = get()
        )

    }

    factory {

        GoogleLinkUseCase(
            repository = get()
        )

    }

    factory {

        GetCurrentUserUseCase(
            repository = get()
        )

    }

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
            repository = get()
        )

    }

    factory {

        GetTarifasUseCase(
            get()
        )

    }

    factory {

        SaveSedeUseCase(
            get()
        )

    }

    factory {

        AuthViewModel(
            loginUseCase = get(),
            googleChallengeUseCase = get(),
            googleLoginUseCase = get(),
            googleLinkUseCase = get(),
            getCurrentUserUseCase = get(),
            sessionManager = get()
        )

    }

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

    factory {

        EncomiendaViewModel(
            get()
        )

    }

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

    factory {

        MovilidadViewModel(
            movilidadUseCase = get()
        )

    }

    factory {

        EntregaRecepcionUseCase(
            repository = get()
        )

    }

    factory {

        EntregaRecepcionViewModel(
            useCase = get()
        )

    }

    factory {

        TrackingUseCase(
            repository = get()
        )

    }

    factory {

        TrackingViewModel(
            useCase = get()
        )

    }

    factory {

        GetNotificationSettingsUseCase(
            get()
        )

    }

    factory {

        SetNotificationEnableUseCase(
            get()
        )

    }

    factory {

        SendTestNotificationUseCase(
            get()
        )

    }

    factory {

        NotificationSettingsViewModel(
            get(),
            get(),
            get()
        )

    }

    factory {
        GetTotalEncomiendasUseCase(
            repository = get()
        )
    }

    factory {
        ReceptionViewModel(
            get()
        )
    }

    factory {

        GetRemoteNotificationsUseCase(
            repository = get()
        )

    }

    factory {

        GetUnreadNotificationsCountUseCase(
            repository = get()
        )

    }

    factory {

        MarkRemoteNotificationAsReadUseCase(
            repository = get()
        )

    }

    factory {

        MarkAllRemoteNotificationsAsReadUseCase(
            repository = get()
        )

    }

    factory {

        RemoteNotificationsViewModel(
            getRemoteNotificationsUseCase = get(),
            getUnreadNotificationsCountUseCase = get(),
            markRemoteNotificationAsReadUseCase = get(),
            markAllRemoteNotificationsAsReadUseCase = get()
        )

    }

}
