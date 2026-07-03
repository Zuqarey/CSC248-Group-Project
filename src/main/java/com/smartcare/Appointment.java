package com.smartcare;

public class Appointment 
{
    private String appointmentId;
    private String date; 
    private String time; 

    public Appointment(String appointmentId, String date, String time) 
    {
        this.appointmentId = appointmentId;
        this.date = date;
        this.time = time;
    }

	//setter
	public void setAppointmentId(String appointmentId)
	{
		this.appointmentId = appointmentId;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	
	//getter
    public String getAppointmentId() 
    { 
    	return appointmentId; 
    }
    public String getDate()
    {
    	return date;
    }
    public String getTime()
    {
    	return time;
    }

    public String toString() 
    {
        return "\nAppointment ID : " + appointmentId + "\nDate : " + date + "\nTime : " + time;
    }
}