package waverr.project.com.Proxinode;

/**
 * Created by Nikhil on 8/11/2015.
 */
public class Contact {

    private String name;
    private String email;
    private String phone;
    private String title;
    private String description;
    private String url;


    public void setName(String name){this.name=name;}
    public void setEmail(String email){this.email=email;}
    public void setPhone(String phone){this.phone=phone;}
    public void setTitle(String title){this.title=title;}
    public void setDescription(String description){this.description=description;}
    public void setUrl(String url){this.url=url;}

    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getPhone(){return phone;}
    public String getTitle(){return title;}
    public String getDescription(){return description;}
    public String getUrl(){return url;}


}
