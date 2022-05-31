package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import database.Database;
import entities.Phong;

public class QuanLyPhong {
	private ArrayList<Phong> dsPhong;

	public QuanLyPhong() {
		dsPhong = new ArrayList<>();
	}

	public ArrayList<Phong> getDS() {
		return dsPhong;
	}

	// ds Phong hien tai
	public ArrayList<Phong> docTuBang() {
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select p.*,\r\n" + "case \r\n" + "	when ctp.tinhtrang is null then 0\r\n"
					+ "	else ctp.tinhtrang\r\n" + "end as tinhtrang\r\n" + " from Phong as p left join\r\n" + "(\r\n"
					+ "select distinct pdp.maPhong,\r\n" + "	case\r\n"
					+ "		when pdp.maPhieuDatPhong is null then 0 -- trong\r\n"
					+ "		when pdp.ngayDen = ? then 2 -- den han check in\r\n"
					+ "		when pdp.ngayDi = ? then 4 -- den han check out\r\n" + "		else 3 -- dang su dung\r\n"
					+ "	end as tinhtrang\r\n"
					+ "from  PhieuDatPhong as pdp right join Phong as p on pdp.maPhong = p.maPhong\r\n"
					+ "where ( (pdp.ngayDen <= ? and pdp.ngayDi  >= ?)) --di-den\r\n"
					+ ") as ctp on p.maPhong = ctp.maPhong\r\n" + "";
			PreparedStatement stm = con.prepareStatement(sql);
			// System.out.println("huhu"+java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(4, java.sql.Date.valueOf(LocalDate.now()));

			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				String maPhong = rs.getString(1);
				String loaiPhong = rs.getNString(2);
				String ghiChu = rs.getNString(3);
				String giaPhong = rs.getString(4);
				String tinhTrang = rs.getString(5);
				// System.out.println("53: "+tinhTrang);
				Phong t = new Phong(Integer.parseInt(maPhong), loaiPhong, ghiChu, Integer.parseInt(giaPhong),
						Integer.parseInt(tinhTrang));
				dsPhong.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsPhong;
	}

