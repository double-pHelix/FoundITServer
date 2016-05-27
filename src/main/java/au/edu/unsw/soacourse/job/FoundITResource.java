package au.edu.unsw.soacourse.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import au.edu.unsw.soacourse.job.dao.JobsDAO;
import au.edu.unsw.soacourse.job.model.CompanyProfile;
import au.edu.unsw.soacourse.job.model.HiringTeam;
import au.edu.unsw.soacourse.job.model.HiringTeamStore;
import au.edu.unsw.soacourse.job.model.JobApplication;
import au.edu.unsw.soacourse.job.model.JobApplicationAssignment;
import au.edu.unsw.soacourse.job.model.JobApplications;
import au.edu.unsw.soacourse.job.model.JobPosting;
import au.edu.unsw.soacourse.job.model.JobPostings;
import au.edu.unsw.soacourse.job.model.Review;
import au.edu.unsw.soacourse.job.model.TeamMemberProfile;
import au.edu.unsw.soacourse.job.model.UserProfile;

//TODONE:: Modify output to include a GET url like in the lecture slides DONE!
//TODO:: Provide @OPTION method for classes
//TODONE:: Delete only archives (Job Posting, Job Application)
//TODO: Processes for
	//if reviews are in set status for application

//TODO: input checks
	//If job post is closed reject application
	//If PUT status isn't valid reject 

//We can change this path
@Path("/foundIT")
public class FoundITResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//User Profile (Candidate/Employer?/Hiring Team Member)
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	POST:
//	Create new user
//	Create a new (User Profile) with a new id set with given values
//	Return new user id (and it’s get URL)
	@POST
	@Path("/userprofile")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response newUserProfile(
			@FormParam("name") String name,
			@FormParam("currentposition") String currentPosition,
			@FormParam("education") String education,
			@FormParam("pastexperience") String pastExperience,
			@FormParam("professionalskills") String professionalSkills
	) throws IOException {
		String id = JobsDAO.instance.getNextUserProfileId();

		//create new profile
		UserProfile newProfile = new UserProfile(id, name, currentPosition, education, pastExperience, professionalSkills);
				
		//store profile
		JobsDAO.instance.storeUserProfile(newProfile);
		
		//System.out.println("Name Recorded is:" + JobsDAO.instance.getUserProfile("hi").getName());
		//getStore().put(id, b);
		
		//TODO: Fix here so that it returns the new book
		Response res = null;
		res = Response.ok(newProfile).build();
		return res;
	}
	
//	GET:
//	Get user information
//	Look up (User Profile) with given “id”
//	Return user information in XML
	//Calling Get on the URL
	@GET
	@Path("/userprofile/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public UserProfile getUserProfile(@PathParam("id") String id) {
		
		UserProfile u = JobsDAO.instance.getUserProfile(id);
		if(u==null)
			throw new RuntimeException("GET: Book with:" + id +  " not found");
		
		return u;
		
	}
	
//	PUT:
//	Update user information
//	Look up (User Profile) with given “id” and delete
//	Place new (User Profile) in its place
//	Return Success
	@PUT
	@Path("/userprofile")
	@Consumes(MediaType.APPLICATION_XML)
	public Response putUserProfile(UserProfile u) {
		Response res = null;
		String test = "test";
		
		//store profile
		JobsDAO.instance.storeUserProfile(u);
		//Probably should modify test to be in xml format or something :/
		res = Response.ok(test).build();
		//res.
		return res;
		//TODO: Fix here so that it returns the updated job
	}
	
	
	
