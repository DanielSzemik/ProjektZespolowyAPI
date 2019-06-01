package de.dks.ws;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

@Path("/clips")
@ApplicationPath("/resources")
public class RestService extends Application {
	static final String FILENAME = "prolog.pl";

	// http://localhost:8080/RestExample/resources/clips/rules
	@GET
	@Path("/rules")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Rule> getRules() {
		List<Rule> rules = new ArrayList<Rule>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(FILENAME));
			String line;

			while ((line = reader.readLine()) != null) {
				if(line.startsWith("expert") == false) {
					String[] parts = line.split(",");
					String[] parts1 =  parts[0].split("\\(");
					int certainty = Integer.parseInt(parts[2].trim().replaceAll("[^0-9]", ""));
					Rule rule = new Rule(parts1[0].trim(),parts[1].trim(),parts1[1].trim(),certainty);
					rules.add(rule);
				}
			}
			reader.close();
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.", FILENAME);
			e.printStackTrace();

		}
		return rules;
	}
	
	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response AddRule(Rule r) {
		List<String> records = new ArrayList<String>();
		int n = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(FILENAME));
			String line;

			while ((line = reader.readLine()) != null) {
				records.add(line);
			}
			reader.close();

			FileWriter fileWriter = new FileWriter(FILENAME);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int i = 0; i < records.size(); i++) {
				bufferedWriter.write(records.get(i));
				bufferedWriter.newLine();
			}
			bufferedWriter.write(r.getPredicament() + "(" + r.getWord() + ", " + 
					r.getName() + ", " + r.getCertainty() + ").");
			
			bufferedWriter.close();

		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.", FILENAME);
			e.printStackTrace();
		}
		return Response.ok().build();
	}
    
	
	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response DeleteRule(Rule r) {
		List<String> records = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(FILENAME));
			String line;

			while ((line = reader.readLine()) != null) {
				if (r.getPredicament().equals(line.substring(0, r.getPredicament().length()))) {
					String[] parts = line.split(",");
					String[] parts1 =  parts[0].split("\\(");
					int certainty = Integer.parseInt(parts[2].trim().replaceAll("[^0-9]", ""));
					Rule rule = new Rule(parts1[0].trim(),parts[1].trim(),parts1[1].trim(),certainty);
					if (!rule.equals(r)) 
						records.add(line);
				}
				else
					records.add(line);
			}
			reader.close();
			
			FileWriter fileWriter = new FileWriter(FILENAME);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int i = 0; i < records.size(); i++) {
				bufferedWriter.write(records.get(i));
				bufferedWriter.newLine();
			}
			bufferedWriter.close();

		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.", FILENAME);
			System.out.println(records.size());
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("/clipsFilePath")
	public String getPath() {
		String cwd = new File("").getAbsolutePath();
		return cwd + "\\" + FILENAME;
	}

	@Override
	public Set<Class<?>> getClasses() {
		return null;
	}
}