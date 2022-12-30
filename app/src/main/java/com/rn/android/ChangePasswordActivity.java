package com.rn.android;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.metiz.pelconnect.R;
import com.metiz.pelconnect.databinding.ActivityChangePasswordBinding;
import com.metiz.pelconnect.databinding.DialogShowAlertBinding;

import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.util.MyReceiver;
import com.rn.android.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rn.android.PatientListActivityNew.clickPosition;

public class ChangePasswordActivity extends BaseActivity {

    private ActivityChangePasswordBinding mBinding;
    /* @BindView(R.id.edt_old_password)
     EditText edtOldPassword;
     @BindView(R.id.edt_new_password)
     EditText edtNewPassword;
     @BindView(R.id.edt_confirm_password)
     EditText edtConfirmPassword;
     @BindView(R.id.btn_submit)
     AppCompatButton btnSubmit;
     @BindView(R.id.txt_val_one_letter)
     TextView txtValOneLetter;
     @BindView(R.id.txt_val_two_letter)
     TextView txtValTwoLetter;
     @BindView(R.id.txt_val_three_letter)
     TextView txtValThreeLetter;
     @BindView(R.id.txt_val_four_letter)
     TextView txtValFourLetter;
     @BindView(R.id.checkBox)
     CheckBox checkBox;
     @BindView(R.id.ll_back)
     LinearLayout llBack;
     @BindView(R.id.tv_activity_name)
     TextView tvActivityName;
     @BindView(R.id.toolbar)
     Toolbar toolbar;    */
    int checkValue = 0;
    private Dialog dialogSHowAlert;
    private DialogShowAlertBinding mDialogShowAlertBinding;

    private BroadcastReceiver MyReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(activity, R.layout.activity_change_password);
        MyReceiver = new MyReceiver();

        ButterKnife.bind(this);

