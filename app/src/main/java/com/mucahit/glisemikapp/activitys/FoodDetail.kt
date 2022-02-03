package com.mucahit.glisemikapp.activitys

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.mucahit.glisemikapp.adapter.CategoryAdapter
import com.mucahit.glisemikapp.adapter.FoodAdapter
import com.mucahit.glisemikapp.databinding.ActivityFoodDetailBinding
import com.mucahit.glisemikapp.models.Category
import com.mucahit.glisemikapp.models.DB
import com.mucahit.glisemikapp.utils.foodDeger
import kotlin.properties.Delegates

class FoodDetail : AppCompatActivity() {
    private lateinit var bind:ActivityFoodDetailBinding
    var uid:Int=0
    lateinit var db:DB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val db= DB(this)
        var uid=intent.getIntExtra("uid",0)
        val name=intent.getStringExtra("Name")
        val gly=intent.getStringExtra("Gly")
        val carbon=intent.getStringExtra("Carbon")
        val calori=intent.getStringExtra("Calori")
        val position = intent.getIntExtra("position",-1)
        bind.apply {
            dfoodName.setText(name)
            dfoodGli.setText(gly)
            dfoodCarbon.setText(carbon)
            dfoodCalori.setText(calori)
        }
        bind.dfoodUpdate.setOnClickListener {
            val alert = AlertDialog.Builder(bind.root.context)
            alert.setTitle("Güncelleme İşlemi!")
            alert.setMessage("Güncellemek istediğinizden emin misiniz?")
            alert.setNegativeButton("Evet", DialogInterface.OnClickListener { dialogInterface, i ->
                val name = bind.dfoodName.text.toString()
                val gly=bind.dfoodGli.text.toString()
                val carbon=bind.dfoodCarbon.text.toString()
                val calori=bind.dfoodCalori.text.toString()
                db.updateFood(uid,name,gly,carbon,calori)
                foodDeger.list[position].name=name
                foodDeger.list[position].glyIndex=gly
                foodDeger.list[position].carbonhidratDegeri=carbon
                foodDeger.list[position].calori=calori
                Toast.makeText(this, "Güncelleme işlemi başarılı", Toast.LENGTH_SHORT).show()
                val i=Intent(bind.root.context,FoodList::class.java)
                startActivity(i)
                finish()
            })
            alert.setPositiveButton("Hayır", DialogInterface.OnClickListener { dialogInterface, i ->})
            alert.show()

            true


        }
    }


}