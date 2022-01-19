package com.example.database.files

import android.graphics.Bitmap
import android.net.Uri

sealed class ImageItem(val fileName: String) {
    class Internal(
        fileName: String,
        val bitmap: Bitmap
    ) : ImageItem(fileName)

    class External(
        fileName: String,
        val contentUri: Uri
    ) : ImageItem(fileName)
}
