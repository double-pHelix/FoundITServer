package au.edu.unsw.soacourse.job.model;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JobApplications {
	@XmlElement(name = "jobApplication")
	List<JobApplication> jobApplications = new ArrayList<JobApplication>();
 
	public JobApplications() {
		
	}
	
	public JobApplications(List<JobApplication> jobApplications) {
		super();
		this.jobApplications = jobApplications;
	}

	public List<JobApplication> getJobApplications() {
		return jobApplications;
	}
	
	public void setJobApplications(List<JobApplication> jobApplications) {
		this.jobApplications = jobApplications;
	}
	   
   
}  