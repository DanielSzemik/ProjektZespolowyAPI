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
	static final String FILENAME = "winedemo.clp";

	// http://localhost:8080/RestExample/resources/clips/rules
	@GET
	@Path("/rules")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Rule> getRules() {
		List<String> records = new ArrayList<String>();
		List<Rule> rules = new ArrayList<Rule>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(FILENAME));
			String line;

			while ((line = reader.readLine()).startsWith("(deffacts the-rules") == false) {
				records.add(line);
			}
			while ((line = reader.readLine()).startsWith(")") == false) {
				if (line.trim().startsWith("(rule")) {
					String atN1 = line.substring(line.indexOf("(if ") + 4, line.indexOf(" is ")).trim();
					String at1 = line.substring(line.indexOf(" is ") + 4, line.indexOf(")")).trim();
					line = reader.readLine();
					String atN2 = line.substring(line.indexOf("(then ") + 6, line.indexOf(" is ")).trim();
					String at2 = line.substring(line.indexOf(" is ") + 4, line.indexOf(" with ")).trim();
					int certainty = Integer.parseInt(line.substring(line.indexOf(" certainty ") + 11, line.indexOf(" ))")).trim());
					Rule rule1 = new Rule(atN1, at1, atN2, at2, certainty);
					rules.add(rule1);
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
				if (line.startsWith("(deffacts the-rules"))
					n = records.size() +1;
			}
			reader.close();

			FileWriter fileWriter = new FileWriter(FILENAME);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int i = 0; i < records.size(); i++) {
				if (i == n) {
					bufferedWriter.write("  (rule     (if " + r.getAttrName1() + " is " + r.getAttr1() + " )");
					bufferedWriter.newLine();
					bufferedWriter.write("        (then " + r.getAttrName2() + " is " + r.getAttr2()
							+ " with certainty " + r.getCertainty() + " ))");
					bufferedWriter.newLine();
				}
				bufferedWriter.write(records.get(i));
				bufferedWriter.newLine();
			}
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
			String line2;

			while ((line = reader.readLine()).startsWith("(deffacts the-rules") == false) {
				records.add(line);
			}
			records.add(line);
			
			while ((line = reader.readLine()).startsWith(")") == false) {
				if (line.trim().startsWith("(rule")) {
					String atN1 = line.substring(line.indexOf("(if ") + 4, line.indexOf(" is ")).trim();
					String at1 = line.substring(line.indexOf(" is ") + 4, line.indexOf(")")).trim();
					line2 = reader.readLine();
					String atN2 = line2.substring(line2.indexOf("(then ") + 6, line2.indexOf(" is ")).trim();
					String at2 = line2.substring(line2.indexOf(" is ") + 4, line2.indexOf(" with ")).trim();
					int certainty = Integer.parseInt(line2.substring(line2.indexOf(" certainty ") + 11, line2.indexOf(" ))")).trim());
					Rule rule = new Rule(atN1, at1, atN2, at2, certainty);
					if (!rule.equals(r))
					{
						records.add(line);
						records.add(line2);
					}
				}
				else
					records.add(line);
			}
			records.add(line);
			
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