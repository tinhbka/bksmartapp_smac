package com.bksmart.smarttalk.models;

public class ItemChat {
	private int id;
	private long time;
	private String content;
	private int id_user_send;
	private int id_user_receice;
	
	public ItemChat(){
		id = 0;
		time = 0;
		content = "";
		id_user_send = 0;
		id_user_receice = 0;
	}
	
	public int getId_user_receice() {
		return id_user_receice;
	}

	public void setId_user_receice(int id_user_receice) {
		this.id_user_receice = id_user_receice;
	}

	public ItemChat(ItemChat itemChat){
		this.id = itemChat.getId();
		this.time = itemChat.getTime();
		this.content = itemChat.getContent();
		this.id_user_send = itemChat.getId_user_send();
		this.id_user_receice = itemChat.getId_user_receice();
	}
	
	public ItemChat(int id, long time,String content,int id_user_send,int id_user_receice){
		this.id = id;
		this.time = time;
		this.content = content;
		this.id_user_send = id_user_send;
		this.id_user_receice = id_user_receice;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getId_user_send() {
		return id_user_send;
	}
	public void setId_user_send(int id_user) {
		this.id_user_send = id_user;
	}
	
}
