package com.github.microkibaco.bearlive.action;

import com.github.microkibaco.bearlive.server.Error;
import com.github.microkibaco.bearlive.server.ResponseObject;
import com.github.microkibaco.bearlive.server.SqlManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JoinRoomAction extends IAction {

	private static final String RequestParamKey_UserId = "userId";
	private static final String RequestParamKey_RoomId = "roomId";

	@Override
	public void doAction(HttpServletRequest req, HttpServletResponse response)
			throws IOException, SQLException {
		Connection dbConnection = null;
		Statement stmt = null;
		try {
			String userIdParam = getParam(req, RequestParamKey_UserId, "");
			int roomIdParam = getParam(req, RequestParamKey_RoomId, -1);

			if (roomIdParam < 0) {
				ResponseObject responseObject = ResponseObject.getFailResponse(
						Error.errorCode_NoRequestParam,
						Error.getNoRequestParamMsg(RequestParamKey_RoomId
								+ "����ֵ<0"));
				responseObject.send(response);
				return;
			}

			dbConnection = SqlManager.getConnection();
			stmt = dbConnection.createStatement();
			// ���ݿ���Ѿ���ȫ���������ˡ�

			String queryRoomIdSql = "SELECT `user_id`,`watcher_nums` FROM `RoomInfo` WHERE `room_id`=\""
					+ roomIdParam + "\"";
			stmt.execute(queryRoomIdSql);
			ResultSet resultSet = stmt.getResultSet();
			if (resultSet != null && !resultSet.wasNull()) {
				while (resultSet.next()) {
					int watchNums = resultSet.getInt("watcher_nums");
					String userId = resultSet.getString("user_id");
					if (userId != null && userId.equals(userIdParam)) {
						// ˵�����������룬����������������Ӧ�������
					} else {
						// ˵���ǹ��ڼ���
						watcherJoin(stmt, roomIdParam, response, watchNums);
					}

				}
			} else {
				// ��ѯʧ���ˣ�Ҳ��Ϊ����ӳɹ���
				ResponseObject responseObject = ResponseObject
						.getSuccessResponse(null);
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

	private void watcherJoin(Statement stmt, int roomIdParam,
			HttpServletResponse response, int watchNums) throws SQLException,
			IOException {

		int fianlWatchNums = (watchNums + 1);

		String updateWatcherNumsSql = "UPDATE `RoomInfo` SET `watcher_nums`=\""
				+ fianlWatchNums + "\" WHERE `room_id`=\"" + roomIdParam + "\"";
		stmt.execute(updateWatcherNumsSql);

		// �ɹ����ɹ�����Ϊ�ɹ��ˡ�
		ResponseObject responseObject = ResponseObject.getSuccessResponse(null);
		responseObject.send(response);

	}

}
