package com.example.assignmentworkstruly.models


class GetModelClass : ArrayList<GetModelClassItem>()

data class GetModelClassItem (
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
):java.io.Serializable