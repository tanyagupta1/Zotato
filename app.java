/*
 * Name: Tanya Gupta
 * Roll No.-2019119
 * Branch:CSE
 * Assignment 2
 * Assumption:-I have assumed that reward points for a particular order 
 * are added after the order has been checked out. i.e. currently earned reward is not deducted from current order
 */
import java.util.*;
interface USER
{
	void pDetail();
	void startMenu();
	double getReward();
	void setreward(double r);
}

class foodItem 
{
	private String foodName;
	private int itemPrice;
	private int itemQuantity;
	private String itemCategory;
	private int offer=0;
	private final restaurant rest;
	private final int id;
	private static int nextId=0;
	public String getName() {return this.foodName;}
	public int getId() {return this.id;}
	public int getPrice() {return this.itemPrice;}
	public String getcategory() {return this.itemCategory;}
	public int getQuantity() {return this.itemQuantity;}
	public int getOffer() {return this.offer;}
	
	public void setFoodName(String n) {this.foodName=n;}
	public void setItemPrice(int n) {this.itemPrice=n;}
	public void setItemQuantity(int n) {this.itemQuantity=n;}
	public void setItemCategory(String n) {this.itemCategory=n;}
	public void setOffer(int n) {this.offer=n;}
	
	public foodItem(app zotato,String name,int price,int q,String cat,int offer,restaurant r)
	{
		this.foodName=name;
		this.itemPrice=price;
		this.itemQuantity=q;
		this.itemCategory=cat;
		this.offer=offer;
		this.rest=r;
		this.id=++nextId;
		zotato.food.put(this.id, this);
		
	}
	public void pDetail() 
	{
    System.out.print(this.id +" "+this.foodName+" "+this.itemPrice);
    System.out.print(" "+this.itemQuantity+" "+" "+this.offer+"% off "+this.itemCategory+"\n");
	}
	public void fullDetail() 
	{
    System.out.print(this.id +" "+this.rest.getName()+" "+this.foodName+" "+this.itemPrice);
    System.out.print(" "+this.itemQuantity+" "+" "+this.offer+"% off "+this.itemCategory+"\n");
	}
	public void cartDetail(int q) 
	{
    System.out.print(this.id +" "+this.rest.getName()+" "+this.foodName+" "+this.itemPrice);
    System.out.print(" "+this.offer+"% off "+this.itemCategory+" qt: "+q+"\n");
	}
	
	
}

