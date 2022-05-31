package uiLogin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import services.QuanLyNhanVien;


public class GUILogin extends JFrame implements ActionListener,KeyListener{
	JLabel lbllogOn,lbluseName,lblpassWord,lblh1;
	JTextField txtuserName;
	JPasswordField txtpassWord;
	JButton btnlogOn,btnExit;
	QuanLyNhanVien nv = new QuanLyNhanVien();
	public static int maNV;
	public GUILogin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\This PC\\Desktop\\image\\logo .png"));
		setTitle("Quản Lý Khách Sạn");
		setSize(600,385);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel pNorth;
		add(pNorth = new JPanel(),BorderLayout.NORTH);
			pNorth.setBorder(BorderFactory.createLineBorder(Color.red));
			pNorth.add(lbllogOn = new JLabel("Truy Cập Ứng Dụng"));
			Font font1 = new Font("SansSerif", Font.BOLD, 30);
			lbllogOn.setFont(font1);
		JPanel pTrai;
		add(pTrai = new JPanel(),BorderLayout.WEST);
			pTrai.add(lblh1 = new JLabel(new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\key.png")));
			pTrai.setBorder(BorderFactory.createLineBorder(Color.red));
			
		Box bp =Box.createVerticalBox();
		add(bp,BorderLayout.CENTER);
		bp.add(Box.createVerticalStrut(80));
		bp.setBorder(BorderFactory.createLineBorder(Color.red));
		bp.add(Box.createVerticalStrut(5));
	
		Box b1= Box.createHorizontalBox();
		bp.add(b1);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(lbluseName = new JLabel("Tài Khoản"));
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtuserName = new JTextField());
		b1.add(Box.createHorizontalStrut(50));
		
		bp.add(Box.createVerticalStrut(15));
		Box b2= Box.createHorizontalBox();
		bp.add(b2);
		b2.add(Box.createHorizontalStrut(10));
		b2.add(lblpassWord = new JLabel("Mật Khẩu"));
		b2.add(Box.createHorizontalStrut(13));
		b2.add(txtpassWord = new JPasswordField());
		b2.add(Box.createHorizontalStrut(50));
		
		bp.add(Box.createVerticalStrut(80));
		
		Box b3 = Box.createVerticalBox();
		add(b3,BorderLayout.SOUTH);
		b3.add(Box.createVerticalStrut(10));
		Box bd = Box.createHorizontalBox();
		b3.add(bd);
		bd.add(Box.createHorizontalStrut(100));
		b3.setBorder(BorderFactory.createLineBorder(Color.red));
		bd.add(btnlogOn = new JButton("Đăng Nhập",new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\login.png")));
		btnlogOn.setMaximumSize(getPreferredSize());
		bd.add(Box.createHorizontalStrut(10));
		bd.add(btnExit = new JButton("Thoát",new ImageIcon("C:\\Users\\This PC\\Desktop\\image\\logout.png")));
		btnExit.setMaximumSize(getPreferredSize());
		bd.add(Box.createHorizontalStrut(100));
		b3.add(Box.createVerticalStrut(10));
			
		
		//Đăng Ký Sự Kiện
		btnlogOn.addActionListener(this);
		btnExit.addActionListener(this);
		txtpassWord.addKeyListener(this);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		try {
    		new GUILogin();
        }catch (Exception e) {
        	
		}
	}

	
	//0 là QUản lý - 1 là nhân viên
	public void Logon () {
		try{
			String b= new String(txtpassWord.getPassword());
			byte[] bytes = b.getBytes("UTF-8");
			String encoded = Base64.getEncoder().encodeToString(bytes);
			

			if(Pattern.matches("(Maisuong)",txtuserName.getText()))
				if(b.equalsIgnoreCase("1234567")) {
					GUIMenu guiMenu = new GUIMenu(0);
					dispose();
					return;
				}
				else
					JOptionPane.showMessageDialog(this,"Nhập Sai Mật Khẩu");
			else {
				//if(txtuserName.getText().matches("[0-9]{3,4}"))
					if(nv.timNhanVien(txtuserName.getText(),encoded)){
						
						maNV = Integer.parseInt(txtuserName.getText());
						System.out.println(maNV);
						GUIMenu guiMenu = new GUIMenu(1);
						//System.out.println(encoded);
						dispose();
					}else
						JOptionPane.showMessageDialog(this,"Không Tồn Tại Tài Khoản, Hoặc Sai Mật Khẩu");
				//else
				//	JOptionPane.showMessageDialog(null,"Nhập Sai Định Dạng");
			}
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(this,"Không Được Để Trống");
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnExit))
			System.exit(0);
		if(o.equals(btnlogOn))
			Logon();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			Logon();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
