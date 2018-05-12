package com.github.microkibaco.bearlive.action;


import com.github.microkibaco.bearlive.server.ResponseObject;
import com.github.microkibaco.bearlive.server.RoomInfo;
import com.github.microkibaco.bearlive.server.SqlManager;
import com.github.microkibaco.bearlive.server.Error;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateRoomAction extends IAction {

	private static final String RequestParamKey_UserId = "userId";
	private static final String RequestParamKey_UserAvatar = "userAvatar";
	private static final String RequestParamKey_UserName = "userName";
	private static final String RequestParamKey_LiveTitle = "liveTitle";
	private static final String RequestParamKey_LiveCover = "liveCover";

	@Override
	public void doAction(HttpServletRequest req, HttpServletResponse response) throws IOException, SQLException {
		Connection dbConnection = null;
		Statement stmt = null;
		try {
			String userId = getParam(req, RequestParamKey_UserId, "");
			String userName = getParam(req, RequestParamKey_UserName, "");
			String userAvatar = getParam(req, RequestParamKey_UserAvatar, "");
			String liveTitle = getParam(req, RequestParamKey_LiveTitle, "");
			String liveCover = getParam(req, RequestParamKey_LiveCover, "");
			
			dbConnection = SqlManager.getConnection();
			stmt = dbConnection.createStatement();
			// ���ݿ���Ѿ���ȫ���������ˡ�
			
			//��ɾ��֮ǰ����û��ɾ���ķ����
			QuitRoomAction.createrQuit( userId, null);
			
			String sqlStr = "INSERT INTO `RoomInfo`(`room_id`, `user_id`, `user_name`, `user_avatar`, `live_poster`, `live_title`, `watcher_nums`) VALUES ("
					+ 0
					+ ","// roomId
					+ "\""
					+ userId
					+ "\"," // user_id
					+ "\""
					+ userName
					+ "\","// user_name
					+ "\""
					+ userAvatar
					+ "\","
					+ "\""
					+ liveCover
					+ "\","
					+ "\"" + liveTitle + "\"," + 0 // watchNums
					+ ")";
			stmt.execute(sqlStr);
			int updateCount = stmt.getUpdateCount();// ��ȡ��Ӱ�������
			// ����ɹ�
			if (updateCount > 0) {
				String queryRoomIdSql = "SELECT `room_id`,`watcher_nums` FROM `RoomInfo` WHERE `user_id`=\""
						+ userId + "\"";
				stmt.execute(queryRoomIdSql);
				ResultSet resultSet = stmt.getResultSet();
				if (resultSet != null && !resultSet.wasNull()) {
					RoomInfo roomInfo = new RoomInfo();
					while (resultSet.next()) {
						int roomId = resultSet.getInt("room_id");
						int watchNums = resultSet.getInt("watcher_nums");
						roomInfo.roomId = roomId;
						roomInfo.userId = userId;
						roomInfo.userName = userName;
						roomInfo.userAvatar = userAvatar;
						roomInfo.liveTitle = liveTitle;
						roomInfo.liveCover = liveCover;
						roomInfo.watcherNums = watchNums;
					}
					
					RoomListManager.getInstance().updateRoom(""+ roomInfo.roomId);
					WatcherListManager.getInstance().addRoom(""+ roomInfo.roomId);
					
					ResponseObject responseObject = ResponseObject
							.getSuccessResponse(roomInfo);
					responseObject.send(response);
				} else {
					// ��ѯʧ����
					ResponseObject responseObject = ResponseObject
							.getFailResponse(Error.errorCode_QueryFail,
									Error.getQueryFailMsg());
					responseObject.send(response);
				}
			} else {
				// ����ʧ���ˡ�
				ResponseObject responseObject = ResponseObject.getFailResponse(
						Error.errorCode_CreateFail, Error.getCreateFailMsg());
				responseObject.send(response);
			}
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (dbConnection != null) {
					dbConnection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
}
