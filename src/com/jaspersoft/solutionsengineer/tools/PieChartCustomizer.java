package com.jaspersoft.solutionsengineer.customizers;

import java.awt.Color;
import java.text.DecimalFormat;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.ui.RectangleEdge;

public class PieChartCustomizer implements JRChartCustomizer {

	public void customize(JFreeChart chart, JRChart jasperChart)
	{
		PiePlot plot = (PiePlot) chart.getPlot();

		// the legend shows just the labels (not x = y format)
		PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0}");
		plot.setLegendLabelGenerator(generator);

		// the legend appears on the right of the pie instead of underneath
		LegendTitle legend = (LegendTitle) chart.getLegend();
		legend.setPosition(RectangleEdge.RIGHT);  
		
		plot.setNoDataMessage("No Data Available");
		// for IE to render a white background
		jasperChart.setBackcolor(Color.white);
        chart.setBackgroundPaint(Color.white);

	}

}