package com.example.assignmentworkstruly.activities



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.assignmentworkstruly.R
import com.example.assignmentworkstruly.adapter.PostListAdapter
import com.example.assignmentworkstruly.models.GetModelClass
import com.example.assignmentworkstruly.models.GetModelClassItem
import com.example.assignmentworkstruly.network.ApiClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity() {

    //    val data = mutableListOf<Data>()
//    val data: List<String>=
    lateinit var adapter: PostListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAllPosts()
        clickOnFlotIcon()

    }


    fun setupAdapter(list: ArrayList<GetModelClassItem>) {
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.adapter = PostListAdapter(this, list)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    private fun getAllPosts() {
        ApiClient.getRetrofitService(this).getPosts().enqueue(object : Callback<GetModelClass> {
            override fun onFailure(call: Call<GetModelClass>, t: Throwable) {

            }

            override fun onResponse(call: Call<GetModelClass>, response: Response<GetModelClass>) {
                setupAdapter(response.body() ?: arrayListOf<GetModelClassItem>())
            }
        })
    }

    fun clickOnFlotIcon() {
        val fab = findViewById<FloatingActionButton>(R.id.floating_action_button)
        fab.setOnClickListener {
            val i = Intent(this, AddEditPostActivity::class.java)
//            i.putExtra("isEdit", false)
            startActivity(i)
        }
    }
}









