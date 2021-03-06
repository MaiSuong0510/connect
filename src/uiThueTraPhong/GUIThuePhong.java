package uiThueTraPhong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import com.toedter.calendar.JDateChooser;
import entities.KhachHang;
import entities.PhieuDatPhong;
import entities.Phong;
import services.QuanLyHoaDon;
import services.QuanLyKhachHang;
import services.QuanLyPhong;
import services.QuanLyThueTra;
import uiLogin.GUIMenu;

public class GUIThuePhong extends JFrame implements ActionListener, MouseListener{
	JPanel pnlWest;
	JPanel pnlEast;
	JPanel pnlSouth;
	JTable table;
	DefaultTableModel tableModel;
	JScrollPane scroll;
	Scanner lineScan;
	public int thutu;
	JComboBox<String> cboKhachHang;
	JRadioButton radSex;
	public static String maPhieu;
	private JButton btnXoaRong;
	private JButton btnThue, btnTra;
	private JButton btnBack;
	private JList jList = createJList();
	JTextField txtMaKhachHang, txttenKhachHang, txtsoDienThoai, txtsoChungMinhNhanDan, txtDate;
	//Label tr??i v?? ph???i c???a kh??ch h??ng
	JLabel lbMa, lbTen, lbNgaySinh, lbgioiTinh, lbSDT, lbCMND, lbNV;
	//Label ph???i c???a Ph??ng
	JLabel lbNgayDatPhong,lbNgayTraPhong, lbLoaiPhong, lbGhiChu, lbGiaPhong, lbMaPhong;
	//TextField ph???i c???a Ph??ng
	JTextField txtMaPhong, txtLoaiPhong, txtGhiChu, txtGiaPhong;
	
	JDateChooser cldNgayDen, cldNgayDi, cldNgaySinh;
	public static String cmndKH;
	public static String maPhong;
	private GUIMenu parent=null;
	private Connection con;
	private Statement stmt;
	
	Phong p;
	
