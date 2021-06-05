package uz.devosmon.pigination.model

import java.io.Serializable

data class Users(
    val page:Int,
    val total:Int,
    val limit:Int,
    val offset:Int,
    val data:List<DataItem>
):Serializable