package pe.edu.pe.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import pe.edu.pe.Datas.Notification.LocalNotificationPlataform
import pe.edu.pe.MainActivity
import java.security.Security

class AndroidLocalNotificationPlataform (
    private val context: Context ):
    LocalNotificationPlataform {
    override suspend fun showTestNotification(): Result<Unit>  = runCatching {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS
        )!= PackageManager.PERMISSION_GRANTED
        ){
            throw SecurityException("Consed el permiso para las notificcaciones")
        }
        val notificationManager = NotificationManagerCompat.from(context)
        if (!notificationManager.areNotificationsEnabled()){
            throw SecurityException("Las notificaciones estas bloqueadas en los ajustes de andrios")

        }
        createNotificationChanel()
        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val peddingInetnt = PendingIntent.getActivity(
            context, TEST_NOTIFICATION_ID, openAppIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        val notification = NotificationCompat.Builder(context, CHANEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Manda_Ya!!")
            .setContentText("Las notificaiones locales estan funcionando")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(peddingInetnt)
            .build()
        notificationManager.notify(TEST_NOTIFICATION_ID, notification)
    }
    private fun createNotificationChanel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val chanel = NotificationChannel(
                CHANEL_ID, "nOTIIFCAciones de pruebas", NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description ="Canal para verificar lals notificaciones local"
            }
            context.getSystemService(NotificationManager::class.java)
                .createNotificationChannel(chanel)
        }
    }
    private companion object{
        const val CHANEL_ID ="Manda_yaLocal:Notifications"
        const val TEST_NOTIFICATION_ID=1001
    }
}