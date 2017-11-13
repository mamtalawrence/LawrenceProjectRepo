package com.lawrence.ditrp.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Anagha.Mahajan on 07-Nov-17.
 */
public class StudentData {


    @SerializedName("STUDENT_ID")
    @Expose
    private String mStudentID;

    @SerializedName("INSTITUTE_NAME")
    @Expose
    private String mInstituteName;

    @SerializedName("STUDENT_CODE")
    @Expose
    private String mStudentCode;

    @SerializedName("STUDENT_FNAME")
    @Expose
    private String mStudentFirstName;

    @SerializedName("STUDENT_MNAME")
    @Expose
    private String mStudentMiddleName;

    @SerializedName("STUDENT_LNAME")
    @Expose
    private String mStudentLastName;

    @SerializedName("STUDENT_DOB")
    @Expose
    private String mStudentDOB;

    @SerializedName("STUDENT_GENDER")
    @Expose
    private String mStudentGender;

    @SerializedName("STUDENT_MOBILE")
    @Expose
    private String mStudentMobileNo;

    @SerializedName("STUDENT_EMAIL")
    @Expose
    private String mStudentEmail;

    @SerializedName("STUDENT_ADDRESS")
    @Expose
    private Object mStudentAddress;

    @SerializedName("STUDENT_COURSES")
    @Expose
    private List<StudentCourse> mStudentCourses = null;

    public String getStudentID() {
        return mStudentID;
    }

    public void setStudentID(String mStudentID) {
        this.mStudentID = mStudentID;
    }

    public String getInstituteName() {
        return mInstituteName;
    }

    public void setInstituteName(String mInstituteName) {
        this.mInstituteName = mInstituteName;
    }

    public String getStudentCode() {
        return mStudentCode;
    }

    public void setStudentCode(String mStudentCode) {
        this.mStudentCode = mStudentCode;
    }

    public String getStudentFirstName() {
        return mStudentFirstName;
    }

    public void setStudentFirstName(String mmStudentFirstName) {
        this.mStudentFirstName = mmStudentFirstName;
    }

    public String getStudentMiddleName() {
        return mStudentMiddleName;
    }

    public void setStudentMiddleName(String mmStudentMiddleName) {
        this.mStudentMiddleName = mmStudentMiddleName;
    }

    public String getStudentLastName() {
        return mStudentLastName;
    }

    public void setStudentLastName(String mStudentLastName) {
        this.mStudentLastName = mStudentLastName;
    }

    public String getStudentDOB() {
        return mStudentDOB;
    }

    public void setStudentDOB(String mmStudentDOB) {
        this.mStudentDOB = mmStudentDOB;
    }

    public String getStudentGender() {
        return mStudentGender;
    }

    public void setStudentGender(String mStudentGender) {
        this.mStudentGender = mStudentGender;
    }

    public String getStudentMobileNo() {
        return mStudentMobileNo;
    }

    public void setStudentMobileNo(String mStudentMobileNo) {
        this.mStudentMobileNo = mStudentMobileNo;
    }

    public String getStudentEmail() {
        return mStudentEmail;
    }

    public void setStudentMail(String mStudentEmail) {
        this.mStudentEmail = mStudentEmail;
    }

    public Object getStudentAddress() {
        return mStudentAddress;
    }

    public void setStudentAddress(Object mStudentAddress) {
        this.mStudentAddress = mStudentAddress;
    }

    public List<StudentCourse> getStudentCourses() {
        return mStudentCourses;
    }

    public void setStudentCourses(List<StudentCourse> studentCourses) {
        this.mStudentCourses = studentCourses;
    }

}
