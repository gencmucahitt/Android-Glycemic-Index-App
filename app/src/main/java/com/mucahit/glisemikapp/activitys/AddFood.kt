package com.mucahit.glisemikapp.activitys

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mucahit.glisemikapp.databinding.ActivityAddFoodBinding
import com.mucahit.glisemikapp.utils.foodDeger
import com.mucahit.glisemikapp.models.DB
import com.mucahit.glisemikapp.models.Food

class AddFood : AppCompatActivity() {
    private lateinit var bind:ActivityAddFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val db=DB(this)
        bind.fbtnAddFood.setOnClickListener {
            val alert = AlertDialog.Builder(bind.root.context)
            alert.setTitle("Ekleme İşlemi!")
            alert.setMessage("Eklemek istediğinizden emin misiniz?")
            alert.setNegativeButton("Evet", DialogInterface.OnClickListener { dialogInterface, i ->
                val name= bind.ftxtName.text.toString()
                val glyIndex=bind.ftxtGli.text.toString()
                val carbonhidratDegeri=bind.txtCarbon.text.toString()
                val calori=bind.ftxtCalori.text.toString()
                if(name.isEmpty()){
                    bind.ftxtName.error = "Besin Girişi Yapmadınız!"
                    bind.ftxtName.requestFocus()
                    return@OnClickListener
                }
                if(glyIndex.isEmpty()){
                    bind.ftxtGli.error = "Glisemik İndex Girişi Yapmadınız!"
                    bind.ftxtName.requestFocus()
                    return@OnClickListener
                }
                if(carbonhidratDegeri.isEmpty()){
                    bind.txtCarbon.error = "Karbonhidrat Değeri Girişi Yapmadınız!"
                    bind.txtCarbon.requestFocus()
                    return@OnClickListener
                }
                if(calori.isEmpty()){
                    bind.ftxtCalori.error = "Kalori Değeri Girişi Yapmadınız!"
                    bind.ftxtCalori.requestFocus()
                    return@OnClickListener
                }
                db.addUrun(foodDeger.cid,name, glyIndex, carbonhidratDegeri, calori)
                val food =Food()
                food.name=name
                food.glyIndex=glyIndex
                food.carbonhidratDegeri=carbonhidratDegeri
                food.calori=calori
                foodDeger.list.add(food)
                val i = Intent(this, FoodList::class.java)
                startActivity(i)
                Toast.makeText(this, "Besin Ekleme İşlemi Başarılı", Toast.LENGTH_SHORT).show()
                finish()
            })
            alert.setPositiveButton("Hayır", DialogInterface.OnClickListener { dialogInterface, i ->})
            alert.show()
            true

        }

    }
}