
public class MedicalRecord 
{
    private String careType;  
    private String details;    
    private double medicalFee;    
    private boolean isCritical;   

    public MedicalRecord(String careType, String details, double medicalFee, boolean isCritical) 
    {
        this.careType = careType;
        this.details = details;
        this.medicalFee = medicalFee;
        this.isCritical = isCritical;
    }

	//setter
	public void setCareType(String careType)
	{
		this.careType = careType;
	} 
	public void setDetails(String details)
	{
		this.details = details;
	}
	public void setMedicalFee(double medicalFee)
	{
		this.medicalFee = medicalFee;
	}
	public void setIsCritical(boolean isCritical)
	{
		this.isCritical = isCritical;
	}
	
	//getter
    public String getCareType() 
    { 
    	return careType; 
    }

	public String getDetails()
	{
		return details;
	}

    public double getMedicalFee() 
    { 
    	return medicalFee; 
    }
    public boolean getIsCritical() 
    { 
    	return isCritical; 
    }

    public String toString() {
        return "\ncare Type : " + careType + "\nDetails : " + details + "\nMedical Fee : RM" + medicalFee + "\nCritical : " + isCritical;
    }
}