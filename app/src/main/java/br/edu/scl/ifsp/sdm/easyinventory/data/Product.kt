package br.edu.scl.ifsp.sdm.easyinventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productCode: Int,
    val productName: String,
    val description: String,
    val quantity: Int,
    val providerContact: String
)
