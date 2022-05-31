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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
import database.Database;
import entities.DichVu;
import services.QuanLyDichVu;
import uiLogin.GUIMenu;

public class GUIQuanLyDichVu extends JPanel implements ActionListener, MouseListener{
	JLabel lbMa, lbTen, lbTheLoai, lbDonVi, lbSoLuong, lbDonGia, lbBack;
	public JTextField txtMa, txtTen, txtTheLoai, txtDonVi, txtSoLuong, txtDonGia;
	JButton btnThem, btnXoaRong, btnXoa, btnSua, btnLuu, btnBack, btnThoat,btnHuy;
	JComboBox<String> cmbDichVu, cmbTheLoai;
	public static DefaultTableModel tableModel;
	JTable table;
	JPanel pNor;
	JLabel lblTitle;
	private Frame child;
	QuanLyDichVu qldv = new  QuanLyDichVu();
	public GUIQuanLyDichVu(Frame parent) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		child = parent;
		pNor = new JPanel();
		pNor.add(lblTitle = new JLabel("Quản Lý Dịch Vụ"));
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
			b1.add(lbMa = new JLabel("Mã Dịch Vụ:"));
			b1.add(Box.createHorizontalStrut(15));
			b1.add(txtMa = new JTextField(100));
			txtMa.setPreferredSize(new Dimension(30, 20));
			txtMa.setMinimumSize(new Dimension(100, 20));
			b1.add(Box.createHorizontalStrut(100));
			b1.add(lbTen = new JLabel("Tên Dịch Vụ:"));
			b1.add(Box.createHorizontalStrut(20));
			b1.add(txtTen = new JTextField(100));
			txtTen.setPreferredSize(new Dimension(30, 20));
			txtTen.setMinimumSize(new Dimension(100, 20));
			b1.add(Box.createHorizontalStrut(550));
		bc.add(Box.createVerticalStrut(15));
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(Box.createHorizontalStrut(150));
			b1.add(lbTheLoai = new JLabel("Loại:"));
			b1.add(Box.createHorizontalStrut(23));
			cmbTheLoai=new JComboBox<String>();
			cmbTheLoai.addItem("Đồ Uống");
			cmbTheLoai.addItem("Dịch Vụ");
			cmbTheLoai.addItem("Khác");
			b1.add(cmbTheLoai);
			b1.add(Box.createHorizontalStrut(60));
			b1.add(lbDonVi = new JLabel("Đơn Vị:"));
			b1.add(Box.createHorizontalStrut(20));
			b1.add(txtDonVi = new JTextField(100));
			txtDonVi.setPreferredSize(new Dimension(30, 20));
			txtDonVi.setMinimumSize(new Dimension(100, 20));
			b1.add(Box.createHorizontalStrut(width - width*60/100));
		bc.add(Box.createVerticalStrut(15));
		bc.add(b1 = Box.createHorizontalBox());
			b1.add(Box.createHorizontalStrut(150));
			b1.add(lbSoLuong = new JLabel("Số Lượng:"));
			b1.add(Box.createHorizontalStrut(20));
			b1.add(txtSoLuong = new JTextField(100));
			txtSoLuong.setPreferredSize(new Dimension(30, 20));
			txtSoLuong.setMinimumSize(new Dimension(100, 20));
			b1.add(Box.createHorizontalStrut(70));
			b1.add(lbDonGia = new JLabel("Đơn Giá:"));
			b1.add(Box.createHorizontalStrut(20));
			b1.add(txtDonGia = new JTextField(100));
			txtDonGia.setPreferredSize(new Dimension(30, 20));
			txtDonGia.setMinimumSize(new Dimension(100, 20));
			b1.add(Box.createHorizontalStrut(50));
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
		lbDonGia.setFont(new Font("Times New Roman",Font.BOLD, 15));
		lbDonVi.setFont(new Font("Times New Roman",Font.BOLD, 15));
		lbSoLuong.setFont(new Font("Times New Roman",Font.BOLD, 15));
		lbTheLoai.setFont(new Font("Times New Roman",Font.BOLD, 15));
		
