package com.monkwithmac.msk.pickornot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactListAdapter(var contactList : ArrayList<ContactModel>) : RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder>() {

    class ContactListViewHolder(val view: View) : RecyclerView.ViewHolder(view){

        fun bind(contactModel: ContactModel) {
            var nameLabel = view.findViewById<TextView>(R.id.tv_name)
            var contactLabel = view.findViewById<TextView>(R.id.tv_number)

            nameLabel.text = contactModel.name
            if(contactModel.number != null && contactModel.number.size > 0)
                contactLabel.text = contactModel.number[0]
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact_list, parent, false)
        return ContactListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        holder.bind(contactList[position])
    }

    fun updateList(listOfContacts: ArrayList<ContactModel>) {
        contactList.clear()
        contactList.addAll(listOfContacts)
        notifyDataSetChanged()
    }
}