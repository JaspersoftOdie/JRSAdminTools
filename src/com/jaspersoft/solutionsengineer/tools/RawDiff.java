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
 
public class RawDiff
{
  public static void main(String[] args) throws Exception
  {
	
	  String oldF = "C:/Users/whenders/Documents/odie/solutions/admin_tools/old";
	  String newF = "C:/Users/whenders/Documents/odie/solutions/admin_tools/new";
	  String outputFileName = "C:/Users/whenders/Documents/odie/solutions/admin_tools/output.txt";
	  List<String> strNoChange = new ArrayList<String>();
	  List<String> strHasChange = new ArrayList<String>();
	 
	  int noChanges = 0;
	  int hasChanges = 0;
	 
	  File ourFolder = new File(oldF);
	  File theirFolder = new File(newF);
	  
	  BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, true));
      
      File[] ourFiles = ourFolder.listFiles();
      File[] theirFiles = theirFolder.listFiles(); 
      
/*      
      for (File file : ourFiles) 
      {
          if (file.isFile())
          {
              System.out.println(file.getAbsolutePath() + file.getName());
          }
      }
  
      for (File file : theirFiles) 
      {
          if (file.isFile())
          {
              System.out.println(file.getAbsolutePath() + file.getName());
          }
      }
*/      
    try
    {
      for (File file1 : theirFiles) {
    	  if (file1.isFile())
          {   
    		  ByteArrayOutputStream out = new ByteArrayOutputStream();
    		  RawText rt1 = new RawText(new File(oldF + "/" + file1.getName()));
    		  RawText rt2 = new RawText(new File(file1.getAbsolutePath()));
    		  EditList diffList = new EditList();
    		  diffList.addAll(new HistogramDiff().diff(RawTextComparator.WS_IGNORE_ALL, rt1, rt2));
    		  DiffFormatter diff = new DiffFormatter(out);
    		  diff.format(diffList, rt1, rt2);
    		  if (out.toString().trim().length() > 0) {
    			/*  System.out.println(" ============== " + file1.getName() + " ========================= \n");
    			  System.out.println(out.toString());
        		  System.out.println("\n\n");*/ 
    			  strHasChange.add(file1.getName());
        		  writer.append(" ======================= " + file1.getName() + " ======================================= \n");
        		  writer.newLine();
        		  writer.newLine();
        		  writer.append(out.toString());
        		  writer.newLine();writer.newLine();
        		  writer.newLine();
        		  hasChanges++;
    		  } else {
    			  strNoChange.add(file1.getName());
    			  noChanges++;
    		  }
    		  
          }
      }
    } catch (IOException e)
    {
      e.printStackTrace();
    }

      System.out.println("No Changes: " + noChanges);
      System.out.println("Has Changes: " + hasChanges);
      writer.append("No Changes: " + noChanges);
      writer.newLine();
      writer.append("Has Changes: " + hasChanges);
      writer.newLine();writer.newLine();
      writer.append("Files with No Changes:");
      writer.newLine();
      for(int i=0;i < strNoChange.size();i++) {
    	  writer.append(strNoChange.get(i));
    	  writer.newLine();
      }
      writer.append("Files with Changes:");
      writer.newLine();writer.newLine();writer.newLine();
      for(int i=0;i < strHasChange.size();i++) {
    	  writer.append(strHasChange.get(i));
    	  writer.newLine();
      }
      
      writer.close();

  }
  
}