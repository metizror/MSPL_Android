package com.rn.android.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.metiz.pelconnect.R;
import com.rn.android.BaseActivity;

import com.rn.android.util.TouchImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PDFOrderActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.dialog_image)
    TouchImageView dialogImage;
    ProgressDialog progressDialog;
    String LastThereeCharacter = "";
    String URL = "";
    @BindView(R.id.ll_back)
    LinearLayout llBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_order);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getIntent().getStringExtra("url") != null) {
            URL = getIntent().getStringExtra("url");
            LastThereeCharacter = URL.substring(URL.length() - 3);
        }
        if (LastThereeCharacter.equalsIgnoreCase("pdf")) {
            loadPdfInWebView(URL);

//            progressDialog.show();
//            webView.setVisibility(View.VISIBLE);
//            dialogImage.setVisibility(View.GONE);
//            webView.invalidate();
//            webView.getSettings().setJavaScriptEnabled(true);
//            webView.getSettings().setSupportZoom(true);
//            webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + URL);
//            webView.setWebViewClient(new WebViewClient() {
//                @Override
//                public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                    progressDialog.show();
//                }
//                @Override
//                public void onPageFinished(WebView view, String url) {
//                    super.onPageFinished(view, url);
//                    progressDialog.dismiss();
//                }
//            });
            Log.e("PDF","PDF");


        } else {

            webView.setVisibility(View.GONE);
            dialogImage.setVisibility(View.VISIBLE);
            Glide.with(this).load(URL).placeholder(R.drawable.app_icon).error(R.drawable.app_icon).into(dialogImage);
            Log.e("Image","Image");
        }


    }

//    @Override
//    protected void onPause() {
//        if (LastThereeCharacter.equalsIgnoreCase("pdf")) {
//            webView.clearCache(true);
//            webView.clearHistory();
//            webView.destroy();
//
//        }
//        super.onPause();
//    }
//
//    @Override
//    protected void onStop() {
//        if (LastThereeCharacter.equalsIgnoreCase("pdf")) {
//            webView.clearCache(true);
//            webView.clearHistory();
//            webView.destroy();
//
//        }
//        super.onStop();
//    }
//
//    //
//    @Override
//    protected void onDestroy() {
//        if (LastThereeCharacter.equalsIgnoreCase("pdf")) {
//            webView.clearCache(true);
//            webView.clearHistory();
//            webView.destroy();
//
//        }
//        super.onDestroy();
//        Log.e("Destory Call","Destroy Call");
//
//
//    }

    private void loadPdfInWebView(String pdfUrl) {
        progressDialog.show();
        webView.setVisibility(View.VISIBLE);
        dialogImage.setVisibility(View.GONE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&overridemobile=true&url=" + pdfUrl);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.loadUrl("javascript:(function() { " +
                        "document.querySelector('[role=\"toolbar\"]').remove();})()");
                if (view.getTitle().equals("") || view.getTitle().equals("about:blank"))
                {
                    webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&overridemobile=true&url=" + pdfUrl);
                    return;
                }
               // globalFunctions.hideProgressDialog();
                progressDialog.dismiss();
            }
        });
    }
}
