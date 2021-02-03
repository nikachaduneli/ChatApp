package com.khvedelidze.chatapp

import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.item_text_message.view.*

data class SentMessages(val message: String): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.sent_message_text.text  = message


    }

    override fun getLayout(): Int  = R.layout.item_text_message

}