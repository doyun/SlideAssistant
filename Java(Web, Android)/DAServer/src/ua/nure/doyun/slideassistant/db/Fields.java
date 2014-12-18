package ua.nure.doyun.slideassistant.db;

public interface Fields {

	String JNDI_NAME = "java:comp/env/jdbc/slideassistant";

	String ENTITY_ID = "id";

	String USER_LOGIN = "login";
	String USER_PASSWORD = "password";
	String USER_NAME = "full_name";
	String USER_ROLE_ID = "role_id";
	String USER_EMAIL = "email";
	
	int ROLE_ADMIN = 0;
	int ROLE_USER = 1;

	String ROLE_NAME = "name";
}
