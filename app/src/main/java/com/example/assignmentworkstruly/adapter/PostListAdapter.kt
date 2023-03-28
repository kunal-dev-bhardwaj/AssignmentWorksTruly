package com.example.assignmentworkstruly.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.assignmentworkstruly.R
import com.example.assignmentworkstruly.activities.AddEditPostActivity
import com.example.assignmentworkstruly.activities.MainActivity
import com.example.assignmentworkstruly.models.GetModelClassItem
import com.example.assignmentworkstruly.network.ApiClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostListAdapter(val context: Context, val data: ArrayList<GetModelClassItem>) :
    RecyclerView.Adapter<PostListAdapter.DataViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reycler_item, parent, false)
        return DataViewHolder(view)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val model = data[position]
        holder.name.text = model.title
        holder.userId.text = model.userId.toString()
        holder.description.text = model.body
        holder.menuIcon.setOnClickListener {
            val popupMenu = PopupMenu(context, it)
            popupMenu.menuInflater.inflate(R.menu.edit_delete_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.delete) {
                    deletePost(model.id, position)
                } else if (menuItem.itemId == R.id.edit) {
                    val intent = Intent(context, AddEditPostActivity::class.java)
                    intent.putExtra("data", model)
                    context.startActivity(intent)
                }
                true
            }
            popupMenu.show()

        }

    }


    class DataViewHolder(itemView: View) : ViewHolder(itemView) {

        val userId = itemView.findViewById<TextView>(R.id.userId)
        val name = itemView.findViewById<TextView>(R.id.name)
        val description = itemView.findViewById<TextView>(R.id.description)
        val menuIcon = itemView.findViewById<ImageView>(R.id.menu_icon)
    }

    private fun deletePost(id: Int, index: Int) {
        ApiClient.getRetrofitService(context).deletePost(id.toString())
            .enqueue(object : Callback<JSONObject> {
                override fun onFailure(call: Call<JSONObject>, t: Throwable) {

                }

                override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                    data.removeAt(index)
                    notifyItemChanged(index)
                    notifyDataSetChanged()

                    Toast.makeText(
                        context, "Post Deleted Successfully", Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
