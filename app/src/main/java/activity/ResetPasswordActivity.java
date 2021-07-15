package activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.database.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonBack,buttonResetPassword;
    private EditText etEmail;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_activity);
        firebaseAuth=FirebaseAuth.getInstance();
        buttonResetPassword=findViewById(R.id.buttonResetPassword);
        buttonBack=findViewById(R.id.buttonCancelled);
        buttonBack.setOnClickListener(this);
        buttonResetPassword.setOnClickListener(this);
        etEmail=findViewById(R.id.editTextEmail);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonCancelled:
                finish();
                break;
            case R.id.buttonResetPassword:{
                String email =etEmail.getText().toString();
                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete( Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ResetPasswordActivity.this, "Мы отправили вам инструкцию для востановления пароля", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ResetPasswordActivity.this, "Не удалось отправить электронное письмо для сброса ", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


            }
            break;
        }

    }
}
