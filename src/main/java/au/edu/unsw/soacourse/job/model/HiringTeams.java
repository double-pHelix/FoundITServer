package au.edu.unsw.soacourse.job.model;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HiringTeams {
	@XmlElement(name = "hiringTeam")
	List<HiringTeamStore> hiringTeams = new ArrayList<HiringTeamStore>();

	public HiringTeams() {
		
	}
	
	public HiringTeams(List<HiringTeamStore> hiringTeams) {
		super();
		this.hiringTeams = hiringTeams;
	}

	public List<HiringTeamStore> getHiringTeams() {
		return hiringTeams;
	}
	
	public void setHiringTeams(List<HiringTeamStore> hiringTeams) {
		this.hiringTeams = hiringTeams;
	}
	   
   
}  