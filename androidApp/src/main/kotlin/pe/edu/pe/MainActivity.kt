package pe.edu.pe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pe.edu.pe.notifications.AndroidLocalNotificationPlataform

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val notificationPlatform = AndroidLocalNotificationPlataform(applicationContext)

        setContent {
            App(notificationPlatform = notificationPlatform)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}