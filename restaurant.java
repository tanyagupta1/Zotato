import java.util.*;

class restaurant implements USER
{
	private final String name;
	protected List<foodItem> menu=new ArrayList<>();
	private String address;
	private int reward=0;
	private String category="";
	private int orders=0;
	public String getName() {return this.name;}
	@Override
	public double getReward() {return this.reward;}
	public String getCategory() {return this.category;}
	@Override
	public void setreward(double r) {this.reward+=r;}
	public void setOrders() {this.orders=this.orders+1;}
	public restaurant(String name,String category,String address)
	{
		this.name=name;
		this.category=category;
		this.address=address;
	}
	@Override
	public void startMenu()
	{   
		System.out.println("Welcome "+this.name);
		System.out.println("1) Add item");
		System.out.println("2) Edit item");
        System.out.println("3) Print Rewards");
        //System.out.println("4) Discount on bill value");
        System.out.println("5) Exit");
	}
	public void addFood(app zotato,Scanner s)
	{
		System.out.println("Food Name:");
		String f=s.next();
		f+=s.nextLine();
		System.out.println("Item price:");
		int ip=s.nextInt();
		System.out.println("Item quantity:");
		int iq=s.nextInt();
		System.out.println("Item category:");
		String ic=s.next();
		System.out.println("Offer:");
		int o=s.nextInt();
		menu.add(new foodItem(zotato,f,ip,iq,ic,o,this));
		menu.get(menu.size()-1).pDetail();
	}
	public void editItem(app zotato,Scanner s)
	{
		
		System.out.println("Choose item by code");
		for(foodItem f:menu) f.fullDetail();
		
		int c=s.nextInt();
		System.out.println("Choose an attribute to edit");
		System.out.println("1) Name");System.out.println("2) Price");System.out.println("3) Quantity");
		System.out.println("4) Category");System.out.println("5) Offer");
		int att=s.nextInt();
		foodItem f=(foodItem)(zotato.food.get(c));
		if(att==1)
		{
			System.out.println("Enter new name");
			String na=s.next(); na+=s.nextLine();
			f.setFoodName(na);
		}
		else if(att==2)
		{
			System.out.println("Enter new price");
			f.setItemPrice(s.nextInt());
		}
		else if(att==3)
		{
			System.out.println("Enter new quantity");
			f.setItemQuantity(s.nextInt());
		}
		else if(att==4)
		{
			System.out.println("Enter new category");
			f.setItemCategory(s.next());
		}
		else if(att==5)
		{
			System.out.println("Enter new offer");
			f.setOffer(s.nextInt());
		}
		f.fullDetail();
		
	}
	@Override
	public void pDetail() 
	{
		System.out.println("name: "+this.name+" address: "+this.address+" orders:"+this.orders);
		
	}                          //foroption 3 of 1st menu
	
