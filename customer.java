import java.util.*;
class customer implements USER
{
	private final String name;
	private final String city;
	private double wallet;
	private double reward;
	private final String category;
	Map<foodItem,Integer> cart=new HashMap<>();
	Queue<order> orders=new LinkedList<>();
	public String getCategory() {return this.category;}
	public String getName() {return this.name;}
	@Override
	public double getReward() {return this.reward;}
	@Override
	public void setreward(double r) {this.reward+=r;}
	public double getWallet() {return this.wallet;}
	public void setWallet(double d) {this.wallet-=d;}
	public customer(String name,String city,String category)
	{
		this.name=name;
		this.city=city;
		this.category=category;
		this.wallet=1000;
	}
	@Override
	public void pDetail() 
	{
		System.out.println(this.name+"("+this.category+")"+","+this.city+","+this.wallet);
	}
	@Override
	public void startMenu()
	{
		System.out.println("Welcome "+this.name);
		System.out.println("Customer Menu");
		System.out.println("1) Select Restaurant");
		System.out.println("2) checkout cart");
		System.out.println("3) Reward won");
		System.out.println("4) print the recent orders");
		System.out.println("5) Exit");
	}
	public void menuAfterRSelect()
	{
		System.out.println("Welcome "+this.name);
		System.out.println("Customer Menu");
		System.out.println("1) Search item");
		System.out.println("2) checkout cart");
		System.out.println("3) Reward won");
		System.out.println("4) print the recent orders");
		System.out.println("5) Exit");
		
	}
	public void printOrders()
	{
		for(order o: orders) o.pDetails();
	}
	public void addToCart(app zotato,restaurant r,int q,int i)
	{
		foodItem f=(foodItem)(zotato.food.get(i));
		if(f.getQuantity()>=q)
			{
			cart.put(f,cart.getOrDefault(f,0)+q);
			f.setItemQuantity(f.getQuantity()-q);
			System.out.println("added to cart");
			
			for(Map.Entry m:cart.entrySet())  
		     {  
		        System.out.println(((foodItem)m.getKey()).getName()+" "+m.getValue());   
		     }
			
			}
		else 
		{
			System.out.println("Invalid");
		}

	}
	
}



