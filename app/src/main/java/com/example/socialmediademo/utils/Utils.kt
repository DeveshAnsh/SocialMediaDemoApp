package com.example.socialmediademo.utils

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.net.URI
import java.util.UUID
import javax.security.auth.callback.Callback


fun uploadImage(uri: Uri, folderName: String, callback: (String?) -> Unit) {
    var imageUrl: String? = null

    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageUrl = it.toString()
                callback(imageUrl)
            }
        }
}

fun uploadVideo(
    uri: Uri,
    folderName: String,

    progressDialog: ProgressDialog,
    callback: (String?) -> Unit
) {
    var imageUrl: String? = null
    progressDialog.setTitle("uploding....")
    progressDialog.show()


    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageUrl = it.toString()
                callback(imageUrl)
            }
        }
        .addOnProgressListener {
            val uploadedValue: Long =( it.bytesTransferred / it.totalByteCount)*100
            progressDialog.setMessage("uploaded $uploadedValue %")
        }


}
