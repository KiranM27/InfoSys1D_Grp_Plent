package com.example.plent.myActivities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plent.R;
import com.example.plent.models.ApiModel;
import com.example.plent.models.Event;
import com.example.plent.models.User;
import com.example.plent.utils.Api;
import com.example.plent.utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.*;

import static com.example.plent.utils.Constants.SHARED_PREF_FILE;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SIGN UP";
    public static int FIELDS = 4;

    private SharedPreferences mPreferences;

    ApiModel api;
    User user;

    int[] fieldIds;
    EditText[] inputFields = new EditText[FIELDS];
    CheckBox pdpa;
    Button submit;

    CharSequence name;
    CharSequence email;
    CharSequence studentId;
    CharSequence password;

    boolean completed = false;
    boolean disabled = true;


    private static boolean validateEmail(String emailStr) {
        Pattern generic_email =
                Pattern.compile("^[0-9?A-z0-9?]+(\\.)?[0-9?A-z0-9?]+@[A-z]+\\.[A-z]{3}.?[A-z]{0,3}$", Pattern.CASE_INSENSITIVE);
        Pattern sutd_email = Pattern.compile("^[0-9?A-z0-9?]+(\\.)?[0-9?A-z0-9?]+@[[A-z]*]{0,8}\\.sutd\\.edu\\.sg$", Pattern.CASE_INSENSITIVE);
        Matcher m_generic = generic_email.matcher(emailStr);
        Matcher m_sutd = sutd_email.matcher(emailStr);
        return(m_sutd.find() || m_generic.find());
    }

    private boolean validatePassword(String passStr) {
        Pattern pass_wo_spl = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$");
        Pattern pass_w_spl = Pattern.compile("(?=^.{6,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$");

        Matcher m_wo_spl = pass_wo_spl.matcher(passStr);
        Matcher m_w_spl = pass_w_spl.matcher(passStr);
        if (m_wo_spl.matches() || m_w_spl.matches() && passStr.length() > 6) {
            return(true);
        }
        else {
            return(false);
        }

    }

    private boolean validateStudentId(String studentIdStr) {
        String regex = "^(100\\d\\d\\d\\d)$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(studentIdStr);
        if (m.matches()) {
            return(true);
        }
        else {
            return(false);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkCompleted() {
        Log.i(TAG, "telegramId is " + password + ", " + disabled);
        completed = pdpa.isChecked()
                && name != null && !name.toString().isEmpty()
                && password != null && !password.toString().isEmpty()
                && email != null && !email.toString().isEmpty()
                && studentId != null && !studentId.toString().isEmpty();
        if (disabled && completed) {
            disabled = false;
            submit.setTextAppearance(R.style.Primary_Button);
            submit.setBackgroundResource(R.drawable.primary_button);
        } else if (!disabled && !completed){
            disabled = true;
            submit.setTextAppearance(R.style.Disabled_Button);
            submit.setBackgroundResource(R.drawable.disabled_button);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Log.d(TAG, "Activity Created");
        api = Api.getInstance().apiModel;
        mPreferences = getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        fieldIds = new int[]{R.id.nameInput, R.id.emailInput, R.id.idInput, R.id.passwordInput};

        for (int i=0; i<FIELDS; i++) {
            inputFields[i] = findViewById(fieldIds[i]);
            inputFields[i].setWidth((int)(width*0.9));
            inputFields[i].setPadding((int)(width*0.05), 0,0,0);
        }

        pdpa = findViewById(R.id.pdpa);
        submit = findViewById(R.id.submit);
        submit.setTextAppearance(R.style.Disabled_Button);
        submit.setBackgroundResource(R.drawable.disabled_button);


        inputFields[0].addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = s;
                checkCompleted();
            }
        });

        inputFields[1].addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email = s;
                checkCompleted();
            }
        });
        inputFields[2].addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                studentId = s;
                checkCompleted();
            }
        });
        inputFields[3].addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = s;
                checkCompleted();
            }
        });

        pdpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCompleted();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!disabled) {
                    if (validateEmail(email.toString())) {
                        if (validatePassword(password.toString())) {
                            if (validateStudentId(studentId.toString()))
                                if (Constants.SKIP_BACKEND) {
                                    user = new User(name.toString(), email.toString(), studentId.toString(), "");
                                    user.setId("dummy_id");
                                    user.setPermission(0);
                                    onSubmitSuccess();
                                } else {
                                    createUser();
                                }
                            else {
                                Toast.makeText(SignUpActivity.this, "Oops, this is not a valid student id",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, "Oops, your password doesn't meet the requirements",
                                    Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(SignUpActivity.this, "Oops, that is not a valid email", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Oops, the form is not completed yet!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void createUser() {
        user = new User(name.toString(), email.toString(), studentId.toString(), password.toString());
        Call<User> call = api.createUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
//                Log.d(TAG, "On Response Called" );
                if (!response.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "An error1 occurred, please try again!", Toast.LENGTH_LONG).show();
                } else {
                    if (response.body() != null) {
                        user.removePassword();
                        user.setId(response.body().getId());
                        user.setPermission(response.body().getPermission());
                        user.setOrganisedEvents(new ArrayList<String>());
                        onSubmitSuccess();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Oops, this student id has already been used. Log in instead!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                t.printStackTrace();

                Toast.makeText(SignUpActivity.this, "An error2 occurred, please try again!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onSubmitSuccess() {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        Gson gson = new Gson();
        preferencesEditor.putString(Constants.USER_KEY, gson.toJson(user));
        preferencesEditor.apply();

        Intent intent = new Intent(SignUpActivity.this, FindEventsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }


}
