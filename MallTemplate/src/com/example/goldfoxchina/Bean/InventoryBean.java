package com.example.goldfoxchina.Bean;

/**
 * 库存
 * @author kysl
 *
 */

public class InventoryBean {
	
	
	private static InventoryBean inventory=null;
	
	private int inventory_count=0;
	
	private InventoryBean(){}
	
	public static InventoryBean getInventory(){
		if(inventory==null){
			inventory=new InventoryBean();
		}
		return inventory;
	}

	public int getInventory_count() {
		return inventory_count;
	}

	public void setInventory_count(int inventory_count) {
		this.inventory_count = inventory_count;
	}
	
	

}
