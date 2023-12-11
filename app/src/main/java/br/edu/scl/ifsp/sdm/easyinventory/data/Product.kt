package br.edu.scl.ifsp.sdm.easyinventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productCode: Int,
    var productName: String,
    var description: String,
    var quantity: Int,
    var providerContact: String
)
