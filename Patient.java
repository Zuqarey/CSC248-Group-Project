public class Patient extends Person 
{
    private String patientID;
    private MedicalRecord medicalRecord; 
    private Appointment appointment;    

    public Patient(String nric, String name, String phoneNumber, String patientID, MedicalRecord medicalRecord, Appointment appointment) 
    {
        super(nric, name, phoneNumber); 
        this.patientID = patientID;
        this.medicalRecord = medicalRecord;
        this.appointment = appointment;
    }

	public void setPatientID(String patientID)
	{
		this.patientID = patientID;
	}
	public void setMedicalRecord(MedicalRecord medicalRecord)
	{
		this.medicalRecord = medicalRecord;
	} 
	public void setAppointment(Appointment appointment)
	{
		this.appointment = appointment;
	}
	
	//getter
    public String getPatientID() 
    { 
    	return patientID; 
    }
    public MedicalRecord getMedicalRecord() 
    { 
    	return medicalRecord; 
    }
    public Appointment getAppointment() 
    { 
    	return appointment; 
    }

    @Override
    public String toString() 
    {
        return super.toString() + "Patient ID : " + patientID + medicalRecord.toString() + "\n" + appointment.toString();
    }
}