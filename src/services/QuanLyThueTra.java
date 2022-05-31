package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import database.Database;
import entities.KhachHang;
import entities.NhanVien;
import entities.PhieuDatPhong;
import entities.Phong;

public class QuanLyThueTra {
	private ArrayList<PhieuDatPhong> dsPhieuDatPhongs;

	public QuanLyThueTra() {
		dsPhieuDatPhongs = new ArrayList<>();
	}

	public ArrayList<PhieuDatPhong> getDS() {
		return dsPhieuDatPhongs;
	}

	public ArrayList<PhieuDatPhong> listPhieuDatPhong(String maPhong) {
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select pdp.*, kh.hoTen,kh.CMND,\r\n" + "	case\r\n"
					+ "		when pdp.ngayDi = ? then 4 -- sap check out\r\n"
					+ "		when pdp.ngayDen = ? then 2 -- dang check in\r\n"
					+ "		when pdp.ngayDen < ? then 3 -- dang su dung\r\n" + "		else 1 -- da dat\r\n"
					+ "	end as tinhtrang\r\n" + "from  PhieuDatPhong as pdp \r\n"
					+ "join Phong as p on pdp.maPhong = p.maPhong\r\n"
					+ "join KhachHang as kh on kh.maKH = pdp.maKH\r\n" + "where (\r\n"
					+ "	( pdp.ngayDi  >=?) -- ngay hien tai\r\n" + "	and p.maPhong = ?\r\n" + ")";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
			stm.setInt(5, Integer.parseInt(maPhong) - 100);
			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				String maPhieu = rs.getString(1);
				String hoTen = rs.getNString(8);
				String CMND = rs.getString(9);
				Date ngayDen = rs.getDate(5);
				Date ngayDi = rs.getDate(6);
				String tinhTrang = rs.getString(10);
				KhachHang k = new KhachHang(hoTen, CMND);
				PhieuDatPhong t = new PhieuDatPhong(Integer.parseInt(maPhieu), ngayDen, ngayDi, k,
						Integer.parseInt(tinhTrang));

				dsPhieuDatPhongs.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsPhieuDatPhongs;
	}

	public ArrayList<PhieuDatPhong> checkCreatePhieuDatPhong(String ngayDi, String ngayDen, String maPhong) {
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			String sql = "select pdp.*, tinhtrang=1 from  PhieuDatPhong as pdp join Phong as p on pdp.maPhong = p.maPhong\r\n"
					+ "where (pdp.ngayDen <= ? and pdp.ngayDi  >= ?	) and p.maPhong = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setDate(1, java.sql.Date.valueOf(ngayDi));
			stmt.setDate(2, java.sql.Date.valueOf(ngayDen));
			stmt.setInt(3, Integer.parseInt(maPhong) - 100);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maPhieu = rs.getString(1);
				Date date1 = rs.getDate(5);
				Date date2 = rs.getDate(6);
				Date ngayLapPhieu = rs.getDate(7);
				String tinhTrang = rs.getString(8);
				PhieuDatPhong t = new PhieuDatPhong(Integer.parseInt(maPhieu), Integer.parseInt(tinhTrang), date1,
						date2, ngayLapPhieu);
				dsPhieuDatPhongs.add(t);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			e.printStackTrace();
		}
		return dsPhieuDatPhongs;
	}

	public boolean createPDP(String maPhong, String maKH, String maNV, String ngayDen, String ngayDi) {
		Database.getInstance();
		Connection con = Database.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement(
					"insert into PhieuDatPhong (maPhong, maKH, maNV, ngayDen, ngayDi, ngayLapPhieu) values (?,?,?,?,?,?)");
			stmt.setInt(1, Integer.parseInt(maPhong) - 100);
			stmt.setString(2, maKH);
			stmt.setString(3, maNV);
			stmt.setDate(4, java.sql.Date.valueOf(ngayDen));
			stmt.setDate(5, java.sql.Date.valueOf(ngayDi));
			stmt.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// e.printStackTrace();
			if (e.getMessage().contains("Violation of PRIMARY KEY"))
				return false;
		}
		return n > 0;
	}

	public ArrayList<PhieuDatPhong> layDLPhong_KH() {
		ArrayList<PhieuDatPhong> phieu = new ArrayList<PhieuDatPhong>();
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select p.*, kh.*,pdp.ngayDen,pdp.ngayDi from PhieuDatPhong as pdp join KhachHang as kh on pdp.maKH = kh.maKH join Phong as p on pdp.maPhong = p.maPhong";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				int maPhong = rs.getInt(1);
				String loaiPhong = rs.getString(2);
				String ghiChu = rs.getString(3);
				int giaPhong = rs.getInt(4);
				int maKH = rs.getInt(5);
				String tenKH = rs.getString(6);
				String cmnd = rs.getString(7);
				Date ngaySinh = rs.getDate(8);
				int gioiTinh = rs.getInt(9);
				String sdt = rs.getString(10);
				Date ngayDen = rs.getDate(11);
				Date ngayDi = rs.getDate(12);

				Date toDay = new Date();

				int tinhTrangPhong = -1;

				if (toDay.compareTo(ngayDi) > 0)
					tinhTrangPhong = 0;
				else if (toDay.compareTo(ngayDen) < 0 && toDay.compareTo(ngayDi) > 0)
					tinhTrangPhong = 3;
				else if (toDay.compareTo(ngayDen) == 0)
					tinhTrangPhong = 4;
				else if (toDay.compareTo(ngayDi) == 0)
					tinhTrangPhong = 2;
				else if (toDay.compareTo(ngayDi) < 0)
					tinhTrangPhong = 1;

				KhachHang k = new KhachHang(maKH, tenKH, cmnd, ngaySinh, gioiTinh, sdt);
				Phong phong = new Phong(maPhong, loaiPhong, ghiChu, giaPhong, tinhTrangPhong);
				phieu.add(new PhieuDatPhong(ngayDen, ngayDi, phong, k));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return phieu;
	}

	public PhieuDatPhong findPhieuDatPhongByMaPhieu(String maPhieu) {
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			PreparedStatement st = con.prepareStatement("select * from PhieuDatPhong where maPhieuDatPhong = ?");
			st.setNString(1,maPhieu);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Date ngayDen = rs.getDate(5);
				Date ngayDi = rs.getDate(6);
				PhieuDatPhong pdp = new PhieuDatPhong(Integer.parseInt(maPhieu),ngayDen, ngayDi);
				return pdp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean update(String maPDP)
	{
		Database.getInstance();
		Connection con= Database.getConnection();
		PreparedStatement stmt=null;
		int n=0;
		try {
			stmt = con.prepareStatement("update PhieuDatPhong set ngayDi =? where maPhieuDatPhong = ? ");
			LocalDate a = LocalDate.now();
			LocalDate b = a.minusDays(1);
			stmt.setDate(1,java.sql.Date.valueOf(b));
			stmt.setInt(2, Integer.parseInt(maPDP));
			n= stmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return n>0;
	}

}