	public void foodMenu()
	{
		for(foodItem f:menu)f.fullDetail();
	}
	public double billAfterFoodDiscount(customer c)
	{
		double sum=0;
		for(Map.Entry m:c.cart.entrySet())  
	     {  
	        foodItem f= (foodItem)m.getKey();
	        sum+=((double)f.getPrice())*((int)m.getValue())*(100-f.getOffer())/100;
	     }
		System.out.println("sum after food discount: "+sum);
		return sum;
	}
	public void checkOut(app zotato,customer c)
	{
	   int del=40;
	   double sum=billAfterFoodDiscount(c);
	   if(c.getCategory().equals("Elite") && sum>200) { sum=sum-50; del=0;}
	   else if(c.getCategory().equals("Special") && sum>200) { sum=sum-25; del=20;}   
	   if(c.getCategory().equals("Special")) del=20;
	   if(c.getCategory().equals("Elite")) del=0;
	   int reward=(5*((int)(sum/100)));
	   finalCheck(reward,del,sum,c,zotato);
	   
	}
	public void finalCheck(int reward,int del, double sum,customer c,app zotato)
	{
		   if((c.getWallet()+c.getReward())<(sum+del))
		   {
			   System.out.println("Not enough money!");
			   c.cart.clear();
			   return;
		   }
		   System.out.println("reward won is: "+reward);
		   System.out.println("Delivery charge is: "+(del));
		   System.out.println("total bill is: "+(sum+del));
		   System.out.println("amount deducted from wallet: "+Math.max(0,(sum+del-c.getReward())));
		   System.out.println("Press 1 to proceed to checkout");
		   Scanner s=new Scanner(System.in);
		   int a=s.nextInt();
		   
		   
		   System.out.println("Items in cart");
			
			for(Map.Entry m:c.cart.entrySet())  
		     {  
				foodItem f=(foodItem)m.getKey();
				f.cartDetail((int)m.getValue());
				//System.out.println(f.getId()+"-"+f.getName()+"-"+f.getOffer()+"%-"+m.getValue());   
		     }
			
		   
		   this.setreward(reward);
		 
		   this.setOrders();
		   zotato.setDelivery(del);
		   zotato.setTransaction(sum*0.01);
		   c.setWallet(Math.max(0,(sum+del-c.getReward())));
		   HashMap<foodItem,Integer> hist=new HashMap<>(); hist.putAll(c.cart);
		   c.orders.offer(new order(hist,sum+del,this,del));
		   if(c.orders.size()>10) c.orders.poll();
		   c.cart.clear();
		   if(sum+del<c.getReward()) {c.setreward(-sum-del);c.setreward(reward);}
		   else { c.setreward(-c.getReward()); c.setreward(reward);}
		  // s.close();
		   System.out.println("Succesfully bought items worth Rs."+(sum+del));
	}
}
class fastFoodRestaurant extends restaurant
{
	int discount=0;
	public void setDiscount(int d) { this.discount=d;}
	public fastFoodRestaurant(String name,int discount,String address)
	{
	super(name,"Fast Food",address);
	this.discount=discount;
	}
	@Override
	public void startMenu()
	{   
		System.out.println("Welcome "+this.getName());
		System.out.println("1) Add item");
		System.out.println("2) Edit item");
        System.out.println("3) Print Rewards");
        System.out.println("4) Discount on bill value");
        System.out.println("5) Exit");
	}
	@Override
	public void checkOut(app zotato,customer c)
	{
		int del=40;
		double sum=billAfterFoodDiscount(c);
		sum=sum*(100-this.discount)/100;
		System.out.println("Bill after overall rest discount: "+sum);
		if(c.getCategory().equals("Elite") && sum>200) 
		{ 
			sum=sum-50; del=0;
			System.out.println("Bill after elite customer discount:"+sum);
		}
		if(c.getCategory().equals("Elite")) del=0;
		else if(c.getCategory().equals("Special") && sum>200) 
		{ 
			sum=sum-25; 
			del=20;
			System.out.println("Bill after special customer discount:"+sum);
		}   
		if(c.getCategory().equals("Special")) del=20;
		int reward=(10*((int)(sum/150)));
		finalCheck(reward,del,sum,c,zotato);
	}
}
class authenticRestaurant extends restaurant
{
	private int discount;
	public authenticRestaurant(String name,int discount,String address)
	{
	super(name,"authentic",address);
	this.discount=discount;
	}
	public void setDiscount(int d) { this.discount=d;}
	@Override
	public void startMenu()
	{   
		System.out.println("Welcome "+this.getName());
		System.out.println("1) Add item");
		System.out.println("2) Edit item");
        System.out.println("3) Print Rewards");
        System.out.println("4) Discount on bill value");
        System.out.println("5) Exit");
	}
	@Override
	public void checkOut(app zotato,customer c)
	{
		int del=40;
		double sum=billAfterFoodDiscount(c);
		sum=sum*(100-this.discount)/100;
		System.out.println("Bill after overall rest discount:"+sum);
		if(sum>100)
			{
			sum-=50;
			System.out.println("Bill after additional 50/- discount:"+sum);
			}
		if(c.getCategory().equals("Elite") && sum>200) 
		{ 
			
			sum=sum-50; del=0;
			System.out.println("Bill after elite customer discount:"+sum);
		}
		if(c.getCategory().equals("Elite")) del=0;
		else if(c.getCategory().equals("Special") && sum>200) 
		{ 
			sum=sum-25; del=20;
			System.out.println("Bill after special customer discount:"+sum);
		} 
		if(c.getCategory().equals("Special")) del=20;
		int reward=(25*((int)(sum/200)));
		finalCheck(reward,del,sum,c,zotato);
	}
}
