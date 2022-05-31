package uiQuanLy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

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
import org.jfree.chart.plot.PlotOrientation;

import java.awt.EventQueue;


import services.ThongKe;

public class GUIThongKePDP  extends JFrame implements ActionListener{
	ThongKe tk = new ThongKe();
	JComboBox<String> cmbThang, cmbNam;
	private static String thangPDP, namPDP ;
	JButton btnThongKe,btnOK;
	JPanel chartPanel = createChartPanel(LocalDate.now().getMonthValue()+"", "2019");
	private JPanel childPanel;

	public GUIThongKePDP() {
		setTitle("Số Lượng Đơn Đặt Phòng");
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
	public void showPanel(JPanel panel) {
		childPanel = panel;
		chartPanel.removeAll();
		chartPanel.add(childPanel);
		chartPanel.validate();
	}
	
	private JPanel createChartPanel(String thangPDP, String namPDP) {
	    String chartTitle = "Số Lượng Đơn Đặt Phòng Trong Tháng/Năm";
	    String categoryAxisLabel = "Mã Phòng (100)";
	    String valueAxisLabel = "Số Lượng Đơn";
	    ThongKe tk = new ThongKe();
	    
	    JFreeChart chart = ChartFactory.createLineChart(chartTitle,
	            categoryAxisLabel, valueAxisLabel, tk.soLuongPDP(thangPDP, namPDP), null, rootPaneCheckingEnabled, rootPaneCheckingEnabled, rootPaneCheckingEnabled);
	 
	    return new ChartPanel(chart);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if(src.equals(btnOK)) {
			thangPDP = cmbThang.getSelectedItem().toString();
			namPDP = cmbNam.getSelectedItem().toString();
			showPanel(createChartPanel(thangPDP,namPDP));
		}
	}
}
