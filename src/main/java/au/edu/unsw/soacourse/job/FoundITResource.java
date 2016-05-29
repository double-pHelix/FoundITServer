package au.edu.unsw.soacourse.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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

import au.edu.unsw.soacourse.job.dao.JobAlertDAO;
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


//TODO::SECURITY
//key == match
//

//TODONE:: Modify output to include a GET url like in the lecture slides DONE!
//TODONE:: Provide @OPTION method for classes (NOPE)
//TODONE:: Delete only archives (Job Posting, Job Application)
//TODONE: Processes for
	//if reviews are in set status for application

//TODO: input checks
	//If job post is closed reject application
	//If PUT status isn't valid reject 

//TODONE: remove invalid postings once companyis removed? etc..
	//TOO HARD WE AVOID THIS!
//TODONE: prevent team member belonging to two hiring teams?
	//if aleady in a team, return error
//TODONE: only one assignment per application
//TODONE: Requried Fields?? For post
//TODONE:: stop nested elements (at most one layer)... or prevent null elements
//TODO::Update Members1-5 to be a list of TeamMemberProfile of size 5... (note passwords being sent as well lol)
//TODO::Change reviewer to TeamMemberProfile with URI
//TODO::ERROR HANDLING CODES...

//We can change this path
@Path("/")
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
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey,
			@FormParam("name") String name,
			@FormParam("currentposition") String currentPosition,
			@FormParam("education") String education,
			@FormParam("pastexperience") String pastExperience,
			@FormParam("professionalskills") String professionalSkills
	) throws IOException {
		String id = JobsDAO.instance.getNextUserProfileId();

		Response res = null;

		//check no input is empty
		if(name == null || currentPosition == null || education == null || 
				pastExperience == null || professionalSkills == null){
			//res.status();
			res = Response.status(201).entity("POST: Missing field/s").build();
			return res;
		}
		//create new profile
		UserProfile newProfile = new UserProfile(id, name, currentPosition, education, pastExperience, professionalSkills);
				
		//store profile
		JobsDAO.instance.storeUserProfile(newProfile);
						
		//res.status();
		res = Response.status(201).entity(newProfile).build();
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
	public Response getUserProfile(@PathParam("id") String id) {
		Response res = null;
		UserProfile u = JobsDAO.instance.getUserProfile(id);
		
		if(u == null){
			//throw new RuntimeException("DELETE: Book with " + id +  " not found");
			String msg = "DELETE: Job with " + id +  " not found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
			//res = Response.status(Response.Status.BAD_REQUEST).build();
			
			//throw new RuntimeException("GET: Book with:" + id +  " not found");

		} else {
			res = Response.ok(u).build();
		}
		
		return res;
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
		res = Response.ok(test).build();
		//res.
		return res;
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
		int errorCode = 400;
		
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
			@FormParam("industrytype") String industryType,
			@FormParam("address") String address
	) throws IOException {
		String id = JobsDAO.instance.getNextCompanyProfileId();

		//check no input is empty
		if(name == null || description == null || website == null || 
				industryType == null || address == null){
			Response res = null;
			//res.status();
			res = Response.status(201).entity("POST: Missing field/s").build();
			return res;
		}
		//create new profile
		CompanyProfile newProfile = new CompanyProfile(id, name, description, website, industryType, address);
				
		//store profile
		JobsDAO.instance.storeCompanyProfile(newProfile);
		
		//System.out.println("Name Recorded is:" + JobsDAO.instance.getUserProfile("hi").getName());
		//getStore().put(id, b);
		
		Response res = null;
		res = Response.status(201).entity(newProfile).build();
		return res;
	}
//	GET:
//	Get company information
//	Look up (Company Profile) with given “id”
//	Return company information in XML
	@GET
	@Path("/companyprofile/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getCompanyProfile(@PathParam("id") String id) {
		Response res = null;
		CompanyProfile c = JobsDAO.instance.getCompanyProfile(id);
		
		//if(c==null)
		//	throw new RuntimeException("GET: Profile with:" + id +  " not found");
		if(c == null){
			//throw new RuntimeException("DELETE: Book with " + id +  " not found");
			String msg = "GET: Profile with:" + id +  " not found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
			//res = Response.status(Response.Status.BAD_REQUEST).build();
			
			//throw new RuntimeException("GET: Book with:" + id +  " not found");
		} else {
			res = Response.ok(c).build();
		}
		
		return res;
		
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
		int errorCode = 400;
		
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

		Response res = null;
		//check no input is empty
		if(title == null || description == null || 
				positionType == null || desiredSkills == null || 
						salaryLevel == null || location == null){
			//res.status();
			res = Response.status(201).entity("POST: Missing field/s").build();
			return res;
		}
		//check company id exists
		CompanyProfile existingComp = JobsDAO.instance.getCompanyProfile(companyProfileId);
		if(existingComp == null){
			//res.status();
			res = Response.status(201).entity("POST: Company with id:" + companyProfileId + "does not exist").build();
			return res;
		}
		
		//create new profile
		JobPosting newJobPosting= new JobPosting(id, title, description, companyProfileId,
				positionType, desiredSkills, salaryLevel,
				location);
				
		//store profile
		JobsDAO.instance.storeJobPosting(newJobPosting);
		
		//System.out.println("Name Recorded is:" + JobsDAO.instance.getUserProfile("hi").getName());
		//getStore().put(id, b);
		
		JobPosting sendJobPosting= new JobPosting(id, title, description, companyProfileId,
				positionType, desiredSkills, salaryLevel,
				location);
		sendJobPosting.setSendVersion(true);
		
		res = Response.status(201).entity(sendJobPosting).build();
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
	public Response getJobPosting(@PathParam("id") String id) {
				
		JobPosting p = JobsDAO.instance.getJobPosting(id);
		Response res = null;

		if(p == null){
			String msg = "GET: Job Posting with:" + id +  " not found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
			//res = Response.status(Response.Status.BAD_REQUEST).build();
			
		} else {
			JobPosting sendJobPosting= new JobPosting(p.getId(), p.getTitle(), p.getDescription(), p.getCompanyProfileId(),
					p.getPositionType(), p.getSkills(), p.getSalaryLevel(),
					p.getLocation());
			sendJobPosting.setSendVersion(true);
			
			res = Response.ok(sendJobPosting).build();
		}
		

		return res;
		
	}
//	Get a Collection i.e Job Search/id={“Job Application id”} or skills/etc..=”REGEX MATCH”
//	Go through each Job Posting and check if they match the query (just start with id for now)... and are NOT archived
//	Return an XML of those job postings (full)
	@GET
	@Path("/jobposting/search")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getSearchJobPostings(@QueryParam("keyword") String keyword, //title
			@QueryParam("title") String title,
			@QueryParam("skills") String skills,
			@QueryParam("status") String status,
			@QueryParam("description") String description
														) {
		Response res = null;

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
		
		//if there was an error
		if(allJobPosts==null){
			String msg = new String();

			//res = Response.status(Response.Status.BAD_REQUEST).build();
			if(keyword != null){
				msg = "GET: No Job Postings not found for keyword:" + keyword;
			} else if(title != null){
				msg = "GET: No Job Postings not found for title:" + title;
			} else if(skills != null){
				msg = "GET: No Job Postings not found for skills:" + skills;
			} else if(status!= null){
				msg = "GET: No Job Postings not found for status:" + status;
			} else if(description!= null){
				msg = "GET: No Job Postings not found for description:" + description;
			} else {
				msg = "GET: No Query Provided";
			}
			
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
			
		} else {
			res = Response.ok(allJobPosts).build();
		}
		
		return res;
		
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
	public Response getAllJobPostings() {
		Response res = null;
		
		JobPostings allJobPosts = JobsDAO.instance.getAllJobPostings();
		
		if(allJobPosts==null){
			String msg = "GET: No Job Postings found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
		} else {
			res = Response.ok(allJobPosts).build();
		}
		
		return res;
		
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
		int errorCode = 400;
		
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

		Response res = null;
		//check no input is empty
		if(coverLetter == null || 
				resume == null){
			//res.status();
			res = Response.status(201).entity("POST: Missing field/s= coverletter/resume").build();
			return res;
		}
		//check job application exists
		//check user profile exists
		JobPosting existingPost = JobsDAO.instance.getJobPosting(jobpostId);
		UserProfile existingUser = JobsDAO.instance.getUserProfile(userProfileId);
		
		
		if(existingPost == null && existingUser == null){
			String msg = "POST: No Job Postings found with id:" + jobpostId + " and no User found with id:" + userProfileId;
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
			
			//throw new RuntimeException("POST: No Job Postings found with id:" + jobpostId + " and no User found with id:" + userProfileId);
		} else if(existingPost == null){
			String msg = "POST: No Job Postings found with id:" + jobpostId;
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
			
			//throw new RuntimeException("POST: No Job Postings found with id:" + jobpostId);
		} else if(existingUser == null){
			String msg = "POST: No User found with id:" + userProfileId;
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
			
			//throw new RuntimeException("POST: No User found with id:" + userProfileId);
		} else {
		
			//create new application
			JobApplication newJobApp= new JobApplication(id, jobpostId,
					userProfileId, coverLetter, resume);
					
			//store application
			JobsDAO.instance.storeJobApplication(newJobApp);
			
			JobApplication sendJobApp= new JobApplication(id, jobpostId,
					userProfileId, coverLetter, resume);
			sendJobApp.setSendVersion(true);
			
			//System.out.println("Name Recorded is:" + JobsDAO.instance.getUserProfile("hi").getName());
			//getStore().put(id, b);
			
			res = Response.status(201).entity(sendJobApp).build();
		}

		return res;
	}
//	GET:
//	Get Single Application including its Status (shortlisted/not-shortlisted or rejected/accepted)
//	Go through all applications and look i
//	Return XML of Job Application
	@GET
	@Path("/jobapplication/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getJobApplication(@PathParam("id") String id) {
		Response res = null;
		
		JobApplication a = JobsDAO.instance.getJobApplication(id);

		if(a==null){
			String msg = "GET: Job Application with:" + id +  " not found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
		} else {
			JobApplication sendVersion = new JobApplication(a.getId(), a.getJobPostId(), a.getUserProfileId(), a.getCoverLetter(), a.getResume());
			sendVersion.setSendVersion(true);
			res = Response.ok(sendVersion).build();
		}
		
		return res;
		
	}
//	Get Job Posting’s Applications (for Manager)
//	Go through all applications that have job_application id matching the given job posting->id
//	Return XML of Job Applications
	@GET
	@Path("/jobapplication/search")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getSearchJobApplications(@QueryParam("jobpostid") String queryJobPost,
											@QueryParam("userprofileid") String queryUser) {
		Response res = null;
		
		if(queryJobPost != null){
			//search description
			//System.out.println("Search Job Application Id:" + query);
			JobApplications allJobApplications = JobsDAO.instance.searchJobApplicationsPostId(queryJobPost);
			
			if(allJobApplications==null){
				String msg = "GET: No Job Applications with Job Application Id:" + queryJobPost;
				ResponseBuilder resBuild = Response.ok(msg);
				resBuild.status(400);
				res = resBuild.build();
			} else {
				res = Response.ok(allJobApplications).build();
			}
		} else if(queryUser != null){
			JobApplications allJobApplications = JobsDAO.instance.searchJobApplicationsUserId(queryUser);
			
			if(allJobApplications==null){
				String msg = "GET: No Job Applications for User Id:" + queryUser;
				ResponseBuilder resBuild = Response.ok(msg);
				resBuild.status(400);
				res = resBuild.build();
			} else {
				res = Response.ok(allJobApplications).build();
			}
		} else {
			//default returns all
			String query = ".*";
			JobApplications allJobApplications = JobsDAO.instance.searchJobApplicationsPostId(query);
			
			if(allJobApplications==null){
				String msg = "GET: No Job Applications exist";
				ResponseBuilder resBuild = Response.ok(msg);
				resBuild.status(400);
				res = resBuild.build();
			} else {
				res = Response.ok(allJobApplications).build();
			}
		}
		
		return res;
		
	}

//	Get Job Applications assigned for review
//	Search (Job Application Assignments), if reviewer-id matches either of assigned reviewer ids, return job application-ids
//	Return list of job-application ids and URIs
	
	
//	Get All Job Applications
//	Return (Job Applications) (all Job Applications inside)
//	Return XML of all Job Applications
	@GET
	@Path("/jobapplication/all")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getAllJobApplications() {
		Response res = null;
		
		JobApplications allJobApps = JobsDAO.instance.getAllJobApplications();
				
		if(allJobApps==null){
			String msg = "GET: No Job Applications found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
		} else {
			res = Response.ok(allJobApps).build();
		}
		
		return res;
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
		
		//check job application exists
		//check user profile exists
		JobPosting existingPost = JobsDAO.instance.getJobPosting(p.getJobPostId());
		JobApplication currApp = JobsDAO.instance.getJobApplication(p.getId());
		UserProfile existingUser = JobsDAO.instance.getUserProfile(p.getUserProfileId());

		String msg = new String();
		
		//archived already
		if(currApp.getArchived().matches(JobApplication.ARCHIVED_TRUE)){			
			msg = "PUT: Application with id:" + p.getUserProfileId() + " already archived.";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
			return res;
			
			//throw new RuntimeException("PUT: Application with id:" + p.getUserProfileId() + " already archived.");
		}
		
		if(existingPost == null && existingUser == null){
			msg = "PUT: No Job Postings found with id:" + p.getJobPostId() + " and no User found with id:" + p.getUserProfileId();
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
			return res;
			
			//throw new RuntimeException("PUT: No Job Postings found with id:" + p.getJobPostId() + " and no User found with id:" + p.getUserProfileId());
		} else if(existingPost == null){
			msg = "PUT: No Job Postings found with id:" + p.getJobPostId();
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
			return res;
			
			//throw new RuntimeException("PUT: No Job Postings found with id:" + p.getJobPostId());
		} else if(existingUser == null){
			msg = "PUT: No User found with id:" + p.getUserProfileId();
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
			return res;
			//throw new RuntimeException("PUT: No User found with id:" + p.getUserProfileId());
		}
		
		if(p.getStatus().matches(JobApplication.STATUS_ACCEPTED) || p.getStatus().matches(JobApplication.STATUS_REJECTED)){
			if(!currApp.getStatus().matches(JobApplication.STATUS_SHORTLISTED)){
				msg = "PUT: Application cannot be accepted or rejected without being shortlisted.";
				ResponseBuilder resBuild = Response.ok(msg);
				resBuild.status(400);
				res = resBuild.build();
				return res;
				//throw new RuntimeException("PUT: Application cannot be accepted or rejected without being shortlisted.");
			}
		}

		msg = "Success";
		//store profile
		JobsDAO.instance.storeJobApplication(p);
		//Probably should modify test to be in xml format or something :/
		res = Response.ok(msg).build();
		//res.
		return res;
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
		int errorCode = 400;
		
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
		String id = JobsDAO.instance.getNextHiringTeamId();

		Response res = null;
		//check no input is empty
		/*
		if(companyProfileId == "" || member1id == "" || member2id == "" || 
				member3id == "" || member4id == "" || member5id == ""){
			//res.status();
			res = Response.status(201).entity("POST: Missing field/s").build();
			return res;
		}
		*/
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
			ResponseBuilder resBuild = Response.ok(errormsg);
			resBuild.status(400);
			res = resBuild.build();
			return res;
			
			//throw new RuntimeException(errormsg);
		}
		
		//TODO: check that company does not already have a hiring team
		
		//create new profile
		HiringTeamStore newHiringTeam = new HiringTeamStore(id, companyProfileId, member1id, member2id, member3id, member4id, member5id);
				
		//store profile
		JobsDAO.instance.storeHiringTeam(newHiringTeam);
		
		HiringTeamStore sendHiringTeam = new HiringTeamStore(id, companyProfileId, member1id, member2id, member3id, member4id, member5id);
		sendHiringTeam.setSendVersion(true);
		
		//System.out.println("Name Recorded is:" + JobsDAO.instance.getUserProfile("hi").getName());
		//getStore().put(id, b);
		
		res = Response.status(201).entity(sendHiringTeam).build();
		return res;
	}
//	GET:
//	Get Hiring Team Members List of a company
//	List in XML
	@GET
	@Path("/hiringteam/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getHiringTeam(@PathParam("id") String id) {
		Response res = null;
		
		//get Hiring Team Store
		HiringTeamStore reqHiringTeam = JobsDAO.instance.getHiringTeam(id);
		
		if(reqHiringTeam==null){
			String msg = "GET: Hiring Team Store with:" + id +  " not found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
		} else {
			HiringTeamStore sendVersion = new HiringTeamStore(reqHiringTeam.getId(), reqHiringTeam.getCompanyProfileId(), reqHiringTeam.getMember1id(), 
					reqHiringTeam.getMember2id(), reqHiringTeam.getMember3id(), reqHiringTeam.getMember4id(), reqHiringTeam.getMember5id());
			sendVersion.setSendVersion(true);	
			res = Response.ok(sendVersion).build();
		}
		
		return res;
		
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

		if(teamMember1 == null || teamMember2 == null || teamMember3 == null || teamMember4 == null || teamMember5 == null || existCompany == null ||
			JobsDAO.instance.existsInHiringTeam(teamMember1) || JobsDAO.instance.existsInHiringTeam(teamMember2) || JobsDAO.instance.existsInHiringTeam(teamMember3) || 
			JobsDAO.instance.existsInHiringTeam(teamMember4) || JobsDAO.instance.existsInHiringTeam(teamMember5)){
			
			//return error that user's do not exist
			String errormsg = "POST:";
			if(teamMember1 == null || teamMember2 == null || teamMember3 == null || teamMember4 == null || teamMember5 == null){
				errormsg += "The following members do not exist: {";
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
				errormsg+= "}. ";
			}
			if(existCompany == null){
				errormsg = "Company with id:" + t.getCompanyProfileId() + " does not exist. ";
			}
			//if any of them have exist in a team
			if(JobsDAO.instance.existsInHiringTeam(teamMember1) || JobsDAO.instance.existsInHiringTeam(teamMember2) || JobsDAO.instance.existsInHiringTeam(teamMember3) || 
					JobsDAO.instance.existsInHiringTeam(teamMember4) || JobsDAO.instance.existsInHiringTeam(teamMember5)){
				errormsg+= "The following team members are already in a team:";
				
				if(JobsDAO.instance.existsInHiringTeam(teamMember1)){
					errormsg +="teamMember1=" + t.getMember1id() + ",";
				}
				if(JobsDAO.instance.existsInHiringTeam(teamMember2)){
					errormsg +="teamMember1=" + t.getMember2id() + ",";
				}
				if(JobsDAO.instance.existsInHiringTeam(teamMember3)){
					errormsg +="teamMember3=" + t.getMember3id()+ ",";
				}
				if(JobsDAO.instance.existsInHiringTeam(teamMember4)){
					errormsg +="teamMember4=" + t.getMember4id() + ",";
				}
				if(JobsDAO.instance.existsInHiringTeam(teamMember5)){
					errormsg +="teamMember5=" + t.getMember5id();
				}
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
	}
	
//	DELETE:
//	Remove the hiring team from a company
	@DELETE
	@Path("/hiringteam/{id}")
	public Response deleteHiringTeam(@PathParam("id") String id) {
		
		HiringTeamStore delProfile = JobsDAO.instance.deleteHiringTeam(id);
		//modify.s
		
		Response res = null;
		int errorCode = 400;
		
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
		Response res = null;
		//check no input is empty
		if(username == null || password == null || professionalSkills == null){
			//res.status();
			res = Response.status(201).entity("POST: Missing field/s").build();
			return res;
		}
		
		//create new profile
		TeamMemberProfile newTeamMemberProfile = new TeamMemberProfile(id, username, password, professionalSkills);
				
		//store profile
		JobsDAO.instance.storeTeamMemberProfile(newTeamMemberProfile);
		
		res = Response.status(201).entity(newTeamMemberProfile).build();
		return res;
	}
//	GET
//	Get Job Application’s Assigned members
	@GET
	@Path("/teammemberprofile/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getTeamMemberProfile(@PathParam("id") String id) {
		Response res = null;
		
		TeamMemberProfile u = JobsDAO.instance.getTeamMemberProfile(id);
		
		if(u==null){
			String msg = "GET: Team Member Profile with:" + id +  " not found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
		} else {
			res = Response.ok(u).build();
		}
		
		return res;
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
	}
//	DELETE:
//	Removal: Not supported.  
	@DELETE
	@Path("/teammemberprofile/{id}")
	public Response deleteTeamMemberProfile(@PathParam("id") String id) {
		
		TeamMemberProfile delProfile = JobsDAO.instance.deleteTeamMemberProfile(id);
		//modify.s
		
		Response res = null;
		int errorCode = 400;
		
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
		Response res = null;
		//check no input is empty
		/*
		if(jobApplicationId == null || reviewer1 == null || reviewer2 == null){
			
			//res.status();
			res = Response.status(201).entity("POST: Missing field/s").build();
			return res;
		}
		*/
		//TODONE: check application exists
		JobApplication existingApp =  JobsDAO.instance.getJobApplication(jobApplicationId);
		//TODONE: check reviewers exist
		TeamMemberProfile existRev1 = JobsDAO.instance.getTeamMemberProfile(reviewer1);
		TeamMemberProfile existRev2 = JobsDAO.instance.getTeamMemberProfile(reviewer2);
		
		if(JobsDAO.instance.assignmentAlreadyExists(existingApp)){
			throw new RuntimeException("Error: Application already has review team assigned");
		}
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

		JobPosting appPosting = JobsDAO.instance.getJobPosting(existingApp.getJobPostId());
		//check they all belong to the same company
		if(!JobsDAO.instance.reviewersFromCompany(appPosting.getCompanyProfileId(), reviewer1, reviewer2)){
			throw new RuntimeException("POST: 1 or more reviewers do not belong to the appropriate company id:" + appPosting.getCompanyProfileId());
		}
		
		//create new ass
		JobApplicationAssignment newJobApplicationAssignment = new JobApplicationAssignment(id, jobApplicationId, reviewer1, reviewer2);
				
		//store ass
		JobsDAO.instance.storeJobApplicationAssignment(newJobApplicationAssignment);
		
		//send version
		JobApplicationAssignment sendJobApplicationAssignment = new JobApplicationAssignment(id, jobApplicationId, reviewer1, reviewer2);
		sendJobApplicationAssignment.setSendVersion(true);
		
		//System.out.println("Name Recorded is:" + JobsDAO.instance.getUserProfile("hi").getName());
		//getStore().put(id, b);
		
		res = Response.status(201).entity(newJobApplicationAssignment).build();
		return res;
	}
	//GET
	//Get Job Application’s Assigned members
	@GET
	@Path("/jobappreviewassign/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getReviewAssignment(@PathParam("id") String id) {
		Response res = null;
		
		JobApplicationAssignment u = JobsDAO.instance.getJobApplicationAssignment(id);

		if(u==null){
			String msg = "GET: Job Application Assignment with:" + id +  " not found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
		} else {
			JobApplicationAssignment sendJobApplicationAssignment = new JobApplicationAssignment(id, u.getJobApplicationId(), u.getReviewer1(), u.getReviewer2());
			sendJobApplicationAssignment.setSendVersion(true);

			res = Response.ok(sendJobApplicationAssignment).build();
		}
		
		return res;
		
	}
	
	//GET
	//Get Job Application’s Assigned members
	@GET
	@Path("/jobappreviewassign/reviewer/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getReviewersAssignment(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey,
			@PathParam("id") String id) {
		Response res = null;
		if(!SecurityChecker.instance.checkPermisionResource(SecurityChecker.POST_METHOD, SecurityChecker.USER_PROFILE, shortKey)){
			//reject
			res = Response.status(201).entity("POST: User permission denied").build();
			return res;
		} else if(!SecurityChecker.instance.keyAccepted(securityKey)){
			res = Response.status(201).entity("POST: Security key incorrect").build();
			return res;
		}

		JobApplications u = JobsDAO.instance.searchApplicationsTeamMembertId(id);

		if(u==null){
			String msg = "GET: Job Application Assignment with:" + id +  " not found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
		} else {

			res = Response.ok(u).build();
		}
		
		return res;
		
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

		Response res = null;
		//check no input is empty
		if(comments == null || 
				decision == null){
			//res.status();
			res = Response.status(201).entity("POST: Missing field/s = comments/field").build();
			return res;
		}
		
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
		
		//create new rev
		Review newReview = new Review(id, teamMemberProfileId, jobApplicationId, comments, decision);
				
		//store rev
		JobsDAO.instance.storeReview(newReview);
		
		//send version
		Review sendReview = new Review(id, teamMemberProfileId, jobApplicationId, comments, decision);
		sendReview.setSendVersion(true);
		
		res = Response.status(201).entity(sendReview).build();
		return res;
	}
//	GET:
//	Get a single review
//	Get job application’s reviews
//	Get all reviews
	@GET
	@Path("/review/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getReview(@PathParam("id") String id) {
		Response res = null;
		
		Review u = JobsDAO.instance.getReview(id);
		
		if(u==null){
			String msg = "GET: Review with:" + id + " not found";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
		} else {
			//send version
			Review sendReview = new Review(id, u.getTeamMemberProfileId(), u.getJobApplicationId(), u.getComments(), u.getDecision());
			sendReview.setSendVersion(true);
			
			res = Response.ok(sendReview).build();
		}
		
		return res;
		
	}
	
//	PUT:
//	Not Supported: Review is finalised once submitted
	
//	DELETE:
//	Not Supported: Review is attached to a job application forever

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Job Alerts
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//http://foundit-server/jobalerts?keyword={keyword}
   // http://foundit-server/jobalerts?keyword={keyword}&sort_by=jobtitle 
	@GET
	@Path("/jobalerts")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getJobAlerts(@QueryParam("keyword") String keyword, //title
			@QueryParam("sort_by") String sortAttribute,
			@QueryParam("email") String email) {
		Response res = null;

		System.out.println("Received " + keyword + " " + sortAttribute + " " + email);
		JobAlertDAO jobAlert = new JobAlertDAO();
		
		System.out.println("Created Class");
		int errorCode = 400;
		
		if(keyword == null || email == null) {
			String msg = "Keyword or email was not given";
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(errorCode);
			res = resBuild.build();
		}
		
		//System.out.println("Received " + keyword + " " + sortAttribute + " " + email);
		//This only needs to be called once..
		jobAlert.setupFile();
		
		String sort = sortAttribute;
		if (sort != null && sort.length() < 2 ) {
			sort = null;
		}
		
		//Execute query
		String emailID = email.replace(".", "");
		emailID = emailID.replace("@","");
		jobAlert.executeQuery(keyword, sort, emailID);
		jobAlert.sendEmailToUser(email, emailID);
		
		//Successful email sent
		String msg = "Email Sent, Have a nice day!";
		ResponseBuilder resBuild = Response.ok(msg);
		resBuild.status(200);
		res = resBuild.build();
		
		/*
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
		
		//if there was an error
		if(allJobPosts==null){
			String msg = new String();

			//res = Response.status(Response.Status.BAD_REQUEST).build();
			if(keyword != null){
				msg = "GET: No Job Postings not found for keyword:" + keyword;
			} else if(title != null){
				msg = "GET: No Job Postings not found for title:" + title;
			} else if(skills != null){
				msg = "GET: No Job Postings not found for skills:" + skills;
			} else if(status!= null){
				msg = "GET: No Job Postings not found for status:" + status;
			} else if(description!= null){
				msg = "GET: No Job Postings not found for description:" + description;
			} else {
				msg = "GET: No Query Provided";
			}
			
			ResponseBuilder resBuild = Response.ok(msg);
			resBuild.status(400);
			res = resBuild.build();
			
		} else {
			res = Response.ok(allJobPosts).build();
		}
		*/
		
		return res;
		
	}
	
	
}
