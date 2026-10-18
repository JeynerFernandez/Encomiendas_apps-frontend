package pe.edu.pe.presentation.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun ScaledText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: Float = 14f,
    fontWeight: FontWeight? = null,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    lineHeight: Float? = null
) {
    val scale = LocalFontSizeScale.current

    Text(
        text = text,
        modifier = modifier,
        fontSize = (fontSize * scale).sp,
        fontWeight = fontWeight,
        color = color,
        textAlign = textAlign,
        lineHeight = if (lineHeight != null) (lineHeight * scale).sp else androidx.compose.ui.unit.TextUnit.Unspecified
    )
}