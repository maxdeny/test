package com.example.myapidemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CopyOfMainActivity extends Activity implements OnClickListener{

    private CustomerView3 view3;  
    VerticalAutoScrollTextView autoScrollTextView;
    private Button mBtnNext;
    private Button mBtnPrev;
    private AutoTextView mTextView02;
    private static int sCount = 10;
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.cpactivity_main);  
        autoScrollTextView = (VerticalAutoScrollTextView)findViewById(R.id.text);
        autoScrollTextView.init(getWindowManager());
        autoScrollTextView.startScroll();
        init();
    }  
  
    private void init() {
        // TODO Auto-generated method stub
        mBtnNext = (Button) findViewById(R.id.next);
        mBtnPrev = (Button) findViewById(R.id.prev);
        mTextView02 = (AutoTextView) findViewById(R.id.switcher02);
        mTextView02.setText("Hello world!");
        mBtnPrev.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
       }

       @Override
       public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
        case R.id.next:
         mTextView02.next();
         sCount++;
         break;
        case R.id.prev:
         mTextView02.previous();
         sCount--;
         break;
        }
        mTextView02.setText(sCount%2==0 ? 
          sCount+"AAFirstAA" :
          sCount+"BBBBBBB");
        System.out.println("getH: ["+mTextView02.getHeight()+"]");

       }


    
}