public class app 
{
private List<restaurant> restaurants=new ArrayList<>();
private List<customer> customers=new ArrayList<>();
HashMap<Integer,foodItem> food=new HashMap<>();
private double delivery=0;
private double transaction=0;
public void setTransaction(double t) {this.transaction+=t;}
public void setDelivery(double t) {this.delivery+=t;}
void startMenu()
{
	System.out.println("Welcome to Zotato:");
	System.out.println("1) Enter as Restaurant Owner");
	System.out.println("2) Enter as Customer");
	System.out.println("3) Check User Details");
	System.out.println("4) Company Account details");
	System.out.println("5) Exit");
}
void restLoginMenu()
{
	System.out.println("Choose restaurant");
	int i=1;
	for(restaurant r: restaurants)
	{
		if(r.getCategory().equals(""))System.out.println(i+") "+r.getName());
		else System.out.println(i+") "+r.getName()+" ("+r.getCategory()+")");
		i++;
	}
}
void custLoginMenu()
{
	int i=1;
	for(customer c:customers)
	{
		
		System.out.println(i+". "+" "+c.getName()+" ("+c.getCategory()+")");
		i++;
	}
}
void companyDeets()
{
	System.out.println("Total Delivery charge collected:"+this.delivery);
	System.out.println("Total company balance:"+this.transaction);
	
}
void userDeets()
{
	System.out.println("1) Customer List");
	System.out.println("2) Restaurant List");
}
void add()
{
	customers.add(new customer("Ram","Delhi","Elite"));
	customers.add(new customer("Sam","Pune","Elite"));
	customers.add(new customer("Tim","Delhi","Special"));
	customers.add(new customer("Kim","Delhi",""));
	customers.add(new customer("Jim","Delhi",""));
	
	authenticRestaurant a=new authenticRestaurant("Shah",0,"Delhi");
	restaurants.add(a); 
	restaurants.add(new restaurant("Ravi's","","Delhi"));
	
	a=new authenticRestaurant("The Chinese",0,"Delhi");
	restaurants.add(a);
	fastFoodRestaurant f=new fastFoodRestaurant("Wang's",0,"Delhi");
	restaurants.add(f);
	
	restaurants.add(new restaurant("Paradise","","Delhi"));
	
	
	
	
}

public static void main(String[] args)
{
	Scanner s=new Scanner(System.in);
	app zotato=new app();
	zotato.add();
	boolean run=true;
	while(run)
	{
		zotato.startMenu();
		int startChoice=s.nextInt();
		switch(startChoice)
		{
		case 1:
			zotato.restLoginMenu();
			boolean option1=true;
			int chosenRest=s.nextInt();
			while(option1)
			{
			restaurant chosenR=zotato.restaurants.get(chosenRest-1);
			chosenR.startMenu();
			int choice1=s.nextInt();
			
			switch(choice1)
			{
			case 1:                      //add item
				chosenR.addFood(zotato,s);
				break;
			case 2:                     //edit item
				chosenR.editItem(zotato,s);
				break;
			case 3:                     //print Reward
				System.out.println("Total reward is "+chosenR.getReward());
				break;
			case 4:                    //discount on bill
				//if (zotato.arestaurants.contains(chosenR))
				if(chosenR.getCategory().equals("authentic"))
				{
					authenticRestaurant aR=(authenticRestaurant)chosenR;
					System.out.println("Enter offer on total bill value");
					aR.setDiscount(s.nextInt());
				}
				//else if(zotato.frestaurants.contains(chosenR))
				else if(chosenR.getCategory().equals("Fast Food"))
				{
					fastFoodRestaurant fR=(fastFoodRestaurant)chosenR;
					System.out.println("Enter offer on total bill value");
					fR.setDiscount(s.nextInt());
				}
				else {System.out.println("This feature is only for Authentic and Fast Food");}
				break;
			case 5:
				option1=false;
				break;
			}
			}
		
		
			
			break;
		case 2:
			zotato.custLoginMenu();
			int cId=s.nextInt();
			customer chosenC=zotato.customers.get(cId-1);
			restaurant curRest=null;
			boolean option2 =true;
			boolean firstTime=true;
			while(option2)
			{
				if(firstTime) chosenC.startMenu(); else chosenC.menuAfterRSelect();
				int choice=s.nextInt();
				
				switch(choice)
				{
				case 1:
				if(firstTime)
				{
					firstTime=false;
					zotato.restLoginMenu();
					int choi=s.nextInt();
					curRest=zotato.restaurants.get(choi-1);
					}
					curRest.foodMenu();
					int cFood=s.nextInt();
					System.out.println("Enter Quantity:");
					int qt=s.nextInt();
					chosenC.addToCart(zotato,curRest, qt, cFood);
					break;
				case 2:                  
					curRest.checkOut(zotato, chosenC);
					firstTime=true;
					break;
				case 3:                     
					System.out.println("Total reward is "+chosenC.getReward());
					break;
				case 4:                   
					chosenC.printOrders();
					break;
				case 5:
					option2=false;
					break;
				}
				
			}
			break;
		case 3:
			zotato.userDeets();
			int uvsr=s.nextInt();
			if(uvsr==1)
			{   
				int i=1;
				for(customer c:zotato.customers) { System.out.println(i+". "+c.getName()); i++;}
				int choice=s.nextInt();
				zotato.customers.get(choice-1).pDetail();
			}
			else if(uvsr==2)
			{
				int i=1;
				for(restaurant r:zotato.restaurants) { System.out.println(i+". "+r.getName()); i++;}
				int choice=s.nextInt();
				zotato.restaurants.get(choice-1).pDetail();
			}
			break;
		case 4:
			zotato.companyDeets();
			break;
		case 5:
			run=false;
			break;
		}
	}
	
}

}

