package com.demo.wikipedia.image

import android.app.Activity
import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.wikipedia.R
import com.demo.wikipedia.category.CategoryItemAdapter
import com.demo.wikipedia.category.categoeyResponseModel.Allcategory
import com.demo.wikipedia.image.imageResponseModel.Imageinfo
import com.demo.wikipedia.image.imageResponseModel.PageNo
import kotlinx.android.synthetic.main.category_item_adapter.view.*
import kotlinx.android.synthetic.main.image_item_adapter.view.*
import retrofit2.http.Url
import java.lang.Exception
import java.net.URI
import java.net.URL

class ImageItemAdapter(val context: Context,val arrayList: ArrayList<PageNo>) : RecyclerView.Adapter<ImageItemAdapter.MyViewHolder>() {
    companion object{
        private const val ITEM = 0
        private const val LOADING = 1
    }


    private var isLoadingAdded = false
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageItemAdapter.MyViewHolder {
        var itemView:View?=null
        when (viewType) {
            ITEM -> itemView = LayoutInflater.from(parent.context).inflate(R.layout.image_item_adapter,parent,false)
            LOADING ->
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_loading,parent,false)

        }

        return MyViewHolder(itemView!!)
    }

    class MyViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        val img=itemView.image
        val tv_title=itemView.title
    }

    override fun getItemCount(): Int {
        return  if (arrayList == null) 0 else arrayList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return  if (position === arrayList!!.size -1 && isLoadingAdded) LOADING else ITEM
    }

    override fun onBindViewHolder(holder: ImageItemAdapter.MyViewHolder, position: Int) {

       when (getItemViewType(position)) {

            ITEM ->{

                try {
                    var url:String?=null
                    holder.tv_title.setText(arrayList!!.get(position).title)

                    println("ImageInfo"+arrayList!!.get(position).imageinfo!!.size)
                    url=arrayList!!.get(position).imageinfo!!.get(0).url
                    Glide.with(context).load(url).into(holder.img)
                }
                catch (e:Exception)
                {
                    e.printStackTrace()
                }

            }
            else -> null
        }
    }



    /*
Helpers*/
    fun add(r: PageNo) {
        arrayList!!.add(r)
        Log.d("arrayList--",arrayList.toString())
        notifyItemInserted(arrayList!!.size - 1 )
    }

    fun addAll(Results: HashMap<String,PageNo>) {
        for (result in Results) {
            add(result.value)
        }
        println("arrayList=="+arrayList.toString())
    }

    fun remove(r: PageNo) {
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
        add(PageNo())
    }
    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = arrayList!!.size - 1
        val result: PageNo = getItem(position)
        if (result != null) {
            arrayList!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    fun getItem(position: Int): PageNo {
        return arrayList!!.get(position)
    }

}