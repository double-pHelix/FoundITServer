package au.edu.unsw.soacourse.job.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.edu.unsw.soacourse.job.model.CompanyProfile;
import au.edu.unsw.soacourse.job.model.HiringTeam;
import au.edu.unsw.soacourse.job.model.JobApplication;
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
    private Map<String, HiringTeam> contentStoreHiringTeam = new HashMap<String, HiringTeam>();
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
    	
    	//add test cases files
    	
    	
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
    
    public void storeHiringTeam(HiringTeam newHiringTeam){
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
    
    public HiringTeam getHiringTeam(String id){
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
    
    public HiringTeam deleteHiringTeam(String id){
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
    
    //QUERY JOB POSTINGS
//    private String description;
//    private String companyProfileId;
//	  private String positionType;
//	  private String desiredSkills;
//    private String salaryLevel;
//    private String location;
//	  private String status; //(created, open, in-review, processed, sent invitations)
//	  private String archived;
	

    public JobPostings searchJobPostingDescription(String query){
    	//
    	List<JobPosting> jobPostingsList = new ArrayList<JobPosting>();
  
    	for(JobPosting posting : contentStorePostings.values()){
    		if(posting.getDescription().matches(query)){
    			jobPostingsList.add(posting);
    		}
    	}
    	JobPostings newJobPostings = new JobPostings(jobPostingsList);
    	
    	return newJobPostings;
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
