package util;

import androidx.fragment.app.Fragment;

public class UserInformation {
    private String userEmail;
    private String providerId;
    private String phoneNumber;

    public UserInformation(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserInformation(String userEmail, String displayName, String phoneNumber) {
        this.userEmail = userEmail;
        this.providerId = displayName;
        this.phoneNumber = phoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getDisplayName() {
        return providerId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
