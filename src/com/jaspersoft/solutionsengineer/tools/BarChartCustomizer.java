package com.jaspersoft.solutionsengineer.customizers;

import java.text.DecimalFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import com.jaspersoft.solutionsengineer.customizers.CustomBarRenderer3D;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;
import org.jfree.chart.labels.*;
import org.jfree.ui.*;

public class BarChartCustomizer implements JRChartCustomizer {

	public void customize(JFreeChart chart, JRChart jasperChart) {
		    CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
		    
			ValueAxis rangeAxis = categoryPlot.getRangeAxis();
			if (rangeAxis instanceof NumberAxis) { 
				NumberAxis axis = (NumberAxis) rangeAxis;
				axis.setNumberFormatOverride(new DecimalFormat("###,###,###"));	
			}
			
// call the overriden renderer class to get new getItemPaint logic.			
			CustomBarRenderer3D renderer = (CustomBarRenderer3D) new CustomBarRenderer3D();
			 renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
		                ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
			categoryPlot.setRenderer(renderer);
			
//Widen the categories so those dots won't show up in the category.
			CategoryAxis domainaxis = categoryPlot.getDomainAxis();
	        domainaxis.setMaximumCategoryLabelWidthRatio(1.5f);

	}
	
}

