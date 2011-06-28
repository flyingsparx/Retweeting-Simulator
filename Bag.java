public class Bag{

    private java.util.ArrayList bag;
    private int overhead;

    public Bag(){
		bag = new java.util.ArrayList<User>();
        overhead = 0;
    }

    public int getSize(){
        return bag.size();
    }

    public int getOverhead(){
        return overhead;
    }

	public void addItem(User u){
		for(int i = 0; i < bag.size(); i++){
            User currentItem = (User)bag.get(i);
            if(u == currentItem){
                overhead++;
                return;
            }
        }
      	bag.add(u);
	} 
	
	public java.util.ArrayList getUsers(){
		return bag;
	}
}
