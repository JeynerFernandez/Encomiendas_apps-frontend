package pe.edu.pe.Datas.remote.mapper

import pe.edu.pe.Datas.remote.dto.MarkAllNotificationsReadResponseDto
import pe.edu.pe.Datas.remote.dto.MarkNotificationReadResponseDto
import pe.edu.pe.Datas.remote.dto.RemoteNotificationDto
import pe.edu.pe.Datas.remote.dto.RemoteNotificationEncomiendaDto
import pe.edu.pe.Datas.remote.dto.UnreadNotificationCountDto
import pe.edu.pe.domain.model.RemoteNotification
import pe.edu.pe.domain.model.RemoteNotificationEncomienda
import pe.edu.pe.domain.model.MarkedAllNotificationsRead
import pe.edu.pe.domain.model.MarkedNotificationRead

fun RemoteNotificationDto.toDomain(): RemoteNotification {
    return RemoteNotification(
        id = id,
        type = type,
        title = title,
        message = message,
        isRead = isRead,
        readAt = readAt,
        createdAt = createdAt,
        metadata = metadata,
        encomienda = encomienda?.toDomain()
    )
}

fun RemoteNotificationEncomiendaDto.toDomain(): RemoteNotificationEncomienda {
    return RemoteNotificationEncomienda(
        id = id,
        codigoRastreo = codigoRastreo,
        estado = estado
    )
}

fun UnreadNotificationCountDto.toDomain(): Int = count

fun MarkNotificationReadResponseDto.toDomain(): MarkedNotificationRead {
    return MarkedNotificationRead(
        id = id,
        isRead = isRead,
        readAt = readAt
    )
}

fun MarkAllNotificationsReadResponseDto.toDomain(): MarkedAllNotificationsRead {
    return MarkedAllNotificationsRead(
        updated = updated
    )
}
