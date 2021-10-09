package activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WorkActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, View.OnKeyListener {
    private EditText editTextSetDate;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat simpleTimeFormat;
    private SimpleDateFormat simpleDateAndTimeFormat;
    private Spinner spinnerWorkShift;
    private EditText editTextBeginWork;
    private EditText editTextFinishWork;
    private EditText editTextBreakTime;
    private String workShift;
    private EditText editTextRatePerHour;
    private TextView textViewTotalHour;
    private TextView textViewTotalMoney;
    private String[] totalDataValues;
    private Button buttonSaveValue;
    private Button buttonChangeValue;
    private Button buttonDeleteValue;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_activity);



        calendar=Calendar.getInstance();

        simpleDateFormat= new SimpleDateFormat("dd MMMM yyyy");
        simpleTimeFormat = new SimpleDateFormat("HH:mm");
        simpleDateAndTimeFormat= new SimpleDateFormat("dd MMMM yyyy HH:mm");


        editTextSetDate=findViewById(R.id.editTextSetDate);
        editTextSetDate.setOnTouchListener(this);
        editTextBeginWork=findViewById(R.id.editTextBeginWork);
        editTextBeginWork.setOnTouchListener(this);
        editTextFinishWork=findViewById(R.id.editTextFinishWork);
        editTextFinishWork.setOnTouchListener(this);
        editTextBreakTime=findViewById(R.id.editTextBreakTime);
        editTextBreakTime.setOnTouchListener(this);
        editTextRatePerHour=findViewById(R.id.editTextRatePerHour);
        editTextRatePerHour.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editTextRatePerHour.setText("0.00");
        editTextRatePerHour.setOnKeyListener(this);

        String currentTimeAndDate=simpleDateAndTimeFormat.format(calendar.getTime());
        String currentTime=simpleTimeFormat.format(calendar.getTime());
        String currentDate=simpleDateFormat.format(calendar.getTime());
        totalDataValues=new String[]{currentTimeAndDate,currentTimeAndDate,"0:00"};

        editTextSetDate.setText(currentDate);
        editTextBeginWork.setText(currentTime);
        editTextFinishWork.setText(currentTime);
        editTextBreakTime.setText("0:00");

        textViewTotalHour=findViewById(R.id.textViewTotalHour);
        textViewTotalHour.setText("0:00");
        textViewTotalMoney=findViewById(R.id.textViewTotalMoney);
        textViewTotalMoney.setText("0.00");



        buttonSaveValue=findViewById(R.id.buttonSaveValue);
        buttonSaveValue.setOnClickListener(this);
        buttonChangeValue=findViewById(R.id.buttonChangeValue);
        buttonChangeValue.setOnClickListener(this);
        buttonDeleteValue=findViewById(R.id.buttonDeleteValue);
        buttonDeleteValue.setOnClickListener(this);


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

    private void setMoneyAndHourResult(TextView totalHour,TextView totalMoney, EditText editTextBreakTime, Calendar calendar, String[] totalDataValues, SimpleDateFormat[] formats){
            if(!totalDataValues[2].equals("0:00")) {
                try {
                    Date dateBeginWork = formats[0].parse(totalDataValues[0]);
                    Date dateFinishWork = formats[0].parse(totalDataValues[1]);
                    Date breakTime = formats[0].parse(totalDataValues[2]);


                    calendar.setTime(breakTime);

                    int breakHours=calendar.get(Calendar.HOUR_OF_DAY);
                    int breakMinute=calendar.get(Calendar.MINUTE);
                    int breakAllMinutes=breakHours*60+breakMinute;


                    calendar.setTime(dateFinishWork);
                    calendar.add(Calendar.MINUTE, -(dateBeginWork.getMinutes()));
                    calendar.add(Calendar.HOUR_OF_DAY, -(dateBeginWork.getHours()));

                    int totalMinutes=calendar.get(Calendar.HOUR_OF_DAY)*60+calendar.get(Calendar.MINUTE);

                    if( totalMinutes<=breakAllMinutes){
                        editTextBreakTime.setText(("0:00"));
                        Toast.makeText(this, "Время перерыва не может быть больше или равно рабочему времени", Toast.LENGTH_SHORT).show();
                        totalDataValues[2]="0:00";
                    }
                    else {
                        calendar.add(Calendar.MINUTE, -breakMinute);
                        calendar.add(Calendar.HOUR_OF_DAY, -breakHours);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Date dateBeginWork = formats[0].parse(totalDataValues[0]);
                    Date dateFinishWork = formats[0].parse(totalDataValues[1]);
                    calendar.setTime(dateFinishWork);
                    calendar.add(Calendar.MINUTE, -(dateBeginWork.getMinutes()));
                    calendar.add(Calendar.HOUR_OF_DAY, -(dateBeginWork.getHours()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            String allTime=formats[1].format(calendar.getTime());
            totalHour.setText(allTime);
            String stringRatePerHour=editTextRatePerHour.getText().toString();
            if (stringRatePerHour.isEmpty()){
                editTextRatePerHour.setError("Ставка не может быть пустой строкой");
                return;
            }
            double money=(calendar.get(Calendar.HOUR_OF_DAY)*60+calendar.get(Calendar.MINUTE))*Float.valueOf(stringRatePerHour)/60;
            String moneyString= String.format("%.2f",money);
            totalMoney.setText(moneyString);
            calendar.setTimeInMillis(System.currentTimeMillis());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSaveValue:
                setAlertDialog(buttonSaveValue).show();
                break;
            case R.id.buttonChangeValue:
                setAlertDialog(buttonChangeValue).show();
                break;
            case R.id.buttonDeleteValue:
                setAlertDialog(buttonDeleteValue).show();
                break;
        }

    }
    private AlertDialog setAlertDialog(Button button){
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkActivity.this);
        DialogInterface.OnClickListener listener= new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==Dialog.BUTTON_POSITIVE){
                    Toast.makeText(WorkActivity.this, "Не хуйня", Toast.LENGTH_SHORT).show();
                }
                if (which==Dialog.BUTTON_NEGATIVE){
                    dialog.cancel();

                }
            }
        };
        builder.setIcon(R.drawable.hard_worker)
                .setPositiveButton("Да",listener)
                .setNegativeButton("Нет",listener)
                .setCancelable(true);

        if (button.getId()==R.id.buttonSaveValue) builder.setTitle("Сохранить данные за "+editTextSetDate.getText().toString()+" ?");
        if (button.getId()==R.id.buttonChangeValue) builder.setTitle("Изменить данные за "+editTextSetDate.getText().toString()+" ?");
        if (button.getId()==R.id.buttonDeleteValue) {
            builder.setTitle("Внимание!!!");
            builder.setMessage("Все данные "+editTextSetDate.getText().toString()+" будут уничтоженные. Продолжить?");
        }
        return builder.create();

    }

    private DatePickerDialog setDateDialog(Context context, Calendar calendar,SimpleDateFormat simpleDateFormat,EditText editText){
        DatePickerDialog.OnDateSetListener onDateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                  calendar.set(year,month,dayOfMonth);
                  String date = simpleDateFormat.format(calendar.getTime());
                  editText.setText((date));
                  calendar.setTimeInMillis(System.currentTimeMillis());
            }
        };
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_DARK;
        return new DatePickerDialog(context,style,onDateSetListener,year,month,day);

    }

    private TimePickerDialog setTimeDialog(Context context,Calendar calendar,EditText editText,SimpleDateFormat[] formats){
        int hour;
        int minute;
        TimePickerDialog.OnTimeSetListener onTimeSetListener= new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),hourOfDay,minute);
                String time=formats[0].format(calendar.getTime());
                editText.setText(time);

                String fullDataValues=formats[1].format(calendar.getTime());

                if(editText.getId()==R.id.editTextBeginWork) totalDataValues[0]=fullDataValues;
                if(editText.getId()==R.id.editTextFinishWork) totalDataValues[1]=fullDataValues;
                if(editText.getId()==R.id.editTextBreakTime) totalDataValues[2]=fullDataValues;

                setMoneyAndHourResult(textViewTotalHour,textViewTotalMoney,editTextBreakTime,calendar,totalDataValues,new SimpleDateFormat[]{formats[1],formats[0]});

            }
        };
         if (editText.getId()==R.id.editTextBreakTime){
              hour=0;
              minute=00;
         } else {
              hour = calendar.get(Calendar.HOUR_OF_DAY);
              minute = calendar.get(Calendar.MINUTE);
         }
        int style = AlertDialog.THEME_HOLO_DARK;
         return new TimePickerDialog(context,style,onTimeSetListener,hour,minute,true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.editTextSetDate:
                    setDateDialog(WorkActivity.this,calendar,simpleDateFormat,editTextSetDate).show();
                    break;
                case R.id.editTextBeginWork:
                    setTimeDialog(WorkActivity.this,calendar,editTextBeginWork,new SimpleDateFormat[]{simpleTimeFormat,simpleDateAndTimeFormat}).show();
                    break;
                case R.id.editTextFinishWork:
                    setTimeDialog(WorkActivity.this,calendar,editTextFinishWork,new SimpleDateFormat[]{simpleTimeFormat,simpleDateAndTimeFormat}).show();
                    break;
                case R.id.editTextBreakTime:
                    setTimeDialog(WorkActivity.this,calendar,editTextBreakTime,new SimpleDateFormat[]{simpleTimeFormat,simpleDateAndTimeFormat}).show();
                    break;
            }
        }
        return false;

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (v.getId()){
            case R.id.editTextRatePerHour:
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)){
                        setMoneyAndHourResult(textViewTotalHour,textViewTotalMoney,editTextBreakTime,calendar,totalDataValues,new SimpleDateFormat[]{simpleDateAndTimeFormat,simpleTimeFormat});
                }
                break;
        }
        return false;
    }
}
