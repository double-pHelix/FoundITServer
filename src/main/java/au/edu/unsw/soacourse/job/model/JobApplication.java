package au.edu.unsw.soacourse.job.model;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JobApplication {
	private String id;
    private String jobApplicationId;
    private String userProfileId;
    private String coverLetter;
    private String resume; //Maybe just a text pretending to be an attachment (i.e. �resume.pdf�)
    private String status; // (shortlisted/not-shortlisted or rejected/accepted)
    private String archived; //Y or N
    
    public JobApplication() {

	}
    
    public JobApplication(String id) {
		super();
		this.id = id;
		this.jobApplicationId = "PLEASE ENTER";
		this.userProfileId = "PLEASE ENTER";
		this.coverLetter = "PLEASE ENTER";
		this.resume = "PLEASE ENTER";
		this.status = "DEFAULT";
		this.archived = "N";
	}
    
    public JobApplication(String id, String jobApplicationId,
			String userProfileId, String coverLetter, String resume) {
		super();
		this.id = id;
		this.jobApplicationId = jobApplicationId;
		this.userProfileId = userProfileId;
		this.coverLetter = coverLetter;
		this.resume = resume;
		this.status = "DEFAULT";
		this.archived = "N";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobApplicationId() {
		return jobApplicationId;
	}

	public void setJobApplicationId(String jobApplicationId) {
		this.jobApplicationId = jobApplicationId;
	}

	public String getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(String userProfileId) {
		this.userProfileId = userProfileId;
	}

	public String getCoverLetter() {
		return coverLetter;
	}

	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getArchived() {
		return archived;
	}

	public void setArchived(String archived) {
		this.archived = archived;
	}
    

}
