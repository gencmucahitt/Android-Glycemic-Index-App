package com.mucahit.glisemikapp.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mucahit.glisemikapp.activitys.CategoryAdd
import com.mucahit.glisemikapp.activitys.FoodList
import com.mucahit.glisemikapp.databinding.CategoryRowBinding
import com.mucahit.glisemikapp.models.Category
import com.mucahit.glisemikapp.utils.foodDeger
import com.mucahit.glisemikapp.models.DB

class CategoryAdapter( val arr: ArrayList<Category> ) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){
    class ViewHolder ( val bind: CategoryRowBinding) : RecyclerView.ViewHolder( bind.root  ) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = CategoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=arr.get(position)
        holder.bind.apply {
            txtCategory.setText(item.title)
            rCardView.setOnClickListener {
                val db =DB(root.context)
                foodDeger.list.clear()
                foodDeger.cid=item.cid
                foodDeger.list.addAll(db.allUrunwithcid(item.cid))
                val i = Intent(holder.bind.root.context, FoodList::class.java)
                notifyDataSetChanged()
                holder.bind.root.context.startActivity(i)
            }
            rCardView.setOnLongClickListener {
                val alert = AlertDialog.Builder(holder.bind.root.context)
                alert.setTitle("Silme İşlemi!")
                alert.setMessage("Silmek istediğinizden emin misiniz?")
                alert.setNegativeButton("Güncelle", DialogInterface.OnClickListener { dialogInterface, i ->
                    val i = Intent(holder.bind.root.context, CategoryAdd::class.java)
                    foodDeger.cid=item.cid
                    i.putExtra("tittle",item.title)
                    Toast.makeText(holder.bind.root.context, "Kategori Güncelleme İşlemi Başarılı", Toast.LENGTH_SHORT).show()
                    holder.bind.root.context.startActivity(i)
                })
                alert.setPositiveButton("Evet", DialogInterface.OnClickListener { dialogInterface, i ->
                    val db = DB(holder.bind.root.context)
                    val count = db.deleteCategory(item.cid)
                    if ( count > 0 ) {
                        Toast.makeText(holder.bind.root.context, "Silme işlemi başarılı!", Toast.LENGTH_SHORT).show()
                        arr.removeAt(position)
                        notifyItemRemoved(position)
                    }else {
                        Toast.makeText(holder.bind.root.context, "Silme işlemi hatası!", Toast.LENGTH_SHORT).show()
                    }
                })
                alert.show()
                true

            }
        }

    }
   /* object foodCid {
        val list = ArrayList<Food>()
    }*/

    override fun getItemCount(): Int {
        return arr.size
    }
}