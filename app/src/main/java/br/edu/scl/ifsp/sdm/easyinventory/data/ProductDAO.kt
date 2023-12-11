package br.edu.scl.ifsp.sdm.easyinventory.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDAO {
    @Insert
    suspend fun insert(product: Product)
    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)
    @Query("SELECT * FROM product ORDER BY productName")
    fun getAllProducts():LiveData<List<Product>>
    @Query("SELECT * FROM product WHERE productCode=:id")
    fun getContactById(id: Int): LiveData<Product>

}