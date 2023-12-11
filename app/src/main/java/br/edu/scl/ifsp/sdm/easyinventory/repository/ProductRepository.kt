package br.edu.scl.ifsp.sdm.easyinventory.repository

import androidx.lifecycle.LiveData
import br.edu.scl.ifsp.sdm.easyinventory.data.Product
import br.edu.scl.ifsp.sdm.easyinventory.data.ProductDAO

class ProductRepository(private val productDAO: ProductDAO) {
    suspend fun insert(product: Product){
        productDAO.insert(product)
    }
    suspend fun update(product: Product){
        productDAO.update(product)
    }

    suspend fun delete(product: Product){
        productDAO.delete(product)
    }
    fun getAllProducts(): LiveData<List<Product>>{
        return productDAO.getAllProducts()
    }
    fun getProductById(id: Int): LiveData<Product>{
        return productDAO.getContactById(id)
    }
}