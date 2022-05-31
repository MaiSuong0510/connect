package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import database.Database;
import entities.DichVu;
import entities.KhachHang;
import entities.Phong;

public class QuanLyKhachHang {
	public ArrayList<KhachHang> dsKhachHang;

	public QuanLyKhachHang() {
		dsKhachHang = new ArrayList<>();
	}

	public ArrayList<KhachHang> getDS() {
		return dsKhachHang;
	}
	public ArrayList<KhachHang> dsKhachHang(){
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select * from KhachHang";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				String maKH = rs.getString(1);
				String hoTen = rs.getNString(2);
				String CMND = rs.getNString(3);
				Date ngaySinh = rs.getDate(4);
				String gioiTinh = rs.getString(5);
				String sDT = rs.getString(6);
				KhachHang kh = new KhachHang(Integer.parseInt(maKH), hoTen, CMND, ngaySinh, Integer.parseInt(gioiTinh), sDT);
				dsKhachHang.add(kh);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsKhachHang;
	}
	
	public KhachHang chiTietKhachHang(String CMND){
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			PreparedStatement st = con.prepareStatement("select * from KhachHang where  CMND = ?");
			st.setNString(1, CMND);
			ResultSet rs = st.executeQuery();
		
			while(rs.next()) {
				String maKH = rs.getString(1);
				String hoTen = rs.getNString(2);
				Date ngaySinh = rs.getDate(4);
				String gioiTinh = rs.getString(5);
				String sDT = rs.getString(6);
				KhachHang kh = new KhachHang(Integer.parseInt(maKH), hoTen, CMND, ngaySinh, Integer.parseInt(gioiTinh), sDT);
				return kh;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//store produce
	public boolean curdKhachHang(String ma,String ten, String CMND, String ngaySinh,int gioiTinh,String sDT, String type) {
		Database.getInstance();
		Connection con = Database.getConnection();
		PreparedStatement stmt = null;
		//donGia = donGia.replace(",", "");
		int n=0;
		try {
			// (  @ma int,  @ten nvarchar(50),  @theloai nvarchar(55),  @dongia int,  @soluong int, @hang nvarchar(80), @ghichu nvarchar(100),  @Type nvarchar(20) = ''  )  
			stmt = con.prepareStatement("EXEC QuanLyKhachHang ?,  ?, ?,  ?,  ?, ?, ?");
			stmt.setNString(1,ma);
			stmt.setNString(2,ten);
			stmt.setNString(3,CMND);
			stmt.setNString(4, ngaySinh);
			stmt.setInt(5, gioiTinh);
			stmt.setString(6,sDT);
			stmt.setString(7,type );
			n = stmt.executeUpdate();
		}catch(SQLException e) {
			//e.printStackTrace();
			if(e.getMessage().contains("Violation of PRIMARY KEY"))
				return false;
		}
//		try {
//			Database.getInstance();
//			Connection con = Database.getConnection();
//			Statement stm = con.createStatement();
//			stm.executeUpdate(String.format("insert into KhachHang values (%s,'%s','%s',%s,%s,'%s')"));
//
//		} catch (SQLException e) {
//			 e.printStackTrace();
//			if(e.getMessage().contains("Violation of PRIMARY KEY"))
//				return false;
//		}
		return n > 0;
	}
	public ArrayList<KhachHang> timKiem(String maKH, String hoTen, String CMND, String ngaySinh, String gioiTinh, String sDT) {
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			String sql = "select * from KhachHang where ";
			if(maKH.equals("") && hoTen.equals("") && CMND.equals("") && ngaySinh.equals("") && gioiTinh.equals("") && sDT.equals("")) {
				sql = "select * from KhachHang ";
			}
			
			boolean flag = true;
			if(flag == true) {
				if(!maKH.equals("")) {
					sql+=" maKH = "+maKH;
					flag = false;   
				}
			}else {
				if(!maKH.equals("")) {
					sql+= " and maKH = "+maKH;
				}
			}
			
			if(flag == true) {
				if(!hoTen.equals("")) {
					sql+=" hoTen like N'%"+hoTen+"%'";
					flag = false;   
				}
			}else {
				if(!hoTen.equals("")) {
					sql+= " and hoTen like N'%"+hoTen+"%'";
				}
			}
			
			if(flag == true) {
				if(!CMND.equals("")) {
					sql+=" CMND like N'%"+CMND+"%'";
					flag = false;   
				}
			}else {
				if(!CMND.equals("")) {
					sql+= " and CMND like N'%"+CMND+"%'";
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
					sql+= " gioiTinh = "+ gioiTinh;
					flag = false;
				}
			}else {
				if(!gioiTinh.equals("")) {
					sql+= " and gioiTinh = "+ Integer.parseInt(gioiTinh);
				}
			}
			sql+=" ;";
			//System.out.println("178: " + sql);
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			ArrayList<KhachHang> list = new ArrayList<>();
			while (rs.next()) {
				// System.err.println(rs);
				String maKH1 = rs.getString(1);
				String hoTen1 = rs.getNString(2);
				String CMND1 = rs.getNString(3);
				String ngaySinh1 = rs.getString(4);
				int gioiTinh1 = rs.getInt(5);
				String sDT1 = rs.getString(6);
				java.util.Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(ngaySinh1);
				KhachHang kh = new KhachHang(Integer.parseInt(maKH1), hoTen1, CMND1,date1,gioiTinh1,sDT1);
				list.add(kh);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
