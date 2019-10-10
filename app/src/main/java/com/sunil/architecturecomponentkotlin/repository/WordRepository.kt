package com.sunil.architecturecomponentkotlin.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.sunil.architecturecomponentkotlin.`interface`.WordDao
import com.sunil.architecturecomponentkotlin.db.WordRoomDatabase
import com.sunil.architecturecomponentkotlin.model.Word

class WordRepository(application: Application)
{
    private val wordDao: WordDao?
    private val allWords: LiveData<ArrayList<Word>>?

    init {
        var db = WordRoomDatabase.getInstance(application)
        wordDao = db?.wordDao()
        allWords = wordDao?.getAllWords()
    }


    fun insertWord(word: Word)
    {
        insertAsyncTask(wordDao!!).execute(word)
    }

    fun deleteWord(word: Word)
    {
        deleteAsyncTask(wordDao!!).execute(word)
    }

    fun deleteAllWords()
    {
        deleteAllAsyncTask(wordDao!!).execute()
    }

    fun getAllWords() : LiveData<ArrayList<Word>>
    {
        return allWords!!
    }

    fun getWordByName(name: String) : Word?
    {
        val allWordList = allWords?.value?.toList()

        allWordList?.iterator()?.forEach {

            if (it.name == name)
            {
                return it
            }
        }
        return null
    }


    private class insertAsyncTask(private val dao: WordDao): AsyncTask<Word, Void, Void>()
    {
        override fun doInBackground(vararg params : Word): Void?
        {
            dao.insertWord(params[0])
            return null
        }
    }

    private class deleteAllAsyncTask(private val dao: WordDao): AsyncTask<Void, Void, Void>()
    {
        override fun doInBackground(vararg params : Void): Void?
        {
            dao.deleteAllWords()
            return null
        }
    }

    private class deleteAsyncTask(private val dao: WordDao): AsyncTask<Word, Void, Void>()
    {
        override fun doInBackground(vararg params : Word): Void?
        {
            dao.deleteWord(params[0])
            return null
        }
    }
}