package uiQuanLy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.sql.Array;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.toedter.calendar.JDateChooser;

import database.Database;
import entities.DichVu;
import entities.KhachHang;
import services.QuanLyDichVu;
import services.QuanLyKhachHang;
import uiLogin.GUIMenu;

public class GUIQuanLyKhachHang extends JPanel implements ActionListener, MouseListener{
	JLabel lbMa, lbTen, lbGioiTinh, lbCMND, lbSDT, lbBack, lbNgaySinh;
	JTextField txtMa, txtTen, txtCMND, txtSDT;
	JButton btnThem, btnXoaRong, btnXoa, btnSua, btnLuu, btnBack, btnThoat,btnHuy;
	JComboBox<String> cmbDichVu, cmbGioiTinh;
	JDateChooser jdcNgaySinh;
	public static DefaultTableModel tableModel;
	JTable table;
	JPanel pNor;
	JLabel lblTitle;
	private Frame child;
	QuanLyKhachHang qlkh = new  QuanLyKhachHang();
	public GUIQuanLyKhachHang(Frame parent) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		child = parent;
		pNor = new JPanel();
		pNor.add(lblTitle = new JLabel("Quản Lý Khách Hàng"));
		lblTitle.setFont(new Font("times new roman", Font.BOLD, 50));
		lblTitle.setForeground(new Color(0xFFAA00));
		Box bc,b1;
		bc = Box.createVerticalBox();
		add(bc);
		bc.add(pNor, BorderLayout.NORTH);
		bc.setPreferredSize(new Dimension(width-width*27/100,height-height*29/100));
		bc.add(Box.createVerticalStrut(50));
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(Box.createHorizontalStrut(150));
			
			b1.add(lbMa = new JLabel("Mã Khách Hàng:"));
			b1.add(Box.createHorizontalStrut(15));
			b1.add(txtMa = new JTextField());
			txtMa.setPreferredSize(new Dimension(30, 20));
			txtMa.setMinimumSize(new Dimension(30, 20));
			b1.add(Box.createHorizontalStrut(20));
			
			b1.add(lbTen = new JLabel("Tên Khách Hàng:"));
			b1.add(Box.createHorizontalStrut(20));
			b1.add(txtTen = new JTextField());
			txtTen.setPreferredSize(new Dimension(30, 20));
			txtTen.setMinimumSize(new Dimension(100, 20));
			b1.add(Box.createHorizontalStrut(width- width*60/100));
			bc.add(Box.createVerticalStrut(15));
			bc.add(b1 = Box.createHorizontalBox());
			b1.add(Box.createHorizontalStrut(150));
			
			b1.add(lbGioiTinh = new JLabel("Giới Tính:"));
			b1.add(Box.createHorizontalStrut(60));
			cmbGioiTinh=new JComboBox<String>();
			cmbGioiTinh.addItem("Nam");
			cmbGioiTinh.addItem("Nữ");
			cmbGioiTinh.setPreferredSize(new Dimension(80, 20));
			cmbGioiTinh.setMaximumSize(new Dimension(80, 20));
			b1.add(cmbGioiTinh);
			b1.add(Box.createHorizontalStrut(35));
			
			b1.add(lbCMND = new JLabel("CMND:"));
			b1.add(Box.createHorizontalStrut(20));
			b1.add(txtCMND = new JTextField());
			txtCMND.setPreferredSize(new Dimension(30, 20));
			txtCMND.setMinimumSize(new Dimension(100, 20));
			b1.add(Box.createHorizontalStrut(width - width*30/100));
			bc.add(Box.createVerticalStrut(15));
			bc.add(b1 = Box.createHorizontalBox());
			b1.add(Box.createHorizontalStrut(150));
			
			b1.add(lbSDT = new JLabel("Số Điện Thoại:"));
			b1.add(Box.createHorizontalStrut(30));
			b1.add(txtSDT = new JTextField());
			txtSDT.setPreferredSize(new Dimension(30, 20));
			txtSDT.setMinimumSize(new Dimension(100, 20));
			b1.add(Box.createHorizontalStrut(30));
			
			b1.add(lbNgaySinh = new JLabel("Ngày Sinh:"));
			b1.add(Box.createHorizontalStrut(30));
			String date = "yyyy-MM-dd";
			b1.add(jdcNgaySinh = new JDateChooser());
			jdcNgaySinh.setDateFormatString(date);
			jdcNgaySinh.setPreferredSize(new Dimension(30, 20));
			jdcNgaySinh.setMinimumSize(new Dimension(100, 20));


