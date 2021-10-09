package activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.database.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import util.UserInformation;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextPassword,editTextRepeatPassword,editTextEmail;
    private Button buttonRegister;
    private FirebaseAuth firebaseAuth;
    private static final String CHILD_INFO="userInformation";
    private Button buttonCancelled;

    private DatabaseReference reference;
    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        buttonCancelled=findViewById(R.id.buttonCancelled);
        buttonCancelled.setOnClickListener(this);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextRepeatPassword = findViewById(R.id.editTextPasswordRepeat);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);
        firebaseAuth=FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance(getResources().getString(R.string.app_firebase_url)).getReference(getResources().getString(R.string.reference_key));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRegister:{
                String email=editTextEmail.getText().toString();
                String password=editTextPassword.getText().toString();
                String repeatPassword=editTextRepeatPassword.getText().toString();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(this, "Вы не ввели email адрес", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){

                    Toast.makeText(this, "Вы не ввели пароль", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(repeatPassword)){
                    Toast.makeText(this, "Вы не ввели повторно пароль", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (repeatPassword.length()<6||password.length()<6){
                    editTextRepeatPassword.setError("Пароль должен быть больше 6 символов");
                    editTextPassword.setError("Пароль должен быть больше 6 символов");
                    return;
                }

                if (!repeatPassword.equals(password)){

                    Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete( Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Cоздание Userа не удалось, код ошибки"+task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                    else { Toast.makeText(RegisterActivity.this, "Регистрация успешная!!!", Toast.LENGTH_SHORT).show();
                                       String userID= firebaseAuth.getCurrentUser().getUid();

                                       String providerId= firebaseAuth.getCurrentUser().getProviderId();
                                       String phoneNumber= firebaseAuth.getCurrentUser().getPhoneNumber();
                                       String userEmail=firebaseAuth.getCurrentUser().getEmail();

                                       UserInformation userInformation =new UserInformation(userEmail,providerId,phoneNumber);
                                       reference.child(userID).child(CHILD_INFO).setValue(userInformation);
                                       finish();
                                       Intent intent = new Intent(RegisterActivity.this, MainWorkActivity.class);
                                       startActivity(intent);
                                    }

                                }
                            });

                }

            }
                break;
            case R.id.buttonCancelled:finish();
                break;

        }
    }
}
