package com.example.cavistacodetest.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cavistacodetest.database.Dao.ImageCommentsDao
import com.example.cavistacodetest.model.ImageComments

@Database(entities = arrayOf(ImageComments::class), version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imageShapeDao(): ImageCommentsDao

     class PopulateDbAsync internal constructor(db: AppDatabase?) :
        AsyncTask<Void?, Void?, Void?>() {

        private val mImageShapeDao: ImageCommentsDao

        init {
            mImageShapeDao = db!!.imageShapeDao()
        }

        override fun doInBackground(vararg p0: Void?): Void? {
         return null
        }
    }

    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "image_database"
                        )
                            .addCallback(sRoomDatabaseCallback)
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        private val sRoomDatabaseCallback: Callback =
            object : Callback() {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    PopulateDbAsync(INSTANCE).execute()
                }
            }
    }
}