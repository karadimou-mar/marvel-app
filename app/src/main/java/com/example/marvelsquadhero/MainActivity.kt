package com.example.marvelsquadhero

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelsquadhero.database.MarvelDatabase
import com.example.marvelsquadhero.model.character.MarvelCharacter
import com.example.marvelsquadhero.utils.ListPaddingDecoration
import com.example.marvelsquadhero.utils.getThumbnail
import com.example.marvelsquadhero.view.MarvelListCharacterAdapter
import com.example.marvelsquadhero.view.SquadListCharacterAdapter
import com.example.marvelsquadhero.view.paging.MarvelCharacterViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class MainActivity : AppCompatActivity(), MarvelListCharacterAdapter.OnItemClickListener {

    var squad = ArrayList<MarvelCharacter>()
    private val adapterSquad = SquadListCharacterAdapter( this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val sRecyclerView = findViewById<RecyclerView>(R.id.squadRecyclerView)
        sRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val characterViewModel = ViewModelProviders.of(this).get<MarvelCharacterViewModel>(
                MarvelCharacterViewModel::class.java
        )

        fetchSquad()


        val adapter = MarvelListCharacterAdapter(this)



        characterViewModel.characterPageList.observe(this, Observer { items ->
            adapter.submitList(items)
        })



        recyclerView!!.adapter = adapter
        sRecyclerView!!.adapter = adapterSquad

        recyclerView.addItemDecoration(
                ListPaddingDecoration(this, 220, 0)

        )

    }

    override fun onResume() {
        super.onResume()
        fetchSquad()
    }

    override fun onStart() {
        super.onStart()
        fetchSquad()
    }



    override fun onItemClicked(hero: MarvelCharacter?) {
        val intent = Intent(this, HeroDetailActivity::class.java)
        intent.putExtra("id", hero?.id)
        intent.putExtra("name", hero?.name)
        intent.putExtra("imageUrl", hero?.thumbnail?.getThumbnail())
        intent.putExtra("desc", hero?.description)
        this.startActivity(intent)
    }

    private fun fetchSquad(){
        GlobalScope.launch(Dispatchers.IO) {
            squad = MarvelDatabase(application).marvelDao()
                    .getAllHeroes() as ArrayList<MarvelCharacter>
        }
        GlobalScope.launch(Dispatchers.Main) {
            adapterSquad.setData(squad)
            if (squad.isEmpty()) {
                textViewSquad.visibility = View.GONE
                squadRecyclerView.visibility = View.GONE
            }
            else{
                textViewSquad.visibility = View.VISIBLE
                squadRecyclerView.visibility = View.VISIBLE
            }

        }
    }
}
