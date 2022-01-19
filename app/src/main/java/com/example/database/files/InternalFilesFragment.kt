package com.example.database.files

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.database.databinding.FragmentFilesBinding
import java.io.IOException
import java.util.*

class InternalFilesFragment : Fragment() {

    private var _binding: FragmentFilesBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        val fileName = UUID.randomUUID().toString()
        val isPhotoSaved = saveFileToInternalStorage(fileName, bitmap)
        if (isPhotoSaved) {
            loadImages()
            Toast.makeText(requireContext(), "Successfully saved photo", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Failed to saved photo", Toast.LENGTH_SHORT).show()
        }
    }

    private val imagesAdapter by lazy {
        ImagesAdapter(requireContext()) { item ->
            if (deleteFileFromInternalStorage(item.fileName)) {
                loadImages()
                Toast.makeText(requireContext(), "Successfully delete photo", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed to delete photo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFilesBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            recyclerView.apply {
                layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
                adapter = imagesAdapter
            }

            buttonSave.setOnClickListener {
                takePhoto.launch(null)
            }

            buttonLoad.setOnClickListener {
                loadImages()
            }
        }

        loadImages()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadImages() {
        imagesAdapter.submitList(openFilesFromInternalStorage())
    }

    private fun saveFileToInternalStorage(fileName: String, image: Bitmap): Boolean {
        return try {
            requireContext().openFileOutput("$fileName.jpg", Context.MODE_PRIVATE).use { stream ->
                if (!image.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap")
                }
            }
            true
        } catch (e: IOException) {
            false
        }
    }

    private fun openFilesFromInternalStorage(): List<ImageItem> {
        return requireContext()
            .filesDir
            .listFiles()
            ?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }
            ?.map { file ->
                val bytes = file.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                ImageItem.Internal(file.name, bmp)
            }
            ?: emptyList()
    }

    private fun deleteFileFromInternalStorage(fileName: String): Boolean {
        return try {
            requireContext().deleteFile(fileName)
        } catch (e: IOException) {
            false
        }
    }
}