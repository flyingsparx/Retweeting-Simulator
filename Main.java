/*
* Class to initiate simulation. Responsible for:
* - Generating a network of users
* - Giving these users followers from the rest of the network
* - Starting tweets
* - Forcing a timeline check (which could result in retweets)
* Density of the network can be altered by changing the NETWORK_SIZE and 
* FOLLOWER_MAGNITUDE values. A dense network should be low and high respectively.
*/

public class Main{
 
 	private static final int NETWORK_SIZE = 100;
	private static final int FOLLOWER_MAGNITUDE = 20;
	private static final int CYCLES = 10;
  
 	/*
 	* Constructor handles the program code, starts threads, initialises and 
 	* connects network and forces Tweet generation and User timeline-checking.
 	*/
 	public Main(){
 		Network network = new Network(NETWORK_SIZE, FOLLOWER_MAGNITUDE);
 		network.connectNetwork();
 		network.generateTweet(null);
 		
 		System.out.println("ITERATION 1");
 		network.checkTimelines();
 		Tweet t = network.getTweets()[0];
 		t.printTree();
 		System.out.println("SIZE = "+t.getSize()+", MAX P-L = "+t.getMaximumPathLength());
 		
 		System.out.println("ITERATION 2");
 		network.checkTimelines();
 		t = network.getTweets()[0];
 		t.printTree();
 		System.out.println("SIZE = "+t.getSize()+", MAX P-L = "+t.getMaximumPathLength());

 		
 		System.out.println("ITERATION 3");
 		network.checkTimelines();
 		t = network.getTweets()[0];
 		t.printTree();
 		System.out.println("SIZE = "+t.getSize()+", MAX P-L = "+t.getMaximumPathLength());

	}	
	
	/*
	* Thread to run for CYCLES number of times. Finds a random User from the
	* network and generates a new Tweet from this user.
	*/
	/*class Tweeter extends Thread{
		public void run(){
			int counter = 0; 
			while(counter < CYCLES){
				try{sleep(1000);}catch(Exception e){}
				int randomIndex = (int)(Math.random() * NETWORK_SIZE);
				array[randomIndex].tweet();
				counter++;
			}
		}
	}
	
	/*
	* Thread to periodically force each User in the network to check their
	* timelines. If a Tweet with sufficient quality is found, the User retweets
	* the Tweet to its followers.
	*/
	/*class TimelineChecker extends Thread{
		public void run(){
			while(true){
				try{sleep(3000);}catch(Exception e){}
				for(int i = 0; i < array.length; i++){
					array[i].checkTimeline();
				}
			}
		}
	}			
	
	/*
	* Main method to run the program. Creates a new instance of Main.
	*/
	public static void main(String [] args){		
		System.out.println("--START--");
		new Main();
		System.out.println("--END--");
	}
}