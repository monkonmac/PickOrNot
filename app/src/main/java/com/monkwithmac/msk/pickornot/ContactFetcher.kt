package com.monkwithmac.msk.pickornot

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader


class ContactFetcher{

    private var loaderManager: LoaderManager? = null
    private val localContactList = ArrayList<ContactModel>()
    companion object{
        private const val CONTACTS_LOADER_ID = 1
        // The column index for the _ID column
        private const val CONTACT_ID_INDEX: Int = 0
        // The column index for the CONTACT_KEY column
        private const val CONTACT_KEY_INDEX: Int = 1

        private val PROJECTION: Array<out String> = arrayOf(
            ContactsContract.Data._ID,
            ContactsContract.Data.MIMETYPE,
            ContactsContract.Data.DATA1,
            ContactsContract.Data.DATA2,
            ContactsContract.Data.DATA3,
            ContactsContract.Data.DATA4,
            ContactsContract.Data.DATA5,
            ContactsContract.Data.DATA6,
            ContactsContract.Data.DATA7,
            ContactsContract.Data.DATA8,
            ContactsContract.Data.DATA9,
            ContactsContract.Data.DATA10,
            ContactsContract.Data.DATA11,
            ContactsContract.Data.DATA12,
            ContactsContract.Data.DATA13,
            ContactsContract.Data.DATA14,
            ContactsContract.Data.DATA15
        )

        private const val SORT_ORDER = ContactsContract.Data.CONTACT_LAST_UPDATED_TIMESTAMP
    }

    private val FROM_COLUMNS: Array<String> = arrayOf(
        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)

    @SuppressLint("InlinedApi")
    private val PROJECTION: Array<out String> = arrayOf(
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.LOOKUP_KEY,
        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    )

    fun getContacts(activity: AppCompatActivity) : ArrayList<ContactModel>{
        localContactList.clear()
        val cr: ContentResolver = activity.contentResolver
        val cur = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )

        if (cur?.count ?: 0 > 0) {
            while (cur != null && cur.moveToNext()) {
                val id = cur.getString(
                    cur.getColumnIndex(ContactsContract.Contacts._ID)
                )
                val name = cur.getString(
                    cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                    )
                )
                if (cur.getInt(
                        cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER
                        )
                    ) > 0
                ) {
                    val pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null
                    )
                    val contacts = ArrayList<String>()
                    while (pCur!!.moveToNext()) {
                        val phoneNo = pCur.getString(
                            pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                        )
                        contacts.add(phoneNo)
                    }
                    localContactList.add(ContactModel(name, contacts))
                    pCur.close()
                }
            }
        }
        cur?.close()
        return localContactList
    }


}