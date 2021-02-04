package com.khvedelidze.chatapp

import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.item_text_message.view.*

data class SentMessages(val message: String, val person:String): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.sent_message_text.text  = message
        viewHolder.itemView.send_message_person.text  = person


    }

    override fun getLayout(): Int  = R.layout.item_text_message

}