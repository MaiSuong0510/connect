package uiQuanLy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import services.QuanLyKhachHang;
import services.QuanLyNhanVien;

public class GUIDangKyMatKhau extends JFrame implements ActionListener {
	JLabel lbMa, lbPass, lbPass2;
	JButton btnDongY, btnHuy;
	JTextField txtUser;
	JPasswordField txtPass, txtPass2;
	JCheckBox chkHide;
	QuanLyNhanVien ql = new QuanLyNhanVien();

	public GUIDangKyMatKhau(int maNV) {
		super("Đăng Ký Tài Khoản Nhân Viên");
		setSize(500, 250);
		setLocationRelativeTo(null);

		Box bc, b1;
		bc = Box.createVerticalBox();
		add(bc);
		bc.add(Box.createVerticalStrut(10));
		bc.add(b1 = Box.createHorizontalBox());
		b1.add(Box.createHorizontalStrut(50));
		b1.add(lbMa = new JLabel("Mã Nhân Viên:"));
		b1.add(Box.createHorizontalStrut(20));
		b1.add(txtUser = new JTextField(10));
		b1.add(Box.createHorizontalStrut(50));
		bc.add(Box.createVerticalStrut(10));
		bc.add(b1 = Box.createHorizontalBox());
		b1.add(Box.createHorizontalStrut(50));
		b1.add(lbPass = new JLabel("Password:"));
		b1.add(Box.createHorizontalStrut(50));
		b1.add(txtPass = new JPasswordField(10));
		b1.add(Box.createHorizontalStrut(50));
		bc.add(Box.createVerticalStrut(10));
		bc.add(b1 = Box.createHorizontalBox());
		b1.add(Box.createHorizontalStrut(50));
		b1.add(lbPass2 = new JLabel("Nhập Lại Password:"));
		b1.add(Box.createHorizontalStrut(20));
		b1.add(txtPass2 = new JPasswordField(10));
		b1.add(Box.createHorizontalStrut(50));

		bc.add(Box.createVerticalStrut(10));
		bc.add(b1 = Box.createHorizontalBox());
		b1.add(Box.createHorizontalStrut(50));
		b1.add(chkHide = new JCheckBox("Hiện Pass"));
		b1.add(Box.createHorizontalStrut(50));
		b1.add(btnDongY = new JButton("Đồng Ý"));
		b1.add(Box.createHorizontalStrut(50));
		b1.add(btnHuy = new JButton("Huỷ"));
		b1.add(Box.createHorizontalStrut(50));

		bc.add(Box.createVerticalStrut(20));

		lbMa.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lbPass.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lbPass2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		chkHide.addActionListener(this);
		btnDongY.addActionListener(this);
		btnHuy.addActionListener(this);
		txtUser.setEnabled(false);
		txtUser.setText(maNV + "");
		fillPass();
		setVisible(true);	 
	}

	private boolean ktrPass(String pass, String pass2) {
		if (pass.equals("")) {
			JOptionPane.showMessageDialog(this, "Điền mật khẩu");
			txtPass.requestFocus();
			return false;
		} else if (!pass.matches("[a-zA-Z0-9]{8,}")) {
			JOptionPane.showMessageDialog(this, "Mật khẩu phải hơn 8 ký tự.");
			txtPass.requestFocus();
			txtPass.selectAll();
			return false;
		}
		if (pass2.equals("")) {
			JOptionPane.showMessageDialog(this, "Không được để rỗng xác thực.");
			txtPass2.requestFocus();
			return false;
		} else if (!pass2.equals(pass)) {
			JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận phải giống mật khẩu.");
			txtPass2.requestFocus();
			txtPass2.selectAll();
			return false;
		}
		return true;
	}

//	public void getuser(String manv) {
//		txtUser.setText(manv);
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		String pass = txtPass.getText().trim();
		String pass2 = txtPass2.getText().trim();
		if (src.equals(btnDongY)) {
			if (ktrPass(pass, pass2)) {
				if (JOptionPane.showConfirmDialog(null, "Bạn có muốn lưu lại mật khẩu không?", "Thông báo",
						JOptionPane.YES_NO_OPTION, 0,
						new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\question.png")) == JOptionPane.YES_OPTION) {
					ql.addPass(Integer.parseInt(txtUser.getText()), pass);
					JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", 0,
							new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\success.png"));
				} else
					JOptionPane.showMessageDialog(null, "Thêm không thành công.", "Thông báo", 0,
							new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\error.png"));
				this.dispose();
			}
		} else if (src.equals(btnHuy)) {
			if (JOptionPane.showConfirmDialog(null, "Bạn có muốn hủy và thoát không?", "Thông báo",
					JOptionPane.YES_NO_OPTION, 0, new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\question.png")) == JOptionPane.YES_OPTION)
				this.dispose();
			else {
				txtPass.selectAll();
				txtPass.requestFocus();
				txtPass2.setText("");
			}
		} else if (src.equals(chkHide)) {
			if (chkHide.isSelected()) {
				txtPass.setEchoChar((char) 0);
				txtPass2.setEchoChar((char) 0);

			} else {
				txtPass.setEchoChar('\u2022');
				txtPass2.setEchoChar('\u2022');
			}
		}

	}
	public static void main(String[] args) {
		new GUIDangKyMatKhau(1);
	}

	public void fillPass() {
		ArrayList<String> layPass_MaNV = ql.layPass();
		for (String x : layPass_MaNV) {
			String[] pass_maNV = x.split("-");
			if (txtUser.getText().equals(pass_maNV[1])) {
				txtPass.setEditable(false);
				txtPass2.setEditable(false);
				txtPass.setText(pass_maNV[0]);
				txtPass2.setText(pass_maNV[0]);
			}
			
		}
	}

}