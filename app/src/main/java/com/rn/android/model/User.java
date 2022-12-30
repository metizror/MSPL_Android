package com.rn.android.model;

import java.io.Serializable;

/**
 * Created by hp on 20/3/18.
 */

public class User implements Serializable {
    /**
     * UserID : 58
     * UserRoleID : 2
     * FirstName : Test
     * LastName : Demo
     * UserName : testdemo
     * ContactNo : 1234567890
     * UserPositionName : mobile
     * Email : test@demo.com
     * UserPosition : null
     * Active : true
     * CreatedOn : 01/03/18
     * CreatedBy : 1
     * UpdateOn : 01/03/18
     * UpdateBy : 1
     * UserRole : 1
     * RoleName : Data Entry
     */

    private int UserID;
    private int UserRoleID;
    private String FirstName;
    private String LastName;
    private String UserName;
    private String ContactNo;
    private String UserPositionName;
    private String Email;
    private Object UserPosition;
    private boolean Active;
    private String CreatedOn;
    private int CreatedBy;
    private String UpdateOn;
    private int UpdateBy;
    private int UserRole;
    private String RoleName;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public int getUserRoleID() {
        return UserRoleID;
    }

    public void setUserRoleID(int UserRoleID) {
        this.UserRoleID = UserRoleID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String ContactNo) {
        this.ContactNo = ContactNo;
    }

    public String getUserPositionName() {
        return UserPositionName;
    }

    public void setUserPositionName(String UserPositionName) {
        this.UserPositionName = UserPositionName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public Object getUserPosition() {
        return UserPosition;
    }

    public void setUserPosition(Object UserPosition) {
        this.UserPosition = UserPosition;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean Active) {
        this.Active = Active;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String CreatedOn) {
        this.CreatedOn = CreatedOn;
    }

    public int getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(int CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public String getUpdateOn() {
        return UpdateOn;
    }

    public void setUpdateOn(String UpdateOn) {
        this.UpdateOn = UpdateOn;
    }

    public int getUpdateBy() {
        return UpdateBy;
    }

    public void setUpdateBy(int UpdateBy) {
        this.UpdateBy = UpdateBy;
    }

    public int getUserRole() {
        return UserRole;
    }

    public void setUserRole(int UserRole) {
        this.UserRole = UserRole;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String RoleName) {
        this.RoleName = RoleName;
    }
}
