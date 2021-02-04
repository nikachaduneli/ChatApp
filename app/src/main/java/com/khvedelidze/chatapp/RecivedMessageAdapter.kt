package com.khvedelidze.chatapp

import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.message_item_recived.view.*

data class RecivedMessages(val message : String, val person:String): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.recived_message_text.text  = message
        viewHolder.itemView.recived_message_person.text  = person
    }

    override fun getLayout(): Int  = R.layout.message_item_recived

}