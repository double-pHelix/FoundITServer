package au.edu.unsw.soacourse.job.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.edu.unsw.soacourse.job.model.CompanyProfile;
import au.edu.unsw.soacourse.job.model.HiringTeam;
import au.edu.unsw.soacourse.job.model.HiringTeamStore;
import au.edu.unsw.soacourse.job.model.JobApplication;
import au.edu.unsw.soacourse.job.model.JobApplications;
import au.edu.unsw.soacourse.job.model.JobPosting;
import au.edu.unsw.soacourse.job.model.JobPostings;
import au.edu.unsw.soacourse.job.model.Review;
import au.edu.unsw.soacourse.job.model.TeamMemberProfile;
import au.edu.unsw.soacourse.job.model.UserProfile;

//This is suppose to be the DAO layer so we should probably change this
//This class should interact with our data storage
//Probably this may  convert all data to xml or something ??
//Need to grab job alerts from two URLs

// https://www.teach.nsw.edu.au/find-teaching-jobs/jobfeed (is in html will need to convert)
// http://rss.jobsearch.careerone.com.au/rssquery.ashx?q=Java&rad_units=km&cy=AU&pp=25&sort=rv.di.dt&baseurl=jobview.careerone.com.au
// above is in xml so no need to convert

// Expose these two urls
// http://foundit-server/jobalerts?keyword={keyword}
// http://foundit-server/jobalerts?keyword={keyword}&sort_by=jobtitle

//everything above is from the lab

//what do we want to be able to do

//store metadata about the service
//nextAvailableID for all model classes (profiles, applications ...etc.)
//


public enum JobsDAO {
	instance;

	//these are in place of XML storage for now! 
	//we need to store persistently
	//referred to by their ids
    private Map<String, CompanyProfile> contentStoreCompProfiles = new HashMap<String, CompanyProfile>();
    private Map<String, UserProfile> contentStoreUserProfiles = new HashMap<String, UserProfile>();
    private Map<String, HiringTeamStore> contentStoreHiringTeam = new HashMap<String, HiringTeamStore>();
    private Map<String, JobApplication> contentStoreApplications = new HashMap<String, JobApplication>();
    private Map<String, JobPosting> contentStorePostings = new HashMap<String, JobPosting>();
    private Map<String, Review> contentStoreReviews = new HashMap<String, Review>();
    private Map<String, TeamMemberProfile> contentStoreTeamProfiles = new HashMap<String, TeamMemberProfile>();

    
    private JobsDAO() {

    	/*
    	JobApplication b = new JobApplication("1", "RESTful Web Services");
        b.setDetail("http://oreilly.com/catalog/9780596529260");
        contentStore.put("1", b);
        b = new JobApplication("2", "RESTful Java with JAX-RS");
        b.setDetail("http://oreilly.com/catalog/9780596158057");
        contentStore.put("2", b);
        */
    	
    	//add test cases files (to avoid having to create manually)
    	addTestCasesJobPostings();
    	addTestCasesUserProfiles();
    	addTestCasesJobApplications();
    	addTestCasesHiringTeamMembersAndTeam();
    	
    }
    
    private void addTestCasesJobPostings(){
    	String id = "1"; 
    	String title = "title"; 
    	String description = "description"; 
    	String companyProfileId = "companyProfileId"; 
		String positionType = "positionType"; 
    	String desiredSkills = "desiredSkills"; 
    	String salaryLevel= "salaryLevel"; 
		String location= "location"; 
    	
    	JobPosting post1 = new JobPosting(id, title, description, companyProfileId,
    			positionType, desiredSkills, salaryLevel,
    			location);
    	
    	JobPosting post2 = new JobPosting("2");
    	JobPosting post3 = new JobPosting("3");
    	JobPosting post4 = new JobPosting("4");
    	

    	contentStorePostings.put(id, post1);
    	contentStorePostings.put("2", post2);
    	contentStorePostings.put("3", post3);
    	contentStorePostings.put("4", post4);
    }
    
