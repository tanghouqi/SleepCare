package com.tang.logindemo;

import com.tang.dao.DbDAO;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText username = null;
	EditText password = null;
	Button submit = null;
	Button reset = null;
	Button login_regist = null;
	String user,pwd;
	DbDAO dbDAO;
	Builder alertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		username = (EditText) findViewById(R.id.username_text);
		password = (EditText) findViewById(R.id.password_text);
		submit = (Button) findViewById(R.id.submit);
		reset = (Button) findViewById(R.id.reset);
		login_regist = (Button) findViewById(R.id.login_regist);
		//�¼�����
		submit.setOnClickListener(listener1);
		reset.setOnClickListener(listener2);
		login_regist.setOnClickListener(login_regist_listener);
		//��ʼ��
		dbDAO = new DbDAO(this);
		//�Ի���
		alertDialog =  new AlertDialog.Builder(this);
	}
	//��ҳ����ת
	private OnClickListener listener1 = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//��ȡ�����ϵ�����
			user = username.getText().toString().trim();
			pwd = password.getText().toString().trim();
			try {
				if(dbDAO.login(user, pwd)){
					alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//��ת��������
							Intent it = new Intent();
							it.setClass(MainActivity.this, MainFrameActivity.class);
							MainActivity.this.startActivity(it);
							dialog.cancel();
						}
						
					});
					alertDialog.setTitle("������Ϣ");
					alertDialog.setMessage("�˺����ƣ�"+username.getText()+"\n"+"�˺����룺"+password.getText());
					alertDialog.show();
					System.out.println("��¼�ɹ�������");
					
				}else{
					Toast.makeText(getApplicationContext(), "�û��������벻��ȷ", Toast.LENGTH_LONG).show();  
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	//�ı��������¼�
	private OnClickListener listener2 = new OnClickListener() {	
		@Override
		public void onClick(View v) {
			username.setText("");
			password.setText("");
		}
	};
	
	//��ת��ע�����
	private OnClickListener login_regist_listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent it = new Intent();
			it.setClass(MainActivity.this, RegistActivity.class);
			MainActivity.this.startActivity(it);
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
