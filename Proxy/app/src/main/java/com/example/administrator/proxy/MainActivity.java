package com.example.administrator.proxy;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Properties prop = System.getProperties();
        //设置HTTP访问要使用的代理服务器的地址
        prop.setProperty("http.proxyHost", "10.230.60.59");
        //设置HTTP访问要使用的代理服务器的端口
        prop.setProperty("http.proxyPort", "8080");
        //设置HTTP访问不需要通过代理服务器访问的主机，
        //可以使用*通配符，多个地址用|分隔
        prop.setProperty("http.nonProxyHosts", "localhost|10.20.*");
        //设置安全HTTP访问使用的代理服务器地址与端口
        //它没有https.nonProxyHosts属性，它按照http.nonProxyHosts 中设置的规则访问
        prop.setProperty("https.proxyHost", "10.10.0.96");
        prop.setProperty("https.proxyPort", "443");
        prop.put("proxySet", "true");

    }

    public void Enter(View view) throws IOException {
        EditText edt_Url = (EditText) findViewById(R.id.Myet);
        WebView wv = (WebView)findViewById(R.id.Mywv);
        String Url = edt_Url.getText().toString();
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl("http://"+Url);
    }

    public void goBack(View view) {
        WebView wv = (WebView)findViewById(R.id.Mywv);
        if  ( wv.canGoBack()){
            wv.goBack();
        }
    }

    public void goForward(View view) {
        WebView wv = (WebView)findViewById(R.id.Mywv);
        if  ( wv.canGoForward()){
            wv.goForward();
        }
    }

    public void Connect(View view) {
        new Thread(){
            public void run(){
                try{
                    String ip ="10.230.60.59";
                    int port = 8080;
                    Socket mSocket = new Socket(ip,port);
                    //dos = new DataOutputStream(mSocket.getOutputStream());
                    PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())),true);
                    if(mSocket.isConnected()){
                        Toast.makeText(MainActivity.this, "Connect successful", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(MainActivity.this, "Connect Failed", Toast.LENGTH_SHORT).show();
                }catch(NumberFormatException ee){
                    ee.printStackTrace();
                }catch(UnknownHostException ee){
                    ee.printStackTrace();
                }catch(Exception ee){
                    ee.printStackTrace();
                }
            }
        }.start();
    }
}
