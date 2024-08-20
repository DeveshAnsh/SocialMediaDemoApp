package com.example.socialmediademo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.socialmediademo.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.example.socialmediademo.Models.User
import com.example.socialmediademo.utils.USER_NODE

import com.example.socialmediademo.utils.USER_PROFIlE_FOLDER
import com.example.socialmediademo.utils.uploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject


import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class SignUpActivity : AppCompatActivity() {

    val binding by lazy {

        ActivitySignUpBinding.inflate(layoutInflater)
    }
    lateinit var user: User
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri ->
        uri?.let {
            uploadImage(uri, USER_PROFIlE_FOLDER) {
                if (it!= null) {
                    user.image = it
                    binding.profileImage.setImageURI(uri)

                }


            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        user = User()
        if (intent.hasExtra("MODE")) {
            if (intent.getIntExtra("MODE", -1) == 1) {
                binding.Register.text = "Update Image"
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid)
                    .get()
                    .addOnSuccessListener {
                        user = it.toObject<User>()!!
                        if (!user.image.isNullOrEmpty()) {
                            Picasso.get().load(user.image).into(binding.profileImage)


                        }
                        binding.name.editText?.setText(user.name)
                        binding.email.editText?.setText(user.email)
                        binding.password.editText?.setText(user.password)

                    }
            }
        }
        binding.Register.setOnClickListener {
            if (intent.hasExtra("MODE")) {
                if (intent.getIntExtra("MODE", -1) == 1) {
                    Firebase.firestore.collection(USER_NODE)
                        .document(Firebase.auth.currentUser!!.uid).set(user)
                        .addOnSuccessListener {
                            startActivity(
                                Intent(
                                    this@SignUpActivity,
                                    HomeActivity::class.java
                                )
                            )
                            finish()
                        }
                }
            } else {


                if (binding.name.editText?.text.toString().equals("") or
                    binding.email.editText?.text.toString().equals("") or
                    binding.password.editText?.text.toString().equals("")
                ) {
                    Toast.makeText(
                        this@SignUpActivity,
                        "field required", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(

                        binding.email.editText?.text.toString(),
                        binding.password.editText?.text.toString()
                    ).addOnCompleteListener { result ->

                        if (result.isSuccessful) {
                            user.name = binding.name.editText?.text.toString()
                            user.password = binding.password.editText?.text.toString()
                            user.email = binding.email.editText?.text.toString()
                            Firebase.firestore.collection(USER_NODE)
                                .document(Firebase.auth.currentUser!!.uid).set(user)
                                .addOnSuccessListener {
                                    startActivity(
                                        Intent(
                                            this@SignUpActivity,
                                            HomeActivity::class.java
                                        )
                                    )
                                    finish()
                                }

                        } else {
                            Toast.makeText(
                                this@SignUpActivity,
                                result.exception?.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }


            }
        }
        binding.addImage.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.loginbtn.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()

        }
    }
}