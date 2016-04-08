package com.mdx.mobile.commons.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ProtoBuffer {
	
	public void protobuf(com.google.protobuf.GeneratedMessage.Builder<?> obj,OutputStream out) throws IOException{
		obj.build().writeTo(out);
		out.close();
	}
	
	public com.google.protobuf.GeneratedMessage.Builder<?> unprotobuf(InputStream in,com.google.protobuf.GeneratedMessage.Builder<?> build) throws IOException{
		build.mergeFrom(in);
		in.close();
		return build;
	}
}
