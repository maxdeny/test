package com.my.spyware.json;

import java.util.List;

import android.graphics.drawable.Drawable;

public class HomeImgListBuilder extends ErrorBuilder {
	private List<HomeImg> Ads;
	private List<HomeUrl> Navs;

	public List<HomeImg> getAds() {
		return Ads;
	}

	public void setAds(List<HomeImg> ads) {
		Ads = ads;
	}

	public List<HomeUrl> getNavs() {
		return Navs;
	}

	public void setNavs(List<HomeUrl> navs) {
		Navs = navs;
	}

	public static class HomeUrl {
		private int id, seq;
		private String name, title, url, imageUrl;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getSeq() {
			return seq;
		}

		public void setSeq(int seq) {
			this.seq = seq;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

	}

	public static class HomeImg {
		private int id, seq, imgid = -1;
		private String name, title, url, imageUrl;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getSeq() {
			return seq;
		}

		public void setSeq(int seq) {
			this.seq = seq;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public int getImgid() {
			return imgid;
		}

		public void setImgid(int imgid) {
			this.imgid = imgid;
		}

	}
}
