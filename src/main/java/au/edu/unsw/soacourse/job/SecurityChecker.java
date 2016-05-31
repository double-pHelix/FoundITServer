package au.edu.unsw.soacourse.job;


public enum SecurityChecker {
	instance;
	
	private boolean[][] readPermission;
	private boolean[][] writePermission;
	private int NUM_RESOURCES = 8;
	private int NUM_USERS = 3;
	//indexes
	public static final int REVIEW_PROFILE = 0;
	public static final int USER_PROFILE = 1;
	public static final int COMP_PROFILE = 2;
	public static final int JOB_APPLICATION = 3;
	public static final int JOB_POSTING = 4;
	public static final int REVIEWS = 5;
	public static final int REVIEW_ASSIGNMENT = 6;
	public static final int HIRE_TEAM = 7;
	//user types

	private int CANDIDATE = 0;
	private int MANAGER = 1;
	private int REVIEWER = 2;
	
	private String secretKeyString = "i-am-foundit";
	private String candidateString = "app-candidate";
	private String managerString = "app-manager";
	private String reviewerString ="app-reviewer";
	
	public static final String GET_METHOD = "GET";
	public static final String POST_METHOD = "POST";
	public static final String PUT_METHOD ="PUT";
	public static final String DELETE_METHOD ="DELETE";
	
	private int READ = 0;
	private int WRITE = 1;

	private int ERROR = 999;
			
	private SecurityChecker(){
		readPermission = new boolean[NUM_RESOURCES][NUM_USERS];
		writePermission = new boolean[NUM_RESOURCES][NUM_USERS];
		
		readPermission[REVIEW_PROFILE][CANDIDATE] = false;
		readPermission[REVIEW_PROFILE][MANAGER] = true;
		readPermission[REVIEW_PROFILE][REVIEWER] = true;
		
		readPermission[USER_PROFILE][CANDIDATE] = true;
		readPermission[USER_PROFILE][MANAGER] = true;
		readPermission[USER_PROFILE][REVIEWER] = true;
		
		readPermission[COMP_PROFILE][CANDIDATE] = true;
		readPermission[COMP_PROFILE][MANAGER] = true;
		readPermission[COMP_PROFILE][REVIEWER] = true;
		
		readPermission[JOB_APPLICATION][CANDIDATE] = true;
		readPermission[JOB_APPLICATION][MANAGER] = true;
		readPermission[JOB_APPLICATION][REVIEWER] = true;
		
		readPermission[JOB_POSTING][CANDIDATE] = true;
		readPermission[JOB_POSTING][MANAGER] = true;
		readPermission[JOB_POSTING][REVIEWER] = true;
		
		readPermission[REVIEWS][CANDIDATE] = false;
		readPermission[REVIEWS][MANAGER] = true;
		readPermission[REVIEWS][REVIEWER] = true;
		
		readPermission[REVIEW_ASSIGNMENT][CANDIDATE] = false;
		readPermission[REVIEW_ASSIGNMENT][MANAGER] = true;
		readPermission[REVIEW_ASSIGNMENT][REVIEWER] = false;
		
		readPermission[HIRE_TEAM][CANDIDATE] = false;
		readPermission[HIRE_TEAM][MANAGER] = true;
		readPermission[HIRE_TEAM][REVIEWER] = false;

		writePermission[REVIEW_PROFILE][CANDIDATE] = false;
		writePermission[REVIEW_PROFILE][MANAGER] = true;
		writePermission[REVIEW_PROFILE][REVIEWER] = true;
		
		writePermission[USER_PROFILE][CANDIDATE] = true;
		writePermission[USER_PROFILE][MANAGER] = false;
		writePermission[USER_PROFILE][REVIEWER] = false;
		
		writePermission[COMP_PROFILE][CANDIDATE] = false;
		writePermission[COMP_PROFILE][MANAGER] = true;
		writePermission[COMP_PROFILE][REVIEWER] = false;
		
		writePermission[JOB_APPLICATION][CANDIDATE] = true;
		writePermission[JOB_APPLICATION][MANAGER] = false;
		writePermission[JOB_APPLICATION][REVIEWER] = false;
		
		writePermission[JOB_POSTING][CANDIDATE] = false;
		writePermission[JOB_POSTING][MANAGER] = true;
		writePermission[JOB_POSTING][REVIEWER] = false;
		
		writePermission[REVIEWS][CANDIDATE] = false;
		writePermission[REVIEWS][MANAGER] = false;
		writePermission[REVIEWS][REVIEWER] = true;
		
		writePermission[REVIEW_ASSIGNMENT][CANDIDATE] = false;
		writePermission[REVIEW_ASSIGNMENT][MANAGER] = true;
		writePermission[REVIEW_ASSIGNMENT][REVIEWER] = false;
		
		writePermission[HIRE_TEAM][CANDIDATE] = false;
		writePermission[HIRE_TEAM][MANAGER] = true;
		writePermission[HIRE_TEAM][REVIEWER] = false;
		
	}
	
	public int convertUser(String user){
		
		if(user.matches(candidateString)){
			return CANDIDATE;
		} else if(user.matches(managerString)){
			return MANAGER;
		} else if(user.matches(reviewerString)){
			return REVIEWER;
		}
		
		return ERROR;
	}
	
	public int convertMethod(String method){
		
		if(method.matches(GET_METHOD)){
			return READ;
		} else if(method.matches(POST_METHOD)){
			return WRITE;
		} else if(method.matches(PUT_METHOD)){
			return WRITE;
		}	else if(method.matches(DELETE_METHOD)){
			return WRITE;
		}
		
		return ERROR;
	}
	
	public boolean keyAccepted(String givenKey){
		
		if(givenKey == null)
			return false;
		
		if(secretKeyString.matches(givenKey)){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkPermisionResource(String method, int resource, String shortKey){
		
		if(shortKey == null)
			return false;
		
		int userVal = convertUser(shortKey);
		int toRead = convertMethod(method);
		
		if(toRead == ERROR || userVal == ERROR){
			return false;
		}
		
		if(toRead == READ){
			return readPermission[resource][userVal];
		} else {
			return writePermission[resource][userVal];
		}
	}
	
	/*
	public static void main(String[] args) {
		SecurityChecker checker = new SecurityChecker();
		
		if(checker.checkPermisionResource("POST", SecurityChecker.COMP_PROFILE, "app-candidate")){
			System.out.println("Works!");
		}
		if(checker.keyAccepted("i-am-foundit")){
			System.out.println("Works!");
		}
		
	}
	*/
}
