import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class PongClient {
	static Socket client;
	static ObjectOutputStream objOutputStream;
	static ObjectInputStream objInputStream;
	static PongPacket lastRecieved;
	static boolean packetAvailable = false;
	static Thread tcpThread;
	
	static PongPacket getLast() {
		
		if(packetAvailable) {
			packetAvailable = false;
			return lastRecieved;
		} else {
			return null;
		}
	}
	
	
	static synchronized void connect(final PongScene scene) {
		try { //72.219.152.42
			client = new Socket("72.219.152.42", 3141);
			client.setTcpNoDelay(true);
			//client.conn
			//while(!client.isConnected()) System.out.println("isConnecting" + client.isConnected());
	
			objOutputStream = new ObjectOutputStream(client.getOutputStream());
			objInputStream = new ObjectInputStream(client.getInputStream());
			
			System.out.println("trying to read magic num");
			int magicNum = objInputStream.readInt();
			System.out.println("got magic num: ");
			System.out.println(magicNum);
			//objOutputStream.writeObject(new PongPacket(new Ball(3476, 0, 0, 0), null));
			//System.exit(0);
			//objInputStream.
			tcpThread = new Thread(new Runnable() {

				@Override
				public void run() {
					while(true) {
					//System.out.println("looping");
					
					try {
						//System.out.println("looping");
						GameObject g = scene.gameObjects[0];
						objOutputStream.reset();
						Paddle me = new Paddle(g.x, g.y, g.width, g.height);
						me.speed = g.speed;
						me.theta = g.theta;
						me.deflectionDir = ((Paddle)g).deflectionDir;
					
						//if(scene.master) System.out.println("packet data " + scene.gameObjects[1].x);
						objOutputStream.writeObject(new PongPacket((Ball)(scene.gameObjects[1]), me, scene.master));
						objOutputStream.flush(); //?
						//System.out.println("here 1");
						//PongPacket input = null;
						lastRecieved = (PongPacket) objInputStream.readObject();
						packetAvailable = true;
						//System.out.println("here 2");
						//Thread.sleep(Constants.GamePeriod);

						
					} catch(Exception e) {
						System.out.println("exiting");
						e.printStackTrace();
						System.exit(0);
						//
						
					}
					}
					
				} 
			});
			tcpThread.start();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	


}