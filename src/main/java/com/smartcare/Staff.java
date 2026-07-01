package src.main.java.com.smartcare;
public class Staff extends Person 
{
    private int staffID;
    private String password;
    private String role; 
    private MedicalRecord medRec;// Assuming a staff member can have patient medical record
    private Appointment App; // Assuming a staff member can have patient appointment

    public Staff(String nric, String name, String phoneNumber, int staffID, String password, String role, MedicalRecord medRec, Appointment App) 
    {
        super(nric, name, phoneNumber);
        this.staffID = staffID;
        this.password = password;
        this.role = role;
        this.medRec = medRec;
        this.App = App;
    }
	
	//setter
	public void setStaffID(int staffID)
	{
		this.staffID = staffID;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public void setRole(String role)
	{
		this.role = role;
	}
    public void setMedRec(MedicalRecord medRec) 
    {
        this.medRec = medRec;
    }
    public void setApp(Appointment App) 
    {
        this.App = App;
    }
	
	//getter
    public int getStaffID() 
    { 
    	return staffID; 
    }
    public String getPassword()
    {
    	return password;
    }
    public String getRole()
    {
    	return role;
    }
    public MedicalRecord getMedRec() 
    {
        return medRec;
    }
    public Appointment getApp() 
    {
        return App;
    }
    // benda ni akan semak password dengan id (kalau id atau pass salah auto dua dua salah)
    public boolean login(int id, String pass) 
    {
        return (this.staffID == id && this.password.equals(pass));
    }

    @Override
    public String toString() 
    {
        return super.toString() + "Staff ID : " + staffID + "\nRole Of Doctor : " + role + "\nMedical Record : " + medRec + "\nAppointment : " + App;
    }
}