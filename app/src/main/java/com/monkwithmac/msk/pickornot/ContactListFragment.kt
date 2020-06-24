package com.monkwithmac.msk.pickornot


import android.os.Bundle
import android.util.SparseIntArray
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var contactListAdapter: ContactListAdapter
    private var contactList: ArrayList<ContactModel> = ArrayList()
    private lateinit var contactFetcher:ContactFetcher

    companion object {
        @JvmStatic
        fun newInstance() =
            ContactListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        contactFetcher = ContactFetcher()
        contactList = contactFetcher.getContacts(activity as AppCompatActivity)
        contactListAdapter = ContactListAdapter(contactList)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = contactListAdapter
        return view
    }


}
