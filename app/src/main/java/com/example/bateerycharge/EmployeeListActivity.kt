package com.example.bateerycharge

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_employee_list.*

class EmployeeListActivity : AppCompatActivity() {

    var emplist : ArrayList<Employee> = ArrayList<Employee>()
    internal var dbHelper = DatabaseHelper(this)
    var employeeAdapter : EmployeeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_list)

        init()

        getListData()

        setListeners()
    }

    fun init(){
        val list_employee = findViewById(R.id.list_employee) as ListView

    }

    fun setListeners(){
        list_employee.setOnItemClickListener { parent, view, position, id ->

            val employee = Employee()
            employee.name = emplist.get(position).name

            //display alert dialog
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Are you sure you want to remove "+emplist.get(position).name+" from list?")
            dialog.setCancelable(true)

            //positive button
            dialog.setPositiveButton("Yes",{dialog, which ->

                dbHelper.delEmployee(employee)

                getListData()

                dialog.dismiss()

            })

            //negative button
            dialog.setNegativeButton("No",{dialog, which ->
                dialog.dismiss()
            })

            val b = dialog.create()
            dialog.show()

        }

    }

    fun getListData(){
        emplist.clear()

        val db = dbHelper.writableDatabase
        val res = "SELECT * FROM ${DatabaseHelper.TABLE_EMPLOYEE}"
        val cursor : Cursor
        //cursor = db.rawQuery(res,null)
        cursor = dbHelper.getAllEmployee()!!
        if(cursor.count>0){
            cursor!!.moveToFirst()
            do {
                val employee = Employee()
                employee.name = cursor.getString(cursor.getColumnIndex("FULLNAME"))
                employee.mobile = cursor.getString(cursor.getColumnIndex("MOBILE"))
                employee.email = cursor.getString(cursor.getColumnIndex("EMAIL"))
                employee.address = cursor.getString(cursor.getColumnIndex("ADDRESS"))
                employee.designation = cursor.getString(cursor.getColumnIndex("DESIGNATION"))
                employee.gender = cursor.getString(cursor.getColumnIndex("GENDER"))
                emplist.add(employee)
            }while (cursor.moveToNext())

        }

        employeeAdapter = EmployeeAdapter(this, emplist)
        list_employee.adapter = employeeAdapter
    }

}
