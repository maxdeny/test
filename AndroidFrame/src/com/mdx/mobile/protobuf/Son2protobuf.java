package com.mdx.mobile.protobuf;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import javax.crypto.IllegalBlockSizeException;

import com.google.protobuf.GeneratedMessage;
import com.mdx.mobile.Frame;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.cache.StoreCacheManage;
import com.mdx.mobile.commons.data.Method;
import com.mdx.mobile.server.Son;

public class Son2protobuf extends Son{
	private static final long serialVersionUID = 1L;
	

	public Son2protobuf(byte[] ins, String metod, String buildMd5,GeneratedMessage.Builder<?> build,int type,int errorType,HashMap<String, String> map) {
		this.metod=metod;
		this.errorType=errorType;
		this.type=type;
		this.mapp=map;
		Msg_Retn.Builder retn=Msg_Retn.newBuilder();
		try{
			Method.unprotobufSeralizeDes(new ByteArrayInputStream(ins), retn);
			this.error=retn.getErrorCode();
			this.msg=retn.getErrorMsg();
			this.mgetmethod=retn.getReturnMethod();
			
			if(retn.getRetnMessage()!=null && retn.getRetnMessage().size()>0){
				byte[] inp=retn.getRetnMessage().toByteArray();
				if (inp.length > 0) {
					if (build == null) {
						GeneratedMessage.Builder<?> buid = Frame.INITCONFIG.getGMsg(mgetmethod);
						if(buid!=null){
							Method.unprotobufSeralize(new ByteArrayInputStream(inp), buid);
							this.build = buid;
						}
					} else {
						Method.unprotobufSeralize(
								new ByteArrayInputStream(inp), build);
						this.build = build;
					}
				}
			}
			if(buildMd5!=null && buildMd5.length()>0){
				if(error==0){
					StoreCacheManage.save(buildMd5,ins );
				}
			}
		} catch (Exception e) {
			if(e instanceof IllegalBlockSizeException){
				error=97;
				msg=e.toString();
			}else{
				error=98;
				msg=e.toString();
			}
			e.printStackTrace();
		}
	}
}
