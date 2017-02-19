package com.lvc.lvcfanyi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lvc.lvcfanyi.util.HttpUtil;
import com.lvc.lvcfanyi.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private EditText editText;
    private Button btn;

    private TextView queryText;
    private TextView transText;
    private TextView explText;
    private TextView webText;

    private Test mtest;

    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
                if (!TextUtils.isEmpty(editText.getText().toString().trim())){
                    String query =editText.getText().toString();
                    address = "http://fanyi.youdao.com/openapi.do?keyfrom=lvc1314&key=537328478&type=data&doctype=json&version=1.1&q="+query;
                    HttpUtil.sendRequestOkhttp(address, new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            String responseText = response.body().string();
                            Test test = Utility.handleFanYiResponse(responseText);
                            Log.d("Test", "onResponse: "+test);
                            System.out.println(test);
                            mtest =test;
                            setResponseText(mtest);
                        }
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }
                    });
                   // setResponseText(mtest);
                }else{
                    Toast.makeText(this,"请输入要翻译的文本",Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }

    private void  initUI(){
        editText = (EditText) findViewById(R.id.edit_text);
        btn = (Button) findViewById(R.id.btn);
        queryText = (TextView) findViewById(R.id .query_text);
        transText = (TextView) findViewById(R.id.trans_text);
        explText = (TextView) findViewById(R.id.explains_text);
        webText = (TextView) findViewById(R.id.web_text);
        btn.setOnClickListener(this);

    }

    private void setResponseText(final Test mtest){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("Test", "setResponseText: "+mtest.getTranslation());

                queryText.setText(mtest.getQuery());

                String trans="";
                for (String str :mtest.getTranslation())
                    trans += str +"\n";
                transText.setText(trans);

                String explains ="";
                for (String str:mtest.getBasic().getExplains())
                    explains +=str + "\n";
                explText.setText(explains);


                String web ="";
                for (int i=0;i<mtest.getWeb().size();i++){

                    web+="key:"+mtest.getWeb().get(i).getKey()+"\n";
                    web+="value:"+mtest.getWeb().get(i).getValue()+"\n";

                }
                webText.setText(web);

            }
        });

    }
}
