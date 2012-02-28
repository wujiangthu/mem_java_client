package org.fastmemclient;

import java.util.*;

public class MemcachedClient {
	private Vector<Server> serverList;
	
public MemcachedClient(){
	serverList = new Vector<Server>();
}

public void AddServer(String ip, Integer port){
	serverList.add(new Server(ip, port));
}

private Client getClient(){
	Server server = selectServer();
	Client client = server.selectClient();
	return client;
}

private Server selectServer(){
	/*should use hash, now just return the first one*/
	return serverList.firstElement();
}

public void set(String key, String value){
	String command = "set"+" "+key+" "+24+" "+0+" "+value.length()+"\r\n";
	String data= value+"\r\n";
	Client client = getClient();
	client.send(command);
	client.send(data);
	String response = client.receive();
	System.out.println("response of set command:"+response);
	client.close();
}

public String get(String key){
	String command = "get "+key+"\r\n";
	Client client = getClient(); 
	client.send(command);            /*send commend to server*/
	String firstReceiveData = client.receive();
	String secondReceiveData = client.receive();
	System.out.println(firstReceiveData);
	System.out.println(secondReceiveData);
	client.close();
	return secondReceiveData;
}

public static void main(String argv[]){
	MemcachedClient client= new MemcachedClient();
	client.AddServer("192.168.1.101",11211);
	client.set("youname","nobody");
	client.get("youname");
}

}
