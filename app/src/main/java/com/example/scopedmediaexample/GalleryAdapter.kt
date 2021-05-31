package com.example.mygallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.scopedmediaexample.databinding.GridItemBinding

class GalleryAdapter(val onClickListener: OnClickListener): ListAdapter<ImageModel, GalleryAdapter.GalleryViewHolder>(
    DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<ImageModel>(){
        override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
            return oldItem ==newItem
        }

        override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
            return oldItem.uri == newItem.uri
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(GridItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(image)
        }


    }
    class GalleryViewHolder(private var binding: GridItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(image: ImageModel) {
            binding.image = image
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (image : ImageModel) -> Unit) {
        fun onClick(image: ImageModel) = clickListener(image)
    }

}