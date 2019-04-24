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

public class RestSignUpActivity extends AppCompatActivity
{
    private static final String TAG = "RestSignupActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText _nameText;
    EditText _emailText;
    EditText _usernameText;
    EditText _passwordText;
    EditText _phoneText;
    EditText _addressText;
    EditText _hoursText;
    Button _signupButton;
    TextView _loginLink;

    private mUserDataManager mUserDataManager;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_signup);

        _nameText = findViewById(R.id.input_name);
        _emailText = findViewById(R.id.input_email);
        _usernameText = findViewById(R.id.input_username);
        _passwordText = findViewById(R.id.input_password);
        _phoneText = findViewById(R.id.input_phone);
        _addressText = findViewById(R.id.input_address);
        _hoursText = findViewById(R.id.input_hours);
        Button _signupButton = findViewById(R.id.btn_signup);
        TextView _loginLink = findViewById(R.id.link_login);

        if (mUserDataManager == null) {
            mUserDataManager = new mUserDataManager(this);
            mUserDataManager.openDataBase();  //create local database
        }

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
/**        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(
                RestSignUpActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);   **/

        if ( isUserNameAndPwdValid() ) {
            String name = _nameText.getText().toString().trim();
            String email = _emailText.getText().toString().trim();
            String username = _usernameText.getText().toString().trim();
            String password = _passwordText.getText().toString().trim();
            String phone = _phoneText.getText().toString().trim();
            //Check if the test_user exists
            int count = mUserDataManager.findUserByName(name);

            //when test_user already exists, giving the prompt
            if (count > 0) {
                Toast.makeText(this, getString(R.string.name_already_exist, username), Toast.LENGTH_SHORT).show();
                return;
            }

            mUserData mUser = new mUserData(username, password);
            mUserDataManager.openDataBase();
            //create new test_user information
            long flag = mUserDataManager.insertUserData(mUser);
            if (flag == -1) {
                Toast.makeText(this, getString(R.string.register_fail), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                Intent intent_Register_to_Login = new Intent(RestSignUpActivity.this, MainActivity.class);    //切换User Activity至Login Activity
                startActivity(intent_Register_to_Login);
                finish();
            }
        }

    }

    public boolean isUserNameAndPwdValid() {
        if (_nameText.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.name_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (_passwordText.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if(_emailText.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_check_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if(_usernameText.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.username_empty),
                    Toast.LENGTH_SHORT).show();
        } else if (_phoneText.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.phone_empty),
                    Toast.LENGTH_SHORT).show();

        }
        return true;
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
        String address = _addressText.getText().toString();
        String hours = _hoursText.getText().toString();

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

        //Address
        if (address.isEmpty())
        {
            _addressText.setError("enter a valid address");
            valid = false;
        } else {
            _addressText.setError(null);
        }

        return valid;
    }
}
