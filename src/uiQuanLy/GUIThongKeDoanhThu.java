package uiQuanLy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import entities.HoaDon;
import services.ThongKe;

/**
 * A simple demonstration application showing how to create an area chart with a date axis for
 * the domain values.
 *
 */
public class GUIThongKeDoanhThu extends JFrame implements ActionListener {
	JComboBox<String> cmbThang, cmbNam;
	JButton btnOK;
	private JPanel childPanel;
	JPanel chartPanel = createChart(LocalDate.now().getMonthValue()+"", "2019");

	private static String thangPDP, namPDP ;
    public GUIThongKeDoanhThu() {
    	setTitle("Doanh Thu Theo Ngày Trong Tháng");
		setSize(700, 520);
		Box bc,b1;
		bc = Box.createVerticalBox();
		add(bc);
		bc.add(b1 = Box.createHorizontalBox());
	    b1.add(chartPanel);
	        
		bc.add(Box.createVerticalStrut(20));
		bc.add(b1 = Box.createHorizontalBox());
		b1.add(Box.createHorizontalStrut(20));
		b1.add(new JLabel("Tháng: "));
		cmbThang = new JComboBox();
		final String[] traceMonth = {"1", "2","3","4","5","6","7","8","9","10","11","12"};
		cmbThang.setModel(new DefaultComboBoxModel(traceMonth));
		b1.add(cmbThang);
		b1.add(Box.createHorizontalStrut(20));
		b1.add(new JLabel("Năm: "));
		cmbNam = new JComboBox();
        final String[] traceYears = {"2018","2019","2020"};
        cmbNam.setModel(new DefaultComboBoxModel(traceYears));
        b1.add(cmbNam);
        b1.add(Box.createHorizontalStrut(20));
        b1.add(btnOK = new JButton("OK"));
        b1.add(Box.createHorizontalStrut(200));

		bc.add(Box.createVerticalStrut(20));
		btnOK.addActionListener(this);
		setLocationRelativeTo(null);
	    setResizable(false);
	    setVisible(true);

    }
    private JFreeChart createChart123(final XYDataset dataset) {
        final JFreeChart chart = ChartFactory.createXYAreaChart(
            "Doanh Thu Tháng Trong Năm",
            "Ngày", "Doanh Thu",
            dataset,
            PlotOrientation.VERTICAL,
            true,  // legend
            true,  // tool tips
            false  // URLs
        );
        final XYPlot plot = chart.getXYPlot();

        final ValueAxis domainAxis = new DateAxis("Ngày");
        domainAxis.setLowerMargin(0.0);
        domainAxis.setUpperMargin(0.0);
        plot.setDomainAxis(domainAxis);
        plot.setForegroundAlpha(0.5f);  
        
        final XYItemRenderer renderer = plot.getRenderer();
        renderer.setToolTipGenerator(
            new StandardXYToolTipGenerator(
                StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
                new SimpleDateFormat("d-MM-yyyy"), new DecimalFormat("#,##0.00")
            )
        );
        return chart;      
    }
    public void showPanel(JPanel panel) {
		childPanel = panel;
		chartPanel.removeAll();
		chartPanel.add(childPanel);
		chartPanel.validate();
	}
//    public static void main(final String[] args) {
//        final GUIThongKeDoanhThu demo = new GUIThongKeDoanhThu();
//        demo.pack();
//        RefineryUtilities.centerFrameOnScreen(demo);
//        //demo.setVisible(true);
//    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if(src.equals(btnOK)) {
			thangPDP = cmbThang.getSelectedItem().toString();
			namPDP = cmbNam.getSelectedItem().toString();
			showPanel(createChart(thangPDP,namPDP));
		}
	}
	private JPanel createChart(String thangPDP, String namPDP) {
		final TimeSeries series1 = new TimeSeries("Doanh Số");
        ThongKe tk = new ThongKe();
        ArrayList<HoaDon> ds = tk.thongKeDoanhThu(thangPDP, namPDP);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        ds.forEach(x->{
        	Date myDate;
			try {
				myDate = sdf.parse(sdf.format(x.getNgayLapHD()));
				series1.add(new Day(myDate), x.getTongTien());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        });

        TimeSeriesCollection dataset = new TimeSeriesCollection(series1);
        JFreeChart chart = createChart123(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 800));
        //setContentPane(chartPanel);
	    return new ChartPanel(chart);
	}

}

           