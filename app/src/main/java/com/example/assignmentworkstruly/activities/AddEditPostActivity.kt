package com.example.assignmentworkstruly.activities

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assignmentworkstruly.R
import com.example.assignmentworkstruly.models.GetModelClassItem
import com.example.assignmentworkstruly.network.ApiClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddEditPostActivity : AppCompatActivity() {
    var editData: GetModelClassItem? = null
    lateinit var editTextUserId: EditText
    lateinit var editTextTitle: EditText
    lateinit var editTextDescription: EditText
    lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_post)

        editTextUserId = findViewById<EditText>(R.id.user_id)
        editTextTitle = findViewById<EditText>(R.id.title)
        editTextDescription = findViewById<EditText>(R.id.description)
        buttonSave = findViewById<Button>(R.id.btn_save)
        val data = intent.getSerializableExtra("data")
        if (data != null) {
            setDefaultData((data as GetModelClassItem))
        } else {
            buttonSave.setOnClickListener {
                if (validate()) {
                    createPost()
                }
            }
        }


    }

    private fun setDefaultData(item: GetModelClassItem) {
        editData = item
        editTextUserId.setText(editData?.userId.toString())
        editTextTitle.setText(editData?.title.toString())
        editTextDescription.setText(editData?.body.toString())
        buttonSave.setOnClickListener {
            if (validate()) {
                editPost()
            }
        }
    }

    private fun editPost() {
        ApiClient.getRetrofitService(this).editPost(
            editTextTitle.text.toString(),
            editTextDescription.text.toString(),
            editTextUserId.text.toString(),
            editData?.id ?: 0
        )
            .enqueue(object : Callback<JSONObject> {
                override fun onFailure(call: Call<JSONObject>, t: Throwable) {

                }


                override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                    Toast.makeText(
                        this@AddEditPostActivity,
                        "Edit Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    onBackPressed()
                }

            })
    }

    private fun createPost() {
        ApiClient.getRetrofitService(this).createPost(
            editTextTitle.text.toString(),
            editTextDescription.text.toString(),
            editTextUserId.text.toString()
        )
            .enqueue(object : Callback<JSONObject> {
                override fun onFailure(call: Call<JSONObject>, t: Throwable) {

                }


                override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                    Toast.makeText(
                        this@AddEditPostActivity,
                        "Post Created Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    onBackPressed()
                }

            })
    }


    fun validate(): Boolean {
        var valid = true
        val title: String = editTextTitle.getText().toString()
        val body: String = editTextDescription.getText().toString()
        val id: String = editTextUserId.getText().toString()
        if (title.isEmpty()) {
            editTextTitle.setError("empty")
            valid = false
        }
        if (body.isEmpty()) {
            editTextDescription.setError("empty")
            valid = false
        }
        if (id.isEmpty()) {
            editTextUserId.setError("empty")
            valid = false
        }


        return valid
    }

}