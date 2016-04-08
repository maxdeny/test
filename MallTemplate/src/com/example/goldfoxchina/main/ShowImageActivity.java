package com.example.goldfoxchina.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.goldfoxchina.Adapter.ChildAdapter;
import com.example.goldfoxMall.R;

import java.util.ArrayList;
import java.util.List;

public class ShowImageActivity extends Activity {
    private int activityId = -1;
    private List<String> choiceImages = new ArrayList<String>();
    private GridView mGridView;
    private List<String> list;
    private ChildAdapter adapter;
    private ImageButton backButton;
    private Button finishAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hzy_show_image_activity);
        try {
            activityId = Integer.parseInt(getIntent().getExtras().get("fromDifferentActivity").toString());
        }catch (NullPointerException e){

        }
        list = getIntent().getStringArrayListExtra("data");
        init();
        click();
    }

    private void init() {
        backButton = (ImageButton) findViewById(R.id.back_to_image_package);
        finishAddButton = (Button) findViewById(R.id.finish_add_images);
        mGridView = (GridView) findViewById(R.id.child_grid);
        adapter = new ChildAdapter(this, list, mGridView);
        mGridView.setAdapter(adapter);
    }

    private void click() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShowImageActivity.this, GoToImageScanActivity.class);
                i.putExtra("fromDifferentActivity",activityId);
                startActivity(i);
                finish();
            }
        });
        finishAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> l = adapter.getSelectItems();
                switch (activityId) {
                    case 1:
                        if (l.size() < 2) {
                            for (int i = 0; i < l.size(); i++) {
                                int number = l.get(i);
                                String path = list.get(number);
                                choiceImages.add(path);
                            }
                            Intent intent = new Intent(ShowImageActivity.this, ShopActivity.class);
                            intent.putStringArrayListExtra("imagePath", (ArrayList<String>) choiceImages);

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ShowImageActivity.this, "只能添加1张图片...", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 2:
                        if (l.size() < 10) {
                            for (int i = 0; i < l.size(); i++) {
                                int number = l.get(i);
                                String path = list.get(number);
                                choiceImages.add(path);
                            }
                            Intent intent = new Intent(ShowImageActivity.this, AddGoodsActivity.class);
                            intent.putStringArrayListExtra("imagePath", (ArrayList<String>) choiceImages);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ShowImageActivity.this, "最多添加9张图片...", Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
