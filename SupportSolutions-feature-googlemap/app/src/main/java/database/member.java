package database;

/**
 * Created by luism on 4/28/2018.
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by luism on 7/30/2017.
 */

public class member {

    public String fname;
    public String lname;
    public String userId;
    public String email;
    public String password;
    public String date;
    public String security;
    public String gender;
    public String language;
    public String custody;
    public String phone;
    //default constructor for calls to DataSnapShot.getValue(User.class)
    public member()
    {   }

    public member(String fn,String ln,String p,String email,String password, String security,String g,String custody,String language)
    {
        this.fname=fn;
        this.lname=ln;
        this.phone = p;
        this.custody = custody;
        this.email=email;
        this.password=password;
        this.userId = getTokenizableId();
        this.security=security;
        date = new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date()).toString();
        this.gender=g;
        this.language=language;
    }

    public member(String fn,String ln,String email, String security,String g, String language)
    {
        this.fname=fn;
        this.lname=ln;
        this.email=email;
        this.userId = getTokenizableId();
        this.security=security;
        date = new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date()).toString();
        this.gender=g;
        this.language=language;
    }

    //this method creates an 15 digit string Id for our database
    public String getTokenizableId()
    {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder string = new StringBuilder();
        Random rnd = new Random();
        while (string.length() < 16) {
            int index = (int) (rnd.nextFloat() * characters.length());
            string.append(characters.charAt(index));
        }

        return  string.toString();
    }


    //setters
    public void setPhone(String phone)
    {this.phone=phone;}
    public void setFName(String name)
    {this.fname=name;}
    public void setLName(String name)
    {this.lname=name;}
    public void setEmail(String email)
    {this.email=email;}
    public void setPassword(String password)
    {this.password=password;}
    public void setSecurity(String security)
    {this.security = security;}
    public void setLanguage(String l)
    {this.language=l;}
    public void setGender(String gender)
    {this.gender=gender;}
    public void setCustody(String c)
    {this.custody=c;}




    //getters
    public String getSecurity()
    {return this.security;}
    public String getFirstName()
    {return this.fname;}
    public String getLastName()
    {return this.lname;}
    public String getEmail()
    {return this.email;}
    public String getPassword()
    {return this.password;}
    public String getUserId()
    {return this.userId;}
    public String getDate()
    {return this.date;}
    public String getGender()
    {return this.gender;}
    public String getLanguage()
    {return this.language;}
    public String getCustody()
    {return this.custody;}
    public String getPhone()
    {return this.phone;}




    public String toString()
    {return " name = "+fname+" userId = "+this.userId+" email = "+email;}

}
