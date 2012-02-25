package org.fastmemclient;
import java.io.*;
import java.net.*;

public class MemcachedClient {
	private String server;
	private String port;
	private Socket socket;
	BufferedReader in;
	PrintWriter out;
	
public MemcachedClient(String ip, String port){
	server=ip;
	this.port=port;
	connect(ip, port);
}

public void set(String key, String value){
	String command = "set"+" "+key+" "+24+" "+0+" "+value.length()+"\r\n";
	String data= value+"\r\n";
	out.print(command);
	out.print(data);
	out.flush();
	try {
		String response=in.readLine();
		System.out.println("response of set command:"+response);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}

public void get(String key){
	String command = "get "+key+"\r\n";
	out.print(command);
	out.flush();
	try {
		System.out.println(in.readLine());
		System.out.println(in.readLine());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

private void connect(String ip, String port){
    System.out.println("here12345678");
	try {
		socket = new Socket(ip, Integer.valueOf(port));
		in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(),true);
		
		
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

public static void main(String argv[]){
	MemcachedClient client= new MemcachedClient("192.168.1.101","11211");
	client.set("myname","wujiang");
	client.get("myname");
}

}
