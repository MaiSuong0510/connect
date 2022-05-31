package uiQuanLyPhong;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import javax.swing.text.*;

import entities.KhachHang;
import entities.PhieuDatPhong;
import entities.Phong;
import services.QuanLyKhachHang;
import services.QuanLyPhong;
import services.QuanLyThueTra;
import uiLogin.GUIMenu;


public class GUIQLPhong extends JPanel implements MouseListener, ActionListener {
	JComboBox<String> cmbLoaiPhong;
	private DefaultTableModel tableModel;
	private JTable table;
	JButton btnThem, btnSua, btnLuu, btnXoa, btnLamMoi, btnBack;
	JToolBar toolBar;
	private Frame child;
	private JTextField textfieldGiaPhong, textfieldMaPhong, textfieldGhiChu;
	private JButton btnTim;
	protected ArrayList<String> values = new ArrayList<String>();
	private StringArrayLookAhead lookAhead = new StringArrayLookAhead(values);
	public LookAheadTextField textSearch = new LookAheadTextField(4, lookAhead);
	private boolean flagThem = true;
	private SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");

	public GUIQLPhong(Frame parent) {
		child = parent;
		Box bc, b1, b2;
		bc = Box.createHorizontalBox();
		add(bc);
		bc.add(b1 = Box.createVerticalBox());
		b1.add(Box.createVerticalStrut(50));
		b1.add(b2 = Box.createHorizontalBox());
		b2.add(Box.createHorizontalStrut(20));
		b2.add(new JLabel("Mã Phòng:  "));
		b2.add(Box.createHorizontalStrut(20));
		b2.add(textfieldMaPhong = new JTextField(20));
		b2.add(Box.createHorizontalStrut(20));
		b2.add(new JLabel("Loại Phòng: "));
		b2.add(Box.createHorizontalStrut(20));
		cmbLoaiPhong = new JComboBox<String>();

		cmbLoaiPhong.addItem("Giường đôi");
		cmbLoaiPhong.addItem("VIP");
		cmbLoaiPhong.addItem("Giường đơn");
		cmbLoaiPhong.addItem("Thường");

		b2.add(cmbLoaiPhong);
		b2.add(Box.createHorizontalStrut(20));
		b1.add(Box.createVerticalStrut(20));
		b1.add(b2 = Box.createHorizontalBox());
		b2.add(Box.createHorizontalStrut(20));
		b2.add(new JLabel("Giá Phòng: "));
		b2.add(Box.createHorizontalStrut(20));
		b2.add(textfieldGiaPhong = new JTextField(10));
		b2.add(Box.createHorizontalStrut(20));
		b1.add(Box.createVerticalStrut(20));
		b1.add(b2 = Box.createHorizontalBox());
		b2.add(Box.createHorizontalStrut(20));
		b2.add(new JLabel("Ghi Chú: "));
		b2.add(Box.createHorizontalStrut(20));
		b2.add(textfieldGhiChu = new JTextField(10));
		b2.add(Box.createHorizontalStrut(20));
		b1.add(Box.createVerticalStrut(20));
		b1.add(b2 = Box.createHorizontalBox());
		b2.add(Box.createHorizontalStrut(20));

//		b2.add(cmbTim);
//		cmbTim.setMaximumSize(new Dimension(200, 40));
		b2.add(Box.createHorizontalStrut(10));
		b2.add(textSearch);
		b2.add(Box.createHorizontalStrut(10));
		b2.add(btnTim = new JButton("Tìm", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\find.png")));
		b2.add(Box.createHorizontalStrut(20));
		b1.add(Box.createVerticalStrut(20));
		b1.add(b2 = Box.createHorizontalBox());
		b2.add(Box.createHorizontalStrut(250));
		b2.add(btnLamMoi = new JButton("Làm Mới", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\reload.png")));
		b2.add(Box.createHorizontalStrut(20));

		b1.add(Box.createVerticalStrut(150));
		b1.add(b2 = Box.createHorizontalBox());
		b2.add(btnThem = new JButton("Thêm", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\add.png")));
		b2.add(Box.createHorizontalStrut(10));
//		b2.add(btnXoa = new JButton("Xoá", new ImageIcon(".\\image\\delete.png")));
		b2.add(Box.createHorizontalStrut(10));
		b2.add(btnSua = new JButton("Chỉnh Sửa", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\edit.png")));
//		b2.add(btnLuu = new JButton("Lưu", new ImageIcon(".\\image\\save.png")));

		b1.add(Box.createVerticalStrut(100));
		b1.add(b2 = Box.createHorizontalBox());
		b2.add(btnBack = new JButton("Lùi Về Trang Trước", new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\logout.png")));
		btnBack.setBackground(Color.red);
		b1.add(Box.createVerticalStrut(100));
		TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED),
				"Tác Vụ Phòng");
		b1.setBorder(title);
// Table
		bc.add(b1 = Box.createVerticalBox());
		String[] cols = "Mã Phòng;Loại Phòng;Giá Phòng;Ghi Chú;Tình Trạng Phòng;Tên Khách Hàng;CMND;Ngày Sinh;Giới Tính"
				.split(";");
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
		scroll.setPreferredSize(new Dimension(750, 50));
		b1.add(scroll);
		find();
// ban đầu 1520
		bc.setPreferredSize(new Dimension(1000, 700));


//Khi ở chế độ thường các khung text và combobox bị khóa		
		textfieldGiaPhong.setEditable(false);
		textfieldGhiChu.setEditable(false);
		textfieldMaPhong.setEditable(false);
		cmbLoaiPhong.setEnabled(false);
//		btnThem.setEnabled(false);
		btnLamMoi.setEnabled(false);
		btnSua.setEnabled(false);
//		btnXoa.setEnabled(false);
		cmbLoaiPhong.setSelectedItem(null);

		layGiaTrichoDienTuDong();

// Đăng ký Sự kiện cho các button
		btnLamMoi.addActionListener(this);
		btnBack.addActionListener(this);
		btnTim.addActionListener(this);
		btnThem.addActionListener(this);
//		btnXoa.addActionListener(this);
		btnSua.addActionListener(this);
		table.addMouseListener(this);
//		btnLuu.addActionListener(new luu());

	}


	public void lamMoi() {
		for (int i = tableModel.getRowCount() - 1; i >= 0; i--)
			tableModel.removeRow(i);
		textfieldMaPhong.setText("");
		cmbLoaiPhong.setSelectedItem("");
		textfieldGhiChu.setText("");
		textfieldGiaPhong.setText("");
		textSearch.setText("");
		find();
	}

	public void goBack() {
		new GUIMenu(GUIMenu.ktrLogin);
		child.setVisible(false);
	}

	public void Tim() {
		for (int i = tableModel.getRowCount() - 1; i >= 0; i--)
			tableModel.removeRow(i);
		find();
		textSearch.setText("");
		textSearch.requestFocus();

	}

	public void them1() {
		textfieldGhiChu.setText("");
		textfieldGiaPhong.setText("");
		textfieldMaPhong.setText("");

		btnSua.setEnabled(true);
//		btnXoa.setEnabled(true);
		textfieldGhiChu.setEditable(true);
		textfieldGiaPhong.setEditable(true);
		cmbLoaiPhong.setEnabled(true);

		cmbLoaiPhong.setSelectedItem(null);
		flagThem = false;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Object src = e.getSource();
		if (src.equals(table)) {
			btnThem.setEnabled(true);
			btnLamMoi.setEnabled(true);
			btnSua.setEnabled(true);
//			btnXoa.setEnabled(true);
			cmbLoaiPhong.setEnabled(true);

			textfieldGhiChu.setEditable(true);
			textfieldGiaPhong.setEditable(true);
			cmbLoaiPhong.setEditable(true);
			flagThem = false;

			hienDLSangTextfield(table.getSelectedRow());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src.equals(btnLamMoi))
			lamMoi();
		else if (src.equals(btnThem)) {
			if (flagThem)
				them1();
			else
				them2();
		} else if (src.equals(btnSua))
			chinhSua();
		else if (src.equals(btnTim))
			Tim();
		else if (src.equals(btnBack))
			goBack();

	}

	
	public void them2() {
		try {
			QuanLyPhong ql = new QuanLyPhong();

			if (textfieldMaPhong.getText().equals("")) {
				if (textfieldGiaPhong.getText().toString().equals("") || textfieldGhiChu.getText().toString().equals("")
						|| cmbLoaiPhong.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Thêm không thành công. Bạn chưa nhập đủ thông tin",
							"Thông báo", 0, new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\error.png"));
					throw new Exception();
				} else {
					String mess = "Bạn có chắc chắn muốn thêm phòng có thông tin:\nLoại phòng: \""
							+ cmbLoaiPhong.getSelectedItem().toString() + "\", \nGiá phòng: \""
							+ textfieldGiaPhong.getText().toString() + "\", \nGhi chú: \""
							+ textfieldGhiChu.getText().toString() + "\" không?";

					if (JOptionPane.showConfirmDialog(null, mess, "Thêm phòng", JOptionPane.YES_NO_OPTION, 0,
							new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\question.png")) == JOptionPane.YES_OPTION) {

						ql.them(cmbLoaiPhong.getSelectedItem().toString(),
								Integer.parseInt(textfieldGiaPhong.getText().toString()), textfieldGhiChu.getText());
						JOptionPane.showMessageDialog(null, "Đã thêm thành công", "Thông báo", 0,
								new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\success.png"));

						find();
						flagThem = true;
					} else
						JOptionPane.showMessageDialog(null, "Thêm không thành công. Vì bạn chọn không thêm.",
								"Thông báo", 0, new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\error.png"));
				}
			} else {
				for (int i = 0; i < table.getRowCount(); i++)
					if (Integer.parseInt(textfieldMaPhong.getText()) == Integer
							.parseInt(table.getValueAt(i, 0).toString())) {
						JOptionPane.showMessageDialog(null, "Thêm không thành công.", "Thông báo", 0,
								new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\error.png"));
						throw new Exception();
					}

				JOptionPane.showMessageDialog(null, "Thêm không thành công. Mã phòng không được nhập.", "Thông báo", 0,
						new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\error.png"));

			}
		} catch (Exception e) {
		}
	}

	public void actionTim(String maPhong, String ghiChu, String gia, String loai, String ngayDen, String ngayDi) {
		try {
			QuanLyPhong qlp = new QuanLyPhong();
			//String maDV, String tenDV, String donVi, String loai, String soLuongCo, String giaDV
			ArrayList<Phong> list = qlp.timKiemTheoThuocTinh(maPhong, ghiChu, gia, loai, ngayDen, ngayDi);
			if(list.size()==0) {
				JOptionPane.showMessageDialog(this, "Không Tìm Thấy");
			}
			else updateTableData1(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void updateTableData1(ArrayList<Phong> list) {
		xoaTableData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(Phong p : list) {
			//Mã Phòng;Loại Phòng;Giá Phòng;Ghi Chú;Tình Trạng Phòng;Tên Khách Hàng;CMND;Ngày Sinh;Giới Tính
			String[] rowData = {p.getId()+"",p.getLoaiPhong(),p.getGiaPhong()+"",p.getGhiChu(),"","","","",""};
			tableModel.addRow(rowData);
		}
		table.setModel(tableModel);
	}
	private void xoaTableData() {
		while(tableModel.getRowCount()>0) {
			tableModel.removeRow(0);
		}
	}
	public void chinhSua() {

		int chonTable = table.getSelectedRow();
		QuanLyPhong ql = new QuanLyPhong();
		if (chonTable == -1)
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn phòng để chỉnh sửa", "Error", 0,
					new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\error.png"));
		else {
			String mess = "Bạn có muốn cập nhật lại giá trị tại phòng có mã phòng: "
					+ table.getValueAt(table.getSelectedRow(), 0).toString();

			if (JOptionPane.showConfirmDialog(null, mess, "Sửa thông tin phòng", JOptionPane.YES_NO_OPTION, 0,
					new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\question.png")) == JOptionPane.YES_OPTION) {

				// Bắt lỗi khi chưa thay đổi giá trị
				String giaPhong = table.getValueAt(chonTable, 2).toString();
				String[] tien = giaPhong.split(",");
				String money = "";
				for (String a : tien)
					money += a;
				String[] chuyenTienSangtxt = money.split(" ");

				if (cmbLoaiPhong.getSelectedItem().toString().equals(table.getValueAt(chonTable, 1))
						&& textfieldGhiChu.getText().equals(table.getValueAt(chonTable, 3))
						&& Integer.parseInt(textfieldGiaPhong.getText()) == Integer.parseInt(chuyenTienSangtxt[0]))
					JOptionPane.showMessageDialog(null, "Cập nhật không thành công. Vì bạn chưa sửa thông tin", "Error",
							0, new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\error.png"));
				else {
					ql.updatePhong(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()) - 100,
							cmbLoaiPhong.getSelectedItem().toString(), Integer.parseInt(textfieldGiaPhong.getText()),
							textfieldGhiChu.getText());

					JOptionPane.showMessageDialog(null, "Đã cập nhật thành công", "Thông báo", 0,
							new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\success.png"));
				}
			} else
				JOptionPane.showMessageDialog(null, "Cập nhật không thành công. Vì bạn chọn No", "Error", 0,
						new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\error.png"));

			xoaDLBang();
			find();
			table.addRowSelectionInterval(chonTable, chonTable);

		}

	}

	public void hienDLSangTextfield(int row) {
		if (row != -1) {
			String maPhong = table.getValueAt(row, 0) + "";
			textfieldMaPhong.setText(maPhong);

			String giaPhong = table.getValueAt(row, 2) + "";
			String[] tien = giaPhong.split(",");
			String money = "";
			for (String a : tien)
				money += a;
			String[] chuyenTienSangtxt = money.split(" ");
			textfieldGiaPhong.setText(chuyenTienSangtxt[0]);

			String ghiChu = table.getValueAt(row, 3) + "";
			textfieldGhiChu.setText(ghiChu);

			String loaiPhong = table.getValueAt(row, 1) + "";
			cmbLoaiPhong.setSelectedItem(loaiPhong);
		}
	}

	public void xoaDLBang() {
		for (int i = tableModel.getRowCount() - 1; i >= 0; i--)
			tableModel.removeRow(i);
	}

	public void find() {
		QuanLyThueTra qlPhieu = new QuanLyThueTra();
		QuanLyPhong qlPhong = new QuanLyPhong();
		ArrayList<PhieuDatPhong> dsDatPhong = qlPhieu.layDLPhong_KH();
		KhachHang kh = new KhachHang();

		ArrayList<Phong> dsPhong = new ArrayList<Phong>();
		if (textSearch.getText().equals("")) {
			dsPhong = qlPhong.docTuBang();
		} else {
			String[] tien = textSearch.getText().split(",");
			String money = "";
			for (String a : tien)
				money += a;
			String[] chuyenTienSangtxt = money.split(" ");
			
			
			ArrayList<Phong> timTheoGia;
			ArrayList<Phong> timTheoLoai = qlPhong.timTheoLoaiPhong(textSearch.getText());
			ArrayList<Phong> timTheoTinhTrang = qlPhong.timTheoTinhTrangPhong(textSearch.getText());
			int maPhong = -1;
			int giaTien=-1;
			try {
				maPhong = Integer.parseInt(textSearch.getText()) - 100;
				giaTien=Integer.parseInt(chuyenTienSangtxt[0]);
				throw new Exception();
			} catch (Exception e) {
			}
			dsPhong = qlPhong.timTheoMa(maPhong);
			timTheoGia = qlPhong.timTheoGiaPhong(giaTien);

			for (Phong p : timTheoGia) {
				if (p.getId() != maPhong) {
					dsPhong.add(p);

				}
			}
			for (Phong p : timTheoLoai) {
				if (p.getId() != maPhong)
					dsPhong.add(p);
			}
			for (Phong p : timTheoTinhTrang) {
				if (p.getId() != maPhong)
					dsPhong.add(p);
			}

		}
		xoaDLBang();
		for (Phong p : dsPhong) {
			String tinhTrangPhong = "";
			if (p.getTinhTrangPhong() == 0) {
				tinhTrangPhong = "Trống";
				kh = new KhachHang(-1, "", "", null, -1, "");
			}
			else if (p.getTinhTrangPhong() == 2) {
				tinhTrangPhong = "Đến hạn Checkin";
				for (PhieuDatPhong phieu : dsDatPhong)
					if (p.getId() == phieu.getPhong().getId())
						kh = phieu.getKhachHang();
				//System.out.println(kh.getId());

			} else if (p.getTinhTrangPhong() == 3) {
				tinhTrangPhong = "Đang sử dụng";
				for (PhieuDatPhong phieu : dsDatPhong)
					if (p.getId() == phieu.getPhong().getId())
						kh = phieu.getKhachHang();


			} else if (p.getTinhTrangPhong() == 4) {
				tinhTrangPhong = "Đến hạn Checkout";
				for (PhieuDatPhong phieu : dsDatPhong)
					if (p.getId() == phieu.getPhong().getId())
						kh = phieu.getKhachHang();

			}
			DecimalFormat money = new DecimalFormat("#,###.### Đồng");
			String giaPhong = money.format(p.getGiaPhong());
			String gioiTinh = "Nam";

			if (kh.getGioiTinh() == 1)
				gioiTinh = "Nữ";
			if (kh.getMa() != -1) {
				String[] rowData = { p.getId() + 100 + "", p.getLoaiPhong(), giaPhong, p.getGhiChu(), tinhTrangPhong,
						kh.getHoTen(), kh.getcMND(), dinhDangNgay.format(kh.getNgaySinh()), gioiTinh };
				tableModel.addRow(rowData);
			} else {
				String[] rowData = { p.getId() + 100 + "", p.getLoaiPhong(), giaPhong, p.getGhiChu(), tinhTrangPhong,
						"null", "null", "null", "null" };
				tableModel.addRow(rowData);
			}
		}

	}

	public void layGiaTrichoDienTuDong() {
		for (int i = 0; i < table.getRowCount(); i++) {
			values.add(table.getValueAt(i, 0).toString());
			values.add(table.getValueAt(i, 1).toString());
			values.add(table.getValueAt(i, 2).toString());
			values.add(table.getValueAt(i, 4).toString());
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

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

}

// Tạo autoComplete Text
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