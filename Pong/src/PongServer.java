import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/* 
 * Pong Packet in 
 * double xPosP, double yPosP, double width, double height
 * 
 * Pong Packet out
 * double xPosP, double yPosP, double width, double height, double xPosB, double yPosB 
 */


public class PongServer {
	static ServerSocket server;
	static Socket[] clients = new Socket[2];
	
	static ObjectOutputStream[] objOutputStreams = new ObjectOutputStream[2];
	static ObjectInputStream[] objInputStreams = new ObjectInputStream[2];

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			
			 server = new ServerSocket(3141);
			 for(int i = 0; i < 2; i++) {
				 clients[i] = server.accept();
				 System.out.println("picked up client");
				 //inputStreams[i] = new DataInputStream(clients[i].getInputStream());
				 //outputStreams[i] = new DataOutputStream(clients[i].getOutputStream());
				 objOutputStreams[i] = new ObjectOutputStream(clients[i].getOutputStream());
				 objInputStreams[i] = new ObjectInputStream(clients[i].getInputStream());
				 
			 }
			 
			 for(int i = 0; i < 2; i++) {
				 objOutputStreams[i].writeInt(3476); //magic number
				 objOutputStreams[i].flush();
	        	 //System.out.println(((PongPacket) objInputStreams[0].readObject()).ball.x);

				 System.out.println("writing magic number");

			 }
	         
	         while(true) {
	        	// System.out.println("here0");
	        	 PongPacket client1 = (PongPacket) objInputStreams[0].readObject();
	        	 //System.out.println("here1");
	        	 PongPacket client2 = (PongPacket) objInputStreams[1].readObject();
	        	 System.out.println(client1.paddle.y + " _ " + client2.paddle.y);
	        	 PongPacket packetFor1 = new PongPacket(client1.ball, client2.paddle);
	        	 PongPacket packetFor2 = new PongPacket(client1.ball, client1.paddle);
	 			 objOutputStreams[0].reset();
	 			 objOutputStreams[1].reset();

	        	 objOutputStreams[0].writeObject(packetFor1);
	        	 objOutputStreams[1].writeObject(packetFor2);
	        	 objOutputStreams[0].flush(); //?
	        	 objOutputStreams[1].flush();


	         }

		} catch(Exception e) {
			
		}

	}
}
