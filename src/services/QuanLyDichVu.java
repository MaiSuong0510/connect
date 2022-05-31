package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import database.Database;
import entities.DichVu;

public class QuanLyDichVu {
	private ArrayList<DichVu> dsDichVu;

	public QuanLyDichVu() {
		dsDichVu = new ArrayList<>();
	}
	public ArrayList<DichVu> getDS() {
		return dsDichVu;
	}
	public ArrayList<DichVu> dsDichVu(){
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select * from DichVu";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				int maDV = rs.getInt(1);
				String tenDV = rs.getNString(2);
				String donVi = rs.getNString(3);
				String loai = rs.getNString(4);
				int soLuongCo = rs.getInt(5);
				int giaDV = rs.getInt(6);
				DichVu dv = new DichVu(maDV, tenDV, donVi, loai, soLuongCo, giaDV);
				dsDichVu.add(dv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsDichVu;
	}
	public DichVu findById(String maDV) {
		DichVu dv = new DichVu();
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select * from DichVu where maDV = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, Integer.parseInt(maDV));
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				String tenDV = rs.getNString(2);
				String donVi = rs.getNString(3);
				String loai = rs.getNString(4);
				int soLuongCo = rs.getInt(5);
				int giaDV = rs.getInt(6);
				dv = new DichVu(Integer.parseInt(maDV), tenDV, donVi, loai, soLuongCo, giaDV);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return dv;	
	}
	public boolean updateSL(String maPhieu, String slsd) {
		Database.getInstance();
		Connection con = Database.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		try {
			stmt = con.prepareStatement("update DichVu set soLuongCo -= ? where maDV = ?");
			stmt.setString(1,slsd);
			stmt.setString(2,maPhieu);
			//stmt.execute();
			n = stmt.executeUpdate();
			return n>0;
		}catch(SQLException e) {
			e.printStackTrace();
			if(e.getMessage().contains("Violation of PRIMARY KEY"))
				return false;
		}
		return false;
	}
	
	//store produce
		public boolean curdDichVu(String ma,String ten, String donVi, String loai,int soLuongCo,int giaDV, String type) {
			Database.getInstance();
			Connection con = Database.getConnection();
			PreparedStatement stmt = null;
			//donGia = donGia.replace(",", "");
			int n=0;
			try {
				// (  @ma int,  @ten nvarchar(50),  @theloai nvarchar(55),  @dongia int,  @soluong int, @hang nvarchar(80), @ghichu nvarchar(100),  @Type nvarchar(20) = ''  )  
				stmt = con.prepareStatement("EXEC QuanLyDichVu ?,  ?, ?,  ?,  ?, ?, ?");
				stmt.setNString(1,ma);
				stmt.setNString(2,ten);
				stmt.setNString(3,donVi);
				stmt.setNString(4, loai);
				stmt.setInt(5, soLuongCo);
				stmt.setInt(6,giaDV);
				stmt.setString(7,type );
				n = stmt.executeUpdate();
			}catch(SQLException e) {
				//e.printStackTrace();
				if(e.getMessage().contains("Violation of PRIMARY KEY"))
					return false;
			}
			return n > 0;
		}

	/*
	 * maDV, tenDV , donVi , loai, soLuongCo, giaDV
	 */
		public ArrayList<DichVu> timKiem(String maDV, String tenDV, String donVi, String loai, String soLuongCo, String giaDV) {
			Database.getInstance();
			Connection con = Database.getConnection();
			try {
				String sql = "select * from DichVu where ";
				if(maDV.equals("") && tenDV.equals("") && donVi.equals("") && loai.equals("") && soLuongCo.equals("") && giaDV.equals("")) {
					sql = "select * from DichVu ";
				}
				
				boolean flag = true;
				if(flag == true) {
					if(!maDV.equals("")) {
						sql+=" maDV = "+maDV;
						flag = false;   
					}
				}else {
					if(!maDV.equals("")) {
						sql+= " and maDV = "+maDV;
					}
				}
				
				if(flag == true) {
					if(!tenDV.equals("")) {
						sql+=" tenDV like N'%"+tenDV+"%'";
						flag = false;   
					}
				}else {
					if(!tenDV.equals("")) {
						sql+= " and tenDV like N'%"+tenDV+"%'";
					}
				}
				
				if(flag == true) {
					if(!donVi.equals("")) {
						sql+=" donVi like N'%"+donVi+"%'";
						flag = false;   
					}
				}else {
					if(!donVi.equals("")) {
						sql+= " and donVi like N'%"+donVi+"%'";
					}
				}
				
				if(flag == true) {
					if(!loai.equals("")) {
						sql+=" loai like N'%"+loai+"%'";
						flag = false;   
					}
				}else {
					if(!loai.equals("")) {
						sql+= " and loai like N'%"+loai+"%'";
					}
				}
				
				if(flag == true) {
					if(!soLuongCo.equals("")) {
						sql+=" loai soLuongCo = "+soLuongCo;
						flag = false;   
					}
				}else {
					if(!soLuongCo.equals("")) {
						sql+= " and soLuongCo = "+soLuongCo;
					}
				}
				if(flag == true) {
					if(!giaDV.equals("")) {
						sql+=" loai giaDV = "+giaDV;
						flag = false;   
					}
				}else {
					if(!giaDV.equals("")) {
						sql+= " and giaDV = "+giaDV;
					}
				}
				sql+=" ;";
				//System.out.println("192: " + sql);
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery();
				ArrayList<DichVu> list = new ArrayList<>();
				while (rs.next()) {
					// System.err.println(rs);
					String maDV1 = rs.getString(1);
					String tenDV1 = rs.getNString(2);
					String donVi1 = rs.getNString(3);
					String loaiDV1 = rs.getString(4);
					String sl1 = rs.getString(5);
					String gia1 = rs.getString(6);
					DichVu dv = new DichVu(Integer.parseInt(maDV1), tenDV1, donVi1,loaiDV1, Integer.parseInt(sl1), Integer.parseInt(gia1));
					list.add(dv);
				}
				return list;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public ArrayList<DichVu> timKiem(String s,String loaiTim){
			Database.getInstance();
			Connection con= Database.getConnection();
			String temp = s;
			s = "%" + s + "%";
			String operation = "like";
			if(loaiTim.equals("Tìm Theo Mã")) {loaiTim = "maDV";	operation = "="; s= temp;}
			if(loaiTim.equals("Tìm Theo Tên")) loaiTim = "tenDV";
			if(loaiTim.equals("Tìm Theo Loại")) loaiTim = "loai";
			
			try {
				PreparedStatement st = con.prepareStatement("select * from DichVu where "+loaiTim+" "+operation+" ? ");
				st.setNString(1, s);
				ResultSet rs = st.executeQuery();
				ArrayList<DichVu> list = new ArrayList<>();
				while(rs.next()) {
					//System.err.println(rs);
					String maDV = rs.getString(1);
					String tenDV = rs.getString(2);
					String donVi = rs.getString(3);
					String loai = rs.getString(4);
					String soLuongCo = rs.getString(5);
					String giaDV = rs.getString(6);
					DichVu dv = new DichVu(Integer.parseInt(maDV), tenDV, donVi, loai, Integer.parseInt(soLuongCo), Integer.parseInt(giaDV));
					//System.out.println(t.toString());
					list.add(dv); 
				}
				return list;
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	
}
