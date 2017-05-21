/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author yannick
 */
public class Curve extends JDialog
{
    private String chartTitle;
    private String xAxisLabel;
    private String yAxisLabel;
    private ArrayList<Point> points;
    
    public Curve(String chartTitle, String xAxis, String yAxis, ArrayList<Point> points, JFrame p)
    {        
        super(p, chartTitle, true);
        this.chartTitle = chartTitle;
        this.xAxisLabel = xAxis;
        this.yAxisLabel = yAxis;
        this.points = points;
        
        JPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);
 
        setSize(640, 480);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    private JPanel createChartPanel()
    {
        XYDataset dataset = createDatasetFromPoints();
        
        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
        
        return new ChartPanel(chart);
    } 
    
    private XYDataset createDatasetFromPoints()
    {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries fitness = new XYSeries("Fitness");
        
        this.points.stream().forEach((p) ->
        {
            fitness.add(p.x, p.y);
        });
        
        dataset.addSeries(fitness);
        
        return dataset;
    }   
}
