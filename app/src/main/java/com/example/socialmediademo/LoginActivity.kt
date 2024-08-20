package com.example.socialmediademo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.socialmediademo.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.login.setOnClickListener {
            if (binding.email.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("")
            ) {
                Toast.makeText(this@LoginActivity, "all field are required", Toast.LENGTH_SHORT)
                    .show()

            } else {
                var user = com.example.socialmediademo.Models.User(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                )
                Firebase.auth.signInWithEmailAndPassword(user.email!!, user.password!!)
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                            finish()

                        }else{
                            Toast.makeText(this@LoginActivity,it.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}