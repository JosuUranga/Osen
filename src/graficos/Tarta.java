package graficos;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleInsets;

@SuppressWarnings("serial")
public class Tarta extends JPanel{
	static ChartPanel pan=null;
	
   public Tarta(String tit1, float var1,float var2) {
	   super();
	   pan=crearPanel(tit1,var1,var2);
	}
   public JPanel getTarta() {
	   return pan;
   }
   public ChartPanel crearPanel(String tit1, float var1,float var2) {
	   JFreeChart chart = createChart(tit1,createDataset(tit1,var1,var2));
	   chart.setPadding(new RectangleInsets(4, 8, 2, 2));
	   ChartPanel panel = new ChartPanel(chart);
	   panel.setMouseWheelEnabled(true);
	   panel.setPreferredSize(new Dimension(600, 300));
	   return panel;
   }
   private static PieDataset createDataset(String tit1, float var1,float var2) {
       DefaultPieDataset dataset = new DefaultPieDataset();
       dataset.setValue(tit1+"-Muestra 1", var1);
       dataset.setValue(tit1+"-Muestra 2", var2);
       return dataset;
   }
   private static JFreeChart createChart(String tit,PieDataset dataset) {
       JFreeChart chart = ChartFactory.createPieChart(
           tit,  // chart title
           dataset,            // data
           false,              // no legend
           true,               // tooltips
           false               // no URL generation
       );
       // customise the title position and font
       TextTitle t = chart.getTitle();
       t.setHorizontalAlignment(HorizontalAlignment.LEFT);
       t.setPaint(new Color(0,0,0));
       t.setFont(new Font("Arial", Font.BOLD, 26));

       PiePlot plot = (PiePlot) chart.getPlot();
       plot.setBackgroundPaint(null);
       plot.setInteriorGap(0.04);
       plot.setOutlineVisible(false);

       // use gradients and white borders for the section colours
       plot.setSectionPaint(dataset.getKey(0), createGradientPaint(new Color(200, 200, 255), Color.BLUE));
       plot.setSectionPaint(dataset.getKey(1), createGradientPaint(new Color(255, 200, 200), Color.GREEN));
       plot.setSectionOutlinesVisible(true);
       plot.setBaseSectionOutlineStroke(new BasicStroke(2.0f));

       // customise the section label appearance
       plot.setLabelFont(new Font("Courier New", Font.BOLD, 20));
       plot.setLabelLinkPaint(Color.BLACK);
       plot.setLabelLinkStroke(new BasicStroke(2.0f));
       plot.setLabelOutlineStroke(null);
       plot.setLabelPaint(Color.BLACK);
       plot.setLabelBackgroundPaint(null);
       
       return chart;

   }
   private static RadialGradientPaint createGradientPaint(Color c1, Color c2) {
       Point2D center = new Point2D.Float(0, 0);
       float radius = 200;
       float[] dist = {0.0f, 1.0f};
       return new RadialGradientPaint(center, radius, dist,
               new Color[] {c1, c2});
   }
}

