package com.cloudApp.pojo;

import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.entity.DaysOfWeek;
import java.time.LocalTime;
import java.util.List;

// Ovo je pomocna klasa koja predstavlja izvod iz company_order_has_days_of_week DB tabele
// za 1 CompanyOrder.
public class CompanyOrderWorkingDaysPresenter {

    private CompanyOrder companyOrder;
    private List<DaysOfWeek> workingDays;
    private LocalTime workingDayStartTime, workingDayStopTime;
    private LocalTime weekendDayStartTime, weekendDayStopTime;

    public CompanyOrder getCompanyOrder() {
        return companyOrder;
    }

    public void setCompanyOrder(CompanyOrder companyOrder) {
        this.companyOrder = companyOrder;
    }

    public List<DaysOfWeek> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<DaysOfWeek> workingDays) {
        this.workingDays = workingDays;
    }

    public LocalTime getWorkingDayStartTime() {
        return workingDayStartTime;
    }

    public void setWorkingDayStartTime(LocalTime workingDayStartTime) {
        this.workingDayStartTime = workingDayStartTime;
    }

    public LocalTime getWorkingDayStopTime() {
        return workingDayStopTime;
    }

    public void setWorkingDayStopTime(LocalTime workingDayStopTime) {
        this.workingDayStopTime = workingDayStopTime;
    }

    public LocalTime getWeekendDayStartTime() {
        return weekendDayStartTime;
    }

    public void setWeekendDayStartTime(LocalTime weekendDayStartTime) {
        this.weekendDayStartTime = weekendDayStartTime;
    }

    public LocalTime getWeekendDayStopTime() {
        return weekendDayStopTime;
    }

    public void setWeekendDayStopTime(LocalTime weekendDayStopTime) {
        this.weekendDayStopTime = weekendDayStopTime;
    }

}
