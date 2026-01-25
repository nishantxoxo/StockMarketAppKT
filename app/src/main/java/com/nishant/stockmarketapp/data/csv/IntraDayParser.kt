package com.nishant.stockmarketapp.data.csv

import com.nishant.stockmarketapp.data.mapper.toIntradatInfo
import com.nishant.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.nishant.stockmarketapp.domain.model.CompanyListingModel
import com.nishant.stockmarketapp.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntraDayParser @Inject constructor(

): CSVParser<IntradayInfo> {
    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))

        return withContext(Dispatchers.IO){
            csvReader.readAll().drop(1)
                .mapNotNull {
                    line->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null

                    val dto = IntradayInfoDto(
                        timestamp = timestamp,
                        close = close.toDouble()
                    )

                    dto.toIntradatInfo()

//                    IntradayInfo(
//                        timestamp = timestamp ?: return@mapNotNull null,
//                        symbol = symbol ?: return@mapNotNull null,
//                        exchange = exchange ?: return@mapNotNull null,
//
//                    )
                }
                .filter {
                    it.date.dayOfMonth == LocalDate.now().minusDays(5).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close()
                }
        }
    }

}