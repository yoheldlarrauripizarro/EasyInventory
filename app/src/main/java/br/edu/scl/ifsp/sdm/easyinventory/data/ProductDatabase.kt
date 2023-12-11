package br.edu.scl.ifsp.sdm.easyinventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase:RoomDatabase() {
    abstract fun productDAO(): ProductDAO

    companion object{
        @Volatile
        private var INSTANCE: ProductDatabase?= null

        fun getDatabase(context: Context):ProductDatabase{
            return INSTANCE?: synchronized(this) {
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "easyinventory.db"
                ).build()
                INSTANCE=instance
                instance
            }
        }
    }
}