    private void addTestCasesUserProfiles(){
    	
    	
    }
    
    private void addTestCasesJobApplications(){
    	String id = "1"; 
        String jobApplicationId= "1"; 
        String userProfileId= "1"; 
        String coverLetter= "Hey"; 
        String resume= "resume.pdf"; //Maybe just a text pretending to be an attachment (i.e. “resume.pdf”)

        JobApplication app1 = new JobApplication(id, jobApplicationId, userProfileId, coverLetter, resume);
        JobApplication app2 = new JobApplication("2");
        JobApplication app3 = new JobApplication("3");
        JobApplication app4 = new JobApplication("4");
        JobApplication app5 = new JobApplication("5");

        //test for manager grab
        app2.setJobApplicationId("1");
        app3.setJobApplicationId("1");
        app4.setJobApplicationId("1");
        contentStoreApplications.put(id, app1);
        contentStoreApplications.put("2", app2);
        contentStoreApplications.put("3", app3);
        contentStoreApplications.put("4", app4);
        contentStoreApplications.put("5", app5);
        
    	
    }
    private void  addTestCasesHiringTeamMembersAndTeam(){
    	String id = "1";
        String companyProfileId = "1";
        
        CompanyProfile newCompProf = new CompanyProfile(companyProfileId, "", "", "", "", "" );
        storeCompanyProfile(newCompProf);
        
        String id2 ="1";
        String username2 = "";
        String password2 = "";
    	String professionalSkills2 = "";
    	String id3="2";
        String username3 = "";
        String password3 = "";
    	String professionalSkills3 = "";
    	String id4="3";
        String username4 = "";
        String password4 = "";
    	String professionalSkills4 = "";
    	String id5="4";
        String username5 = "";
        String password5 = "";
    	String professionalSkills5 = "";
    	String id6="5";
        String username6 = "";
        String password6 = "";
    	String professionalSkills6 = "";
        
    	TeamMemberProfile memProf1 = new TeamMemberProfile(id2, username2, password2, professionalSkills2);
    	TeamMemberProfile memProf2 = new TeamMemberProfile(id3, username3, password3, professionalSkills3);
    	TeamMemberProfile memProf3 = new TeamMemberProfile(id4, username4, password4, professionalSkills4);
    	TeamMemberProfile memProf4 = new TeamMemberProfile(id5, username5, password5, professionalSkills5);
    	TeamMemberProfile memProf5 = new TeamMemberProfile(id6, username6, password6, professionalSkills6);
    	
    	storeTeamMemberProfile(memProf1);
    	storeTeamMemberProfile(memProf2);
    	storeTeamMemberProfile(memProf3);
    	storeTeamMemberProfile(memProf4);
    	storeTeamMemberProfile(memProf5);
		
		//create new profile to transfer
		HiringTeamStore newHiringTeam = new HiringTeamStore(id, companyProfileId, id2, id3, id4, id5, id6);
				
    	storeHiringTeam(newHiringTeam);
    }
    
    //CREATE/UPDATE
    
    //what do we want to do?
    //store userProfiles etc..
    
    public void storeUserProfile(UserProfile newUserProfile){
    	contentStoreUserProfiles.put(newUserProfile.getId(), newUserProfile);
    }
    
    public void storeCompanyProfile(CompanyProfile newCompanyProfile){
    	contentStoreCompProfiles.put(newCompanyProfile.getId(), newCompanyProfile);
    }
    
    public void storeHiringTeam(HiringTeamStore newHiringTeam){
    	contentStoreHiringTeam.put(newHiringTeam.getId(), newHiringTeam);
    }
    
    public void storeJobApplication(JobApplication newJobApplication ){
    	contentStoreApplications.put(newJobApplication.getId(), newJobApplication);
    }
    
    public void storeJobPosting(JobPosting newJobPosting){
    	contentStorePostings.put(newJobPosting.getId(), newJobPosting);
    }
    
