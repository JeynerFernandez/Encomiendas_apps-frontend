package pe.edu.pe.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import pe.edu.pe.presentation.theme.LocalMandaYaSemanticColors

/** Semantic variants supported by [InlineMessage]. */
enum class InlineMessageType {
    SUCCESS,
    ERROR,
    WARNING,
    INFO
}

data class InlineMessageAction(
    val label: String,
    val onClick: () -> Unit
)

/**
 * Visual state for an [InlineMessage]. State ownership remains with the caller.
 */
data class InlineMessageModel(
    val type: InlineMessageType,
    val description: String,
    val title: String? = null,
    val action: InlineMessageAction? = null,
    val dismissible: Boolean = false,
    val onDismiss: (() -> Unit)? = null,
    val icon: ImageVector? = null,
    val iconContentDescription: String? = null
)

private data class InlineMessageStyle(
    val container: Color,
    val content: Color,
    val accent: Color,
    val icon: ImageVector
)

@Composable
fun InlineMessage(
    message: InlineMessageModel,
    modifier: Modifier = Modifier
) {
    if (message.description.isBlank()) {
        return
    }

    val style = message.type.style()
    val onDismiss = message.onDismiss
    val action = message.action
    Card(
        modifier = modifier
            .fillMaxWidth()
            .semantics { liveRegion = LiveRegionMode.Polite },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = style.container),
        border = BorderStroke(
            width = 1.dp,
            color = style.accent.copy(alpha = 0.45f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = message.icon ?: style.icon,
                    contentDescription = message.iconContentDescription,
                    tint = style.accent,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (!message.title.isNullOrBlank()) {
                    Text(
                        text = message.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = style.content
                    )
                }

                Text(
                    text = message.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = style.content
                )

                if (action != null && action.label.isNotBlank()) {
                    TextButton(
                        onClick = action.onClick,
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                    ) {
                        Text(
                            text = action.label,
                            color = style.accent,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }

            if (message.dismissible && onDismiss != null) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar mensaje",
                        tint = style.content
                    )
                }
            }
        }
    }
}

@Composable
private fun InlineMessageType.style(): InlineMessageStyle {
    val scheme = MaterialTheme.colorScheme
    val semanticColors = LocalMandaYaSemanticColors.current

    return when (this) {
        InlineMessageType.SUCCESS -> InlineMessageStyle(
            container = semanticColors.successContainer,
            content = semanticColors.onSuccessContainer,
            accent = semanticColors.success,
            icon = Icons.Default.CheckCircle
        )

        InlineMessageType.ERROR -> InlineMessageStyle(
            container = scheme.errorContainer,
            content = scheme.onErrorContainer,
            accent = scheme.error,
            icon = Icons.Default.ErrorOutline
        )

        InlineMessageType.WARNING -> InlineMessageStyle(
            container = scheme.secondaryContainer,
            content = scheme.onSecondaryContainer,
            accent = scheme.secondary,
            icon = Icons.Default.Warning
        )

        InlineMessageType.INFO -> InlineMessageStyle(
            container = scheme.primaryContainer,
            content = scheme.onPrimaryContainer,
            accent = scheme.primary,
            icon = Icons.Default.Info
        )
    }
}
