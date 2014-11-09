package com.vladimir.pinterestlistview;


import android.app.Activity;

import android.app.hairstyle.R;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class SearchActivity extends	Activity {
	String g,h,p,c;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity);
		
		RadioGroup Gender = (RadioGroup) findViewById(R.id.Gender);
		RadioGroup HairLength = (RadioGroup) findViewById(R.id.HairLength);
		RadioGroup Permanent = (RadioGroup) findViewById(R.id.Permanent);
		RadioGroup Color = (RadioGroup) findViewById(R.id.Color);
		Button serchbutton = (Button) findViewById(R.id.serchbutton);
		Button noserchbutton = (Button) findViewById(R.id.noserchbutton);
		
		Gender.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rb = (RadioButton) findViewById(checkedId);
				if(checkedId==R.id.Male){
					g="1";
				}
				else if(checkedId==R.id.Female){
					g="2";
				}
				
			}
		});
		HairLength.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rb = (RadioButton) findViewById(checkedId);
				if(checkedId==R.id.Short){
					h="1";
				}
				else if(checkedId==R.id.Mid){
					h="2";
				}
				else if(checkedId==R.id.Long){
					h="3";
				}
				
			}
		});
		Permanent.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rb = (RadioButton) findViewById(checkedId);
				if(checkedId==R.id.Yes){
					p="1";
				}
				else if(checkedId==R.id.No){
					p="2";
				}
				
			}
		});
		Color.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rb = (RadioButton) findViewById(checkedId);
				if(checkedId==R.id.Yes2){
					c="1";
				}
				else if(checkedId==R.id.No2){
					c="2";
				}
				
			}
		});
		serchbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			if(g=="0"||h=="0"||p=="0"||c=="0"){
				Toast.makeText(SearchActivity.this, "안누르신부분이 있습니다.", Toast.LENGTH_SHORT).show();
			}else{
				
			}
			finish();
				
			}
		});
		noserchbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
				
				
			}
		});
	}
	@Override
	protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first){
		super.onApplyThemeResource(theme, resid, first);
		
		theme.applyStyle(android.R.style.Theme_Panel, true);
	}
}
