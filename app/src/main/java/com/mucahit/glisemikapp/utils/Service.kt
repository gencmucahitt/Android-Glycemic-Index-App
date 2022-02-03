package com.mucahit.glisemikapp.utils

import android.content.Context
import com.mucahit.glisemikapp.models.Category
import com.mucahit.glisemikapp.models.DB
import com.mucahit.glisemikapp.models.Food
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class Service(context: Context) {
    val db= DB(context)
    fun getHtmlFromWeb() {
        Thread(Runnable {
            val stringBuilder = StringBuilder()
            try {
                val doc: Document = Jsoup.connect("http://kolaydoktor.com/saglik-icin-yasam/diyet-ve-beslenme/besinlerin-glisemik-indeks-tablosu/0503/1").get()
                val table: Elements = doc.select("table")
                table.forEach {
                    val sutun : Elements = it.select("tbody").select("tr")
                    var cid:String = ""
                    for((index, row) in sutun.withIndex()){
                        if(index == 0){
                            val title = row.select("p").text()
                            if(title.trim().isEmpty()) continue
                            val category = Category()
                            category.title = title
                            cid = Category().cid
                            db.addKategori(title,cid)
                        }
                        else if (index == 1){continue}
                        else{
                            val elements: Elements = row.select("p")
                            val food = Food()
                            food.cid = cid
                            food.name = elements[0].text()
                            food.glyIndex = elements[1].text().trim()
                            food.carbonhidratDegeri = elements[2].text().toString()
                            food.calori = elements[3].text().toString()
                            db.addUrun(food.cid!!,food.name!!,food.glyIndex!!,food.carbonhidratDegeri!!,food.calori!!)
                        }
                    }
                }

            } catch (e: IOException) {
                stringBuilder.append("Error : ").append(e.message).append("\n")
            }

        }).start()
    }
}