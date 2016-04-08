package test;

import java.util.ArrayList;
import org.json.JSONObject;
import com.mdx.mobile.json.JsonData;

public class TestJson extends JsonData{
	public ArrayList<SinghtType> singhtTypes=new ArrayList<SinghtType>();
	
	@Override
	public void build(JSONObject object) throws Exception {
		System.out.println(object.toString());
		getJsonArray(object, "data", SinghtType.class, singhtTypes);
	}
	
	
	public static class SinghtType{
		public String name,image;
		public ArrayList<Singht> singhts=new ArrayList<TestJson.Singht>();
		
		public SinghtType(JSONObject json) throws Exception{
			this.name=getJsonString(json, "name");
			this.image=getJsonString(json, "image");
			getJsonArray(json, "sight", Singht.class, singhts);
		}
	}
	
	public static class Singht{
		public String id,lev,price,interval,name,image,mark,cimage;

		public Singht(JSONObject json) throws Exception{
			this.id=getJsonString(json, "id");
			this.lev=getJsonString(json, "lev");
			this.price=getJsonString(json, "price");
			this.interval=getJsonString(json, "interval");
			this.name=getJsonString(json, "name");
			this.image=getJsonString(json, "image");
			this.mark=getJsonString(json, "mark");
			this.cimage=getJsonString(json, "cimage");
		}
	}

	@Override
	public int getErrorCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getErrorMsg() {
		// TODO Auto-generated method stub
		return null;
	}
}


