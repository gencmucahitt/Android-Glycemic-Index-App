package com.mucahit.glisemikapp.activitys

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mucahit.glisemikapp.databinding.ActivityCategoryAddBinding
import com.mucahit.glisemikapp.utils.foodDeger
import com.mucahit.glisemikapp.models.Category
import com.mucahit.glisemikapp.models.DB

class CategoryAdd : AppCompatActivity() {
    private lateinit var bind:ActivityCategoryAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityCategoryAddBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val db=DB(this)
        val t=intent.getStringExtra("tittle")
        bind.ctxtTittle.setText(t)
        bind.cbtnCateAdd.setOnClickListener {
            val alert = AlertDialog.Builder(bind.root.context)
            alert.setTitle("Kategori Ekleme İşlemi!")
            alert.setMessage("Yeni Kategori Eklemek İstediğinizden Emin Misiniz?")
            alert.setNegativeButton("Evet", DialogInterface.OnClickListener { dialogInterface, i ->
                val title = bind.ctxtTittle.text.toString()
                val cid = Category().cid
                if(title.isEmpty()){
                    bind.ctxtTittle.error = "Kategori İsmi Girişi Yapmadınız!"
                    bind.ctxtTittle.requestFocus()
                    return@OnClickListener
                }
                db.addKategori(title,cid)
                val intent= Intent(this, CategoryList::class.java)
                startActivity(intent)
                Toast.makeText(this, "Kategori Ekleme İşlemi Başarılı", Toast.LENGTH_SHORT).show()
                finish()
            })
            alert.setPositiveButton("Hayır", DialogInterface.OnClickListener{dialogInterface, i ->  })
            alert.show()
            true

        }
        bind.cbtnUpdate.setOnClickListener {
            val alert = AlertDialog.Builder(bind.root.context)
            alert.setTitle("Kategori Güncelleme İşlemi!")
            alert.setMessage("Güncellemek istediğinizden emin misiniz?")
            alert.setNegativeButton("Evet", DialogInterface.OnClickListener { dialogInterface, i ->
                val title=bind.ctxtTittle.text.toString()
                db.updateCategory(foodDeger.cid,title)
                val intent= Intent(this, CategoryList::class.java)
                startActivity(intent)
                Toast.makeText(this, "Kategori Güncelleme İşlemi Başarılı", Toast.LENGTH_SHORT).show()
                finish()
            })
            alert.setPositiveButton("Hayır", DialogInterface.OnClickListener{dialogInterface, i ->  })
            alert.show()
            true

        }

    }
}