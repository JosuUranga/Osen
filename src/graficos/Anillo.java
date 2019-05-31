package graficos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlotState;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.text.TextUtilities;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import idiomas.ControladorIdioma;

@SuppressWarnings("serial")
public class Anillo extends JPanel {
	static ChartPanel pan=null;
	static ControladorIdioma controladorIdioma;
    static class CustomRingPlot extends RingPlot {
        private Font centerTextFont; 
        private Color centerTextColor;
        
        public CustomRingPlot(PieDataset dataset) {
            super(dataset);
            this.centerTextFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);
            this.centerTextColor = Color.BLACK;
        }
        
        @Override
        protected void drawItem(Graphics2D g2, int section, 
                Rectangle2D dataArea, PiePlotState state, int currentPass) {
            super.drawItem(g2, section, dataArea, state, currentPass);
            if (currentPass == 1 && section == 0) {
                g2.setFont(this.centerTextFont);
                g2.setPaint(this.centerTextColor);
                if (this.getDataset().getKey(0).equals(controladorIdioma.getListaPalabras().get(19))) {
                	TextUtilities.drawAlignedString(this.getDataset().getValue(this.getDataset().getKey(0))+"/"+"450", g2, 
                            (float) dataArea.getCenterX(), 
                            (float) dataArea.getCenterY(),  
                            TextAnchor.CENTER);
                }else if(this.getDataset().getKey(0).equals(controladorIdioma.getListaPalabras().get(20))) {
                	TextUtilities.drawAlignedString(this.getDataset().getValue(this.getDataset().getKey(0))+"/"+"100%", g2, 
                            (float) dataArea.getCenterX(), 
                            (float) dataArea.getCenterY(),  
                            TextAnchor.CENTER);
                }else if(this.getDataset().getKey(0).equals("Voc")) {
                	TextUtilities.drawAlignedString(this.getDataset().getValue(this.getDataset().getKey(0))+"/"+"500", g2, 
                            (float) dataArea.getCenterX(), 
                            (float) dataArea.getCenterY(),  
                            TextAnchor.CENTER);
                }else if(this.getDataset().getKey(0).equals(controladorIdioma.getListaPalabras().get(21))) {
                	TextUtilities.drawAlignedString(this.getDataset().getValue(this.getDataset().getKey(0))+"/"+"50", g2, 
                            (float) dataArea.getCenterX(), 
                            (float) dataArea.getCenterY(),  
                            TextAnchor.CENTER);
                }
            }
        }
    }
    public Anillo(String variable, float var,ControladorIdioma controladorIdiomas) {
        super();
        controladorIdioma=controladorIdiomas;
        pan=createPanel(variable,var);
    }
    
    public JPanel getPanel() {
    	return pan;
    }
    private PieDataset createDataset(String variable, float var) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue(variable, var);
        if (variable.equals(controladorIdioma.getListaPalabras().get(19))) {
        	dataset.setValue("B", 450-var);
        }else if(variable.equals(controladorIdioma.getListaPalabras().get(20))) {
        	dataset.setValue("B", 100-var);
        }else if(variable.equals("Voc")) {
        	dataset.setValue("B", 500-var);
        }else if(variable.equals(controladorIdioma.getListaPalabras().get(21))) {
        	dataset.setValue("B", 50-var);
        }
        return dataset;
    }
    private static JFreeChart createChart(PieDataset dataset) {
        CustomRingPlot plot = new CustomRingPlot(dataset);
        JFreeChart chart = new JFreeChart(dataset.getKey(0).toString(), 
                JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        chart.setBackgroundPaint(new GradientPaint(new Point(0, 0), 
                new Color(240, 240, 240), new Point(400, 200), Color.WHITE));

        TextTitle t = chart.getTitle();
        t.setHorizontalAlignment(HorizontalAlignment.LEFT);
        t.setPaint(new Color(0, 0, 0));
        t.setFont(new Font("Arial", Font.BOLD, 26));

        plot.setBackgroundPaint(null);
        plot.setOutlineVisible(false);
        plot.setLabelGenerator(null);
        plot.setSectionPaint(dataset.getKey(0), Color.BLUE);
        plot.setSectionPaint(dataset.getKey(1), Color.RED);
        plot.setSectionDepth(0.05);
        plot.setSectionOutlinesVisible(false);
        plot.setShadowPaint(null);
        return chart;

    }
    public ChartPanel createPanel(String variable, float var) {
        JFreeChart chart = createChart(createDataset(variable,var));
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(600, 300));
        return panel;
    }
}