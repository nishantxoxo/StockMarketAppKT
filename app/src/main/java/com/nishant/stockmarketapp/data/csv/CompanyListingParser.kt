package com.nishant.stockmarketapp.data.csv

import com.nishant.stockmarketapp.domain.model.CompanyListingModel
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListingParser @Inject constructor(

): CSVParser<CompanyListingModel> {
    override suspend fun parse(stream: InputStream): List<CompanyListingModel> {
        val csvReader = CSVReader()
    }

}