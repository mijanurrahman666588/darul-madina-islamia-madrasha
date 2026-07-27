package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        StudentEntity::class,
        NoticeEntity::class,
        DonationEntity::class,
        PrayerTimeEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MadrasaDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun noticeDao(): NoticeDao
    abstract fun donationDao(): DonationDao
    abstract fun prayerTimeDao(): PrayerTimeDao

    companion object {
        @Volatile
        private var INSTANCE: MadrasaDatabase? = null

        fun getDatabase(context: Context): MadrasaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MadrasaDatabase::class.java,
                    "darul_madina_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
