package com.example.socialmediademo.Post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.socialmediademo.HomeActivity
import com.example.socialmediademo.Models.Post
import com.example.socialmediademo.Models.User
import com.example.socialmediademo.databinding.ActivityPostBinding
import com.example.socialmediademo.utils.POST
import com.example.socialmediademo.utils.POST_FOLDER
import com.example.socialmediademo.utils.USER_NODE
import com.example.socialmediademo.utils.uploadImage
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class PostActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageUrl: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri ->
        uri?.let {
            uploadImage(uri, POST_FOLDER) { url ->
                if (url != null) {
                    binding.selectImage.setImageURI(uri)
                    imageUrl = url

                }
            }

        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()

        }


        binding.cancelButton.setOnClickListener {

            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()


        }


        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }


        binding.postButton.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document().get()
                .addOnSuccessListener {
                    var user = it.toObject<User>()!!
                    val post: Post = Post(
                        postUrl = imageUrl!!,
                        caption = binding.caption.editText?.text.toString(),
                        uid = Firebase.auth.currentUser!!.uid,
                        time = System.currentTimeMillis().toString()
                    )
                    //write the argument here inside th post image url and caption 2:53


                    Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document()
                            .set(post)
                            .addOnSuccessListener {
                                startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                                finish()
                            }


                    }


                }

        }


    }


}