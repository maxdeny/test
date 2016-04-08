package com.mdx.mobile.commons.data;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Serialize {	
		
	public void serialize(Object obj,OutputStream out) throws IOException{
		ObjectOutputStream oots = new ObjectOutputStream(out);
		oots.writeObject(obj);
		oots.close();
		out.close();
	}
	
	public Object unSerialize(InputStream in) throws IOException, ClassNotFoundException{
		ObjectInputStream instream = new ObjectInputStream(in);
		Object retn=instream.readObject();
		in.close();
		return retn;
	} 
}