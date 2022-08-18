package com.task1.chat_app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.task1.chat_app.R
import com.task1.chat_app.databinding.ItemRoomBinding
import com.task1.chat_app.database.model.Room

class RoomsAdapter(var items: List<Room?>?) : RecyclerView.Adapter<RoomsAdapter.RoomsViewHolder>() {

    class RoomsViewHolder(var itemRoomBinding: ItemRoomBinding) :
        RecyclerView.ViewHolder(itemRoomBinding.root) {


        fun bind(room: Room?) {

            itemRoomBinding.item = room
            itemRoomBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {

        var itemBinding: ItemRoomBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_room,
            parent,
            false
        )

        return RoomsViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        var item = items?.get(position)

        holder.bind(item)

        if (onItemClickListener != null) {
            holder.itemRoomBinding.root.setOnClickListener {

                onItemClickListener?.onItemClick(position, item!!)
            }
        }
    }

    override fun getItemCount(): Int {

        return items?.size ?: 0
    }

    fun refreashAdapter(rooms: List<Room>) {

        this.items = rooms
        notifyDataSetChanged()
    }

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {

        fun onItemClick(position: Int, room: Room);
    }
}