package uz.devosmon.pigination.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import uz.devosmon.pigination.model.Users

interface ApiInterface {
@Headers("app-id:60adf17d9a4129428cab300b")
    @GET("user")
    fun getUser(@Query("page") page:Int,
@Query("limit") limit:Int=10):Call<Users>
}