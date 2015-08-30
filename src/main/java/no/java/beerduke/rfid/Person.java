package no.java.beerduke.rfid;

import java.io.Serializable;

public class Person implements Serializable{
	private static final long serialVersionUID = 1L;
	private  String name, company, rfid, status, rfidDec;
    private int beerCount = 0, rejectCount = 0;
    private boolean rfidHexed = false;

	public Person() {
		super();
    }

    public Person(String rfid, String name, String company, String status, String rfidDec) {
		super();
		this.name = name;
		this.company = company;
		this.rfid = rfid;
		this.status = status;
		this.rfidDec = rfidDec;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getRfid() {
    	if ((rfid == null || "".equals(rfid)) && !rfidHexed) {
    		rfid = (rfidDec == null || "".equals(rfidDec)) ? "" : decToTenDigitHexString();
    		rfidHexed = true;
    	}
        return rfid.toLowerCase();
    }

	private String decToTenDigitHexString() {
		String hex = Long.toHexString(Long.parseLong(getRfidDec()));
		while(hex.length() < 10){
			hex = "0" + hex;
		}
		return hex;
	}

    public String getRfidDec() {
		return this.rfidDec;
	}

	public void setRfidDec(String rfidDec) {
		this.rfidDec = rfidDec;
	}

	public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid.toLowerCase();
    }
    
    public String toString(){
    	return getRfid()+";"+getName() +";"+ getCompany() +";"+getStatus();
    }
    
    public String getStatus() {
    	return status;
    }
    
    public void setStatus(String status) {
    	this.status = status;
    }

	public void setBeerCount(int count) {
		beerCount = count;
	}

	public void setRejectCount(int count) {
		rejectCount = count;
	}

	public void incrementBeerCount(){
		beerCount++;
	}
	public void incrementRejectedCount(){
		rejectCount++;
	}
	
	public boolean isHero(){
		return "hero".equals(status);
	}
}
