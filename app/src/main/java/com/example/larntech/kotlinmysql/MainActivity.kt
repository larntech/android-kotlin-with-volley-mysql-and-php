package com.example.larntech.kotlinmysql

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject
import android.widget.Toast



class MainActivity : AppCompatActivity() {

var activity: EditText? = null
var day: Spinner? = null
val addUrl : String = "http://192.168.43.254/activity/addActivity.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //finding views
        activity = findViewById(R.id.activity)
        day = findViewById(R.id.day)
        val btnAaddActivity = findViewById<Button>(R.id.addactivity)
        btnAaddActivity.setOnClickListener {
           //we send user data to database
            addActivity()
        }

    }

    private fun addActivity() {
        val getActivity = activity?.text.toString()
        val getDay = day?.selectedItem.toString()
        val stringRequest = object : StringRequest(Request.Method.POST,addUrl,Response.Listener<String>{
            response ->
            try {
                val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
            }catch (e: JSONException){
                e.printStackTrace()
            }

        }, object : Response.ErrorListener{
            override fun onErrorResponse(volleyError: VolleyError) {
                Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
            }
        }){
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("activity", getActivity)
                params.put("day", getDay)
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}
