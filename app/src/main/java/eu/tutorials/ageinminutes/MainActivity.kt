package eu.tutorials.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.ageinhoursminutes.R
import java.text.SimpleDateFormat
import java.util.*

val A_DAY_IN_MILISECONDS = 86400000

class MainActivity : AppCompatActivity() {
    var btnDatePicker: Button? = null
    var tvSelectedDate: TextView? = null
    var tvSelectedDateInMinutes: TextView? = null
    var tvSelectedDateInHours: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDatePicker = findViewById(R.id.btnDatePicker)
        btnDatePicker?.setOnClickListener { handlingClickDatePicker() }


        //get the id of the textviews from the layout
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvSelectedDateInMinutes = findViewById(R.id.tvSelectedDateInMinutes)
        tvSelectedDateInHours = findViewById(R.id.tvSelectedDateInHours)
    }

    private fun handlingClickDatePicker() {
        /**
        This Gets a calendar using the default time zone and locale.
        The calender returned is based on the current time
        in the default time zone with the default.
         **/
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dPD = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                /**
                 * The listener used to indicate the user has finished selecting a date.
                 */

                /**
                 *Here the selected date is set into format i.e : day/Month/Year
                 * And the month is counted in java is 0 to 11 so we need to add +1 so it can be as selected.
                 * */
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                tvSelectedDate?.text = selectedDate

                /**
                 * Here we have taken an instance of Date Formatter as it will format our
                 * selected date in the format which we pass it as an parameter and Locale.
                 * Here I have passed the format as dd/MM/yyyy.
                 */
                val sDF = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val selectedDateFormat = sDF.parse(selectedDate)
//                Get selected date with format
                selectedDateFormat?.let {
                    /** Here we have get the time in milliSeconds from Date object
                     * And as we know the formula as milliseconds can be converted to second by dividing it by 1000.
                     * And the seconds can be converted to minutes by dividing it by 60.
                     * So now in the selected date into minutes.
                     */
                    val selectedDateInMinutes = selectedDateFormat.time / 60000

//                    Get current date with format
                    val currentDate = sDF.parse(sDF.format(System.currentTimeMillis()))
                    //use the safe call operator with .let to avoid app crashing it currentDate is null
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        val differenceInHours = differenceInMinutes / 60
                        tvSelectedDateInMinutes?.text = "$differenceInMinutes"
                        tvSelectedDateInHours?.text = "$differenceInHours"
                    }
                }
            },
            year,
            month,
            day
        )
        /**
         * Sets the maximal date supported by this in
         * milliseconds since January 1, 1970 00:00:00 in time zone.
         *
         * @param maxDate The maximal supported date.
         */
        dPD.datePicker.maxDate = System.currentTimeMillis() - A_DAY_IN_MILISECONDS
        dPD.show()
    }
}