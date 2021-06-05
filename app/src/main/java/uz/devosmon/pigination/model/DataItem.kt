package uz.devosmon.pigination.model

import java.io.Serializable

data class DataItem(
    val id:String,
    val email:String,
    val firstName:String,
    val lastName:String,
    val picture:String
):Serializable