package com.wjwl.mobile.taocz.data;

public class LifeData {
	String[] str = {
			"法国NUXE欧树神奇护理油全效包养油晶莹油改善气质皮肤发质...",
			"74.5",
			"86.00",
			"123",
			"2",
			"20天10小时31天",
			"非常好，比想象中的好很多。原来也在京东买过其他品牌的小叶紫檀手串，品相很差，Kriti的包装就很精美，手串做工很精致，大小适合女孩子，还有佛珠盘法和毛巾，很周到。",
			"冰冻菜头", "一天前", "21-50元", "面包、蛋糕", "休息小憩", "可刷卡，有下午茶", "香滑巧克力",
			"一米的阳光", "4.7", "4.7", "4.7" };
	public String intr, price, oldprice, ordernum, limit, overtime, comment,
			comment_name, commment_time, renjun, leixing, fanwei, tese,
			tuijian, shopname, service, environment, pricecost;

	public LifeData() {
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		setintr(str[0]);
		setprice(str[1]);
		setoldprice(str[2]);
		setordernum(str[3]);
		setlimit(str[4]);
		setovertime(str[5]);
		setcomment(str[6]);
		setcomment_name(str[7]);
		setcommment_time(str[8]);
		setrenjun(str[9]);
		setleixing(str[10]);
		setfanwei(str[11]);
		settese(str[12]);
		settuijian(str[13]);
		setshopname(str[14]);
		setservice(str[15]);
		setenvironment(str[16]);
		setpricecost(str[17]);
	}

	private void setintr(String intr) {
		this.intr = intr;
	}

	public String getintr() {
		return intr;
	}

	private void setprice(String price) {
		this.price = price;
	}

	public String getprice() {
		return price;
	}

	private void setoldprice(String oldprice) {
		this.oldprice = oldprice;
	}

	public String getoldprice() {
		return oldprice;
	}

	private void setordernum(String ordernum) {
		this.ordernum = ordernum;
	}

	public String getordernum() {
		return ordernum;
	}

	private void setlimit(String limit) {
		this.limit = limit;
	}

	public String getlimit() {
		return limit;
	}

	private void setovertime(String overtime) {
		this.overtime = overtime;
	}

	public String getovertime() {
		return overtime;
	}

	private void setcomment(String comment) {
		this.comment = comment;
	}

	public String getcomment() {
		return comment;
	}

	private void setcomment_name(String comment_name) {
		this.comment_name = comment_name;
	}

	public String getcomment_name() {
		return comment_name;
	}

	private void setcommment_time(String commment_time) {
		this.commment_time = commment_time;
	}

	public String getcommment_time() {
		return commment_time;
	}

	private void setrenjun(String renjun) {
		this.renjun = renjun;
	}

	public String getrenjun() {
		return renjun;
	}

	private void setleixing(String leixing) {
		this.leixing = leixing;
	}

	public String getleixing() {
		return leixing;
	}

	private void setfanwei(String fanwei) {
		this.fanwei = fanwei;
	}

	public String getfanwei() {
		return fanwei;
	}

	private void settese(String tese) {
		this.tese = tese;
	}

	public String gettese() {
		return tese;
	}

	private void settuijian(String tuijian) {
		this.tuijian = tuijian;
	}

	public String gettuijian() {
		return tuijian;
	}

	private void setshopname(String shopname) {
		this.shopname = shopname;
	}

	public String getshopname() {
		return shopname;
	}

	private void setservice(String service) {
		this.service = service;
	}

	public String getservice() {
		return service;
	}

	private void setenvironment(String environment) {
		this.environment = environment;
	}

	public String getenvironment() {
		return environment;
	}

	private void setpricecost(String pricecost) {
		this.pricecost = pricecost;
	}

	public String getpricecost() {
		return pricecost;
	}
}
