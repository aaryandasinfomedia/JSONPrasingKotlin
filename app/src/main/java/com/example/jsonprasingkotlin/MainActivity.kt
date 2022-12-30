package com.example.jsonprasingkotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {
    // ArrayList for person names, email Id's, mobile numbers & gender
    var personNames = ArrayList<String>()
    var emailIds = ArrayList<String>()
    var mobileNumbers = ArrayList<String>()
    var persongender = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        // get the reference of RecyclerView
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        try {
            // get JSONObject from JSON file
            val obj = JSONObject(loadJSONFromAsset())

            // fetch JSONArray named users
            val userArray = obj.getJSONArray("users")

            // implement for loop for getting users list data
            for (i in 0 until userArray.length()) {

                // create a JSONObject for fetching single user data
                val userDetail = userArray.getJSONObject(i)

                // fetch email and name and store it in arraylist
                personNames.add(userDetail.getString("name"))
                emailIds.add(userDetail.getString("email"))
                persongender.add(userDetail.getString("gender"))
//                                mobileNumbers.add(userDetail.getString("contact"));   for....

                // create a object for getting contact data from JSONObject
                val contact = userDetail.getJSONObject("contact")

                // fetch mobile number and store it in arraylist
                mobileNumbers.add(contact.getString("mobile"))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        val customAdapter = CustomAdapter(this@MainActivity, personNames, emailIds, mobileNumbers,persongender)
        recyclerView.adapter = customAdapter // set the Adapter to RecyclerView
    }

    fun loadJSONFromAsset(): String? {
        var json: String? = null
        json = try {
            val `is` = assets.open("users_list.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}