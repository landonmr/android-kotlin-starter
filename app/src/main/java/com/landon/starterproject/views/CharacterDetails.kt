package com.landon.starterproject.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.landon.starterproject.Constants
import com.landon.starterproject.R
import com.landon.starterproject.models.Character
import kotlinx.android.synthetic.main.character_detail.*

class CharacterDetails: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_detail)

        supportActionBar?.title = getString(R.string.character_detail_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val character = intent.getParcelableExtra<Character>(Constants.EXTRA_KEY_CHARACTER)
        val heroImage = "${character?.thumbnail?.path?.replace("http", "https")}.${character?.thumbnail?.extension}"
        Glide.with(this).load(heroImage).into(headerImage);
        characterDesc.text = if (character?.description?.isEmpty()!!)
            getString(R.string.character_detail_no_description)
            else character.description

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}