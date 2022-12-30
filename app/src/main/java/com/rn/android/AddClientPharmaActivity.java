package com.rn.android;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.metiz.pelconnect.R;
import com.rn.android.Adapter.AddClientDocumentAdapter;
import com.rn.android.model.Allergy;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.network.VolleyMultipartRequest;
import com.rn.android.network.VolleySingleton;
import com.rn.android.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rn.android.util.FileUtils.getPath;

public class AddClientPharmaActivity extends BaseActivity {

    private final static int READ_STORAGE_PERMISSION_CODE = 100;
    private static final int MaxDocumentUploadLimit = 5;
    @BindView(R.id.edt_date)
    EditText edtDate;
    @BindView(R.id.edt_first_name)
    EditText edtFirstName;
    @BindView(R.id.edt_last_name)
    EditText edtLastName;
    @BindView(R.id.edt_start_care_date)
    EditText edtStartCareDate;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    @BindView(R.id.rg_gender)
    RadioGroup rgGender;
    @BindView(R.id.edt_client_dob)
    EditText edtClientDob;
    @BindView(R.id.edt_mr)
    EditText edtMr;
    @BindView(R.id.edt_hospice_diagnosis)
    EditText edtHospiceDiagnosis;
    @BindView(R.id.edt_client_address1)
    EditText edtClientAddress1;
    @BindView(R.id.edt_client_address2)
    EditText edtClientAddress2;
    @BindView(R.id.edt_client_city)
    EditText edtClientCity;
    @BindView(R.id.edt_zip)
    EditText edtZip;
    @BindView(R.id.edt_client_phone)
    EditText edtClientPhone;
    @BindView(R.id.edt_md_name)
    EditText edtMdName;
    @BindView(R.id.edt_md_phone)
    EditText edtMdPhone;
    @BindView(R.id.img_promotional_item)
    ImageView imgPromotionalItem;
    @BindView(R.id.selected_photos_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.cb_med_sheet_attached)
    CheckBox cbMedSheetAttached;
    @BindView(R.id.cb_med_sheet_follow)
    CheckBox cbMedSheetFollow;
    @BindView(R.id.btn_dialog_cancel)
    TextView btnDialogCancel;
    @BindView(R.id.btn_dialog_add)
    TextView btnDialogAdd;
    List<String> selectedDocumentListPaths = new ArrayList<>();
    AddClientDocumentAdapter adapter;
    @BindView(R.id.txt_upload_lmt)
    TextView txtUploadLmt;
    @BindView(R.id.multi_auto_allergies)
    MultiAutoCompleteTextView multiAutoAllergies;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.first)
    CardView first;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_activity_name)
    TextView tvActivityName;
    @BindView(R.id.tv_documents)
    TextView tvDocuments;
    private int PICK_IMAGE_MULTIPLE = 101;
    List<Allergy> allergyList = new ArrayList<>();
    ProgressDialog progressDialog;
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_client_new);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        initProgress();
        initView();
        initRecyclerView();
        getAllergiesList();
        txtUploadLmt.setText("Max document upload limit is " + MaxDocumentUploadLimit);
        tvActivityName.setText("ADD NEW CLIENT");
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    private void initProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
    }
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        adapter = new AddClientDocumentAdapter(this, selectedDocumentListPaths);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        edtLastName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_NEXT
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN) {
                    edtClientDob.performClick();
                    return true;
                }

                return false;
            }
        });


        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtDate.setText(sdf.format(new Date()));
        edtStartCareDate.setText(sdf.format(new Date()));

        edtClientDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openDatePicker(AddClientPharmaActivity.this, edtClientDob, new Utils.DatePickerSelected() {
                    @Override
                    public void selectedDate(String date) {
                        edtClientDob.setText(date);
                        edtMr.requestFocus();
                    }
                });

            }
        });
        edtStartCareDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openDatePicker(AddClientPharmaActivity.this, edtStartCareDate, new Utils.DatePickerSelected() {
                    @Override
                    public void selectedDate(String date) {
                        edtStartCareDate.setText(date);
                        edtMr.requestFocus();
                    }
                });

            }
        });
    }

    @OnClick({R.id.img_promotional_item, R.id.btn_dialog_cancel, R.id.btn_dialog_add,R.id.tv_documents})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_promotional_item:

                pickImage();
                break;
            case R.id.tv_documents:
                pickImage();
                break;
            case R.id.btn_dialog_cancel:
                finish();
                break;
            case R.id.btn_dialog_add:
                if (isValid()) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 4000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    submitClientAPIcall();

                }


                break;
        }
    }

    private void pickImage() {


        if (!selectedDocumentListPaths.isEmpty() && selectedDocumentListPaths.size() >= MaxDocumentUploadLimit) {
            Utils.showAlertToast(AddClientPharmaActivity.this, "Max document upload limit is " + MaxDocumentUploadLimit);
            return;
        }

        if (ContextCompat.checkSelfPermission(AddClientPharmaActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermission(AddClientPharmaActivity.this, READ_STORAGE_PERMISSION_CODE);
            return;
        }

    /*    if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(
                    Intent.createChooser(intent, "Select Picture")
                    , PICK_IMAGE_MULTIPLE
            );
        } else {

            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_MULTIPLE);
        }*/

        openSelectMediaDialog();
    }

    private void openSelectMediaDialog() {
        final Dialog dialog1 = new Dialog(this);
        View view = getLayoutInflater().inflate(R.layout.row_image_pic_dialog, null);
        dialog1.setContentView(view);
        dialog1.setCancelable(true);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog1.show();

        ImageView imgImage = dialog1.findViewById(R.id.imgImage);
        ImageView imgPdf = dialog1.findViewById(R.id.imgPdf);
        TextView txtName = dialog1.findViewById(R.id.txtUploadName);
        txtName.setText("Select Document");

        imgImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog1.dismiss();
                dialogForImage("image");

//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(
//                        Intent.createChooser(intent, "Select Picture")
//                        , PICK_IMAGE_MULTIPLE
//                );

            }
        });

        imgPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                dialogForImage("pdf");

