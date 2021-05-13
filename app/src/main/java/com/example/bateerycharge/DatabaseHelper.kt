package com.example.bateerycharge
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,1){

    override fun onCreate(db: SQLiteDatabase?) {
      //type 1 to check not null
        /*if (db != null) {
            db.execSQL(qry)
        }*/

        db!!.execSQL(qry)    //type 2 to check not null

        //db!!.execSQL(qry)   //type 3 to check not null
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS "+ TABLE_EMPLOYEE)
        onCreate(db)
    }

    //add data into database
    fun addEmployee(name :String, mobile :String, email :String, address :String,designation : String, gender :String){

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("FULLNAME",name)
        contentValues.put("MOBILE",mobile)
        contentValues.put("EMAIL",email)
        contentValues.put("ADDRESS",address)
        contentValues.put("DESIGNATION",designation)
        contentValues.put("GENDER",gender)
        db.insert(TABLE_EMPLOYEE,null,contentValues)

    }

    //delete data from database
        fun delEmployee(emp:Employee):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("FULLNAME",emp.name)
        val success = db.delete(TABLE_EMPLOYEE,"FULLNAME='"+emp.name+"'",null)
        return success
    }

    fun updateEmployee(emp:Employee):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("FULLNAME",emp.name)
        val success = db.update(TABLE_EMPLOYEE,contentValues,"FULLNAME='"+emp.name+"'",null)
        return success

    }

    val allData : Cursor
        get() {
            val db = this.readableDatabase
            val res = db.rawQuery("SELECT * FROM " + TABLE_EMPLOYEE, null)
            return res
        }

    fun getAllEmployee(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM "+TABLE_EMPLOYEE, null)
    }

    companion object{
        val DATABASE_NAME = "employeedatabase.db"
        val TABLE_EMPLOYEE = "Employee"

        val qry = "CREATE TABLE $TABLE_EMPLOYEE (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " FULLNAME TEXT, MOBILE TEXT, EMAIL TEXT, ADDRESS TEXT, DESIGNATION TEXT, GENDER TEXT)"
    }

}

