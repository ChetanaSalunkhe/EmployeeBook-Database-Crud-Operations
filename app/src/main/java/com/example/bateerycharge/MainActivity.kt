package com.example.bateerycharge

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.BatteryManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.view.ViewCompat.setX
import android.opengl.ETC1.getWidth
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatSeekBar
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var currProgress = 0
    internal var dbHelper = DatabaseHelper(this)
    var gender :String = ""
    var isMale:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListeners()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setListeners(){
        increase_progress.setOnClickListener {
            if(currProgress <= 90){
                currProgress += 10
                updateProgressResult()
            }
        }

        decrease_progress.setOnClickListener {
            if(currProgress >=10){
                currProgress -= 10
                updateProgressResult()
            }
        }

        rdbtnmale.setOnClickListener {
            isMale = true
            if(isMale==true){
               rdbtnfemale.isChecked = false
                rdbtnmale.isChecked = true
                gender = "Male"
            }else{
                rdbtnfemale.isChecked = true
                rdbtnmale.isChecked = false
                gender = "Female"

            }
        }

        rdbtnfemale.setOnClickListener {
            isMale = false
            if(isMale==true){
                rdbtnfemale.isChecked = false
                rdbtnmale.isChecked = true
                gender = "Male"
            }else{
                rdbtnfemale.isChecked = true
                rdbtnmale.isChecked = false
                gender = "Female"
            }
        }

        btnsave.setOnClickListener(){
            insertEmployee()
        }

    }

    private fun updateProgressResult(){
        progressbar.progress = currProgress
        text_progress.text="$currProgress"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.option -> openDialog()

            R.id.view -> viewBook()
        }

        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun openDialog(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.activity_dialog)
        val seekbar = dialog.findViewById(R.id.seekbar) as AppCompatSeekBar
        val txtseek = dialog.findViewById(R.id.txtseek) as TextView

        val bm = applicationContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

        val batLevel : Int = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

        text_progress.text = batLevel.toString()
        progressbar.progress = batLevel
        seekbar.progress = batLevel

        val width = seekbar.getWidth() - seekbar.getPaddingLeft() - seekbar.getPaddingRight()
        val thumbPos = seekbar.getPaddingLeft() + width * seekbar.getProgress() / seekbar.getMax()

        txtseek.measure(0, 0)
        val txtW = txtseek.getMeasuredWidth()
        val delta = txtW / 2
        txtseek.setX(seekbar.getX() + thumbPos - delta)
        txtseek.text = batLevel.toString()+"%"

        dialog.show()

    }

    fun clearEditTexts(){
        edtname.setText("")
        edtmobile.setText("")
        edtemail.setText("")
        edtaddress.setText("")
        edtdesignation.setText("")

    }

    fun insertEmployee(){
        try{
            dbHelper.addEmployee(edtname.text.toString(),edtmobile.text.toString(),
                edtemail.text.toString(),edtaddress.text.toString(),edtdesignation.text.toString(),gender)
            Toast.makeText(this,"Employee data added to book.",Toast.LENGTH_SHORT).show()
        }catch (e:Exception){
            e.printStackTrace()
            Toast.makeText(this,e.message.toString(),Toast.LENGTH_SHORT).show()
        }

        clearEditTexts()
    }

    fun viewData(){
        val res = dbHelper.allData
        if(res.count > 0){
            val buffer = StringBuffer()
            while (res.moveToNext()){
                buffer.append("ID :"+res.getString(0)+"\n")
                buffer.append("Name :"+res.getString(1)+"\n")
                buffer.append("Mobile :"+res.getString(2)+"\n")
                buffer.append("Email :"+res.getString(3)+"\n")
                buffer.append("Address :"+res.getString(4)+"\n")
                buffer.append("Designation :"+res.getString(5)+"\n")
            }

            showDialog(buffer.toString(),"Employee Details")

        }else{
            showDialog("No employee in table","Employee Details")
        }
    }

    fun showDialog(message:String, title:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }

    fun viewBook(){
        val intent = Intent(this,EmployeeListActivity::class.java)
        startActivity(intent)
    }

}
