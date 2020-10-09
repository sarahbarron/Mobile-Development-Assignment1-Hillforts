package org.wit.hillfort.helpers

import android.app.Activity
import android.content.Intent
import org.wit.hillfort.R

// Function to show an image picker dialog
fun showImagePicker(parent: Activity, id: Int) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_OPEN_DOCUMENT
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    val chooser = Intent.createChooser(intent, R.string.select_hillfort_image.toString())
    parent.startActivityForResult(chooser, id)
}
