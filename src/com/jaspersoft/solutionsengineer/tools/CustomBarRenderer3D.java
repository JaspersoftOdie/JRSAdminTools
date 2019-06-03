package com.jaspersoft.solutionsengineer.customizers;

import org.jfree.chart.renderer.category.BarRenderer3D;
import java.awt.Paint;
import java.awt.Color;

public class CustomBarRenderer3D extends BarRenderer3D {

private Paint[] paintArray = new Paint[] 
       {Color.red,
		Color.orange,
		Color.yellow, 
		Color.cyan, 
		Color.green,
		Color.black,
        Color.blue,
        Color.darkGray,
        Color.magenta,
        Color.gray       
       };	

    	public CustomBarRenderer3D() {
    		super();
        }

    /**
     * Returns the paint for an item.  Overrides the default behaviour 
     * inherited from AbstractSeriesRenderer.
     *
     * @param row  the series.
     * @param column  the category.
     *
     * @return The item color.
     */
    public Paint getItemPaint(int row, int column) {

    if(column > paintArray.length)
    	return paintArray[0];
    else
        return paintArray[column];   
    }
}
