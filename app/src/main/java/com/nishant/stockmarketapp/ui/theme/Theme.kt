package com.nishant.stockmarketapp.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.nishant.stockmarketapp.ui.theme.Pink40
import com.nishant.stockmarketapp.ui.theme.Pink80
import com.nishant.stockmarketapp.ui.theme.Purple40
import com.nishant.stockmarketapp.ui.theme.Purple80
import com.nishant.stockmarketapp.ui.theme.PurpleGrey40
import com.nishant.stockmarketapp.ui.theme.PurpleGrey80

private val DarkColorScheme = darkColorScheme(
    primary = Color.Green,

    background = DarkBlue,
    onPrimary = Color.DarkGray,
    onBackground = TextWhite,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)
@Composable
fun StockMarketAppTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}