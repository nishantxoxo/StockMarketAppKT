package com.nishant.stockmarketapp.presentation.company_listings

import androidx.room.Query
import com.nishant.stockmarketapp.domain.model.CompanyListingModel

data class CompanyListingsState(
    val companies: List<CompanyListingModel> = emptyList(),
    val isloading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
