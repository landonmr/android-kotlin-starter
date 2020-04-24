package com.landon.starterproject.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.landon.starterproject.Constants
import com.landon.starterproject.R
import com.landon.starterproject.adapters.CharacterAdapter
import com.landon.starterproject.adapters.OnCharacterClickListener
import com.landon.starterproject.models.Character
import com.landon.starterproject.viewModels.CharacterViewModel
import com.landon.starterproject.viewModels.ViewState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnCharacterClickListener {

    private lateinit var recyclerView: RecyclerView
    private var characterAdapter = CharacterAdapter(arrayListOf(), this)
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val viewModel: CharacterViewModel by viewModels()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.main_title)

        viewManager = LinearLayoutManager(this)

        recyclerView = findViewById<RecyclerView>(R.id.characterList).apply {
            layoutManager = viewManager
            adapter = characterAdapter
        }

        viewModel.state.observe(this, Observer {
            it?.let { render(it) }
        })

        viewModel.loadCharacters()
    }

    private fun render(viewState: ViewState) {
        when (viewState) {
            ViewState.Loading -> showLoading()
            ViewState.Error -> showError()
            ViewState.Loaded -> showCharacters()
        }
    }

    private fun showError() {
        loading.visibility = View.GONE
        errorMsg.visibility = View.VISIBLE
    }

    private fun showLoading() {
        loading.visibility = View.VISIBLE
        errorMsg.visibility = View.GONE
        characterList.visibility = View.GONE
    }

    private fun showCharacters() {
        characterAdapter.updateCharacters(viewModel.characters)

        loading.visibility = View.GONE
        errorMsg.visibility = View.GONE
        characterList.visibility = View.VISIBLE
    }

    override fun onItemClicked(character: Character) {
        val intent = Intent(this, CharacterDetails::class.java)
        intent.putExtra(Constants.EXTRA_KEY_CHARACTER, character)
        startActivity(intent)
    }
}
