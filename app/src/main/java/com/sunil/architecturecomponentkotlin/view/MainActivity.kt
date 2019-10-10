package com.sunil.architecturecomponentkotlin.view



import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunil.architecturecomponentkotlin.R
import com.sunil.architecturecomponentkotlin.adapter.WordAdapter
import com.sunil.architecturecomponentkotlin.adapter.WordAdapter.ItemClickListener
import com.sunil.architecturecomponentkotlin.model.Word
import com.sunil.architecturecomponentkotlin.utils.*
import com.sunil.architecturecomponentkotlin.viewmodel.WordViewModel

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ItemClickListener {


    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: WordAdapter
    private var mWords: ArrayList<Word> = mutableListOf<Word>() as ArrayList<Word>
    private lateinit var mWordViewModel: WordViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mRecyclerView = findViewById(R.id.recyclerView)
        mAdapter = WordAdapter(this , this)
        mAdapter.setWords(words = mWords)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        mWordViewModel.getAllWords().observe(this , Observer { words ->
            words?.let {
                mAdapter.setWords(it)
            }
        })

        fab.setOnClickListener { view ->

            val intent = Intent(this , NewWordActivity::class.java)
            startActivityForResult(intent , NEW_WORD_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onItemClick(view: View, position: Int)
    {
        val intent = Intent(this , NewWordActivity::class.java)
        intent.putExtra(EXTRA_KEY_WORD , mAdapter.getWords()[position].name)
        intent.putExtra(EXTRA_KEY_MEANING , mAdapter.getWords()[position].meaning)
        startActivityForResult(intent , NEW_WORD_ACTIVITY_REQUEST_CODE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_clear_list -> {
                clearListAction()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clearListAction() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.app_name))
        builder.setMessage("Are you sure you wish to clear list?")

        builder.setPositiveButton("YES"){
            dialog, which ->
            run {

                mWordViewModel.deleteAllWords()
                Toast.makeText(this , "List is Cleared Successfully" , Toast.LENGTH_SHORT).show()

            }
        }

        builder.setNegativeButton("NO"){
            dialog, _ -> {
                dialog.dismiss()
            }
        }

        var dialog = builder.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_SAVE)
        {
            data?.let {
                val word = Word(it.getStringExtra(EXTRA_KEY_WORD) , it.getStringExtra(EXTRA_KEY_MEANING))
                mWordViewModel.insertWord(word)
            }
        }
        else if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_DELETE)
        {
            data?.let {
                val word = mWordViewModel.getWordByName(it.getStringExtra(EXTRA_KEY_WORD))
                mWordViewModel.deleteWord(word!!)

                Toast.makeText(this , getString(R.string.delete_word) , Toast.LENGTH_LONG).show()
            }
        }
        else if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_ERROR)
        {
            Toast.makeText(this , getString(R.string.empty_word_not_saved) , Toast.LENGTH_LONG).show()
        }
    }
}
