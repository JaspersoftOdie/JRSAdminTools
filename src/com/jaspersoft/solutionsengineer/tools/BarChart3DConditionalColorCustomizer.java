package com.jaspersoft.solutionsengineer.customizers;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.CategoryDataset;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;

public class BarChart3DConditionalColorCustomizer implements JRChartCustomizer {

	public void customize(JFreeChart chart, JRChart jasperChart) {
			jasperChart.setBackcolor(Color.white);
			chart.setBackgroundPaint(Color.white);
			BarRenderer3D renderer = (BarRenderer3D) chart.getCategoryPlot().getRenderer();
			CategoryPlot categoryPlot = renderer.getPlot();
			categoryPlot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
			ValueAxis rangeAxis = categoryPlot.getRangeAxis();
			if (rangeAxis instanceof NumberAxis) { 
				NumberAxis axis = (NumberAxis) rangeAxis;
				axis.setNumberFormatOverride(new DecimalFormat("###,###,###.##"));	
			}
			
			categoryPlot.setNoDataMessage("No Data Available");
			
//			Widen the categories so those dots won't show up in the category.
			CategoryAxis domainaxis = categoryPlot.getDomainAxis();
	        domainaxis.setMaximumCategoryLabelWidthRatio(1.5f);       
	        CategoryDataset ds = categoryPlot.getDataset();
	        
//	      lo=light blue, med=orange yellow, hi=red -see severity_*_16x.png images
			Color[] colors = {new Color(0, 150, 255), new Color(250, 170, 0), Color.red};
	        
			// severity lo=1, med=2, hi=3
	        
			try {
				List<String> keys = (List<String>)ds.getRowKeys();
				int ik = -1;
		        
		        for (int ii = 0; ii < keys.size(); ii++) {
		        	String key = keys.get(ii);
		        	if (key.contains("Low")) {
		        		// use the 0 color
		        		ik = 0;
		        		        		
		        	} else if (key.contains("Medium")) {
		        		ik = 1;
		        		
		        	} else if (key.contains("High")) {
		        		ik = 2;
		        		
		        	}
		        	if (ik != -1 ) 
		        		renderer.setSeriesPaint(ii, colors[ik]);
		        }
				
			} catch (NullPointerException e) {
				//ignore it. Set regular colors.
			}
 	        
	}
	
}

