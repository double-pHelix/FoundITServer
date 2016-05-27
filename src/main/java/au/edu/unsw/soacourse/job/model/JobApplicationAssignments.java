package au.edu.unsw.soacourse.job.model;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JobApplicationAssignments {
	@XmlElement(name = "JobApplicationAssignment")
	List<JobApplicationAssignment> jobApplicationAssignments = new ArrayList<JobApplicationAssignment>();

	public JobApplicationAssignments() {
		
	}
	
	public JobApplicationAssignments(List<JobApplicationAssignment> jobApplicationAssignments) {
		super();
		this.jobApplicationAssignments = jobApplicationAssignments;
	}

	public List<JobApplicationAssignment> getJobApplicationAssignments() {
		return jobApplicationAssignments;
	}
	
	public void setJobApplicationAssignments(List<JobApplicationAssignment> jobApplicationAssignments) {
		this.jobApplicationAssignments = jobApplicationAssignments;
	}
	   
   
}  