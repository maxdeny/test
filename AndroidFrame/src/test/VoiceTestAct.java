package test;

import java.util.ArrayList;

import com.mdx.android.frame.R;
import com.mdx.mobile.Frame;
import com.mdx.mobile.log.MLog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class VoiceTestAct extends Activity {
    /** Called when the activity is first created. */
    private Button btnReconizer;
    
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 618;
    
    private TextView resText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        Frame.init(this);
        setContentView(R.layout.main_voide);
        btnReconizer = (Button) this.findViewById(R.id.button1);
        resText = (TextView) findViewById(R.id.textview);
        btnReconizer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "");
                    startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
                }
                catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "", 1).show();
                }
            }
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String resultString = "";
            for (int i = 0; i < results.size(); i++) {
                resultString += String.valueOf(i + 1) + ":" + results.get(i) + ";";
                Log.i("", resultString);
            }
            resText.setText(resultString);
            Toast.makeText(this, resultString, 1).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        MLog.E("onStart");
        super.onStart();
    }
    
    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        MLog.E("onRestart");
        super.onRestart();
    }
    
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        MLog.E("onResume");
        super.onResume();
    }
    
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        MLog.E("onPause");
        super.onPause();
    }
    
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        MLog.E("onStop");
        super.onStop();
    }
    
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        MLog.E("onDestroy");
        super.onDestroy();
    }
}
