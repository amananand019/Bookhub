package com.devil.premises.bookhub.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devil.premises.bookhub.R
import com.devil.premises.bookhub.activity.DescriptionActivity
import com.devil.premises.bookhub.database.BookEntity
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter(val context: Context, val bookList: List<BookEntity>):
    RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {
    class FavouriteViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtBookName: TextView = view.findViewById(R.id.txt_FavBookName)
        val txtBookAuthor: TextView = view.findViewById(R.id.txt_FavAuthorName)
        val txtBookPrice: TextView = view.findViewById(R.id.txt_FavPrice)
        val txtBookRating: TextView = view.findViewById(R.id.txt_FavBookRating)
        val imgBookImg: ImageView= view.findViewById(R.id.img_FavBookImage)
        val favContent: RelativeLayout = view.findViewById(R.id.favouriteContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_favourite_single_tile, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val book = bookList[position]
        holder.txtBookName.text = book.bookName
        holder.txtBookAuthor.text = book.bookAuthor
        holder.txtBookPrice.text = book.bookPrice
        holder.txtBookRating.text = book.bookRating

        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.imgBookImg)


        holder.favContent.setOnClickListener{
//            val intent = Intent(context, DescriptionActivity::class.java)
//            intent.putExtra("book_id", book.book_id)
//            context.startActivity(intent)

            val intent = Intent(context, DescriptionActivity::class.java)
            intent.putExtra("book_id", book.book_id.toString())
            context.startActivity(intent)

            Log.i("TAG", "onBindViewHolder: Book_id:" + book.book_id)
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}