package au.edu.unsw.soacourse.job.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;




//XML
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//XSLT
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

//XQUERY
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;







import net.sf.saxon.xqj.SaxonXQDataSource;





//XML
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class JobAlertDAO {

	private final String  CAREER_SITE = "http://rss.jobsearch.careerone.com.au/rssquery.ashx?"
			+ "q=Java&rad_units=km&cy=AU&pp=25&sort=rv.di.dt&baseurl="
			+ "jobview.careerone.com.au";

	private final String TEACH_SITE = "https://www.teach.nsw.edu.au/find-teaching-jobs/jobfeed";
	
	String homeDir = System.getProperty("catalina.home")+"/webapps/{root context}/WEB-INF/";
	
	//TODO: Make sure this is where the job postings are stored
	private final String JOB_POSTING_FILEDIR = System.getProperty("user.dir") + "/jobpostingstore.xml";
	private final String JOBTRANS_XML = System.getProperty("user.dir") + "/jobpostingMod.xml";
	
	private final String CAREER_XML = System.getProperty("user.dir") + "/careerOneFeed.xml";
	private final String CAREERTRANS_XML = System.getProperty("user.dir") + "/careerOneFeedMod.xml";
	private final String TEACH_HTML = System.getProperty("user.dir") + "/teachJobs.html";
	private final String TEACH_XML = System.getProperty("user.dir") + "/teachJobsMod.xml";
	private final String QUERY_XQ = System.getProperty("user.dir") + "/query.xq";
	private final String QUERY_MATCH = System.getProperty("user.dir") + "/result.xml";
	private final String JOB_LIST = System.getProperty("user.dir") + "/joblist.xml";
	
	//TODO: This is not created, has to be moved there
	private final String TRANS_XSLT = System.getProperty("user.dir") + "/careerTrans.xslt";
	//TODO: Write the xslt to transform job postings
	private final String JOB_TRANS_XSLT = System.getProperty("user.dir") + "/jobTrans.xslt";

	
	//Constructor
	public JobAlertDAO() {
		
	}
	
	//Only needs to be done once
	public void setupFile() {
		//Download career one file
		writeFile();
		
		//Write the transform
		//Doesn't seem to work :/
		writeXSLTTrans();
		
		//Parse it into readable format
		parseXsltCareer();
		
		//Download teach website
		downloadHtml();
		
		//Parse html into more better format
		parseHtml();
		
		//Parse our job xml into more readable format
		//TODO: Uncomment when the xslt works
		//parseXsltJob();
		
		//writeXSLTTransJob();
		
		//Merges into documents to allow easier query
		mergeDocuments();
	}

	/*
	public void parseFile() {
		URL url = null;
		try {
			url = new URL(CAREER_SITE);
		} catch (MalformedURLException e3) {
			e3.printStackTrace();
		}
		InputStream stream = null;
		try {
			stream = url.openStream();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		try {
			Document doc = dBuilder.parse(stream);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("item");
	        
	        for (int temp = 0; temp < nList.getLength(); temp++) {
	        	Node nNode = nList.item(temp);
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	            	Element eElement = (Element) nNode;
	            	String title = "";
	            	String description = "";
            		if (eElement.getElementsByTagName("title").item(0) != null) {
            			title = eElement.getElementsByTagName("title").item(0).getTextContent();
	            	}
            		if (eElement.getElementsByTagName("description").item(0) != null) {
            			description = eElement.getElementsByTagName("description").item(0).getTextContent();
	            	}
            		
	            }
	         }
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	//Transforms xml into more readable xml (the one we use)
	private void parseXsltCareer() {
		TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File(TRANS_XSLT));
        Transformer transformer = null;
		try {
			transformer = factory.newTransformer(xslt);
		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		}

        Source text = new StreamSource(new File(CAREER_XML));
        try {
			transformer.transform(text, new StreamResult(new File(CAREERTRANS_XML)));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	private void parseXsltJob() {
		TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File(JOB_TRANS_XSLT));
        Transformer transformer = null;
		try {
			transformer = factory.newTransformer(xslt);
		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		}

        Source text = new StreamSource(new File(JOB_POSTING_FILEDIR));
        try {
			transformer.transform(text, new StreamResult(new File(JOBTRANS_XML)));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	//Write file from website
	private void writeFile() {
		URL url = null;
		try {
			url = new URL(CAREER_SITE);
		} catch (MalformedURLException e3) {
			e3.printStackTrace();
		}
		InputStream stream = null;
		try {
			stream = url.openStream();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(CAREER_XML);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		
		String line;
		try {
			while ((line = br.readLine()) != null) {
				fw.write(line);
				fw.write("\n");
			}
			fw.close();
			br.close();
			stream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
	}

	private void printFile() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		try {
			File inputFile = new File(CAREERTRANS_XML);
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("Job");
	        
	        for (int temp = 0; temp < nList.getLength(); temp++) {
	        	Node nNode = nList.item(temp);
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	            	Element eElement = (Element) nNode;
	            	String title = "";
	            	String description = "";
            		if (eElement.getElementsByTagName("Title").item(0) != null) {
            			title = eElement.getElementsByTagName("Title").item(0).getTextContent();
	            	}
            		if (eElement.getElementsByTagName("Description").item(0) != null) {
            			description = eElement.getElementsByTagName("Description").item(0).getTextContent();
	            	}
            		System.out.println("Title:" + title);
            		System.out.println("Desciption:" + description);
	            }
	         }
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeXquery(String keyword, String sort) {
		FileWriter fw = null;
		//Do something if keyword is null
		if(keyword == null) {
			keyword = "";
		}
		try {
			fw = new FileWriter(QUERY_XQ);
			
			String query = 	"<QueryMatch>\n" +
							"{ \n" +
							"for $job in doc(\"joblist.xml\")/JobList/Job \n"+
							"where contains($job/Title/text()," + '"'+keyword+'"' + ") or contains($job/Description/text()," + '"'+keyword+'"' + ") \n";
			
			if(sort!=null) {
				query = query+"order by $job/Title \n";
			}
			
			query = query+  "return \n" +
							"<Job> \n" +
							"{ $job/Title } \n" +
							"{ $job/Description } \n"+
							"</Job>\n"+
							"}\n" +
							"</QueryMatch>";
			
			fw.write(query);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void executeQuery(String keyword, String sort) {
		writeXquery(keyword, sort);
		executeQueryAll();
	}
	
	private void mergeDocuments() {
		
		BufferedReader br;
		try {
			
			br = new BufferedReader(new FileReader(CAREERTRANS_XML));
			String line = br.readLine();
			BufferedWriter bw = new BufferedWriter(new FileWriter(JOB_LIST));
			bw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
			bw.write("<JobList>\n");
			while (line != null) {
				if(line.contains("Job>") || line.contains("<Description>") ||  line.contains("<Title>")) {
					bw.write(line+"\n");
				}
				line = br.readLine();
			}
			br.close();
			br = new BufferedReader(new FileReader(TEACH_XML));
			line = br.readLine();
			while (line != null) {
				if(line.contains("Job>") || line.contains("<Description>") ||  line.contains("<Title>")) {
					bw.write(line+"\n");
				}
				line = br.readLine();
			}
			bw.write("</JobList>\n");
			bw.close();
			//TODO: Uncomment when xslt transform works
			/*
			 * 	br.close();
		 	 	br = new BufferedReader(new FileReader(JOBTRANS_XML));
				line = br.readLine();
				while (line != null) {
					if(line.contains("Job>") || line.contains("<Description>") ||  line.contains("<Title>")) {
						bw.write(line+"\n");
					}
					line = br.readLine();
				}
			  	
			  	bw.write("</JobList>\n");
				bw.close();
			 */
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void executeQueryAll() {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(QUERY_XQ));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		XQDataSource ds = new SaxonXQDataSource();
		XQConnection conn;
		XQPreparedExpression exp;
		XQResultSequence result = null;
		FileWriter fw = null;
		try {
			conn = ds.getConnection();
			exp = conn.prepareExpression(inputStream);
			result = exp.executeQuery();
			
			fw = new FileWriter(QUERY_MATCH);
			BufferedWriter out = new BufferedWriter(fw);
			out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
			while (result.next()) {
				String temp = result.getItemAsString(null);
				out.write(temp);
				//System.out.println(temp);
			}
			out.close();
			fw.close();
			inputStream.close();
		} catch (XQException | IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public void readFile() {
		try {
			FileReader fr = new FileReader(QUERY_MATCH);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			while(line != null) {
				System.out.println(line);
				line = br.readLine();
			}
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void downloadHtml() {
		URL url = null;
		try {
			url = new URL(TEACH_SITE);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		InputStream is = null;
		FileWriter fw = null;
        try {
			is = url.openStream();
			
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(is));
			fw = new FileWriter(TEACH_HTML);
			
	        String line = br.readLine();;
	        while (line != null) {
	        	fw.write(line+"\n");
	        	line = br.readLine();
	        	//System.out.println(line);
	        }
	        fw.close();
	        br.close();
	        is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
	}

	private void parseHtml() {
		BufferedReader br;
		FileWriter fw = null;
		try {
			br = new BufferedReader(new FileReader(TEACH_HTML));
			fw = new FileWriter(TEACH_XML);
			BufferedWriter out = new BufferedWriter(fw);
			String line = br.readLine();
			out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
			out.write("<JobList>\n");
			while (!(line.contains("Temporary Teacher positions"))) {
				line = br.readLine();
			}
			
			String splitsP[] = line.split("<p>");
			
			int i = 3;
			
			while (i < splitsP.length) {
				//System.out.println(splitsP[i]);
				
				if(splitsP[i].contains("<strong>")) {
					String splitStr[] = splitsP[i].split("</strong>");
					
					String title = splitStr[0].replace("<strong>", "");
					title = title.replace("&nbsp", " ");
					title = title.replace(";", "");
					title = title.replace("&ndash", "");
					String splitDes[] = splitsP[i].split("\">", 2);
					if(splitDes.length != 2) {
						i=i+1;
						continue;
					}
					
					if(title.contains("positions")) {
						String splitLi[] = splitsP[i].split("<li>");

						int j = 0;
						
						while(j != splitLi.length) {
							String splitBr[] = splitLi[j].split(">");
							String newTitle = splitBr[1].replace("</a", "");
							String des = splitBr[2].replace("</li", "");
							des = des.replace("&amp", "");
								
							if(newTitle.contains("positions")) {
								j = j+1;
								continue;
							}
							
							des = des.replaceAll("<.*?>", "");
							//System.out.println("title2: "+ title);
							//System.out.println("des2: " + des);
							out.write("<Job>\n");
							out.write("<Title>" + newTitle + "</Title>\n");
							out.write("<Description>" + des + "</Description>\n");
							out.write("</Job>\n");
							j = j+1;
						}
					} else {
						String description = splitDes[1];
						description = description.replaceAll("<.*?>", "");
						out.write("<Job>\n");
						out.write("<Title>" + title + "</Title>\n");
						out.write("<Description>" + description + "</Description>\n");
						out.write("</Job>\n");
					}
					
					//System.out.println("title1: " + title);
					//System.out.println("des1: " + description);
					//System.out.println(splitsP[i]);
				} else if (splitsP[i].contains("Non school based")) {
					String splitLi[] = splitsP[i].split("<li>");

					int j = 0;
					
					while(j != splitLi.length) {
						String splitBr[] = splitLi[j].split(">");
						String title = splitBr[1].replace("</a", "");
						String des = splitBr[2].replace("</li", "");
						des = des.replace("&amp", "");
						des = des.replaceAll("<.*?>", "");
						if(title.contains("temporary")) {
							j = j+1;
							continue;
						}
						//System.out.println("title2: "+ title);
						//System.out.println("des2: " + des);
						out.write("<Job>\n");
						out.write("<Title>" + title + "</Title>\n");
						out.write("<Description>" + des + "</Description>\n");
						out.write("</Job>\n");
						j = j+1;
					}
				} else {
					String splitA[] = splitsP[i].split("<a");
					String splitEnd[] = splitsP[i].split(">");
					String splitAEnd[] = splitsP[i].split("</a>", 2);
					
					String title = splitA[0].trim();
					//System.out.println(splitsP[i]);
					
					if(title.length() > 40) {
						i = i+1;
						continue;
					}
					if(splitAEnd.length != 2) {
						i = i+1;
						continue;
					}
					String school = splitEnd[1].replace("</a", "");
					String des = splitAEnd[1];
					des = des.replaceAll("<.*?>", "");
					//System.out.println("title3: " + title);
					//System.out.println(school);
					//System.out.println("des3: " + school + description);
					out.write("<Job>\n");
					out.write("<Title>" + title + "</Title>\n");
					out.write("<Description>" + school + des + "</Description>\n");
					out.write("</Job>\n");
				}
				
				i = i+1;
			}
			out.write("</JobList>\n");
			out.close();
			fw.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//PLay with tests here
	public void testJobAlert() {
		setupFile();
		executeQuery("Math", "title");
		readFile();
	}
	
	private void writeXSLTTrans() {
		BufferedWriter bw = null;
		FileWriter fr = null;
		try {
			bw = new BufferedWriter(new FileWriter(TRANS_XSLT));
			//fr = new FileWriter("careerTrans.xslt");
			String xslt = 	"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
							"<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\">\n" +
							"<xsl:output method=\"xml\" indent=\"yes\"/>\n" +
							"<xsl:template match=\"channel\">\n" +
							"<xsl:element name=\"JobList\">\n" +
							"<xsl:apply-templates/>\n" +
							"</xsl:element>\n" +
							"</xsl:template>\n" +
							"<xsl:template match=\"title\">\n" +
							"</xsl:template>\n" +
							"<xsl:template match=\"description\">\n" +
							"</xsl:template>\n" +
							"<xsl:template match=\"link\">\n" +
							"</xsl:template>\n" +
							"<xsl:template match=\"image\">\n" +
							"</xsl:template>\n" +
							"<xsl:template match=\"item\">\n" +
							"<xsl:element name=\"Job\">\n" +
							"<xsl:element name=\"Title\">\n" +
							"<xsl:value-of select=\"title\"/>\n" +
							"</xsl:element>\n" +
							"<xsl:element name=\"Description\">\n" +
							"<xsl:value-of select=\"description\"/>\n" +
							"</xsl:element>\n" +
							"</xsl:element>\n" +
							"</xsl:template>\n" +
							"</xsl:stylesheet>";
			//System.out.println(xslt);
			
			String split[] = xslt.split("\n");
			int i = 0;
			while (i != split.length) {
				System.out.println(split[i]);
				bw.write(split[i] + "\n");
				//bw.write(i);
				i = i+1;
			}
			bw.close();
			//fw.close();
			//bw.write(xslt);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	//TODO: Write xslt
	private void writeXSLTTransJob() {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(JOB_TRANS_XSLT));
			
			String xslt = 	"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
							"<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\">\n" +
							"<xsl:output method=\"xml\" indent=\"yes\"/>\n" +
							"<xsl:template match=\"channel\">\n" +
							"<xsl:element name=\"JobList\">\n" +
							"<xsl:apply-templates/>\n" +
							"</xsl:element>\n" +
							"</xsl:template>\n" +
							"<xsl:template match=\"title\">\n" +
							"</xsl:template>\n" +
							"<xsl:template match=\"description\">\n" +
							"</xsl:template>\n" +
							"<xsl:template match=\"link\">\n" +
							"</xsl:template>\n" +
							"<xsl:template match=\"image\">\n" +
							"</xsl:template>\n" +
							"<xsl:template match=\"item\">\n" +
							"<xsl:element name=\"Job\">\n" +
							"<xsl:element name=\"Title\">\n" +
							"<xsl:value-of select=\"title\"/>\n" +
							"</xsl:element>\n" +
							"<xsl:element name=\"Description\">\n" +
							"<xsl:value-of select=\"description\"/>\n" +
							"</xsl:element>\n" +
							"</xsl:element>\n" +
							"</xsl:template>\n" +
							"</xsl:stylesheet>\n";
			
			bw.write(xslt);
			bw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
