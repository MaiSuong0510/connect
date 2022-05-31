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
	//Label trái và phải của khách hàng
	JLabel lbMa, lbTen, lbNgaySinh, lbgioiTinh, lbSDT, lbCMND, lbNV;
	//Label phải của Phòng
	JLabel lbNgayDatPhong,lbNgayTraPhong, lbLoaiPhong, lbGhiChu, lbGiaPhong, lbMaPhong;
	//TextField phải của Phòng
	JTextField txtMaPhong, txtLoaiPhong, txtGhiChu, txtGiaPhong;
	
	JDateChooser cldNgayDen, cldNgayDi, cldNgaySinh;
	public static String cmndKH;
	public static String maPhong;
	private GUIMenu parent=null;
	private Connection con;
	private Statement stmt;
	
	Phong p;
	
	public GUIThuePhong(Phong p) {
		
		
		
		super("Thuê phòng");
		
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
			b2.setBorder(BorderFactory.createTitledBorder("Tác vụ"));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbMa = new JLabel("Mã Khách Hàng: "));
					b3.add(Box.createHorizontalStrut(25));
					b3.add(txtMaKhachHang = new JTextField(30));
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbgioiTinh = new JLabel("Giới Tính:"));
					b3.add(Box.createHorizontalStrut(20));
					b3.add(radSex = new JRadioButton("Nữ"));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbTen = new JLabel("Tên Khách Hàng: "));
					b3.add(Box.createHorizontalStrut(16));
					b3.add(b31 = Box.createVerticalBox());
						b31.add(new JScrollPane(jList));
						b31.add(txttenKhachHang = createTextField());
						b3.add(Box.createHorizontalStrut(20));				
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbSDT = new JLabel("Số Điện Thoại:"));
					b3.add(Box.createHorizontalStrut(33));
					b3.add(txtsoDienThoai = new JTextField(30));
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbCMND = new JLabel("Số CMND:"));
					b3.add(Box.createHorizontalStrut(58));
					b3.add(txtsoChungMinhNhanDan = new JTextField(30));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbNgaySinh = new JLabel("Ngày Sinh: "));
					b3.add(Box.createHorizontalStrut(44));
					b3.add(cldNgaySinh = new JDateChooser());
					String date = "dd/MM/yyyy";
					cldNgaySinh.setDateFormatString(date);
					b3.add(Box.createHorizontalStrut(100));
			
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbNgayDatPhong = new JLabel("Ngày Đặt Phòng:"));
					b3.add(Box.createHorizontalStrut(10));
					b3.add(cldNgayDen = new JDateChooser());
					cldNgayDen.setDateFormatString(date);
					b3.add(Box.createHorizontalStrut(10));
					b3.add(lbNgayTraPhong = new JLabel("Ngày Trả Phòng:"));
					b3.add(Box.createHorizontalStrut(10));
					b3.add(cldNgayDi = new JDateChooser());
					cldNgayDi.setDateFormatString(date);
					b3.add(Box.createVerticalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(350));
					b3.add(btnXoaRong = new JButton("Làm Mới", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\reload.png")));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(btnThue = new JButton("Đăng Ký Đặt Phòng", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\booking.png")));
					b3.add(Box.createHorizontalStrut(20));
					b3.add(btnTra = new JButton("Trả Phòng", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\booking.png")));

				b2.add(Box.createVerticalStrut(20));
			b1.add(b2 = Box.createVerticalBox());
				b2.setBorder(BorderFactory.createTitledBorder("Thông Tin Phòng"));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbMaPhong = new JLabel("Mã Số Phòng:"));
					b3.add(Box.createHorizontalStrut(10));
					b3.add(txtMaPhong = new JTextField(25));
					b3.add(Box.createHorizontalStrut(200));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbLoaiPhong = new JLabel("Loại Phòng:"));
					b3.add(Box.createHorizontalStrut(23));
					b3.add(txtLoaiPhong = new JTextField(25));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbGhiChu = new JLabel("Ghi Chú:"));
					b3.add(Box.createHorizontalStrut(46));
					b3.add(txtGhiChu = new JTextField(25));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbGiaPhong = new JLabel("Giá Phòng:"));
					b3.add(Box.createHorizontalStrut(30));
					b3.add(txtGiaPhong= new JTextField(25));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(200));
				
		bc.add(b1 = Box.createVerticalBox());
				String[] cols = "Mã Phiếu;Họ Tên;CMND;Ngày Đến;Ngày Đi;Tình Trạng".split(";");
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
				// xét cứng cột
				table.getTableHeader().setReorderingAllowed(false);
			JScrollPane scroll = new JScrollPane(table);
			b1.add(scroll);
		//add(pnlEast = new JPanel(), BorderLayout.EAST);
		// pnlEast.setBackground(Color.black);

		add(pnlSouth = new JPanel(), BorderLayout.SOUTH);
		pnlSouth.setBackground(Color.LIGHT_GRAY);
		pnlSouth.setBorder(BorderFactory.createTitledBorder("Tùy chọn"));
		pnlSouth.setPreferredSize(new Dimension(0, 80));
		Box b11 = Box.createHorizontalBox();
		b11.add(btnBack = new JButton("Lùi Về Trang Trước", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\logout.png")));
		btnBack.setBackground(Color.red);
		b11.add(Box.createHorizontalStrut(300));
		 b11.add(lbNV = new JLabel("Nhân Viên: Vũ Hoàng Trang"));
		 b11.add(Box.createHorizontalStrut(20));
		 b11.add(txtDate = new JTextField());
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = new Date();
		 txtDate.setText(sdf.format(date1.getTime()));
		
		pnlSouth.add(b11);
	
		//xét label cùng 1 font
		List<JLabel> tmpLb = Arrays.asList(lbNV,lbTen,lbMa,lbCMND,lbNgayDatPhong,lbNgayTraPhong,lbSDT,lbgioiTinh, lbNgaySinh);
		List<JLabel> tmpLb2 = Arrays.asList(lbMaPhong,lbLoaiPhong,lbGhiChu,lbGiaPhong);
		setFontLabel(tmpLb);
		setFontLabel(tmpLb2);
		
		//xét TextField is false
		List<JTextField> tmpTx1 = Arrays.asList(txtMaPhong, txtLoaiPhong, txtGhiChu, txtGiaPhong,txtMaKhachHang,txtDate);
		setTextisFalse(tmpTx1,false);
		
		//Đăng ký sự kiện các button
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
		super("Thuê phòng");
		
		
		
		setSize(900,900);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		Box bc,b1,b2,b3,b31;
		bc = Box.createVerticalBox();
		add(bc);
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(b2 = Box.createVerticalBox());
			b2.setBorder(BorderFactory.createTitledBorder("Tác vụ"));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbMa = new JLabel("Mã Khách Hàng: "));
					b3.add(Box.createHorizontalStrut(25));
					b3.add(txtMaKhachHang = new JTextField(30));
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbgioiTinh = new JLabel("Giới Tính:"));
					b3.add(Box.createHorizontalStrut(20));
					b3.add(radSex = new JRadioButton("Nữ"));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbTen = new JLabel("Tên Khách Hàng: "));
					b3.add(Box.createHorizontalStrut(16));
					b3.add(b31 = Box.createVerticalBox());
						b31.add(new JScrollPane(jList));
						b31.add(txttenKhachHang = createTextField());
						b3.add(Box.createHorizontalStrut(20));				
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbSDT = new JLabel("Số Điện Thoại:"));
					b3.add(Box.createHorizontalStrut(33));
					b3.add(txtsoDienThoai = new JTextField(30));
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbCMND = new JLabel("Số CMND:"));
					b3.add(Box.createHorizontalStrut(58));
					b3.add(txtsoChungMinhNhanDan = new JTextField(30));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbNgaySinh = new JLabel("Ngày Sinh: "));
					b3.add(Box.createHorizontalStrut(44));
					b3.add(cldNgaySinh = new JDateChooser());
					String date = "dd/MM/yyyy";
					cldNgaySinh.setDateFormatString(date);
					b3.add(Box.createHorizontalStrut(100));
			
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbNgayDatPhong = new JLabel("Ngày Đặt Phòng:"));
					b3.add(Box.createHorizontalStrut(10));
					b3.add(cldNgayDen = new JDateChooser());
					cldNgayDen.setDateFormatString(date);
					b3.add(Box.createHorizontalStrut(10));
					b3.add(lbNgayTraPhong = new JLabel("Ngày Trả Phòng:"));
					b3.add(Box.createHorizontalStrut(10));
					b3.add(cldNgayDi = new JDateChooser());
					cldNgayDi.setDateFormatString(date);
					b3.add(Box.createVerticalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(350));
					b3.add(btnXoaRong = new JButton("Làm Mới", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\reload.png")));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(btnThue = new JButton("Đăng Ký Đặt Phòng", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\booking.png")));
					b3.add(Box.createHorizontalStrut(20));
					b3.add(btnTra = new JButton("Trả Phòng", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\booking.png")));

				b2.add(Box.createVerticalStrut(20));
			b1.add(b2 = Box.createVerticalBox());
				b2.setBorder(BorderFactory.createTitledBorder("Thông Tin Phòng"));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbMaPhong = new JLabel("Mã Số Phòng:"));
					b3.add(Box.createHorizontalStrut(10));
					b3.add(txtMaPhong = new JTextField(25));
					b3.add(Box.createHorizontalStrut(200));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbLoaiPhong = new JLabel("Loại Phòng:"));
					b3.add(Box.createHorizontalStrut(23));
					b3.add(txtLoaiPhong = new JTextField(25));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbGhiChu = new JLabel("Ghi Chú:"));
					b3.add(Box.createHorizontalStrut(46));
					b3.add(txtGhiChu = new JTextField(25));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(20));
				b2.add(b3 = Box.createHorizontalBox());
					b3.add(Box.createHorizontalStrut(20));
					b3.add(lbGiaPhong = new JLabel("Giá Phòng:"));
					b3.add(Box.createHorizontalStrut(30));
					b3.add(txtGiaPhong= new JTextField(25));
					b3.add(Box.createHorizontalStrut(20));
				b2.add(Box.createVerticalStrut(200));
				
		bc.add(b1 = Box.createVerticalBox());
				String[] cols = "Mã Phiếu;Họ Tên;CMND;Ngày Đến;Ngày Đi;Tình Trạng".split(";");
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
				// xét cứng cột
				table.getTableHeader().setReorderingAllowed(false);
			JScrollPane scroll = new JScrollPane(table);
			b1.add(scroll);
		//add(pnlEast = new JPanel(), BorderLayout.EAST);
		// pnlEast.setBackground(Color.black);

		add(pnlSouth = new JPanel(), BorderLayout.SOUTH);
		pnlSouth.setBackground(Color.LIGHT_GRAY);
		pnlSouth.setBorder(BorderFactory.createTitledBorder("Tùy chọn"));
		pnlSouth.setPreferredSize(new Dimension(0, 80));
		Box b11 = Box.createHorizontalBox();
		b11.add(btnBack = new JButton("Lùi Về Trang Trước", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\logout.png")));
		btnBack.setBackground(Color.red);
		b11.add(Box.createHorizontalStrut(300));
		 b11.add(lbNV = new JLabel("Nhân Viên: Vũ Hoàng Trang"));
		 b11.add(Box.createHorizontalStrut(20));
		 b11.add(txtDate = new JTextField());
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = new Date();
		 txtDate.setText(sdf.format(date1.getTime()));
		
		pnlSouth.add(b11);
	
		//xét label cùng 1 font
		List<JLabel> tmpLb = Arrays.asList(lbNV,lbTen,lbMa,lbCMND,lbNgayDatPhong,lbNgayTraPhong,lbSDT,lbgioiTinh, lbNgaySinh);
		List<JLabel> tmpLb2 = Arrays.asList(lbMaPhong,lbLoaiPhong,lbGhiChu,lbGiaPhong);
		setFontLabel(tmpLb);
		setFontLabel(tmpLb2);
		
		//xét TextField is false
		List<JTextField> tmpTx1 = Arrays.asList(txtMaPhong, txtLoaiPhong, txtGhiChu, txtGiaPhong,txtMaKhachHang,txtDate);
		setTextisFalse(tmpTx1,false);
		
		//Đăng ký sự kiện các button
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
//		ArrayList<Phong> dsPhong = qlp.timKiem(GUIThueTraPhong.maPhong, "Tìm Theo Mã Phòng");
//		p = dsPhong.get(thutu);
//		dsPhong.forEach(p->{
			txtMaPhong.setText(p.getId()+100+"");
			txtLoaiPhong.setText(p.getLoaiPhong());
			txtGhiChu.setText(p.getGhiChu());
			DecimalFormat df = new DecimalFormat("###,000");	
			txtGiaPhong.setText(df.format(p.getGiaPhong())+" VNĐ");
//		});
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if(src.equals(btnTra)) {
			int row = table.getSelectedRow();
			if(row==-1) {
				JOptionPane.showMessageDialog(this,"Cần Chọn Phiếu Trả");
				return;
			}
			if(table.getValueAt(row, 5).toString().equals("Đã Đặt")) {
				JOptionPane.showMessageDialog(this, "Phòng Chưa Ở - Không Thể Trả");
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
//			System.out.println("ĐÃ BACK");
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
				tinhTrangPhieu = "Đến Hạn Check Out";
			}else if(pdp.getTinhTrangPhieu() == 2) {
				tinhTrangPhieu = "Đang Check In";
			}else if(pdp.getTinhTrangPhieu() == 3) {
				tinhTrangPhieu = "Đang Sử Dụng";
			}else tinhTrangPhieu = "Đã Đặt";//1
			String[] rowData = {pdp.getId()+"",pdp.getKhachHang().getHoTen(),pdp.getKhachHang().getcMND(),pdp.getNgayDen()+"",pdp.getNgayDi()+"",tinhTrangPhieu};
			tableModel.addRow(rowData);
		};
		table.setModel(tableModel);
	}
	
	/*
	 * Thực Hiện Việc Lưu thông tin phòng khách đặt -> Tình trạng phòng = 1, tình trạng Phiếu = 0
	 * Cách thực hiện:
	 * + Khi khách chưa có trong dtb: Lưu dtb khách hàng + phòng + tạo phiếu đặt phòng + hoá đơn
	 * + Khi khách đã có trong dtb: Lưu dtb phòng + tạo phiếu đặt phòng + hoá đơn 
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
			JOptionPane.showMessageDialog(this,"Thêm Thành Công");
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
			JOptionPane.showMessageDialog(this, "Cần Nhập Thông Tin Khách Hàng!");
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
			JOptionPane.showMessageDialog(this, "Thiếu Ngày Đến, Ngày Đi");
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date()); 
		Date dateNow = new Date();
	
		String ngayDen = sdf.format(cldNgayDen.getDate());
		String ngayDi = sdf.format(cldNgayDi.getDate());
		String ngayHienTai = sdf.format(dateNow);
				
		if(ngayDen.compareTo(ngayDi)>= 0) {
			JOptionPane.showMessageDialog(this, "Ngày Đến <= Ngày Đi");
			return;
		}
//		if(ngayDen.compareTo(ngayHienTai)<0) {
//			JOptionPane.showMessageDialog(this, "Ngày Đến Phải Lớn Hơn Ngày Hiện Tại: "+ngayHienTai);
//			return;
//		}
		//1 là mã nhân viên, để tạm

		String dateDi = sdf.format(cldNgayDi.getDate());
		String dateDen = sdf.format(cldNgayDen.getDate());
		System.out.println(txtMaPhong.getText());
		System.out.println(qlttr.checkCreatePhieuDatPhong(dateDi,dateDen, txtMaPhong.getText()) );

		if(qlttr.checkCreatePhieuDatPhong(dateDi,dateDen, txtMaPhong.getText()).size()==0) {
			//1 là mã nhân viên tạm
			if(qlttr.createPDP(txtMaPhong.getText().trim(), maKH, "1", dateDen, dateDi)) {
				JOptionPane.showMessageDialog(this,"Đặt Phòng Thành Công");
				//1 - 0 admin - user
				GUIMenu gui = new GUIMenu(1);
				gui.setVisible(false);
				gui.showPanel(new GUIThueTraPhong(gui));
				//reloadData(gui);
				parent.showPanel(new GUIThueTraPhong(parent));
				updateTableData();
				String maPDP = table.getValueAt(table.getRowCount()-1, 0).toString();
				//tạo hoá đơn
				QuanLyHoaDon qlhd = new QuanLyHoaDon();
				QuanLyPhong qlp = new QuanLyPhong();
				Phong p = qlp.tim1Phong(Integer.parseInt(txtMaPhong.getText())-100);
				long ngay = cldNgayDi.getDate().getTime() - cldNgayDen.getDate().getTime();
				ngay = ngay/(1000*60*60*24);
				double tongTien = ngay*p.getGiaPhong();
				//mã nhân viên để tạm là 1
				qlhd.createHD(maPDP, 1+"", 0, dateDi, 0);
				Cancel();
			}else JOptionPane.showMessageDialog(this,"Lỗi");
		}else {
			JOptionPane.showMessageDialog(this, "Phòng Đã Được Đặt Trong Khoảng Thời Gian Này!");
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
