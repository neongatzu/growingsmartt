package com.example.informationdisplayapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val itemList:ArrayList<item>)
    : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    var onItemClick : ((item) -> Unit)? = null

    class ItemViewHolder(itemView:View)
    : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
           val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent , false)
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
           val item = itemList[position]
            holder.imageView.setImageResource(item.image)
            holder.textView.text= item.name

            holder.itemView.setOnClickListener{
                onItemClick?.invoke(item)
            }
        }

        override fun getItemCount(): Int {
          return itemList.size
        }
    }
