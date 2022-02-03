package com.mucahit.glisemikapp.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mucahit.glisemikapp.adapter.CategoryAdapter
import com.mucahit.glisemikapp.databinding.ActivityCategoryListBinding
import com.mucahit.glisemikapp.models.DB

class CategoryList : AppCompatActivity() {
    private lateinit var bind:ActivityCategoryListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityCategoryListBinding.inflate(layoutInflater)
        setContentView(bind.root)
        //supportActionBar?.hide()
        val db = DB(this)
        val datas = db.allKategori()
        bind.CategoryList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false )
        bind.CategoryList.adapter = CategoryAdapter(datas)

        bind.button2.setOnClickListener {
            val i= Intent(this, CategoryAdd::class.java)
            startActivity(i)
        }
    }
}
