package com.example.marvelsquadhero

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelsquadhero.database.Converters
import com.example.marvelsquadhero.database.MarvelDatabase
import com.example.marvelsquadhero.model.character.MarvelCharacter
import com.example.marvelsquadhero.model.comic.MarvelComic
import com.example.marvelsquadhero.model.comic.MarvelComicData
import com.example.marvelsquadhero.model.response.MarvelComicResponse
import com.example.marvelsquadhero.network.MarvelApiClient
import com.example.marvelsquadhero.utils.loadImage
import com.example.marvelsquadhero.view.ComicListAdapter
import kotlinx.android.synthetic.main.activity_hero_detail.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeroDetailActivity : AppCompatActivity() {

    val comicList = arrayListOf<MarvelComic>()
    val mHandler = Handler()
    var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_detail)
        getIntentExtras()


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_comic)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val adapter = ComicListAdapter(comicList, this)

        recyclerView!!.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        getComics(id!!)
    }


    private fun getIntentExtras() {
        val bundle: Bundle? = intent.extras
        id = bundle?.getInt("id")
        //id?.let { getComics(it) }
        val name = bundle?.getString("name")
        val imageUrl = bundle?.getString("imageUrl")
        val desc =
            if (bundle?.getString("desc") == "") "No description available" else bundle?.getString("desc")
        setIntentExtras(name, imageUrl, desc)
        val conv = Converters()
        val thumbnail = conv.stringToThumbnail(imageUrl)
        GlobalScope.launch(Dispatchers.IO) {

            if (MarvelDatabase(application).marvelDao().getHeroById(id!!) == null) {
                GlobalScope.launch(Dispatchers.Main) {
                    btn_hire_hero.visibility = View.VISIBLE
                }
                btn_hire_hero.setOnClickListener {
                    GlobalScope.launch(Dispatchers.IO) {
                        MarvelDatabase(application).marvelDao()
                            .insert(MarvelCharacter(id!!, name!!, thumbnail!!, desc!!))
                    }
                    GlobalScope.launch(Dispatchers.Main) {
                        btn_hire_hero.visibility = View.GONE
                        Toast.makeText(
                            applicationContext,
                            "$name hired to squad",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    btn_fire_hero.visibility = View.VISIBLE
                }
                btn_fire_hero.setOnClickListener {
                    mHandler.post(Runnable {
                        kotlin.run {
                            deleteAlertDialog(MarvelCharacter(id!!, name!!, thumbnail!!, desc!!))
                        }
                    })

                }

            }

        }

    }


    private fun setIntentExtras(name: String?, imageUrl: String?, desc: String?) {
        val heroName: TextView = findViewById(R.id.hero_name)
        val image: ImageView = findViewById(R.id.hero_image)
        val description: TextView = findViewById(R.id.hero_description)
        heroName.text = name
        image.loadImage(imageUrl)
        description.text = desc

    }

    private fun getComics(characterId: Int) {

        val call: Call<MarvelComicResponse> = MarvelApiClient.getComics(characterId)
        call.enqueue(object : Callback<MarvelComicResponse> {
            override fun onFailure(call: Call<MarvelComicResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("FAILED API CONNECTION", t.message)


            }

            override fun onResponse(
                call: Call<MarvelComicResponse>,
                response: Response<MarvelComicResponse>
            ) {
                val marvelComicResponse: MarvelComicResponse? = response.body()
                val data: MarvelComicData? = marvelComicResponse?.data
                val total: Int? = data?.total
                val count: Int? = data?.count
                val list: List<MarvelComic>? = data?.results
                if (list != null) {
                    if (list.size < 2) {
                        for (i in 0..list.size) {
                            comicList.add(data?.results!![i])
                        }
                    } else {
                        comicList.add(data?.results!![0])
                        comicList.add(data?.results!![1])
                    }

                }
                val moreComics = total?.minus(2)

                if (moreComics!! > 0) {
                    textView_more.text = getString(R.string.more_comic, moreComics.toString())
                    if (moreComics!! > 1) {
                        textView_more.append("s")
                    }
                }


                recyclerView_comic.adapter?.notifyDataSetChanged()
            }

        })

    }

    private fun deleteAlertDialog(marvelCharacter: MarvelCharacter) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("KABOOM!" + "\uD83D\uDCA5")
        builder.setMessage("Are you sure you want to fire " + marvelCharacter.name + " ?")
        builder.setPositiveButton("YES") { dialog, which ->
            GlobalScope.launch(Dispatchers.IO) {
                MarvelDatabase(application).marvelDao()
                    .delete(marvelCharacter)

            }
            GlobalScope.launch(Dispatchers.Main) {
                btn_fire_hero.visibility = View.GONE
                Toast.makeText(
                    applicationContext,
                    "${marvelCharacter.name} fired from squad",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        builder.setNegativeButton("NO") { dialog, which ->
        }
        builder.show()
    }


}