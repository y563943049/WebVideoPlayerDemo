package com.example.netvideoplayerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class WebActivity extends AppCompatActivity {

    private static final String TAG = "WebActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        final WebView webView = (WebView) findViewById(R.id.web_view);
        final EditText webPath = (EditText) findViewById(R.id.web_path);
        final Button webButton = (Button) findViewById(R.id.web_button);
        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: 点击成功");
                String path = webPath.getText().toString().trim();
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(path);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.select_video:
                Intent select_intent = new Intent(this,VideoActivity.class);
                startActivity(select_intent);
                break;
            case R.id.finish:
                finish();
                break;
            case R.id.web_view:
                Intent web_intent = new Intent(this,WebActivity.class);
                startActivity(web_intent);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