        textListners();
        mBinding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkValue = 1;
                } else {
                    checkValue = 0;
                }
            }
        });

        mBinding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void textListners() {
        mBinding.edtNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }

    private void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onResume() {
        super.onResume();
        broadcastIntent();
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=!?*.()_~|/,&amp;\\\"':;])(?=\\S+$).{6,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (isValidate()) {
            if (Utils.checkInternetConnection(getApplicationContext())) {
                changePasswordAPICall();
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isValidate() {
        boolean isValid = true;

        if (mBinding.edtOldPassword.getText().toString().trim().isEmpty()) {
            mBinding.edtOldPassword.setError("Can't be blank");
            mBinding.edtOldPassword.requestFocus();
            return false;
        } else if (mBinding.edtNewPassword.getText().toString().trim().isEmpty()) {
            mBinding.edtNewPassword.setError("Can't be blank");
            mBinding.edtNewPassword.requestFocus();
            return false;
        } else if (mBinding.edtConfirmPassword.getText().toString().trim().isEmpty()) {
            mBinding.edtConfirmPassword.setError("Can't be blank");
            mBinding.edtConfirmPassword.requestFocus();
            return false;
        } else if (!mBinding.edtNewPassword.getText().toString().trim().equalsIgnoreCase(mBinding.edtConfirmPassword.getText().toString().trim())) {
//            Utils.showAlertToast(context, "New password and Confirm password Not Match");
            mBinding.edtConfirmPassword.requestFocus();
            showAlertDialog(context.getResources().getString(R.string.new_pass_and_confim_pass_not_match), "", "", "OK", "3");

            return false;
        } else if (mBinding.edtOldPassword.getText().toString().trim().equalsIgnoreCase(mBinding.edtNewPassword.getText().toString().trim())) {
//            Utils.showAlertToast(context, "Old password and new password are the same.Please enter a different password");
            mBinding.edtConfirmPassword.requestFocus();
            showAlertDialog(context.getResources().getString(R.string.old_pass_and_new_pass_same_enter_diff_pass), "", "", "OK", "3");

            return false;
        }
        if (!isValidPassword(mBinding.edtNewPassword.getText().toString().trim())) {
//            Utils.showAlertToast(context, "New password is not strong enough, Please check all required characters");
            showAlertDialog(context.getResources().getString(R.string.new_pass_not_strong), "", "", "OK", "3");

            return false;
        }
        return isValid;

    }

    private void changePasswordAPICall() {
        JSONObject json = new JSONObject();
        try {
            json.put("UserID", MyApplication.getPrefranceData("UserID"));
            json.put("OldPass", mBinding.edtOldPassword.getText().toString().trim());
            json.put("NewPassword", mBinding.edtNewPassword.getText().toString().trim());
            json.put("IsChangePassword", checkValue);
        } catch (Exception ex) {

        }
        showProgressDialog(this);
        NetworkUtility.makeJSONObjectRequest(API.ResetPassword, json, API.ResetPassword, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();

                try {
                    if (!result.getBoolean("Data")) {
                        Utils.showAlertToast(ChangePasswordActivity.this, "Old password incorrect please try again");
                    } else {

                        MyApplication.clearPrefrences();
                        MyApplication.setPreferencesBoolean("isLogin", false);
                        Toast.makeText(context, "Password has been changed", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = getSharedPreferences("Finger", MODE_PRIVATE).edit();
                        editor.clear().commit();
                        Intent i = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                        clickPosition = 0;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(JSONObject result) {
                dismissProgressDialog();
            }
        });
    }

    private void showAlertDialog(String message, String title, String negativeBtn, String positiveBtn, String check) {

        dialogSHowAlert = new Dialog(context);//, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        mDialogShowAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_show_alert, null, false);
        dialogSHowAlert.setContentView(mDialogShowAlertBinding.getRoot());
//                            dialogDiscontinue.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        dialogSHowAlert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogSHowAlert.getWindow().setStatusBarColor(context.getResources().getColor(R.color.white));
        dialogSHowAlert.setCancelable(true);
        dialogSHowAlert.setCanceledOnTouchOutside(false);

        mDialogShowAlertBinding.tvTitle.setText(title);
        mDialogShowAlertBinding.tvMessage.setText(message);
        mDialogShowAlertBinding.okBtn.setText(positiveBtn);
        mDialogShowAlertBinding.canleBtn.setText(negativeBtn);

        mDialogShowAlertBinding.imgalert.setVisibility(View.GONE);
        mDialogShowAlertBinding.tvTitle.setVisibility(View.GONE);
        mDialogShowAlertBinding.canleBtn.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(title)) {
            mDialogShowAlertBinding.tvTitle.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(negativeBtn)) {
            mDialogShowAlertBinding.canleBtn.setVisibility(View.VISIBLE);
        }
        mDialogShowAlertBinding.canleBtn.setOnClickListener(v -> {
            dialogSHowAlert.dismiss();
            dialogSHowAlert = null;
            if (check.equalsIgnoreCase("1")) {

            } else if (check.equalsIgnoreCase("2")) {

            } else if (check.equalsIgnoreCase("3")) {

            } else if (check.equalsIgnoreCase("4")) {

            } else if (check.equalsIgnoreCase("5")) {

            }
        });
        mDialogShowAlertBinding.okBtn.setOnClickListener(v -> {
            dialogSHowAlert.dismiss();
            dialogSHowAlert = null;
            if (check.equalsIgnoreCase("1")) {
            } else if (check.equalsIgnoreCase("2")) {

            } else if (check.equalsIgnoreCase("3")) {

            } else if (check.equalsIgnoreCase("4")) {

            } else if (check.equalsIgnoreCase("5")) {

            }
        });
        dialogSHowAlert.show();

        /*TextView textView = new TextView(context);
        textView.setText(title);
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setBackgroundResource(R.color.colorPrimary);
        textView.setTextColor(Color.WHITE);

        if (alertDialog != null) {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }
        Log.e("TAG", "showAlertDialog: >>>>>" + check, null);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setMessage(message);
        if (!TextUtils.isEmpty(title)) {
            builder.setCustomTitle(textView);
        }
        builder.setCancelable(false);
        if (!TextUtils.isEmpty(negativeBtn)) {
            builder.setNegativeButton(negativeBtn, (dialog, id) -> {
                dialog.dismiss();
                if (check.equalsIgnoreCase("1")) {
                    finish();
                } else if (check.equalsIgnoreCase("2")) {

                } else if (check.equalsIgnoreCase("3")) {

                }
            });
        }
        builder.setPositiveButton(positiveBtn, (dialog, id) -> {
            dialog.dismiss();
            if (check.equalsIgnoreCase("1")) {
                openAlertDialog2();
            } else if (check.equalsIgnoreCase("2")) {
                getChangePrescriptionByPatientId();
                isDialogShown = true;
//                    isDialogShownMiss = true;
                if (missCount > 0) {
                    openAlertDialog3();
                }
            } else if (check.equalsIgnoreCase("3")) {
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();*/
    }

}
