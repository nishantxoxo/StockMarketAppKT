package com.nishant.stockmarketapp.domain.repository

import com.nishant.stockmarketapp.domain.model.CompanyListingModel
import com.nishant.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String

    ):Flow<Resource<List<CompanyListingModel>>>
}