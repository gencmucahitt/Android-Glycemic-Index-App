package com.mucahit.glisemikapp.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mucahit.glisemikapp.activitys.FoodDetail
import com.mucahit.glisemikapp.models.Food
import com.mucahit.glisemikapp.databinding.FoodRowBinding
import com.mucahit.glisemikapp.models.DB

class FoodAdapter( val arr: ArrayList<Food> ) : RecyclerView.Adapter<FoodAdapter.ViewHolder>()
{
    private val searchArray=ArrayList<Food>(arr)
    class ViewHolder ( val bind: FoodRowBinding ) : RecyclerView.ViewHolder( bind.root  ) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = FoodRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=searchArray.get(position)
        holder.bind.apply {
            txtName.setText(item.name)
            txtGlyindex.setText(item.glyIndex)
            txtKarbon.setText(item.carbonhidratDegeri)
            txtCalori.setText(item.calori)

            rCardView.setOnLongClickListener {
                val alert = AlertDialog.Builder(holder.bind.root.context)
                alert.setTitle("Silme İşlemi!")
                alert.setMessage("Silmek istediğinizden emin misiniz?")
                alert.setNegativeButton("Güncelle", DialogInterface.OnClickListener { dialogInterface, i ->
                    val intent = Intent(holder.bind.root.context, FoodDetail::class.java)
                    intent.putExtra("uid",item.uid)
                    intent.putExtra("Name",item.name)
                    intent.putExtra("Gly",item.glyIndex)
                    intent.putExtra("Carbon",item.carbonhidratDegeri)
                    intent.putExtra("Calori",item.calori)
                    intent.putExtra("position",position)
                    holder.bind.root.context.startActivities(arrayOf(intent))
                })
                alert.setPositiveButton("Evet", DialogInterface.OnClickListener { dialogInterface, i ->
                    val db = DB(holder.bind.root.context)
                    val count = db.deleteFood(item.uid!!)
                    if ( count > 0 ) {
                        Toast.makeText(holder.bind.root.context, "Silme işlemi başarılı!", Toast.LENGTH_SHORT).show()
                        searchArray.removeAt(position)
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
    override fun getItemCount(): Int {
        return searchArray.size
    }
    fun searchFood(search:String){
        searchArray.clear()
        for (item in arr){
            if((item.name!!.lowercase().contains(search.lowercase())) ||  (item.glyIndex!!.contains(search)) || (item.carbonhidratDegeri!!.contains(search))|| (item.calori!!.contains(search))){
                searchArray.add(item)
            }
        }
        notifyDataSetChanged()
    }

}