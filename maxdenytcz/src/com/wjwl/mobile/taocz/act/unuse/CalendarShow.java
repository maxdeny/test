package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import com.wjwl.mobile.taocz.act.CalendarAct;
//
//public class CalendarShow {
//	private int mYear;
//	private int mMonth;
//	public CalendarShow(int mYear, int mMonth){
//		this.mYear = mYear;
//		this.mMonth = mMonth;
//		
//		calculateMonthFirstday();
//	}
//	
//	public void calculateMonthFirstday(){
//		int month, first_day=0;
//    	if((mYear%4==0 && mYear%100!=0)||(mYear%400==0))
//    		month=1;
//    	else
//    		month=0;
//
//    	int y, y12, c, c12, m, d;//Zeller公式:w=y+[y/4]+[c/4]-2*c+[26*(m+1)/10]+d-1 
//    	y = mYear%100;
//    	y12 = (mYear-1)%100; //only for January and February
//    	c = mYear/100;
//    	c12 = (mYear-1)/100;
//    	m = mMonth;
//    	d = 1;
//    	
//    	switch(mMonth){
//    	case 1: {first_day = y12 + y12/4 +c12/4 - 2*c12 + 26*(13 + 1)/10 + d - 1;break;}
//    	case 2: {first_day = y12 + y12/4 +c12/4 - 2*c12 + 26*(14 + 1)/10 + d - 1;break;}
//    	case 3: {first_day = y + y/4 +c/4 - 2*c + 26*(m + 1)/10 + d - 1;break;}
//    	case 4: {first_day = y + y/4 +c/4 - 2*c + 26*(m + 1)/10 + d - 1;break;}
//    	case 5: {first_day = y + y/4 +c/4 - 2*c + 26*(m + 1)/10 + d - 1;break;}
//    	case 6: {first_day = y + y/4 +c/4 - 2*c + 26*(m + 1)/10 + d - 1;break;}
//    	case 7: {first_day = y + y/4 +c/4 - 2*c + 26*(m + 1)/10 + d - 1;break;}
//    	case 8: {first_day = y + y/4 +c/4 - 2*c + 26*(m + 1)/10 + d - 1;break;}
//    	case 9: {first_day = y + y/4 +c/4 - 2*c + 26*(m + 1)/10 + d - 1;break;}
//    	case 10: {first_day = y + y/4 +c/4 - 2*c + 26*(m + 1)/10 + d - 1;break;}
//    	case 11: {first_day = y + y/4 +c/4 - 2*c + 26*(m + 1)/10 + d - 1;break;}
//    	case 12: {first_day = y + y/4 +c/4 - 2*c + 26*(m + 1)/10 + d - 1;break;}
//    	}
//    	if(first_day<0)
//    		first_day = 7 - (Math.abs(first_day))%7;//first_day每月第一天星期几
//    	else
//    		first_day = first_day%7;
//    	
//    	switch(mMonth){
//			case 1: {CalculateCalendar(1,first_day,31);break;}
//			case 2: {CalculateCalendar(2,first_day,28+month);break;}
//			case 3: {CalculateCalendar(3,first_day,31);break;}
//			case 4: {CalculateCalendar(4,first_day,30);break;}
//			case 5: {CalculateCalendar(5,first_day,31);break;}
//			case 6: {CalculateCalendar(6,first_day,30);break;}
//			case 7: {CalculateCalendar(7,first_day,31);break;}
//			case 8: {CalculateCalendar(8,first_day,31);break;}
//			case 9: {CalculateCalendar(9,first_day,30);break;}
//			case 10: {CalculateCalendar(10,first_day,31);break;}
//			case 11: {CalculateCalendar(11,first_day,30);break;}
//			case 12: {CalculateCalendar(12,first_day,31);break;}
//    	}
//	}
//	
//	public void CalculateCalendar(int month_no, int week_no, int month_days){
//		
//		int i, s;
//    	for (i=0;i<week_no;i++)
//    		CalendarAct.dateArr[i/7][i%7] = "";
//    	for(i=week_no; i<week_no + month_days; i++){
//    		s = i - week_no + 1;
//    		CalendarAct.dateArr[i/7][i%7] = String.valueOf(s);
//    	}
//    	for(i=week_no+month_days; i<42; i++)
//    		CalendarAct.dateArr[i/7][i%7] = "";
//	}
//}
