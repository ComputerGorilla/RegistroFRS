package com.computergorila.registrofrs.data.local
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [IncidenciaEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun incidenciaDao(): IncidenciaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "incidencias_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}