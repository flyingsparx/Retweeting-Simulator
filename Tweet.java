public class Tweet{
	
	public double id;
	public User sourceUser;
	public double quality;
	public Bag uniqueViewers;
	public int uniqueSize;
	public int overhead;
	public RetweetTree rt;
	
	/* 
	* Constructs a new Tweet object with the User who created it. Sets a 
	* random quality and id based on the source User's id.
	*/
	public Tweet(User u){
		sourceUser = u;
		id = sourceUser.id+(Math.random());
		quality = Math.random();
		uniqueViewers = new Bag();
		rt = new RetweetTree(u, this);
	}
	
	/*
	* Called from User when this Tweet is received. Adds to a Bag of Users
	* so that the distinct audience size and overhead can be calculated.
	*/
	public void addViewer(User u){
		uniqueViewers.addItem(u);
		uniqueSize = uniqueViewers.getSize();
		overhead = uniqueViewers.getOverhead();
	}
	
	/*
	* Called from the User when this Tweet is retweeted. User is added to 
	* the ArrayList of retweeters.
	*/
	public void addRetweeter(User retweeter, User previousRetweeter){
		rt.addTweet(retweeter, previousRetweeter);
	}
	
	/*
	* Return the current size of this Tweet's retweet group.
	*/
	public int getSize(){
		return rt.getSize();
	}
	
	/*
	* Return the current maximum path-length of this retweet group.
	*/
	public int getMaximumPathLength(){
		return rt.getMaximumPathLength();
	}
	
	/*
	* Method to print the current state of this Tweet's retweet tree to STDOUT
	*/
	public void printTree(){
		rt.print();
	}
}