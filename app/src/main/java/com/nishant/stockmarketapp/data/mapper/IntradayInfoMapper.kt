package com.nishant.stockmarketapp.data.mapper

import com.nishant.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.nishant.stockmarketapp.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun IntradayInfoDto.toIntradatInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"

    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime= LocalDateTime.parse(timestamp, formatter)

    return IntradayInfo(
        date = localDateTime,
        close = close
    )
}