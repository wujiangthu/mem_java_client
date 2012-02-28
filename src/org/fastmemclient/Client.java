package org.fastmemclient;

import java.net.*;
import java.io.*;

public class Client {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Server server;
	
	public Client(String ip, Integer port, Server server){
		try {
			socket = new Socket(ip, port);
			in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(),true);
			this.server = server;
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void send(String data){
		out.print(data);
		out.flush();
	}
	
	public String receive(){
		try {
			return in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void close(){
		server.addClient(this);
	}
}
