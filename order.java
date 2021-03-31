import java.util.*;
class order 
{
 HashMap<foodItem,Integer> item;
 double price;
 restaurant r;
 int delivery;
 public order(HashMap h,double p,restaurant r,int delivery)
 {
	 this.item=h;
	 this.price=p;
	 this.r=r;
	 this.delivery=delivery;
 }
 void pDetails()
 {  
	 System.out.println("Item list:");
	 for(Map.Entry m:item.entrySet())  
	 {
		System.out.println(((foodItem)m.getKey()).getName()+" qty: "+(int)m.getValue());
	 }
	 
System.out.println("for Rs " +this.price+" from Restaurant "+r.getName()+ " and Delivery Charge: "+ this.delivery);
 }
}
