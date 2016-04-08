package com.example.goldfoxchina.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.goldfoxchina.Adapter.SeeTheEvaluationAdapter;
import com.example.goldfoxMall.R;
import java.util.ArrayList;
import java.util.List;

/**
 *  查看评价（未用）
 */
public class SeeTheEvaluationActivity extends Activity {
    private List<Integer> Item = new ArrayList<Integer>();//初始化进来的时候 列表中包含的内容
    private ListView seeEvaluationList;
    private ImageButton backButton,home;
    private SeeTheEvaluationAdapter adapter;
    private RatingBar satisfactionRatingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hzy2_see_the_evaluation);
        init();
        click();
    }
    private void init(){
        backButton = (ImageButton) findViewById(R.id.go_to_orderActivity);
        home = (ImageButton) findViewById(R.id.check_reply_go_to_home);

        satisfactionRatingBar = (RatingBar) findViewById(R.id.satisfaction_bar);
        getInt(3.6);//由于RatingBar的setRating()方法只能给整数，所以要先进行对数值的四舍五入取整,这里传入的数值是一个double类型的

        seeEvaluationList = (ListView) findViewById(R.id.see_the_evaluation_listView);
        for(int num = 0;num<10;num++){
            Item.add(num);
        }
        adapter = new SeeTheEvaluationAdapter(SeeTheEvaluationActivity.this, Item);
        seeEvaluationList.setAdapter(adapter);
        seeEvaluationList.setDividerHeight(2);
    }
    private void click(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Home按钮
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SeeTheEvaluationActivity.this,TabHostActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        seeEvaluationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }
    /**
     *由于RatingBar的setRating()方法只能给整数，所以要先进行对数值的四舍五入取整,这里传入的数值是一个double类型的
     * @param number
     */
    private void getInt(double number){
        int num = (int) Math.round(number);
        satisfactionRatingBar.setRating(num);
    }
}
