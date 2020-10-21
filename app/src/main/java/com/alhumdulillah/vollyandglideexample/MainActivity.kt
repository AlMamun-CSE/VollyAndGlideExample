package com.alhumdulillah.vollyandglideexample

import android.content.Intent
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Global Var
    var currentImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set Title
        setTitle("Birthday App")

        //Call Method
        loadMeme()
    }

    private fun loadMeme(){
        //progressBar visibility set Visible
        progressBar.visibility = View.VISIBLE

        // api url
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                currentImageUrl = response.getString("url")

                // Using Glide Library And Load Url Image
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        //progressBar visibility set Gone
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        //progressBar visibility set Gone
                        progressBar.visibility = View.GONE
                        return false
                    }

                }).into(bossImageView)
            },
            Response.ErrorListener { })

        // Add the request to the RequestQueue.Recomened MySingleton class for google
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun ShareMeme(view: View) {
        //Intent this 2 type Implicit and Explicit
        val intent = Intent(Intent.ACTION_SEND)
        //Multiple Type Mp3,Mp4,Image
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Checkout this cool name I got from Reddit $currentImageUrl")
        //Chooser is set Open Dialog
        val chooser = Intent.createChooser(intent,"Share this meme using....")
        startActivity(chooser)
    }
    fun NextMame(view: View) {
        loadMeme()
    }

}