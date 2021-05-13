package com.example.bateerycharge

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class EmployeeAdapter(private val context: Context,private val list_emp: ArrayList<Employee>):BaseAdapter() {

    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val convertView : View = View.inflate(context,R.layout.employee_list,null)

        val txtname = convertView.findViewById(R.id.txtname) as TextView
        val txtmobile = convertView.findViewById(R.id.txtmobile) as TextView
        val txtemail = convertView.findViewById(R.id.txtemail) as TextView
        val txtaddress = convertView.findViewById(R.id.txtaddress) as TextView
        val txtdesignation = convertView.findViewById(R.id.txtdesignation) as TextView
        val imgprof = convertView.findViewById(R.id.imgprof) as ImageView

        val employee = getItem(position) as Employee
        
        txtname.text = employee.name
        txtmobile.text = employee.mobile
        txtemail.text = employee.email
        txtaddress.text = employee.address
        txtdesignation.text = employee.designation

        if(employee.gender == "Male"){
            imgprof.setImageResource(R.drawable.male)
        }else{
            imgprof.setImageResource(R.drawable.female)
        }


        return convertView
    }

    override fun getItem(position: Int): Any {

        return list_emp[position]

    }

    override fun getItemId(position: Int): Long {

        return position.toLong()
    }

    override fun getCount(): Int {

        return list_emp.size

    }

    companion object{

    }

}



