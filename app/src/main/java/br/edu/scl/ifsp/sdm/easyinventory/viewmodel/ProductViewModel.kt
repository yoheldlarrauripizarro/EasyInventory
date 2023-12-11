package br.edu.scl.ifsp.sdm.easyinventory.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.edu.scl.ifsp.sdm.easyinventory.data.Product
import br.edu.scl.ifsp.sdm.easyinventory.data.ProductDatabase
import br.edu.scl.ifsp.sdm.easyinventory.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ProductRepository
    val allProducts: LiveData<List<Product>>
    lateinit var product: LiveData<Product>

    init {
        val dao = ProductDatabase.getDatabase(application).productDAO()
        repository = ProductRepository(dao)
        allProducts = repository.getAllProducts()
    }

    fun insert(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(product)
    }

    fun update(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(product)
    }

    fun delete(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(product)
    }


    fun getProductById(id: Int) {
        viewModelScope.launch {
            product = repository.getProductById(id)
        }

    }
}