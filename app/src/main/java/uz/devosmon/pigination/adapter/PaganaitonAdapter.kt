package uz.devosmon.pigination.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.loading_item_layoyt.view.*
import kotlinx.android.synthetic.main.page_list_layout.view.*
import uz.devosmon.pigination.R
import uz.devosmon.pigination.model.DataItem

class PaganaitonAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

   var list: ArrayList<DataItem>
    var ITEM = 0
    var LOADING = 1
    private var isLoading = false

    init {
        list = ArrayList()
    }

    class UserItemViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(dataItem: DataItem) {

            view.email.text = dataItem.email
            view.lname.text = dataItem.lastName
            view.fname.text = dataItem.firstName
            Picasso.get().load(dataItem.picture).into(view.image)
        }

    }

    class LoadingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun  onBind(){
            itemView.progress_circular.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var viewHolder: RecyclerView.ViewHolder? = null

        var layoutInflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ITEM -> {
                viewHolder = UserItemViewHolder(
                    layoutInflater.inflate(
                        R.layout.page_list_layout,
                        parent,
                        false
                    )
                )
            }
            LOADING -> {
                viewHolder = LoadingItemViewHolder(
                    layoutInflater.inflate(
                        R.layout.loading_item_layoyt,
                        parent,
                        false
                    )
                )
            }
        }
        return viewHolder!!

    }

    override fun getItemViewType(position: Int): Int {
        if (list.size - 1 == position && isLoading) {
            return LOADING
        } else {
            return ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val dataItem: DataItem = list[position]

        when (getItemViewType(position)) {
            ITEM -> {
                var userItemViewHolder: UserItemViewHolder = holder as UserItemViewHolder
                userItemViewHolder.onBind(dataItem)
            }
            LOADING -> {
                var loadingItemViewHolder: LoadingItemViewHolder = holder as LoadingItemViewHolder
loadingItemViewHolder.onBind()

            }


        }


    }

    override fun getItemCount(): Int {

        if (list == null) {
            return 0
        } else {
            return list.size
        }
    }

    fun addDataItems(listDataItem: ArrayList<DataItem>) {
        list.addAll(listDataItem)
        notifyDataSetChanged()

    }

    fun setLoading(b: Boolean) {
isLoading = b
    }

}