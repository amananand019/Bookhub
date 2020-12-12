package com.devil.premises.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.devil.premises.bookhub.R
import com.devil.premises.bookhub.model.Book
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso

class DashboardRecyclerAdapter(val context: Context, val itemList: ArrayList<Book>): RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row, parent, false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book = itemList[position]
        holder.txtBookName.text = book.bookName
        holder.txtBookAuthor.text = book.bookAuthor
        holder.txtBookPrice.text = book.bookPrice
        holder.txtBookRating.text = book.bookRating
        holder.txtBookRating.text = book.bookRating
        //holder.imgBookImage.setImageResource(book.bookImage)
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.imgBookImage)

        holder.rlContent.setOnClickListener {
            Toast.makeText(context, "Clicked on ${holder.txtBookName.text}", Toast.LENGTH_SHORT).show()
        }
    }

    class DashboardViewHolder(view:View): RecyclerView.ViewHolder(view){
        val txtBookName: TextView = view.findViewById(R.id.txt_BookName)
        val txtBookAuthor: TextView = view.findViewById(R.id.txt_AuthorName)
        val txtBookPrice: TextView = view.findViewById(R.id.txt_Price)
        val txtBookRating: TextView = view.findViewById(R.id.txt_BookRating)
        val imgBookImage: CircularImageView = view.findViewById(R.id.img_BookImage)

        val rlContent: RelativeLayout = view.findViewById(R.id.rlContent)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}