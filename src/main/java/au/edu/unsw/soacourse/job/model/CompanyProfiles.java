package au.edu.unsw.soacourse.job.model;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyProfiles {
	@XmlElement(name = "CompanyProfile")
	List<CompanyProfile> companyProfiles = new ArrayList<CompanyProfile>();

	public CompanyProfiles() {
		
	}
	
	public CompanyProfiles(List<CompanyProfile> companyProfiles) {
		super();
		this.companyProfiles = companyProfiles;
	}

	public List<CompanyProfile> getCompanyProfiles() {
		return companyProfiles;
	}
	
	public void setCompanyProfiles(List<CompanyProfile> companyProfiles) {
		this.companyProfiles = companyProfiles;
	}
	   
   
}  