package com.nishant.stockmarketapp.data.mapper

import com.nishant.stockmarketapp.data.local.CompanyListingEntity
import com.nishant.stockmarketapp.data.remote.dto.CompanyInfoDto
import com.nishant.stockmarketapp.domain.model.CompanyInfo
import com.nishant.stockmarketapp.domain.model.CompanyListingModel

fun CompanyListingEntity.toCompanyListing(): CompanyListingModel{
    return CompanyListingModel(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListingModel.toCompanyListingEntity(): CompanyListingEntity{
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}


fun CompanyInfoDto.toCompanyInfo() :CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description  ?: "",
        name = name  ?: "",
        country = country  ?: "",
        industry = industry  ?: "",
    )
}