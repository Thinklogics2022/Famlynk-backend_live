package com.famlynk.dto;

/**
 * 
 * @author josephine
 * @date 15-05-2023
 *
 */
public class FamilyTreeResponse {

	private String name;
	private String image;
	private String relationShip;
	private String uniqueUserID;
	private String gender;
	private String userId;
	private String fId;
	private String mId;
	private String bId;
	private String sId;
	private String wId;
	private String dId;
	private String sonId;
	private String uId;
	private String gMId;
	private String gFId;
	private String hId;
	private String email;
	private String dob;
	private String number;
	private String side;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(String relationShip) {
		this.relationShip = relationShip;
	}

	public String getUniqueUserID() {
		return uniqueUserID;
	}

	public void setUniqueUserID(String uniqueUserID) {
		this.uniqueUserID = uniqueUserID;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getfId() {
		return fId;
	}

	public void setfId(String fId) {
		this.fId = fId;
	}

	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public String getwId() {
		return wId;
	}

	public void setwId(String wId) {
		this.wId = wId;
	}

	public String getdId() {
		return dId;
	}

	public void setdId(String dId) {
		this.dId = dId;
	}

	public String getSonId() {
		return sonId;
	}

	public void setSonId(String sonId) {
		this.sonId = sonId;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getgMId() {
		return gMId;
	}

	public void setgMId(String gMId) {
		this.gMId = gMId;
	}

	public String getgFId() {
		return gFId;
	}

	public void setgFId(String gFId) {
		this.gFId = gFId;
	}

	public String gethId() {
		return hId;
	}

	public void sethId(String hId) {
		this.hId = hId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	@Override
	public String toString() {
		return "FamilyTreeResponse [name=" + name + ", image=" + image + ", relationShip=" + relationShip
				+ ", uniqueUserID=" + uniqueUserID + ", gender=" + gender + ", userId=" + userId + ", fId=" + fId
				+ ", mId=" + mId + ", bId=" + bId + ", sId=" + sId + ", wId=" + wId + ", dId=" + dId + ", sonId="
				+ sonId + ", uId=" + uId + ", gMId=" + gMId + ", gFId=" + gFId + ", hId=" + hId + ", email=" + email
				+ ", dob=" + dob + ", number=" + number + ", side=" + side + "]";
	}

}
