package com.sunil.architecturecomponentkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sunil.architecturecomponentkotlin.`interface`.WordDao
import com.sunil.architecturecomponentkotlin.model.Word

@Database(entities = [Word::class] , version = 1)
abstract class WordRoomDatabase : RoomDatabase()
{
    abstract fun wordDao(): WordDao

    companion object
    {
        @Volatile
        var database : WordRoomDatabase? = null

        fun getInstance(context: Context): WordRoomDatabase?{

            if (database == null)
            {
                synchronized(WordRoomDatabase::class.java)
                {
                    if (database == null)
                    {
                        database = Room.databaseBuilder(context , WordRoomDatabase::class.java , "word_database").build()
                    }
                }
            }
            return database
        }
    }
}