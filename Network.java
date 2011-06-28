/*
* Class representing a Twitter network. Holds references to all Tweets and 
* Users and their links within the Network. Functions for generating a Tweet
* and for checking the timelines.
*/

public class Network{

	private User[] network;
	private java.util.ArrayList tweets;

	/*
	* Construct a new Network with a given size and follower magnitude.
	* The larger the magnitude, the more followers each user has.
	*/
	public Network(int size, int followerMagnitude){
		network = new User[size];
		for(int i = 0; i < size; i++){
			User u = new User(i+1, followerMagnitude);
			network[i] = u;
		}
		tweets = new java.util.ArrayList<Tweet>();
	}
	
	/*
	* Introduces a new Tweet to the network. This Tweet can be from a specified
	* User, or, if user = null, a random one is chosen from the Network. The
	* newly created Tweet is added to the ArrayList 'tweets'.
	*/
	public void generateTweet(User user){
		if(user == null){
			int randomIndex = (int)(Math.random() * network.length);
			user = network[randomIndex];
		}
		Tweet t = user.tweet();
		tweets.add(t);
	}
	
	/*
	* Forces each User in the Network to check their timelines. This could 
	* result in retweets.
	*/
	public void checkTimelines(){
		for(int i = 0; i < network.length; i++){
			network[i].checkTimeline();
		}
	}
	
	/*
	* Returns an array of Tweets produced in this Network in order of creation.
	*/
	public Tweet[] getTweets(){
		Tweet[] tweetArray = new Tweet[tweets.size()];
		for(int i = 0; i < tweets.size(); i++){
			tweetArray[i] = (Tweet)tweets.get(i);
		}
		return tweetArray;
	}
	
	/*
	* Randomly connects the Network by assigning a set of random Users to follow
	* each User in the Network. The number of followers of each User is 
	* generated (from the follower magnitude) in the constructor of User.
	*/
	public void connectNetwork(){
		User[] temp = new User[network.length];
		for(int i = 0; i < network.length; i++){
			temp[i] = network[i];
		}
		for(int i = 0; i < network.length; i++){
			User u = network[i];
			java.util.Collections.shuffle(java.util.Arrays.asList(temp));
			for(int j = 0; j < u.followers.length; j++){
				if(u.id != temp[j].id){
					u.followers[j] = temp[j];
				}
				/*if(u.id == temp[j].id){
					j--;
				}*/
			}
		}
	}
}	