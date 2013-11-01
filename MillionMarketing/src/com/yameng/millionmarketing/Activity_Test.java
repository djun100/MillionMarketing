package com.yameng.millionmarketing;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yamtz.millionmarketing.R;

public class Activity_Test extends Activity {
	//private static final int REQUEST_CODE = 2;  
	 HttpPost httpRequest=new HttpPost(UriAPI.HTTPCustomer);  
	 EditText et_name;
	 EditText et_pwd;
	 TextView show_login;
	Button btn_login;
	Button btn_cancle;
	ProgressDialog progressDialog;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        //初始化登陆界面
        btn_login=(Button) findViewById(R.id.btn_login);
/*        btn_cancle=(Button) findViewById(R.id.btn_cancle);
        et_name=(EditText) findViewById(R.id.et_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        show_login=(TextView) findViewById(R.id.show_login);*/
   
        progressDialog = new ProgressDialog(this);
        btn_login.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				//通过AsyncTask类提交数据 异步显示
				new AT().execute(et_name.getText().toString(),et_pwd.getText().toString());
			}
		});
        
    }
    public class UriAPI {  
        /** 定义一个Uri **/  
    	public static final String HTTPCustomer ="http://10.0.1.9:8026/JSONDemo/servlet/CustomerServlet";
    }
    @SuppressWarnings("rawtypes")
	class AT extends AsyncTask{
      
    	String result="";
    	@Override
    	protected void onPreExecute() {
    		//加载progressDialog
    		progressDialog.show();
    	}
    	
		@Override
		protected Object doInBackground(Object... params_obj) {
			CharSequence username="";
			CharSequence password="";
			
			username=et_name.getText();
			
			password =et_pwd.getText();
			if(!username.equals("")&&!password.equals("")){
				//请求数据
				HttpPost httpRequest  = new HttpPost(UriAPI.HTTPCustomer);
				//创建参数  
				 List<NameValuePair> params=new ArrayList<NameValuePair>(); 
				 params.add(new BasicNameValuePair("username", username.toString()));
				 params.add(new BasicNameValuePair("password", password.toString()));
				 //params.add(new BasicNameValuePair("flag","0"));
				 try {
					 //对提交数据进行编码
					httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
					HttpResponse httpResponse=new DefaultHttpClient().execute(httpRequest);
					//获取响应服务器的数据
					if (httpResponse.getStatusLine().getStatusCode()==200) {
						//利用字节数组流和包装的绑定数据
						byte[] data =new byte[2048];
						//先把从服务端来的数据转化成字节数组
						data =EntityUtils. toByteArray((HttpEntity)httpResponse.getEntity());  
						//再创建字节数组输入流对象   
						ByteArrayInputStream bais = new ByteArrayInputStream(data);  
						 //绑定字节流和数据包装流   
						DataInputStream dis = new DataInputStream(bais);  
						  //将字节数组中的数据还原成原来的各种数据类型，代码如下：  
						result=new String(dis.readUTF());
						   Log.i("服务器返回信息:", result);
					}
				} catch(ClientProtocolException e){  
                    e.printStackTrace();  
                }catch(UnsupportedEncodingException e){  
                    e.printStackTrace();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
				
			}
			return result;
		}
    	
      @Override
    protected void onPostExecute(Object result) {
    	
    	  //获得服务器返回信息成功后
    	  show_login.setText(result.toString());
    	  //取消进度条
    	  progressDialog.cancel();
      }
    
    } 
}