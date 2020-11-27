package com.example.plent.myActivities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plent.R;
import com.example.plent.models.ApiModel;
import com.example.plent.models.User;
import com.example.plent.utils.Constants;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInformationActivity extends AppCompatActivity {
    private boolean disabled = true;
    private boolean changed = false;

    Button edit;
    Button logout;
    EditText nameInput;
    EditText emailInput;
    EditText idInput;
    CharSequence name_CS;
    CharSequence email_CS;
    CharSequence id_CS;

    User user;
    User editedUser;
    private SharedPreferences mPreferences;
    private final String TAG = "MyInformation";
    ApiModel api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.plent.R.layout.activity_my_information);

        edit = findViewById(R.id.edit);
        logout = findViewById(R.id.logout);
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        idInput = findViewById(R.id.idInput);


        Gson gson = new Gson();
        mPreferences = getSharedPreferences(Constants.SHARED_PREF_FILE, MODE_PRIVATE);
        String json = mPreferences.getString(Constants.USER_KEY, null);
        if (json == null) {
            Log.i(TAG, "is null");
            // redirect to login page if no user info stored
            Intent intent = new Intent(MyInformationActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Log.i(TAG, "json");
            user = gson.fromJson(json, User.class);

            idInput.setText(user.getStudentId());
            emailInput.setText(user.getEmail());
            nameInput.setText(user.getName());

            nameInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    name_CS = s;
                    buttonStateChange();
                }
            });

            emailInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    email_CS = s;
                    buttonStateChange();
                }
            });

            idInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    id_CS = s;
                    buttonStateChange();
                }
            });
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                preferencesEditor.remove(Constants.USER_KEY);
                preferencesEditor.apply();

                Intent intent = new Intent(MyInformationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedUser.setName(nameInput.getText().toString());
                editedUser.setId(idInput.getText().toString());
                editedUser.setEmail(emailInput.getText().toString());
                editedUser.setPassword(user.getPassword());
                editedUser.setId(user.getId());
                editedUser.setPermission(user.getPermission());
                editedUser.setEvents(user.getEvents());
                editUser();
            }
        });
    }

    private void editUser() {
        Call<User> call = api.editUser(editedUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MyInformationActivity.this, "An error occurred, please try again!",
                            Toast.LENGTH_LONG).show();

                    nameInput.setHint(user.getName());
                    idInput.setHint(user.getId());
                    emailInput.setHint(user.getEmail());
                } else {
                    Gson gson = new Gson();
                    SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                    // preferencesEditor.remove(Constants.USER_KEY);
                    preferencesEditor.putString(Constants.USER_KEY, gson.toJson(editedUser));
                    preferencesEditor.apply();
                    Toast.makeText(MyInformationActivity.this, "Your details have been updated !",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MyInformationActivity.this, "An error occurred, please try again!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void buttonStateChange() {
        changed = (name_CS != user.getName() && !name_CS.toString().isEmpty()
                && email_CS != user.getEmail() && !email_CS.toString().isEmpty())
                && id_CS != user.getId() && !id_CS.toString().isEmpty();
        if (changed) {
            disabled = false;
            edit.setTextAppearance(R.style.Primary_Button);
            edit.setBackgroundResource(R.drawable.primary_button);
        } else {
            // else if (!completed){
            disabled = true;
            edit.setTextAppearance(R.style.Disabled_Button);
            edit.setBackgroundResource(R.drawable.disabled_button);
        }
    }

}