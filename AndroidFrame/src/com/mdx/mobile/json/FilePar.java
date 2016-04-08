/*
 * 文件名: FilePar.java
 * 版    权：  Copyright Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: ryan
 * 创建时间:2013-12-12
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.mdx.mobile.json;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 * @author ryan
 * @version [2013-12-12 上午10:24:31] 
 */
public class FilePar {
   public String paramid;
   public String contexttype;
   public Object object;
   
   public FilePar(){}
   
   public FilePar(String name,Object obj){
       this.paramid=name;
       this.object=obj;
   }
   
   public FilePar(String name,String contexttype,Object obj){
       this.paramid=name;
       this.object=obj;
       this.contexttype=contexttype;
   }
   
}
