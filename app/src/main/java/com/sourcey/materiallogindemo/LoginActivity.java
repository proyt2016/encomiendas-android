package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sourcey.materiallogindemo.Shares.DataEmpleado;
import com.sourcey.materiallogindemo.api.EmpleadoApi;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @Bind(R.id.input_user) EditText _userText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        String user = _userText.getText().toString();
        String password = _passwordText.getText().toString();



        JsonObject empleado = new JsonObject();
        empleado.addProperty("usuario",_userText.getText().toString());
        empleado.addProperty("clave",_passwordText.getText().toString());

        Call<DataEmpleado> call = EmpleadoApi.createService().login(AddHeader.clave, empleado);
        call.enqueue(new Callback<DataEmpleado>() {
            @Override
            public void onResponse(Call<DataEmpleado> call, Response<DataEmpleado> response) {
                if(response.isSuccessful()) {

                    DataEmpleado empleado = response.body();

                    if (empleado!=null) {

                        if (empleado.getEmail().getEmail().equals(_userText.getText().toString()) && empleado.getClave().equals(_passwordText.getText().toString())) {
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            // On complete call either onLoginSuccess or onLoginFailed
                                            onLoginSuccess();
                                            progressDialog.dismiss();
                                        }
                                    }, 3000);
                        } else {
                                if(empleado.getEmail().getEmail() != _userText.getText().toString()){
                                    System.out.println("Usuario ingresado incorrecto");
                                    Toast.makeText(LoginActivity.this,"Usuario ingresado incorrecto",Toast.LENGTH_LONG).show();
                                    _loginButton.setEnabled(true);
                                    progressDialog.dismiss();
                                }else {
                                       System.out.println("Contrasenia ingresada incorrecta");
                                       Toast.makeText(LoginActivity.this,"Contrasenia ingresada incorrecta",Toast.LENGTH_LONG).show();
                                        _loginButton.setEnabled(true);
                                   }
                    }
                    } else {
                      //  onLoginFailed();
                        System.out.println("No existe Usuario Registrado");
                        Toast.makeText(LoginActivity.this,"Usuario y/o Contraseña Incorrectos",Toast.LENGTH_LONG).show();
                        _loginButton.setEnabled(true);
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataEmpleado> call, Throwable t) {
                System.out.println("Fallo el Servicio, contactar LacBus");
                onLoginFailed();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Fallo el Servicio, contactar LacBus", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _userText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            _userText.setError("ingrese su usuario");
            valid = false;
        } else {
            _userText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("ingrese su clave");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