    public void storeReview(Review newReview ){
    	contentStoreReviews.put(newReview.getId(), newReview);
    }
    
    public void storeTeamMemberProfile(TeamMemberProfile newTeamMemberProfile){
    	contentStoreTeamProfiles.put(newTeamMemberProfile.getId(), newTeamMemberProfile);
    }
    
    
    

    /*
    public Map<String, JobApplication> getStore(){
        return contentStore;
    }
*/
    //GET
    
    public UserProfile getUserProfile(String id){
    	return contentStoreUserProfiles.get(id);
    }
    
    public CompanyProfile getCompanyProfile(String id){
    	return contentStoreCompProfiles.get(id);
    }
    
    public HiringTeamStore getHiringTeam(String id){
    	return contentStoreHiringTeam.get(id);
    }
    
    public JobApplication getJobApplication(String id){
    	return contentStoreApplications.get(id);
    }
    
    public JobPosting getJobPosting(String id){
    	return contentStorePostings.get(id);
    }
    
    public Review getReview(String id){
    	return contentStoreReviews.get(id);
    }
    
    public TeamMemberProfile getTeamMemberProfile(String id){
    	return contentStoreTeamProfiles.get(id);
    }
    
    //DELETE
    
    public UserProfile deleteUserProfile(String id){
    	return contentStoreUserProfiles.remove(id);
    }
    
    public CompanyProfile deleteCompanyProfile(String id){
    	return contentStoreCompProfiles.remove(id);
    }
    
    public HiringTeamStore deleteHiringTeam(String id){
    	return contentStoreHiringTeam.remove(id);
    }
    
    public JobApplication deleteJobApplication(String id){
    	return contentStoreApplications.remove(id);
    }
    
    public JobPosting deleteJobPosting(String id){
    	return contentStorePostings.remove(id);
    }
    
    public Review deleteReview(String id){
    	return contentStoreReviews.remove(id);
    }
    
    public TeamMemberProfile deleteTeamMemberProfile(String id){
    	return contentStoreTeamProfiles.remove(id);
    }
     
 
    ///ID STUFF
    
    public String getNextUserProfileId(){
    	int nextId = contentStoreUserProfiles.size() + 1;
    	while(contentStoreUserProfiles.containsKey(Integer.toString(nextId))){
    		nextId++;
    	}
    	return Integer.toString(nextId);
    }
    
    public String getNextCompanyProfileId(){
    	int nextId = contentStoreUserProfiles.size() + 1;
    	while(contentStoreUserProfiles.containsKey(Integer.toString(nextId))){
    		nextId++;
    	}
    	return Integer.toString(nextId);
    }
    
    public String getNextHiringTeamId(){
    	int nextId = contentStoreHiringTeam.size() + 1;
    	while(contentStoreHiringTeam.containsKey(Integer.toString(nextId))){
    		nextId++;
    	}
    	return Integer.toString(nextId);
    }
    
    public String getNextJobApplicationId(){
    	int nextId = contentStoreApplications.size() + 1;
    	while(contentStoreApplications.containsKey(Integer.toString(nextId))){
    		nextId++;
    	}
    	return Integer.toString(nextId);
    }
    
    public String getNextJobPostingId(){
    	int nextId = contentStorePostings.size() + 1;
    	while(contentStorePostings.containsKey(Integer.toString(nextId))){
    		nextId++;
    	}
    	return Integer.toString(nextId);
    }
    
    public String getNextReviewId(){
    	int nextId = contentStoreReviews.size() + 1;
    	while(contentStoreReviews.containsKey(Integer.toString(nextId))){
    		nextId++;
    	}
    	return Integer.toString(nextId);
    }
    
    public String getNextTeamMemberProfileId(){
    	int nextId = contentStoreTeamProfiles.size() + 1;
    	while(contentStoreTeamProfiles.containsKey(Integer.toString(nextId))){
    		nextId++;
    	}
    	return Integer.toString(nextId);
    }
    
