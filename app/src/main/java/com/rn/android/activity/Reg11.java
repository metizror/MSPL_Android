package com.rn.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.metiz.pelconnect.R;
import com.rn.android.network.VolleyMultipartRequest;
import com.rn.android.network.VolleySingleton;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Reg11 extends AppCompatActivity  /* implements IPickResult*/ {
    Button button,openImage;
    ImageView img;
    Bitmap bitmap =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg11);
        button = (Button)findViewById(R.id.button);
        openImage = (Button)findViewById(R.id.openImage);
        img = (ImageView) findViewById(R.id.img);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apicall();
            }


        });

        openImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   PickImageDialog.build(new PickSetup())

                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                //TODO: do what you have to...

                                if (r.getBitmap() != null) {

                                    bitmap = r.getBitmap();
                                    img.setImageBitmap(r.getBitmap());
                                  //  base64 = Utils.convertImageToBase64(r.getBitmap());
                                }
                            }
                        }).show(Reg11.this);*/
            }
        });
    }

/*
    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {

            Bitmap bitmap = r.getBitmap();

            img.setImageBitmap(r.getBitmap());
         //   base64 = Utils.convertImageToBase64(r.getBitmap());


            //Image path
            //r.getPath();
        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }*/

    private  void  apicall(){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, "http://shopifyapp.metizcloud.com/bexplorer/users/register", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                // parse success output

                Log.e("Responce",resultResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", "aa");
                params.put("email", "Anggaaa");
                params.put("password", "12345678");
                params.put("confirm_password", "12345678");
                params.put("phone", "8866389170");
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put("profile", new DataPart("sid.jpg",byteArray, "image/jpeg"));

                return params;
            }
        };

        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }

}
