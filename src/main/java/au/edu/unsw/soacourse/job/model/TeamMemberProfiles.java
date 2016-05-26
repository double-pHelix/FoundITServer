package au.edu.unsw.soacourse.job.model;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamMemberProfiles {
	@XmlElement(name = "TeamMemberProfile")
	List<TeamMemberProfile> teamMemberProfiles = new ArrayList<TeamMemberProfile>();

	public TeamMemberProfiles() {
		
	}
	
	public TeamMemberProfiles(List<TeamMemberProfile> teamMemberProfiles) {
		super();
		this.teamMemberProfiles = teamMemberProfiles;
	}

	public List<TeamMemberProfile> getTeamMemberProfiles() {
		return teamMemberProfiles;
	}
	
	public void setTeamMemberProfiles(List<TeamMemberProfile> teamMemberProfiles) {
		this.teamMemberProfiles = teamMemberProfiles;
	}
	   
   
}  