//                Intent intent = new Intent();
//                intent.setType("application/pdf");
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(
//                        Intent.createChooser(intent, "Select PDF")
//                        , PICK_IMAGE_MULTIPLE
//                );

//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("application/pdf");
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
            }
        });
    }

    private void dialogForImage(String type) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        if (type == "image") {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
    
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(
                                    Intent.createChooser(intent, "Select Picture")
                                    , PICK_IMAGE_MULTIPLE
                            );
                        } else if ((type == "pdf")) {

                            Intent intent = new Intent();
                            intent.setType("application/pdf");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(
                                    Intent.createChooser(intent, "Select PDF")
                                    , PICK_IMAGE_MULTIPLE
                            );
                        }


                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("I acknowledged that I fully HIPAA compliant to upload picture here.")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }


    private void requestPermission(AppCompatActivity activity, int code) {

        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, code);

    }

    private void submitClientAPIcall() {
        progressDialog.show();

        String clientName = edtFirstName.getText().toString() + edtLastName.getText().toString();
        JSONObject param = new JSONObject();
        try {
            param.put("FacilityID", MyApplication.getPrefranceDataInt("ExFacilityID"));
            param.put("ClientName", clientName);
            param.put("StartOfCare", edtStartCareDate.getText().toString());
            param.put("ClientBOD", edtClientDob.getText().toString());
            param.put("Gender", rbMale.isChecked() ? "M" : "F");
            param.put("MR", edtMr.getText().toString());
            if (multiAutoAllergies.getText().toString().trim().endsWith(",")) {
                param.put("Allergies", removeLastChar(multiAutoAllergies.getText().toString()));
            } else {
                param.put("Allergies", multiAutoAllergies.getText().toString());
            }
            param.put("HospiceDiagnosis", edtHospiceDiagnosis.getText().toString());
            param.put("Address", edtClientAddress1.getText().toString());
            param.put("Address1", edtClientAddress2.getText().toString());
            param.put("City", edtClientCity.getText().toString());
            param.put("Zip", edtZip.getText().toString());
            param.put("ClientPhone", edtClientPhone.getText().toString());
            param.put("MDName", edtMdName.getText().toString());
            param.put("MDPhone", edtMdPhone.getText().toString());
            param.put("CreatedBy", MyApplication.getPrefranceData("UserID"));

            //  param.put("MeedsheetAtteched","yes");
            //  param.put("MetsheetTofollow", "yes");
            param.put("CreatedOn", edtDate.getText().toString());


        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        NetworkUtility.makeJSONObjectRequest(API.AddPharmaClient, param, API.AddPharmaClient, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {


                    if (selectedDocumentListPaths.size() > 0) {
                        for (int i = 0; i < selectedDocumentListPaths.size(); i++) {

                            uploadDocuments(result.get("Data").toString(), selectedDocumentListPaths.get(i));

                            Log.e("Loop",String.valueOf(i));
                        }
                    } else {
                        progressDialog.dismiss();
                        Utils.showAlertToast(AddClientPharmaActivity.this, result.getString("Message"));
                      //  sendNotification("New Pharmacy client", "New pharmacy client added.");
                        finish();
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject result) {
                progressDialog.dismiss();
            }
        });

    }
    private boolean isValid() {

        if (edtDate.getText().toString().trim().isEmpty()) {
            edtDate.requestFocus();
            Utils.showAlertToast(AddClientPharmaActivity.this, "Enter Date");
            return false;
        } else if (edtFirstName.getText().toString().trim().isEmpty()) {
            edtFirstName.requestFocus();
            Utils.showAlertToast(AddClientPharmaActivity.this, "Enter First Name");
            return false;
        } else if (edtLastName.getText().toString().trim().isEmpty()) {
            edtLastName.requestFocus();
            Utils.showAlertToast(AddClientPharmaActivity.this, "Enter Last Name");
            return false;
        } else if (edtStartCareDate.getText().toString().trim().isEmpty()) {
            edtStartCareDate.requestFocus();
            Utils.showAlertToast(AddClientPharmaActivity.this, "Enter Start Care Date");

            return false;
        } else if (edtClientDob.getText().toString().trim().isEmpty()) {
            edtClientDob.requestFocus();
            Utils.showAlertToast(AddClientPharmaActivity.this, "Enter Client DOB");

            return false;

        } else if (multiAutoAllergies.getText().toString().trim().isEmpty()) {
            multiAutoAllergies.requestFocus();
            Utils.showAlertToast(AddClientPharmaActivity.this, "Enter Allergies");

            return false;

        } else if (selectedDocumentListPaths.size() > MaxDocumentUploadLimit) {
            Utils.showAlertToast(AddClientPharmaActivity.this, "Max document upload limit is " + MaxDocumentUploadLimit);
            return false;
        }
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK && null != data) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();

                    for (int i = 0; i < count; i++) {

                        Uri uri = data.getClipData().getItemAt(i).getUri();
                        String selectedFilePath = getPath(AddClientPharmaActivity.this, uri);
                        //Toast.makeText(this, "clip    :    "+data.getClipData(), Toast.LENGTH_SHORT).show();
                        if (new File(selectedFilePath).exists()) {
                            Log.e("Selected Image", "At: === " + i + selectedFilePath);
                            selectedDocumentListPaths.add(selectedFilePath);
                        }
                    }
                    /*for (i in 0..count - 1) {
                        var imageUri: Uri = data.clipData.getItemAt(i).uri
                        getPathFromURI(imageUri)
                    }*/
                } else if (data.getData() != null) {
                    Uri uri = data.getData();
                    //Toast.makeText(this, "data   :   "+data.getData(), Toast.LENGTH_SHORT).show();
                    String imagePath = getPath(AddClientPharmaActivity.this, uri);
                    Log.e("imagePath", imagePath);
                    selectedDocumentListPaths.add(imagePath);
                }
            }

            adapter.updateMe(selectedDocumentListPaths);
            listUpdated(selectedDocumentListPaths);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    imgPromotionalItem.performClick();
                }
                break;
        }
    }


    public void uploadDocuments(String id, String path) {
        if (selectedDocumentListPaths.size() == 0) {
            return;
        }
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, API.uploadClientDocuments, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
//                String resultResponse = new String(response.data);
                try {
                    Log.e("response", "Image upload thai gai bhai");
                    Log.e("responseImage", response.toString());

                    selectedDocumentListPaths.remove(path);
                    if (selectedDocumentListPaths.isEmpty()) {
                        progressDialog.dismiss();
                     //  sendNotification("New Pharmacy client", "New pharmacy client added.");
                        finish();
                    }
                } catch (Exception ex) {
                    progressDialog.dismiss();
                    finish();

                    ex.printStackTrace();
                }
                // parse success output
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                finish();

                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                try {
                    params.put("OrderID", id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();


                params.put("1", new DataPart(new File(path).getName(), Utils.readBytesFromFile(path), "*/*"));

                Log.e("Params",params.toString());

                return params;
            }
        };

        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }

    /**
     * set image count as per the selected media from internal storage
     *
     * @param list
     */
    public void listUpdated(List<String> list) {

        if (list.isEmpty()) {
            txtUploadLmt.setText("Max document upload limit is " + MaxDocumentUploadLimit);

        } else {
            txtUploadLmt.setText(list.size() + "/" + MaxDocumentUploadLimit + " Document selected");

        }
    }
    private void getAllergiesList() {
        progressDialog.show();

        NetworkUtility.makeJSONObjectRequest(API.Allergymaster, new JSONObject(), API.Patient_List_URL, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    allergyList.clear();


                    if (result != null) {
                        Type listType = new TypeToken<List<Allergy>>() {
                        }.getType();
                        allergyList = MyApplication.getGson().fromJson(result.getJSONArray("Data").toString(), listType);
                                progressDialog.dismiss();

                        setAllergies(multiAutoAllergies, allergyList);

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onError(JSONObject result) {
               progressDialog.dismiss();
            }
        });
    }

    public void setAllergies(final MultiAutoCompleteTextView multiAutoCompleteTextView, List<Allergy> list) {

        final ArrayAdapter<Allergy> stateArrayAdapter = new ArrayAdapter<Allergy>
                (AddClientPharmaActivity.this, R.layout.simple_spinner_item,
                        list);

        //selected item will look like a spinner set from XML
        stateArrayAdapter.setDropDownViewResource(R.layout
                .spinner_dropdown_item);

        runOnUiThread(() -> {
            multiAutoCompleteTextView.setThreshold(1);//will start working from first character
            multiAutoCompleteTextView.setAdapter(stateArrayAdapter);//setting the adapter data into the AutoCompleteTextView
            multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        });
    }


    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 2);
    }
}
