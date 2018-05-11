package com.github.microkibaco.bearlive.server;

public class Error {
	public static final String errorCode_NoAction = "404";
	private static final String errorMsg_NoAction = "û��Action����";

	public static String getNoActionMsg() {
		return errorMsg_NoAction;
	}

	public static final String errorCode_NoRequestParam = "405";
	private static final String errorMsg_NoRequestParam = "缺少参数值";

	public static String getNoRequestParamMsg(String requestParam) {
		return errorMsg_NoRequestParam + ":" + requestParam;
	}

	public static final String errorCode_Exception = "500";
	private static final String errorMsg_Exception = "服务器异常";

	public static String getExceptionMsg(String e) {
		return errorMsg_Exception + ":" + e;
	}

	// ������ͨ�õ�error

	// �������ض�action��error,600,610,620,630
	public static final String errorCode_CreateFail = "600";
	private static final String errorMsg_CreateFail = "����ֱ������ʧ��";

	public static String getCreateFailMsg() {
		return errorMsg_CreateFail;
	}

	public static final String errorCode_QueryFail = "601";
	private static final String errorMsg_QueryFail = "��ȡֱ������ʧ��";

	public static String getQueryFailMsg() {
		return errorMsg_QueryFail;
	}

	public static final String errorCode_QueryListFail = "602";
	private static final String errorMsg_QueryListFail = "��ȡֱ�������б�ʧ��";

	public static String getQueryListFailMsg() {
		return errorMsg_QueryListFail;
	}
	
	public static final String errorCode_QuitFail = "603";
	private static final String errorMsg_QuitFail = "�˳�ֱ������ʧ��";

	public static String getQuitFailMsg() {
		return errorMsg_QuitFail;
	}

}
