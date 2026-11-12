package com.tap.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {

    private Map<Integer, CartItem> items;
    private int restaurantId;

    public Cart() {
        items = new LinkedHashMap<>();
        restaurantId = 0;
    }

    

    public boolean addItem(Menu menu) {
    	
    	if (items.isEmpty()) {
            restaurantId = menu.getRestaurantId();
        }

    	if (restaurantId != menu.getRestaurantId()) {
            return false;
        }
    	
    	int menuId = menu.getMenuId();
    	
    	 if (items.containsKey(menuId)) {

    	        CartItem existingItem = items.get(menuId);

    	        existingItem.setQuantity(
    	                existingItem.getQuantity() + 1
    	        );

    	    } else {
    	    	
    	    	CartItem cartItem = new CartItem(menu, 1);
    	        items.put(menuId, cartItem);
    	    }
    	 
    	 return true;
    }

    public void updateQuantity(int menuId, int quantity) {

        if (items.containsKey(menuId)) {
        	
        	if(quantity <=0) {
        		removeItem(menuId);
        	}
        	else {
        		 CartItem cartItem = items.get(menuId);
                 cartItem.setQuantity(quantity);
        	}
        }
    }
    
    public void removeItem(int menuId) {

        items.remove(menuId);

        if (items.isEmpty()) {
            restaurantId = 0;
        }
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public double getTotalAmount() {

        double total = 0;

        for (CartItem item : items.values()) {
            total += item.getSubtotal();
        }

        return total;
    }
    
    public double getTotalQuantityt() {

        double totalQuantity = 0;

        for (CartItem item : items.values()) {
        	totalQuantity += item.getQuantity();
        }

        return totalQuantity;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

   
    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {

        items.clear();
        restaurantId = 0;
    }
    
    public int getItemQuantity(int menuId) {

        if (items.containsKey(menuId)) {
            return items.get(menuId).getQuantity();
        }

        return 0;
    }
}