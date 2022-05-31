package uiLogin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.toedter.calendar.JDateChooser;

import Client.ClientChatter;
import Client.ManagerChatter;
import entities.KhachHang;
import services.QuanLyNhanVien;
import services.QuanLyPhong;
import uiQuanLy.GUIQuanLyDichVu;
import uiQuanLy.GUIQuanLyKhachHang;
import uiQuanLy.GUIQuanLyNhanVien;
import uiQuanLy.GUIThongKe;
import uiQuanLy.GUIThongKeDoanhThu;
import uiQuanLy.GUIThongKePDP;
import uiQuanLyPhong.GUIQLPhong;
import uiThueTraPhong.GUIThueTraPhong;

public class GUIMenu extends JFrame implements ActionListener {

	// Thanh Menu
	JToolBar toolbar1;
	JMenuBar menuBar;
	JMenu menuTuyChon, menuNhanVien, menuPhong,menuKhachHang, menuDichVu, menuChucNang, menuThongTin;
	// Menu item của menuTuyChon
	JMenuItem meimDangXuat, meimThoat;
	// Menu item của menuQuanLy
	JMenuItem meimCapNhatKhachHang, meimTimKiemKhachHang; //Khách Hàng
	JMenuItem meimCapNhatNhanVien, meimTimKiemNhanVien; //Nhân Viên
	JMenuItem meimCapNhatPhong, meimDatTraPhong,meimTimKiemPhong,meimChuyenPhong;//Phòng
	JMenuItem meimCapNhatDichVu , meimTimKiemDichVu; //Dịch Vụ
	
	// Menu item của menuChucNang
	JMenuItem meimBaoCao, meimThongKe,meimBaoCao2;
	
	
	JLabel lbNhanVien, lbNgayHienTai;
	JTextField txtTenNhanVien;
	JTextField txtMaNhanVien;
	JPanel pnMain = new JPanel();
	private JPanel childPanel;

	// Quản Lý Phòng
	QuanLyPhong ql = new QuanLyPhong();
	// Quản Lý Nhân Viên
	QuanLyNhanVien qlNV = new QuanLyNhanVien();
	public static String maPhong;
	public static int ktrLogin;

