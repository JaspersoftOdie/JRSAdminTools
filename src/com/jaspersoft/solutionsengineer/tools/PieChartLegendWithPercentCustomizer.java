package com.jaspersoft.solutionsengineer.customizers;

	import java.text.DecimalFormat;
	import java.util.ArrayList;
	import java.util.List;

	import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
	import net.sf.jasperreports.engine.JRChart;
	import net.sf.jasperreports.engine.JRChartCustomizer;
	import net.sf.jasperreports.engine.fill.JRBaseFiller;
	import net.sf.jasperreports.engine.fill.JRFillChart;

	import org.jfree.chart.JFreeChart;
	import org.jfree.chart.labels.PieSectionLabelGenerator;
	import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;

import com.jaspersoft.solutionsengineer.customizers.PieChartCustomizer;

	public class PieChartLegendWithPercentCustomizer extends JRAbstractChartCustomizer {

		private List<JRChartCustomizer> components = new ArrayList<JRChartCustomizer>();
		
		@Override
		public void init(JRBaseFiller chartFiller, JRFillChart chart) {
			super.init(chartFiller, chart);
			
			components.add(new ChartBackgroundCustomizer());
			components.add(new PieChartCustomizer());
		}
		
		public void customize(JFreeChart chart, JRChart jasperChart)
		{
			for (JRChartCustomizer r: components) {
				r.customize(chart, jasperChart);
			}
			
			PiePlot plot = (PiePlot) chart.getPlot();

			// the legend shows just the labels (not x = y format)
			PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0} | {1} | {2}", new DecimalFormat("0"), new DecimalFormat("0.0%"));
			plot.setLegendLabelGenerator(generator);	

		}
	}

