package uiThueTraPhong;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.DefaultFormatter;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import entities.DichVu;
import entities.HoaDon;
import entities.KhachHang;
import entities.PhieuDatPhong;
import entities.Phong;
import services.QuanLyCTDV;
import services.QuanLyDichVu;
import services.QuanLyHoaDon;
import services.QuanLyKhachHang;
import services.QuanLyPhong;
import services.QuanLyThueTra;
import uiLogin.GUIMenu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GUITraPhong extends JFrame implements ActionListener, MouseListener, Printable {

	JLabel lbMaKH, lbGT, lbTenKH, lbSDT, lbDiaChi, lbCMND, lbTongTien, lbTongTienData;
	JTextField txtMaKH, txtGT, txtTenKH, txtSDT, txtDiaChi, txtCMND;

	JLabel lbMaPhong, lbLoaiPhong, lbView, lbGiaPhong, lbTim;
	JLabel lbTienCoc, lbTienCocData, lbTongTienPhong, lbTongTienPhongData;
	JTextField txtMaPhong, txtLoaiPhong, txtView, txtGiaPhong, txtTim;
	JTable tableLeft, tableRight;
	DefaultTableModel tableLeftModel, tableRightModel;
	JScrollPane scroll;
	JButton btnBack, btnTim, btnCancel, btnPrint, btnPay;
	JComboBox<String> cmbTimKiem;
	private GUIMenu parent;
	Box Bprint;

	public GUITraPhong() {
		setSize(1230, 600);
		setResizable(false);
		setLocationRelativeTo(null);

		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\This PC\\Desktop\\image\\logo.png"));
		setTitle("Trả Phòng");
		Box b1, b2, bc, bcc;
		bcc = Box.createHorizontalBox();
		add(bcc);
		bcc.add(bc = Box.createVerticalBox());
		String[] header = "Mã Dịch Vụ;Tên Dịch Vụ;Loại Dịch Vụ".split(";");
		tableLeftModel = new DefaultTableModel(header, 0);
		tableLeft = new JTable(tableLeftModel) {
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
		tableLeft.setAutoCreateRowSorter(true);
		// tableModel.isCellEditable(row, column);
		scroll = new JScrollPane(tableLeft, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(950, 530));
		JTableHeader header1 = tableLeft.getTableHeader();
		header1.setBackground(Color.CYAN);
		header1.setOpaque(false); // xét cứng cột
		tableLeft.getTableHeader().setReorderingAllowed(false);
		scroll.setPreferredSize(new Dimension(400, 300));
		bc.add(scroll);
		bc.add(Box.createVerticalStrut(10));

		JSeparator s = new JSeparator();
		s.setOrientation(SwingConstants.VERTICAL);
		bcc.add(Box.createHorizontalStrut(10));
		bcc.add(s);
		bcc.add(Box.createHorizontalStrut(10));
		// Right
		bcc.add(bc = Box.createVerticalBox());
		bc.add(b1 = Box.createHorizontalBox());
		b1.add(Box.createHorizontalStrut(50));
		b1.add(lbTim = new JLabel("Tìm Kiếm Dịch Vụ:"));
		b1.add(Box.createHorizontalStrut(20));
		cmbTimKiem = new JComboBox<String>();
		cmbTimKiem.addItem("Tìm Theo Mã");
		cmbTimKiem.addItem("Tìm Theo Tên");
		cmbTimKiem.addItem("Tìm Theo Loại");
		cmbTimKiem.setPreferredSize(new Dimension(200, 5));
		b1.add(cmbTimKiem);
		b1.add(Box.createHorizontalStrut(20));
		b1.add(txtTim = new JTextField());
		b1.add(Box.createHorizontalStrut(20));
		b1.add(btnTim = new JButton("Tìm", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\find.png")));
//		JSpinner timeSpinner = new JSpinner( new SpinnerDateModel() );
//		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "dd/MM/yyyy HH:mm:ss");
//		timeSpinner.setEditor(timeEditor);
//		timeSpinner.setValue(new Date());
//		b1.add(timeSpinner);
		b1.add(Box.createHorizontalStrut(150));
		bc.add(Box.createVerticalStrut(10));
		// bc.setPreferredSize(new Dimension(300, 500));
		bc.add(Bprint = Box.createVerticalBox());
		Bprint.add(Box.createVerticalStrut(10));
		Bprint.add(b1 = Box.createHorizontalBox());

		JLabel lbNgayHienTai;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		QuanLyThueTra qlLyThueTra = new QuanLyThueTra();
		PhieuDatPhong pdp = qlLyThueTra.findPhieuDatPhongByMaPhieu(GUIThuePhong.maPhieu);
		b1.add(new JLabel("Ngày Đến: " + sdf.format(pdp.getNgayDen())));
		b1.add(Box.createHorizontalStrut(20));
		b1.add(lbNgayHienTai = new JLabel());
		Date date = new Date();
		lbNgayHienTai.setText("Ngày Lập Hoá Đơn: " + sdf.format(date.getTime()));
		Bprint.add(Box.createVerticalStrut(5));
		Box b3, b4;
		Bprint.add(b3 = Box.createHorizontalBox());
		b3.add(b4 = Box.createHorizontalBox());
		b4.add(Box.createHorizontalStrut(50));
		b4.add(b1 = Box.createVerticalBox());
		b1.add(Box.createVerticalStrut(10));
		b1.add(b2 = Box.createHorizontalBox());
		b2.add(lbMaKH = new JLabel("Mã Khách Hàng: "));
		b2.add(Box.createHorizontalStrut(10));
		b2.add(txtMaKH = new JTextField());
		b2.add(Box.createHorizontalStrut(10));
		b2.add(lbGT = new JLabel("Giới Tính: "));
		b2.add(Box.createHorizontalStrut(10));
		b2.add(txtGT = new JTextField());
		b2.add(Box.createHorizontalStrut(10));
		b1.add(Box.createVerticalStrut(10));
		b1.add(b2 = Box.createHorizontalBox());
		b2.add(lbTenKH = new JLabel("Tên Khách Hàng: "));
		b2.add(Box.createHorizontalStrut(10));
		b2.add(txtTenKH = new JTextField());
		b2.add(Box.createHorizontalStrut(10));
		b1.add(Box.createVerticalStrut(10));
		b1.add(b2 = Box.createHorizontalBox());
		b2.add(lbSDT = new JLabel("Số Điện Thoại: "));
		b2.add(Box.createHorizontalStrut(10));
		b2.add(txtSDT = new JTextField());
		b2.add(Box.createHorizontalStrut(10));
		TitledBorder title1 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED),
				"Thông Tin Khách Hàng");
		b4.setBorder(title1);
		b1.add(Box.createVerticalStrut(10));
		b1.add(b2 = Box.createHorizontalBox());
		b4.add(Box.createHorizontalStrut(50));
		b3.add(b4 = Box.createHorizontalBox());
		b4.add(Box.createHorizontalStrut(50));
		b4.add(b1 = Box.createVerticalBox());
		b1.add(Box.createVerticalStrut(10));
		b1.add(b2 = Box.createHorizontalBox());
		b1.add(b2 = Box.createHorizontalBox());
		b2.add(lbMaPhong = new JLabel("Mã Phòng:"));
		b2.add(Box.createHorizontalStrut(10));
		b2.add(txtMaPhong = new JTextField());
		b2.add(Box.createHorizontalStrut(10));
		b1.add(Box.createVerticalStrut(10));
		b1.add(b2 = Box.createHorizontalBox());
		b2.add(lbLoaiPhong = new JLabel("Loại Phòng:"));
		b2.add(Box.createHorizontalStrut(10));
		b2.add(txtLoaiPhong = new JTextField());
		b2.add(Box.createHorizontalStrut(10));
		b1.add(Box.createVerticalStrut(10));
		b1.add(b2 = Box.createHorizontalBox());
		b2.add(lbView = new JLabel("Ghi Chú:"));
		b2.add(Box.createHorizontalStrut(10));
		b2.add(txtView = new JTextField());
		b2.add(Box.createHorizontalStrut(10));
		b1.add(Box.createVerticalStrut(10));
		b1.add(b2 = Box.createHorizontalBox());
		b2.add(lbGiaPhong = new JLabel("Giá Phòng:"));
		b2.add(Box.createHorizontalStrut(10));
		b2.add(txtGiaPhong = new JTextField());
		b2.add(Box.createHorizontalStrut(10));
		TitledBorder title2 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED),
				"Thông Tin Phòng");
		b4.setBorder(title2);
		b1.add(Box.createVerticalStrut(10));
		b1.add(b2 = Box.createHorizontalBox());
		b4.add(Box.createHorizontalStrut(50));
		Bprint.add(Box.createVerticalStrut(5));
		Bprint.add(b1 = Box.createHorizontalBox());
		String[] header2 = "Mã Dịch Vụ;Tên Dịch Vụ;Đơn Giá;Đơn Vị;Số Lượng;Tổng Tiền; Thời Gian".split(";");
		
		
		tableRightModel = new DefaultTableModel(header2, 0);
		tableRight = new JTable(tableRightModel) {
			public boolean isCellEditable(int row, int col) {
				if (col < 4 || col == 5)
					return false;
				else
					return true;
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
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < tableRight.getColumnCount(); i++) {
			tableRight.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		tableRight.setAutoCreateRowSorter(true);
		// tableModel.isCellEditable(row, column);
		scroll = new JScrollPane(tableRight, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(950, 530));
		b1.add(scroll);
		JTableHeader header3 = tableRight.getTableHeader();
		header3.setBackground(Color.CYAN);
		header3.setOpaque(false); // xét cứng cột
		tableRight.getTableHeader().setReorderingAllowed(false);
//		JSpinner timeSpinner = new JSpinner( new SpinnerDateModel() );
//		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "dd/MM/yyyy HH:mm:ss");
//		timeSpinner.setEditor(timeEditor);
//		timeSpinner.setValue(new Date()); 
//		
		//tableRight.getColumnModel().getColumn(6).setCellRenderer(new JDateChooserRenderer(timeSpinner));
		//tableRight.getColumnModel().getColumn(6).setCellEditor(new JDateChooserCellEditor());
		//System.out.println("a: "+tableRight.getValueAt(0, 6));
		Bprint.add(Box.createVerticalStrut(10));
		Bprint.add(b1 = Box.createHorizontalBox());
		b1.add(lbTongTienPhong = new JLabel("Tổng Tiền Phòng: "));
		b1.add(lbTongTienPhongData = new JLabel());
		b1.add(Box.createHorizontalStrut(100));
		b1.add(lbTienCoc = new JLabel("Đã Cọc: "));
		b1.add(lbTienCocData = new JLabel());
		b1.add(Box.createHorizontalStrut(100));
		b1.add(lbTongTien = new JLabel("Thành Tiền:"));
		b1.add(Box.createHorizontalStrut(30));
		b1.add(lbTongTienData = new JLabel());
		b1.add(Box.createHorizontalStrut(5));
		b1.add(new JLabel("VNĐ"));

		bc.add(Box.createVerticalStrut(10));
		bc.add(b1 = Box.createHorizontalBox());
		b1.add(Box.createHorizontalStrut(30));
		b1.add(btnPay = new JButton("Thanh Toán", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\pay.png")));
		b1.add(Box.createHorizontalStrut(30));
		b1.add(btnPrint = new JButton("In Hoá Đơn", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\print.png")));
		b1.add(Box.createHorizontalStrut(150));
		b1.add(btnCancel = new JButton("Huỷ", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\cancel.png")));
		b1.add(Box.createHorizontalStrut(30));
		b1.add(btnBack = new JButton("Lùi Về Trang Trước", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\logout.png")));
		btnBack.setBackground(Color.red);
		b1.add(Box.createHorizontalStrut(30));
		bc.add(Box.createVerticalStrut(10));
//		TitledBorder b = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
//				"bbbbb");
//		bc.setBorder(b);
		List<JLabel> tmp1 = Arrays.asList(lbMaPhong, lbLoaiPhong, lbView, lbGiaPhong, lbTongTien, lbTim, lbTienCoc,
				lbTienCocData, lbTongTienPhong, lbTongTienPhongData);
		List<JLabel> tmp = Arrays.asList(lbMaKH, lbGT, lbTenKH, lbSDT);
		List<JTextField> tmp2 = Arrays.asList(txtMaKH, txtTenKH, txtGT, txtSDT);
		List<JTextField> tmp3 = Arrays.asList(txtMaPhong, txtLoaiPhong, txtView, txtGiaPhong);

		setTextisFalse(tmp2);
		setTextisFalse(tmp3);
		setFontLabel(tmp);
		setFontLabel(tmp1);
		// List<JTextField> tmp2 = Arrays.asList(a);
		// setSize(1500, 600);
		// ================Load Database==============
		database.Database.getInstance().connect();
		updateTableServiceData();
		// showDataCustomer();
		// Đăng Ký sự kiện
		tableLeft.addMouseListener(this);
		tableRight.addMouseListener(this);
		btnBack.addActionListener(this);
		btnCancel.addActionListener(this);
		btnPrint.addActionListener(this);
		btnTim.addActionListener(this);
		tableLeft.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableRight.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		btnCancel.setEnabled(false);
		btnPay.addActionListener(this);
		showDataCustomer();
		showDataRoom();

		setVisible(true);

	}

	public GUITraPhong(GUIMenu parent) {
		this();
		this.parent = parent;
	}

	public void setFontLabel(List<JLabel> listLabel) {
		listLabel.forEach(x -> {
			x.setFont(new Font("Times New Roman", Font.BOLD, 15));
		});
	}

	public void setTextisFalse(List<JTextField> listText) {
		listText.forEach(x -> {
			x.setEnabled(false);
			x.setDisabledTextColor(Color.BLACK);
		});
	}

	private void xoaTableData() {
		while (tableRightModel.getRowCount() > 0) {
			tableRightModel.removeRow(0);
		}
	}

	private void updateTableServiceData() {
		QuanLyDichVu ql = new QuanLyDichVu();
		ArrayList<DichVu> listDV = ql.dsDichVu();
		for (DichVu dv : listDV) {
			String[] rowData = { dv.getMaDV() + "", dv.getTenDV(), dv.getLoai() };
			tableLeftModel.addRow(rowData);
		}
		;
		tableLeft.setModel(tableLeftModel);
	}

	private void showDataTableRight(String maDV) {
		// TODO Auto-generated method stub
		QuanLyDichVu ql = new QuanLyDichVu();
		DichVu dv = ql.findById(maDV);
		DecimalFormat df = new DecimalFormat("###,000");
		String[] rowData = { dv.getMaDV() + "", dv.getTenDV(), df.format(dv.getGiaDV()), dv.getDonVi() };
		tableRightModel.addRow(rowData);
		tableRight.setModel(tableRightModel);
	}

	private void showDataCustomer() {
		QuanLyKhachHang ql = new QuanLyKhachHang();
		KhachHang kh = ql.chiTietKhachHang(GUIThuePhong.cmndKH);
		txtMaKH.setText(kh.getMa() + "");
		txtTenKH.setText(kh.getHoTen());
		txtSDT.setText(kh.getsDT());
		if (kh.getGioiTinh() == 1) {
			txtGT.setText("Nữ");
		} else
			txtGT.setText("Nam");
	}

	private void showDataRoom() {
		QuanLyPhong ql = new QuanLyPhong();
		Phong p = ql.tim1Phong(Integer.parseInt(GUIThuePhong.maPhong) - 100);
		txtMaPhong.setText((100 + p.getId()) + "");
		txtLoaiPhong.setText(p.getLoaiPhong());
		txtView.setText(p.getGhiChu());
		DecimalFormat df = new DecimalFormat("###,000");
		txtGiaPhong.setText(df.format(p.getGiaPhong()));

		Date dHT = new Date();
		QuanLyThueTra qlLyThueTra = new QuanLyThueTra();
		PhieuDatPhong pdp = qlLyThueTra.findPhieuDatPhongByMaPhieu(GUIThuePhong.maPhieu);
		long ngay = dHT.getTime() - pdp.getNgayDen().getTime();

		ngay = ngay / (1000 * 60 * 60 * 24);
		//System.out.println("áđâsd: " + ngay);
		if (ngay < 1) {
			ngay = 1;
		}
		lbTongTienPhongData.setText(df.format(p.getGiaPhong() * ngay));
		lbTienCocData.setText(df.format(p.getGiaPhong()));
	}

	private boolean checkService(String maDV) {
		for (int i = 0; i < tableRight.getRowCount(); i++) {
			if (maDV.equals(tableRight.getValueAt(i, 0))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if(src.equals(tableLeft)) {
			if (e.getClickCount() == 2) {
				if (src.equals(tableLeft)) {
					int row = tableLeft.getSelectedRow();
					if (checkService(tableLeft.getValueAt(row, 0).toString()) == true) {
						showDataTableRight(tableLeft.getValueAt(row, 0).toString());
					}
					
				}
			}
		}
		if(src.equals(tableRight) && tableRight.getSelectedColumn()==6) {
			JPanel panel = new JPanel(new GridLayout(0,1));
			panel.setPreferredSize(new Dimension(20,50));
			JSpinner timeSpinner = new JSpinner( new SpinnerDateModel() );
			JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "dd/MM/yyyy HH:mm:ss");
			timeSpinner.setEditor(timeEditor);
			timeSpinner.setValue(new Date());
			panel.add(timeSpinner);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	        int result = JOptionPane.showConfirmDialog(null, panel, "Thời Gian",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	        if(result == JOptionPane.OK_OPTION) {
	        	tableRight.setValueAt(sdf.format(timeSpinner.getValue()),tableRight.getSelectedRow() , 6);
	        }
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if (src.equals(tableRight)) {
			btnCancel.setEnabled(true);
			int row = tableRight.getSelectedRow();
			if (tableRight.getValueAt(row, 4) != null && !tableRight.getValueAt(row, 4).toString().matches("[0-9]+")) {
				JOptionPane.showMessageDialog(this, "Số Lượng Chỉ Là Số");
				return;
			}
			if (tableRight.getValueAt(row, 4) != null) {
				String maDV = tableRight.getValueAt(row, 0).toString();
				String slsd = tableRight.getValueAt(row, 4).toString();
				if (checkSoLuong(maDV, slsd)) {
					String tien = tableRight.getValueAt(row, 2).toString().replace(",", "");
					int tongTien = Integer.parseInt(tableRight.getValueAt(row, 4).toString()) * Integer.parseInt(tien);
					DecimalFormat df = new DecimalFormat("###,000");
					tableRight.setValueAt(df.format(tongTien), row, 5);
				}

			}
		}
	}

	private boolean checkSoLuong(String maDV, String slsd) {
		QuanLyDichVu qldv = new QuanLyDichVu();
		DichVu dv = qldv.findById(maDV);
		if (Integer.parseInt(slsd) > dv.getSoLuongCo()) {
			JOptionPane.showMessageDialog(this, "Số Lượng SD Phải Nhỏ Hơn " + dv.getSoLuongCo());
			return false;
		}
		return true;
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if (src.equals(btnBack)) {
			new GUIThuePhong();
			this.dispose();
		}
		if (src.equals(btnCancel)) {
			int row = tableRight.getSelectedRow();
			if (row != -1) {
				tableRightModel.removeRow(row);// (row,row);
				btnCancel.setEnabled(false);
			}
		}
		if (src.equals(btnPrint)) {
			PrinterJob pj = PrinterJob.getPrinterJob();
			pj.setJobName(" Bill ");

			pj.setPrintable(new Printable() {
				public int print(Graphics pg, PageFormat pf, int pageNum) {
					if (pageNum > 0) {
						return Printable.NO_SUCH_PAGE;
					}
					Graphics2D g2 = (Graphics2D) pg;
					g2.translate(pf.getImageableX(), pf.getImageableY());
					Bprint.paint(g2);
					return Printable.PAGE_EXISTS;
				}
			});
			if (pj.printDialog() == false)
				return;

			try {
				pj.print();
			} catch (PrinterException ex) {
				// handle exception
			}
		}
		if (src.equals(btnPay)) {
			actionPay();
		}
		if(src.equals(btnTim)) {
			String s = txtTim.getText().trim();
			actionTim(s);
		}

	}
	public void actionTim(String s) {
		try {
			String loai = cmbTimKiem.getSelectedItem().toString();
			QuanLyDichVu ds = new QuanLyDichVu();
			ArrayList<DichVu> list = ds.timKiem(s,loai);
			if(list.size()==0) {
				JOptionPane.showMessageDialog(this, "Không Tìm Thấy");
			}
			else updateTableData1(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void xoaTableLeftData() {
		while (tableLeftModel.getRowCount() > 0) {
			tableLeftModel.removeRow(0);
		}
	}
	private void updateTableData1(ArrayList<DichVu> list) {
		xoaTableLeftData();
		DecimalFormat df = new DecimalFormat();
		for(DichVu dv : list) {
			String[] rowData = { dv.getMaDV() + "", dv.getTenDV(), dv.getLoai() };
			tableLeftModel.addRow(rowData);
		}
		tableLeft.setModel(tableLeftModel);
	}

	private void actionPay() {
		// TODO Auto-generated method stub
		double tongTienDV = 0;
		for (int i = 0; i < tableRight.getRowCount(); i++) {
			String maDV = tableRight.getValueAt(i, 0).toString();
			String soLuong = tableRight.getValueAt(i, 4).toString();
			String thoiGian = tableRight.getValueAt(i, 6).toString();
			QuanLyCTDV qlctdv = new QuanLyCTDV();
			qlctdv.createCTDV(maDV, GUIThuePhong.maPhieu, soLuong,thoiGian);
			QuanLyDichVu qldv = new QuanLyDichVu();
			DichVu dv = qldv.findById(tableRight.getValueAt(i, 0).toString());
			tongTienDV += dv.getGiaDV() * Integer.parseInt(soLuong);
			// System.out.println("haha: "+dv);
			// System.out.println("hih: "+dv.getLoai());
			if (dv.getLoai().equals("Đồ Uống")) {
				qldv.updateSL(tableRight.getValueAt(i, 0).toString(), soLuong);
			}
		}
		// tính tiền phòng
		QuanLyHoaDon qlhd = new QuanLyHoaDon();
		QuanLyPhong qlp = new QuanLyPhong();
		QuanLyThueTra qlLyThueTra = new QuanLyThueTra();
		PhieuDatPhong pdp = qlLyThueTra.findPhieuDatPhongByMaPhieu(GUIThuePhong.maPhieu);

		Phong p = qlp.tim1Phong(Integer.parseInt(txtMaPhong.getText()) - 100);
		Date dHT = new Date();
		long ngay = dHT.getTime() - pdp.getNgayDen().getTime();
		ngay = ngay / (1000 * 60 * 60 * 24);
		if (ngay == 0 || ngay < 1)
			ngay = 1;
		double tongTien = ngay * p.getGiaPhong();
		// cần p update lên cái tong tien
		 //HoaDon hd = qlhd.timTheoMa(GUIThuePhong.maPhieu);
		qlhd.update(tongTien + tongTienDV + "", GUIThuePhong.maPhieu);

		// update PDP ngay
		QuanLyThueTra qltt = new QuanLyThueTra();
		qltt.update(GUIThuePhong.maPhieu);
		HoaDon hd = qlhd.timTheoMa(GUIThuePhong.maPhieu);
		DecimalFormat df = new DecimalFormat("###,000");
		lbTongTienData.setText(df.format(hd.getTongTien()));
		btnPay.setEnabled(false);
		JOptionPane.showMessageDialog(this, "Thanh Toán Thành Công");
		parent.showPanel(new GUIThueTraPhong(parent));
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		// TODO Auto-generated method stub
		return 0;
	}

//	static class JDateChooserRenderer extends JSpinner implements TableCellRenderer   {
//		SpinnerDateModel timeModel = new SpinnerDateModel(new Date(), null, null,Calendar.MINUTE);
//		JSpinner timeJSpinner = new JSpinner( timeModel );
//		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(timeJSpinner, "dd/MM/yyyy HH:mm:ss");
//		
//		public JDateChooserRenderer(JSpinner timeJSpinner) {
//			this.timeJSpinner = timeJSpinner;
//			setOpaque(true);//MUST do this for background to show up.
//	    }
//		
//		@Override
//		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
//				int row, int column) {
////			SpinnerDateModel timeModel = new SpinnerDateModel(new Date(), null, null,Calendar.MINUTE);
////			JSpinner timeJSpinner = new JSpinner( timeModel );
////			JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(timeJSpinner, "dd/MM/yyyy HH:mm:ss");
//			// TODO Auto-generated method stub
//			System.out.println("col: "+column);
//			if(column==6) {
//				if (!(value instanceof Date)) {
//	                return this;
//	            }
//				setValue(value);
//			}
//			System.out.println("aaa: "+value);
//			return this;
//		}
//	}
//
//	class JDateChooserCellEditor extends AbstractCellEditor implements TableCellEditor, ChangeListener{
//		private static final long serialVersionUID = 917881575221755609L;
//		JSpinner dateSpinner;
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//		String ans= sdf.format(new Date());
//		JTextField txt;
//		 public JDateChooserCellEditor() {
////				txt = new JTextField();				
////			    SpinnerDateModel timeModel = new SpinnerDateModel(new Date(), null, null,Calendar.MINUTE);
////			    dateSpinner = new JSpinner(timeModel);
////			    txt.add(dateSpinner);
////			    JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy HH:mm:ss");
////			    dateSpinner.setEditor(dateEditor);
////			    dateSpinner.addChangeListener(this);
////			    dateEditor = ((JSpinner.DateEditor)dateSpinner.getEditor());
////			    System.out.println(dateEditor.getTextField().getValue());
////			    txt = dateEditor.getTextField();
////			    System.out.println("hehe: "+txt);
//			 System.out.println("mmmm: "+tableRight.getSelectedColumn()+" "+tableRight.getSelectedRow());
//			 dateSpinner = new JSpinner(new SpinnerDateModel());
//			 dateSpinner.setOpaque(true);
//			 dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy HH:mm:ss"));
//			 dateSpinner.addChangeListener(this);
//			 
//		 }
//		 @Override
//		public void stateChanged(ChangeEvent e) {
//			// TODO Auto-generated method stub
//			JSpinner s = (JSpinner)e.getSource();
//			System.out.println("a: "+s.getValue().toString());
//			ans = s.getValue().toString();
//			txt.setText(ans);
//			System.out.println("b: "+txt.getText());
//			setVisible(true);
//			fireEditingStopped();
//		}
//		@Override
//		public Object getCellEditorValue() {
//			// TODO Auto-generated method stub
//			System.out.println("bbb: "+dateSpinner.getValue());
//			if(tableRight.getSelectedColumn()==6)
//			return dateSpinner.getValue();
//			return null;
//		}
//		@Override
//		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
//				int column) {
//			// TODO Auto-generated method stub
////			System.out.println("col123: "+column);
////			System.out.println("aaa: "+value);
////			if(column==6)
//			if(value!=null)
//			dateSpinner.setValue(value);
//			    return dateSpinner;
//			    
//			//return null;
//		}	
//	}

}
