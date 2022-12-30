package com.rn.android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.metiz.pelconnect.R;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.input_user_name)
    EditText userNameET;
    @BindView(R.id.input_verification_code)
    EditText verificationCodeET;
    @BindView(R.id.llVerification)
    LinearLayout llVerification;
    @BindView(R.id.btn_forgot_password)
    Button btn_forgot_password;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        btn_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llVerification.getVisibility() == View.VISIBLE) {
                    if(!verificationCodeET.getText().toString().isEmpty()){
                        sendPasswordToUser(userNameET.getText().toString(),verificationCodeET.getText().toString());
                    }
                    else {
                        userNameET.setError("Please Enter Verification code");
                    }


                } else {
                    if(!userNameET.getText().toString().isEmpty()){
                        forgotPassword(userNameET.getText().toString());
                    }
                    else {
                        userNameET.setError("Please Enter Username first");
                    }

                }
            }
        });
    }

    public void forgotPassword(String userName){
        progressDialog.show();
        NetworkUtility.makeJSONObjectRequest1(API.Forgotpassword + "?username=" +userName , new JSONObject(), API.Forgotpassword, new VolleyCallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(JSONObject result) {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    if (result != null) {
                        boolean isData=result.getBoolean("Data");
                        if(isData){
                            Log.e("forgotPassword:", "123: "+result.toString() );
                            AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                            builder.setMessage("Your verification code sent on your registered email id.");
                            builder.setCancelable(false);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                            llVerification.setVisibility(View.VISIBLE);
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                            builder.setMessage("user doesn't exists.")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                            dialog.dismiss();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }


                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                    builder.setMessage(ex.getMessage())
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }

            @Override
            public void onError(JSONObject result) {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
               // dismissProgressDialog();
                AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                builder.setMessage("Data not available!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });
    }
    public void sendPasswordToUser(String userName,String verificationCode){
      //  showProgressDialog(this);
        progressDialog.show();
        NetworkUtility.makeJSONObjectRequest1(API.SendRandomPasswordToUser + "?username=" +userName +"&OTPtext="+verificationCode, new JSONObject(), API.SendRandomPasswordToUser, new VolleyCallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(JSONObject result) {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    if (result != null) {
                        Log.e("sendPasswordToUser:", "123: "+result.toString() );
                        boolean isData=result.getBoolean("Data");
                        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                        if(isData){
                            builder.setMessage("New Password sent to your registered email id.")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                            dialog.dismiss();
                                            finish();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                        else{
                            builder.setMessage("Please enter valid OTP.")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                            dialog.dismiss();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                    builder.setMessage(ex.getMessage())
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }

            @Override
            public void onError(JSONObject result) {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                builder.setMessage("Data not available!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });
    }

}
