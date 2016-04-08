package com.example.testpassword;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;

import com.example.gridpasswordview.util.GridPasswordView;
import com.example.gridpasswordview.util.PasswordType;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemSelected;


public class MainActivity extends Activity {

    @ViewInject(R.id.gpv_normal)
    GridPasswordView gpvNormal;
    @ViewInject(R.id.gpv_length)
    GridPasswordView gpvLength;
    @ViewInject(R.id.gpv_transformation)
    GridPasswordView gpvTransformation;
    @ViewInject(R.id.gpv_passwordType)
    GridPasswordView gpvPasswordType;
    @ViewInject(R.id.gpv_customUi)
    GridPasswordView gpvCustomUi;
    @ViewInject(R.id.gpv_normail_twice)
    GridPasswordView gpvNormalTwice;
    @ViewInject(R.id.pswtype_sp)
    Spinner pswtypeSp;

    
    boolean isFirst = true;
    String firstPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        onPwdChangedTest();
    }


    //    @OnItemSelected(R.id.pswtype_sp)
    //    void onTypeSelected(int position) {
    //        switch (position) {
    //            case 0:
    //                gpvPasswordType.setPasswordType(PasswordType.NUMBER);
    //                break;
    //
    //            case 1:
    //                gpvPasswordType.setPasswordType(PasswordType.TEXT);
    //                break;
    //
    //            case 2:
    //                gpvPasswordType.setPasswordType(PasswordType.TEXTVISIBLE);
    //                break;
    //
    //            case 3:
    //                gpvPasswordType.setPasswordType(PasswordType.TEXTWEB);
    //                break;
    //        }
    //
    //    }

    // Test GridPasswordView.clearPassword() in OnPasswordChangedListener.
    // Need enter the password twice and then check the password , like Alipay
    void onPwdChangedTest(){
        gpvNormalTwice.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                if (psw.length() == 6 && isFirst){
                    gpvNormalTwice.clearPassword();
                    isFirst = false;
                    firstPwd = psw;
                }else if (psw.length() == 6 && !isFirst){
                    if (psw.equals(firstPwd)){
                        Log.d("MainActivity", "The password is: " + psw);
                    }else {
                        Log.d("MainActivity", "password doesn't match the previous one, try again!");
                        gpvNormalTwice.clearPassword();
                        isFirst = true;
                    }
                }
            }

            @Override
            public void onInputFinish(String psw) { }
        });
    }

}