	public GUIMenu(int check) {
		ktrLogin = check;
		setType(Type.POPUP);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		setSize(width,height);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\This PC\\Desktop\\image\\logo.png"));
		setTitle("Quản Lý Khách Sạn");
		Font font1 = new Font("SansSerif", Font.BOLD, 20);
		Box bc, b1, b2, b3, b4;
		bc = Box.createVerticalBox();
		add(bc);
		//HEADER
		bc.add(b1 = Box.createHorizontalBox());
		menuBar = new JMenuBar();
		menuBar.setToolTipText("");
		menuBar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		menuBar.add(Box.createRigidArea(new Dimension(0, 80)));
		b1.add(menuBar);

		menuTuyChon = new JMenu("Tuỳ Chọn");
		menuTuyChon.setIcon(new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\menu.png"));
		menuNhanVien = new JMenu("Nhân Viên");
		menuNhanVien.setIcon(new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\employees.png"));
		menuKhachHang = new JMenu("Khách Hàng");
		menuKhachHang.setIcon(new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\manager.png"));
		menuDichVu = new JMenu("Dịch Vụ");
		menuDichVu.setIcon(new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\services.png"));
		menuPhong = new JMenu("Phòng");
		menuPhong.setIcon(new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\bed.png"));
		menuChucNang = new JMenu("Thống Kê");
		menuChucNang.setIcon(new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\function.png"));
		menuThongTin = new JMenu("Thông Tin");
		menuThongTin.setIcon(new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\information.png"));
		
		menuBar.add(menuTuyChon);
		menuBar.add(menuNhanVien);
		menuBar.add(menuKhachHang);
		menuBar.add(menuDichVu);
		menuBar.add(menuPhong);
		menuBar.add(menuChucNang);
		menuBar.add(menuThongTin);
		menuBar.add(Box.createHorizontalStrut(width-1000));
		menuBar.add(lbNhanVien = new JLabel("Mai Sương"));
		menuBar.add(Box.createHorizontalStrut(30));
		menuBar.add(lbNgayHienTai = new JLabel());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		lbNgayHienTai.setText(sdf.format(date.getTime()));
		meimDangXuat = new JMenuItem("Đăng Xuất", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\logout.png"));
		menuTuyChon.add(meimDangXuat);
		JMenuItem meimThoat = new JMenuItem("Thoát", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\exit.png"));
		menuTuyChon.add(meimThoat);
		
		
		//Nút con Nhân Viên
		meimCapNhatNhanVien = new JMenuItem("Cập Nhật", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\services.png"));
		meimTimKiemNhanVien = new JMenuItem("Tìm Kiếm", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\find.png"));
		menuNhanVien.add(meimCapNhatNhanVien);
		menuNhanVien.add(meimTimKiemNhanVien);
		//Nút con Khách Hàng
		meimCapNhatKhachHang = new JMenuItem("Cập Nhật", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\employees.png"));
		meimTimKiemKhachHang = new JMenuItem("Tìm Kiếm", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\find.png"));
		menuKhachHang.add(meimCapNhatKhachHang);
		menuKhachHang.add(meimTimKiemKhachHang);
		//Nút con Dịch Vụ
		meimCapNhatDichVu = new JMenuItem("Cập Nhật", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\employees.png"));
		meimTimKiemDichVu = new JMenuItem("Tìm Kiếm", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\find.png"));
		JMenuItem meimDichVu = new JMenuItem("Nhắn tin với quản lý",new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\send.png"));
		meimDichVu.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e){
		        if(e.getSource() == meimDichVu ){
		            JOptionPane.showMessageDialog(null, "Bạn vừa chọn 'Nhắn tin với quản lý'", "Thông báo", JOptionPane.CLOSED_OPTION);
		            ManagerChatter mChatter = new ManagerChatter();
					mChatter.setVisible(true);
					mChatter.setLocation(720, 200);
					ClientChatter cChatter = new ClientChatter();
					cChatter.setVisible(true);
					cChatter.setLocation(50, 200);
		        }
		    }
		});
		menuDichVu.add(meimCapNhatDichVu);
		menuDichVu.add(meimTimKiemDichVu);
		menuDichVu.add(meimDichVu);
		//Nút Con Phòng
		meimDatTraPhong = new JMenuItem("Đặt - Trả Phòng", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\booking.png"));
		meimCapNhatPhong = new JMenuItem("Cập Nhật", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\bed.png"));
		meimTimKiemPhong = new JMenuItem("Tìm Kiếm", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\find.png"));
		meimChuyenPhong = new JMenuItem("Chuyển Phòng", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\booking.png"));
		menuPhong.add(meimDatTraPhong);
		menuPhong.add(meimCapNhatPhong);
		menuPhong.add(meimTimKiemPhong);
		menuPhong.add(meimChuyenPhong);
		//Nút Con menuChuNang

		meimThongKe = new JMenuItem("Doanh Thu Theo Tháng/Năm", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\report.png"));
		meimBaoCao = new JMenuItem("Số Lượng Đơn Đặt Phòng", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\report.png"));
		meimBaoCao2 = new JMenuItem("Doanh Thu Theo Ngày/Tháng", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\report.png"));
		menuChucNang.add(meimThongKe);
		menuChucNang.add(meimBaoCao);
		menuChucNang.add(meimBaoCao2);
		menuBar.add(Box.createHorizontalStrut(width-300));
		
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(pnMain, BorderLayout.CENTER);//thay đổi pnMain thành GUI khác
				pnMain.setBackground(new Color(153, 204, 255));
				//pnMain.setBounds(32, 32, 4096, 4096);
				pnMain.setPreferredSize(new Dimension(width-520, height-320));
				pnMain.setLayout(new BorderLayout(0,0));
				pnMain.setSize(width-520,height-320);

			
		//bc.add(Box.createVerticalStrut(50));
		// Đăng Ký Sự Kiện
		meimDangXuat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GUILogin login = new GUILogin();
				login.setVisible(true);
				DangXuat();
			}
			
		});
		meimCapNhatKhachHang.addActionListener(this);
		meimCapNhatNhanVien.addActionListener(this);
		meimDatTraPhong.addActionListener(this);
		meimCapNhatPhong.addActionListener(this);
		meimThongKe.addActionListener(this);
		meimTimKiemDichVu.addActionListener(this);
		meimCapNhatDichVu.addActionListener(this);
		meimTimKiemNhanVien.addActionListener(this);
		meimTimKiemKhachHang.addActionListener(this);
		meimTimKiemPhong.addActionListener(this);
		meimChuyenPhong.addActionListener(this);
		meimBaoCao.addActionListener(this);
		meimBaoCao2.addActionListener(this);
		setVisible(true);

	}

	public void showPanel(JPanel panel) {
		childPanel = panel;
		pnMain.removeAll();
		pnMain.add(childPanel);
		pnMain.validate();
	}
	public void DangXuat() {
		this.setVisible(false);
	}
	public static void main(String[] args) {
		try {
	
			new GUIMenu(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setFontLabel(List<JLabel> listLabel) {
		listLabel.forEach(x -> {
			x.setFont(new Font("Times New Roman", Font.BOLD, 15));
		});

	}
	private void thoat() {
		if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn thoát?", "Cảnh Báo",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if (src.equals(meimCapNhatNhanVien)) {
			showPanel(new GUIQuanLyNhanVien(this));
		} else if (src.equals(meimDatTraPhong)) {
			showPanel(new GUIThueTraPhong(this));
		}	else if (src.equals(meimCapNhatPhong)) {
			showPanel(new GUIQLPhong(this));
		} else if(src.equals(meimThoat)) {
			thoat();
		}else if(src.equals(meimThongKe)) {
			new GUIThongKe();
		}else if(src.equals(meimCapNhatDichVu)) {
			showPanel(new GUIQuanLyDichVu(this));
		}else if(src.equals(meimTimKiemDichVu)) {
			actionTimDichVu();
		}else if(src.equals(meimTimKiemNhanVien)) {
			actionTimNhanVien();
		}else if(src.equals(meimCapNhatKhachHang)) {
			showPanel(new GUIQuanLyKhachHang(this));
		}
		else if(src.equals(meimTimKiemKhachHang)) {
			actionTimKhachHang();
		}
		else if(src.equals(meimTimKiemPhong)) {
			actionTimPhong();
		}
		else if(src.equals(meimBaoCao)) {
			new GUIThongKePDP();
		}else if(src.equals(meimBaoCao2)) {
			new GUIThongKeDoanhThu();
		}
	}

	private void actionTimPhong() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel(new GridLayout(0,1));
		panel.setPreferredSize(new Dimension(400,100));
		
		JLabel lbMa, lbLoai, lbGhiChu, lbGia, lbNgayDen, lbNgayDi;
		JTextField txtMa, txtGhiChu, txtGia;
		JComboBox<String> cmbLoai;
		JDateChooser cldNgayDen, cldNgayDi;
		JPanel pNor;
		pNor = new JPanel();
		Box bc,b1;
		bc = Box.createVerticalBox();
		
		panel.add(bc);
		bc.add(pNor, BorderLayout.NORTH);
	//	bc.setPreferredSize(new Dimension(width-width*27/100,height-height*29/100));
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(lbMa = new JLabel("Mã Phòng:"));
			b1.add(Box.createHorizontalStrut(5));
			b1.add(txtMa = new JTextField());
			
			
			b1.add(Box.createHorizontalStrut(20));
			b1.add(lbGhiChu = new JLabel("Ghi Chú:"));
			b1.add(Box.createHorizontalStrut(15));
			b1.add(txtGhiChu = new JTextField());
		bc.add(Box.createVerticalStrut(10));
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(lbLoai = new JLabel("Loại Phòng:"));
			b1.add(Box.createHorizontalStrut(30));
			cmbLoai=new JComboBox<String>();
			cmbLoai.addItem("");
			cmbLoai.addItem("Giường Đơn");
			cmbLoai.addItem("Giường Đôi");
			cmbLoai.addItem("Thường");
			cmbLoai.addItem("VIP");
			cmbLoai.setPreferredSize(new Dimension(150, 20));
			cmbLoai.setMaximumSize(new Dimension(150, 20));
			b1.add(cmbLoai);
			b1.add(Box.createHorizontalStrut(30));
			b1.add(lbGia = new JLabel("Giá:"));
			b1.add(Box.createHorizontalStrut(20));
			b1.add(txtGia = new JTextField());
		bc.add(Box.createVerticalStrut(10));
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(new JLabel("Tìm Phòng Trống Theo Thời Gian: "));
		bc.add(Box.createVerticalStrut(10));
		bc.add(b1 = Box.createHorizontalBox());
			String date = "dd-MM-yyyy";
			b1.add(lbNgayDen = new JLabel("Ngày Đến:"));
			b1.add(Box.createHorizontalStrut(10));
			b1.add(cldNgayDen = new JDateChooser());
			cldNgayDen.setDateFormatString(date);
			b1.add(Box.createHorizontalStrut(10));
			b1.add(lbNgayDi = new JLabel("Ngày Đi:"));
			b1.add(Box.createHorizontalStrut(10));
			b1.add(cldNgayDi = new JDateChooser());
			cldNgayDi.setDateFormatString(date);
			
        int result = JOptionPane.showConfirmDialog(null, panel, "Tìm Kiếm Phòng",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        String maPhong="", ghiChu="" , gia="", loai="", ngayDen = "", ngayDi ="";
        if(txtMa.getText().trim()!= null) {
        	maPhong = txtMa.getText().trim();
        }
        if(txtGhiChu.getText().trim()!=null) {
        	ghiChu = txtGhiChu.getText().trim();
        }

        //timKiem(String maKH, String hoTen, String CMND, String ngaySinh, String gioiTinh, String sDT) {
        if(txtGia.getText().trim()!=null) {
        	gia = txtGia.getText().trim();
        }
        if(!cmbLoai.getSelectedItem().toString().equals("")) {
        	loai = cmbLoai.getSelectedItem().toString();
        }
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(cldNgayDen.getDate()!=null) {
			ngayDen = sdf.format(cldNgayDen.getDate());
        }
        if(cldNgayDi.getDate()!=null) {
    		ngayDi = sdf.format(cldNgayDi.getDate());
        } 
		//System.out.println("367: "+ngayDen + " " + ngayDi);
		if(result == JOptionPane.OK_OPTION) {
			GUIQLPhong gui = new GUIQLPhong(this);
			showPanel(gui);
			gui.actionTim(maPhong,ghiChu,gia,loai,ngayDen, ngayDi);	
		}
	}

	private void actionTimKhachHang() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel(new GridLayout(0,1));
		panel.setPreferredSize(new Dimension(400,100));
		
		JLabel lbMa, lbTen, lbGioiTinh, lbCMND, lbSDT, lbBack, lbNgaySinh;
		JTextField txtMa, txtTen, txtCMND, txtSDT;
		JButton btnThem, btnXoaRong, btnXoa, btnSua, btnLuu, btnBack, btnThoat,btnHuy;
		JComboBox<String> cmbDichVu, cmbGioiTinh;
		JDateChooser jdcNgaySinh;
		JPanel pNor;
		pNor = new JPanel();
		Box bc,b1;
		bc = Box.createVerticalBox();
		
		panel.add(bc);
		bc.add(pNor, BorderLayout.NORTH);
	//	bc.setPreferredSize(new Dimension(width-width*27/100,height-height*29/100));
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(lbMa = new JLabel("Mã Khách Hàng:"));
			b1.add(Box.createHorizontalStrut(15));
			b1.add(txtMa = new JTextField());
			txtMa.setPreferredSize(new Dimension(50, 20));
			txtMa.setMaximumSize(new Dimension(30, 20));
			b1.add(lbTen = new JLabel("Tên Khách Hàng:"));
			b1.add(Box.createHorizontalStrut(40));
			b1.add(txtTen = new JTextField());
		bc.add(Box.createVerticalStrut(10));
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(lbGioiTinh = new JLabel("Giới Tính:"));
			b1.add(Box.createHorizontalStrut(15));
			cmbGioiTinh=new JComboBox<String>();
			cmbGioiTinh.addItem("");
			cmbGioiTinh.addItem("Nam");
			cmbGioiTinh.addItem("Nữ");
			cmbGioiTinh.setPreferredSize(new Dimension(80, 20));
			cmbGioiTinh.setMaximumSize(new Dimension(50, 20));
			b1.add(cmbGioiTinh);
			b1.add(Box.createHorizontalStrut(15));
			b1.add(lbCMND = new JLabel("CMND:"));
			b1.add(Box.createHorizontalStrut(20));
			b1.add(txtCMND = new JTextField());
		bc.add(Box.createVerticalStrut(10));
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(lbSDT = new JLabel("Số Điện Thoại:"));
			b1.add(Box.createHorizontalStrut(5));
			b1.add(txtSDT = new JTextField());
			txtSDT.setPreferredSize(new Dimension(80, 20));
			txtSDT.setMaximumSize(new Dimension(50, 20));
			b1.add(Box.createHorizontalStrut(10));
			b1.add(lbNgaySinh = new JLabel("Ngày Sinh:"));
			b1.add(Box.createHorizontalStrut(20));
			String date = "yyyy-MM-dd";
			b1.add(jdcNgaySinh = new JDateChooser());
			jdcNgaySinh.setDateFormatString(date);
			jdcNgaySinh.setPreferredSize(new Dimension(30, 20));
        int result = JOptionPane.showConfirmDialog(null, panel, "Tìm Kiếm Khách Hàng",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        String maKH="", hoTen="" , cmnd="" , gioiTinh="", ngaySinh="", sdt="";
        if(txtMa.getText().trim()!= null) {
        	maKH = txtMa.getText().trim();
        }
        if(txtTen.getText().trim()!=null) {
        	hoTen = txtTen.getText().trim();
        }
        if(jdcNgaySinh.getDate()!=null) {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        ngaySinh = sdf.format(jdcNgaySinh.getDate());
        }
        //timKiem(String maKH, String hoTen, String CMND, String ngaySinh, String gioiTinh, String sDT) {
        if(txtCMND.getText().trim()!=null) {
        	cmnd = txtCMND.getText().trim();
        }
        if(!cmbGioiTinh.getSelectedItem().toString().equals("")) {
        	gioiTinh = cmbGioiTinh.getSelectedItem().toString();
        }
        
        if(txtSDT.getText().trim()!=null) {
        	sdt = txtSDT.getText().trim();
        }
		if(result == JOptionPane.OK_OPTION) {
			GUIQuanLyKhachHang gui = new GUIQuanLyKhachHang(this);
			showPanel(gui);
			gui.actionTim(maKH,hoTen,cmnd,ngaySinh,gioiTinh,sdt);	
		}
	}

	private void actionTimDichVu() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel(new GridLayout(0,1));
		panel.setPreferredSize(new Dimension(400,100));
		JTextField txtMa, txtTen, txtDonVi, txtSoLuong, txtDonGia;
		JComboBox<String> cmbTheLoai;
		Box bc,b1;
		bc = Box.createVerticalBox();
		panel.add(bc);		
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(new JLabel("Mã Dịch Vụ:"));
			b1.add(Box.createHorizontalStrut(15));
			b1.add(txtMa = new JTextField());
			b1.add(Box.createHorizontalStrut(20));
			b1.add(new JLabel("Tên Dịch Vụ:"));
			b1.add(Box.createHorizontalStrut(20));
			b1.add(txtTen = new JTextField());
			//b1.add(Box.createHorizontalStrut(200));
		bc.add(Box.createVerticalStrut(10));
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(new JLabel("Loại:"));
			b1.add(Box.createHorizontalStrut(50));
			cmbTheLoai=new JComboBox<String>();
			cmbTheLoai.addItem("");
			cmbTheLoai.addItem("Đồ Uống");
			cmbTheLoai.addItem("Dịch Vụ");
			cmbTheLoai.addItem("Khác");
			cmbTheLoai.setPreferredSize(new Dimension(100,22));
			b1.add(cmbTheLoai);
			b1.add(Box.createHorizontalStrut(20));
			b1.add(new JLabel("Đơn Vị:"));
			b1.add(Box.createHorizontalStrut(10));
			b1.add(txtDonVi = new JTextField());
			//b1.add(Box.createHorizontalStrut(200));
		bc.add(Box.createVerticalStrut(10));
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(new JLabel("Số Lượng:"));
			b1.add(Box.createHorizontalStrut(15));
			b1.add(txtSoLuong = new JTextField());
			b1.add(Box.createHorizontalStrut(20));
			b1.add(new JLabel("Đơn Giá:"));
			b1.add(Box.createHorizontalStrut(10));
			b1.add(txtDonGia = new JTextField());
			//b1.add(Box.createHorizontalStrut(200));
        int result = JOptionPane.showConfirmDialog(null, panel, "Tìm Kiếm Dịch Vụ",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		//System.out.println(combo.getSelectedItem()+" " +signIll2.getText() + " " +stt2.getText());
        String maDV="", tenDV="" , donVi="" , loai="", soLuongCo="", giaDV="";
        if(txtMa.getText().trim()!= null) {
        	maDV = txtMa.getText().trim();
        }
        if(txtTen.getText().trim()!=null) {
        	tenDV = txtTen.getText().trim();
        }
        if(!cmbTheLoai.getSelectedItem().toString().equals("")) {
        	loai = cmbTheLoai.getSelectedItem().toString();
        }
        if(txtDonVi.getText().trim()!=null) {
        	donVi = txtDonVi.getText().trim();
        }
        if(txtSoLuong.getText().trim()!=null) {
        	soLuongCo = txtSoLuong.getText().trim();
        }
        if(txtDonGia.getText().trim()!=null) {
        	giaDV = txtDonGia.getText().trim();
        }
		if(result == JOptionPane.OK_OPTION) {
			GUIQuanLyDichVu gui = new GUIQuanLyDichVu(this);
			showPanel(gui);
			gui.actionTim(maDV,tenDV,donVi,loai, soLuongCo,giaDV);	
		}
	}
	private void actionTimNhanVien() {
			// TODO Auto-generated method stub
			//String[] possibilities = {"Mã Nhân Viên","Tên Nhân Viên", "CMND"};
			//JComboBox<String> combo = new JComboBox<>(possibilities);
			JLabel lblMaNhanVien, lbCMND,lbNgaySinh, lblTenNhanVien, lblSoDienThoai,lbDiaChi,lblgioitinh;
			JTextField txtCmnd,txtMaNhanVien1,txtSoDienThoai,txtDiaChi;
			JDateChooser dcNgaySinh;
			JComboBox<String> cmbGioitinh;
			JTextField txtTim = new JTextField();
			JPanel panel = new JPanel(new GridLayout(0,1));
			Box bc,b1;
			bc = Box.createVerticalBox();
			panel.add(bc);
			bc.add(b1 = Box.createHorizontalBox());
				//b1.add(Box.createHorizontalStrut(20));
				b1.add(lblMaNhanVien = new JLabel("Mã Nhân Viên:"));
				b1.add(Box.createHorizontalStrut(24));
				b1.add(txtMaNhanVien1 = new JTextField());
				txtMaNhanVien1.setPreferredSize(new Dimension(80, 20));
				txtMaNhanVien1.setMaximumSize(new Dimension(50, 20));
				b1.add(Box.createHorizontalStrut(28));
				b1.add(lbCMND = new JLabel("CMND:"));
				b1.add(Box.createHorizontalStrut(10));
				b1.add(txtCmnd = new JTextField());
				txtCmnd.setPreferredSize(new Dimension(80, 20));
				txtCmnd.setMaximumSize(new Dimension(50, 20));
				b1.add(Box.createHorizontalStrut(30));
				b1.add(lbNgaySinh = new JLabel("Ngày Sinh:"));
				b1.add(Box.createHorizontalStrut(20));
				String date = "yyyy-MM-dd";
				b1.add(dcNgaySinh = new JDateChooser());
				dcNgaySinh.setDateFormatString(date);
				dcNgaySinh.setPreferredSize(new Dimension(30, 20));
				//b1.add(Box.createHorizontalStrut(width-width*60/100));
			bc.add(Box.createVerticalStrut(10));
			bc.add(b1 = Box.createHorizontalBox());
				//b1.add(Box.createHorizontalStrut(20));
				b1.add(lblTenNhanVien = new JLabel("Tên Nhân Viên:"));
				b1.add(Box.createHorizontalStrut(20));
				b1.add(txtTenNhanVien = new JTextField());
				txtTenNhanVien.setPreferredSize(new Dimension(100, 20));
				txtTenNhanVien.setMaximumSize(new Dimension(100, 20));
				b1.add(Box.createHorizontalStrut(15));
				b1.add(lblSoDienThoai = new JLabel("Số điện thoại:"));
				b1.add(Box.createHorizontalStrut(10));
				b1.add(txtSoDienThoai = new JTextField());
				txtSoDienThoai.setPreferredSize(new Dimension(80, 20));
				txtSoDienThoai.setMaximumSize(new Dimension(50, 20));
				b1.add(Box.createHorizontalStrut(15));
				b1.add(lblgioitinh = new JLabel("Giới tính:"));
				b1.add(Box.createHorizontalStrut(60));
				cmbGioitinh = new JComboBox<>();
				cmbGioitinh.addItem("");
				cmbGioitinh.addItem("Nam");
				cmbGioitinh.addItem("Nữ");
				cmbGioitinh.setMaximumSize(new Dimension(80, 20));
				cmbGioitinh.setPreferredSize(new Dimension(200,22));
				b1.add(cmbGioitinh);
				bc.add(Box.createVerticalStrut(60));
				bc.add(b1 = Box.createHorizontalBox());
				//b1.add(Box.createHorizontalStrut(20));
				b1.add(lbDiaChi = new JLabel("Địa Chỉ:"));
				b1.add(Box.createHorizontalStrut(60));
				b1.add(txtDiaChi = new JTextField());
				
	        panel.setPreferredSize(new Dimension(600,100));
	        int result = JOptionPane.showConfirmDialog(null, panel, "Tìm Kiếm Nhân Viên!",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	        String maNV="",cmnd="",ngaySinh="",tenNV="",sDT="",gioiTinh="",diaChi="";
	        if(txtMaNhanVien1.getText().trim()!= null) {
	        	maNV = txtMaNhanVien1.getText().trim();
	        }
	        if(txtCmnd.getText().trim()!=null) {
	        	cmnd = txtCmnd.getText().trim();
	        }
	        if(dcNgaySinh.getDate()!=null) {
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        ngaySinh = sdf.format(dcNgaySinh.getDate());
	        }
	        if(txtTenNhanVien.getText().trim()!=null) {
	        	tenNV = txtTenNhanVien.getText().trim();
	        }
	        if(txtSoDienThoai.getText().trim()!=null) {
	        	sDT = txtSoDienThoai.getText().trim();
	        }
	        if(!cmbGioitinh.getSelectedItem().toString().equals("")) {
	        	gioiTinh = cmbGioitinh.getSelectedItem().toString();
	        }
	        if(txtDiaChi.getText().trim()!=null) {
	        	diaChi = txtDiaChi.getText().trim();
	        }
	        
//			//System.out.println(combo.getSelectedItem()+" " +signIll2.getText() + " " +stt2.getText());
			if(result == JOptionPane.OK_OPTION) {
				GUIQuanLyNhanVien gui = new GUIQuanLyNhanVien(this);
				showPanel(gui);
				gui.actionTim(maNV,cmnd,ngaySinh, tenNV, sDT, gioiTinh, diaChi);	
			}
	}
	

}
