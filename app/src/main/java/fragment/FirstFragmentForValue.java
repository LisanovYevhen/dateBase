package fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.database.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import activity.SignupActivity;
import util.UserValue;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragmentForValue#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragmentForValue extends Fragment implements View.OnTouchListener, View.OnKeyListener, View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private static final String CHILD_VALUES="userValue";
    private String mParam1;
    private String mParam2;
    private String workShift;
    private EditText editTextSetDate;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat simpleTimeFormat;
    private SimpleDateFormat simpleDateAndTimeFormat;
    private Spinner spinnerWorkShift;
    private EditText editTextBeginWork;
    private EditText editTextFinishWork;
    private EditText editTextBreakTime;
    private EditText editTextRatePerHour;
    private TextView textViewTotalHour;
    private TextView textViewTotalMoney;
    private String[] totalDataValues;
    private Button buttonSaveValue;
    private Button buttonChangeValue;
    private Button buttonDeleteValue;
    private View root;
    private Activity activity;
    private UserValue value;
    private DatabaseReference reference;
    private FirebaseAuth auth;

    public FirstFragmentForValue() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragmentForValue.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragmentForValue newInstance(String param1, String param2) {
        FirstFragmentForValue fragment = new FirstFragmentForValue();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(SignupActivity.LOG, "Fragment onAttach");
        //Вызывается, когда фрагмент связывается с активностью. С этого момента мы можем получить ссылку на активность через метод
      activity= getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(SignupActivity.LOG, "Fragment onCreate");
        //В этом методе можно сделать работу, не связанную с интерфейсом. Например, подготовить адаптер.
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.work_activity, container, false);

        calendar=Calendar.getInstance();

        simpleDateFormat= new SimpleDateFormat("dd MMMM yyyy");
        simpleTimeFormat = new SimpleDateFormat("HH:mm");
        simpleDateAndTimeFormat= new SimpleDateFormat("dd MMMM yyyy HH:mm");

        editTextSetDate=root.findViewById(R.id.editTextSetDate);
        editTextSetDate.setOnTouchListener(this);
        editTextBeginWork=root.findViewById(R.id.editTextBeginWork);
        editTextBeginWork.setOnTouchListener(this);
        editTextFinishWork=root.findViewById(R.id.editTextFinishWork);
        editTextFinishWork.setOnTouchListener(this);
        editTextBreakTime=root.findViewById(R.id.editTextBreakTime);
        editTextBreakTime.setOnTouchListener(this);
        editTextRatePerHour=root.findViewById(R.id.editTextRatePerHour);
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

        textViewTotalHour=root.findViewById(R.id.textViewTotalHour);
        textViewTotalHour.setText("0:00");
        textViewTotalMoney=root.findViewById(R.id.textViewTotalMoney);
        textViewTotalMoney.setText("0.00");

        buttonSaveValue=root.findViewById(R.id.buttonSaveValue);
        buttonSaveValue.setOnClickListener(this);
        buttonChangeValue=root.findViewById(R.id.buttonChangeValue);
        buttonChangeValue.setOnClickListener(this);
        buttonDeleteValue=root.findViewById(R.id.buttonDeleteValue);
        buttonDeleteValue.setOnClickListener(this);


        spinnerWorkShift=root.findViewById(R.id.spinnerWorkShift);
        setSpinner(spinnerWorkShift);






        //Вызывается для создания компонентов внутри фрагмента
        Log.d(SignupActivity.LOG, "Fragment onCreateView");
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(SignupActivity.LOG, "Fragment onViewCreated");
        //Вызывается сразу после onCreateView()
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(SignupActivity.LOG, "Fragment onActivityCreated");
        // когда создаётся активность-хозяйка для фрагмента. Здесь можно объявить объекты, необходимые для Context.
        // Вызывается, когда отработает метод активности onCreate(), а значит фрагмент может обратиться к компонентам активности

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(SignupActivity.LOG, "Fragment onStart");
        //Вызывается, когда фрагмент видим для пользователя и сразу же срабатывает onStart() активности
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(SignupActivity.LOG, "Fragment onResume");
        //Вызываем после onResume() активности
        //Вызывается, когда набор компонентов удаляется из фрагмента
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(SignupActivity.LOG, "Fragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(SignupActivity.LOG, "Fragment onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(SignupActivity.LOG, "Fragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(SignupActivity.LOG, "Fragment onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(SignupActivity.LOG, "Fragment onDetach");
        //Вызывается, когда фрагмент отвязывается от активности
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
                    Toast.makeText(getActivity(), "Время перерыва не может быть больше или равно рабочему времени", Toast.LENGTH_SHORT).show();
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

    private AlertDialog setAlertDialog(Button button){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(R.drawable.hard_worker)
                .setCancelable(true);;
        DialogInterface.OnClickListener listener=null;
        switch (button.getId()){
            case R.id.buttonSaveValue:{
                builder.setTitle("Сохранить данные за "+editTextSetDate.getText().toString()+" ?");
                listener= new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which== Dialog.BUTTON_POSITIVE){
                            workIntoFireBase(editTextSetDate.getText().toString(),calendar,button,null);
                        }
                        if (which==Dialog.BUTTON_NEGATIVE){
                            dialog.cancel();
                        }
                    }
                };
                builder.setIcon(R.drawable.hard_worker)
                        .setPositiveButton("Да",listener)
                        .setNegativeButton("Нет",listener);
                return builder.create();
            }
            case R.id.buttonChangeValue:{
                builder.setTitle("Изменить данные за "+editTextSetDate.getText().toString()+" ?");
                builder.setMessage("Выберите поля, которые желаете заменить.");
                View view=activity.getLayoutInflater().inflate(R.layout.dialog_change_value,null);
                listener= new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which== Dialog.BUTTON_POSITIVE){
                            CheckBox cbBeginWork=view.findViewById(R.id.checkBoxBeginWork);
                            CheckBox cbFinishWork=view.findViewById(R.id.checkBoxFinishWork);
                            CheckBox cbBreakTime=view.findViewById(R.id.checkBoxBreakTime);
                            CheckBox cbRatePerHour=view.findViewById(R.id.checkBoxRatePerHour);
                            CheckBox cbWorkShift=view.findViewById(R.id.checkBoxWorkShift);
                            HashMap<String,Boolean> checked= new HashMap<>();
                            checked.put("beginOfWork",cbBeginWork.isChecked());
                            checked.put("endOfWork",cbFinishWork.isChecked());
                            checked.put("breakTime",cbBreakTime.isChecked());
                            checked.put("ratePerHour",cbRatePerHour.isChecked());
                            checked.put("workShift",cbWorkShift.isChecked());
                            workIntoFireBase(editTextSetDate.getText().toString(),calendar,button,checked);
                            checked.clear();

                        }
                        if (which==Dialog.BUTTON_NEGATIVE){
                            dialog.cancel();
                        }
                    }
                };
                builder.setIcon(R.drawable.hard_worker)
                        .setPositiveButton("Изменить",listener)
                        .setNegativeButton("Отмена",listener);
                AlertDialog dialog=builder.create();
                dialog.setView(view);
                return dialog;
            }

            default: {
                builder.setTitle("Внимание!!!");
                builder.setMessage("Все данные "+editTextSetDate.getText().toString()+" будут уничтоженные. Продолжить?");
                listener= new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which== Dialog.BUTTON_POSITIVE){
                            workIntoFireBase(editTextSetDate.getText().toString(),calendar,button,null);
                        }
                        if (which==Dialog.BUTTON_NEGATIVE){
                            dialog.cancel();
                        }
                    }
                };
                builder.setIcon(R.drawable.hard_worker)
                        .setPositiveButton("Да",listener)
                        .setNegativeButton("Нет",listener);
                return builder.create();
            }
        }



    }

    private DatePickerDialog setDateDialog(Context context, Calendar calendar, SimpleDateFormat simpleDateFormat, EditText editText){
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

    private TimePickerDialog setTimeDialog(Context context, Calendar calendar, EditText editText, SimpleDateFormat[] formats){
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

    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.editTextSetDate:
                    setDateDialog(activity,calendar,simpleDateFormat,editTextSetDate).show();
                    break;
                case R.id.editTextBeginWork:
                    setTimeDialog(activity,calendar,editTextBeginWork,new SimpleDateFormat[]{simpleTimeFormat,simpleDateAndTimeFormat}).show();
                    break;
                case R.id.editTextFinishWork:
                    setTimeDialog(activity,calendar,editTextFinishWork,new SimpleDateFormat[]{simpleTimeFormat,simpleDateAndTimeFormat}).show();
                    break;
                case R.id.editTextBreakTime:
                    setTimeDialog(activity,calendar,editTextBreakTime,new SimpleDateFormat[]{simpleTimeFormat,simpleDateAndTimeFormat}).show();
                    break;
            }
        }
        return false;

    }

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

    private void  setSpinner(Spinner spinner){
        String data[]={"1-я","2-я","night"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item,data);
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

    private void workIntoFireBase(String date,Calendar calendar, Button button,HashMap<String,Boolean> checked){
        auth=FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance(getResources().getString(R.string.app_firebase_url)).getReference(getResources().getString(R.string.reference_key));
        String uID= auth.getCurrentUser().getUid();
        String year=null;
        String month=null;
        String day=null;
        try {
            calendar.setTime(simpleDateFormat.parse(date));
            year=String.valueOf(calendar.get(Calendar.YEAR));
            switch (calendar.get(Calendar.MONTH)){
                case 0:
                    month="Январь";
                    break;
                case 1:
                    month="Февраль";
                    break;
                case 2:
                    month="Март";
                    break;
                case 3:
                    month="Апрель";
                    break;
                case 4:
                    month="Май";
                    break;
                case 5:
                    month="Июнь";
                    break;
                case 6:
                    month="Июль";
                    break;
                case 7:
                    month="Август";
                    break;
                case 8:
                    month="Сентябрь";
                    break;
                case 9:
                    month="Октябрь";
                    break;
                case 10:
                    month="Ноябрь";
                    break;
                case 11:
                    month="Декабрь";
                    break;
            }
            day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            calendar.setTimeInMillis(System.currentTimeMillis());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String beginWork=editTextBeginWork.getText().toString();
        String finishWork=editTextFinishWork.getText().toString();
        String breakTime=editTextBreakTime.getText().toString();
        String ratePerHour=editTextRatePerHour.getText().toString();
        String hours=textViewTotalHour.getText().toString();
        String money=textViewTotalMoney.getText().toString();

        value= new UserValue(
                beginWork,
                finishWork,
                breakTime,
                workShift,
                ratePerHour,
                hours,
                money
        );

        switch (button.getId()){
            case R.id.buttonSaveValue:{
                String path = new StringBuilder(uID)
                        .append("/")
                        .append(CHILD_VALUES)
                        .append("/")
                        .append(year)
                        .append("/")
                        .append(month)
                        .append("/")
                        .append(day)
                        .toString();
                reference.child(path).setValue(value);
            }
            break;
            case R.id.buttonChangeValue:{
                String path = new StringBuilder(uID)
                        .append("/")
                        .append(CHILD_VALUES)
                        .append("/")
                        .append(year)
                        .append("/")
                        .append(month)
                        .append("/")
                        .append(day)
                        .append("/").toString();
                Set set=checked.entrySet();
                Iterator iterator=set.iterator();
                while (iterator.hasNext()){
                    Map.Entry entry=(Map.Entry) iterator.next();
                    boolean check=(Boolean) entry.getValue();
                    String keyMap=(String) entry.getKey();
                   if(check){
                       switch (keyMap){
                           case "beginOfWork": {
                               reference.child(path + "beginOfWork")
                                       .setValue(ratePerHour);
                               reference.child(path + "totalHour")
                                       .setValue(hours);
                               reference.child(path + "totalMoney")
                                       .setValue(money);
                           }
                               break;
                           case "endOfWork": {
                               reference.child(path + "endOfWork")
                                       .setValue(ratePerHour);
                               reference.child(path + "totalHour")
                                       .setValue(hours);
                               reference.child(path + "totalMoney")
                                       .setValue(money);
                           }
                               break;
                           case "breakTime": {
                               reference.child(path + "breakTime")
                                       .setValue(ratePerHour);
                               reference.child(path + "totalHour")
                                       .setValue(hours);
                               reference.child(path + "totalMoney")
                                       .setValue(money);
                           }
                               break;
                           case "ratePerHour": {
                               reference.child(path + "ratePerHour")
                                       .setValue(ratePerHour);
                               reference.child(path + "totalHour")
                                       .setValue(hours);
                               reference.child(path + "totalMoney")
                                       .setValue(money);
                           }
                               break;
                           case "workShift": {
                               reference.child(path + "workShift")
                                       .setValue(workShift);
                               reference.child(path + "totalHour")
                                       .setValue(hours);
                               reference.child(path + "totalMoney")
                                       .setValue(money);
                           }
                               break;
                       }

                   }

                }

            }
            case R.id.buttonDeleteValue:
                reference.child(uID)
                        .child(CHILD_VALUES)
                        .child(year)
                        .child(month)
                        .child(day)
                        .removeValue();
        }


    }


}