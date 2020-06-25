package com.monkwithmac.msk.pickornot

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class ContactListAdapter(var contactList: ArrayList<ContactModel>) :
    RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder>() {


    val colorList: ArrayList<Int> = ArrayList()
    var lastIndex = 0

    class ContactListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(contactModel: ContactModel, color: Int) {
            var nameLabel = view.findViewById<TextView>(R.id.tv_name)
            var contactLabel = view.findViewById<TextView>(R.id.tv_number)
            var contactImage = view.findViewById<ImageView>(R.id.image_contact)

            nameLabel.text = contactModel.name
            if (contactModel.number != null && contactModel.number.size > 0)
                contactLabel.text = contactModel.number[0]

            val myDrawable = ContextCompat.getDrawable(view.context, R.drawable.bg_round_user_icon)?.mutate()
            if (myDrawable != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    myDrawable.setColorFilter(BlendModeColorFilter(color, BlendMode.COLOR))
                } else {
                    myDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
                }
                contactImage.background = myDrawable
            }
        }
    }

    init {
        colorList.add(Color.parseColor("#6200EE"))
        colorList.add(Color.parseColor("#3700BE"))
        colorList.add(Color.parseColor("#BB86FC"))
        colorList.add(Color.parseColor("#03DAC5"))
        colorList.add(Color.parseColor("#FFDE03"))
        colorList.add(Color.parseColor("#FF0266"))
        colorList.add(Color.parseColor("#29B6F6"))
        colorList.add(Color.parseColor("#00BCD4"))
        colorList.add(Color.parseColor("#689F38"))
        colorList.add(Color.parseColor("#FB8C00"))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact_list, parent, false)
        return ContactListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        holder.bind(contactList[position], getNextColor())
    }

    fun updateList(listOfContacts: ArrayList<ContactModel>) {
        contactList.clear()
        contactList.addAll(listOfContacts)
        notifyDataSetChanged()
    }

    private fun getNextColor(): Int {
        val r = Random()
        val index: Int = r.nextInt(9 - 0 + 1) + 0
        if (lastIndex == index)
            getNextColor()

        lastIndex = index
        return colorList[index]
    }
}