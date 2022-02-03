package com.mucahit.glisemikapp.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.mucahit.glisemikapp.adapter.FoodAdapter
import com.mucahit.glisemikapp.databinding.ActivityFoodListBinding
import com.mucahit.glisemikapp.utils.foodDeger
import com.mucahit.glisemikapp.models.DB

class FoodList : AppCompatActivity() {
    private lateinit var bind: ActivityFoodListBinding
    private lateinit var adapter: FoodAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(bind.root)
        supportActionBar?.hide()
        searchListener()
        val datas = foodDeger.list
        adapter= FoodAdapter(datas)
        bind.foodList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        bind.foodList.adapter = adapter
        bind.button.setOnClickListener {
            val i = Intent(this, AddFood::class.java)
            startActivity(i)
            finish()
        }
    }
    fun searchListener() {
        bind.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    if (p0.isNotEmpty()) {
                        adapter.searchFood(p0)
                    } else {
                        adapter.searchFood("")
                    }
                }
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    if (p0.isNotEmpty()) {
                        adapter.searchFood(p0)
                    } else {
                        adapter.searchFood("")
                    }
                }
                return true
            }

        })

    }
    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

}