	public int tongSoPhong() {
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select COUNT(*) as tongSoPhong from Phong";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String tongSoPhong = rs.getString(1);
				// System.out.println(tongSoPhong);
				return Integer.parseInt(tongSoPhong);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public ArrayList<Phong> timKiem(String s, String loai) {
		Database.getInstance();
		Connection con = Database.getConnection();
		String temp = s;
		s = "%" + s + "%";
		String operation = "like";
		if (loai.equals("Tìm Theo Mã Phòng")) {
			loai = "maPhong";
			operation = "=";
			s = temp;
		}
		if (loai.equals("Tìm Theo Loại Phòng"))
			loai = "loaiPhong";
		if (loai.equals("Tìm Theo Đơn Giá")) {
			loai = "giaPhong";
			operation = "=";
			s = temp;
		}
		try {
			PreparedStatement st = con.prepareStatement("select * from Phong where " + loai + " " + operation + " ? ");
			st.setNString(1, s);
			ResultSet rs = st.executeQuery();
			ArrayList<Phong> list = new ArrayList<>();
			while (rs.next()) {
				// System.err.println(rs);
				String maPhong = rs.getString(1);
				String loaiPhong = rs.getNString(2);
				String ghiChu = rs.getNString(3);
				String giaPhong = rs.getString(4);
				Phong t = new Phong(Integer.parseInt(maPhong), loaiPhong, ghiChu, Integer.parseInt(giaPhong));
				list.add(t);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Phong> timTheoMa(int maPhong) {
		ArrayList<Phong> ds = new ArrayList<Phong>();

		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select p.*,\r\n" + "case \r\n" + "	when ctp.tinhtrang is null then 0\r\n"
					+ "	else ctp.tinhtrang\r\n" + "end as tinhtrang\r\n" + " from Phong as p left join\r\n" + "(\r\n"
					+ "select distinct pdp.maPhong,\r\n" + "	case\r\n"
					+ "		when pdp.maPhieuDatPhong is null then 0 -- trong\r\n"
					+ "		when pdp.ngayDen = ? then 2 -- den han check in\r\n"
					+ "		when pdp.ngayDi = ? then 4 -- den han check out\r\n" + "		else 3 -- dang su dung\r\n"
					+ "	end as tinhtrang\r\n"
					+ "from  PhieuDatPhong as pdp right join Phong as p on pdp.maPhong = p.maPhong\r\n"
					+ "where ( (pdp.ngayDen <= ? and pdp.ngayDi  >= ?)) --di-den\r\n"
					+ ") as ctp on p.maPhong = ctp.maPhong\r\n" + "where p.maPhong = " + maPhong;

			PreparedStatement stm = con.prepareStatement(sql);

			stm.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(4, java.sql.Date.valueOf(LocalDate.now()));

			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				ds.add(new Phong(maPhong, rs.getNString(2), rs.getNString(3), rs.getInt(4), rs.getInt(5)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}

//Sửa lại phần này
	public ArrayList<Phong> timTheoLoaiPhong(String s) {
		ArrayList<Phong> ds = new ArrayList<Phong>();
		System.out.println(s);
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select p.*,\r\n" + "case \r\n" + "	when ctp.tinhtrang is null then 0\r\n"
					+ "	else ctp.tinhtrang\r\n" + "end as tinhtrang\r\n" + " from Phong as p left join\r\n" + "(\r\n"
					+ "select distinct pdp.maPhong,\r\n" + "	case\r\n"
					+ "		when pdp.maPhieuDatPhong is null then 0 -- trong\r\n"
					+ "		when pdp.ngayDen = ? then 2 -- den han check in\r\n"
					+ "		when pdp.ngayDi = ? then 4 -- den han check out\r\n" + "		else 3 -- dang su dung\r\n"
					+ "	end as tinhtrang\r\n"
					+ "from  PhieuDatPhong as pdp right join Phong as p on pdp.maPhong = p.maPhong\r\n"
					+ "where ( (pdp.ngayDen <= ? and pdp.ngayDi  >= ?)) --di-den\r\n"
					+ ") as ctp on p.maPhong = ctp.maPhong\r\n" + "where p.loaiPhong like N'" + s + "'";

			PreparedStatement stm = con.prepareStatement(sql);

			stm.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(4, java.sql.Date.valueOf(LocalDate.now()));

			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				ds.add(new Phong(rs.getInt(1), rs.getNString(2), rs.getNString(3), rs.getInt(4), rs.getInt(5)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}

	public ArrayList<Phong> timTheoGiaPhong(int giaPhong) {
		ArrayList<Phong> ds = new ArrayList<Phong>();

		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select p.*,\r\n" + "case \r\n" + "	when ctp.tinhtrang is null then 0\r\n"
					+ "	else ctp.tinhtrang\r\n" + "end as tinhtrang\r\n" + " from Phong as p left join\r\n" + "(\r\n"
					+ "select distinct pdp.maPhong,\r\n" + "	case\r\n"
					+ "		when pdp.maPhieuDatPhong is null then 0 -- trong\r\n"
					+ "		when pdp.ngayDen = ? then 2 -- den han check in\r\n"
					+ "		when pdp.ngayDi = ? then 4 -- den han check out\r\n" + "		else 3 -- dang su dung\r\n"
					+ "	end as tinhtrang\r\n"
					+ "from  PhieuDatPhong as pdp right join Phong as p on pdp.maPhong = p.maPhong\r\n"
					+ "where ( (pdp.ngayDen <= ? and pdp.ngayDi  >= ?)) --di-den\r\n"
					+ ") as ctp on p.maPhong = ctp.maPhong\r\n" + "where p.giaPhong = " + giaPhong;

			PreparedStatement stm = con.prepareStatement(sql);

			stm.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(4, java.sql.Date.valueOf(LocalDate.now()));

			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				ds.add(new Phong(rs.getInt(1), rs.getNString(2), rs.getNString(3), rs.getInt(4), rs.getInt(5)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}

	public ArrayList<Phong> timTheoTinhTrangPhong(String tinhTrang) {
		ArrayList<Phong> ds = new ArrayList<Phong>();
		String ktra = "";
		if (tinhTrang.equals("Trống"))
			ktra = "is null";
		else if (tinhTrang.equals("Đến hạn Checkin"))
			ktra = "= 2";
		else if (tinhTrang.equals("Đang sử dụng"))
			ktra = "= 3";
		else if (tinhTrang.equals("Đến hạn Checkout"))
			ktra = "= 4";
		else
			ktra = "= -1";
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select p.*,\r\n" + "case \r\n" + "	when ctp.tinhtrang is null then 0\r\n"
					+ "	else ctp.tinhtrang\r\n" + "end as tinhtrang\r\n" + " from Phong as p left join\r\n" + "(\r\n"
					+ "select distinct pdp.maPhong,\r\n" + "	case\r\n"
					+ "		when pdp.maPhieuDatPhong is null then 0 -- trong\r\n"
					+ "		when pdp.ngayDen = ? then 2 -- den han check in\r\n"
					+ "		when pdp.ngayDi = ? then 4 -- den han check out\r\n" + "		else 3 -- dang su dung\r\n"
					+ "	end as tinhtrang\r\n"
					+ "from  PhieuDatPhong as pdp right join Phong as p on pdp.maPhong = p.maPhong\r\n"
					+ "where ( (pdp.ngayDen <= ? and pdp.ngayDi  >= ?)) --di-den\r\n"
					+ ") as ctp on p.maPhong = ctp.maPhong\r\n" + "where ctp.tinhtrang " + ktra;

			PreparedStatement stm = con.prepareStatement(sql);

			stm.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(4, java.sql.Date.valueOf(LocalDate.now()));

			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				ds.add(new Phong(rs.getInt(1), rs.getNString(2), rs.getNString(3), rs.getInt(4), rs.getInt(5)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}

	public Phong tim1Phong(int maPhong) {
		Phong p = new Phong();
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select * from Phong where maPhong=" + maPhong;
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String loaiPhong = rs.getNString(2);
				String ghiChu = rs.getNString(3);
				int giaPhong = rs.getInt(4);
				p = new Phong(maPhong, loaiPhong, ghiChu, giaPhong);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	public void updatePhong(int maPhong, String loaiPhong, int giaPhong, String ghiChu) {
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			Statement stm = con.createStatement();
			stm.executeUpdate("update Phong set loaiPhong = N'" + loaiPhong + "', giaPhong = " + giaPhong
					+ ", ghiChu = N'" + ghiChu + "' where maPhong = " + maPhong);

		} catch (SQLException e) {
			// e.printStackTrace();
		}

	}

	public void them(String loaiPhong, int giaPhong, String ghiChu) {
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			Statement stm = con.createStatement();
			stm.executeUpdate(
					"INSERT INTO Phong(loaiPhong, giaPhong, ghiChu) OUTPUT inserted.maPhong VALUES(N'"
							+ loaiPhong + "'," + giaPhong + ",N'" + ghiChu + "')");

		} catch (SQLException e) {
			// e.printStackTrace();
		}
	}
	
	public ArrayList<Phong> timKiemTheoThuocTinh(String maPhong, String ghiChu, String gia, String loai, String ngayDen, String ngayDi) {
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			String sql = "select * from Phong where ";
			if(maPhong.equals("") && gia.equals("") && loai.equals("")&& ghiChu.equals("") && ngayDen.equals("") && ngayDi.equals("")  ) {
				sql = "select * from Phong ";
			}
			if(maPhong.equals("") && gia.equals("") && loai.equals("")&& ghiChu.equals("") && !ngayDen.equals("") || !ngayDi.equals("")  ) {
				sql = "select * from Phong ";
			}
			
			boolean flag = true;
			if(flag == true) {
				if(!maPhong.equals("")) {
					sql+=" maPhong = "+maPhong;
					flag = false;   
				}
			}else {
				if(!maPhong.equals("")) {
					sql+= " and maPhong = "+maPhong;
				}
			}
			
			if(flag == true) {
				if(!ghiChu.equals("")) {
					sql+=" ghiChu like N'%"+ghiChu+"%'";
					flag = false;   
				}
			}else {
				if(!ghiChu.equals("")) {
					sql+= " and ghiChu like N'%"+ghiChu+"%'";
				}
			}
			
			if(flag == true) {
				if(!loai.equals("")) {
					sql+=" loaiPhong like N'%"+loai+"%'";
					flag = false;   
				}
			}else {
				if(!loai.equals("")) {
					sql+= " and loaiPhong like N'%"+loai+"%'";
				}
			}
			
			sql+=" except \r\n" + 
					"select p.* from  PhieuDatPhong as pdp join Phong as p on pdp.maPhong = p.maPhong\r\n" + 
					"where (pdp.ngayDen  <= ? and pdp.ngayDi  >= ? )";
			
			//System.out.println("hahahaha: " + sql);
			PreparedStatement st = con.prepareStatement(sql);
			if(!ngayDi.equals(""))
				st.setDate(1,java.sql.Date.valueOf(ngayDi) );
			else st.setString(1,"");
			
			if(!ngayDen.equals(""))
				st.setDate(2,java.sql.Date.valueOf(ngayDen) );
			else st.setString(2,"");
			//System.out.println("huhuhu: " + sql);
			ResultSet rs = st.executeQuery();
			ArrayList<Phong> list = new ArrayList<>();
			while (rs.next()) {
				// System.err.println(rs);
				String maPhong1 = rs.getString(1);
				String loaiPhong1 = rs.getNString(2);
				String ghiChu1 = rs.getNString(3);
				String giaPhong1 = rs.getString(4);
				Phong p = new Phong(Integer.parseInt(maPhong1), loaiPhong1, ghiChu1, Integer.parseInt(giaPhong1));
				list.add(p);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Phong> sapXepPhongTheoGia(String loai) {
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = "select p.*,\r\n" + "case \r\n" + "	when ctp.tinhtrang is null then 0\r\n"
					+ "	else ctp.tinhtrang\r\n" + "end as tinhtrang\r\n" + " from Phong as p left join\r\n" + "(\r\n"
					+ "select distinct pdp.maPhong,\r\n" + "	case\r\n"
					+ "		when pdp.maPhieuDatPhong is null then 0 -- trong\r\n"
					+ "		when pdp.ngayDen = ? then 2 -- den han check in\r\n"
					+ "		when pdp.ngayDi = ? then 4 -- den han check out\r\n" + "		else 3 -- dang su dung\r\n"
					+ "	end as tinhtrang\r\n"
					+ "from  PhieuDatPhong as pdp right join Phong as p on pdp.maPhong = p.maPhong\r\n"
					+ "where ( (pdp.ngayDen <= ? and pdp.ngayDi  >= ?)) --di-den\r\n"
					+ ") as ctp on p.maPhong = ctp.maPhong\r\n" + "";
			if(loai.equals("Sắp Xếp Theo Giá Phòng Tăng")) {
				sql +=  " order by p.giaPhong ASC" + "";
			}else if(loai.equals("Sắp Xếp Theo Giá Phòng Giảm")) {
				sql += " order by p.giaPhong DESC" + "";
			}
			
			PreparedStatement stm = con.prepareStatement(sql);
			// System.out.println("huhu"+java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
			stm.setDate(4, java.sql.Date.valueOf(LocalDate.now()));

			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				String maPhong = rs.getString(1);
				String loaiPhong = rs.getNString(2);
				String ghiChu = rs.getNString(3);
				String giaPhong = rs.getString(4);
				String tinhTrang = rs.getString(5);
				// System.out.println("53: "+tinhTrang);
				Phong t = new Phong(Integer.parseInt(maPhong), loaiPhong, ghiChu, Integer.parseInt(giaPhong),
						Integer.parseInt(tinhTrang));
				dsPhong.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsPhong;
	}

}
