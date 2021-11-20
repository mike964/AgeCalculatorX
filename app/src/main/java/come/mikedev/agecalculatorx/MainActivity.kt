package come.mikedev.agecalculatorx

import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import come.mikedev.agecalculatorx.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

/**
 * This is a clone version of Denis Panjuta project which I will push to Github
 * and add binding and other features to it
 * 2021-11-7
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        //        setContentView(R.layout.activity_main)
        setContentView(view)


        binding.datePickerBtn.setOnClickListener { view: View ->
            onDatePickerClick(view)
//            Toast.makeText(this@MainActivity, "Button works", Toast.LENGTH_LONG).show()
        }
    }

    private fun onDatePickerClick(view: View) {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        Log.d("CALENDAR", "$year | $month | $day" )  // output: 2021 | 10 | 20

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->
//                Toast.makeText(this@MainActivity, "Date picker works!", Toast.LENGTH_LONG).show()

                val selectedDateString = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                // selectedMonth + 1 bcuz month starts from 0
                binding.selectedDateTv.text = selectedDateString

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val selectedDate = sdf.parse(selectedDateString)  // check java Date()

                Log.d("TAG-11", "_selectedDate: $selectedDate")
                // output: Mon Nov 01 00:00:00 GMT+03:00 2021
                Log.d("TAG-12", "${selectedDate!!.time}" )
                // output: 1636146000000

                // divide by 1000 to get seconds - divide by 60 to get minutes
                val selectedDateInMinutes = selectedDate!!.time / 60000   // except null !!

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                val currDateInMinutes = currentDate!!.time / 60000
                val diffInMinutes = currDateInMinutes - selectedDateInMinutes
                val ageInHours = diffInMinutes/(60*24)
                val ageInYears = diffInMinutes/(60*24*365)

                binding.ageInHoursTv.text = (diffInMinutes/60).toString()
                binding.ageInDaysTv.text =  ageInHours.toString()
                binding.yourAgeTv.text = "You are $ageInYears years old"

            }, year, month, day
        )
        // Prevent user to select future dates (bigger than today)
        dpd.datePicker.maxDate = Date().time - 86400000    // 86400000 means one day
        dpd.show()
    }
}