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
import au.edu.unsw.soacourse.job.model.JobApplication;
import au.edu.unsw.soacourse.job.model.JobApplications;
import au.edu.unsw.soacourse.job.model.JobPosting;
import au.edu.unsw.soacourse.job.model.JobPostings;
import au.edu.unsw.soacourse.job.model.UserProfile;

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
//	POST:
//	Create new user
//	Create a new (User Profile) with a new id set with given values
//	Return new user id (and it’s get URL)
	@POST
	@Path("/userprofile")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newUserProfile(
			@FormParam("name") String name,
			@FormParam("currentPosition") String currentPosition,
			@FormParam("education") String education,
			@FormParam("pastExperience") String pastExperience,
			@FormParam("professionalSkills") String professionalSkills
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
		res = Response.ok(id).build();
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
		res = Response.ok(id).build();
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
			throw new RuntimeException("GET: Book with:" + id +  " not found");
		
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
//	POST:
//	Create Job Posting
//	Create a new (Job Posting) with a new id set with given values
//	Return new id URI to Company
	@POST
	@Path("/jobposting")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newJobPosting(
			@FormParam("description") String description,
			@FormParam("companyProfileId") String companyProfileId,
			@FormParam("positionType") String positionType,
			@FormParam("desiredSkills") String desiredSkills,
			@FormParam("salaryLevel") String salaryLevel,
			@FormParam("location") String location
	) throws IOException {
		String id = JobsDAO.instance.getNextJobPostingId();
		
		//create new profile
		JobPosting newJobPosting= new JobPosting(id, description, companyProfileId,
				positionType, desiredSkills, salaryLevel,
				location);
				
		//store profile
		JobsDAO.instance.storeJobPosting(newJobPosting);
		
		//System.out.println("Name Recorded is:" + JobsDAO.instance.getUserProfile("hi").getName());
		//getStore().put(id, b);
		
		//TODO: Fix here so that it returns the new book
		Response res = null;
		res = Response.ok(id).build();
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
	public JobPostings getSearchJobPostings(@DefaultValue(".*") @QueryParam("query") String query) {

		//search description
		System.out.println("Search query:" + query);
		JobPostings allJobPosts = JobsDAO.instance.searchJobPostingDescription(query);
		
		if(allJobPosts==null)
			throw new RuntimeException("GET: No Job Postings not found for query:" + query);
		
		return allJobPosts;
		
	}
	
//	Get All
	@GET
	@Path("/jobposting/all")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JobPostings getAllJobPostings() {
		
		JobPostings allJobPosts = JobsDAO.instance.getAllJobPostings();
		
		if(allJobPosts==null)
			throw new RuntimeException("GET: No Job Postings not found");
		
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
//	POST:
//	Create Job Application (Apply to a Job Posting)
//	Create a new job application with new id and given details
//	Returns: new Job Application ID (and it’s get URI)
	@POST
	@Path("/jobapplication")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newJobApplication(
			@FormParam("description") String jobApplicationId,
			@FormParam("companyProfileId") String userProfileId,
			@FormParam("positionType") String coverLetter,
			@FormParam("desiredSkills") String resume
	) throws IOException {
		String id = JobsDAO.instance.getNextJobApplicationId();
			
		//create new application
		JobApplication newJobApp= new JobApplication(id, jobApplicationId,
				userProfileId, coverLetter, resume);
				
		//store application
		JobsDAO.instance.storeJobApplication(newJobApp);
		
		//System.out.println("Name Recorded is:" + JobsDAO.instance.getUserProfile("hi").getName());
		//getStore().put(id, b);
		
		//TODO: Fix here so that it returns the new book
		Response res = null;
		res = Response.ok(id).build();
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
//	//Hiring Team Member
//	Hiring Team for Company
//	POST:
//	Create Team for a company:
//	Member 1, username, pw
//	Member 2, username, pw
//	Member 3, username, pw
//	Member 4, username, pw
//	Member 5, username, pw
//	Returns a list of team members usernames
	
//	GET:
//	Get Hiring Team Members List of a company
//	List in XML
	
//	PUT:
//	Update Hiring Team members and assignments for a company
	
//	DELETE:
//	Remove the hiring team from a company
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	//Review Assignment
//	POST:
//	Assign two company team members to an application
	
//	GET
//	Get Job Application’s Assigned members
	
//	PUT
//	Update: Not supported

//	DELETE:
//	Removal: Not supported.  
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Reviews: By Hiring Team Member
//	POST:
//	Create Review for a job application
//	Return URI for review (GET address)
	
//	GET:
//	Get a single review
//	Get job application’s reviews
//	Get all reviews
	
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
		//TODO: Fix here so that it returns the updated job
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
