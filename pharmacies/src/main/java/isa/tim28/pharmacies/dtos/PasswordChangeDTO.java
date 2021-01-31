package isa.tim28.pharmacies.dtos;

public class PasswordChangeDTO {

	private String oldPassword;
	private String newPassword;
	private String newPasswordRepeat;
	
	public PasswordChangeDTO() {
		super();
	}
	
	public PasswordChangeDTO(String oldPassword, String newPassword, String newPasswordRepeat) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.newPasswordRepeat = newPasswordRepeat;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordRepeat() {
		return newPasswordRepeat;
	}

	public void setNewPasswordRepeat(String newPasswordRepeat) {
		this.newPasswordRepeat = newPasswordRepeat;
	}
	
	public boolean isValid() {
		if(!this.newPassword.equals(this.newPasswordRepeat)) return false;
		else if(this.newPassword.equals("") || this.oldPassword.equals("") || this.newPasswordRepeat.equals("")) return false;
		else if(this.newPassword.length() < 8) return false;
		return true;
	}
}