    //QUERY JOB POSTINGS
//  private String description;
//  private String companyProfileId;
//	  private String positionType;
//	  private String desiredSkills;
//  private String salaryLevel;
//  private String location;
//	  private String status; //(created, open, in-review, processed, sent invitations)
//	  private String archived;
	
    public JobPostings searchJobPostingKeyword(String keyword){
      	//		
    	//match as a substring
    	//note (?i: PATTERN ) makes match case insensitive
    	  
    	  
    	keyword = "(?i:.*" + keyword + ".*)";
    	
      	List<JobPosting> jobPostingsList = new ArrayList<JobPosting>();

      	for(JobPosting posting : contentStorePostings.values()){
      		if(posting.getTitle().matches(keyword) || posting.getSkills().matches(keyword) || posting.getStatus().matches(keyword) || posting.getDescription().matches(keyword)){
      			jobPostingsList.add(posting);
      		}
      	}
      	JobPostings newJobPostings = new JobPostings(jobPostingsList);
      	
      	return newJobPostings;
      }
    
  public JobPostings searchJobPostingAttribute(String query, String attrbute){
  	//		
	//match as a substring
	//note (?i: PATTERN ) makes match case insensitive
	  
	  
	query = "(?i:.*" + query + ".*)";
	
  	List<JobPosting> jobPostingsList = new ArrayList<JobPosting>();

  	for(JobPosting posting : contentStorePostings.values()){
  		
  		if(attrbute == "title"){
  			if(posting.getTitle().matches(query)){
  	  			jobPostingsList.add(posting);
  	  		}
  		} else if(attrbute == "skills"){
  			if(posting.getSkills().matches(query)){
  	  			jobPostingsList.add(posting);
  	  		}
  		} else if(attrbute == "status"){
  			if(posting.getStatus().matches(query)){
  	  			jobPostingsList.add(posting);
  	  		}
  		} else if(attrbute == "description"){
  			if(posting.getDescription().matches(query)){
  	  			jobPostingsList.add(posting);
  	  		}
  		} 
  		
  	}
  	
  	JobPostings newJobPostings = new JobPostings(jobPostingsList);
  	
  	return newJobPostings;
  }
  
  public JobPostings getAllJobPostings(){
	  	//
	  	List<JobPosting> jobPostingsList = new ArrayList<JobPosting>();

	  	for(JobPosting posting : contentStorePostings.values()){
	  		
  			jobPostingsList.add(posting);
	  		
	  	}
	  	JobPostings newJobPostings = new JobPostings(jobPostingsList);
	  	
	  	return newJobPostings;
  }
  public JobApplications searchJobApplicationsPostId(String query){
	  	//		
		//match as a substring
		//query = ".*" + query + ".*";
		
	  	List<JobApplication> JobApplicationsList = new ArrayList<JobApplication>();

	  	for(JobApplication app : contentStoreApplications.values()){
	  		if(app.getJobApplicationId().matches(query)){
	  			JobApplicationsList.add(app);
	  		}
	  	}
	  	JobApplications newJobApplications = new JobApplications(JobApplicationsList);
	  	
	  	return newJobApplications;
  }
  
  
  public JobApplications getAllJobApplications(){
	  	//
	  	List<JobApplication> jobApplicationList = new ArrayList<JobApplication>();

	  	for(JobApplication application : contentStoreApplications.values()){
	  		
	  		jobApplicationList.add(application);
	  		
	  	}
	  	JobApplications newJobApplications = new JobApplications(jobApplicationList);
	  	
	  	return newJobApplications;
}
	  
  
  
/*	
  public JobPosting searchJobPostingPositionType(String query){
  	return contentStorePostings.remove(id);
  }
  public JobPosting searchJobPostingDesiredSkills(String query){
  	return contentStorePostings.remove(id);
  }
  public JobPosting searchJobPostingSalaryLevel(String query){
  	return contentStorePostings.remove(id);
  }
  public JobPosting searchJobPostingLocation(String query){
  	return contentStorePostings.remove(id);
  }
*/
    
    
}	