	public GUIThuePhong(Phong p) {
		
		
		
		super("Thu?? ph??ng");
		
		this.p = p;
		
		
		setSize(900,900);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		Box bc,b1,b2,b3,b31;
		bc = Box.createVerticalBox();
		add(bc);
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(b2 = Box.createVerticalBox());
			b2.setBorder(BorderFactory.createTitledBorder("T??c v???"));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbMa = new JLabel("M?? Kh??ch H??ng: "));
					b3.add(Box.createHorizontalStrut(25));
					b3.add(txtMaKhachHang = new JTextField(30));
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbgioiTinh = new JLabel("Gi???i T??nh:"));
					b3.add(Box.createHorizontalStrut(20));
					b3.add(radSex = new JRadioButton("N???"));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbTen = new JLabel("T??n Kh??ch H??ng: "));
					b3.add(Box.createHorizontalStrut(16));
					b3.add(b31 = Box.createVerticalBox());
						b31.add(new JScrollPane(jList));
						b31.add(txttenKhachHang = createTextField());
						b3.add(Box.createHorizontalStrut(20));				
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbSDT = new JLabel("S??? ??i???n Tho???i:"));
					b3.add(Box.createHorizontalStrut(33));
					b3.add(txtsoDienThoai = new JTextField(30));
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbCMND = new JLabel("S??? CMND:"));
					b3.add(Box.createHorizontalStrut(58));
					b3.add(txtsoChungMinhNhanDan = new JTextField(30));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbNgaySinh = new JLabel("Ng??y Sinh: "));
					b3.add(Box.createHorizontalStrut(44));
					b3.add(cldNgaySinh = new JDateChooser());
					String date = "dd/MM/yyyy";
					cldNgaySinh.setDateFormatString(date);
					b3.add(Box.createHorizontalStrut(100));
			
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbNgayDatPhong = new JLabel("Ng??y ?????t Ph??ng:"));
					b3.add(Box.createHorizontalStrut(10));
					b3.add(cldNgayDen = new JDateChooser());
					cldNgayDen.setDateFormatString(date);
					b3.add(Box.createHorizontalStrut(10));
					b3.add(lbNgayTraPhong = new JLabel("Ng??y Tr??? Ph??ng:"));
					b3.add(Box.createHorizontalStrut(10));
					b3.add(cldNgayDi = new JDateChooser());
					cldNgayDi.setDateFormatString(date);
					b3.add(Box.createVerticalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(350));
					b3.add(btnXoaRong = new JButton("L??m M???i", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\reload.png")));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(btnThue = new JButton("????ng K?? ?????t Ph??ng", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\booking.png")));
					b3.add(Box.createHorizontalStrut(20));
					b3.add(btnTra = new JButton("Tr??? Ph??ng", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\booking.png")));

				b2.add(Box.createVerticalStrut(20));
			b1.add(b2 = Box.createVerticalBox());
				b2.setBorder(BorderFactory.createTitledBorder("Th??ng Tin Ph??ng"));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbMaPhong = new JLabel("M?? S??? Ph??ng:"));
					b3.add(Box.createHorizontalStrut(10));
					b3.add(txtMaPhong = new JTextField(25));
					b3.add(Box.createHorizontalStrut(200));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbLoaiPhong = new JLabel("Lo???i Ph??ng:"));
					b3.add(Box.createHorizontalStrut(23));
					b3.add(txtLoaiPhong = new JTextField(25));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbGhiChu = new JLabel("Ghi Ch??:"));
					b3.add(Box.createHorizontalStrut(46));
					b3.add(txtGhiChu = new JTextField(25));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbGiaPhong = new JLabel("Gi?? Ph??ng:"));
					b3.add(Box.createHorizontalStrut(30));
					b3.add(txtGiaPhong= new JTextField(25));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(200));
				
		bc.add(b1 = Box.createVerticalBox());
				String[] cols = "M?? Phi???u;H??? T??n;CMND;Ng??y ?????n;Ng??y ??i;T??nh Tr???ng".split(";");
				tableModel = new DefaultTableModel(cols, 0);
				table = new JTable(tableModel) {
					public boolean isCellEditable(int row, int col) {
						return false;
					}
					public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
						Component c = super.prepareRenderer(renderer, row, col);
						if (row % 2 == 0 && !isCellSelected(row, col)) {
							c.setBackground(Color.decode("#F1F1F1"));
						} else if (!isCellSelected(row, col)) {
							c.setBackground(Color.decode("#D7F1FF"));
						} else {
							c.setBackground(Color.decode("#25C883"));
						}
						return c;
					}
				};
				table.setAutoCreateRowSorter(true);
				
				JTableHeader header1 = table.getTableHeader();
				header1.setBackground(Color.CYAN);
				header1.setOpaque(false);
				// x??t c???ng c???t
				table.getTableHeader().setReorderingAllowed(false);
			JScrollPane scroll = new JScrollPane(table);
			b1.add(scroll);
		//add(pnlEast = new JPanel(), BorderLayout.EAST);
		// pnlEast.setBackground(Color.black);

		add(pnlSouth = new JPanel(), BorderLayout.SOUTH);
		pnlSouth.setBackground(Color.LIGHT_GRAY);
		pnlSouth.setBorder(BorderFactory.createTitledBorder("T??y ch???n"));
		pnlSouth.setPreferredSize(new Dimension(0, 80));
		Box b11 = Box.createHorizontalBox();
		b11.add(btnBack = new JButton("L??i V??? Trang Tr?????c", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\logout.png")));
		btnBack.setBackground(Color.red);
		b11.add(Box.createHorizontalStrut(300));
		 b11.add(lbNV = new JLabel("Nh??n Vi??n: V?? Ho??ng Trang"));
		 b11.add(Box.createHorizontalStrut(20));
		 b11.add(txtDate = new JTextField());
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = new Date();
		 txtDate.setText(sdf.format(date1.getTime()));
		
		pnlSouth.add(b11);
	
		//x??t label c??ng 1 font
		List<JLabel> tmpLb = Arrays.asList(lbNV,lbTen,lbMa,lbCMND,lbNgayDatPhong,lbNgayTraPhong,lbSDT,lbgioiTinh, lbNgaySinh);
		List<JLabel> tmpLb2 = Arrays.asList(lbMaPhong,lbLoaiPhong,lbGhiChu,lbGiaPhong);
		setFontLabel(tmpLb);
		setFontLabel(tmpLb2);
		
		//x??t TextField is false
		List<JTextField> tmpTx1 = Arrays.asList(txtMaPhong, txtLoaiPhong, txtGhiChu, txtGiaPhong,txtMaKhachHang,txtDate);
		setTextisFalse(tmpTx1,false);
		
		//????ng k?? s??? ki???n c??c button
		btnThue.addActionListener(this);
		btnTra.addActionListener(this);
		btnBack.addActionListener(this);
		btnXoaRong.addActionListener(this);
		table.addMouseListener(this);
		jList.addMouseListener(this);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//================Load Database==============
		database.Database.getInstance().connect();
		hienDataPhong();
		updateTableData();
		setVisible(true);
		
	}
	
	public GUIThuePhong() {
		super("Thu?? ph??ng");
		
		
		
		setSize(900,900);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		Box bc,b1,b2,b3,b31;
		bc = Box.createVerticalBox();
		add(bc);
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(b2 = Box.createVerticalBox());
			b2.setBorder(BorderFactory.createTitledBorder("T??c v???"));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbMa = new JLabel("M?? Kh??ch H??ng: "));
					b3.add(Box.createHorizontalStrut(25));
					b3.add(txtMaKhachHang = new JTextField(30));
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbgioiTinh = new JLabel("Gi???i T??nh:"));
					b3.add(Box.createHorizontalStrut(20));
					b3.add(radSex = new JRadioButton("N???"));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbTen = new JLabel("T??n Kh??ch H??ng: "));
					b3.add(Box.createHorizontalStrut(16));
					b3.add(b31 = Box.createVerticalBox());
						b31.add(new JScrollPane(jList));
						b31.add(txttenKhachHang = createTextField());
						b3.add(Box.createHorizontalStrut(20));				
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbSDT = new JLabel("S??? ??i???n Tho???i:"));
					b3.add(Box.createHorizontalStrut(33));
					b3.add(txtsoDienThoai = new JTextField(30));
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbCMND = new JLabel("S??? CMND:"));
					b3.add(Box.createHorizontalStrut(58));
					b3.add(txtsoChungMinhNhanDan = new JTextField(30));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbNgaySinh = new JLabel("Ng??y Sinh: "));
					b3.add(Box.createHorizontalStrut(44));
					b3.add(cldNgaySinh = new JDateChooser());
					String date = "dd/MM/yyyy";
					cldNgaySinh.setDateFormatString(date);
					b3.add(Box.createHorizontalStrut(100));
			
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbNgayDatPhong = new JLabel("Ng??y ?????t Ph??ng:"));
					b3.add(Box.createHorizontalStrut(10));
					b3.add(cldNgayDen = new JDateChooser());
					cldNgayDen.setDateFormatString(date);
					b3.add(Box.createHorizontalStrut(10));
					b3.add(lbNgayTraPhong = new JLabel("Ng??y Tr??? Ph??ng:"));
					b3.add(Box.createHorizontalStrut(10));
					b3.add(cldNgayDi = new JDateChooser());
					cldNgayDi.setDateFormatString(date);
					b3.add(Box.createVerticalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(350));
					b3.add(btnXoaRong = new JButton("L??m M???i", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\reload.png")));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(btnThue = new JButton("????ng K?? ?????t Ph??ng", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\booking.png")));
					b3.add(Box.createHorizontalStrut(20));
					b3.add(btnTra = new JButton("Tr??? Ph??ng", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\booking.png")));

				b2.add(Box.createVerticalStrut(20));
			b1.add(b2 = Box.createVerticalBox());
				b2.setBorder(BorderFactory.createTitledBorder("Th??ng Tin Ph??ng"));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbMaPhong = new JLabel("M?? S??? Ph??ng:"));
					b3.add(Box.createHorizontalStrut(10));
					b3.add(txtMaPhong = new JTextField(25));
					b3.add(Box.createHorizontalStrut(200));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbLoaiPhong = new JLabel("Lo???i Ph??ng:"));
					b3.add(Box.createHorizontalStrut(23));
					b3.add(txtLoaiPhong = new JTextField(25));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbGhiChu = new JLabel("Ghi Ch??:"));
					b3.add(Box.createHorizontalStrut(46));
					b3.add(txtGhiChu = new JTextField(25));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbGiaPhong = new JLabel("Gi?? Ph??ng:"));
					b3.add(Box.createHorizontalStrut(30));
					b3.add(txtGiaPhong= new JTextField(25));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(200));
				
		bc.add(b1 = Box.createVerticalBox());
				String[] cols = "M?? Phi???u;H??? T??n;CMND;Ng??y ?????n;Ng??y ??i;T??nh Tr???ng".split(";");
				tableModel = new DefaultTableModel(cols, 0);
				table = new JTable(tableModel) {
					public boolean isCellEditable(int row, int col) {
						return false;
					}
					public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
						Component c = super.prepareRenderer(renderer, row, col);
						if (row % 2 == 0 && !isCellSelected(row, col)) {
							c.setBackground(Color.decode("#F1F1F1"));
						} else if (!isCellSelected(row, col)) {
							c.setBackground(Color.decode("#D7F1FF"));
						} else {
							c.setBackground(Color.decode("#25C883"));
						}
						return c;
					}
				};
				table.setAutoCreateRowSorter(true);
				
				JTableHeader header1 = table.getTableHeader();
				header1.setBackground(Color.CYAN);
				header1.setOpaque(false);
				// x??t c???ng c???t
				table.getTableHeader().setReorderingAllowed(false);
			JScrollPane scroll = new JScrollPane(table);
			b1.add(scroll);
		//add(pnlEast = new JPanel(), BorderLayout.EAST);
		// pnlEast.setBackground(Color.black);

		add(pnlSouth = new JPanel(), BorderLayout.SOUTH);
		pnlSouth.setBackground(Color.LIGHT_GRAY);
		pnlSouth.setBorder(BorderFactory.createTitledBorder("T??y ch???n"));
		pnlSouth.setPreferredSize(new Dimension(0, 80));
		Box b11 = Box.createHorizontalBox();
		b11.add(btnBack = new JButton("L??i V??? Trang Tr?????c", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\logout.png")));
		btnBack.setBackground(Color.red);
		b11.add(Box.createHorizontalStrut(300));
		 b11.add(lbNV = new JLabel("Nh??n Vi??n: V?? Ho??ng Trang"));
		 b11.add(Box.createHorizontalStrut(20));
		 b11.add(txtDate = new JTextField());
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = new Date();
		 txtDate.setText(sdf.format(date1.getTime()));
		
		pnlSouth.add(b11);
	
		//x??t label c??ng 1 font
		List<JLabel> tmpLb = Arrays.asList(lbNV,lbTen,lbMa,lbCMND,lbNgayDatPhong,lbNgayTraPhong,lbSDT,lbgioiTinh, lbNgaySinh);
		List<JLabel> tmpLb2 = Arrays.asList(lbMaPhong,lbLoaiPhong,lbGhiChu,lbGiaPhong);
		setFontLabel(tmpLb);
		setFontLabel(tmpLb2);
		
		//x??t TextField is false
		List<JTextField> tmpTx1 = Arrays.asList(txtMaPhong, txtLoaiPhong, txtGhiChu, txtGiaPhong,txtMaKhachHang,txtDate);
		setTextisFalse(tmpTx1,false);
		
		//????ng k?? s??? ki???n c??c button
		btnThue.addActionListener(this);
		btnTra.addActionListener(this);
		btnBack.addActionListener(this);
		btnXoaRong.addActionListener(this);
		table.addMouseListener(this);
		jList.addMouseListener(this);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//================Load Database==============
		database.Database.getInstance().connect();
		hienDataPhong();
		updateTableData();
		setVisible(true);
		
	}
	public GUIThuePhong(GUIMenu parent) {
		this();
		this.parent = parent;
	}
	public void setFontLabel(List<JLabel> listLabel) {
		listLabel.forEach(x->{
			x.setFont(new Font("Times New Roman", Font.BOLD, 15));
		});
	}
	
	public void setTextisFalse(List<JTextField> listText, boolean bool) {
		listText.forEach(x->{
			x.setEnabled(bool);
			x.setDisabledTextColor(Color.BLACK);
		});
	}

	public void hienDataPhong() {
//		QuanLyPhong qlp = new QuanLyPhong();
//		ArrayList<Phong> dsPhong = qlp.timKiem(GUIThueTraPhong.maPhong, "T??m Theo M?? Ph??ng");
//		p = dsPhong.get(thutu);
//		dsPhong.forEach(p->{
			txtMaPhong.setText(p.getId()+100+"");
			txtLoaiPhong.setText(p.getLoaiPhong());
			txtGhiChu.setText(p.getGhiChu());
			DecimalFormat df = new DecimalFormat("###,000");	
			txtGiaPhong.setText(df.format(p.getGiaPhong())+" VN??");
//		});
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if(src.equals(btnTra)) {
			int row = table.getSelectedRow();
			if(row==-1) {
				JOptionPane.showMessageDialog(this,"C???n Ch???n Phi???u Tr???");
				return;
			}
			if(table.getValueAt(row, 5).toString().equals("???? ?????t")) {
				JOptionPane.showMessageDialog(this, "Ph??ng Ch??a ??? - Kh??ng Th??? Tr???");
				return;
			}
			String cmnd = table.getValueAt(row, 2).toString();
			maPhieu = table.getValueAt(row, 0).toString();
			cmndKH = cmnd;
			maPhong = txtMaPhong.getText();
			new GUITraPhong(parent);
			dispose();
		}
		if(src.equals(btnThue)){
			datPhong();
		}
		if(src.equals(btnXoaRong)) {
			Cancel();
		}
		if(src.equals(btnBack)) {
//			GUI = new GUIMenu(1);
//			gui.showPanel(new GUIThueTraPhong(this));
//			dispose();
//			System.out.println("???? BACK");
		}
	}

	private void Cancel() {
		txtMaKhachHang.setText("");
		txttenKhachHang.setText("");
		txtsoDienThoai.setText("");
		txtsoChungMinhNhanDan.setText("");
		((JTextField)cldNgayDen.getDateEditor().getUiComponent()).setText("");
		cldNgayDen.setDate(null);
		((JTextField)cldNgayDi.getDateEditor().getUiComponent()).setText("");
		cldNgayDi.setDate(null);
		((JTextField)cldNgaySinh.getDateEditor().getUiComponent()).setText("");
		cldNgaySinh.setDate(null);
		table.clearSelection();
		List<JTextField> tmpTx2 = Arrays.asList(txttenKhachHang,txtsoDienThoai,txtsoChungMinhNhanDan);
		setTextisFalse(tmpTx2, true);	
		cldNgayDen.setEnabled(true);
		cldNgayDi.setEnabled(true);
		cldNgaySinh.setEnabled(true);
		jList.setEnabled(true);
	}
	private String catChuoi(String s) {
		int startString = s.indexOf(" [");
		int finishString = s.indexOf("]");
		String cmnd = s.substring(startString + 2, finishString);
		//System.out.println(s+ " " +cmnd);
		return cmnd;
	}
	private void hienDataKhachHang() {
		String s = jList.getSelectedValue().toString();
		String cmnd = catChuoi(s);
		QuanLyKhachHang qlkh = new QuanLyKhachHang();
		KhachHang kh;
		kh = qlkh.chiTietKhachHang(cmnd);
		txttenKhachHang.setText(kh.getHoTen());
		txtsoChungMinhNhanDan.setText(kh.getcMND());
		txtsoDienThoai.setText(kh.getsDT());
		if(kh.getGioiTinh() == 1) {
			radSex.setSelected(true);
		}else radSex.setSelected(false);
		cldNgaySinh.setDate(kh.getNgaySinh());
		txtMaKhachHang.setText(kh.getMa()+"");
		
	}
	private void xoaTableData() {
		while(tableModel.getRowCount()>0) {
			tableModel.removeRow(0);
		}
	}
	private void updateTableData() {
		xoaTableData();
		QuanLyThueTra ql = new  QuanLyThueTra();
		ArrayList<PhieuDatPhong> listPDP = ql.listPhieuDatPhong(txtMaPhong.getText().trim());
		String tinhTrangPhieu ="";
		for(PhieuDatPhong pdp : listPDP){
			if(pdp.getTinhTrangPhieu() == 4) {
				tinhTrangPhieu = "?????n H???n Check Out";
			}else if(pdp.getTinhTrangPhieu() == 2) {
				tinhTrangPhieu = "??ang Check In";
			}else if(pdp.getTinhTrangPhieu() == 3) {
				tinhTrangPhieu = "??ang S??? D???ng";
			}else tinhTrangPhieu = "???? ?????t";//1
			String[] rowData = {pdp.getId()+"",pdp.getKhachHang().getHoTen(),pdp.getKhachHang().getcMND(),pdp.getNgayDen()+"",pdp.getNgayDi()+"",tinhTrangPhieu};
			tableModel.addRow(rowData);
		};
		table.setModel(tableModel);
	}
	
	/*
	 * Th???c Hi???n Vi???c L??u th??ng tin ph??ng kh??ch ?????t -> T??nh tr???ng ph??ng = 1, t??nh tr???ng Phi???u = 0
	 * C??ch th???c hi???n:
	 * + Khi kh??ch ch??a c?? trong dtb: L??u dtb kh??ch h??ng + ph??ng + t???o phi???u ?????t ph??ng + ho?? ????n
	 * + Khi kh??ch ???? c?? trong dtb: L??u dtb ph??ng + t???o phi???u ?????t ph??ng + ho?? ????n 
	 */
	private void themKhachHang() {
		String ten = txttenKhachHang.getText().trim();
		String ma = "";
		String CMND = txtsoChungMinhNhanDan.getText().trim();
		Date ngaySinh = cldNgaySinh.getDate();
		int gioiTinh;
		if(radSex.isSelected()==true) {
			gioiTinh = 1;
		}else gioiTinh = 0;
		String sDT = txtsoDienThoai.getText().trim();
		String type = "Insert";
		QuanLyKhachHang qlkh = new QuanLyKhachHang();
		if(qlkh.curdKhachHang(ma, ten, CMND, String.valueOf(ngaySinh), gioiTinh, sDT, type)) {
			updateTableData();
			JOptionPane.showMessageDialog(this,"Th??m Th??nh C??ng");
			table.clearSelection();
		}
	}
	
	private boolean isKHExist(String cmnd) {
		KhachHang kh;
		QuanLyKhachHang qlkh = new QuanLyKhachHang();
		kh = qlkh.chiTietKhachHang(cmnd);
		System.out.println("407: "+kh);
		if(kh == null) {
			return true;
		}
		return false;
	}
	public void datPhong() {
		String cmnd = txtsoChungMinhNhanDan.getText().trim();
		if(cmnd.equals("")) {
			JOptionPane.showMessageDialog(this, "C???n Nh???p Th??ng Tin Kh??ch H??ng!");
			return;
		}
		String maKH = "";
		if(isKHExist(cmnd) == true) {
			themKhachHang();
			KhachHang kh;
			QuanLyKhachHang qlkh = new QuanLyKhachHang();
			kh = qlkh.chiTietKhachHang(cmnd);
			maKH = kh.getMa()+"";
		}else maKH = txtMaKhachHang.getText();
		QuanLyThueTra qlttr = new QuanLyThueTra();
		if(cldNgayDen.getDate() == null || cldNgayDi.getDate() == null) {
			JOptionPane.showMessageDialog(this, "Thi???u Ng??y ?????n, Ng??y ??i");
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date()); 
		Date dateNow = new Date();
	
		String ngayDen = sdf.format(cldNgayDen.getDate());
		String ngayDi = sdf.format(cldNgayDi.getDate());
		String ngayHienTai = sdf.format(dateNow);
				
		if(ngayDen.compareTo(ngayDi)>= 0) {
			JOptionPane.showMessageDialog(this, "Ng??y ?????n <= Ng??y ??i");
			return;
		}
//		if(ngayDen.compareTo(ngayHienTai)<0) {
//			JOptionPane.showMessageDialog(this, "Ng??y ?????n Ph???i L???n H??n Ng??y Hi???n T???i: "+ngayHienTai);
//			return;
//		}
		//1 l?? m?? nh??n vi??n, ????? t???m

		String dateDi = sdf.format(cldNgayDi.getDate());
		String dateDen = sdf.format(cldNgayDen.getDate());
		System.out.println(txtMaPhong.getText());
		System.out.println(qlttr.checkCreatePhieuDatPhong(dateDi,dateDen, txtMaPhong.getText()) );

		if(qlttr.checkCreatePhieuDatPhong(dateDi,dateDen, txtMaPhong.getText()).size()==0) {
			//1 l?? m?? nh??n vi??n t???m
			if(qlttr.createPDP(txtMaPhong.getText().trim(), maKH, "1", dateDen, dateDi)) {
				JOptionPane.showMessageDialog(this,"?????t Ph??ng Th??nh C??ng");
				//1 - 0 admin - user
				GUIMenu gui = new GUIMenu(1);
				gui.setVisible(false);
				gui.showPanel(new GUIThueTraPhong(gui));
				//reloadData(gui);
				parent.showPanel(new GUIThueTraPhong(parent));
				updateTableData();
				String maPDP = table.getValueAt(table.getRowCount()-1, 0).toString();
				//t???o ho?? ????n
				QuanLyHoaDon qlhd = new QuanLyHoaDon();
				QuanLyPhong qlp = new QuanLyPhong();
				Phong p = qlp.tim1Phong(Integer.parseInt(txtMaPhong.getText())-100);
				long ngay = cldNgayDi.getDate().getTime() - cldNgayDen.getDate().getTime();
				ngay = ngay/(1000*60*60*24);
				double tongTien = ngay*p.getGiaPhong();
				//m?? nh??n vi??n ????? t???m l?? 1
				qlhd.createHD(maPDP, 1+"", 0, dateDi, 0);
				Cancel();
			}else JOptionPane.showMessageDialog(this,"L???i");
		}else {
			JOptionPane.showMessageDialog(this, "Ph??ng ???? ???????c ?????t Trong Kho???ng Th???i Gian N??y!");
			return;
		}	
	}
	private ArrayList<String> listTenKH() {
		QuanLyKhachHang qlkh = new QuanLyKhachHang();
		ArrayList<KhachHang> listKH = qlkh.dsKhachHang();
		ArrayList<String> listTen = new ArrayList<String>();
		
		listKH.forEach(x->{
			listTen.add(x.getHoTen()+" ["+x.getcMND()+"]");
			System.out.println(x.getHoTen());
		});
		return listTen;
	}

	private JTextField createTextField() {
        final JTextField field = new JTextField(15);
        field.getDocument().addDocumentListener(new DocumentListener(){
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) {}
            private void filter() {
                String filter = field.getText();
                filterModel((DefaultListModel<String>)jList.getModel(), filter);
            }
        });
        return field;
    }

    private JList createJList() {
        JList list = new JList(createDefaultListModel());
        list.setVisibleRowCount(5);
        return list;
    }
    private ListModel<String> createDefaultListModel() {
        DefaultListModel<String> model = new DefaultListModel<>();
        ArrayList<String> listTenKh = listTenKH();
        for (String s : listTenKh) {
            model.addElement(s);
        }
        return model;
    }

    public void filterModel(DefaultListModel<String> model, String filter) {
        ArrayList<String> listTenKh = listTenKH();
    	for (String s : listTenKh) {
    		//s=s.trim();	
            if (!s.startsWith(filter)) {
                if (model.contains(s)) {
                    model.removeElement(s);
                }
            } else {
                if (!model.contains(s)) {
                    model.addElement(s);
                }
            }
        }
    }
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if(src.equals(jList)) {
			if(jList.isEnabled() != false)
			hienDataKhachHang();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
//	public void PhieuDatPhong() {
//		String sql = "exec ThemPhieuDatPhong " + txtMaPhong.getText() + ", " + txtMaKhachHang.getText() + ", " + 1 + ", '"
//				 +cldNgayDen.getDate() +"', '" + cldNgayDi.getDate() + "', '" + cldNgayDi.getDate() + "'";
//		try {
//			String connection_URL = "jdbc:sqlserver://localhost:1433;databaseName=QLKS;user=sa;password=1234567";
//			con = DriverManager.getConnection(connection_URL);
//			stmt = con.createStatement();
//			stmt.executeUpdate(sql);
//			JOptionPane.showMessageDialog(this, "Dat Phong Thanh Cong");
//			tableModel.addRow(new String[] {
//					txtMaPhong.getText(), 
//					txttenKhachHang.getText(),
//					txtsoChungMinhNhanDan.getText(),
//					String.valueOf(cldNgayDen.getDate()),
//					String.valueOf(cldNgayDi.getDate()),
//					"Day du"
//		});
//			
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			JOptionPane.showMessageDialog(this, "Loi ");
//		}
//	}
}
