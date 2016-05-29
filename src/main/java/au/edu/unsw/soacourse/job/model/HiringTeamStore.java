package au.edu.unsw.soacourse.job.model;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//for storing
@XmlRootElement(name = "hiringTeam")
public class HiringTeamStore {
	private String id;
    private String companyProfileId;
    private CompanyProfile companyProfileLnk;
    private String member1id;
    private String member2id;
    private String member3id;
    private String member4id;
    private String member5id;
    private String link;
    private String rel;
	private boolean sendVersion = false;
	public void setSendVersion(boolean set){
		this.sendVersion = set;
	}
	public HiringTeamStore() {
		super();
	}
	public HiringTeamStore(String id, String companyProfileId) {
		super();
		this.id = id;
		this.companyProfileId = companyProfileId;
		this.link = "http://localhost:8080/RestfulJobService/hiringteam/" + this.id;
	}
	
	public HiringTeamStore(String id, String companyProfileId,
			String member1id, String member2id, String member3id,
			String member4id, String member5id) {
		super();
		this.id = id;
		this.companyProfileId = companyProfileId;
		this.companyProfileLnk = new CompanyProfile(this.companyProfileId);
		this.member1id = member1id;
		this.member2id = member2id;
		this.member3id = member3id;
		this.member4id = member4id;
		this.member5id = member5id;
		this.link = "http://localhost:8080/RestfulJobService/hiringteam/" + this.id;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompanyProfileId() {
		if(sendVersion == true)
			return null;
		
		return companyProfileId;
	}
	public void setCompanyProfileId(String companyProfileId) {
		this.companyProfileId = companyProfileId;
	}
	
	public String getMember1id() {
		return member1id;
	}
	public void setMember1id(String member1id) {
		this.member1id = member1id;
	}
	public String getMember2id() {
		return member2id;
	}
	public void setMember2id(String member2id) {
		this.member2id = member2id;
	}
	public String getMember3id() {
		return member3id;
	}
	public void setMember3id(String member3id) {
		this.member3id = member3id;
	}
	public String getMember4id() {
		return member4id;
	}
	public void setMember4id(String member4id) {
		this.member4id = member4id;
	}
	public String getMember5id() {
		return member5id;
	}
	public void setMember5id(String member5id) {
		this.member5id = member5id;
	}

	@XmlAttribute(name = "href")
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
		this.id = link.substring(link.length()-1);
	}

	@XmlAttribute(name = "rel")
	public String getRel() {
		rel = "hiringteam";
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}

	@XmlElement(name = "companyProfile")
	public CompanyProfile getCompanyProfileLnk() {
		if(this.companyProfileId == null)
			return null;
		
		this.companyProfileLnk = new CompanyProfile(this.companyProfileId);
		return this.companyProfileLnk;
	}
	public void setCompanyProfileLnk(CompanyProfile companyProfileLnk) {
		this.companyProfileLnk = companyProfileLnk;
		this.companyProfileId = companyProfileLnk.getId();
		
	}



}
