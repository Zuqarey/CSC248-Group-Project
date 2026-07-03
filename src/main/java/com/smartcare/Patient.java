package com.smartcare;

public class Patient extends Person 
{
    private String patientID;
    private MedicalRecord medRec; 
    private Appointment App;    

    public Patient(String nric, String name, String phoneNumber, String patientID, MedicalRecord medRec, Appointment App) 
    {
        super(nric, name, phoneNumber); 
        this.patientID = patientID;
        this.medRec = medRec;
        this.App = App;
    }

	public void setPatientID(String patientID)
	{
		this.patientID = patientID;
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
    public String getPatientID() 
    { 
    	return patientID; 
    }
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
        return super.toString() + "Patient ID : " + patientID + medRec.toString() + "\n" + App.toString();
    }

    public boolean login(String nric) {
        if (nric.equals(this.nric)) {
            return true;
        } else {
		return false;
	}
    }
}
