package com.rld.iodemo.pojo;


import java.util.Date;

public class Employee {

    private String firstName;
    private String lastName;
    private String sex;
    private String favColor;
    private String birthDay;
    private Date dateForCompare;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFavColor() {
        return favColor;
    }

    public void setFavColor(String favColor) {
        this.favColor = favColor;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public Date getDateForCompare() {
        return dateForCompare;
    }

    public void setDateForCompare(Date dateForCompare) {
        this.dateForCompare = dateForCompare;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex='" + sex + '\'' +
                ", favColor='" + favColor + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", dateForCompare=" + dateForCompare +
                '}';
    }
}
