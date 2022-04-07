package server.database.data;

/**
 * @author: HMX
 * @className User
 * @description 用于存放数据库user表的数据实体类
 * @date: 2022-04-07 16:33
 */
public class User
{
    private String username;
    private String password;

    //构造函数
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    //对应的setter和getter
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
