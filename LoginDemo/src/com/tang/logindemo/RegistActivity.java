package com.tang.logindemo;

import java.util.ArrayList;
import java.util.List;

import com.tang.bean.UserInfo;
import com.tang.dao.DbDAO;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class RegistActivity extends Activity {
	//�����ϵĿؼ�
	EditText username = null;
	EditText password1 = null;
	EditText password2 = null;
	EditText age = null;
	EditText phone = null;
	RadioGroup sex_group = null;
	RadioButton sex_button = null;
	//RadioButton sex_female_button = null;
	CheckBox agreement_checkBox = null;
	Button regist_submit = null;
	Button regist_reset = null;
	Button login_frame = null;
	//�����ϵ�����
	String user_value,pwd1_value,pwd2_value,sex_value;
	int age_value,phone_value;
	//���ݿ������װ��
	DbDAO dbDAO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		//��ȡ����ؼ�
		username = (EditText) findViewById(R.id.regist_username_text);
		password1 = (EditText) findViewById(R.id.regist_password1_text);
		password2 = (EditText) findViewById(R.id.regist_password2_text);
		age = (EditText) findViewById(R.id.regist_age_text);		
		//��ѡ��ť
		sex_group = (RadioGroup) findViewById(R.id.radioGroup2);
		sex_group.check(R.id.radio0);
		//����Ĭ���Ա�Ϊ��
		sex_value = "��";
		//sex_button = (RadioButton) sex_group.findViewById(sex_group��getCheckedRadioButtonId());
		agreement_checkBox = (CheckBox) findViewById(R.id.accept_checkBox);
		phone = (EditText) findViewById(R.id.regist_phone_text);
		login_frame = (Button) findViewById(R.id.back_login_button);
		regist_submit = (Button) findViewById(R.id.regist_submit_button);
		regist_reset = (Button) findViewById(R.id.regist_reset_button);
		
		//���ж������Ƿ�һ���¼�
		password2.setOnFocusChangeListener(passowrd_listener);
		//����ת��¼�����¼�
		login_frame.setOnClickListener(to_login_frame);
		//ע�ᰴť�Ƿ�����¼�
		agreement_checkBox.setOnClickListener(checkBox_listener);
		//���ע�ᰴť�¼�
		regist_submit.setOnClickListener(regist_submit_listener);
		//��ʼ��DbDAO
		dbDAO = new DbDAO(this);
		//������ð�ť�¼� 
		regist_reset.setOnClickListener(regist_rest_listener);
		
		//��ѡ��ť�¼�
		sex_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int sex_button_id = group.getCheckedRadioButtonId();
				sex_button = (RadioButton) sex_group.findViewById(sex_button_id);
				Toast.makeText(getApplicationContext(), "��ȡ��ID��"+sex_button.getText(), Toast.LENGTH_LONG).show();  
				sex_value = sex_button.getText().toString();
				System.out.println("sex:"+sex_value);
			}
		});
		
	}
	//�ر����ݿ�
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//Ӧ�õ����һ��activity�ر�ʱ�ͷ�DB
		dbDAO.close();
	}
	//�ж����������Ƿ�һֱ
	private OnFocusChangeListener passowrd_listener = new OnFocusChangeListener() {	
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			pwd1_value = password1.getText().toString().trim();
			pwd2_value = password2.getText().toString().trim();
			if(password2.hasFocus() == false){
				if(pwd1_value.equals(pwd2_value)){				
					Toast.makeText(getApplicationContext(), "����һ��...", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getApplicationContext(), "���벻һ��...", Toast.LENGTH_LONG).show();
				}
			}
			
		}
	};
	//��ת��¼�����¼�
	private OnClickListener to_login_frame = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent it = new Intent();
			it.setClass(RegistActivity.this, MainActivity.class);
			RegistActivity.this.startActivity(it);
		}
	}; 
	
	//ע�ᰴť�¼��Ƿ�����¼�
	private OnClickListener checkBox_listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			pwd1_value = password1.getText().toString().trim();
			pwd2_value = password2.getText().toString().trim();
			if(agreement_checkBox.isChecked()==true){
				if(pwd1_value.equals(pwd2_value)){
					regist_submit.setEnabled(true);
				}else{
					Toast.makeText(getApplicationContext(), "���벻һ��...", Toast.LENGTH_LONG).show();
				}
			}else if(agreement_checkBox.isChecked()==false ){
				regist_submit.setEnabled(false);
			}
			
		}
	};
	//ע�ᰴť�¼�
	private OnClickListener regist_submit_listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//��ȡ��������
			user_value = username.getText().toString().trim();
			pwd1_value = password1.getText().toString().trim();
			pwd2_value = password2.getText().toString().trim();
				//�׳����ݸ�ʽ���쳣
			try {
				age_value = Integer.parseInt(age.getText().toString());
				phone_value = Integer.parseInt(phone.getText().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			UserInfo userinfo = new UserInfo(user_value, pwd1_value, age_value, sex_value, phone_value);
			if(dbDAO.regsit(userinfo)){
				Toast.makeText(getApplicationContext(), "��ϲ�û���"+user_value+"ע��ɹ�������", Toast.LENGTH_LONG).show(); 
				//ע��ɹ�����ת����¼����
				Intent it = new Intent();
				it.setClass(RegistActivity.this, MainActivity.class);
				RegistActivity.this.startActivity(it);
			}else{
				Toast.makeText(getApplicationContext(), "ע��δ�ɹ�", Toast.LENGTH_LONG).show(); 
			}
		}
	};
	
	//���ð�ť�¼�
	private OnClickListener regist_rest_listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			username.setText("");
			password1.setText("");
			password2.setText("");
			age.setText("");
			sex_group.check(R.id.radio0);
			phone.setText("");
			agreement_checkBox.setChecked(false);
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.regist, menu);
		return true;
	}

}
