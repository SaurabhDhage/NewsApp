package com.example.newsapk

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var recyclerView = findViewById<RecyclerView>(R.id.rcView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter

    }

    fun fetchData() {
        val url =
            "https://api.nytimes.com/svc/topstories/v2/science.json?api-key=AIXqtI1KkBHO4BjdTYLuBNvozAtlCtiQ"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("results")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val subJsonArray = newsJsonObject.getJSONArray("multimedia")
                    val obj = subJsonArray.getJSONObject(0)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("byline"),
                        newsJsonObject.getString("url"),
                        obj.getString("url")
                    )

                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {

            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }


    override fun onItemClicked(item: News) {
        var builder = CustomTabsIntent.Builder()
        var customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.URL))
    }
}