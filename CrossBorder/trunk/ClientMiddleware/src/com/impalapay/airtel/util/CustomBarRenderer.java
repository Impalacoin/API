
package com.impalapay.airtel.util;

import java.awt.Paint;

import org.jfree.chart.renderer.category.StackedBarRenderer;

/**
 * A custom render for overriding default bar colours
 * <p>
 * Copyright (c) Shujaa Solutions Ltd., Oct 16, 2013
 * @author <a href="mailto:anthonym@shujaa.co.ke">Anthony Wafula</a>
 * @version %I%, %G%
 */
public class CustomBarRenderer extends StackedBarRenderer{
        
        /** The colors. */
        private Paint[] colors;

        /**
         * Creates a new renderer.
         *
         * @param colors  the colors.
         */
        public CustomBarRenderer(final Paint[] colors) {
            this.colors = colors;
        }

        /**
         * Returns the paint for an item.  Overrides the default behaviour inherited from
         * AbstractSeriesRenderer.
         *
         * @param row  the series.
         * @param column  the category.
         *
         * @return The item color.
         */
        @Override
        public Paint getItemPaint(final int row, final int column) {
            return this.colors[row];
        }
   

}
