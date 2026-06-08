package com.example.prototype;

public class Constants {
	
	public class STATUS_HTTP_CODE {
		public static Integer CODE_SUCCESS = 200;
		public static Integer CODE_CREATED = 201;
		public static Integer CODE_NO_CONTENT = 204;
		public static Integer CODE_MOVED_PERMANENTLY = 301;
		public static Integer CODE_FOUND = 302;
		public static Integer CODE_NOT_MODIFIED = 304;
		public static Integer CODE_BAD_REQUEST = 400;
		public static Integer CODE_UNAUTHORIZED = 401;
		public static Integer CODE_FORBIDDEN = 403;
		public static Integer CODE_NOT_FOUND = 404;
		public static Integer CODE_UNPROCESSABLE_ENTITY = 422;
		public static Integer CODE_INTERNAL_SERVICE_ERROR = 500;
		public static Integer CODE_BAD_GATEWAY = 502;
		public static Integer CODE_SERVICE_UNVAILABLE = 503;
		public static Integer CODE_GATEWAY_TIMEOUT = 504;
	}
	
	public class STATUS_HTTP_MESSAGES {
		public static String SUCCESS = "SUCCESS";
		public static String CREATED = "CREATED";
		public static String NO_CONTENT = "NO_CONTENT";
		public static String MOVED_PERMANENTLY = "MOVED_PERMANENTLY";
		public static String FOUND = "FOUND";
		public static String NOT_MODIFIED = "NOT_MODIFIED";
		public static String BAD_REQUEST = "BAD_REQUEST";
		public static String UNAUTHORIZED = "UNAUTHORIZED";
		public static String FORBIDDEN = "FORBIDDEN";
		public static String NOT_FOUND = "NOT_FOUND";
		public static String UNPROCESSABLE_ENTITY = "UNPROCESSABLE_ENTITY";
		public static String INTERNAL_SERVICE_ERROR = "INTERNAL_SERVICE_ERROR";
		public static String BAD_GATEWAY = "BAD_GATEWAY";
		public static String SERVICE_UNVAILABLE = "SERVICE_UNVAILABLE";
		public static String GATEWAY_TIMEOUT = "GATEWAY_TIMEOUT";
	}
	
	public class ERROR_MESSAGES {
		public static String USER_NOT_FOUND = "user do not exist.";
		public static String USER_CREATED_BY_NOT_FOUND = "created by user do not exist.";
		public static String USER_DELETED_NOT_FOUND = "user deleted do not exist.";
	}
	
	public class STATUS_PROFILE {
		public static String PROFILE_IS_ACTIVED = "Y";
	}
	
	public class FORMATE_CODE_TABLE{
		public static String USER_TB = "%06d";
		public static String ROLES_TB = "%03d";
		public static String USER_ROLES_TB = "%06d";
	}
	
}
