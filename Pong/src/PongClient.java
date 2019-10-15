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
	
	
	static synchronized void connect() {
		try {
			client = new Socket("127.0.0.1", 3141);
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
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	static synchronized PongPacket update(UserPaddle me, Ball myBall) { 
		try {
			//System.out.println(me.y);
			objOutputStream.reset();
			objOutputStream.writeObject(new PongPacket(myBall, me));
			objOutputStream.flush(); //?
			//System.out.println("here 1");
			//PongPacket input = null;
			PongPacket input = (PongPacket) objInputStream.readObject();
			//System.out.println("here 3");

			return input;
		} catch(Exception e) {
			System.out.println("exiting");
			e.printStackTrace();
			System.exit(0);
			//
			return null;
		}
		
	}

}
