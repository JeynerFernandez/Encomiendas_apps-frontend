package pe.edu.pe.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import encomienda_appmovil.shared.generated.resources.Res
import encomienda_appmovil.shared.generated.resources.logo_mandaya

@Composable
fun SplashContent(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(Res.drawable.logo_mandaya),
            contentDescription = "MandaYa",
            modifier = Modifier.size(180.dp),
            contentScale = ContentScale.Fit
        )
    }
}
