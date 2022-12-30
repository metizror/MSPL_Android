package com.rn.android.activity;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.metiz.pelconnect.R;
import com.rn.android.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PDFViewActivity extends BaseActivity {
    String pdfUrl;
    @BindView(R.id.webView)
    WebView webView;
    // ProgressDialog progressDialog;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        ButterKnife.bind(this);
        initProgress();

        if (getIntent().getStringExtra("url") != null) {
            pdfUrl = getIntent().getStringExtra("url");
            try {
                //   downloadTask(pdfUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            loadPdfInWebView(pdfUrl);
        }


        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void downloadPDF(String pdfUrl) {
        try {

            String imageurl = pdfUrl;
            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(imageurl));

            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!


            dm.enqueue(request);
            Log.e("TAG", "downloadPDF: " + dm.enqueue(request));
        } catch (Exception e) {
            Log.e("TAG", "downloadPDF: " + e.getMessage());
        }
    }

    private void initProgress() {
/*        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);*/
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("document is loading...")
                .setCancellable(false);
    }


    private void loadPdfInWebView(String pdfUrl) {
        hud.show();
        try {
            webView.getSettings().setJavaScriptEnabled(true);
            if (Build.VERSION.SDK_INT >= 19) {
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else {
                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
            webView.getSettings().setBuiltInZoomControls(true);

            webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&overridemobile=true&url=" + pdfUrl);
            webView.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    webView.loadUrl("javascript:(function() { " +
                            "document.querySelector('[role=\"toolbar\"]').remove();})()");
                    if (view.getTitle().equals("") || view.getTitle().equals("about:blank")) {
                        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&overridemobile=true&url=" + pdfUrl);
                        //             webView.loadUrl( "http:\\/\\/apitaskmanagement.metizcloud.in\\/exportmedsheet\\/637851103792425839.pdf");

                        return;
                    }
                    // globalFunctions.hideProgressDialog();
                    hud.dismiss();
                }
            });
        } catch (Exception e) {
            Log.e("loadPdfInWebView:", "ex: " + e.getMessage());
        }

    }

}
