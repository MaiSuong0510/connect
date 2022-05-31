package entities;

public class CT_DichVu {
	private int maCT;
	private int maDV;
	private int maHD;
	private int SLSD;
	public int getMaCT() {
		return maCT;
	}
	public void setMaCT(int maCT) {
		this.maCT = maCT;
	}
	public int getMaDV() {
		return maDV;
	}
	public void setMaDV(int maDV) {
		this.maDV = maDV;
	}
	public int getMaHD() {
		return maHD;
	}
	public void setMaHD(int maHD) {
		this.maHD = maHD;
	}
	public int getSLSD() {
		return SLSD;
	}
	public void setSLSD(int sLSD) {
		SLSD = sLSD;
	}
	public CT_DichVu(int maDV, int maHD, int sLSD) {
		super();
		this.maDV = maDV;
		this.maHD = maHD;
		SLSD = sLSD;
	}
	public CT_DichVu() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CT_DichVu [maCT=" + maCT + ", maDV=" + maDV + ", maHD=" + maHD + ", SLSD=" + SLSD + "]";
	}
	
}
