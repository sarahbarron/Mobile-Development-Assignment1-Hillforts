package org.wit.hillfort.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_hillfort.view.*
import kotlinx.android.synthetic.main.card_hillfort.view.*
import kotlinx.android.synthetic.main.card_image.view.*
import org.wit.hillfort.R
import org.wit.hillfort.helpers.readImageFromPath



class ImageAdapter constructor(private var images: ArrayList<String>) :

    RecyclerView.Adapter<ImageAdapter.MainHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {

        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_image,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val image = images[holder.adapterPosition]
        holder.bind(image)
    }


    //    Gets the number of images we need to display
    override fun getItemCount(): Int = images.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(image: String) {

            itemView.hillfortImage.setImageBitmap(
                readImageFromPath(
                    itemView.context,
                    image
                )
            )
        }
    }
}
