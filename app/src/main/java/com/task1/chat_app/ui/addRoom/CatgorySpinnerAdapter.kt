package com.task1.chat_app.ui.addRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.task1.chat_app.R
import com.task1.chat_app.databinding.ItemCatgoryBinding
import com.task1.chat_app.database.model.CatgorySpinner

class CatgorySpinnerAdapter(var items: List<CatgorySpinner?>?) : BaseAdapter() {


    override fun getCount(): Int {

        return items?.size ?: 0
    }

    override fun getItem(postion: Int): Any {

        return items?.get(postion) ?: 0
    }

    override fun getItemId(postion: Int): Long {

        return 0
    }


    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {

        var currentView = view
        var catgorySpinnerViewHolder: ViewHolder
        var tempView: ItemCatgoryBinding
        val item = items?.get(position)


        if (currentView == null) {

            tempView = DataBindingUtil.inflate(
                LayoutInflater.from(parent?.context),
                R.layout.item_catgory,
                parent,
                false
            )

            currentView = tempView.root
            catgorySpinnerViewHolder = ViewHolder(tempView)
            currentView.setTag(catgorySpinnerViewHolder)
        } else {
            catgorySpinnerViewHolder = currentView.tag as ViewHolder
        }

        catgorySpinnerViewHolder.bind(item!!)
        return currentView!!
    }

    class ViewHolder(val itemCatgoryBinding: ItemCatgoryBinding) {

        fun bind(catgory: CatgorySpinner) {

            itemCatgoryBinding.item = catgory
            itemCatgoryBinding.executePendingBindings()

        }

    }
}