package by.bsac.telephonestation.domain;

import java.time.Year;

public class Landline {
    private long id;
    private int phoneNumber;
    private Year setupYear;
    private User user;

    public Landline() {
    }

    public Landline(long id, int phoneNumber, Year setupYear, User user) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.setupYear = setupYear;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Year getSetupYear() {
        return setupYear;
    }

    public void setSetupYear(Year setupYear) {
        this.setupYear = setupYear;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Landline landline = (Landline) o;

        if (id != landline.id) return false;
        if (phoneNumber != landline.phoneNumber) return false;
        if (!setupYear.equals(landline.setupYear)) return false;
        return user.equals(landline.user);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + phoneNumber;
        result = 31 * result + setupYear.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "#" + id +
                ": phoneNumber=" + phoneNumber +
                ", setupYear=" + setupYear +
                ". (user=" + user +
                ')';
    }
}