		JScrollPane scroll;
		bc.add(Box.createVerticalStrut(20));
		int col[]= {5,20,20,15,10,20};
		//setColumnWidth(col);
		String[] tieuDe = "Mã Dịch Vụ;Tên Dịch Vụ;Đơn Vị;Loại;Số Lượng;Đơn Giá".split(";");
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
		//lbDonVi.setPreferredSize(dim);
		lbTheLoai.setMinimumSize(dim);
		lbSoLuong.setMinimumSize(dim);
		//lbDonGia.setPreferredSize(dim);
		//lbDonVi.setMinimumSize(dim);	
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
//		cmbTheLoai.addActionListener(this);	
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
//			new GUIQuanLyDichVu();
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
	public void enableText(boolean b) {
		//txtMa.setEnabled(b);
		txtTen.setEnabled(b);
		txtDonVi.setEnabled(b);
		txtSoLuong.setEnabled(b);
		txtDonGia.setEnabled(b);
		cmbTheLoai.setEnabled(b);
	}
	public void enableBtn() {
		btnThem.setEnabled(true);
		btnSua.setEnabled(false);
		btnXoa.setEnabled(false);
		//btnLuu.setEnabled(false);
		btnThoat.setEnabled(true);
		enableText(false);
		txtMa.setEnabled(false);
		cmbTheLoai.setSelectedItem(null);
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
			}else{
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
		enableText(true);
		txtMa.setEnabled(false);
	}
	public void actionRong() {
		cmbTheLoai.setSelectedItem(null);
		txtDonGia.setText("");
		txtDonVi.setText("");
		txtMa.setText("");
		txtSoLuong.setText("");
		txtTen.setText("");
		btnSua.setEnabled(false);
		btnXoa.setEnabled(false);
		table.clearSelection();
		enableText(false);
		xoaTableData();
		updateTableData();
	}
	private void actionXoa() {
		if (table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Chọn dòng cần xóa");
			return;
		}
		int row = table.getSelectedRow();
		QuanLyDichVu qldv = new QuanLyDichVu();
		DichVu dv;
		String ma = table.getValueAt(row, 0).toString();
		if(JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn xóa dịch vụ này?", "Cảnh Báo",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {								
				qldv.curdDichVu(ma, "", "", "", 0,0, "Delete");
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
		donGia = donGia.replace(",", "");
		System.out.println(donGia);
		
		if(cmbTheLoai.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "Chọn Thể Loại");
			return false;
		}
		
		if (ten.equals("")) {
			JOptionPane.showMessageDialog(this, "Nhập Tên Dịch Vụ");
			txtTen.requestFocus();
			return false ;
		}
//		}else if(!ten.matches("[\\D]+")){
//			JOptionPane.showMessageDialog(this, "Tên dịch vụ chỉ chứa ký tự, ký số, khoảng trắng");
//			txtTen.selectAll();
//			return false;
//		}
		
		if (donGia.equals("")) {
			JOptionPane.showMessageDialog(this, "Nhập Đơn Giá");
			txtDonGia.requestFocus();
			return false;
		}else if(!donGia.matches("[0-9]+")) {
			JOptionPane.showMessageDialog(this, "Đơn Giá Phải Dạng Số");
			txtDonGia.selectAll();
			txtDonGia.requestFocus();
			return false;
		}else if(Integer.parseInt(donGia) < 0){
			JOptionPane.showMessageDialog(this, "Đơn Giá Phải Có Giá Trị Dương");
			txtDonGia.selectAll();
			txtDonGia.requestFocus();
			return false;
		}
	
		if (donVi.equals("")) {
			JOptionPane.showMessageDialog(this, "Nhập Đơn Vị");
			txtDonVi.requestFocus();
			return false;
		}
		else if(!donVi.matches("[\\D]+")){
			JOptionPane.showMessageDialog(this, "Đơn Vị chỉ chứa ký tự, ký số, khoảng trắng");
			txtDonVi.selectAll();
			return false;
		}
	
		if (soLuong.equals("")) {
			JOptionPane.showMessageDialog(this, "Nhập Số Lượng");
			txtSoLuong.requestFocus();
			return false;
		}else if(!soLuong.matches("[0-9]+")) {
			JOptionPane.showMessageDialog(this, "Số Lượng Phải Là Số");
			txtSoLuong.selectAll();
			txtSoLuong.requestFocus();
			return false;
		}else if(Integer.parseInt(soLuong) < 0){
			JOptionPane.showMessageDialog(this, "Số Lượng Phải Có Giá Trị Dương");
			txtSoLuong.selectAll();
			txtSoLuong.requestFocus();
			return false;
		}
		return true;
	}
	private void actionLuu() {
		String ma="";
		String ten = txtTen.getText().trim();
		String theLoai = (String) cmbTheLoai.getSelectedItem();
		String donVi = txtDonVi.getText().trim();
		String donGia = txtDonGia.getText();
		donGia = donGia.replace(",", "");
		String soLuong = txtSoLuong.getText();
		if(chucNangHienTai.equals("them")) {
			String type = "Insert";
			if(checkData("",ten,theLoai,donVi,donGia,soLuong)) {	
				if(qldv.curdDichVu(ma, ten, donVi, theLoai, Integer.parseInt(soLuong), Integer.parseInt(donGia), type)) {
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
			 enableText(true);
			 int row = table.getSelectedRow();
			 String type = "Update";
				if(row >=0){
					//ma, ten,theLoai,donGia,soLuong,donVi,ghiChu
					//lấy cái mã để sửa thông tin băng đĩa dựa trên mã trên txt
					ma = table.getValueAt(row, 0).toString();
					if(checkData(ma,ten,theLoai,donVi,donGia,soLuong)) {
						if(qldv.curdDichVu(ma, ten, donVi, theLoai, Integer.parseInt(soLuong), Integer.parseInt(donGia), type)) {
							updateTableData();					
							actionHuy();
						}			
					}
				}
		}
	}
	private void actionSua() {
		btnHuy.setVisible(true);
		btnThem.setVisible(false);
		btnXoa.setVisible(false);
		chucNangHienTai = "sua";
		
		btnSua.setEnabled(true);	btnSua.setText("Lưu"); btnSua.setIcon(new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\save.png"));
		enableText(true);
		txtMa.setEnabled(false);
	}
	public void actionTim(String maDV, String tenDV, String donVi, String loai, String soLuongCo, String giaDV) {
		try {
			qldv = new QuanLyDichVu();
			//String maDV, String tenDV, String donVi, String loai, String soLuongCo, String giaDV
			ArrayList<DichVu> list = qldv.timKiem(maDV, tenDV, donVi, loai, soLuongCo, giaDV);
			if(list.size()==0) {
				JOptionPane.showMessageDialog(this, "Không Tìm Thấy");
			}
			else updateTableData1(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Hiển thị tìm đc lên
	private void updateTableData1(ArrayList<DichVu> list) {
			xoaTableData();
			DecimalFormat df = new DecimalFormat();
			for(DichVu dv : list) {
				String[] rowData = {dv.getMaDV()+"",dv.getTenDV(), dv.getDonVi(),dv.getLoai(),dv.getSoLuongCo()+"",df.format(dv.getGiaDV())};
				tableModel.addRow(rowData);
			}
			table.setModel(tableModel);
	}
	//=======================Đưa Data lên table=========================

	private void updateTableData() {
		xoaTableData();
		QuanLyDichVu ds = new  QuanLyDichVu();
		java.util.List<DichVu> list = ds.dsDichVu();
			for(DichVu dv : list) {
				DecimalFormat df = new DecimalFormat();
				String[] rowData = {dv.getMaDV()+"",dv.getTenDV(), dv.getDonVi(),dv.getLoai(),dv.getSoLuongCo()+"",df.format(dv.getGiaDV())};
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
			String txtCombo = table.getValueAt(row, 3).toString();
			cmbTheLoai.setSelectedItem(txtCombo); 
			txtDonVi.setText(table.getValueAt(row, 2).toString());
			txtSoLuong.setText(table.getValueAt(row, 4).toString());
			txtDonGia.setText(table.getValueAt(row, 5).toString());
		}
	}


	
}
