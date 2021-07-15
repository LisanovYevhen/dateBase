package activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.database.R;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WorkActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private EditText editTextSetDate;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat simpleTimeFormat;
    private Spinner spinnerWorkShift;
    private EditText editTextBeginWork;
    private EditText editTextFinishWork;
    private EditText editTextBreakTime;
    private String workShift;
    private EditText editTextTextRatePerHour;
    private TextView textViewTotalHour;
    private TextView textViewTotalMoney;
    private String[] totalDataValues= new String[3];


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_activity);

        calendar=Calendar.getInstance();
        simpleDateFormat= new SimpleDateFormat("dd MMMM yyyy");
        simpleTimeFormat = new SimpleDateFormat("HH:mm");

        editTextSetDate=findViewById(R.id.editTextSetDate);
        editTextSetDate.setOnTouchListener(this);
        editTextBeginWork=findViewById(R.id.editTextBeginWork);
        editTextBeginWork.setOnTouchListener(this);
        editTextFinishWork=findViewById(R.id.editTextFinishWork);
        editTextFinishWork.setOnTouchListener(this);
        editTextBreakTime=findViewById(R.id.editTextBreakTime);
        editTextBreakTime.setOnTouchListener(this);
        editTextTextRatePerHour=findViewById(R.id.editTextTextRatePerHour);
        editTextTextRatePerHour.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

        editTextSetDate.setText(simpleDateFormat.format(calendar.getTime()));
        editTextBeginWork.setText(simpleTimeFormat.format(calendar.getTime()));
        editTextFinishWork.setText(simpleTimeFormat.format(calendar.getTime()));
        editTextBreakTime.setText("0:00");

        textViewTotalHour=findViewById(R.id.textViewTotalHour);
        textViewTotalMoney=findViewById(R.id.textViewTotalMoney);


        spinnerWorkShift= findViewById(R.id.spinnerWorkShift);
        setSpinner(spinnerWorkShift);


    }

    private void  setSpinner(Spinner spinner){
        String data[]={"1-я","2-я","night"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(WorkActivity.this, android.R.layout.simple_spinner_item,data);
        spinner.setAdapter(adapter);
        spinner.setSelection(1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                workShift= String.valueOf(spinner.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setMoneyAndHourResult(TextView totalHour,TextView totalMoney, Calendar calendar){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }

    }

    private DatePickerDialog setDate(Context context, Calendar calendar,SimpleDateFormat simpleDateFormat,EditText editText){
        DatePickerDialog.OnDateSetListener onDateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                  calendar.set(year,month,dayOfMonth);
                  String date = simpleDateFormat.format(calendar.getTime());
                  Toast.makeText(context, "Установлено дата: "+date, Toast.LENGTH_SHORT).show();
                  editText.setText((date));
                  calendar.setTime(new Date());
            }
        };
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_DARK;
        return new DatePickerDialog(context,style,onDateSetListener,year,month,day);

    }

    private TimePickerDialog setTime(Context context,Calendar calendar,SimpleDateFormat simpleDateFormat,EditText editText){
        TimePickerDialog.OnTimeSetListener onTimeSetListener= new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),hourOfDay,minute);
                String time=simpleDateFormat.format(calendar.getTime());
                editText.setText(time);
                calendar.setTimeInMillis(System.currentTimeMillis());
                Log.d(SignupActivity.LOG,simpleDateFormat.format(calendar.getTime()));
            }
        };
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        int style = AlertDialog.THEME_HOLO_DARK;
        return new TimePickerDialog(context,style,onTimeSetListener,hour,minute,true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.editTextSetDate:
                    setDate(WorkActivity.this,calendar,simpleDateFormat,editTextSetDate).show();
                    break;
                case R.id.editTextBeginWork:
                    setTime(WorkActivity.this,calendar,simpleTimeFormat,editTextBeginWork).show();
                    break;
                case R.id.editTextFinishWork:
                    setTime(WorkActivity.this,calendar,simpleTimeFormat,editTextFinishWork).show();
                    break;
                case R.id.editTextBreakTime:
                    setTime(WorkActivity.this,calendar,simpleTimeFormat,editTextBreakTime).show();
                    break;

            }
        }
        return true;

    }
}
