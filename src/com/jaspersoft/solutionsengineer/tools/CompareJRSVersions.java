package com.jaspersoft.solutionsengineer.tools;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.json.simple.*;

public class CompareJRSVersions {

	public static void main(String[] args) throws IOException, Exception {

		  String paramFile = args[0];
		  String oldF = "";
	      String newF = "";
	      String outputFileName = "";
		  
		  Helpers helper = new Helpers();
		  
/*		  String oldF = "C:/Users/whenders/Documents/odie/solutions/admin_tools/old";
		  String newF = "C:/Users/whenders/Documents/odie/solutions/admin_tools/new";
		  String outputFileName = "C:/Users/whenders/Documents/odie/solutions/admin_tools/output.txt"; 
		  String myArgs = helper.returnFileContents("C:/Users/whenders/Documents/odie/solutions/admin_tools/diffargs.json"); */
		  
		  String myArgs = helper.returnFileContents(paramFile);
		  
		  JSONArray argArray = helper.queryJSON(myArgs, "diffargs");
		  
		  for (int i = 0; i < argArray.size(); i++) {
  	        JSONObject jsonObjectRow = (JSONObject) argArray.get(i);
  	        oldF = (String) jsonObjectRow.get("ourDir");
  	        newF = (String) jsonObjectRow.get("theirDir");
  	        outputFileName = (String) jsonObjectRow.get("outputFile");
  		    helper.JRSCompare(oldF, newF, outputFileName);
		  }

	}
}
