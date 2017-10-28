package com.bg.model;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class Profile {
	private long profileId;
	private String avatarUrl;
	private String fullName;
	@Size(max=10, message = "gendet can't be more than {max} characters")
	private String gender;
	@Past
	private Date dateOfBirth;
	@Size(max=50, message ="Please enter a brief description shorter than {max} characters")
	private String info;
	private SocialNetworking socNet;
	
	public Profile() {}
	
	public Profile(String avatarUrl, String fullName, String gender, Date dateOfBirth, String info,
			SocialNetworking socNet) {
		this.avatarUrl = avatarUrl;
		this.fullName = fullName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.info = info;
		this.socNet = socNet;
	}
	
	public Profile(long profileId,String avatarUrl, String fullName, String gender, Date dateOfBirth, String info,
			SocialNetworking socNet) {
		this(avatarUrl, fullName, gender, dateOfBirth, info, socNet);
		this.profileId = profileId;
	}

	public long getProfileId() {
		return profileId;
	}

	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public String getFullName() {
		return fullName;
	}

	public String getGender() {
		return gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public String getInfo() {
		return info;
	}

	public SocialNetworking getSocNet() {
		return socNet;
	}
	
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setSocNet(SocialNetworking socNet) {
		this.socNet = socNet;
	}
}
