package com.example.food2go;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DriverSignupActivity extends AppCompatActivity
{
    private static final String TAG = "DriverSignupActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText _nameText;
    EditText _emailText;
    EditText _usernameText;
    EditText _passwordText;
    EditText _phoneText;
    Button _signupButton;
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_signup);

        EditText _nameText = findViewById(R.id.input_name);
        EditText _emailText = findViewById(R.id.input_email);
        EditText _usernameText = findViewById(R.id.input_username);
        EditText _passwordText = findViewById(R.id.input_password);
        EditText _phoneText = findViewById(R.id.input_phone);
        Button _signupButton = findViewById(R.id.btn_signup);
        TextView _loginLink = findViewById(R.id.link_login);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void signup()
    {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(DriverSignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        //-- Signup --
        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        String phone = _phoneText.getText().toString();

        //TODO

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess()
    {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed()
    {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate()
    {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        String phone = _phoneText.getText().toString();

        //Name
        if (name.isEmpty() || name.length() < 3)
        {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        //Email
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        //Username
        if (username.isEmpty())
        {
            _usernameText.setError("enter a valid username");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        //Password
        if (password.isEmpty() || password.length() < 4 || password.length() > 10)
        {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        //Phone Number
        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches())
        {
            _phoneText.setError("enter a valid phone number");
            valid = false;
        } else {
            _phoneText.setError(null);
        }

        return valid;
    }
}
