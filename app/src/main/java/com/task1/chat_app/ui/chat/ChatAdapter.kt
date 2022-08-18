package com.task1.chat_app.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.task1.chat_app.DataUtils
import com.task1.chat_app.R
import com.task1.chat_app.database.model.Message
import com.task1.chat_app.databinding.ItemMessageRecevieBinding
import com.task1.chat_app.databinding.ItemMessageSentBinding

class ChatAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val SENT = 1
    val RECEVIE = 2
    var items = mutableListOf<Message>()


    override fun getItemViewType(position: Int): Int {

        val message = items.get(position)
        return if (message.senderId == DataUtils.currentUser?.userID) {

            SENT
        } else {

            RECEVIE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == SENT) {

            val itemBinding: ItemMessageSentBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_message_sent, parent, false
            )

            return MessageSentViewHolder(itemBinding)
        }

        val itemBinding: ItemMessageRecevieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_message_recevie, parent, false
        )

        return MessageRecevieViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var item = items.get(position)

        if (holder is MessageSentViewHolder) {

            holder.bind(item)
        } else if (holder is MessageRecevieViewHolder) {

            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {

        return items.size
    }


    class MessageSentViewHolder(val itemMessageSentBinding: ItemMessageSentBinding) :
        RecyclerView.ViewHolder(itemMessageSentBinding.root) {


        fun bind(message: Message) {

            itemMessageSentBinding.itemSent = message
            itemMessageSentBinding.executePendingBindings()
        }
    }

    class MessageRecevieViewHolder(val itemMessageRecevieBinding: ItemMessageRecevieBinding) :
        RecyclerView.ViewHolder(itemMessageRecevieBinding.root) {

        fun bind(message: Message) {

            itemMessageRecevieBinding.itemRecevie = message
            itemMessageRecevieBinding.executePendingBindings()
        }
    }


    fun refreashAdapter(newMessages: MutableList<Message>) {

        this.items.addAll(newMessages)
        notifyItemRangeInserted(items.size + 1, newMessages.size)
    }
}