package codebuds.poketmon.aggregate;

import java.io.Serializable;
import java.util.List;

public class Member implements Serializable {
    private int userNum;
    private String name;
    private String pw;
    private int age;
    private List<PoketMon> myPoketMons;
    private AccountStatus accountStatus;

    public Member() {
    }

    public Member(int userNum, String name, String pw, int age, List<PoketMon> myPoketMons, AccountStatus accountStatus) {
        this.userNum = userNum;
        this.name = name;
        this.pw = pw;
        this.age = age;
        this.myPoketMons = myPoketMons;
        this.accountStatus = accountStatus;
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<PoketMon> getMyPoketMons() {
        return myPoketMons;
    }

    public void setMyPoketMons(List<PoketMon> myPoketMons) {
        this.myPoketMons = myPoketMons;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public String toString() {
        return "Member{" +
                "userNum=" + userNum +
                ", name='" + name + '\'' +
                ", pw='" + pw + '\'' +
                ", age=" + age +
                ", myPoketMons=" + myPoketMons +
                ", accountStatus=" + accountStatus +
                '}';
    }
}
