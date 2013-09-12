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
	//界面上的控件
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
	//界面上的数据
	String user_value,pwd1_value,pwd2_value,sex_value;
	int age_value,phone_value;
	//数据库操作封装类
	DbDAO dbDAO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		//获取界面控件
		username = (EditText) findViewById(R.id.regist_username_text);
		password1 = (EditText) findViewById(R.id.regist_password1_text);
		password2 = (EditText) findViewById(R.id.regist_password2_text);
		age = (EditText) findViewById(R.id.regist_age_text);		
		//单选按钮
		sex_group = (RadioGroup) findViewById(R.id.radioGroup2);
		sex_group.check(R.id.radio0);
		//设置默认性别为男
		sex_value = "男";
		//sex_button = (RadioButton) sex_group.findViewById(sex_group。getCheckedRadioButtonId());
		agreement_checkBox = (CheckBox) findViewById(R.id.accept_checkBox);
		phone = (EditText) findViewById(R.id.regist_phone_text);
		login_frame = (Button) findViewById(R.id.back_login_button);
		regist_submit = (Button) findViewById(R.id.regist_submit_button);
		regist_reset = (Button) findViewById(R.id.regist_reset_button);
		
		//绑定判断密码是否一致事件
		password2.setOnFocusChangeListener(passowrd_listener);
		//绑定跳转登录界面事件
		login_frame.setOnClickListener(to_login_frame);
		//注册按钮是否可用事件
		agreement_checkBox.setOnClickListener(checkBox_listener);
		//添加注册按钮事件
		regist_submit.setOnClickListener(regist_submit_listener);
		//初始化DbDAO
		dbDAO = new DbDAO(this);
		//添加重置按钮事件 
		regist_reset.setOnClickListener(regist_rest_listener);
		
		//单选按钮事件
		sex_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int sex_button_id = group.getCheckedRadioButtonId();
				sex_button = (RadioButton) sex_group.findViewById(sex_button_id);
				Toast.makeText(getApplicationContext(), "获取的ID是"+sex_button.getText(), Toast.LENGTH_LONG).show();  
				sex_value = sex_button.getText().toString();
				System.out.println("sex:"+sex_value);
			}
		});
		
	}
	//关闭数据库
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//应用的最后一个activity关闭时释放DB
		dbDAO.close();
	}
	//判断两次密码是否一直
	private OnFocusChangeListener passowrd_listener = new OnFocusChangeListener() {	
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			pwd1_value = password1.getText().toString().trim();
			pwd2_value = password2.getText().toString().trim();
			if(password2.hasFocus() == false){
				if(pwd1_value.equals(pwd2_value)){				
					Toast.makeText(getApplicationContext(), "密码一致...", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getApplicationContext(), "密码不一致...", Toast.LENGTH_LONG).show();
				}
			}
			
		}
	};
	//跳转登录界面事件
	private OnClickListener to_login_frame = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent it = new Intent();
			it.setClass(RegistActivity.this, MainActivity.class);
			RegistActivity.this.startActivity(it);
		}
	}; 
	
	//注册按钮事件是否可用事件
	private OnClickListener checkBox_listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			pwd1_value = password1.getText().toString().trim();
			pwd2_value = password2.getText().toString().trim();
			if(agreement_checkBox.isChecked()==true){
				if(pwd1_value.equals(pwd2_value)){
					regist_submit.setEnabled(true);
				}else{
					Toast.makeText(getApplicationContext(), "密码不一致...", Toast.LENGTH_LONG).show();
				}
			}else if(agreement_checkBox.isChecked()==false ){
				regist_submit.setEnabled(false);
			}
			
		}
	};
	//注册按钮事件
	private OnClickListener regist_submit_listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//获取界面数据
			user_value = username.getText().toString().trim();
			pwd1_value = password1.getText().toString().trim();
			pwd2_value = password2.getText().toString().trim();
				//抛出数据格式化异常
			try {
				age_value = Integer.parseInt(age.getText().toString());
				phone_value = Integer.parseInt(phone.getText().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			UserInfo userinfo = new UserInfo(user_value, pwd1_value, age_value, sex_value, phone_value);
			if(dbDAO.regsit(userinfo)){
				Toast.makeText(getApplicationContext(), "恭喜用户："+user_value+"注册成功。。。", Toast.LENGTH_LONG).show(); 
				//注册成功后跳转至登录界面
				Intent it = new Intent();
				it.setClass(RegistActivity.this, MainActivity.class);
				RegistActivity.this.startActivity(it);
			}else{
				Toast.makeText(getApplicationContext(), "注册未成功", Toast.LENGTH_LONG).show(); 
			}
		}
	};
	
	//重置按钮事件
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
