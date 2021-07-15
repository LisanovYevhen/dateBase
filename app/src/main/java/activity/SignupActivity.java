package activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.database.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    public static String LOG="SignupActivity";
    private Button connect,register;
    private TextView textViewForgotPassword;
    private EditText edEmail,edPassword;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        connect=findViewById(R.id.buttonConnect);
        connect.setOnClickListener(this);

        register=findViewById(R.id.buttonRegister);
        register.setOnClickListener(this);

        textViewForgotPassword=findViewById(R.id.textViewForgotPassword);
        textViewForgotPassword.setOnClickListener(this);

        firebaseAuth=FirebaseAuth.getInstance();

        edEmail =findViewById(R.id.editTextEmail);
        edPassword=findViewById(R.id.editTextPassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRegister: {
                finish();
                startActivity(new Intent(SignupActivity.this, RegisterActivity.class));
            }
            break;
            case R.id.buttonConnect:{
                String email = edEmail.getText().toString();
                final String password = edPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Введите ва email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Введите пароль", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete( Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    if(password.length()<6){
                                        edPassword.setError("Длина пароля должна быть не меньше 6 символов");
                                    } else {
                                        Toast.makeText(SignupActivity.this, "Ошибка входа проверьте ваш email или пароль", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    finish();
                                    Intent intent = new Intent(SignupActivity.this, WorkActivity.class);
                                    startActivity(intent);

                                }

                            }
                        });


            }
            break;
            case R.id.textViewForgotPassword:{
                startActivity(new Intent(SignupActivity.this,ResetPasswordActivity.class));

            }
            break;


        }
    }

}