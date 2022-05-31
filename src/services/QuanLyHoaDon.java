package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import database.Database;
import entities.HoaDon;
import entities.Phong;

public class QuanLyHoaDon {
	public ArrayList<HoaDon> dsHD;
	public QuanLyHoaDon() {
		dsHD = new ArrayList<>();
	}
	public ArrayList<HoaDon> getDS(){
		return dsHD;
	}
	/*
	maHD = maDatPhong
	tongTien = giaPhong * (ngaydi - ngayden)
	ngayLapHD = null
	tinhTrangPhieu = 0
	*/
	public boolean createHD(String maHD, String maNV, double tongTien, String ngayLapHD, int tinhTrangHD) {
		Database.getInstance();
		Connection con = Database.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement(
					"insert into HoaDon (maHD, maNV, tongTien, ngayLapHD, tinhTrangHoaDon) values (?,?,?,?,?)");
			stmt.setInt(1, Integer.parseInt(maHD));
			stmt.setInt(2, Integer.parseInt(maNV));
			stmt.setDouble(3, tongTien);
			stmt.setDate(4, java.sql.Date.valueOf(ngayLapHD));
			stmt.setInt(5, tinhTrangHD);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// e.printStackTrace();
			if (e.getMessage().contains("Violation of PRIMARY KEY"))
				return false;
		}
		return n > 0;
	}
	public boolean update(String tongTien, String maHD)
	{
		Database.getInstance();
		Connection con= Database.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt = con.prepareStatement("update HoaDon set tongTien += ?, ngayLapHD = ?, tinhTrangHoaDon = 1 where maHD = ? ");
			stmt.setDouble(1, Double.parseDouble(tongTien));
			stmt.setDate(2,java.sql.Date.valueOf(LocalDate.now()));
			stmt.setInt(3, Integer.parseInt(maHD));
			n= stmt.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return n>0;
	}
	public HoaDon timTheoMa(String maPhieu) {
		HoaDon hd = null;
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = " select * from HoaDon where maHD = "+maPhieu;
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				hd = new HoaDon(rs.getInt(1), rs.getInt(2), rs.getDouble(3),rs.getDate(4), rs.getInt(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hd;
	}

}
