package com.nishant.stockmarketapp.data.repository

import coil.network.HttpException
import com.nishant.stockmarketapp.data.local.StockDatabase
import com.nishant.stockmarketapp.data.mapper.toCompanyListing
import com.nishant.stockmarketapp.data.remote.StockApi
import com.nishant.stockmarketapp.domain.model.CompanyListingModel
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
    val db: StockDatabase
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
                response.byteStream()


            } catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldnt load data"))
            } catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("couldnt load data from internet"))
            }



        }
    }

}