			b1.add(btnXoaRong=new JButton("Làm Mới",new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\reload.png")));
			b1.add(Box.createHorizontalStrut(width - width*80/100));
			bc.add(Box.createVerticalStrut(15));
			bc.add(b1 = Box.createHorizontalBox());
			b1.setBorder(BorderFactory.createTitledBorder("Chọn Tác Vụ"));
			b1.add(Box.createHorizontalStrut(15));
			b1.add(Box.createHorizontalStrut(10));
			b1.add(btnThem= new JButton("Thêm",new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\add.png")));
			b1.add(Box.createHorizontalStrut(5));
			b1.add(btnXoa=new JButton("Xoá",new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\remove.png")));
			b1.add(Box.createHorizontalStrut(5));
			b1.add(btnSua=new JButton("Chỉnh Sửa",new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\edit.png")));
			b1.add(Box.createHorizontalStrut(5));
			b1.add(btnHuy=new JButton("Huỷ",new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\cancel.png")));
			b1.add(Box.createHorizontalStrut(5));
			b1.add(btnThoat=new JButton("Thoát",new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\exit.png")));
			b1.add(Box.createHorizontalStrut(5));	

		lbMa.setFont(new Font("Times New Roman",Font.BOLD, 15));
		lbTen.setFont(new Font("Times New Roman",Font.BOLD, 15));
		lbNgaySinh.setFont(new Font("Times New Roman",Font.BOLD, 15));
		lbCMND.setFont(new Font("Times New Roman",Font.BOLD, 15));
		lbSDT.setFont(new Font("Times New Roman",Font.BOLD, 15));
		lbGioiTinh.setFont(new Font("Times New Roman",Font.BOLD, 15));
		
		JScrollPane scroll;
		bc.add(Box.createVerticalStrut(20));
		int col[]= {5,20,20,15,10,20};
		//setColumnWidth(col);
		String[] tieuDe = "Mã Khách Hàng;Tên Khách Hàng;CMND;Số Điện Thoại;Giới Tính;Ngày Sinh".split(";");
		bc.add(b1 = Box.createHorizontalBox());
			tableModel = new DefaultTableModel(tieuDe,0);
			table = new JTable(tableModel) {
				public boolean isCellEditable(int row, int col) {
						return false;
				}
				public Component prepareRenderer( TableCellRenderer renderer, int row, int col ) {
					Component c = super.prepareRenderer(renderer, row, col);
					if ( row % 2 == 0 && !isCellSelected(row, col)) {
						c.setBackground( Color.decode("#F1F1F1") );
					}
					else 
						if(!isCellSelected(row, col)){
							c.setBackground( Color.decode("#D7F1FF") );
						}else {
							c.setBackground(Color.decode("#25C883"));
						}		        
					return c;
				}
			};
			b1.add(scroll = new JScrollPane(table));
			table.setAutoCreateRowSorter(true);
			JTableHeader header = table.getTableHeader();
			header.setBackground(Color.CYAN);
			header.setOpaque(false);
			//xét cứng cột
			table.getTableHeader().setReorderingAllowed(false);
			//xét độ dài của cột
			for(int i = 0; i < 6; i++) {
				table.getColumnModel().getColumn(i).setPreferredWidth(col[i]*4);
			}
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment( JLabel.CENTER );
			for(int i=0;i<6;i++) {
				table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
			}
		//	b1.add(Box.createHorizontalStrut(50));		
		bc.add(Box.createVerticalStrut(15));
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(Box.createHorizontalStrut(50));
			b1.add(btnBack = new JButton("Lùi Về Trang Trước", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\logout.png")));
			btnBack.setBackground(Color.red);
			b1.add(Box.createHorizontalStrut(20));
		bc.add(Box.createVerticalStrut(15));							
		Dimension dim = new Dimension(100, 16);
		lbMa.setMinimumSize(dim);
		//lbTen.setPreferredSize(dim);
		//lbCMND.setPreferredSize(dim);
		lbGioiTinh.setMinimumSize(dim);
		lbSDT.setMinimumSize(dim);
		//lbDonGia.setPreferredSize(dim);
		//lbCMND.setMinimumSize(dim);	
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//================Load Database==============
		Database.getInstance().connect();
		updateTableData();
		
		//================ĐĂNG KÝ SỰ KIỆN==========================
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		btnThem.addActionListener(this);
		btnXoaRong.addActionListener(this);
		btnXoa.addActionListener(this);
		btnSua.addActionListener(this);
		btnBack.addActionListener(this);
		btnThoat.addActionListener(this);
		btnHuy.addActionListener(this);
		table.addMouseListener(this);
//		cmbDichVu.addActionListener(this);
//		cmbGioiTinh.addActionListener(this);	
		//===================================================
		enableBtn();//Enable component
		setVisible(true);
		
		btnHuy.setVisible(false);
		}
	
//	public static void main(String[] args) {
//		//Database.getInstance();
//		//Database.connect();
//		try {
//            UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
//			new GUIQuanLyKhachHang();
//
//        }catch (Exception e) {
//        	e.printStackTrace();
//		}	
//	}
	
	String chucNangHienTai=null;
	
	public void tim(ArrayList<DichVu> listDv) {
		xoaTableData();
	}
	//================Xét việc đóng hay mở tác vụ với các Jtext======

	public void enableText(List<JTextField> listText, boolean b) {
		listText.forEach(x->{
			x.setEnabled(b);
			x.setDisabledTextColor(Color.BLACK);
		});
	}
	public void enableBtn() {
		btnThem.setEnabled(true);
		btnSua.setEnabled(false);
		btnXoa.setEnabled(false);
		//btnLuu.setEnabled(false);
		btnThoat.setEnabled(true);
		List<JTextField> listTxt = Arrays.asList(txtCMND,txtMa,txtSDT,txtTen);
		enableText(listTxt, false);
		cmbGioiTinh.setEnabled(false);
		jdcNgaySinh.setEnabled(false);
		txtMa.setEnabled(false);
		cmbGioiTinh.setSelectedItem(null);
	}	
	//=========================HÀNH ĐỘNG==============================
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if(src.equals(btnThem)) {
			actionThem();
		}else if(src.equals(btnThoat)) {
			actionThoat();
		}else if(src.equals(btnSua)) {
			if(btnSua.getText().equalsIgnoreCase("Lưu")) {
				actionLuu();
			}else {
				actionSua();
			}
		
		}else if(src.equals(btnHuy)) {
			actionHuy();
		}else if(src.equals(btnXoa)) {
			 actionXoa();
		}else if(src.equals(btnXoaRong)) {
			actionRong();
		}else if(src.equals(btnBack)) {
		
			new GUIMenu(1);
			child.setVisible(false);
		}
	}
	private void actionThem() {
		actionRong();
		btnHuy.setVisible(true);
		btnThem.setVisible(false);
		btnXoa.setVisible(false);
		btnSua.setText("Lưu"); btnSua.setIcon(new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\save.png"));
		btnSua.setEnabled(true);
		chucNangHienTai = "them";
		List<JTextField> listTxt = Arrays.asList(txtCMND,txtMa,txtSDT,txtTen);
		enableText(listTxt, true);
		cmbGioiTinh.setEnabled(true);
		jdcNgaySinh.setEnabled(true);
		txtMa.setEnabled(false);
	}
	public void actionRong() {
		cmbGioiTinh.setSelectedItem(null);
		//txtDonGia.setText("");
		txtCMND.setText("");
		txtMa.setText("");
		txtSDT.setText("");
		txtTen.setText("");
		btnSua.setEnabled(false);
		btnXoa.setEnabled(false);
		table.clearSelection();
		List<JTextField> listTxt = Arrays.asList(txtCMND,txtMa,txtSDT,txtTen);
		enableText(listTxt, false);
		cmbGioiTinh.setEnabled(false);
		jdcNgaySinh.setEnabled(false);
		xoaTableData();
		updateTableData();
	}
	private void actionXoa() {
		if (table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Chọn dòng cần xóa");
			return;
		}
		int row = table.getSelectedRow();
		QuanLyKhachHang qlkh = new QuanLyKhachHang();
		KhachHang kh;
		String ma = table.getValueAt(row, 0).toString()+1;
		if(JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn xóa Khách Hàng này?", "Cảnh Báo",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {								
			qlkh.curdKhachHang(ma, "", "", null, 0, "", "Delete");
				JOptionPane.showMessageDialog(this,"Đã Xoá");
				updateTableData();
		}	
		actionHuy();
		
	}
	private void actionHuy() {
		actionRong();
		chucNangHienTai = null;
		btnHuy.setVisible(false);
		btnThem.setVisible(true);
		btnSua.setVisible(true);
		btnSua.setText("Sửa"); btnSua.setIcon(new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\edit.png"));
		btnXoa.setVisible(true);
		enableBtn();
	}
	private void actionThoat() {
		if(JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn thoát?", "Cảnh Báo",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	private boolean checkData(String ma, String ten,String theLoai,String donVi,String donGia,String soLuong) {
		// TODO Auto-generated method stub		
//		donGia = donGia.replace(",", "");
//		System.out.println(donGia);
//		
//		if(cmbGioiTinh.getSelectedItem() == null) {
//			JOptionPane.showMessageDialog(this, "Chọn Thể Loại");
//			return false;
//		}
//		
//		if (ten.equals("")) {
//			JOptionPane.showMessageDialog(this, "Nhập Tên Khách Hàng");
//			txtTen.requestFocus();
//			return false ;
//		}
//		}else if(!ten.matches("[\\D]+")){
//			JOptionPane.showMessageDialog(this, "Tên Khách Hàng chỉ chứa ký tự, ký số, khoảng trắng");
//			txtTen.selectAll();
//			return false;
//		}
		
//		if (donGia.equals("")) {
//			JOptionPane.showMessageDialog(this, "Nhập Đơn Giá");
//			//txtDonGia.requestFocus();
//			return false;
//		}else if(!donGia.matches("[0-9]+")) {
//			JOptionPane.showMessageDialog(this, "Đơn Giá Phải Dạng Số");
//			txtDonGia.selectAll();
//			txtDonGia.requestFocus();
//			return false;
//		}else if(Integer.parseInt(donGia) < 0){
//			JOptionPane.showMessageDialog(this, "Đơn Giá Phải Có Giá Trị Dương");
//			txtDonGia.selectAll();
//			txtDonGia.requestFocus();
//			return false;
//		}
	
//		if (donVi.equals("")) {
//			JOptionPane.showMessageDialog(this, "Nhập Đơn Vị");
//			txtCMND.requestFocus();
//			return false;
//		}
//		else if(!donVi.matches("[\\D]+")){
//			JOptionPane.showMessageDialog(this, "Đơn Vị chỉ chứa ký tự, ký số, khoảng trắng");
//			txtCMND.selectAll();
//			return false;
//		}
//	
//		if (soLuong.equals("")) {
//			JOptionPane.showMessageDialog(this, "Nhập Số Lượng");
//			txtSDT.requestFocus();
//			return false;
//		}else if(!soLuong.matches("[0-9]+")) {
//			JOptionPane.showMessageDialog(this, "Số Lượng Phải Là Số");
//			txtSDT.selectAll();
//			txtSDT.requestFocus();
//			return false;
//		}else if(Integer.parseInt(soLuong) < 0){
//			JOptionPane.showMessageDialog(this, "Số Lượng Phải Có Giá Trị Dương");
//			txtSDT.selectAll();
//			txtSDT.requestFocus();
//			return false;
//		}
		return true;
	}
	private void actionLuu() {
		String ma="";
		String ten = txtTen.getText().trim();
		String gioiTinh = (String) cmbGioiTinh.getSelectedItem();
		String cmnd = txtCMND.getText().trim();
		String sdt = txtSDT.getText();
		
		String ngaySinh  = ((JTextField)jdcNgaySinh.getDateEditor().getUiComponent()).getText();
		int gt = -1;
		if(gioiTinh.equals("Nam")) {
			gt = 0;
		}else if(gioiTinh.equals("Nữ")) {
			gt = 1;
		}
		Date dateNS;
		try {
			ma = (String)(qlkh.dsKhachHang.size()+2+"");
			System.out.println("Da tim thay");
			dateNS = new SimpleDateFormat("yyyy-MM-dd").parse(ngaySinh);
			//áđâsđâsbnvnsdfnfnfnfwj
			if(chucNangHienTai.equals("them")) {
				String type = "Insert";
				//String ma, String ten,String theLoai,String donVi,String donGia,String soLuong
				if(checkData("",ten,gioiTinh,cmnd,String.valueOf(dateNS),sdt)) {	
					if(qlkh.curdKhachHang(ma, ten, cmnd, String.valueOf(dateNS), gt, sdt, type)) {
						//Object[] rowdata = {ma, ten,theLoai,donGia,soLuong,donVi,ghiChu};
						//tableModel.addRow(rowdata);
						updateTableData();
						JOptionPane.showMessageDialog(this,"Thêm Thành Công");
						enableBtn();
						actionRong();
						actionHuy();
						table.clearSelection();	
						btnHuy.setVisible(false);
						btnThem.setVisible(true);
						btnSua.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(this,"Dữ liệu đã có trong database - Trùng Mã");
					}
				}
			}else if(chucNangHienTai.equals("sua")) {
				List<JTextField> listTxt = Arrays.asList(txtCMND,txtMa,txtSDT,txtTen);
				enableText(listTxt, true);
				 int row = table.getSelectedRow();
				 String type = "Update";
					if(row >=0){
						//ma, ten,theLoai,donGia,soLuong,donVi,ghiChu
						//lấy cái mã để sửa thông tin băng đĩa dựa trên mã trên txt
						ma = table.getValueAt(row, 0).toString();
						if(checkData("",ten,gioiTinh,cmnd,String.valueOf(dateNS),sdt)) {
							if(qlkh.curdKhachHang(ma, ten, cmnd, String.valueOf(dateNS), gt, sdt, type)) {
								updateTableData();					
								actionHuy();
							}			
						}
					}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Loi o day");
		} 
		
	}
	private void actionSua() {
		btnHuy.setVisible(true);
		btnThem.setVisible(false);
		btnXoa.setVisible(false);
		chucNangHienTai = "sua";
		
		btnSua.setEnabled(true);	btnSua.setText("Lưu"); btnSua.setIcon(new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\save.png"));
		List<JTextField> listTxt = Arrays.asList(txtCMND,txtMa,txtSDT,txtTen);
		enableText(listTxt, true);

		cmbGioiTinh.setEnabled(true);
		jdcNgaySinh.setEnabled(true);
		txtMa.setEnabled(false);
	}
	//public ArrayList<KhachHang> timKiem(String maKH, String hoTen, String CMND, String ngaySinh, String gioiTinh, String sDT) {

	public void actionTim(String maKH, String hoTen, String CMND, String ngaySinh, String gioiTinh, String sDT) {
		try {
			qlkh = new QuanLyKhachHang();
			//String maDV, String tenDV, String donVi, String loai, String soLuongCo, String giaDV
			ArrayList<KhachHang> list = qlkh.timKiem(maKH, hoTen, CMND, ngaySinh, gioiTinh, sDT);
			if(list.size()==0) {
				JOptionPane.showMessageDialog(this, "Không Tìm Thấy");
			}
			else updateTableData1(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Hiển thị tìm đc lên
	private void updateTableData1(ArrayList<KhachHang> list) {
			xoaTableData();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(KhachHang kh : list) {
				String gioiTinh = "";
				if(kh.getGioiTinh() == 1) {
					gioiTinh = "Nữ";
				}else gioiTinh = "Nam";
				String[] rowData = {kh.getMa()+"",kh.getHoTen(), kh.getcMND(),kh.getsDT(),gioiTinh,sdf.format(kh.getNgaySinh())};
				tableModel.addRow(rowData);
			}
			table.setModel(tableModel);
	}
	//=======================Đưa Data lên table=========================

	private void updateTableData() {
		xoaTableData();
		QuanLyKhachHang ds = new  QuanLyKhachHang();
		java.util.List<KhachHang> list = ds.dsKhachHang();
			for(KhachHang dv : list) {
				String gioiTinh=  "";
				if(dv.getGioiTinh()==1) {
					gioiTinh = "Nữ";
				}else gioiTinh = "Nam";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String[] rowData = {dv.getMa()+"",dv.getHoTen(),dv.getcMND(), dv.getsDT(),gioiTinh,sdf.format(dv.getNgaySinh())};
				tableModel.addRow(rowData);
			}
			table.setModel(tableModel);
	}
	private void xoaTableData() {
		while(tableModel.getRowCount()>0) {
			tableModel.removeRow(0);
		}
	}
	
	//=======================Sự kiện con chuột=========================
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		Object src = e.getSource();
		if(chucNangHienTai==null && src.equals(table)) {
			int index = table.getSelectedRow();
				hienDataComponent(index);
				btnXoa.setEnabled(true);
				btnSua.setEnabled(true);
		}
		
	}
	
	private void hienDataComponent(int row) {
		// TODO Auto-generated method stub
			if(row!=-1) {
			txtMa.setText(table.getValueAt(row,0).toString());
			txtTen.setText(table.getValueAt(row, 1).toString());
			String txtCombo = table.getValueAt(row, 4).toString();
			cmbGioiTinh.setSelectedItem(txtCombo); 
			txtCMND.setText(table.getValueAt(row, 2).toString());
			txtSDT.setText(table.getValueAt(row, 3).toString());
			java.util.Date ngaySinh = null;
			try {
				ngaySinh = new SimpleDateFormat("yyyy-MM-dd").parse(table.getValueAt(row, 5).toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jdcNgaySinh.setDate(ngaySinh);
		}
	}
	
	public void ThemKhachHang() {
		
	}


	
}
