package com.sunil.architecturecomponentkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sunil.architecturecomponentkotlin.model.Word
import com.sunil.architecturecomponentkotlin.repository.WordRepository

class WordViewModel(application: Application): AndroidViewModel(application)
{
    private val wordRepository: WordRepository
    private val allWords: LiveData<ArrayList<Word>>

    init {
        wordRepository = WordRepository(application)
        allWords = wordRepository.getAllWords()
    }

    fun insertWord(word: Word)
    {
        wordRepository.insertWord(word)
    }

    fun deleteWord(word: Word)
    {
        wordRepository.deleteWord(word)
    }

    fun deleteAllWords()
    {
        wordRepository.deleteAllWords()
    }

    fun getAllWords() : LiveData<ArrayList<Word>>
    {
        return allWords
    }

    fun getWordByName(name: String) : Word?
    {
        return wordRepository.getWordByName(name)
    }
}