package com.nishant.stockmarketapp.data.repository

import coil.network.HttpException
import com.nishant.stockmarketapp.data.csv.CSVParser
import com.nishant.stockmarketapp.data.csv.CompanyListingParser
import com.nishant.stockmarketapp.data.csv.IntraDayParser
import com.nishant.stockmarketapp.data.local.StockDatabase
import com.nishant.stockmarketapp.data.mapper.toCompanyInfo
import com.nishant.stockmarketapp.data.mapper.toCompanyListing
import com.nishant.stockmarketapp.data.mapper.toCompanyListingEntity
import com.nishant.stockmarketapp.data.remote.StockApi
import com.nishant.stockmarketapp.domain.model.CompanyInfo
import com.nishant.stockmarketapp.domain.model.CompanyListingModel
import com.nishant.stockmarketapp.domain.model.IntradayInfo
import com.nishant.stockmarketapp.domain.repository.StockRepository
import com.nishant.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDatabase,
    val companyListingParser: CSVParser<CompanyListingModel>,
    val intraDayParser: CSVParser<IntradayInfo>

): StockRepository {

    private val dao = db.dao


    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListingModel>>> {

        return flow {
            emit(Resource.Loading(true))
            val localLisitngs =dao.searchCompanyListing(query)

            emit(Resource.Success(
                data = localLisitngs.map { it.toCompanyListing() }
            ))

            val isDbEmpty = localLisitngs.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {

                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
//                response.byteStream()


            } catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldnt load data"))
                null
            } catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("couldnt load data from internet"))
                null
            }

            remoteListings?.let {
                it->



                dao.clearCompanyListings()
                dao.insertCompanyListings(it.map { it.toCompanyListingEntity() })

                emit(Resource.Success(
                    data = dao.searchCompanyListing("").map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))

            }

        }
    }


    override suspend fun getIntrdayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntraInfo(symbol)
            val result = intraDayParser.parse(response.byteStream())

            Resource.Success(result)
        } catch (e: IOException){
        e.printStackTrace()
            Resource.Error(message = "COuldnt load intraday info")
        } catch (e: HttpException){
          e.printStackTrace()
            Resource.Error(message = "COuldnt load intraday info")

        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            Resource.Success(result.toCompanyInfo())
        } catch (e: IOException){
            e.printStackTrace()
            Resource.Error(message = "COuldnt load company info")
        } catch (e: HttpException){
            e.printStackTrace()
            Resource.Error(message = "COuldnt load company info")

        }
    }
}