package com.nishant.stockmarketapp.di

import com.nishant.stockmarketapp.data.csv.CSVParser
import com.nishant.stockmarketapp.data.csv.CompanyListingParser
import com.nishant.stockmarketapp.data.csv.IntraDayParser
import com.nishant.stockmarketapp.data.repository.StockRepositoryImpl
import com.nishant.stockmarketapp.domain.model.CompanyListingModel
import com.nishant.stockmarketapp.domain.model.IntradayInfo
import com.nishant.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListingModel>


    @Binds
    @Singleton
    abstract fun bindsStockRepo(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(
        intraDayParser: IntraDayParser
    ): CSVParser<IntradayInfo>

}