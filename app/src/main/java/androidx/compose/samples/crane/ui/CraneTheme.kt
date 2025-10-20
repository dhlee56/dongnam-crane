package androidx.compose.samples.crane.ui
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
//import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.samples.crane.ui.craneTypography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val crane_caption = Color.Companion.DarkGray
val crane_divider_color = Color.Companion.LightGray
private val crane_red = Color(0xFFE30425)
private val crane_white = Color.Companion.White
private val crane_purple_700 = Color(0xFF720D5D)
private val crane_purple_800 = Color(0xFF5D1049)
private val crane_purple_900 = Color(0xFF4E0D3A)

val craneColors = lightColorScheme(
    primary = crane_purple_800,
    secondary = crane_red,
    surface = crane_purple_900,
    onSurface = crane_white,
//    primaryVariant = crane_purple_700
)

val BottomSheetShape = RoundedCornerShape(
    topStart = 20.dp,
    topEnd = 20.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
)

@Composable
fun CraneTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = craneColors, typography = craneTypography) {
        content()
    }
}