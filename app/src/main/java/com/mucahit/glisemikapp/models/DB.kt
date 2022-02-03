package com.mucahit.glisemikapp.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mucahit.glisemikapp.utils.Service


class DB(var context: Context, name: String? = "mucocum123.db", factory: SQLiteDatabase.CursorFactory? = null, version: Int = 1) :
    SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL("CREATE TABLE \"Category\" (\n" +
                "\t\"cid\"\tTEXT,\n" +
                "\t\"title\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"cid\")\n" +
                ");")

        p0!!.execSQL("CREATE TABLE \"Food\" (\n" +
                "\t\"uid\"\tINTEGER,\n" +
                "\t\"cid\"\tTEXT,\n" +
                "\t\"name\"\tTEXT,\n" +
                "\t\"glyIndex\"\tTEXT,\n" +
                "\t\"carbonhidratDegeri\"\tTEXT,\n" +
                "\t\"calori\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"uid\" AUTOINCREMENT),\n" +
                "\tFOREIGN KEY(\"cid\") REFERENCES \"Category\"(\"cid\")\n" +
                ");")

                val se = Service(context)
                se.getHtmlFromWeb()
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS product")
        onCreate(p0)
    }


    fun addUrun(cid:String, name:String, glyIndex:String, carbonhidratDegeri:String, calori:String){
        val write = this.writableDatabase
        val values = ContentValues()
        values.put("cid", cid)
        values.put("name", name)
        values.put("glyIndex", glyIndex)
        values.put("carbonhidratDegeri", carbonhidratDegeri)
        values.put("calori", calori)
        val insertCount = write.insert("Food",null,values)
    }

    fun addKategori(title:String, cid:String){
        val write = this.writableDatabase
        val values = ContentValues()
        values.put("title", title)
        values.put("cid", cid)
        val insertCount = write.insert("Category", null,values)
    }

    fun allUrun() : ArrayList<Food> {
        val list = ArrayList<Food>()
        val read = this.readableDatabase
        val querySql = "select * from Food"
        val cursor = read.rawQuery(querySql, null)
        while ( cursor.moveToNext() ) {
            var uid:Int = cursor.getInt(0)
            var cid:String = cursor.getString(1)
            var name:String = cursor.getString(2)
            var glyIndex:String = cursor.getString(3)
            var carbonhidratDegeri:String = cursor.getString(4)
            var calori:String = cursor.getString(5)
            val p = Food(uid, cid,name,glyIndex,carbonhidratDegeri,calori)
            list.add(p)
        }
        return list
    }

    fun allUrunwithcid(cid:String) : ArrayList<Food> {
        val list = ArrayList<Food>()
        val read = this.readableDatabase
        val querySql = "select * from Food where cid =?"
        val cursor = read.rawQuery(querySql, arrayOf(cid))
        while ( cursor.moveToNext() ) {
            var uid:Int = cursor.getInt(0)
            var cid:String = cursor.getString(1)
            var name:String = cursor.getString(2)
            var glyIndex:String = cursor.getString(3)
            var carbonhidratDegeri:String = cursor.getString(4)
            var calori:String = cursor.getString(5)
            val p = Food(uid, cid,name,glyIndex,carbonhidratDegeri,calori)
            list.add(p)
        }
        return list
    }
    fun allKategori() : ArrayList<Category> {
        val list = ArrayList<Category>()
        val read = this.readableDatabase
        val querySql = "select * from Category"
        val cursor = read.rawQuery(querySql, null)
        while ( cursor.moveToNext() ) {
            var cid:String = cursor.getString(0)
            var title:String = cursor.getString(1)
            val p = Category(cid, title)
            list.add(p)
        }
        return list
    }
    fun updateFood(uid:Int, name:String, glyIndex:String, carbonhidratDegeri:String, calori:String): Int {
        val write = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("glyIndex", glyIndex)
        contentValues.put("carbonhidratDegeri", carbonhidratDegeri)
        contentValues.put("calori", calori)
        return write.update("Food", contentValues, "uid = ?", arrayOf(uid.toString()))
    }
    fun updateCategory(cid: String,title: String):Int{
        val write = this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put("cid",cid)
        contentValues.put("title",title)
        return write.update("Category", contentValues, "cid = ?", arrayOf(cid.toString()))

    }
    fun deleteFood(uid:Int):Int{
        val write = this.writableDatabase
        val contentValues=ContentValues()
        val count = write.delete("Food","uid="+uid,null)
        return count
    }
    fun deleteCategory(cid:String):Int{
        val write =this.writableDatabase
        val sutunKosul = "cid = ?"
        val argumanDegeri = arrayOf<String>(java.lang.String.valueOf(cid))
        val count = write.delete("Category", sutunKosul, argumanDegeri)
        write.delete("Food", sutunKosul,argumanDegeri)
        return count
    }

}