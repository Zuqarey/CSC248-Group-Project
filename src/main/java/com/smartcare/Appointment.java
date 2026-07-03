package com.smartcare;

public class Appointment {
    private String date; 
    private String time; 

    public Appointment(String date, String time) {
        this.date = date;
        this.time = time;
    }

	//setter
	public void setDate(String date) {
		this.date = date;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	//getter
    public String getDate() {
    	return date;
    }

    public String getTime() {
    	return time;
    }

    public String toString() {
        return "\nDate : " + date + "\nTime : " + time;
    }
}