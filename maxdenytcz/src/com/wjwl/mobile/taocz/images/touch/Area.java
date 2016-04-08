package com.wjwl.mobile.taocz.images.touch;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

public class Area {
	private float left,top,width,height;
	private float paddingleft,paddingtop,paddingwidth,paddingheight;
	private float marginleft,margintop,marginwidth,marginheight;
	
	private float rate=1;
	
	public  Area(float left,float top,float width,float height){
		this.set(left, top, width, height);
	}
	
	public Area clone(){
		Area retn= new Area(this.left,this.top,this.width,this.height);
		retn.setPadding(this.paddingleft, paddingtop, paddingwidth, paddingheight);
		retn.setMargin(marginleft, margintop, marginwidth, marginheight);
		retn.setRate(this.rate);
		return retn;
	}
	
	public void set(float left,float top,float width,float height){
		this.left= left;
		this.top= top;
		this.width= width;
		this.height= height;
	}
	

	public void set(double left, double top, double width, double height) {
		this.left= (float) left;
		this.top= (float) top;
		this.width= (float) width;
		this.height= (float) height;
	}
	
	public Area(float area[]){
		set(area);
	}
	
	public void set(float area[]){
		this.left=area[0];
		this.top=area[1];
		this.width=area[2];
		this.height=area[3];
	}
	
	public void set(float left,float top){
		this.left= left;
		this.top= top;
	}
	
	public void setPadding(float padding){
		this.paddingleft=padding;
		this.paddingtop=padding;
		this.paddingwidth=padding;
		this.paddingheight=padding;
	}
	
	public void setPadding(float paddingleft,float paddingtop,float paddingwidth,float paddingheight){
		this.paddingleft=paddingleft;
		this.paddingtop=paddingtop;
		this.paddingwidth=paddingwidth;
		this.paddingheight=paddingheight;
	}
	
	
	public void setMargin(float margin){
		this.marginleft=margin;
		this.margintop=margin;
		this.marginwidth=margin;
		this.marginheight=margin;
	}
	
	public void setMargin(float marginleft,float margintop,float marginwidth,float marginheight){
		this.marginleft=marginleft;
		this.margintop=margintop;
		this.marginwidth=marginwidth;
		this.marginheight=marginheight;
	}
	
	public boolean isin(float x,float y){
		if((x>=this.L() && x<=this.R()) && (y>=this.T() && y<=this.B())){
			return true;
		}
		return false;
	}
	
	public boolean isin(MotionEvent event){
		return isin(event.getX(),event.getY());
	}
	
	
	public boolean isinx(float x){
		if((x>=this.L() && x<=this.R())){
			return true;
		}
		return false;
	}
	
	public boolean isinx(MotionEvent event){
		return isinx(event.getX());
	}
	
	public Rect Rect(){
		return new Rect((int)this.L(),(int)this.T(),(int)this.R(),(int)this.B());
	}
	
	public RectF RectF(){
		return new RectF(this.L(),this.T(),this.R(),this.B());
	}
	
	public float ox(){
		return ((left+this.width/2)*rate);
	}

	public float oy(){
		return ((top+this.height/2)*rate);
	}
	
	public float R(){
		return (this.getRight());
	}
	
	public float B(){
		return (this.getBottom());
	}
	
	public float L(){
		return (this.getLeft());
	}
	
	public float T(){
		return (this.getTop());
	}
	
	public float W(){
		return (this.getWidth());
	}
	
	public float H(){
		return (this.getHeight());
	}

	public float mL(){
		return (this.getMarginleft());
	}
	
	public float mT(){
		return (this.getMargintop());
	}
	
	public float mW(){
		return (this.getMarginwidth());
	}
	
	public float mH(){
		return (this.getMarginheight());
	}
	
	public float pL(){
		return (this.getPaddingleft());
	}
	
	public float pT(){
		return (this.getPaddingtop());
	}
	
	public float pW(){
		return (this.getPaddingtop());
	}
	
	public float pH(){
		return (this.getPaddingheight());
	}
	
	public float getRight(){
		return (left+width)*this.rate;
	}
	
	public float getBottom(){
		return (top+height)*this.rate;
	}

	public float getLeft() {
		return left*this.rate;
	}

	public void setLeft(float left) {
		this.left = left;
	}

	public float getTop() {
		return top*this.rate;
	}

	public void setTop(float top) {
		this.top = top;
	}

	public float getWidth() {
		return width*this.rate;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height*this.rate;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getPaddingleft() {
		return paddingleft*this.rate;
	}

	public void setPaddingleft(float paddingleft) {
		this.paddingleft = paddingleft;
	}

	public float getPaddingtop() {
		return paddingtop*this.rate;
	}

	public void setPaddingtop(float paddingtop) {
		this.paddingtop = paddingtop;
	}

	public float getPaddingwidth() {
		return paddingwidth*this.rate;
	}

	public void setPaddingwidth(float paddingwidth) {
		this.paddingwidth = paddingwidth;
	}

	public float getPaddingheight() {
		return paddingheight*this.rate;
	}

	public void setPaddingheight(float paddingheight) {
		this.paddingheight = paddingheight;
	}

	public float getMarginleft() {
		return marginleft*this.rate;
	}

	public void setMarginleft(float marginleft) {
		this.marginleft = marginleft;
	}

	public float getMargintop() {
		return margintop*this.rate;
	}

	public void setMargintop(float margintop) {
		this.margintop = margintop;
	}

	public float getMarginwidth() {
		return marginwidth*this.rate;
	}

	public void setMarginwidth(float marginwidth) {
		this.marginwidth = marginwidth;
	}

	public float getMarginheight() {
		return marginheight*this.rate;
	}

	public void setMarginheight(float marginheight) {
		this.marginheight = marginheight;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}
	
	public float Left(){
		return this.left;
	}
	public float Top(){
		return this.top;
	}
	public float Width(){
		return this.width;
	}
	public float Height(){
		return this.height;
	}
}
