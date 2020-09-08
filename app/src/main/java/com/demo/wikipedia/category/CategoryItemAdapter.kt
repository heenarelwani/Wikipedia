package com.demo.wikipedia.category

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.wikipedia.R
import com.demo.wikipedia.category.categoeyResponseModel.Allcategory
import kotlinx.android.synthetic.main.category_item_adapter.view.*

class CategoryItemAdapter(val context: Context, private var categoryResults: ArrayList<Allcategory>): RecyclerView.Adapter<CategoryItemAdapter.MyViewHolder>() {
    companion object{
        private const val ITEM = 0
        private const val LOADING = 1
    }


    private var isLoadingAdded = false
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryItemAdapter.MyViewHolder {

        var itemView:View?=null
        when (viewType) {
            ITEM -> itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_item_adapter,parent,false)
            LOADING ->
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_loading,parent,false)

        }

        return MyViewHolder(itemView!!)
    }

    class MyViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        val tv_item=itemView.tv_item
    }

    override fun getItemCount(): Int {
        return if (categoryResults == null) 0 else categoryResults!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position === categoryResults!!.size - 1 && isLoadingAdded) LOADING else ITEM
    }
    override fun onBindViewHolder(holder: CategoryItemAdapter.MyViewHolder, position: Int) {
        when(getItemViewType(position)){
            ITEM ->
                holder.tv_item.setText(categoryResults!!.get(position).category)
            else -> null
        }

    }

/*
Helpers*/
    fun add(r: Allcategory) {
    categoryResults!!.add(r)
    println("sixe=="+categoryResults!!.size)
   notifyItemInserted(categoryResults!!.size-1 )
    }

    fun addAll(Results: ArrayList<Allcategory>) {
        for (result:Allcategory in Results) {

            add(result)
        }
    }

    fun remove(r: Allcategory) {
        val position: Int = categoryResults!!.indexOf(r)
        if (position > -1) {
            categoryResults?.removeAt(position)
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
        add(Allcategory())
    }
    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = categoryResults!!.size - 1
        val result: Allcategory = getItem(position)
        if (result != null) {
            categoryResults!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    fun getItem(position: Int): Allcategory {
        return categoryResults!!.get(position)
    }

}