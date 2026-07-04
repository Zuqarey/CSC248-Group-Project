package com.smartcare;

public class Patient extends Person 
{
    
    private MedicalRecord medRec; 
    private Appointment App;    

    public Patient(String nric, String name, String phoneNumber, MedicalRecord medRec, Appointment App) {
        super(nric, name, phoneNumber); 
        this.medRec = medRec;
        this.App = App;
    }

	public void setMedicalRecord(MedicalRecord medicalRecord)
	{
		this.medRec = medicalRecord;
	} 
	public void setAppointment(Appointment appointment)
	{
		this.App = appointment;
	}
	
	//getter

    public MedicalRecord getMedicalRecord() 
    { 
    	return medRec; 
    }
    public Appointment getAppointment() 
    { 
    	return App; 
    }

    @Override
    public String toString() 
    {
        return super.toString() + medRec.toString() + "\n" + App.toString();
    }

    public boolean login(String nric) {
        if (nric.equals(this.nric)) {
            return true;
        } else {
		return false;
	}
    }
}
