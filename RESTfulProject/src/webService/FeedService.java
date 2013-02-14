package webService;

import java.util.ArrayList;
import java.util.HashSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.json.simple.JSONObject;

import model.ProjectManager;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import dto.FeedObjects;

@Path("/WebService")
public class FeedService {
	
	
	
	@POST
	@Path("/challengeFriend")
	// @Consumes("application/json")
	// @Produces("application/json")
	public String postXMLchallengeFriend(@FormParam("userId") String _userId,
			@FormParam("friendMail") String _friendMail ,@FormParam("gameName") String _gameName) {

		System.out.println(this.getClass().getName()
				+ " challengeFriend getFriendStatus  _userId :" + _userId + "\n");
		System.out.println(this.getClass().getName()
				+ " challengeFriend  getFriendStatus _friendMail :" + _friendMail + "\n");
		System.out.println(this.getClass().getName()
				+ " challengeFriend  getFriendStatus _gameName :" + _gameName + "\n");
		

		String feeds = null;
		try {

			ArrayList<FeedObjects> feedData = null;
			ProjectManager projectManager = new ProjectManager();
			feedData = projectManager.challengeFriend(_userId, _friendMail,_gameName);
			// StringBuffer sb = new StringBuffer();
			Gson gson = new Gson();
			System.out.println(gson.toJson(feedData));
			feeds = gson.toJson(feedData);

		} catch (Exception e) {
			System.out.println("postXMLchallengeFriend error");
		}
		return feeds;
	}
	
	@POST
	@Path("/inviteFriend")
	// @Consumes("application/json")
	// @Produces("application/json")
	public String postXMLinviteFriend(@FormParam("userId") String _userId,
			@FormParam("friendMail") String _friendMail ,@FormParam("gameName") String _gameName) {

		System.out.println(this.getClass().getName()
				+ " postXMLisUserExist getFriendStatus  userId :" + _userId + "\n");
		System.out.println(this.getClass().getName()
				+ " postXMLisUserExist  getFriendStatus friendMail :" + _friendMail + "\n");
		System.out.println(this.getClass().getName()
				+ " postXMLisUserExist  getFriendStatus gameName :" + _gameName + "\n");
		

		String feeds = null;
		try {

			ArrayList<FeedObjects> feedData = null;
			ProjectManager projectManager = new ProjectManager();
			feedData = projectManager.inviteFriend(_userId, _friendMail,_gameName);
			// StringBuffer sb = new StringBuffer();
			Gson gson = new Gson();
			System.out.println(gson.toJson(feedData));
			feeds = gson.toJson(feedData);

		} catch (Exception e) {
			System.out.println("postXMLinviteFriend error");
		}
		return feeds;
	}
	
	@POST
	@Path("/getFriendStatus")
	// @Consumes("application/json")
	// @Produces("application/json")
	public String postXMLgetFriendStatus(@FormParam("userId") String _userId,
			@FormParam("friendMail") String _friendMail,@FormParam("gameName") String _gameName) {

		System.out.println(this.getClass().getName()
				+ " postXMLisUserExist getFriendStatus  _userId :" + _userId + "\n");
		System.out.println(this.getClass().getName()
				+ " postXMLisUserExist  getFriendStatus _friendMail :" + _friendMail + "\n");

		System.out.println(this.getClass().getName()
				+ " postXMLisUserExist  getFriendStatus _gameName :" + _gameName + "\n");
		

		String feeds = null;
		try {

			ArrayList<FeedObjects> feedData = null;
			ProjectManager projectManager = new ProjectManager();
			feedData = projectManager.getFriendStatus(_userId, _friendMail,_gameName);
			// StringBuffer sb = new StringBuffer();
			Gson gson = new Gson();
			System.out.println(gson.toJson(feedData));
			feeds = gson.toJson(feedData);

		} catch (Exception e) {
			System.out.println("postXMLgetFriendStatus error");
		}
		return feeds;
	}
		
	@POST
	@Path("/signUp")
	// @Consumes("application/json")
	// @Produces("application/json")
	public String postXMLsignUp(@FormParam("userName") String _userName,
			@FormParam("userPassw") String _userPassw,
			@FormParam("deviceType") String _deviceType,
			@FormParam("deviceID") String _deviceID,
			@FormParam("deviceSerialID") String _deviceSerialID,
			@FormParam("deviceDesc") String _deviceDesc) {

		System.out.println(this.getClass().getName()
				+ " postXMLisUserExist  userName :" + _userName + "\n");
		System.out.println(this.getClass().getName()
				+ " postXMLisUserExist  userPassw :" + _userPassw + "\n");
		System.out.println(this.getClass().getName()
				+ " postXMLisUserExist  deviceType :" + _deviceType + "\n");
		System.out.println(this.getClass().getName()
				+ " postXMLisUserExist  deviceID :" + _deviceID + "\n");

		System.out.println(this.getClass().getName()
				+ " postXMLisUserExist  _deviceSerialID :" + _deviceSerialID + "\n");
		System.out.println(this.getClass().getName()
				+ " postXMLisUserExist  _deviceDesc :" + _deviceDesc + "\n");
		
		String feeds = null;
		try {

			ArrayList<FeedObjects> feedData = null;
			ProjectManager projectManager = new ProjectManager();
			feedData = projectManager.signUp(_userName, _userPassw,
					_deviceType, _deviceID,_deviceSerialID,_deviceDesc);
			// StringBuffer sb = new StringBuffer();
			Gson gson = new Gson();
			System.out.println(gson.toJson(feedData));
			feeds = gson.toJson(feedData);

		} catch (Exception e) {
			System.out.println("postXMLisUserExist error");
		}
		return feeds;
	}

