package dao;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.mockito.exceptions.misusing.FriendlyReminderException;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.mysql.jdbc.Statement;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.PayloadBuilder;

import dto.FeedObjects;

public class Project {

	public ArrayList<FeedObjects> GetFeeds(Connection connection)
			throws Exception {


		System.out.println(this.getClass().getName() + " " + "GetFeeds");

		/**
		 * test APNS connectuin
		 */
		System.out.println(this.getClass().getName() + " "
				+ "GetFeeds start test");

		File directory = new File(
				"/Users/vitali/Documents/Client-SIXII-workspace/RESTfulProject/resources"/* "/Users/vitali/Downloads/RESTfulProject/resources" */);

		// get all the files from a directory
		// 123

		File[] fList = directory.listFiles();

		for (File file : fList) {

			System.out.println("file.getName()=" + file.getName());

		}

		ApnsService service = APNS
				.newService()
				.withCert(
						"/Users/vitali/Downloads/RESTfulProject/resources/iphone_dev.p12",
						"uhykh").withSandboxDestination().build();

		// service.start();
		String payload = APNS.newPayload().alertBody("Hi you!!!").build();

		String token = "f0cd4a4f0743cae477399de77a8a21b375cfeb6d11bfa60d813e3b4fb930443e";

		service.push(token, payload);

		System.out.println(this.getClass().getName() + " "
				+ "GetFeeds end test");

		/**
		 * end test APNS connectuin
		 */

		/**
		 * start test android notification
		 * 
		 */
		// Instance of com.android.gcm.server.Sender, that does the
		// transmission of a Message to the Google Cloud Messaging service.
		String SENDER_ID = "AIzaSyANIsNYV7lGBIWfIiQ1yPClId3cY-Hfu-w";
		String ANDROID_DEVICE = /*
								 * "APA91bEvrzIKDAmuTcDDFUxoyIQDmosa40KZ3lZz-0qBGXBL-FP0Ne5NmRBQWyum-iWiwwETDi4mYMbNPuj5izGoIPg7glIEfqp_XNY81uhG6SmJhgMnVvsa7GdiW7DuCxWqG6Nekisi81WzJz8Aeef-FRHUM35-KQ"
								 * ;//
								 * "APA91bEoPUEta5NTiItzrb9UjaL3j65XY_CRHFCG6-BSVmdqg8O1CB-ypaCkI9fyYDrw95cYxjJro8MoQuPuPw9HulcuMXlIgpEX8h42fePS3-sltHSyxd-8KNwl23WzebWOvAdFsBHC3rzFcTYqz62r6v8MNOTAUw"
								 * ;//
								 */"APA91bEoPUEta5NTiItzrb9UjaL3j65XY_CRHFCG6-BSVmdqg8O1CB-ypaCkI9fyYDrw95cYxjJro8MoQuPuPw9HulcuMXlIgpEX8h42fePS3-sltHSyxd-8KNwl23WzebWOvAdFsBHC3rzFcTYqz62r6v8MNOTAUw";// "YOUR_CAPTURED_ANDROID_DEVICE_KEY";

		List<String> androidTargets = new ArrayList<String>();
		androidTargets.add(ANDROID_DEVICE);

		Sender sender = new Sender(SENDER_ID);
		if (sender == null) {
			System.out.println("sender==null");
		} else {
			System.out.println("sender != null");
		}

		// This Message object will hold the data that is being transmitted
		// to the Android client devices. For this demo, it is a simple text
		// string, but could certainly be a JSON object.
		Message message = new Message.Builder()

				// If multiple messages are sent using the same .collapseKey()
				// the android target device, if it was offline during earlier
				// message
				// transmissions, will only receive the latest message for that
				// key when
				// it goes back on-line.
				.collapseKey("test_collapseKey"/* collapseKey */)
				.timeToLive(30).delayWhileIdle(true)
				.addData("message", "eshq?"/* userMessage */).build();

		try {
			// use this for multicast messages. The second parameter
			// of sender.send() will need to be an array of register ids.
			MulticastResult result = sender.send(message, androidTargets, 1);

			if (result.getResults() != null) {
				int canonicalRegId = result.getCanonicalIds();
				if (canonicalRegId != 0) {

				}
			} else {
				int error = result.getFailure();
				System.out.println("Broadcast failure: " + error);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * end test android notification
		 * 
		 */

		ArrayList<FeedObjects> feedData = new ArrayList<FeedObjects>();
		try {
			// String uname = request.getParameter("uname");
			PreparedStatement ps = connection
					.prepareStatement("SELECT userID,userNickName,userEmail  FROM sixeyeGamesUsers"/* "SELECT aps,acme1,acme2 FROM website ORDER BY id DESC" */);
			// ps.setString(1,uname);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				FeedObjects feedObject = new FeedObjects();
				feedObject.setParam(rs.getString("userID"));
				feedObject.setParam1(rs.getString("userNickName"));
				feedObject.setParam2(rs.getString("userEmail"));

				// feedObject.setAps(rs.getString("aps"));
				// feedObject.setAcme1(rs.getString("acme1"));
				// feedObject.setAcme2(rs.getString("acme2"));
				feedData.add(feedObject);
			}
			return feedData;
		} catch (Exception e) {
			throw e;
		}
	}

	public ArrayList<FeedObjects> isUserExist(Connection connection,
			String _userName, String _userPassw) throws Exception {

		System.out.println(this.getClass().getName() + " " + "isUserExist"
				+ " _userName = " + _userName + " , _userPassw =" + _userPassw);

		ArrayList<FeedObjects> feedData = new ArrayList<FeedObjects>();

		try {
			// String uname = request.getParameter("uname");
			PreparedStatement ps = connection
					.prepareStatement("SELECT UserID,userEmail,userPassw from sixeyeGamesUsers WHERE userEmail='"
							+ _userName
							+ "' and userPassw='"
							+ _userPassw
							+ "' and userActiveStatus=1");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				FeedObjects feedObject = new FeedObjects();
				feedObject.setUserid(rs.getString("UserID"));

				feedData.add(feedObject);
			}
			return feedData;
		} catch (Exception e) {
			throw e;
		}
	}

