package test;

import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.mdx.android.frame.R;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.json.Updateone2json;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.server.Temp;
import com.mdx.mobile.widget.AMLayout;
import com.mdx.mobile.widget.FillLine;

public class TestJsonActive extends MActivity {
	AMLayout layout;
	FillLine fillline;
	CurrView cv;
	
	@Override
	protected void create(Bundle savedInstanceState) {
		Temp.getDpath("ssss.txt","fff");
		setContentView(R.layout.main);
		dataLoad();
		findViewById(R.index.button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dataLoad();
			}
		});
		
		try {
			JSONObject json=new JSONObject("{sss:\"\",bb:\"\"}");
			json.get("sss");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("glist")) {
			TestJson json=(TestJson) son.getBuild(); 
		}
		System.out.print(son.error);
	}
	
	
	//&amp;app_key=1322674501&amp;sig=3355771592&amp;personSessName=tang&amp;personId=&amp;Inviteidvalues=453%7C467%7C468%7C471%7C472%7C
	@Override
	public void dataLoad(int[] types){
		this.loadData(new Updateone[]{
				new Updateone2json("ZHOSTELm",
						new String[][] { { "app_key", "1322674501" }, { "sig", "3355771592" }, { "personSessName", "tang" },
										 { "personId", "" }, { "Inviteidvalues", "453|467|468|471|472|" }, }),
		});
	}
}