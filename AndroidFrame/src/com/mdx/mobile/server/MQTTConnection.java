package com.mdx.mobile.server;

import java.io.ByteArrayInputStream;

import android.content.Context;
import com.ibm.mqtt.IMqttClient;
import com.ibm.mqtt.MqttClient;
import com.ibm.mqtt.MqttException;
import com.ibm.mqtt.MqttPersistence;
import com.ibm.mqtt.MqttPersistenceException;
import com.ibm.mqtt.MqttSimpleCallback;
import com.mdx.mobile.Frame;
import com.mdx.mobile.base.Notify_Data;
import com.mdx.mobile.commons.data.Method;


public class MQTTConnection implements MqttSimpleCallback {
	private static int[] MQTT_QUALITIES_OF_SERVICE  ;
	private static int	MQTT_QUALITY_OF_SERVICE= 0;
	private static boolean	MQTT_RETAINED_PUBLISH = false;
	private String MQTT_HOST = "";
	private MqttPersistence MQTT_PERSISTENCE = null;
	private boolean MQTT_CLEAN_START = true;
	private short MQTT_KEEP_ALIVE = 1 * 1500;
	private IMqttClient mqttClient = null;
	private long mStartTime;
	private Context mContext;
	private OnContentListener mOnContentListener;
	private String mInitTopic="";
	
	public MQTTConnection(String brokerHostName, String initTopic,Context context) throws Exception {
		try{
			this.mContext = context;
			this.MQTT_HOST = brokerHostName;
			this.mInitTopic = initTopic;
			if (Frame.checkNetWork(context)) {
				String mqttConnSpec = MQTT_HOST;
				mqttClient = MqttClient.createMqttClient(mqttConnSpec,MQTT_PERSISTENCE);
				mStartTime = System.currentTimeMillis();
			} else {
				throw new Exception("network lost");
			}
		}catch(Exception e){
			if(mqttClient!=null){
				disconnect();
			}
			throw e;
		}
	}
	
	public void disconnect() {
		try {			
			mqttClient.disconnect();
		} catch (MqttPersistenceException e) {
		}
		if(mOnContentListener!=null){
			mOnContentListener.onContentLost();
		}
	}
	
	public void connect() throws Exception{
		try{
			String clientID = NotifyService.getClientId() + "/"+ Frame.getDeviceid(getContext());
			mqttClient.connect(clientID, MQTT_CLEAN_START, MQTT_KEEP_ALIVE);
			mqttClient.registerSimpleHandler(this);
			subscribeToTopic(mInitTopic);
			if (mOnContentListener != null) {
				mOnContentListener.onContent();
			}
		}catch(Exception e){
			if(mqttClient!=null){
				disconnect();
			}
			throw e;
		}
	}
	
	private void subscribeToTopic(String topicName) throws MqttException {
		if ((mqttClient == null) || (mqttClient.isConnected() == false)) {
		} else {
			
			String[] topics = topicName.split(",");
			MQTT_QUALITIES_OF_SERVICE=new int[topics.length];
			for(int i=0;i<topics.length;i++){
				MQTT_QUALITIES_OF_SERVICE[i]=0;
				if(topics[i].indexOf("/")<0 && !topics[i].startsWith("&")){
					topics[i]=NotifyService.getClientId()+"/"+topics[i];
				}else if(topics[i].startsWith("&")){
					topics[i]=topics[i].substring(1);
				}
			}
			mqttClient.subscribe(topics, MQTT_QUALITIES_OF_SERVICE);
		}
	}	

	public void publishArrived(String topicName, byte[] payload, int qos, boolean retained) {
		ByteArrayInputStream bais=new ByteArrayInputStream(payload);
		Notify_Data.Msg_Notify_Data.Builder build=Notify_Data.Msg_Notify_Data.newBuilder();
		try {
			Method.unprotobufSeralize(bais, build);
			if(mOnContentListener!=null){
				mOnContentListener.onRecive(build);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}   

	@Override
	public void connectionLost() throws Exception {
		disconnect();
	}
	
	private void publishToTopic(String topicName, String message) throws MqttException {		
		if ((mqttClient == null) || (mqttClient.isConnected() == false)) {
		} else {
			mqttClient.publish(topicName, 
							   message.getBytes(),
							   MQTT_QUALITY_OF_SERVICE, 
							   MQTT_RETAINED_PUBLISH);
		}
	}	
	
	public void sendKeepAlive() throws MqttException {
		publishToTopic(NotifyService.getClientId() + "/keepalive", Frame.getDeviceid(getContext())+"");
	}	
	
	
	public Context getContext(){
		return mContext;
	}
	
	public long getStartTime(){
		return mStartTime;
	}
	
	public void setOnContentListener(OnContentListener onContentListener){
		this.mOnContentListener=onContentListener;
	}
	
	public boolean isContented(){
		return mqttClient.isConnected();
	}
	
	public interface OnContentListener{
		public void onContent();
		public void onRecive(Notify_Data.Msg_Notify_Data.Builder build);
		public void onContentLost();
	}
}