	public ArrayList<FeedObjects> signUp(Connection connection,
			String _userName, String _userPassw, String _deviceType,
			String _deviceID, String _deviceSerialID, String _deviceDesc)
			throws Exception {

		System.out.println(this.getClass().getName() + " signUp \n");

		PreparedStatement ps = connection
				.prepareStatement("SELECT UserID from sixeyeGamesUsers WHERE userEmail='"
						+ _userName + "' and userPassw='" + _userPassw + "'");

		ArrayList<FeedObjects> feedData = new ArrayList<FeedObjects>();
		ResultSet rs = ps.executeQuery();

		FeedObjects feedObject = null;

		if (rs.isBeforeFirst()) {
			System.out.println(" signUp : there are records");

			while (rs.next()) {

				feedObject = new FeedObjects();
				feedObject.setUserid(rs.getString("userID"));

				feedData.add(feedObject);

				// test sixeyeGamesDevices if user exist
				// if true : update device id

				return feedData;

			}

		} else {
			// create a new UserID

			// should be check if empty user already exist

			System.out.println(" signUp : create a new User");

			String sqlQuery = "INSERT INTO MainGames.sixeyeGamesUsers VALUES(null,?,?,?,?) ";

			ps = connection.prepareStatement(sqlQuery,
					PreparedStatement.RETURN_GENERATED_KEYS);

			ps.setString(1, _userName);

			ps.setString(2, _userName);

			ps.setString(3, _userPassw);

			ps.setBoolean(4, true);

			// test if device exist

			int row = 0;
			if (row != 0) {
				System.out.println(" signUp : created a new User");
			} else {
				System.out.println(" signUp : failed to create a new User");
			}
			try {
				row = ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println("e.getMessage() = " + e.getMessage());
			}

			return isUserExist(connection, _userName, _userPassw);
		}

		return null;

	}

