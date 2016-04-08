package com.my.spyware.json;

import java.io.Serializable;

public class DeviceDto implements Serializable {
	/** @Fields serialVersionUID : TODO */
	private static final long serialVersionUID = 1L;
	private String DeviceToken, UserName;

	public DeviceDto(String deviceToken, String userName) {
		DeviceToken = deviceToken;
		UserName = userName;
	}

	public String getDeviceToken() {
		return DeviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		DeviceToken = deviceToken;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public static class PostData {
		private String name;
		private byte[] value;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public byte[] getValue() {
			return value;
		}

		public void setValue(byte[] value) {
			this.value = value;
		}

		public PostData(String name, byte[] value) {
			super();
			this.name = name;
			this.value = value;
		}
	}
}