	@POST
	@Path("/isUserExist")
	// @Consumes("application/json")
	// @Produces("application/json")
	public String postXMLisUserExist(@FormParam("userName") String _userName,
			@FormParam("userPassw") String _userPassw) {

		System.out.println("postXMLisUserExist  userName :" + _userName + "\n");

		System.out.println("postXMLisUserExist  userPassw :" + _userPassw
				+ "\n");

		String result = "-1";
		try {

			ProjectManager projectManager = new ProjectManager();
			result = projectManager.isUserExist(_userName, _userPassw);
			
			

		} catch (Exception e) {
			System.out.println("postXMLisUserExist error");
		}
		return result;
	}

	@POST
	@Path("/logIn")
	// @Consumes("application/json")
	// @Produces("application/json")
	public String postLogIn(@FormParam("userName") String _userName,
			@FormParam("userPassw") String _userPassw,
			@FormParam("userRegType") String _userRegType) {

		System.out.println("postXMLisUserExist  userName :" + _userName + "\n");

		System.out.println("postXMLisUserExist  userPassw :" + _userPassw
				+ "\n");
		
		System.out.println("postXMLisUserExist  userRegType :" + _userRegType
				+ "\n");
		
		String result  = null;
		try {			
			ProjectManager projectManager = new ProjectManager();
			result = projectManager.logIn(_userName, _userPassw, _userRegType);
			// StringBuffer sb = new StringBuffer(); 
			//Gson gson = new Gson();
			System.out.println(result);
			//feeds = gson.toJson(feedData);

		} catch (Exception e) {
			System.out.println("postXMLlogIn error");
		}
		return result;
	}
	
	@POST
	@Path("/signUpViaEmail")
	// @Consumes("application/json")
	// @Produces("application/json")
	public String signUpViaEmail(@FormParam("userName") String _userName,
			@FormParam("userPassw") String _userPassw) {

		System.out.println("postXMLisUserExist  userName :" + _userName + "\n");

		System.out.println("postXMLisUserExist  userPassw :" + _userPassw
				+ "\n");
				
		String result  = null;
		try {			
			ProjectManager projectManager = new ProjectManager();
			result = projectManager.signUpViaEmail(_userName, _userPassw);
			
			System.out.println(result);

		} catch (Exception e) {
			System.out.println("postXMLlogIn error");
		}
		return result;
	}
	
	@POST
	@Path("/getNonInvited")
	public String getNonInvited(@FormParam("usersList") String _usersList) {

		System.out.println("postXMLisUserExist  usersList :" + _usersList + "\n");

		
				
		String result  = null;
		try {			
			ProjectManager projectManager = new ProjectManager();
			result = projectManager.getNonInvited(_usersList);
			
			System.out.println(result);

		} catch (Exception e) {
			System.out.println("postXMLlogIn error");
		}
		return result;
	} 
	// @GET
	// @Path("/GetFeeds")
	// @Produces("application/json")
	// public String feed(/*@QueryParam("userId") String User_Id*/) {
	//
	// // System.out.println("GET User_Id = "+User_Id);
	//
	// String feeds = null;
	// try {
	// System.out.println(this.getClass().getName() + " " +
	// "FeedService hello");
	// ArrayList<FeedObjects> feedData = null;
	// ProjectManager projectManager = new ProjectManager();
	// feedData = projectManager.GetFeeds();
	// // StringBuffer sb = new StringBuffer();
	// Gson gson = new Gson();
	// System.out.println(gson.toJson(feedData));
	// feeds = gson.toJson(feedData);
	//
	// } catch (Exception e) {
	// System.out.println("error");
	// }
	// return feeds;
	// }

	@POST
	@Path("/jsontest/{device}")
	@Consumes("application/json")
	@Produces("text/plain")
	// @Produces({MediaType.TEXT_PLAIN, "application/x-www-form-urlencoded"})
	// @Consumes({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML,
	// MediaType.APPLICATION_JSON, MediaType.TEXT_HTML,
	// "application/x-www-form-urlencoded"})
	public String getjson(@PathParam("device") String device) {

		System.out.println("getjson  device:" + device);

		JSONObject object = new JSONObject();
		try {
			object.put("name", "Jack Hack");
			object.put("score", new Integer(200));
			object.put("current", new Double(152.32));
			object.put("nickname", "Hacker");
		} catch (JsonIOException e) {
			e.printStackTrace();
		}
		return object.toString();

	}

	@POST
	@Path("/xmltest")
	// @Consumes("application/json")
	// @Produces("application/json")
	public String postOnlyXML(@FormParam("userName") String userName,
			@FormParam("userPassw") String userPassw) {

		System.out.println("postOnlyXML  userId :" + userName + "\n");

		System.out.println("postOnlyXML  userPassw :" + userPassw + "\n");

		// here is the XML file....enjoy.
		if (userName == null) {
			return "trt tr userName==null ";
		}
		return "trtr not null User_Id = " + userName + " , userPassw = "
				+ userPassw;
	}
}