	public ArrayList<FeedObjects> getFriendStatus(Connection connection,
			String _userId, String _friendMail, String _gameName)
			throws Exception {
		System.out.println(this.getClass().getName() + " " + "isUserExist"
				+ " _userId = " + _userId + " , _friendMail =" + _friendMail);

		ArrayList<FeedObjects> feedData = new ArrayList<FeedObjects>();

		/**
		 * SELECT sixeyeGamesUsers.userID,sixeyeGamesFriendStatus.status from
		 * sixeyeGamesUsers LEFT JOIN sixeyeGamesFriendStatus ON
		 * sixeyeGamesUsers.userID=sixeyeGamesFriendStatus.friendID WHERE
		 * sixeyeGamesUsers.userEmail = 'test2mail' AND
		 * sixeyeGamesFriendStatus.userID=1 and
		 * sixeyeGamesFriendStatus.gameName='puzzleFace'
		 */

		try {
			String sqlrequest = "SELECT sixeyeGamesUsers.userID,sixeyeGamesFriendStatus.status "
					+ "from sixeyeGamesUsers LEFT JOIN sixeyeGamesFriendStatus ON sixeyeGamesUsers.userID=sixeyeGamesFriendStatus.friendID "
					+ "WHERE sixeyeGamesUsers.userEmail =  '"
					+ _friendMail
					+ "' AND sixeyeGamesFriendStatus.userID="
					+ _userId
					+ " and sixeyeGamesFriendStatus.gameName='"
					+ _gameName
					+ "'";

			PreparedStatement ps = connection.prepareStatement(sqlrequest);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				FeedObjects feedObject = new FeedObjects();
				feedObject.setUserid(rs.getString("UserID"));
				feedObject.setStatus(rs.getString("status"));
				feedData.add(feedObject);
			}
			return feedData;
		} catch (Exception e) {
			throw e;
		}
	}

	public ArrayList<FeedObjects> inviteFriend(Connection connection,
			String _userId, String _friendMail, String _gameName)
			throws Exception {

		System.out.println(this.getClass().getName() + " " + "isUserExist"
				+ " userId = " + _userId + " , friendMail =" + _friendMail);

		ArrayList<FeedObjects> feedData = new ArrayList<FeedObjects>();
		FeedObjects feedObject = new FeedObjects();

		try {
			String sqlrequest_checkstatus = "SELECT count(sixeyeGamesUsers.userID) as count"
					+ " from sixeyeGamesUsers LEFT JOIN sixeyeGamesFriendStatus ON sixeyeGamesUsers.userID=sixeyeGamesFriendStatus.friendID "
					+ " WHERE sixeyeGamesUsers.userEmail =  '"
					+ _friendMail
					+ "' AND sixeyeGamesFriendStatus.userID="
					+ _userId
					+ " AND sixeyeGamesFriendStatus.gameName='"
					+ _gameName
					+ "'  ";

			PreparedStatement ps_checkstatus = connection
					.prepareStatement(sqlrequest_checkstatus);

			ResultSet rs_checkstatus = ps_checkstatus.executeQuery();
			int result_exist_in_relation = 0;

			while (rs_checkstatus.next()) {
				result_exist_in_relation = rs_checkstatus.getInt("count");
			}

			System.out
					.println(this.getClass().getName()
							+ " result_exist_in_relation = "
							+ result_exist_in_relation);

			// friend exist as user , lets update a friendStatus list and
			// send
			// notification

			// System.out.println(this.getClass().getName()
			// +" result_checkstatus = "+result_checkstatus);
			//
			int result_frienduserID = -1;

			// user "friend" exist
			if (result_exist_in_relation == 1) {

				String sqlrequest_frienduserID = "select sixeyeGamesUsers.userID from sixeyeGamesUsers WHERE sixeyeGamesUsers.userEmail='"
						+ _friendMail
						+ "'  AND sixeyeGamesUsers.userActiveStatus=1 ";

				PreparedStatement ps_frienduserID = connection
						.prepareStatement(sqlrequest_frienduserID);
				ResultSet rs_frienduserID = ps_frienduserID.executeQuery();

				while (rs_frienduserID.next()) {
					result_frienduserID = rs_frienduserID.getInt("userID");
				}

				System.out
						.println(this.getClass().getName()
								+ " result_exist_in_relation ==1 :  result_frienduserID = "
								+ result_frienduserID);

				// lets test if two users are exist and friends

				// friend exist in sixeyeGamesUsers and active=1

				if (result_frienduserID !=-1) {

					String sqlrequest_acceptedfriends = "select count(sixeyeGamesFriendStatus.statusid) as count "
							+ "from sixeyeGamesFriendStatus "
							+ "where sixeyeGamesFriendStatus.UserID="
							+ result_frienduserID
							+ " "
							+ "and sixeyeGamesFriendStatus.friendID="
							+ _userId
							+ "  "
							+ "and sixeyeGamesFriendStatus.status='A' "
							+ "AND sixeyeGamesFriendStatus.gameName='"
							+ _gameName
							+ "'  "
							+ "OR "
							+ "sixeyeGamesFriendStatus.UserID="
							+ _userId
							+ " "
							+ "and sixeyeGamesFriendStatus.friendID="
							+ result_frienduserID
							+ "  "
							+ "and sixeyeGamesFriendStatus.status='W' "
							+ "AND sixeyeGamesFriendStatus.gameName='"
							+ _gameName + "' ";

					PreparedStatement ps_acceptedfriends = connection
							.prepareStatement(sqlrequest_acceptedfriends);

					System.out.println(this.getClass().getName()
							+ " result_checkstatus step 1 "+sqlrequest_acceptedfriends);

					ResultSet rs_acceptedfriends = ps_acceptedfriends
							.executeQuery();

					System.out.println(this.getClass().getName()
							+ " result_checkstatus step 2 ");

					int result_acceptedfriends = -1;

					while (rs_acceptedfriends.next()) {
						result_acceptedfriends = rs_acceptedfriends
								.getInt("count");
					}

					System.out.println(this.getClass().getName()
							+ " result_acceptedfriends = "
							+ result_acceptedfriends);

					// the both are accepted and exist all good update a status
					if (result_acceptedfriends == 2) {

						// update record with friendStatus frienduserID and
						// useID to
						// A
						System.out.println(this.getClass().getName()
								+ " result_acceptedfriends == 2");

						String sqlrequest_onefirstfriendareaccepted = "SELECT * from sixeyeGamesFriendStatus where"
								+ " sixeyeGamesFriendStatus.userID="
								+ _userId
								+ " "
								+ "and sixeyeGamesFriendStatus.friendID="
								+ result_frienduserID
								+ " "
								+ "and sixeyeGamesFriendStatus.status='W'";

						java.sql.Statement stmt = connection.createStatement(
								ResultSet.TYPE_SCROLL_SENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
						ResultSet rs_twofriendareaccepted = stmt
								.executeQuery(sqlrequest_onefirstfriendareaccepted);
						rs_twofriendareaccepted.first();
						rs_twofriendareaccepted.updateString("status", "A");
						rs_twofriendareaccepted.updateRow();

						// test if two friend are accepted

						feedObject.setUserid(Integer
								.toString(result_frienduserID));
						feedObject.setStatus("A");

						feedData.add(feedObject);

						return feedData;

						//
						// String sqlrequest_twofriendareaccepted =
						// "SELECT count(*) as count "
						// + "from sixeyeGamesFriendStatus "
						// +
						// "where sixeyeGamesFriendStatus.userID=1 and sixeyeGamesFriendStatus.friendID=2 "
						// +
						// "or sixeyeGamesFriendStatus.userID=2 and sixeyeGamesFriendStatus.friendID=1  "
						// + "and sixeyeGamesFriendStatus.status='A'";
						//
						// PreparedStatement ps_twofriendareaccepted =
						// connection
						// .prepareStatement(sqlrequest_twofriendareaccepted);
						// ResultSet rs_checkstatus_inctiveuserID =
						// ps_twofriendareaccepted
						// .executeQuery();
						// int result_twofriendareaccepted = 0;
						//
						// while (rs_checkstatus_inctiveuserID.next()) {
						// result_twofriendareaccepted =
						// rs_checkstatus_inctiveuserID
						// .getInt("count");
						// }
						//
						// if (result_twofriendareaccepted == 2) {
						//
						//
						//
						//
						//
						// // // notification
						//
						// feedObject.setUserid(Integer.toString(result_frienduserID));
						// feedObject.setStatus("A");
						//
						// feedData.add(feedObject);
						//
						// }
						// else {
						// feedObject.setUserid(Integer.toString(result_frienduserID));
						// feedObject.setStatus("W");
						//
						// feedData.add(feedObject);
						// }
						//
						//
						//
						// return feedData;

					} else {

						feedObject.setUserid(Integer
								.toString(result_frienduserID));
						feedObject.setStatus("W");

						feedData.add(feedObject);

						return feedData;
					}

					// // update sixeyeGamesFriendStatus
					// // set sixeyeGamesFriendStatus.`status`='A'
					// // where sixeyeGamesFriendStatus.`UserID`=1 and
					// // sixeyeGamesFriendStatus.`friendID`=2
					// // AND sixeyeGamesFriendStatus.gameName='puzzleFace'
					//
					// // update record with friendStatus frienduserID and useID
					// // to
					// // A
					// String sqlrequest_onefriendareaccepted =
					// "UPDATE sixeyeGamesFriendStatus SET sixeyeGamesFriendStatus.status='W'"
					// + "  WHERE  sixeyeGamesFriendStatus.friendID="
					// + result_frienduserID
					// + " "
					// + "AND sixeyeGamesFriendStatus.UserID="
					// + _userId
					// + " "
					// + "AND sixeyeGamesFriendStatus.gameName='"
					// + _gameName
					// + "' "
					// +
					// " AND sixeyeGamesFriendStatus.status<>'W' or sixeyeGamesFriendStatus.status<>'A'";
					//
					// System.out.println(this.getClass().getName()
					// + " sqlrequest_onefriendareaccepted="
					// + sqlrequest_onefriendareaccepted);
					//
					// PreparedStatement ps_onefriendareaccepted = connection
					// .prepareStatement(sqlrequest_onefriendareaccepted);
					//
					// ps_onefriendareaccepted
					// .executeUpdate(sqlrequest_onefriendareaccepted);
					//
					// // ResultSet rs_twofriendareaccepted =
					// // ps_onefriendareaccepted
					// // .executeQuery();
					// //
					//
					// // send notification to frienduserID (push notification
					// // or by
					// // fb
					// // // notification?)
					// feedObject.setUserid(Integer.toString(result_frienduserID));
					// feedObject.setStatus("W");
					//
					// feedData.add(feedObject);
					//
					// // test sixeyeGamesDevices if user exist
					// // if true : update device id
					//
					// return feedData;
					// } else if (result_acceptedfriends == 0) {
					//
					// System.out.println(this.getClass().getName()
					// + " result_acceptedfriends==0 ");
					//
					// feedObject.setUserid(Integer.toString(result_frienduserID));
					// feedObject.setStatus("W");
					//
					// feedData.add(feedObject);
					//
					// return feedData;

				} else {
					feedObject.setUserid(Integer.toString(result_frienduserID));
					feedObject.setStatus("E");

					feedData.add(feedObject);
					return feedData;

				}

			} // friend doesn't exist records==0

			else {

				
				//
				 String sqlQuery_newEmptyUser =
				 "INSERT INTO MainGames.sixeyeGamesUsers VALUES(null,?,?,?,?) ";
				
				 PreparedStatement ps = connection.prepareStatement(
				 sqlQuery_newEmptyUser,
				 PreparedStatement.RETURN_GENERATED_KEYS);
				
				 ps.setString(1, "");
				
				 ps.setString(2, _friendMail);
				
				 ps.setString(3, "");
				
				 ps.setBoolean(4, false);
				
				 int row = 0;
				
				 try {
				 row = ps.executeUpdate();
				 } catch (SQLException e) {
				 System.out
				 .println("e.getMessage() = " + e.getMessage());
				 }
				
				 String sqlrequest_frienduserID =
				 "select sixeyeGamesUsers.userID from sixeyeGamesUsers WHERE sixeyeGamesUsers.userEmail='"
				 + _friendMail
				 + "'  AND sixeyeGamesUsers.userActiveStatus=0 ";
				
				 
				 PreparedStatement ps_frienduserID = connection
				 .prepareStatement(sqlrequest_frienduserID);
				 ResultSet rs_frienduserID = ps_frienduserID.executeQuery();
				
				 int newEmptyUserId=-1;
				 while (rs_frienduserID.next()) {
				 newEmptyUserId = rs_frienduserID.getInt("userID");
				 }
				
				 String sqlQuery_newEmptyUserFriendStatus =
				 "INSERT INTO MainGames.sixeyeGamesFriendStatus VALUES(null,?,?,?,?,?) ";
				
				 PreparedStatement ps_newEmptyUserFriendStatus = connection
				 .prepareStatement(
				 sqlQuery_newEmptyUserFriendStatus,
				 PreparedStatement.RETURN_GENERATED_KEYS);
				
				
				 ps_newEmptyUserFriendStatus.setString(1, _userId);
				
				 ps_newEmptyUserFriendStatus
				 .setInt(2, newEmptyUserId);
				 ps_newEmptyUserFriendStatus.setString(3, "E");
				
				
				 java.util.Date date = new Date();
				 ps_newEmptyUserFriendStatus.setDate(4,
				 new java.sql.Date(date.getTime()));
				
				 ps_newEmptyUserFriendStatus.setString(5, _gameName);
				
				 // test if device exist
				
				 int row_empty_user = 0;
				
				 try {
				 row_empty_user = ps_newEmptyUserFriendStatus
				 .executeUpdate();
				 } catch (SQLException e) {
				 System.out.println("e.getMessage() = "
				 + e.getMessage());
				 }
				
				
				 feedObject.setUserid(Integer.toString(newEmptyUserId));
				 feedObject.setStatus("E");
				
				 feedData.add(feedObject);
				
				 return feedData;
				

			}

		} catch (Exception e) {
			e.getStackTrace();
		}

		return null;
	}

	public ArrayList<FeedObjects> challengeFriend(Connection connection,
			String _userId, String _friendMail, String _gameName)
			throws Exception {
		System.out.println(this.getClass().getName() + " " + "challengeFriend"
				+ " _userId = " + _userId + " , _friendMail =" + _friendMail);

		ArrayList<FeedObjects> feedData = new ArrayList<FeedObjects>();

		// try {
		// String sqlrequest =
		// "SELECT sixeyeGamesUsers.userID,sixeyeGamesFriendStatus.status "
		// +
		// "from sixeyeGamesUsers LEFT JOIN sixeyeGamesFriendStatus ON sixeyeGamesUsers.userID=sixeyeGamesFriendStatus.friendID "
		// + "WHERE sixeyeGamesUsers.userEmail =  '"
		// + _friendMail
		// + "' AND sixeyeGamesFriendStatus.userID="
		// + _userId
		// + " and sixeyeGamesFriendStatus.gameName='"
		// + _gameName
		// + "'";
		//
		// PreparedStatement ps = connection.prepareStatement(sqlrequest);
		// ResultSet rs = ps.executeQuery();
		// while (rs.next()) {
		// FeedObjects feedObject = new FeedObjects();
		// feedObject.setUserid(rs.getString("UserID"));
		// feedObject.setStatus(rs.getString("status"));
		// feedData.add(feedObject);
		// }
		// return feedData;
		// } catch (Exception e) {
		// throw e;
		// }
		return null;
	}
}
