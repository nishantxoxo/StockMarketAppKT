package com.plcoding.stockmarketapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import retrofit2.http.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingEntities: List<CompanyListingEntity>
    )


    @androidx.room.Query("DELETE FROM companylistingentity")
    suspend fun clearCompanyListings()



    @androidx.room.Query("""
         SELECT * 
         FROM companylistingentity
         WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
         UPPER(:query) == symbol
    """)
    suspend fun searchCompanyListing(query: String): List<CompanyListingEntity>

}