package au.edu.unsw.soacourse.job.model;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CompanyProfile {
	private String id;
    private String name;
    private String description;
	private String website;
    private String industryType;
    private String address;
    private String link;
    private String rel;
    
	public CompanyProfile() {
		super();
	}

	public CompanyProfile(String id) {
		super();
		this.link = "http://localhost:8080/FoundITServer/companyprofile/" + id;
	}


	public CompanyProfile(String id, String name, String description,
			String website, String industryType, String address) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.website = website;
		this.industryType = industryType;
		this.address = address;
		this.link = "http://localhost:8080/FoundITServer/companyprofile/" + this.id;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@XmlAttribute(name = "href")
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
		//use this link info to update id
		this.id = link.substring(link.length()-1);
	}
	
	@XmlAttribute(name = "rel")
	public String getRel() {
		rel = "company";
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
    
    
    
}
