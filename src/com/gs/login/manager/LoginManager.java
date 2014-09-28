package com.gs.login.manager;

public class LoginManager {
	private final static class LoginManagerHolder{
		private final static LoginManager instance = new LoginManager();
	}
	
	public static LoginManager getInstance(){
		return LoginManagerHolder.instance;
	}
	
	public void register(){
		
	}
	
	
}
