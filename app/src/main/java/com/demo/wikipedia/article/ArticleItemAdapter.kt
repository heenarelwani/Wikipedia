package com.demo.wikipedia.article

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.wikipedia.R
import com.demo.wikipedia.article.ArticleResponseModel.NoPages
import com.demo.wikipedia.image.ImageItemAdapter
import com.demo.wikipedia.image.imageResponseModel.PageNo
import kotlinx.android.synthetic.main.article_item_adapter.view.*
import kotlinx.android.synthetic.main.image_item_adapter.view.*
import java.lang.Exception

class ArticleItemAdapter(val context: Context,val arrayList: ArrayList<NoPages>): RecyclerView.Adapter<ArticleItemAdapter.MyViewHolder>() {
    companion object{
        private const val ITEM = 0
        private const val LOADING = 1
    }


    private var isLoadingAdded = false
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleItemAdapter.MyViewHolder {
        var itemView:View?=null
        when (viewType) {
            ITEM -> itemView = LayoutInflater.from(parent.context).inflate(R.layout.article_item_adapter,parent,false)
            LOADING ->
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_loading,parent,false)

        }
        return MyViewHolder(itemView!!)
    }

    class MyViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val tv_title=itemView.tv_item
            }

    override fun getItemCount(): Int {
        return  if (arrayList == null) 0 else arrayList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return  if (position === arrayList!!.size -1 && isLoadingAdded) LOADING else ITEM
    }
    override fun onBindViewHolder(holder: ArticleItemAdapter.MyViewHolder, position: Int) {
        when (getItemViewType(position)) {

           ITEM ->{
                    holder.tv_title.setText(arrayList!!.get(position).title)
            }
            else -> null
        }
    }

    /*
Helpers*/
    fun add(r: NoPages) {
        arrayList!!.add(r)
        Log.d("arrayList--",arrayList.toString())
        notifyItemInserted(arrayList!!.size - 1 )
    }

    fun addAll(Results: HashMap<String,NoPages>) {
        for (result in Results) {
            add(result.value)
        }
        println("arrayList=="+arrayList.toString())
    }

    fun remove(r:NoPages) {
        val position: Int = arrayList!!.indexOf(r)
        if (position > -1) {
            arrayList?.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }
    fun isEmpty(): Boolean {
        return itemCount == 0
    }
    fun addLoadingFooter() {
        isLoadingAdded = true
        add(NoPages())
    }
    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = arrayList!!.size - 1
        val result: NoPages = getItem(position)
        if (result != null) {
            arrayList!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    fun getItem(position: Int): NoPages{
        return arrayList!!.get(position)
    }
}