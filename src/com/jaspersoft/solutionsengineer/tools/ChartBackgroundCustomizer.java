package com.jaspersoft.solutionsengineer.customizers;

import org.jfree.chart.JFreeChart;

import java.awt.Color;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;

import org.jfree.chart.JFreeChart;


public class ChartBackgroundCustomizer implements JRChartCustomizer {
		
		public void customize(JFreeChart chart, JRChart jasperChart) { 
			jasperChart.setBackcolor(Color.white);
			chart.setBackgroundPaint(Color.white);
		}


}
