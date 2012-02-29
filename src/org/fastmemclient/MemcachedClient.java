package org.fastmemclient;

import java.util.*;

public class MemcachedClient {
	private static Vector<Server> serverList = null;
	
public MemcachedClient(){
	if(null == serverList){
	  serverList = new Vector<Server>();
	  }
}

public void AddServer(String ip, Integer port){
	serverList.add(new Server(ip, port));
}

private Client getClient(String key){
	Server server = selectServer(key);
	Client client = server.selectClient();
	return client;
}

private Server selectServer(String key){
	/*should use hash, now just return the first one*/
	return serverList.firstElement();
}

public void set(String key, String value){
	String command = "set"+" "+key+" "+24+" "+0+" "+value.length()+"\r\n";
	String data= value+"\r\n";
	Client client = getClient(key);
	client.send(command);
	client.send(data);
	String response = client.receive();
	System.out.println("response of set command:"+response);
	client.close();
}

public String get(String key){
	String command = "get "+key+"\r\n";
	Client client = getClient(key); 
	client.send(command);            /*send commend to server*/
	String firstReceiveData = client.receive();
	String secondReceiveData = client.receive();
	System.out.println(firstReceiveData);
	System.out.println(secondReceiveData);
	client.close();
	return secondReceiveData;
}

public void delete(String key){
	/*consider using enum or contant to replace hard code for command*/
	String command = "delete"+" "+key+" "+"\r\n";
	Client client = getClient(key); 
	client.send(command);    
	String response = client.receive();
	System.out.println("response of delete command:"+response);
	client.close();	
}

public static void main(String argv[]){
	MemcachedClient client= new MemcachedClient();
	client.AddServer("192.168.1.101",11211);
	client.set("youname","nobody");
	client.get("youname");
}

}
