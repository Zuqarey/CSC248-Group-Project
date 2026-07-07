package com.smartcare;

public abstract class Person 
{
    protected String nric; 
    protected String name;
    protected String phoneNumber;

    public Person(String nric, String name, String phoneNumber) 
    {
        this.nric = nric;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    
    //setter
    public void setNRIC(String nric)
    {
    	this.nric = nric;
    }
    public void setName(String name)
    {
    	this.name = name;
    }
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	//getter
    public String getNRIC() 
    { 
    	return nric; 
    }
    public String getName() 
    { 
    	return name;
    }
    public String getPhoneNumber() 
    { 
    	return phoneNumber; 
    }
    public String getBirthdate() 
    {
        String year = nric.substring(0, 2);
        String month = nric.substring(2, 4);
        String day = nric.substring(4, 6);
        
        int yearInt = Integer.parseInt(year); //tukar string ke no. bolat
        String fullYear;
        
        if(yearInt > 26)
        	fullYear = "19" + year;
        else
        	fullYear = "20" + year;
        
        return day + "-" + month + "-" + fullYear;
    }
	
    public String getState() 
    {
        String stateCode = nric.substring(6, 8);
        
        switch (stateCode) 
        {
            case "01": case "21": case "22": case "23": case "24": return "Johor";
            case "02": case "25": case "26": case "27": return "Kedah";
            case "03": case "28": case "29": return "Kelantan";
            case "04": case "30": return "Melaka";
            case "05": case "31": case "59": return "Negeri Sembilan";
            case "06": case "32": case "33": return "Pahang";
            case "07": case "34": case "35": return "Pulau Pinang";
            case "08": case "36": case "37": case "38": case "39": return "Perak";
            case "09": case "40": return "Perlis";
            case "10": case "41": case "42": case "43": case "44": return "Selangor";
            case "11": case "45": case "46": return "Terengganu";
            case "12": case "47": case "48": return "Sabah";
            case "13": case "49": case "50": case "51": case "52": case "53": return "Sarawak";
            case "14": case "54": case "55": case "56": return "Wilayah Persekutuan Kuala Lumpur";
            case "15": case "57": return "Wilayah Persekutuan Labuan";
            case "16": case "58": return "Wilayah Persekutuan Putrajaya";
            default: return "Luar Negara / Tidak Diketahui";
        }
    }

    public String getGender() 
    {
        String genderP = nric.substring(11, 12);
        int lastDigit = Integer.parseInt(genderP); //tukar string ke no. bolat
        
        if (lastDigit % 2 == 0) 
        {
            return "Male";
        } 
        else 
        {
            return "Female";
        }
    }
	
	//printer
    public String toString() 
    {
        return "NRIC: " + nric + "\nName : " + name + "\nNumber Phone : " + phoneNumber 
        	+ "\nBirthday Of Date : " + getBirthdate() 
        	+ "\nState : " + getState() 
        	+ "\nGender : " + getGender();
    }
}