package come.mikedev.agecalculatorx

import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * This is a clone version of Denis Panjuta project which I will push to Github
 * and add binding and other features to it
 * 2021-11-7
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        datePickerBtn.setOnClickListener { view: View ->
            clickDatePicker(view)
//            Toast.makeText(this@MainActivity, "Button works", Toast.LENGTH_LONG).show()
        }
    }

    private fun clickDatePicker(view: View) {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        Log.d("CALENDAR", "$year | $month | $day" )

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->
//                Toast.makeText(this@MainActivity, "Date picker works!", Toast.LENGTH_LONG).show()

                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                // selectedMonth + 1 bcuz month starts from 0
                selectedDateTv.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)  // check java Date()

                // divide by 1000 to get seconds - divide by 60000 to get minutes
                val selectedDateInMinutes = theDate!!.time / 60000   // except null !!

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                val currDateInMinutes = currentDate!!.time / 60000
                val diffInMinutes = currDateInMinutes - selectedDateInMinutes
                selectedDateInMinutesTv.text = diffInMinutes.toString()

            }, year, month, day
        )
        // Prevent user to select future dates (bigger than today)
        dpd.datePicker.maxDate = Date().time - 86400000    // 86400000 means one day
        dpd.show()
    }
}