//	DELETE
//	Delete user information
//	Look up (User Profile) with given “id” and delete
	@DELETE
	@Path("/userprofile/{id}")
	public Response deleteUserProfile(@PathParam("id") String id) {
		
		UserProfile delProfile = JobsDAO.instance.deleteUserProfile(id);
		//modify.s
		
		Response res = null;
		int errorCode = 220;
		
		if(delProfile == null) {
			//throw new RuntimeException("DELETE: Book with " + id +  " not found");
			String msg = "DELETE: Profile with " + id +  " not found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(errorCode);
			res = resBuild.build();
			//res = Response.status(Response.Status.BAD_REQUEST).build();
		} else {
			String msg = "Deleted Profile:" + id;
			res = Response.ok(msg).build();
		}
		return res;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Company Profile
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	POST:
//	Create new company
//	Create a new (Company Profile) with a new id set with given values
	
//	Return new company id (and it’s get URL)
	@POST
	@Path("/companyprofile")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newCompanyProfile(
			@FormParam("name") String name,
			@FormParam("description") String description,
			@FormParam("website") String website,
			@FormParam("industryType") String industryType,
			@FormParam("address") String address
	) throws IOException {
		String id = JobsDAO.instance.getNextCompanyProfileId();

		//create new profile
		CompanyProfile newProfile = new CompanyProfile(id, name, description, website, industryType, address);
				
		//store profile
		JobsDAO.instance.storeCompanyProfile(newProfile);
		
		//System.out.println("Name Recorded is:" + JobsDAO.instance.getUserProfile("hi").getName());
		//getStore().put(id, b);
		
		//TODO: Fix here so that it returns the new book
		Response res = null;
		res = Response.ok(newProfile).build();
		return res;
	}
//	GET:
//	Get company information
//	Look up (Company Profile) with given “id”
//	Return company information in XML
	@GET
	@Path("/companyprofile/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public CompanyProfile getCompanyProfile(@PathParam("id") String id) {
		
		CompanyProfile c = JobsDAO.instance.getCompanyProfile(id);
		
		if(c==null)
			throw new RuntimeException("GET: Profile with:" + id +  " not found");
		
		return c;
		
	}
	
//	PUT:
//	Update company information
//	Look up (Company Profile) with given “id” and delete
//	Place new (Company Profile) in its place
//	Return Success
	@PUT
	@Path("/companyprofile")
	@Consumes(MediaType.APPLICATION_XML)
	public Response putCompanyProfile(CompanyProfile u) {
		Response res = null;
		String msg = "success";
		
		//store profile
		JobsDAO.instance.storeCompanyProfile(u);
		//Probably should modify test to be in xml format or something :/
		res = Response.ok(msg).build();
		//res.
		return res;
		//TODO: Fix here so that it returns the updated job
	}
	
//	DELETE
//	Delete user information
//	Look up (Company Profile) with given “id” and delete
	@DELETE
	@Path("/companyprofile/{id}")
	public Response deleteCompanyProfile(@PathParam("id") String id) {
		
		CompanyProfile delProfile = JobsDAO.instance.deleteCompanyProfile(id);
		//modify.s
		
		Response res = null;
		int errorCode = 220;
		
		if(delProfile == null) {
			//throw new RuntimeException("DELETE: Book with " + id +  " not found");
			String msg = "DELETE: Profile with " + id +  " not found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(errorCode);
			res = resBuild.build();
			//res = Response.status(Response.Status.BAD_REQUEST).build();
		} else {
			String msg = "Deleted Profile:" + id;
			res = Response.ok(msg).build();
		}
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Job Posting (for Employer)
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	POST:
//	Create Job Posting
//	Create a new (Job Posting) with a new id set with given values
//	Return new id URI to Company
	@POST
	@Path("/jobposting")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newJobPosting(
			@FormParam("title") String title,
			@FormParam("description") String description,
			@FormParam("companyprofileid") String companyProfileId,
			@FormParam("positiontype") String positionType,
			@FormParam("desiredskills") String desiredSkills,
			@FormParam("salarylevel") String salaryLevel,
			@FormParam("location") String location
	) throws IOException {
		String id = JobsDAO.instance.getNextJobPostingId();
		
		//create new profile
		JobPosting newJobPosting= new JobPosting(id, title, description, companyProfileId,
				positionType, desiredSkills, salaryLevel,
				location);
				
		//store profile
		JobsDAO.instance.storeJobPosting(newJobPosting);
		
		//System.out.println("Name Recorded is:" + JobsDAO.instance.getUserProfile("hi").getName());
		//getStore().put(id, b);
		
		//TODO: Fix here so that it returns the new book
		Response res = null;
		res = Response.ok(newJobPosting).build();
		return res;
	}
//	GET:
//	Get Job Posting
//	Look up (Company Profile) with given “id”
//	If id does not exist return error
//	Return XML of Job Posting
	@GET
	@Path("/jobposting/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JobPosting getJobPosting(@PathParam("id") String id) {
		
		JobPosting p = JobsDAO.instance.getJobPosting(id);
		
		if(p==null)
			throw new RuntimeException("GET: Job Posting with:" + id +  " not found");
		
		return p;
		
	}
//	Get a Collection i.e Job Search/id={“Job Application id”} or skills/etc..=”REGEX MATCH”
//	Go through each Job Posting and check if they match the query (just start with id for now)... and are NOT archived
//	Return an XML of those job postings (full)
	@GET
	@Path("/jobposting/search")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JobPostings getSearchJobPostings(@QueryParam("keyword") String keyword, //title
			@QueryParam("title") String title,
			@QueryParam("skills") String skills,
			@QueryParam("status") String status,
			@QueryParam("description") String description
														) {

		//search description
		JobPostings allJobPosts;
		if(keyword != null){
			allJobPosts = JobsDAO.instance.searchJobPostingKeyword(keyword);
		} else { //the rest
			if(title != null){
				allJobPosts = JobsDAO.instance.searchJobPostingAttribute(title, "title");
			} else if(skills != null){
				allJobPosts = JobsDAO.instance.searchJobPostingAttribute(skills, "skills");
			} else if(status!= null){
				allJobPosts = JobsDAO.instance.searchJobPostingAttribute(status, "status");
			} else if(description!= null){
				allJobPosts = JobsDAO.instance.searchJobPostingAttribute(description, "description");
			} else {
				allJobPosts = null;
			}
			
		}
		
		if(allJobPosts==null){
			if(title != null){
				allJobPosts = JobsDAO.instance.searchJobPostingAttribute(title, "title");
				throw new RuntimeException("GET: No Job Postings not found for title:" + title);
			} else if(skills != null){
				allJobPosts = JobsDAO.instance.searchJobPostingAttribute(skills, "skills");
				throw new RuntimeException("GET: No Job Postings not found for skills:" + skills);
			} else if(status!= null){
				allJobPosts = JobsDAO.instance.searchJobPostingAttribute(status, "status");
				throw new RuntimeException("GET: No Job Postings not found for status:" + status);
			} else if(description!= null){
				allJobPosts = JobsDAO.instance.searchJobPostingAttribute(description, "description");
				throw new RuntimeException("GET: No Job Postings not found for description:" + description);
			} else {
				allJobPosts = null;
				throw new RuntimeException("GET: No Query Provided");
			}
		}
		return allJobPosts;
		
	}
	/* ADVANCED SEARCH??
	@GET
	@Path("/jobposting/search/advanced")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JobPostings getSearchJobPostingsAdvanced(@DefaultValue("^$") @QueryParam("keyword") String keyword, //title
			@DefaultValue("^$") @QueryParam("skills") String skills,
			@DefaultValue("^$") @QueryParam("status") String status,
			@DefaultValue("^$") @QueryParam("query") String query
														) {

		//search description
		System.out.println("Search query:" + query);
		JobPostings allJobPosts = JobsDAO.instance.searchJobPostingDescription(keyword, skills, status, query);
		
		if(allJobPosts==null)
			throw new RuntimeException("GET: No Job Postings not found for query:" + query);
		
		return allJobPosts;
		
	}
	*/
//	Get All
	@GET
	@Path("/jobposting/all")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JobPostings getAllJobPostings() {
		
		JobPostings allJobPosts = JobsDAO.instance.getAllJobPostings();
		
		if(allJobPosts==null)
			throw new RuntimeException("GET: No Job Postings found");
		
		return allJobPosts;
		
	}
//	PUT:
//	Update Job Posting Public Details (if no applications applied for it)
//	Update Job Posting status (open/in-review/closed)
//	For BOTH situations:
//	We delete the old Job Posting with id
//	Replace with the given Job Posting
//	Return GET URI and id of the resource… to signal success.
	@PUT
	@Path("/jobposting")
	@Consumes(MediaType.APPLICATION_XML)
	public Response putJobPosting(JobPosting p) {
		Response res = null;
		String msg = "success";
		
		//store profile
		JobsDAO.instance.storeJobPosting(p);
		//Probably should modify test to be in xml format or something :/
		res = Response.ok(msg).build();
		//res.
		return res;
		//TODO: Fix here so that it returns the updated job
	}
	
//	DELETE:
//	Archive job posting (mark as archived, not physically delete)
//	Look up job posting with id
//	Set flag to be archived
	@DELETE
	@Path("/jobposting/{id}")
	public Response deleteJobPosting(@PathParam("id") String id) {
		
		JobPosting delProfile = JobsDAO.instance.deleteJobPosting(id);
		//modify.s
		
		Response res = null;
		int errorCode = 220;
		
		if(delProfile == null) {
			//throw new RuntimeException("DELETE: Book with " + id +  " not found");
			String msg = "DELETE: Posting with " + id +  " not found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(errorCode);
			res = resBuild.build();
			//res = Response.status(Response.Status.BAD_REQUEST).build();
		} else {
			String msg = "Deleted Posting:" + id;
			res = Response.ok(msg).build();
		}
		return res;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	//Job Application (for Candidate)
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	POST:
//	Create Job Application (Apply to a Job Posting)
//	Create a new job application with new id and given details
//	Returns: new Job Application ID (and it’s get URI)
	@POST
	@Path("/jobapplication")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newJobApplication(
			@FormParam("jobpostid") String jobpostId,
			@FormParam("userprofileid") String userProfileId,
			@FormParam("coverletter") String coverLetter,
			@FormParam("resume") String resume
	) throws IOException {
		String id = JobsDAO.instance.getNextJobApplicationId();
		
		//check job application exists
		//check user profile exists
		JobPosting existingPost = JobsDAO.instance.getJobPosting(jobpostId);
		UserProfile existingUser = JobsDAO.instance.getUserProfile(userProfileId);
	
		if(existingPost == null && existingUser == null){
			throw new RuntimeException("POST: No Job Postings found with id:" + jobpostId + " and no User found with id:" + userProfileId);
		} else if(existingPost == null){
			throw new RuntimeException("POST: No Job Postings found with id:" + jobpostId);
		} else if(existingUser == null){
			throw new RuntimeException("POST: No User found with id:" + userProfileId);
		}
		
		//create new application
		JobApplication newJobApp= new JobApplication(id, jobpostId,
				userProfileId, coverLetter, resume);
				
		//store application
		JobsDAO.instance.storeJobApplication(newJobApp);
		
		//System.out.println("Name Recorded is:" + JobsDAO.instance.getUserProfile("hi").getName());
		//getStore().put(id, b);
		
		//TODO: Fix here so that it returns the new book
		Response res = null;
		res = Response.ok(newJobApp).build();
		return res;
	}
//	GET:
//	Get Single Application including its Status (shortlisted/not-shortlisted or rejected/accepted)
//	Go through all applications and look i
//	Return XML of Job Application
	@GET
	@Path("/jobapplication/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JobApplication getJobApplication(@PathParam("id") String id) {
		
		JobApplication a = JobsDAO.instance.getJobApplication(id);
		
		if(a==null)
			throw new RuntimeException("GET: Job Application with:" + id +  " not found");
		
		return a;
		
	}
//	Get Job Posting’s Applications (for Manager)
//	Go through all applications that have job_application id matching the given job posting->id
//	Return XML of Job Applications
	@GET
	@Path("/jobapplication/search")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JobApplications getSearchJobApplications(@DefaultValue(".*") @QueryParam("jobpostid") String query) {

		//search description
		System.out.println("Search Job Application Id:" + query);
		JobApplications allJobApplications = JobsDAO.instance.searchJobApplicationsPostId(query);
		
		if(allJobApplications==null)
			throw new RuntimeException("GET: No Job Applications with Job Application Id:" + query);
		
		return allJobApplications;
		
	}
	
//	Get Job Applications assigned for review
//	Search (Job Application Assignments), if reviewer-id matches either of assigned reviewer ids, return job application-ids
//	Return list of job-application ids and URIs
	//TODO::
	
	
//	Get All Job Applications
//	Return (Job Applications) (all Job Applications inside)
//	Return XML of all Job Applications
	@GET
	@Path("/jobapplication/all")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JobApplications getAllJobApplications() {
		
		JobApplications allJobApps = JobsDAO.instance.getAllJobApplications();
		
		if(allJobApps==null)
			throw new RuntimeException("GET: No Job Applications found");
		
		return allJobApps;
	}
	
//	PUT:
//	Update Job Application (if job posting is still open)
//	Delete old job Application
//	Replace with one provided
//	Returns Success
//	Update Accept/Reject Application Invitation status (after shortlisted)
//	Check that shortlisted 
//	Search and delete old job Application
//	Replace (create) with one provided
//	Returns Success
	@PUT
	@Path("/jobapplication")
	@Consumes(MediaType.APPLICATION_XML)
	public Response putJobApplication(JobApplication p) {
		Response res = null;
		String msg = "success";
		
		//check job application exists
		//check user profile exists
		JobPosting existingPost = JobsDAO.instance.getJobPosting(p.getJobPostId());
		UserProfile existingUser = JobsDAO.instance.getUserProfile(p.getUserProfileId());
	
		if(existingPost == null && existingUser == null){
			throw new RuntimeException("POST: No Job Postings found with id:" + p.getJobPostId() + " and no User found with id:" + p.getUserProfileId());
		} else if(existingPost == null){
			throw new RuntimeException("POST: No Job Postings found with id:" + p.getJobPostId());
		} else if(existingUser == null){
			throw new RuntimeException("POST: No User found with id:" + p.getUserProfileId());
		}
		

		//store profile
		JobsDAO.instance.storeJobApplication(p);
		//Probably should modify test to be in xml format or something :/
		res = Response.ok(msg).build();
		//res.
		return res;
		//TODO: Fix here so that it returns the updated job
	}
	
//	DELETE:
//	Withdraw Application (Archive application, if completed workflow)
//	Returns Success
	@DELETE
	@Path("/jobapplication/{id}")
	public Response deleteJobApplication(@PathParam("id") String id) {
		
		JobApplication delApp = JobsDAO.instance.deleteJobApplication(id);
		//modify.s
		
		Response res = null;
		int errorCode = 220;
		
		if(delApp == null) {
			//throw new RuntimeException("DELETE: Book with " + id +  " not found");
			String msg = "DELETE: Application with " + id +  " not found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(errorCode);
			res = resBuild.build();
			//res = Response.status(Response.Status.BAD_REQUEST).build();
		} else {
			String msg = "Deleted Application:" + id;
			res = Response.ok(msg).build();
		}
		return res;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	//Hiring Team 
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	Hiring Team for Company
//	POST:
//	Create Team for a company:
//	Member 1 id, 
//	Member 2 id,
//	Member 3 id, 
//	Member 4 id, 
//	Member 5 id,
//	Returns aid list of team members usernames
	@POST
	@Path("/hiringteam")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newHiringTeam(
			@FormParam("companyprofileid") String companyProfileId,
			@FormParam("member1id") String member1id,
			@FormParam("member2id") String member2id,
			@FormParam("member3id") String member3id,
			@FormParam("member4id") String member4id,
			@FormParam("member5id") String member5id
	) throws IOException {
		String id = JobsDAO.instance.getNextTeamMemberProfileId();
		
		//check company exists
		CompanyProfile existCompany = JobsDAO.instance.getCompanyProfile(companyProfileId);
		//get users
		TeamMemberProfile teamMember1 = JobsDAO.instance.getTeamMemberProfile(member1id);
		TeamMemberProfile teamMember2 = JobsDAO.instance.getTeamMemberProfile(member2id);
		TeamMemberProfile teamMember3 = JobsDAO.instance.getTeamMemberProfile(member3id);
		TeamMemberProfile teamMember4 = JobsDAO.instance.getTeamMemberProfile(member4id);
		TeamMemberProfile teamMember5 = JobsDAO.instance.getTeamMemberProfile(member5id);

		if(teamMember1 == null || teamMember2 == null || teamMember3 == null || teamMember4 == null || teamMember5 == null || existCompany == null){
			//return error that user's do not exist
			String errormsg = new String();
			if(teamMember1 == null || teamMember2 == null || teamMember3 == null || teamMember4 == null || teamMember5 == null){
				errormsg += "POST: The following members do not exist: {";
				if(teamMember1 == null){
					errormsg +="teamMember1=" + member1id + ",";
				} 
				if(teamMember2 == null){
					errormsg +="teamMember1=" + member2id + ",";
				} 
				if(teamMember3 == null){
					errormsg +="teamMember3=" + member3id+ ",";
				} 
				if(teamMember4 == null){
					errormsg +="teamMember4=" + member4id + ",";
				} 
				if(teamMember5 == null){
					errormsg +="teamMember5=" + member5id;
				}
				errormsg+= "}";
			}
			if(existCompany == null){
				errormsg = "POST: Company with id:" + companyProfileId + " does not exist";
			}
			throw new RuntimeException(errormsg);
		}
		
		
		//create new profile
		HiringTeamStore newHiringTeam = new HiringTeamStore(id, companyProfileId, member1id, member2id, member3id, member4id, member5id);
				
		//store profile
		JobsDAO.instance.storeHiringTeam(newHiringTeam);
		
		//System.out.println("Name Recorded is:" + JobsDAO.instance.getUserProfile("hi").getName());
		//getStore().put(id, b);
		
		//TODO: Fix here so that it returns the new book
		Response res = null;
		res = Response.ok(newHiringTeam).build();
		return res;
	}
//	GET:
//	Get Hiring Team Members List of a company
//	List in XML
	@GET
	@Path("/hiringteam/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public HiringTeamStore getHiringTeam(@PathParam("id") String id) {
		//get Hiring Team Store
		HiringTeamStore reqHiringTeam = JobsDAO.instance.getHiringTeam(id);
		
		if(reqHiringTeam == null)
			throw new RuntimeException("GET: Hiring Team Store with:" + id +  " not found");
		
		return reqHiringTeam;
		
		/*
		String companyProfileId = reqHiringTeam.getCompanyProfileId();
		String member1id = reqHiringTeam.getMember1id();
		String member2id = reqHiringTeam.getMember2id();
		String member3id = reqHiringTeam.getMember3id();
		String member4id = reqHiringTeam.getMember4id();
		String member5id = reqHiringTeam.getMember5id();
		
		//get users
		TeamMemberProfile teamMember1 = JobsDAO.instance.getTeamMemberProfile(member1id);
		TeamMemberProfile teamMember2 = JobsDAO.instance.getTeamMemberProfile(member2id);
		TeamMemberProfile teamMember3 = JobsDAO.instance.getTeamMemberProfile(member3id);
		TeamMemberProfile teamMember4 = JobsDAO.instance.getTeamMemberProfile(member4id);
		TeamMemberProfile teamMember5 = JobsDAO.instance.getTeamMemberProfile(member5id);
		
		List<TeamMemberProfile> teamMembers = new ArrayList<TeamMemberProfile>();
		teamMembers.add(teamMember1);
		teamMembers.add(teamMember2);
		teamMembers.add(teamMember3);
		teamMembers.add(teamMember4);
		teamMembers.add(teamMember5);
		
		//create new profile to transfer
		HiringTeam newHiringTeam = new HiringTeam(id, companyProfileId, teamMembers);
		
		return newHiringTeam;
		*/
	}
//	PUT:
//	Unsupported
	
	@PUT
	@Path("/hiringteam")
	@Consumes(MediaType.APPLICATION_XML)
	public Response putHiringTeam(HiringTeamStore t) {
		Response res = null;
		
		//check company exists
		CompanyProfile existCompany = JobsDAO.instance.getCompanyProfile(t.getCompanyProfileId());
		//get users
		TeamMemberProfile teamMember1 = JobsDAO.instance.getTeamMemberProfile(t.getMember1id());
		TeamMemberProfile teamMember2 = JobsDAO.instance.getTeamMemberProfile(t.getMember2id());
		TeamMemberProfile teamMember3 = JobsDAO.instance.getTeamMemberProfile(t.getMember3id());
		TeamMemberProfile teamMember4 = JobsDAO.instance.getTeamMemberProfile(t.getMember4id());
		TeamMemberProfile teamMember5 = JobsDAO.instance.getTeamMemberProfile(t.getMember5id());

		if(teamMember1 == null || teamMember2 == null || teamMember3 == null || teamMember4 == null || teamMember5 == null || existCompany == null){
			//return error that user's do not exist
			String errormsg = new String();
			if(teamMember1 == null || teamMember2 == null || teamMember3 == null || teamMember4 == null || teamMember5 == null){
				errormsg += "POST: The following members do not exist: {";
				if(teamMember1 == null){
					errormsg +="teamMember1=" + t.getMember1id() + ",";
				} 
				if(teamMember2 == null){
					errormsg +="teamMember1=" + t.getMember2id() + ",";
				} 
				if(teamMember3 == null){
					errormsg +="teamMember3=" + t.getMember3id()+ ",";
				} 
				if(teamMember4 == null){
					errormsg +="teamMember4=" + t.getMember4id() + ",";
				} 
				if(teamMember5 == null){
					errormsg +="teamMember5=" + t.getMember5id();
				}
				errormsg+= "}";
			}
			if(existCompany == null){
				errormsg = "POST: Company with id:" + t.getCompanyProfileId() + " does not exist";
			}
			throw new RuntimeException(errormsg);
		}
		
		
		//store profile
		JobsDAO.instance.storeHiringTeam(t);
		
		String msg = "success";		
		//Probably should modify test to be in xml format or something :/
		res = Response.ok(msg).build();
		//res.
		return res;
		//TODO: Fix here so that it returns the updated job
	}
	
//	DELETE:
//	Remove the hiring team from a company
	@DELETE
	@Path("/hiringteam/{id}")
	public Response deleteHiringTeam(@PathParam("id") String id) {
		
		HiringTeamStore delProfile = JobsDAO.instance.deleteHiringTeam(id);
		//modify.s
		
		Response res = null;
		int errorCode = 220;
		
		if(delProfile == null) {
			//throw new RuntimeException("DELETE: Book with " + id +  " not found");
			String msg = "DELETE: Team with " + id +  " not found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(errorCode);
			res = resBuild.build();
			//res = Response.status(Response.Status.BAD_REQUEST).build();
		} else {
			String msg = "Deleted Team Profile:" + id;
			res = Response.ok(msg).build();
		}
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	//Hiring Team Member
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	POST:
//	Create a new team member
	@POST
	@Path("/teammemberprofile")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newTeamMemberProfile(
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("professionalskills") String professionalSkills
	) throws IOException {
		String id = JobsDAO.instance.getNextTeamMemberProfileId();
				
		//create new profile
		TeamMemberProfile newTeamMemberProfile = new TeamMemberProfile(id, username, password, professionalSkills);
				
		//store profile
		JobsDAO.instance.storeTeamMemberProfile(newTeamMemberProfile);
		
		//System.out.println("Name Recorded is:" + JobsDAO.instance.getUserProfile("hi").getName());
		//getStore().put(id, b);
		
		//TODO: Fix here so that it returns the new book
		Response res = null;
		res = Response.ok(newTeamMemberProfile).build();
		return res;
	}
//	GET
//	Get Job Application’s Assigned members
	@GET
	@Path("/teammemberprofile/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public TeamMemberProfile getTeamMemberProfile(@PathParam("id") String id) {
		
		TeamMemberProfile u = JobsDAO.instance.getTeamMemberProfile(id);
		if(u==null)
			throw new RuntimeException("GET: Team Member Profile with:" + id +  " not found");
		
		return u;
		
	}
//	PUT
//	(same as userprofile)
	@PUT
	@Path("/teammemberprofile")
	@Consumes(MediaType.APPLICATION_XML)
	public Response putTeamMemberProfile(TeamMemberProfile p) {
		Response res = null;
		String msg = "success";
		
		//store profile
		JobsDAO.instance.storeTeamMemberProfile(p);
		//Probably should modify test to be in xml format or something :/
		res = Response.ok(msg).build();
		//res.
		return res;
		//TODO: Fix here so that it returns the updated job
	}
//	DELETE:
//	Removal: Not supported.  
	@DELETE
	@Path("/teammemberprofile/{id}")
	public Response deleteTeamMemberProfile(@PathParam("id") String id) {
		
		TeamMemberProfile delProfile = JobsDAO.instance.deleteTeamMemberProfile(id);
		//modify.s
		
		Response res = null;
		int errorCode = 220;
		
		if(delProfile == null) {
			//throw new RuntimeException("DELETE: Book with " + id +  " not found");
			String msg = "DELETE: Team Member Profile with " + id +  " not found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(errorCode);
			res = resBuild.build();
			//res = Response.status(Response.Status.BAD_REQUEST).build();
		} else {
			String msg = "Deleted Team Member Profile:" + id;
			res = Response.ok(msg).build();
		}
		return res;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////Review Assignment
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//POST:
	//Assign two company team members to an application
	@POST
	@Path("/jobappreviewassign")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newReviewAssignment(
			@FormParam("jobapplicationid") String jobApplicationId,
			@FormParam("reviewer1") String reviewer1,
			@FormParam("reviewer2") String reviewer2
	) throws IOException {
		String id = JobsDAO.instance.getJobApplicationAssignmentId();
		
		//TODONE: check application exists
		JobApplication existingApp =  JobsDAO.instance.getJobApplication(jobApplicationId);
		//TODONE: check reviewers exist
		TeamMemberProfile existRev1 = JobsDAO.instance.getTeamMemberProfile(reviewer1);
		TeamMemberProfile existRev2 = JobsDAO.instance.getTeamMemberProfile(reviewer2);
		
		if(existingApp == null || existRev1 == null || existRev2 == null){
			String errormsg = "POST:";
			
			if(existingApp == null){
				errormsg+= "Job Application with id:" + jobApplicationId + " doesn't exist.";
			} 

			if (existRev1 == null || existRev2 == null){
				errormsg+= "Reviews with id:";
				if(existRev1 == null){
					errormsg+= reviewer1 + ",";
				}
				if (existRev2 == null){
					errormsg+= reviewer2;
				}
				errormsg+= " doesn't exist.";
			}

			throw new RuntimeException(errormsg);
			
		}
		
		//create new ass
		JobApplicationAssignment newJobApplicationAssignment = new JobApplicationAssignment(id, jobApplicationId, reviewer1, reviewer2);
				
		//store ass
		JobsDAO.instance.storeJobApplicationAssignment(newJobApplicationAssignment);
		
		//System.out.println("Name Recorded is:" + JobsDAO.instance.getUserProfile("hi").getName());
		//getStore().put(id, b);
		
		//TODO: Fix here so that it returns the new book
		Response res = null;
		res = Response.ok(newJobApplicationAssignment).build();
		return res;
	}
	//GET
	//Get Job Application’s Assigned members
	@GET
	@Path("/jobappreviewassign/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JobApplicationAssignment getReviewAssignment(@PathParam("id") String id) {
		
		JobApplicationAssignment u = JobsDAO.instance.getJobApplicationAssignment(id);
		if(u==null)
			throw new RuntimeException("GET: Job Application Assignment with:" + id +  " not found");
		
		return u;
		
	}
	//PUT
	//Update: Not supported
	
	//DELETE:
	//Removal: Not supported.  

	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Reviews: By Hiring Team Member
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	POST:
//	Create Review for a job application
//	Return URI for review (GET address)
	@POST
	@Path("/review")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newReview(
			@FormParam("teammemberprofileid") String teamMemberProfileId,
			@FormParam("jobapplicationid") String jobApplicationId,
			@FormParam("comments") String comments,
			@FormParam("decision") String decision
	) throws IOException {
		String id = JobsDAO.instance.getNextReviewId();	
		
		//TODONE: check member exists
		JobApplication existingApp =  JobsDAO.instance.getJobApplication(jobApplicationId);
		//TODONE: check application exist
		TeamMemberProfile existRev = JobsDAO.instance.getTeamMemberProfile(teamMemberProfileId);
		
		if(existingApp == null || existRev == null){
			String errormsg = "POST:";
			
			if(existingApp == null){
				errormsg+= "Job Application with id:" + jobApplicationId + " doesn't exist.";
			} 

			if (existRev == null){
				errormsg+= "Review with id:" + teamMemberProfileId + " doesn't exist.";
			}

			throw new RuntimeException(errormsg);
			
		}
		
		//create new ass
		Review newReview = new Review(id, teamMemberProfileId, jobApplicationId, comments, decision);
				
		//store ass
		JobsDAO.instance.storeReview(newReview);
		
		//System.out.println("Name Recorded is:" + JobsDAO.instance.getUserProfile("hi").getName());
		//getStore().put(id, b);
		
		//TODO: Fix here so that it returns the new book
		Response res = null;
		res = Response.ok(newReview).build();
		return res;
	}
//	GET:
//	Get a single review
//	Get job application’s reviews
//	Get all reviews
	@GET
	@Path("/review/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Review getReview(@PathParam("id") String id) {
		
		Review u = JobsDAO.instance.getReview(id);
		if(u==null)
			throw new RuntimeException("GET: Review with:" + id +  " not found");
		
		return u;
	}
	
//	PUT:
//	Not Supported: Review is finalised once submitted
	
//	DELETE:
//	Not Supported: Review is attached to a job application forever


	
	
	
	
	/*
	// Return the list of Jobs for client applications/programs
	@GET
	//Create a new path for job posting, hiring etc..
	@Path("/jobsearch")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<JobApplication> getJobPostings() {
		List<JobApplication> bs = new ArrayList<JobApplication>();
		bs.addAll( JobsDAO.instance.getStore().values() );
		return bs; 
	}
	
	@GET
	//Create a new path for job posting, hiring etc..
	@Path("/jobapps")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<JobApplication> getJobApplications() {
		List<JobApplication> bs = new ArrayList<JobApplication>();
		bs.addAll( JobsDAO.instance.getStore().values() );
		return bs; 
	}
	
	@GET
	//Create a new path for job posting, hiring etc..
	@Path("/appinvitations")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<JobApplication> getApplicationInvitations() {
		List<JobApplication> bs = new ArrayList<JobApplication>();
		bs.addAll( JobsDAO.instance.getStore().values() );
		return bs; 
	}
	
	// Return the list of Jobs for client applications/programs
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<JobApplication> getBooks() {
		List<JobApplication> bs = new ArrayList<JobApplication>();
		bs.addAll( JobsDAO.instance.getStore().values() );
		return bs; 
	}
	
	// Return the number of books in the bookstore
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = JobsDAO.instance.getStore().size();
		return String.valueOf(count);
	}
	
	// POST should not expect an id
	// It should generate an id and let user know in response
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newBook(
			@FormParam("title") String title,
			@FormParam("detail") String detail
	) throws IOException {
		String id = "hi";
		JobApplication b = new JobApplication(id,title);
		if (detail!=null){
			b.setDetail(detail);
		}
		JobsDAO.instance.getStore().put(id, b);
		//TODO: Fix here so that it returns the new book
		Response res = null;
		
		return res;
	}
	
	
	//Calling Get on the URL
	@GET
	@Path("{book}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JobApplication getBook(@PathParam("book") String id) {
		JobApplication b = JobsDAO.instance.getStore().get(id);
		if(b==null)
			throw new RuntimeException("GET: Book with" + id +  " not found");
		return b;
	}
	
	//Calling Put on the URL
	//Parameter book
	//Expect type job application for some reason 

//	
//	  <jobApplication>
//		  <id>Tove</id>
//		  <title>Jani</title>
//		</jobApplication>
//	 
	@PUT
	@Path("{jobApp}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response putBook(JobApplication b) {
		Response res = null;
		String test = "test";
		//Probably should modify test to be in xml format or something :/
		res = Response.ok(test).build();
		//res.
		return res;
	}
	
	//Calling Delete on the URL
	//Maybe return a response to tell the user it has been deleted
	@DELETE
	@Path("{jobDelete}")
	public Response deleteBook(@PathParam("jobDelete") String id) {
		JobApplication delb = JobsDAO.instance.getStore().remove(id);
		Response res = null;
		int errorCode = 220;
		
		if(delb==null) {
			//throw new RuntimeException("DELETE: Book with " + id +  " not found");
			String msg = "DELETE: Job with " + id +  " not found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(errorCode);
			res = resBuild.build();
			//res = Response.status(Response.Status.BAD_REQUEST).build();
		} else {
			String msg = "Deleted Job" + id;
			res = Response.ok(msg).build();
		}
		return res;
	}
	
	private Response putAndGetResponse(JobApplication b) {
		Response res;
		if(JobsDAO.instance.getStore().containsKey(b.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		JobsDAO.instance.getStore().put(b.getId(), b);
		return res;
	}
	
		*/
}
