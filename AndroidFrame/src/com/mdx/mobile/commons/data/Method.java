package com.mdx.mobile.commons.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.mdx.mobile.commons.verify.DES;

public class Method {
	public static void serializeZip(Object obj,OutputStream out) throws IOException{
		Serialize slz=new Serialize();
		Zip zip=new Zip();
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		slz.serialize(obj, outstream);
		InputStream in=new ByteArrayInputStream(outstream.toByteArray());
		zip.zip(in, out);
	}
	
	public static Object unserializeZip(String path) throws IOException, ClassNotFoundException{
		return unserializeZip(new File(path));
	}
	
	public static Object unserializeZip(File file) throws IOException, ClassNotFoundException{
		Serialize slz=new Serialize();
		Zip zip=new Zip();
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		zip.unzip(file, outstream);
		return slz.unSerialize(new ByteArrayInputStream(outstream.toByteArray()));
	}

	public static void protobufZip(com.google.protobuf.GeneratedMessage.Builder<?> obj,OutputStream out) throws IOException{
		ProtoBuffer pbf=new ProtoBuffer();
		Zip zip=new Zip();
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		pbf.protobuf(obj, outstream);
		InputStream in=new ByteArrayInputStream(outstream.toByteArray());
		zip.zip(in, out);
	}
	
	public static com.google.protobuf.GeneratedMessage.Builder<?> unprotobufZip(String path,com.google.protobuf.GeneratedMessage.Builder<?> build) throws IOException{
		return unprotobufZip(new FileInputStream(new File(path)), build);
	}
	
	public static com.google.protobuf.GeneratedMessage.Builder<?> unprotobufZip(File file,com.google.protobuf.GeneratedMessage.Builder<?> build) throws IOException{
		return unprotobufZip(new FileInputStream(file), build);
	}
	
	public static com.google.protobuf.GeneratedMessage.Builder<?> unprotobufZip(InputStream in,com.google.protobuf.GeneratedMessage.Builder<?> build) throws IOException{
		ProtoBuffer pbf=new ProtoBuffer();
		Zip zip=new Zip();
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		zip.unzip(in, outstream);
		return pbf.unprotobuf(new ByteArrayInputStream(outstream.toByteArray()), build);
	}
	
	
	public static void protobufSeralizeDes(com.google.protobuf.GeneratedMessage.Builder<?> obj,OutputStream out) throws Exception{
		ProtoBuffer pbf=new ProtoBuffer();
		ByteArrayOutputStream o = new ByteArrayOutputStream();
		pbf.protobuf(obj, o);
		DES des = new DES();
		byte[] bytes = des.desEncrypt(o.toByteArray());
		out.write(bytes);
		out.close();
	}
	
	public static void protobufSeralize(com.google.protobuf.GeneratedMessage.Builder<?> obj,OutputStream out) throws Exception{
		ProtoBuffer pbf=new ProtoBuffer();
		ByteArrayOutputStream o = new ByteArrayOutputStream();
		pbf.protobuf(obj, o);
		out.write(o.toByteArray());
		out.close();
	}
	
	public static byte[] protobufSeralize(com.google.protobuf.GeneratedMessage.Builder<?> obj) throws Exception{
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		protobufSeralize(obj,out);
		return out.toByteArray();
	}
	
	public static byte[] protobufSeralizeDes(com.google.protobuf.GeneratedMessage.Builder<?> obj) throws Exception{
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		protobufSeralizeDes(obj,out);
		return out.toByteArray();
	}

	public static  com.google.protobuf.GeneratedMessage.Builder<?> unprotobufSeralize(InputStream in,com.google.protobuf.GeneratedMessage.Builder<?> build) throws Exception{
		ProtoBuffer pbf=new ProtoBuffer();
		return pbf.unprotobuf(in, build);
	}
	
	public static  com.google.protobuf.GeneratedMessage.Builder<?> unprotobufSeralize(byte[] bytes,com.google.protobuf.GeneratedMessage.Builder<?> build) throws Exception{
		ByteArrayInputStream in=new ByteArrayInputStream(bytes);
		return unprotobufSeralize(in,build);
	}
	
	public static  com.google.protobuf.GeneratedMessage.Builder<?> unprotobufSeralizeDes(InputStream in,com.google.protobuf.GeneratedMessage.Builder<?> build) throws Exception{
		ProtoBuffer pbf=new ProtoBuffer();
		ByteArrayOutputStream fos=new ByteArrayOutputStream();
		byte[] bt = new byte[1024];
		int ind = 0;
		while ((ind = in.read(bt)) >= 0) {
			fos.write(bt, 0, ind);
		}
		fos.close();
		DES des = new DES();
		byte[] bytes = des.desDecrypt(fos.toByteArray());
		ByteArrayInputStream bytein=new ByteArrayInputStream(bytes);
		return pbf.unprotobuf(bytein, build);
	}
}
