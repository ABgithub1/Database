package com.example.database.files

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.database.databinding.ItemImageBinding

class ImagesAdapter(
    context: Context,
    private val onLongItemClicked: (ImageItem) -> Unit
) : ListAdapter<ImageItem, ImageViewHolder>(DIFF_CALLBACK) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            binding = ItemImageBinding.inflate(layoutInflater, parent, false),
            onLongItemClicked = onLongItemClicked
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageItem>() {
            override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class ImageViewHolder(
    private val binding: ItemImageBinding,
    private val onLongItemClicked: (ImageItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ImageItem) {
        with(binding.image) {

            when (item) {
                is ImageItem.External -> setImageURI(item.contentUri)
                is ImageItem.Internal -> setImageBitmap(item.bitmap)
            }

            setOnLongClickListener {
                onLongItemClicked(item)
                true
            }
        }
    }
}