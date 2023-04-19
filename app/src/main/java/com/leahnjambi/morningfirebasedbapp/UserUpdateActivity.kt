package com.leahnjambi.morningfirebasedbapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class UserUpdateActivity : AppCompatActivity() {
    lateinit var edtName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtIdNumber: EditText
    lateinit var progressDialog: ProgressDialog
    lateinit var btnUpdate: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_update)
        edtName = findViewById(R.id.mEdtName)
        edtEmail = findViewById(R.id.mEdtEmail)
        edtIdNumber = findViewById(R.id.mEdtId)
        btnUpdate = findViewById(R.id.btnUpdate)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Updating")
        progressDialog.setMessage("Please Wait...")
        var receivedName = intent.getStringExtra("name")
        var receivedEmail = intent.getStringExtra("email")
        var receivedIdNumber = intent.getStringExtra("idNumber")
        var receivedId = intent.getStringExtra("id")
        //Set to receive data to the EditText
        edtEmail.setText(receivedEmail)
        edtIdNumber.setText(receivedIdNumber)
        edtName.setText(receivedName)
        //Set onClickListener on the button Update
        btnUpdate.setOnClickListener {
            var name = edtName.text.toString().trim()
            var email = edtEmail.text.toString().trim()
            var idNumber = edtIdNumber.text.toString().trim()
            var id = receivedId
            if (name.isEmpty()){
                edtName.setError("Please fill this field")
                edtName.requestFocus()
            }else if (email.isEmpty()){
                edtEmail.setError("Please fill this field")
                edtEmail.requestFocus()

            }else if (idNumber.isEmpty()){
                edtIdNumber.setError("Please fill this field")
                edtIdNumber.requestFocus()
            }else{
                //Proceed to save
                //Prepare the user to be saved
                var user = User(name,email, idNumber, id!!)
                //Create a reference in the firebase database
                var ref = FirebaseDatabase.getInstance().
                getReference().child("User/"+id)
                //Show the program to start saving
                progressDialog.show()
                ref.setValue(user).addOnCompleteListener{
                    //Dismiss the program and check  if the task is successfull
                        task->
                    progressDialog.dismiss()
                    if (task.isSuccessful){
                        Toast.makeText(this,"User Updated successfully",
                            Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@UserUpdateActivity,UsersActivity::class.java))
                        finish()

                    }else{
                        Toast.makeText(this,"User updating failed",
                            Toast.LENGTH_LONG).show()

                    }
                }

            }

        }

    }

}
