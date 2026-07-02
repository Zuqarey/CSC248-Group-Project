package src.main.java.com.smartcare;
public class Staff extends Person 
{
    private int staffID;
    private String password;
    private String role; 

    public Staff(String nric, String name, String phoneNumber, int staffID, String password, String role) 
    {
        super(nric, name, phoneNumber);
        this.staffID = staffID;
        this.password = password;
        this.role = role;
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

    // benda ni akan semak password dengan id (kalau id atau pass salah auto dua dua salah)
    public boolean login(int id, String pass) 
    {
        return (this.staffID == id && this.password.equals(pass));
    }

    @Override
    public String toString() 
    {
        return super.toString() + "Staff ID : " + staffID + "\nRole Of Doctor : " + role;
    }
}