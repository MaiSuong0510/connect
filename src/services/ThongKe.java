package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import database.Database;
import entities.HoaDon;

public class ThongKe {
	public CategoryDataset doanhThuTungPhong(String thang, String nam) {
		DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
            try {
            	Database.getInstance();
            	Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                    ResultSet query_set = stmt.executeQuery("\r\n" + 
                    		"select p.maPhong, p.loaiPhong, tt = SUM(hd.tongTien) from Phong as p\r\n" + 
                    		"join PhieuDatPhong as pdp on pdp.maPhong = p.maPhong\r\n" + 
                    		"join HoaDon as hd on hd.maHD = pdp.maPhieuDatPhong\r\n" + 
                    		"group by p.maPhong, p.loaiPhong,pdp.ngayDen\r\n" + 
                    		"having DATEPART(mm,pdp.ngayDen) = "+thang+" and  DATEPART(yyyy,pdp.ngayDen) = "+nam +" ");
                    //int i=0;
                    while (query_set.next()) {
                            String category = query_set.getString("maPhong");
                            int tongTien = query_set.getInt("tt");
                            //my_bar_chart_dataset.addValue(tongTien,"maPhong",category);
                            my_bar_chart_dataset.addValue(tongTien, "Mã Phòng", category);
                            //System.out.println(tongTien);
                            }
                    return my_bar_chart_dataset;
            }
             catch (Exception i)
             {
        System.out.println(i);
             }
			return my_bar_chart_dataset;
    }
	public CategoryDataset soLuongPDP(String thang,String nam) {
		DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
            try {
            	Database.getInstance();
            	Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                    ResultSet query_set = stmt.executeQuery("select p.maPhong, tt = Count(p.maPhong) from Phong as p\r\n" + 
                    		"join PhieuDatPhong as pdp on pdp.maPhong = p.maPhong\r\n" + 
                    		"where DATEPART(MM,pdp.ngayDen) = "+thang+" and  DATEPART(yyyy,pdp.ngayDen) ="+ nam+ "\r\n" + 
                    		"group by p.maPhong");
                    //int i=0;
                    while (query_set.next()) {
                            String category = query_set.getString("maPhong");
                            int soLuong = query_set.getInt("tt");
                            //my_bar_chart_dataset.addValue(tongTien,"maPhong",category);
                            my_bar_chart_dataset.addValue(soLuong, "Số Lượng", category);
                            //System.out.println(tongTien);
                            }
                    return my_bar_chart_dataset;
            }
             catch (Exception i)
             {
        System.out.println(i);
             }
			return my_bar_chart_dataset;
    }
	public ArrayList<HoaDon> thongKeDoanhThu(String thang, String nam) {
		ArrayList<HoaDon> ds = new ArrayList<HoaDon>();
		try {
			Database.getInstance();
			Connection con = Database.getConnection();
			String sql = 
					"select hd.ngayLapHD,tt = SUM(hd.tongTien) from HoaDon as hd\r\n" + 
					"where DATEPART(MM,hd.ngayLapHD) = "+thang+" and  DATEPART(yyyy,hd.ngayLapHD) = "+nam+"\r\n" + 
					"group by hd.ngayLapHD";
			
			PreparedStatement stm = con.prepareStatement(sql);

			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Date ngayLap = rs.getDate(1);
				Double tt = rs.getDouble(2);
				// System.out.println("53: "+tinhTrang);
				HoaDon hd = new HoaDon(tt, ngayLap);
				ds.add(hd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}
}
