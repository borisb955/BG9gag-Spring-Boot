package com.bg.model;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class Profile {
	private long profileId;
	private String avatarUrl;
	@Pattern(regexp="^[\\p{L} .'-]+$", message="Your name must contains only letters")
	private String fullName;
	@Size(max=10, message = "gender can't be more than {max} characters")
	private String gender;
	@Past(message = "You have entered a date in the future ...")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dateOfBirth;
	@Size(max=50, message ="Please enter a brief description shorter than {max} characters")
	private String info;
	private SocialNetworking socNet;
	
	public Profile() {}
	
	public Profile(String avatarUrl, String fullName, String gender, Date dateOfBirth, String info) {
		this.avatarUrl = avatarUrl;
		this.fullName = fullName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.info = info;
	}
	public Profile(long profileId,String avatarUrl, String fullName, String gender, Date dateOfBirth,
			String info) {
		this(avatarUrl, fullName, gender, dateOfBirth, info);
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
