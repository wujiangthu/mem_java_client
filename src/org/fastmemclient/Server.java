package org.fastmemclient;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class Server {
	private String ip;
	private Integer port;
	private Vector<Client> availableClientList;

	public Server(String ip, Integer port){
		this.ip = ip;
		this.port = port;
		availableClientList = new Vector<Client>();
	}
	
	public Client selectClient(){
		if(true == availableClientList.isEmpty())
		{
			return new Client(ip, port, this);
		}else{
			return availableClientList.remove(0);
		}
	}
	
	public void addClient(Client client){
		availableClientList.add(client);
	}
}
