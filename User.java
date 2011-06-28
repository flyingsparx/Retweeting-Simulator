public class User{

	public int id;
	public double threshold;
	public User[] followers;
	public User[] friends;
	public Tweet[] timeline;
	public User[] userTimeline;
	public java.util.ArrayList previousRetweets;
	public Tweet tw;
	
	/*
	* Construct a new User with an integer id and the magnitude to calculate
	* the number of followers for this User from.
	*/
	public User(int i, int followerMagnitude){
		id = i;
		timeline = new Tweet[20];
		userTimeline = new User[20];
		followers = new User[calculateFollowers(followerMagnitude)];
		friends = new User[20];
		threshold = Math.random();
		previousRetweets = new java.util.ArrayList<Tweet>();
	}
	
	/*
	* Method called from constructor. Responsible for generating the number of
	* followers this user is to have, derived from a function of magnitude.
	*/
	private int calculateFollowers(int mag){
		int numFollowers = (int)(Math.random() * mag);
		return numFollowers;
	}
	
	/*
	* Method called to calculate the exact number of non-null followers
	* (since follower array could hold null values)
	*/
	private int getFollowers(){
		int followerCount = 0;
		for(int i = 0; i < followers.length; i++){
			if(followers[i] != null){
				followerCount++;
			}
		}
		return followerCount;
	}
	
	/*
	* Called from a different instance of User.sendTweet(). Receives Tweet
	* from a friend and adds it to the timeline. If it exists, the bottom
	* Tweet in the timeline is discarded and  all others are moved down.
	*/
	public void receiveTweet(Tweet t, User fromUser){
		for(int i = timeline.length-1; i > 0; i--){
			timeline[i] = timeline[i-1];
			userTimeline[i] = userTimeline[i-1];
		}
		timeline[0] = t;
		userTimeline[0] = fromUser;
		t.addViewer(this);
	}	
	
	/*
	* Called from a main program class. Checks timeline array. If a tweet with
	* a quality > User.threshold is found, retweet() is called
	*/
	public void checkTimeline(){
		for(int i = 0; i < timeline.length; i++){
			if(timeline[i] != null && timeline[i].quality >= threshold){
				retweet(timeline[i], userTimeline[i]);
			}
		}
	}
	
	/*
	* Called from a main program class. Generates a new Tweet with this User
	* as the parameter. The tweet is then passed to the sendTweet() method
	* and returns a reference to this new Tweet.
	*/
	public Tweet tweet(){
		Tweet t = new Tweet(this);
		sendTweet(t);
		System.out.println("User "+id+" generating tweet with quality "+t.quality);
		return t;
	}
	
	/*
	* Called from User.checkTimeline(). Method checks if this user has 
	* previously retweeted this tweet, and, if not, passes it to sendTweet()
	*/
	private void retweet(Tweet t, User u){
		if(!getPreviouslyRetweeted(t)){
			System.out.println("User "+id+"(t="+threshold+") retweeting to "+getFollowers()+" users tweet "+t.id+" originally from "+u.id);
			t.addRetweeter(this, u);
			sendTweet(t);
		}
	}
	
	/*
	* Called from this Tweet.retweet() to see if the Tweet has already been 
	* retweeted by this User. Returns true if retweet previously sent.
	*/
	private Boolean getPreviouslyRetweeted(Tweet t){
		for(int i = 0; i < previousRetweets.size(); i++){
			Tweet t2 = (Tweet)previousRetweets.get(i);
			if(t2.id == t.id){
				return true;
			}
		}
		return false;
	}
	
	/*
	* Method calls receiveTweet() for every follower of this User, sending the
	* Tweet to them. Adds this Tweet to the ArrayList of previously sent Tweets.
	*/
	private void sendTweet(Tweet t){
		previousRetweets.add(t);
		for(int i = 0; i < followers.length; i++){
			if(followers[i] != null){
				followers[i].receiveTweet(t, this);
			}
		}
	}	
}