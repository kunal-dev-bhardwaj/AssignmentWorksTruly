package com.example.assignmentworkstruly.network

import com.example.assignmentworkstruly.models.GetModelClass
import com.example.assignmentworkstruly.models.GetModelClassItem
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiInterface {

    @GET("posts")
//    fun getPosts(): Call<GetModelClass>
fun getPosts():Call<GetModelClass>
    @DELETE("posts/{postId}")
    fun deletePost(@Path("postId") postId: String): Call<JSONObject>

    @FormUrlEncoded
    @POST("posts")
    fun createPost(
        @Field("title") title: String,
        @Field("body") body: String,
        @Field("userId") userId: String
    ): Call<JSONObject>

    @FormUrlEncoded
    @PUT("posts")
    fun editPost(
        @Field("title") title: String,
        @Field("body") body: String,
        @Field("userId") userId: String,
        @Field("id") id: Int
    ): Call<JSONObject>
}