package graficos;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

public class Tarta extends JPanel{
	static ChartPanel pan=null;
	
   public Tarta() {
	   super();
	   JFreeChart chart = createChart(createDataset());
	   chart.setPadding(new RectangleInsets(4, 8, 2, 2));
	   ChartPanel panel = new ChartPanel(chart);
	   panel.setMouseWheelEnabled(true);
	   panel.setPreferredSize(new Dimension(600, 300));
	   pan=panel;
	}
   public JPanel getTarta() {
	   return pan;
   }
   private static PieDataset createDataset() {
       DefaultPieDataset dataset = new DefaultPieDataset();
       dataset.setValue("Samsung", new Double(27.8));
       dataset.setValue("Others", new Double(55.3));
       dataset.setValue("Nokia", new Double(16.8));
       dataset.setValue("Apple", new Double(17.1));
       return dataset;
   }
   private static JFreeChart createChart(PieDataset dataset) {
       JFreeChart chart = ChartFactory.createPieChart(
           "Smart Phones Manufactured / Q3 2011",  // chart title
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
       plot.setSectionPaint("Others", createGradientPaint(new Color(200, 200, 255), Color.BLUE));
       plot.setSectionPaint("Samsung", createGradientPaint(new Color(255, 200, 200), Color.RED));
       plot.setSectionPaint("Apple", createGradientPaint(new Color(200, 255, 200), Color.GREEN));
       plot.setSectionPaint("Nokia", createGradientPaint(new Color(200, 255, 200), Color.YELLOW));
       plot.setBaseSectionOutlinePaint(Color.WHITE);
       plot.setSectionOutlinesVisible(true);
       plot.setBaseSectionOutlineStroke(new BasicStroke(2.0f));

       // customise the section label appearance
       plot.setLabelFont(new Font("Courier New", Font.BOLD, 20));
       plot.setLabelLinkPaint(Color.WHITE);
       plot.setLabelLinkStroke(new BasicStroke(2.0f));
       plot.setLabelOutlineStroke(null);
       plot.setLabelPaint(Color.WHITE);
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

