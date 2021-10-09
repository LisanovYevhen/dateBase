package util;

public class UserValue {
    private String beginOfWork;
    private String endOfWork;
    private String breakTime;
    private String workShift;
    private String ratePerHour;
    private String totalHour;
    private String totalMoney;

    public UserValue(String date) {
    }

    public UserValue(String beginOfWork, String endOfWork, String breakTime, String workShift, String ratePerHour, String totalHour, String totalMoney) {
        this.beginOfWork = beginOfWork;
        this.endOfWork = endOfWork;
        this.breakTime = breakTime;
        this.workShift = workShift;
        this.ratePerHour = ratePerHour;
        this.totalHour = totalHour;
        this.totalMoney = totalMoney;
    }

    public String getTotalHour() {
        return totalHour;
    }

    public String getTotalMoney() {
        return totalMoney;
    }


    public String getBeginOfWork() {
        return beginOfWork;
    }

    public String getEndOfWork() {
        return endOfWork;
    }

    public String getBreakTime() {
        return breakTime;
    }

    public String getWorkShift() {
        return workShift;
    }

    public String getRatePerHour() {
        return ratePerHour;
    }
}
