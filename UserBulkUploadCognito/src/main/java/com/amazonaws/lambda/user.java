package com.amazonaws.lambda;

import com.opencsv.bean.CsvBindByName;

public class user {
	@CsvBindByName(column = "FirstName")
    private String FirstName;

    @CsvBindByName(column = "MiddleName")
    private String MiddleName;

    @CsvBindByName(column = "LastName")
    private String LastName;

    @CsvBindByName(column = "EmployeeId")
    private String EmployeeId;

    @CsvBindByName(column = "Level")
    private String Level;

    @CsvBindByName(column = "EmailId")
    private String EmailId;

    @CsvBindByName(column = "Supervisor")
    private String Supervisor;

    @CsvBindByName(column = "OrgId")
    private String OrgId;

    public user() {
        super();
    }

    public user(String firstName, String middleName, String lastName, String employeeId, String level, String emailId,
                String supervisor, String orgId) {
        super();
        FirstName = firstName;
        MiddleName = middleName;
        LastName = lastName;
        EmployeeId = employeeId;
        Level = level;
        EmailId = emailId;
        Supervisor = supervisor;
        OrgId = orgId;
    }


    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getSupervisor() {
        return Supervisor;
    }

    public void setSupervisor(String supervisor) {
        Supervisor = supervisor;
    }

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

}
