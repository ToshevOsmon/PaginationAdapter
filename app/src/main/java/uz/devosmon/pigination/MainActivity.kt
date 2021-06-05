package uz.devosmon.pigination

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import uz.devosmon.pigination.adapter.PaganaitonAdapter
import uz.devosmon.pigination.model.DataItem
import uz.devosmon.pigination.model.Users
import uz.devosmon.pigination.retrofit.AdiService
import uz.devosmon.pigination.retrofit.ApiInterface
import uz.devosmon.pigination.utils.PaganationScrollListener

class MainActivity : AppCompatActivity() {

    lateinit var paganaitonAdapter: PaganaitonAdapter

    var PAGE_START = 1
    var TOTAL_PAGE = 10
    var CURRENT_PAGE = PAGE_START
    var isLastPage = false
    var isLoading = false

    lateinit var retrofit: Retrofit
    lateinit var create: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofit = AdiService().getRetrofit()
        create = retrofit.create(ApiInterface::class.java)

        val linearLayoutManager = LinearLayoutManager(this)

        rv.layoutManager = linearLayoutManager
        paganaitonAdapter = PaganaitonAdapter(this)
        rv.adapter = paganaitonAdapter


        rv.addOnScrollListener(object : PaganationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                CURRENT_PAGE++


                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        loadNextPage()
                    }

                }, 2000)


            }

            override fun getTotalPageCount(): Int = TOTAL_PAGE

            override fun isLastPage(): Boolean = isLastPage

            override fun isLoading(): Boolean {
                return isLoading
            }
        })


        loadingFirstPage()


    }

    private fun loadNextPage() {

        if (CURRENT_PAGE <= TOTAL_PAGE) {

            create.getUser(CURRENT_PAGE).enqueue(object : Callback<Users> {
                override fun onResponse(call: Call<Users>, response: Response<Users>) {

                    Log.d("TTTT",response.code().toString())

                    if (response.isSuccessful) {

                        isLoading = false
                        paganaitonAdapter.addDataItems(response.body()?.data as ArrayList<DataItem>)

                    }

                }

                override fun onFailure(call: Call<Users>, t: Throwable) {
Log.d("TTTT",t.localizedMessage)
                }
            })

        }else{

            paganaitonAdapter.setLoading(false)
            paganaitonAdapter.notifyDataSetChanged()
        }

    }

    private fun loadingFirstPage() {

        create.getUser(CURRENT_PAGE).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {

                var list: ArrayList<DataItem> = ArrayList()
                if (response.isSuccessful) {

                    list.addAll(response.body()?.data as ArrayList<DataItem>)

                    Log.d("TTTT", response.code().toString())

                    paganaitonAdapter.addDataItems(list)
                    paganaitonAdapter.setLoading(true)

                }

            }

            override fun onFailure(call: Call<Users>, t: Throwable) {

Log.d("TATA",t.localizedMessage)
            }
        })

    }
}
