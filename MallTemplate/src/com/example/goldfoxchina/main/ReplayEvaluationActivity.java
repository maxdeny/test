package com.example.goldfoxchina.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.goldfoxMall.R;

public class ReplayEvaluationActivity extends Activity {
    private ImageButton back, editReply;
    private EditText replyWords;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hzy2_replay_evalusation);
        init();
        click();
    }

    private void init() {
        back = (ImageButton) findViewById(R.id.back_to_see_the_evaluation_Activity);
        editReply = (ImageButton) findViewById(R.id.finish_reply);
        replyWords = (EditText) findViewById(R.id.reply_words);
        replyWords.addTextChangedListener(textWatcher);
        textView = (TextView) findViewById(R.id.more_words);
    }

    private void click() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = replyWords.getText().toString();
                if (word.length() > 50) {
                    Toast.makeText(ReplayEvaluationActivity.this, "请控制输入内容在50字以内...", Toast.LENGTH_LONG).show();
                }else{
                    finish();
                }
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String word = replyWords.getText().toString();
            if (word.length() > 50) {
                textView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.INVISIBLE);
            }
        }
    };
}
