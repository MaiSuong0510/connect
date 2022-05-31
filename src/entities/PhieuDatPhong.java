package entities;

import java.util.Date;

public class PhieuDatPhong {
	private int id;
	private int tinhTrangPhieu;
	private Date ngayDen;
	private Date ngayDi;
	private Date ngayLapPhieu;//ngày đặt phòng
	private Phong phong;
	private KhachHang khachHang;
	private NhanVien nhanVien;
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTinhTrangPhieu() {
		return tinhTrangPhieu;
	}
	public void setTinhTrangPhieu(int tinhTrangPhieu) {
		this.tinhTrangPhieu = tinhTrangPhieu;
	}
	public Date getNgayDen() {
		return ngayDen;
	}
	public void setNgayDen(Date ngayDen) {
		this.ngayDen = ngayDen;
	}
	public Date getNgayDi() {
		return ngayDi;
	}
	public void setNgayDi(Date ngayDi) {
		this.ngayDi = ngayDi;
	}

	public Date getNgayLapPhieu() {
		return ngayLapPhieu;
	}
	public void setNgayLapPhieu(Date ngayLapPhieu) {
		this.ngayLapPhieu = ngayLapPhieu;
	}
	public Phong getPhong() {
		return phong;
	}
	public void setPhong(Phong phong) {
		this.phong = phong;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public PhieuDatPhong(int id, int tinhTrangPhieu, Date ngayDen, Date ngayDi, Date ngayLapPhieu, Phong phong,
			KhachHang khachHang, NhanVien nhanVien) {
		super();
		this.id = id;
		this.tinhTrangPhieu = tinhTrangPhieu;
		this.ngayDen = ngayDen;
		this.ngayDi = ngayDi;
		this.ngayLapPhieu = ngayLapPhieu;
		this.phong = phong;
		this.khachHang = khachHang;
		this.nhanVien = nhanVien;
	}
	public PhieuDatPhong(int tinhTrangPhieu, Date ngayDen, Date ngayDi,
			Date ngayLapPhieu, Phong phong, KhachHang khachHang) {
		super();
		this.tinhTrangPhieu = tinhTrangPhieu;
		this.ngayDen = ngayDen;
		this.ngayDi = ngayDi;
		this.ngayLapPhieu = ngayLapPhieu;
		this.phong = phong;
		this.khachHang = khachHang;
	}
	public PhieuDatPhong(Date ngayDen, Date ngayDi, Phong phong, KhachHang khachHang) {
		super();
		this.ngayDen = ngayDen;
		this.ngayDi = ngayDi;
		this.phong = phong;
		this.khachHang = khachHang;
	}

	public PhieuDatPhong(int id, Date ngayDen, Date ngayDi, KhachHang khachHang, int tinhTrangPhieu) {
		super();
		this.id = id;
		this.ngayDen = ngayDen;
		this.ngayDi = ngayDi;
		this.khachHang = khachHang;
		this.tinhTrangPhieu = tinhTrangPhieu;
	}
	
	public PhieuDatPhong(int id, int tinhTrangPhieu, Date ngayDen, Date ngayDi, Date ngayLapPhieu) {
		super();
		this.id = id;
		this.tinhTrangPhieu = tinhTrangPhieu;
		this.ngayDen = ngayDen;
		this.ngayDi = ngayDi;
		this.ngayLapPhieu = ngayLapPhieu;
	}
	public PhieuDatPhong() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PhieuDatPhong(int id, Date ngayDen, Date ngayDi) {
		super();
		this.id = id;
		this.ngayDen = ngayDen;
		this.ngayDi = ngayDi;
	}

	
	
	
}
