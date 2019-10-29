package com.example.finalyearproject.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalyearproject.MainActivity;
import com.example.finalyearproject.R;
import com.example.finalyearproject.Session;
import com.example.finalyearproject.ui.login.LoginViewModel;
import com.example.finalyearproject.ui.login.LoginViewModelFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private EditText name,password;
    private Button loginButton;
    private ProgressBar loading;
    Session session;
    private static String URL_REGIST;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //　獲取ViewModel
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);



        session=new Session(this);
        loading=findViewById(R.id.loading);
        name=findViewById(R.id.username);
        password=findViewById(R.id.password);
        loginButton=findViewById(R.id.login);

        HashMap<String,String> user=session.getUserDetail();
        String url=user.get(session.URL);

        URL_REGIST=url+"login.php";


        // 觀察表單數據的變化
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    name.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    password.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });
        // EditText輸入框的變化
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }
            @Override
            public void afterTextChanged(Editable s) {
                // 設置表單數據的變化，通知觀察者
                loginViewModel.loginDataChanged(name.getText().toString(),
                        password.getText().toString());
            }
        };
        name.addTextChangedListener(afterTextChangedListener);// 註冊文本變化監聽器
        password.addTextChangedListener(afterTextChangedListener);// 註冊文本變化監聽器
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {// 軟鍵盤Done事件
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(name.getText().toString(),
                            password.getText().toString());
                }
                return false;
            }
        });





        // 登錄按鈕事件
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });

    }

    private void login(){

        loading.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);

        final String name = this.name.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1")){

                        session.create(name);

                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                        //intent.putExtra("name",name);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "login success！",
                                Toast.LENGTH_SHORT).show();
                    }

                    else if (success.equals("2")){
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this,LoginActivity.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "password error！",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if (success.equals("3")){

                        session.create(name);

                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this,MainActivity.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                        //intent.putExtra("name",name);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "register success！",
                                Toast.LENGTH_SHORT).show();
                    }


                }
                catch (JSONException e){e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "register errorrrr！" + e.toString(),
                            Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    loginButton.setVisibility(View.VISIBLE);}

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "register error！" + error.toString(),
                                Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        loginButton.setVisibility(View.VISIBLE);

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("password",password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




}