package com.example.communitymessages.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.communitymessages.databinding.ItemViewMessageBinding
import com.example.communitymessages.domain.model.local.Message

class CommunityMessageAdapter(private val onClickListener: OnClickListener) :
    PagingDataAdapter<Message, CommunityMessageAdapter.MessageViewHolder>(DiffCallback) {

    class MessageViewHolder(private var binding: ItemViewMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.message = message
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            ItemViewMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
//        val message = getItem(position)
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(message)
//        }
//        holder.bind(message)
        getItem(position)?.let { message ->
        holder.itemView.setOnClickListener {
            onClickListener.onClick(message)
        }
            holder.bind(message)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (message: Message) -> Unit) {
        fun onClick(message: Message) = clickListener(message)
    }
}