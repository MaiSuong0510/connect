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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.TabableView;

import com.toedter.calendar.JDateChooser;
import database.Database;
import entities.DichVu;
import entities.NhanVien;
import entities.PhieuDatPhong;
import services.QuanLyDichVu;
import services.QuanLyNhanVien;
import services.QuanLyThueTra;
import uiLogin.GUIMenu;

public class GUIQuanLyNhanVien extends JPanel implements ActionListener, MouseListener {
	JSplitPane split;
	JPanel pNor, pCen, pSouth;
	JLabel lblTitle, lblMaNhanVien, lblTenNhanVien, lblSoDienThoai, lbNgaySinh, lbDiaChi, lblgioitinh, lbCMND;
	JTextField txtMaNV, txtTenNhanVien, txtSoDienThoai, txtDiaChi, txtCMND;
	JComboBox<String> cmbGioitinh;
	Box b;
	JDateChooser dcNgaySinh;
	static DefaultTableModel tableModel;
	JTable table;
	JButton btnTim, btnPass, btnThem, btnXoaTrang, btnXoa, btnLuu, btnBack, btnSua, btnThoat;
	JRadioButton phai;
	public int count = 0;
	QuanLyNhanVien dsNV = new QuanLyNhanVien();
	private Frame child;
	public static boolean flagBtnThem = true;
	private SimpleDateFormat dinhDangNgay = new SimpleDateFormat("yyyy-MM-dd");
	JComboBox<String> cmbTim;
	private ArrayList<String> values = new ArrayList<String>();
	private StringArrayLookAhead lookAhead = new StringArrayLookAhead(values);
	private LookAheadTextField txtNhap = new LookAheadTextField(20, lookAhead);

	public GUIQuanLyNhanVien(Frame parent) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		child = parent;
		pNor = new JPanel();
		pNor.add(lblTitle = new JLabel("Quản Lý Nhân Viên"));
		lblTitle.setFont(new Font("times new roman", Font.BOLD, 40));
		lblTitle.setForeground(new Color(0xFFAA00));
		Box b1, b2, b3;
		Box b = Box.createVerticalBox();
		add(b);
		b.add(pNor, BorderLayout.NORTH);
		b.add(Box.createVerticalStrut(30));
		b.add(b1 = Box.createHorizontalBox());
		b1.add(Box.createHorizontalStrut(20));
		
		b1.add(lblMaNhanVien = new JLabel("Mã Nhân Viên:"));
		lblMaNhanVien.setPreferredSize(new Dimension(300, 20));
		lblMaNhanVien.setMinimumSize(new Dimension(100, 20));
		b1.add(Box.createHorizontalStrut(30));
		b1.add(txtMaNV = new JTextField());
		txtMaNV.setPreferredSize(new Dimension(30, 20));
		txtMaNV.setMinimumSize(new Dimension(30, 20));
		txtMaNV.setEditable(true);
		b1.add(Box.createHorizontalStrut(28));
		
