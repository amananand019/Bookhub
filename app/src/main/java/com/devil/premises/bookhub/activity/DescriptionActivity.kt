package com.devil.premises.bookhub.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.devil.premises.bookhub.R
import com.devil.premises.bookhub.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap

class DescriptionActivity : AppCompatActivity() {

    private val TAG = "DescriptionActivity"

    lateinit var txtBookName: TextView
    lateinit var txtBookAuthor: TextView
    lateinit var txtBookPrice: TextView
    lateinit var txtBookRating: TextView
    lateinit var imgBookImage: ImageView
    lateinit var txtBookDesc: TextView
    lateinit var btnAddToFav: Button
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    lateinit var toolbar: Toolbar

    var bookId: String? = "100"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        txtBookName = findViewById(R.id.txtBookName)
        txtBookAuthor = findViewById(R.id.txtBookAuthor)
        txtBookPrice = findViewById(R.id.txtBookPrice)
        txtBookRating = findViewById(R.id.txtBookRating)
        imgBookImage = findViewById(R.id.imgBookImage)
        txtBookDesc = findViewById(R.id.txtBookDesc)
        btnAddToFav = findViewById(R.id.btnAddToFav)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        progressLayout = findViewById(R.id.progressbarLayout)
        progressLayout.visibility = View.VISIBLE

        toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Book Details"

        if (intent != null) {
            bookId = intent.getStringExtra("book_id")
        } else {
            finish()
            Toast.makeText(
                this@DescriptionActivity,
                "Some unexpected error occurred!",
                Toast.LENGTH_SHORT
            ).show()
        }

        if (bookId == "100") {
            finish()
            Toast.makeText(
                this@DescriptionActivity,
                "Some unexpected error occurred!",
                Toast.LENGTH_SHORT
            ).show()
        }

        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "http://13.235.250.119/v1/book/get_book/"

        val jsonParams = JSONObject()
        jsonParams.put("book_id", bookId)

        if (ConnectionManager().checkConnectivity(this@DescriptionActivity)) {

            val jsonRequest = object : JsonObjectRequest(
                Method.POST,
                url,
                jsonParams,
                Response.Listener {
                    try {
                        Log.i(TAG, "onCreate: Into jsonRequest")
                        val success = it.getBoolean("success")

                        if (success) {
                            val bookJSONObject = it.getJSONObject("book_data")
                            progressLayout.visibility = View.GONE
                            progressBar.visibility = View.GONE

                            Picasso.get().load(bookJSONObject.getString("image"))
                                .error(R.drawable.default_book_cover).into(imgBookImage)
                            txtBookName.text = bookJSONObject.getString("name").trim()
                            txtBookAuthor.text = bookJSONObject.getString("author").trim()
                            txtBookPrice.text = bookJSONObject.getString("price").trim()
                            txtBookRating.text = bookJSONObject.getString("rating").trim()
                            txtBookDesc.text = bookJSONObject.getString("description").trim()

                        } else {
                            Toast.makeText(
                                this@DescriptionActivity,
                                "Some Error Occurred!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            this@DescriptionActivity,
                            "Some Error Occurred!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(this@DescriptionActivity, "Volley Error $it", Toast.LENGTH_SHORT)
                        .show()
                }) {

                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application"
                    headers["token"] = "82443d9edf0a08"
                    return headers
                }
            }
            queue.add(jsonRequest)
        } else {
            val dialog = AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                //DO nothing
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                finish()
            }

            dialog.setNegativeButton("Exit") { text, listener ->
                //Do nothing
                ActivityCompat.finishAffinity(this@DescriptionActivity)
            }
            dialog.create()
            dialog.show()
        }
    }
}