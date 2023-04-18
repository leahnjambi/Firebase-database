package com.leahnjambi.morningfirebasedbapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.style.TtsSpan.OrdinalBuilder
import android.widget.AbsListView
import android.widget.Adapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UsersActivity : AppCompatActivity() {
    lateinit var listUser: ListView
    lateinit var adapter: CustomAdapter
    lateinit var progressDialog: ProgressDialog
    lateinit var users:ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        listUser = findViewById(R.id.mListUsers)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Please wait...")
        users = ArrayList()
        adapter = CustomAdapter(this,users)
        var ref = FirebaseDatabase.getInstance().getReference().child("Users")
        //START LOADING THE USER FROM THE REF
        progressDialog.show()
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
                users.clear()
                for (data in snapshot.children){
                    var user = data.getValue(User::class.java)
                    users.add(user!!)
                }
                adapter.notifyDataSetChanged()
                progressDialog.dismiss()
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Toast.makeText(this@UsersActivity,"Sorry , OB is inaccessible",
                Toast.LENGTH_LONG).show()
            }
        })
        // Tell the Listener to
        listUser.adapter = adapter
    }
}