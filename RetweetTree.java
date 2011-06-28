/*
* Class representing a retweet tree. Tree consists of an ArrayList of ArrayLists.
* The first, 'tree', holds a set of tree tiers. Each tier is another ArrayList
* of users. Tier 0 is of size 1 and only holds the source user. 
* One more tree made in exactly the same way holds the link to the previous
* retweeter in the chain ('previousRetweeters').
*/

public class RetweetTree{
	
	private User source;
	private Tweet represented;
	private java.util.ArrayList tree, previousRetweeters;
	
	/*
	* Construct a new RetweetTree with the original tweeter as an argument.
	* This User is then made the source User and is the root of the tree.
	* Method initialises tree by creating the ArrayLists representing the main
	* tree and the previous retweeter tree.
	*/
	public RetweetTree(User u, Tweet t){
		source = u;
		tree = new java.util.ArrayList<java.util.ArrayList>();
		previousRetweeters = new java.util.ArrayList<java.util.ArrayList>();
		for(int i = 0; i < 30; i++){
			java.util.ArrayList newTier = new java.util.ArrayList<User>();
			tree.add(newTier);
			java.util.ArrayList newTierRetweeters = new java.util.ArrayList<User>();
			previousRetweeters.add(newTierRetweeters);
		}
		((java.util.ArrayList)tree.get(0)).add(source);
		((java.util.ArrayList)previousRetweeters.get(0)).add(source);
	}
	
	/*
	* Returns the size of the retweet group represented by this tree. I.E.
	* the number of Users responsible for forwarding the Tweet.
	*/
	public int getSize(){
		int size = 0;
		for(int i = 0; i < tree.size(); i++){
			java.util.ArrayList treeTier = (java.util.ArrayList)tree.get(i);
			java.util.ArrayList treeTierPrevious = (java.util.ArrayList)previousRetweeters.get(i);
			if(treeTier.size() == 0){
				return size;
			}
			for(int j = 0; j < treeTier.size(); j++){
				User u = (User)treeTier.get(j);
				User prev = (User)treeTierPrevious.get(j);
				if(u != null){
					size++;
				}
			}
		}
		return size;
	}
	
	/*
	* Returns the maximum path-length of the retweet group represented by this
	* tree. Gives the hypothetical distance travelled by the Tweet.
	*/
	public int getMaximumPathLength(){
		int pathLength = -1;
		for(int i = 0; i < tree.size(); i++){
			java.util.ArrayList treeTier = (java.util.ArrayList)tree.get(i);
			java.util.ArrayList treeTierPrevious = (java.util.ArrayList)previousRetweeters.get(i);
			if(treeTier.size() == 0){
				return pathLength;
			}
			pathLength++;
		}
		return pathLength;
	}
	
	/*
	* Adds a Tweet to the tree. The current retweeter and the previous retweeter
	* in the chain are the arguments. The method calculates the current retweeter's
	* position in the tree using the previous retweeter's position. This
	* position is then passed to addToTree().
	*/
	public void addTweet(User retweeter, User previousRetweeter){
		for(int i = 0; i < tree.size(); i++){
			java.util.ArrayList treeTier = (java.util.ArrayList)tree.get(i);
			for(int j = 0; j < treeTier.size(); j++){
				User u = (User)treeTier.get(j);
				if(u.id == previousRetweeter.id){
					addToTree(retweeter, previousRetweeter, i+1);
					break;
				}
			}
		}
	}
	
	/*
	* Called from addTweet(). The current retweeter, the previous retweeter
	* and the tier to add to the tree are passed. The current retweeter is
	* added to the 'tree' tree and the previous retweeter is added to the
	* 'previousRetweeters' tree.
	*/
	private void addToTree(User user, User previousRetweeter, int tier){
	 	java.util.ArrayList treeTier = (java.util.ArrayList)tree.get(tier);
		treeTier.add(user);
		java.util.ArrayList treeTierPrevious = (java.util.ArrayList)previousRetweeters.get(tier);
		treeTierPrevious.add(previousRetweeter);
	}
	
	/*
	* Method prints, to STDOUT, the current state of the tree. Each new line 
	* represents each tier of the tree (the top one being of size 1 for the 
	* source user). Prints each user out with the previous retweeter in their
	* chain in brackets afterwards. From this, the route down the tree for
	* each chain can be visualised.
	*/
	public void print(){
		for(int i = 0; i < tree.size(); i++){
			java.util.ArrayList treeTier = (java.util.ArrayList)tree.get(i);
			java.util.ArrayList treeTierPrevious = (java.util.ArrayList)previousRetweeters.get(i);
			if(treeTier.size() == 0){
				return;
			}
			for(int j = 0; j < treeTier.size(); j++){
				User u = (User)treeTier.get(j);
				User prev = (User)treeTierPrevious.get(j);
				if(u != null){
					System.out.print(u.id+"("+prev.id+")  ");
				}
			}
			System.out.print("\n");
		}
	}
}