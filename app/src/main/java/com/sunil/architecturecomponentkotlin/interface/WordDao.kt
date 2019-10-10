package com.sunil.architecturecomponentkotlin.`interface`

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sunil.architecturecomponentkotlin.model.Word

@Dao
interface WordDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word: Word)

    @Query("SELECT * FROM word_table ORDER BY name ASC")
    fun getAllWords() : LiveData<ArrayList<Word>>

    @Query("SELECT * FROM word_table WHERE name = :name")
    fun getWordByName(name: String) : Word

    @Query("DELETE FROM word_table")
    fun deleteAllWords()

    @Delete
    fun deleteWord(word: Word)
}