		b1.add(lbCMND = new JLabel("CMND:"));
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtCMND = new JTextField());
		txtCMND.setPreferredSize(new Dimension(30, 20));
		txtCMND.setMinimumSize(new Dimension(100, 20));
		b1.add(Box.createHorizontalStrut(30));
		
		b1.add(lbNgaySinh = new JLabel("Ngày Sinh:"));
		b1.add(Box.createHorizontalStrut(30));
		String date = "yyyy-MM-dd";
		b1.add(dcNgaySinh = new JDateChooser());
		dcNgaySinh.setDateFormatString(date);
		dcNgaySinh.setPreferredSize(new Dimension(30, 20));
		dcNgaySinh.setMinimumSize(new Dimension(100, 20));
		b1.add(Box.createHorizontalStrut(width-width*60/100));
		b.add(Box.createVerticalStrut(10));
		b.add(b1 = Box.createHorizontalBox());
		b1.add(Box.createHorizontalStrut(20));
		
		b1.add(lblTenNhanVien = new JLabel("Tên Nhân Viên:"));
		lblTenNhanVien.setPreferredSize(new Dimension(300, 20));
		lblTenNhanVien.setMinimumSize(new Dimension(100, 20));
		b1.add(Box.createHorizontalStrut(30));
		b1.add(txtTenNhanVien = new JTextField());
		txtTenNhanVien.setPreferredSize(new Dimension(30, 20));
		txtTenNhanVien.setMinimumSize(new Dimension(100, 20));
		b1.add(Box.createHorizontalStrut(28));
		
		b1.add(lblSoDienThoai = new JLabel("Số điện thoại:"));
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtSoDienThoai = new JTextField());
		txtSoDienThoai.setPreferredSize(new Dimension(30, 20));
		txtSoDienThoai.setMinimumSize(new Dimension(100, 20));
		b1.add(Box.createHorizontalStrut(width-width*50/100));
		b.add(Box.createVerticalStrut(10));
		b.add(b1 = Box.createHorizontalBox());
		b1.add(Box.createHorizontalStrut(20));
		
		b1.add(lbDiaChi = new JLabel("Địa Chỉ:"));
		b1.add(Box.createHorizontalStrut(79));
		b1.add(txtDiaChi = new JTextField());
		txtDiaChi.setPreferredSize(new Dimension(30, 20));
		txtDiaChi.setMinimumSize(new Dimension(100, 20));
		b1.add(Box.createHorizontalStrut(350));
		b.add(Box.createVerticalStrut(10));
		b.add(b1 = Box.createHorizontalBox());
		b1.add(Box.createHorizontalStrut(20));
		
		b1.add(lblgioitinh = new JLabel("Giới tính:"));
		b1.add(Box.createHorizontalStrut(70));
		cmbGioitinh = new JComboBox<>();
		cmbGioitinh.addItem("Nam");
		cmbGioitinh.addItem("Nữ");
		b1.add(cmbGioitinh);
		b1.add(Box.createHorizontalStrut(width - width*80/100));
		
		b1.add(btnPass = new JButton("Đặt Mật Khẩu", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\pass.png")));
		b1.add(Box.createHorizontalStrut(50));
		b1.add(btnXoaTrang = new JButton("Làm Mới", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\reload.png")));
		b1.add(Box.createHorizontalStrut(5));
		b1.add(btnBack = new JButton("Lùi Về Trang Trước", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\logout.png")));
		btnBack.setBackground(Color.red);
		b1.add(Box.createHorizontalStrut(5));
		b1.add(btnThoat = new JButton("Thoát", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\exit.png")));
		b1.add(Box.createHorizontalStrut(width - width*82/100));
		b.add(Box.createVerticalStrut(10));
		String[] cols = "Mã Nhân Viên;Tên Nhân Viên;Giới Tính;Số Điện Thoại;CMND;Ngày Sinh;Địa Chỉ".split(";");
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
		int col[] = {10,30,10,10,10,10,120};
		for(int i=0;i<7;i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(col[i]*4);

		}
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<7;i++) {
			table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
		}
		JTableHeader header1 = table.getTableHeader();
		header1.setBackground(Color.CYAN);
		header1.setOpaque(false);
		// xét cứng cột
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane scroll = new JScrollPane(table);
		b.add(scroll);
		b.add(Box.createVerticalStrut(10));
		JPanel pSouth = new JPanel();
		JPanel pWest2 = new JPanel();
		JPanel pCenter2 = new JPanel();
		pSouth.setLayout(new BorderLayout());
		pWest2.add(b1 = Box.createHorizontalBox());
		b1.add(Box.createHorizontalStrut(50));
		cmbTim = new JComboBox<>();
		cmbTim.addItem("Tìm theo mã nhân viên");
		cmbTim.addItem("Tìm theo tên nhân viên");
//		cmbTim.addItem("Tìm theo ngày ký hợp đồng");
//		b1.add(cmbTim);
//		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtNhap);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(btnTim = new JButton("Tìm", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\find.png")));
		b1.add(Box.createHorizontalStrut(50));
		pSouth.add(pWest2, BorderLayout.WEST);
		// add(pSouth, BorderLayout.SOUTH);

		pCenter2.add(b1 = Box.createHorizontalBox());
		b1.add(btnThem = new JButton("Thêm", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\add.png")));
		b1.add(Box.createHorizontalStrut(10));
		b1.add(btnXoa = new JButton("Xoá", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\delete.png")));
		b1.add(Box.createHorizontalStrut(10));
		b1.add(btnSua = new JButton("Chỉnh Sửa", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\edit.png")));
//		b1.add(Box.createHorizontalStrut(10));
//		b1.add(btnLuu = new JButton("Lưu", new ImageIcon(".\\image\\save.png")));
		pSouth.add(pCenter2, BorderLayout.CENTER);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pWest2, pCenter2);
		pSouth.add(splitPane);
		b.add(pSouth, BorderLayout.SOUTH);// 1550

//ban đầu 1520,760 || 1000,600
		b.setPreferredSize(new Dimension(width-width*20/100,height-height*30/100));
		
		lblMaNhanVien.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblTenNhanVien.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblgioitinh.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lbDiaChi.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lbNgaySinh.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblSoDienThoai.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lbCMND.setFont(new Font("Times New Roman", Font.BOLD, 15));

		btnPass.addActionListener(this);
		btnThoat.addActionListener(this);
//		btnLuu.addActionListener(this);
		btnThem.addActionListener(this);
		btnTim.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		table.addMouseListener(this);
		btnBack.addActionListener(this);
		btnSua.addActionListener(this);

		// Database.getInstance().connect();
		find();
		enableText(false);
		btnSua.setEnabled(false);
//		btnLuu.setEnabled(false);
		btnPass.setEnabled(false);
		btnXoa.setEnabled(false);
		txtMaNV.setEnabled(false);
		// setVisible(true);

		layGiaTrichoDienTuDong();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src.equals(btnBack)) {
			new GUIMenu(GUIMenu.ktrLogin);
			child.setVisible(false);
		} else if (src.equals(btnThoat)) {
			thoat();
		} else if (src.equals(btnThem)) {
//			passWord = null;
			if (flagBtnThem)
				them1();
			else
				them2();
		} else if (src.equals(btnXoaTrang)) {
			xoaRong();
//		} else if (src.equals(btnLuu)) {

		} else if (src.equals(btnXoa)) {
			xoa();
		} else if (src.equals(btnTim)) {
			tim();
		} else if (src.equals(btnSua)) {
			chinhSua();
		} else if (src.equals(btnPass)) {
			GUIDangKyMatKhau Pass = new GUIDangKyMatKhau(
					Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
			Pass.setVisible(true);
		}
	}

	private void updateTableData() {
		xoaDLBang();
		QuanLyNhanVien ds = new QuanLyNhanVien();
		List<NhanVien> list = ds.docTuBang();
		int count_temp = 0;
		String phuNu;
		for (NhanVien nv : list) {
			if (nv.getGioiTinh() == 1) {
				phuNu = "Nữ";
			} else
				phuNu = "Nam";
			String[] rowData = { nv.getId() + "", nv.getHoTen(), phuNu, nv.getsDT(), nv.getcMND(),
					dinhDangNgay.format(nv.getNgaySinh()), nv.getDiaChi() };
			count_temp = nv.getId();
			tableModel.addRow(rowData);
		}
		table.setModel(tableModel);
		if (count_temp > count)
			count = count_temp;
	}

	public void xoaDLBang() {
		for (int i = tableModel.getRowCount() - 1; i >= 0; i--)
			tableModel.removeRow(i);
	}

	public void enableText(boolean b) {
		txtTenNhanVien.setEnabled(b);
		dcNgaySinh.setEnabled(b);
		txtSoDienThoai.setEnabled(b);
		txtDiaChi.setEnabled(b);
//		cmbGioitinh.setSelectedItem(null);
		cmbGioitinh.setEnabled(b);
		txtCMND.setEnabled(b);

		List<JTextField> txt = Arrays.asList(txtTenNhanVien, txtCMND, txtDiaChi, txtSoDienThoai, txtMaNV);
		setDisabledText(txt);
	}

	public void setDisabledText(List<JTextField> txt) {
		txt.forEach(x -> {
			x.setDisabledTextColor(Color.BLACK);
		});
	}

	private void thoat() {
		if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn thoát?", "Cảnh Báo",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	public void xoaRong() {
		cmbGioitinh.setSelectedItem(null);
		txtMaNV.setText("");
		txtDiaChi.setText("");
		txtNhap.setText("");
		txtSoDienThoai.setText("");
		txtTenNhanVien.setText("");
		txtCMND.setText("");
		dcNgaySinh.setDate(null);
		btnSua.setEnabled(false);
		btnXoa.setEnabled(false);
//		btnLuu.setEnabled(false);
		table.clearSelection();
		// enableText(false);
		// updateTableData();

		// xoaTableData();

	}

	private void them1() {
		xoaRong();
		btnSua.setEnabled(true);
		btnXoa.setEnabled(true);
//		btnLuu.setEnabled(true);
		enableText(true);
		btnPass.setEnabled(true);
		txtMaNV.setEnabled(false);
		flagBtnThem = false;

	}

//chưa xử lý dữ liệu vào
	private void them2() {
		try {
			QuanLyNhanVien ql = new QuanLyNhanVien();
			ArrayList<NhanVien> list = new ArrayList<NhanVien>();
			if (txtMaNV.getText().equals("")) {
				if (txtTenNhanVien.getText().equals("") || txtCMND.getText().equals("")
						|| txtDiaChi.getText().equals("") || txtSoDienThoai.getText().equals("")
						|| cmbGioitinh.getSelectedItem() == null || dcNgaySinh.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Thêm không thành công. Bạn chưa nhập đủ thông tin",
							"Thông báo", 0, new ImageIcon(".\\image\\error.png"));
					throw new Exception();
				} else {
					String mess = "Bạn có chắc chắn muốn thêm nhân viên có thông tin: \n\t\tTên nhân viên: "
							+ txtTenNhanVien.getText() + "\n\t\tCMND: " + txtCMND.getText() + "\n\t\tSDT: "
							+ txtSoDienThoai.getText() + "\n\t\tNgày sinh: " + dinhDangNgay.format(dcNgaySinh.getDate())
							+ "\n\t\tĐịa chỉ: " + txtDiaChi.getText() + "\n\t\tGiới tính: "
							+ cmbGioitinh.getSelectedItem().toString() + " không?";
					if (JOptionPane.showConfirmDialog(null, mess, "Thông báo", JOptionPane.YES_NO_OPTION, 0,
							new ImageIcon(".\\image\\question.png")) == JOptionPane.YES_OPTION) {
						int gioiTinh = 0;
						if (cmbGioitinh.getSelectedItem().toString().equals("Nữ"))
							gioiTinh = 1;

						SimpleDateFormat ngaySinh = new SimpleDateFormat("yyyy-MM-dd");

						ql.themDuLieu(txtTenNhanVien.getText(), gioiTinh, txtCMND.getText(), txtSoDienThoai.getText(),
								ngaySinh.format(dcNgaySinh.getDate()), txtDiaChi.getText());
						JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", 0,
								new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\success.png"));
						find();

						layGiaTrichoDienTuDong();
						flagBtnThem = true;

					} else
						JOptionPane.showMessageDialog(null, "Thêm không thành công. Vì bạn chọn không thêm.",
								"Thông báo", 0, new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\error.png"));
				}
			} else {
				for (int i = 0; i < table.getRowCount(); i++)
					if (Integer.parseInt(txtMaNV.getText()) == Integer
							.parseInt(table.getValueAt(i, 0).toString())) {
						JOptionPane.showMessageDialog(null, "Thêm không thành công.", "Thông báo", 0,
								new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\error.png"));
						throw new Exception();
					}

				JOptionPane.showMessageDialog(null, "Thêm không thành công. Mã nhân viên không được nhập.", "Thông báo",
						0, new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\error.png"));
			}

		} catch (Exception e) {
		}
	}

	private boolean ktrDuLieu(String ten, String sDT, String moTa, String gioiTinh, String passWord) {
		/*
		 * if (txtTenNhanVien.getText().trim().equals("")) {
		 * JOptionPane.showMessageDialog(this, "Điền Tên");
		 * txtTenNhanVien.requestFocus(); return false; } if
		 * (txtSoDienThoai.getText().trim().equals("")) {
		 * JOptionPane.showMessageDialog(this, "Điền Số Điện Thoại");
		 * txtSoDienThoai.requestFocus(); return false; } else if
		 * (!txtSoDienThoai.getText().trim().matches("[0-9]{10}")) {
		 * JOptionPane.showMessageDialog(this, "Số Điện Thoại Phải Gồm 10 Chữ Số");
		 * txtSoDienThoai.requestFocus(); return false; }
		 * 
		 * if (cmbGioitinh.getSelectedItem() == null) {
		 * JOptionPane.showMessageDialog(this, "Chọn Giới Tính"); return false; }
		 */
		if (passWord == null) {
			JOptionPane.showMessageDialog(this, "Bạn Chưa Cài PassWord Cho Nhân Viên");
			return false;
		}
		return true;
	}

//Xong
	private void xoa() {
		QuanLyNhanVien ql = new QuanLyNhanVien();
		// TODO Auto-generated method stub System.err.println(table.getSelectedRow());
		if (table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Chọn dòng cần xóa", "Error", 0, new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\error.png")); // System.err.println(table.getSelectedRow());
		} else {
			if (JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn xóa dòng này?", "Thông báo",
					JOptionPane.YES_NO_OPTION, 0, new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\question.png")) == JOptionPane.YES_OPTION) {
				boolean ktraDel = ql.delete(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
				if (ktraDel) {
					JOptionPane.showMessageDialog(null, "Xóa thành công", "Thông báo", 0,
							new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\success.png"));
					ql.delPass(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
					updateTableData();
				} else
					JOptionPane.showMessageDialog(null, "Xóa không thành công", "Thông báo", 0,
							new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\error.png"));
			} else
				JOptionPane.showMessageDialog(null, "Xóa không thành công", "Thông báo", 0,
						new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\error.png"));
		}

	}

	private void tim() {
		for (int i = tableModel.getRowCount() - 1; i >= 0; i--)
			tableModel.removeRow(i);

		find();

		txtNhap.setText("");
		txtNhap.requestFocus();
	}

	private void chinhSua() {
		int chonTable = table.getSelectedRow();
		QuanLyNhanVien ql = new QuanLyNhanVien();
		if (chonTable == -1)
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn nhân viên để chỉnh sửa", "Error",
					JOptionPane.ERROR_MESSAGE);
		else {
			String mess = "Bạn có muốn chỉnh sửa thông tin của nhân viên có mã: "
					+ table.getValueAt(table.getSelectedRow(), 0).toString();
			if (txtCMND.getText().equals(table.getValueAt(chonTable, 4))
					&& txtDiaChi.getText().equals(table.getValueAt(chonTable, 6))
					&& txtSoDienThoai.getText().equals(table.getValueAt(chonTable, 3))
					&& cmbGioitinh.getSelectedItem().equals(table.getValueAt(chonTable, 2))
					&& dcNgaySinh.getDate().equals(table.getValueAt(chonTable, 5)))
				JOptionPane.showMessageDialog(null, "Cập nhật không thành công. Vì bạn chưa sửa thông tin", "Error", 0,
						new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\error.png"));
			else {
				if (JOptionPane.showConfirmDialog(null, mess, "Sửa thông tin nhân viên", JOptionPane.YES_NO_OPTION, 0,
						new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\question.png")) == JOptionPane.YES_OPTION) {

					int gioiTinh = 0;
					if (cmbGioitinh.getSelectedItem().equals("Nữ"))
						gioiTinh = 1;
					SimpleDateFormat ngay = new SimpleDateFormat("yyyy-MM-dd");
					ql.updateNV(Integer.parseInt(txtMaNV.getText()), txtTenNhanVien.getText(), gioiTinh,
							txtSoDienThoai.getText(), txtCMND.getText(), ngay.format(dcNgaySinh.getDate()),
							txtDiaChi.getText());

					JOptionPane.showMessageDialog(null, "Đã cập nhật thành công", "Thông báo", 0,
							new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\success.png"));

				} else
					JOptionPane.showMessageDialog(null, "Cập nhật không thành công. Vì bạn chọn No", "Error", 0,
							new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\error.png"));
			}
			xoaDLBang();
			find();
			table.addRowSelectionInterval(chonTable, chonTable);

		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generatedmethod stub
		Object src = e.getSource();
		if (src.equals(table)) {
			btnSua.setEnabled(true);
			btnXoa.setEnabled(true);
			btnPass.setEnabled(true);
			enableText(true);
			txtMaNV.setEditable(false);
			int index = table.getSelectedRow();
			hienDataComponent(index);
			flagBtnThem = false;
		}
	}

	private void hienDataComponent(int row) {
		if (row != -1) {
			txtMaNV.setText(table.getValueAt(row, 0).toString());
			txtTenNhanVien.setText(table.getValueAt(row, 1).toString());
			String txtCombo = table.getValueAt(row, 2).toString();
			cmbGioitinh.setSelectedItem(txtCombo);

			txtSoDienThoai.setText(table.getValueAt(row, 3).toString());
			txtCMND.setText(table.getValueAt(row, 4).toString());
			java.util.Date ngaySinh = null;
			try {
//				String[] ngay=table.getValueAt(row, 5).toString().split("/");
//				for(int i =0;i<ngay.length;i++)
//					System.out.println(ngay[i]);
//				LocalDate ngay=LocalDate.of(year, month, dayOfMonth)
				ngaySinh = new SimpleDateFormat("yyyy-MM-dd").parse(table.getValueAt(row, 5).toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dcNgaySinh.setDate(ngaySinh);
			// System.out.println(ngaySinh);
			txtDiaChi.setText(table.getValueAt(row, 6).toString());
			// btnPass.setEnabled(false);
		}
	}

	public void find() {
		QuanLyNhanVien ql = new QuanLyNhanVien();
		List<NhanVien> list = new ArrayList<NhanVien>();
		if (txtNhap.getText().equals(""))
			list = ql.docTuBang();
		else {
			ArrayList<NhanVien> listTimTheoTen = new ArrayList<NhanVien>();

			int maNV = -1;
			try {
				maNV = Integer.parseInt(txtNhap.getText());
				throw new Exception();
			} catch (Exception e) {
			}
			list = ql.timTheoMa(maNV);
			listTimTheoTen = ql.timTheoTen(txtNhap.getText());
			for (NhanVien nv : listTimTheoTen) {
				if (nv.getId() != maNV)
					list.add(nv);
			}
		}
		xoaDLBang();

		for (NhanVien x : list) {
			String gioiTinh = "";
			if (x.getGioiTinh() == 1)
				gioiTinh = "Nữ";
			else
				gioiTinh = "Nam";
			Object[] rowData = { x.getId(), x.getHoTen(), gioiTinh, x.getsDT(), x.getcMND(), x.getNgaySinh(),
					x.getDiaChi() };
			tableModel.addRow(rowData);
		}

	}

	public void layGiaTrichoDienTuDong() {
		for (String a : values)
			values.remove(a);
		for (int i = 0; i < table.getRowCount(); i++) {
			values.add(table.getValueAt(i, 0).toString());
			values.add(table.getValueAt(i, 1).toString());
//			values.add(table.getValueAt(i, 2).toString());
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void actionTim(String maNV,String cmnd,String ngaySinh,String tenNV,String sDT,String gioiTinh,String diaChi) {
		//System.out.println("648: "+maNV+" " +cmnd+" "+ngaySinh +" "+tenNV + " "+sDT + " " + gioiTinh + " "+diaChi);
		try {
			
			dsNV = new QuanLyNhanVien();
			ArrayList<NhanVien> list = dsNV.timKiem(maNV,cmnd,ngaySinh,tenNV,sDT, gioiTinh,diaChi);
			if(list.size()==0) {
				JOptionPane.showMessageDialog(this, "Không Tìm Thấy");
			}
			else updateTableDataSearch(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void updateTableDataSearch(ArrayList<NhanVien> list) {
		xoaDLBang();
		int count_temp = 0;
		String phuNu;
		for (NhanVien nv : list) {
			if (nv.getGioiTinh() == 1) {
				phuNu = "Nữ";
			} else
				phuNu = "Nam";
			String[] rowData = { nv.getId() + "", nv.getHoTen(), phuNu, nv.getsDT(), nv.getcMND(),
					dinhDangNgay.format(nv.getNgaySinh()), nv.getDiaChi() };
			count_temp = nv.getId();
			tableModel.addRow(rowData);
		}
		table.setModel(tableModel);
		if (count_temp > count)
			count = count_temp;
	}
}

//Tạo autoComplete Text
class LookAheadTextField extends JTextField {
	public LookAheadTextField() {
		this(0, null);
	}

	public LookAheadTextField(int columns) {
		this(columns, null);
	}

	public LookAheadTextField(int columns, TextLookAhead lookAhead) {
		super(columns);
		setLookAhead(lookAhead);
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// Remove any existing selection
				setCaretPosition(getDocument().getLength());
			}
		});
		addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent evt) {
			}

			public void focusLost(FocusEvent evt) {
				if (evt.isTemporary() == false) {
					// Remove any existing selection
					setCaretPosition(getDocument().getLength());
				}
			}
		});
	}

	public void setLookAhead(TextLookAhead lookAhead) {
		this.lookAhead = lookAhead;
	}

	public TextLookAhead getLookAhead() {
		return lookAhead;
	}

	public void replaceSelection(String content) {
		super.replaceSelection(content);

		if (isEditable() == false || isEnabled() == false) {
			return;
		}

		Document doc = getDocument();
		if (doc != null && lookAhead != null) {
			try {
				String oldContent = doc.getText(0, doc.getLength());
				String newContent = lookAhead.doLookAhead(oldContent);
				if (newContent != null) {
					// Substitute the new content
					setText(newContent);

					// Highlight the added text
					setCaretPosition(newContent.length());
					moveCaretPosition(oldContent.length());
				}
			} catch (BadLocationException e) {
				// Won't happen
			}
		}
	}

	protected TextLookAhead lookAhead;

	// The TextLookAhead interface
	public interface TextLookAhead {
		public String doLookAhead(String key);
	}
}

class StringArrayLookAhead implements LookAheadTextField.TextLookAhead {
	public StringArrayLookAhead() {
		values = new ArrayList<String>();
	}

	public StringArrayLookAhead(ArrayList<String> values2) {
		this.values = values2;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public String doLookAhead(String key) {
		int length = values.size();

		// Look for a string that starts with the key
		for (int i = 0; i < length; i++) {
			if (values.get(i).startsWith(key) == true) {
				return values.get(i);
			}
		}

		// No match found - return null
		return null;
	}

	protected ArrayList<String> values;
}