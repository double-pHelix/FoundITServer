package au.edu.unsw.soacourse.job.model;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JobPostings {
	@XmlElement(name = "jobPosting")
	List<JobPosting> jobPostings = new ArrayList<JobPosting>();

	public JobPostings(List<JobPosting> jobPostings) {
		super();
		this.jobPostings = jobPostings;
	}

	public List<JobPosting> getJobPostings() {
		return jobPostings;
	}
	
	public void setJobPostings(List<JobPosting> jobPostings) {
		this.jobPostings = jobPostings;
	}
	   
 
}  