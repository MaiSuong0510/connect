package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import database.Database;
import entities.DichVu;
import entities.NhanVien;

public class QuanLyNhanVien {
	private ArrayList<NhanVien> dsNhanVien;

	public QuanLyNhanVien() {
		dsNhanVien = new ArrayList<>();
	}

	// select maNV, passWord from NhanVien where maNV = ? bên Đăng nhập
	public boolean timNhanVien(String user, String pass) {
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement("select id from NhanVien where id = ? and passWord = ?");
			stm.setNString(1, user);
			stm.setNString(2, pass);
			ResultSet rs = stm.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

	public ArrayList<NhanVien> docTuBang() {
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "Select * from NhanVien";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getNString(2);
				String CMND = rs.getString(3);
				Date ngaySinh = rs.getDate(4);
				String gioiTinh = rs.getString(5);
				String sDT = rs.getString(6);
				String diaChi = rs.getNString(7);

				NhanVien nv = new NhanVien(Integer.parseInt(maNV), tenNV, CMND, ngaySinh, Integer.parseInt(gioiTinh),
						sDT, diaChi);
				// maNV, passWord, tenNV, sDT, ngayKyHopDong, moTa
				dsNhanVien.add(nv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsNhanVien;
	}

	public void themDuLieu(String ten, int phuNu, String cmnd, String sDT, String ngaySinh, String diaChi) {
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			Statement stm = con.createStatement();
			stm.executeUpdate("insert into NhanVien(hoTen,CMND,diaChi,gioiTinh,ngaySinh,sDT) "
					+ "output inserted.maNV values(N'" + ten + "','" + cmnd + "',N'" + diaChi + "'," + phuNu + ",'"
					+ ngaySinh + "','" + sDT + "')");

		} catch (SQLException e) {
			// e.printStackTrace();
		}

	}

	public boolean delete(int mNV) {
		Connection con = database.Database.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("delete from NhanVien where maNV = ?");
			stmt.setInt(1, mNV);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public ArrayList<NhanVien> timTheoMa(int maNV) {
		ArrayList<NhanVien> ds = new ArrayList<NhanVien>();
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select * from NhanVien where maNV = " + maNV;
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);

			while (rs.next()) {
				ds.add(new NhanVien(rs.getInt(1), rs.getNString(2), rs.getString(3), rs.getDate(4), rs.getInt(5),
						rs.getString(6), rs.getNString(7)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ds;
	}

	public ArrayList<NhanVien> timTheoTen(String tenNV) {
		ArrayList<NhanVien> ds = new ArrayList<NhanVien>();
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select * from NhanVien where hoTen like N'" + tenNV + "'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);

			while (rs.next()) {
				ds.add(new NhanVien(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5),
						rs.getString(6), rs.getString(7)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ds;
	}

	public void updateNV(int maNV, String tenNV, int gioiTinh, String sdt, String cmnd, String ngaySinh,
			String diaChi) {

		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			Statement stm = con.createStatement();
			stm.executeUpdate("update NhanVien set hoTen = N'" + tenNV + "' ,gioiTinh=" + gioiTinh + ",sDT='" + sdt
					+ "',CMND='" + cmnd + "',ngaySinh='" + ngaySinh + "',diaChi=N'" + diaChi + "' where maNV=" + maNV);

		} catch (SQLException e) {
			// e.printStackTrace();
		}
	}

	public void addPass(int maNV, String pass) {
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			Statement stm = con.createStatement();
			stm.execute("INSERT INTO TaiKhoan(password,maNV) VALUES (N'" + pass + "', " + maNV + ") ");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void delPass(int maNV) {
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			Statement stm = con.createStatement();
			stm = con.prepareStatement("delete from TaiKhoan where maNV = " + maNV);
		} catch (SQLException e) {
		}
	}

	public void updatePass(int maNV, String pass) {
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			Statement stm = con.createStatement();
			stm.execute("Update TaiKhoan set password = " + pass + " where maNV = " + maNV);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<String> layPass() {
		ArrayList<String> list = new ArrayList<String>();
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select * from TaiKhoan";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			String pass = "";
			int maNV = -1;
			while (rs.next()) {
				pass = rs.getString(1);
				maNV = rs.getInt(2);
				list.add(pass + "-" + maNV);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	//String maNV,String cmnd,String ngaySinh,String tenNV,String sDT,String gioiTinh,String diaChi
	public ArrayList<NhanVien> timKiem(String maNV,String cmnd, String ngaySinh,String tenNV, String sDT, String gioiTinh, String diaChi) {
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			String sql = "select * from NhanVien where ";
			if(maNV.equals("") && cmnd.equals("") && ngaySinh.equals("") && tenNV.equals("") && sDT.equals("") && gioiTinh.equals("") && diaChi.equals("")) {
				sql = "select * from NhanVien ";
			}
			boolean flag = true;
			if(flag == true) {
				if(!maNV.equals("")) {
					sql+=" maNV = "+maNV;
					flag = false;   
				}
			}else {
				if(!maNV.equals("")) {
					sql+= " and maNV = "+maNV;
				}
			}
			if(flag == true) {
				if(!cmnd.equals("")) {
					sql+= " cmnd like '%"+cmnd+"%'";
					flag = false;
				}
			}else {
				if(!cmnd.equals("")) {
					sql+= " and cmnd like '%"+cmnd+"%'";
				}
			}
			if(flag == true) {
				if(!ngaySinh.equals("")) {
					sql+= " ngaySinh = '"+ngaySinh+"'";
					flag = false;
				}
			}else {
				if(!ngaySinh.equals("")) {
					sql+= " and ngaySinh = '"+ngaySinh+"'";
				}
			}
			if(flag == true) {
				if(!tenNV.equals("")) {
					sql+= " hoTen like N'%"+tenNV+"%'";
					flag = false;
				}
			}else {
				if(!tenNV.equals("")) {
					sql+= " and hoTen like N'%"+tenNV+"%'";
				}
			}
			if(flag == true) {
				if(!sDT.equals("")) {
					sql+= " sDT like '%"+sDT+"%'";
					flag = false;
				}
			}else {
				if(!sDT.equals("")) {
					sql+= "and sDT like '%"+sDT+"%'";
				}
			}	
			if(!gioiTinh.equals("")) {
				if(gioiTinh.equals("Nam")) {
					gioiTinh = "0";
				}else gioiTinh = "1";
			}	
			if(flag == true) {
				if(!gioiTinh.equals("")) {
					
					sql+= " gioiTinh = "+ Integer.parseInt(gioiTinh);
					flag = false;
				}
			}else {
				if(!gioiTinh.equals("")) {
					sql+= " and gioiTinh = "+ Integer.parseInt(gioiTinh);
				}
			}
			if(flag == true) {
				if(!diaChi.equals("")) {
					sql+= " diaChi like N'%"+diaChi+"%'";
					flag = false;
				}
			}else {
				if(!diaChi.equals("")) {
					sql+= " and diaChi like N'%"+diaChi+"%'";
				}
			}		
			sql+=" ;";
			//System.out.println("232: " + sql);
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			ArrayList<NhanVien> list = new ArrayList<>();
			while (rs.next()) {
				// System.err.println(rs);
				String maNV1 = rs.getString(1);
				String hoTen1 = rs.getNString(2);
				String CMND1 = rs.getNString(3);
				String ngaySinh1 = rs.getString(4);
				String gioiTinh1 = rs.getString(5);
				String sDT1 = rs.getString(6);
				String diaChi1 = rs.getString(7);
			//	try {
					java.util.Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(ngaySinh1);
				NhanVien nv = new NhanVien(Integer.parseInt(maNV1), hoTen1, CMND1, date1, Integer.parseInt(gioiTinh1), sDT1, diaChi1);
				list.add(nv);
				//	public NhanVien(int id, String hoTen, String cMND, Date ngaySinh, int gioiTinh, String sDT, String diaChi) {

			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
