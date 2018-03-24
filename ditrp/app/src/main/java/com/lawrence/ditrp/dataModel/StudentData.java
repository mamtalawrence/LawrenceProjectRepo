package com.lawrence.ditrp.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentData {
    @SerializedName("INSTITUTE_ID")
    @Expose
    private String sINSTITUTEID;
    @SerializedName("STUDENT_ID")
    @Expose
    private String sTUDENTID;
    @SerializedName("INSTITUTE_NAME")
    @Expose
    private String iNSTITUTENAME;
    @SerializedName("STUDENT_CODE")
    @Expose
    private String sTUDENTCODE;
    @SerializedName("STUDENT_FNAME")
    @Expose
    private String sTUDENTFNAME;
    @SerializedName("STUDENT_MNAME")
    @Expose
    private String sTUDENTMNAME;
    @SerializedName("STUDENT_LNAME")
    @Expose
    private String sTUDENTLNAME;
    @SerializedName("STUDENT_DOB")
    @Expose
    private String sTUDENTDOB;
    @SerializedName("STUDENT_GENDER")
    @Expose
    private String sTUDENTGENDER;
    @SerializedName("STUDENT_MOBILE")
    @Expose
    private String sTUDENTMOBILE;
    @SerializedName("STUDENT_EMAIL")
    @Expose
    private String sTUDENTEMAIL;
    @SerializedName("STUDENT_ADDRESS")
    @Expose
    private Object sTUDENTADDRESS;
    @SerializedName("INSTITUTE_CODE")
    @Expose
    private String iNSTITUTECODE;
    @SerializedName("INSTITUTE_ADDRESS")
    @Expose
    private String iNSTITUTEADDRESS;
    @SerializedName("INSTITUTE_CITY")
    @Expose
    private String iNSTITUTECITY;
    @SerializedName("INSTITUTE_STATE")
    @Expose
    private String iNSTITUTESTATE;
    @SerializedName("INSTITUTE_POSTCODE")
    @Expose
    private String iNSTITUTEPOSTCODE;
    @SerializedName("INSTITUTE_EMAIL")
    @Expose
    private String iNSTITUTEEMAIL;
    @SerializedName("INSTITUTE_MOBILE")
    @Expose
    private String iNSTITUTEMOBILE;
    @SerializedName("STUDENT_COURSES")
    @Expose
    private List<StudentCourse> sTUDENTCOURSES = null;

    public String getINSTITUTEID() {
        return sINSTITUTEID;
    }

    public String getSTUDENTID() {
        return sTUDENTID;
    }

    public void setSTUDENTID(String sTUDENTID) {
        this.sTUDENTID = sTUDENTID;
    }

    public String getINSTITUTENAME() {
        return iNSTITUTENAME;
    }

    public void setINSTITUTENAME(String iNSTITUTENAME) {
        this.iNSTITUTENAME = iNSTITUTENAME;
    }

    public String getSTUDENTCODE() {
        return sTUDENTCODE;
    }

    public void setSTUDENTCODE(String sTUDENTCODE) {
        this.sTUDENTCODE = sTUDENTCODE;
    }

    public String getSTUDENTFNAME() {
        return sTUDENTFNAME;
    }

    public void setSTUDENTFNAME(String sTUDENTFNAME) {
        this.sTUDENTFNAME = sTUDENTFNAME;
    }

    public String getSTUDENTMNAME() {
        return sTUDENTMNAME;
    }

    public void setSTUDENTMNAME(String sTUDENTMNAME) {
        this.sTUDENTMNAME = sTUDENTMNAME;
    }

    public String getSTUDENTLNAME() {
        return sTUDENTLNAME;
    }

    public void setSTUDENTLNAME(String sTUDENTLNAME) {
        this.sTUDENTLNAME = sTUDENTLNAME;
    }

    public String getSTUDENTDOB() {
        return sTUDENTDOB;
    }

    public void setSTUDENTDOB(String sTUDENTDOB) {
        this.sTUDENTDOB = sTUDENTDOB;
    }

    public String getSTUDENTGENDER() {
        return sTUDENTGENDER;
    }

    public void setSTUDENTGENDER(String sTUDENTGENDER) {
        this.sTUDENTGENDER = sTUDENTGENDER;
    }

    public String getSTUDENTMOBILE() {
        return sTUDENTMOBILE;
    }

    public void setSTUDENTMOBILE(String sTUDENTMOBILE) {
        this.sTUDENTMOBILE = sTUDENTMOBILE;
    }

    public String getSTUDENTEMAIL() {
        return sTUDENTEMAIL;
    }

    public void setSTUDENTEMAIL(String sTUDENTEMAIL) {
        this.sTUDENTEMAIL = sTUDENTEMAIL;
    }

    public Object getSTUDENTADDRESS() {
        return sTUDENTADDRESS;
    }

    public void setSTUDENTADDRESS(Object sTUDENTADDRESS) {
        this.sTUDENTADDRESS = sTUDENTADDRESS;
    }

    public String getINSTITUTECODE() {
        return iNSTITUTECODE;
    }

    public void setINSTITUTECODE(String iNSTITUTECODE) {
        this.iNSTITUTECODE = iNSTITUTECODE;
    }

    public String getINSTITUTEADDRESS() {
        return iNSTITUTEADDRESS;
    }

    public void setINSTITUTEADDRESS(String iNSTITUTEADDRESS) {
        this.iNSTITUTEADDRESS = iNSTITUTEADDRESS;
    }

    public String getINSTITUTECITY() {
        return iNSTITUTECITY;
    }

    public void setINSTITUTECITY(String iNSTITUTECITY) {
        this.iNSTITUTECITY = iNSTITUTECITY;
    }

    public String getINSTITUTESTATE() {
        return iNSTITUTESTATE;
    }

    public void setINSTITUTESTATE(String iNSTITUTESTATE) {
        this.iNSTITUTESTATE = iNSTITUTESTATE;
    }

    public String getINSTITUTEPOSTCODE() {
        return iNSTITUTEPOSTCODE;
    }

    public void setINSTITUTEPOSTCODE(String iNSTITUTEPOSTCODE) {
        this.iNSTITUTEPOSTCODE = iNSTITUTEPOSTCODE;
    }

    public String getINSTITUTEEMAIL() {
        return iNSTITUTEEMAIL;
    }

    public void setINSTITUTEEMAIL(String iNSTITUTEEMAIL) {
        this.iNSTITUTEEMAIL = iNSTITUTEEMAIL;
    }

    public String getINSTITUTEMOBILE() {
        return iNSTITUTEMOBILE;
    }

    public void setINSTITUTEMOBILE(String iNSTITUTEMOBILE) {
        this.iNSTITUTEMOBILE = iNSTITUTEMOBILE;
    }

    public List<StudentCourse> getStudentCourses() {
        return sTUDENTCOURSES;
    }

    public void setSTUDENTCOURSES(List<StudentCourse> sTUDENTCOURSES) {
        this.sTUDENTCOURSES = sTUDENTCOURSES;
    }

}
