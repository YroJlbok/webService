package model;

import java.sql.Connection;
import java.util.ArrayList;

import dao.Database;

import dao.Project;
import dto.FeedObjects;

public class ProjectManager {

	public String isUserExist(String _userName,
			String _userPassw) throws Exception {
		String result = "-1";
		
		try {
			Database database = new Database();
			Connection connection = database.Get_Connection();
			Project project = new Project();
			result = project.isUserExist(connection, _userName, _userPassw);

		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	
	
	public String logIn(String _userName,
			String _userPassw,
			String _userRegType ) throws Exception {

		// System.out.println(this.getClass().getName() + " " + "isUserExist");
		String result = "-1";
		try {
			Database database = new Database();
			Connection connection = database.Get_Connection();
			Project project = new Project();
			result = project.logIn(connection, _userName, _userPassw, _userRegType);

		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	public String signUpViaEmail(String _userName,
			String _userPassw) throws Exception {

		// System.out.println(this.getClass().getName() + " " + "isUserExist");
		String result = "-1";
		try {
			Database database = new Database();
			Connection connection = database.Get_Connection();
			Project project = new Project();
			result = project.signUpViaEmail(connection, _userName, _userPassw);

		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public String getNonInvited(String _usersList) throws Exception {

		// System.out.println(this.getClass().getName() + " " + "isUserExist");
		String result = "";
		try {
			Database database = new Database();
			Connection connection = database.Get_Connection();
			Project project = new Project();
			result = project.getNonInvited(connection, _usersList);

		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	public ArrayList<FeedObjects> signUp(String _userName, String _userPassw,
			String _deviceType, String _deviceID, String _deviceSerialID,
			String _deviceDesc) throws Exception {

		System.out.println(this.getClass().getName() + " signUp \n");

		ArrayList<FeedObjects> feeds = null;
		try {
			Database database = new Database();
			Connection connection = database.Get_Connection();
			Project project = new Project();
			feeds = project.signUp(connection, _userName, _userPassw,
					_deviceType, _deviceID, _deviceSerialID, _deviceDesc);

		} catch (Exception e) {
			throw e;
		}
		return feeds;
	}

	public ArrayList<FeedObjects> getFriendStatus(String _userId,
			String _friendMail, String _gameName) throws Exception {
		System.out.println(this.getClass().getName() + " getFriendStatus \n");

		ArrayList<FeedObjects> feeds = null;
		try {
			Database database = new Database();
			Connection connection = database.Get_Connection();
			Project project = new Project();
			feeds = project.getFriendStatus(connection, _userId, _friendMail,_gameName);

		} catch (Exception e) {
			throw e;
		}
		return feeds;
	}

	public ArrayList<FeedObjects> inviteFriend(String _userId,
			String _friendMail, String _gameName) throws Exception {
		System.out.println(this.getClass().getName() + " inviteFriend \n");

		ArrayList<FeedObjects> feeds = null;
		try {
			Database database = new Database();
			Connection connection = database.Get_Connection();
			Project project = new Project();
			feeds = project.inviteFriend(connection, _userId, _friendMail,_gameName);

		} catch (Exception e) {
			throw e;
		}
		return feeds;
	}

	public ArrayList<FeedObjects> challengeFriend(String _userId,
			String _friendMail, String _gameName) throws Exception{
		System.out.println(this.getClass().getName() + " challengeFriend \n");

		ArrayList<FeedObjects> feeds = null;
		try {
			Database database = new Database();
			Connection connection = database.Get_Connection();
			Project project = new Project();
			feeds = project.challengeFriend(connection, _userId, _friendMail,_gameName);

		} catch (Exception e) {
			throw e;
		}
		return feeds;
	}

	
}
