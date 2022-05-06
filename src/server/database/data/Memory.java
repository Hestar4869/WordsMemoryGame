package server.database.data;

import java.io.Serializable;

/**
 * @className: Memory
 * @description: 记忆的单词
 * @author: HMX
 * @date: 2022-05-05 18:53
 */
public class Memory implements Serializable
{
    private boolean isMaster;
    private String word;
    private String username;

    //构造函数
    public Memory(boolean isMaster, String word, String username)
    {
        this.isMaster = isMaster;
        this.word = word;
        this.username = username;
    }

    public boolean isMaster()
    {
        return isMaster;
    }

    public void setMaster(boolean master)
    {
        isMaster = master;
    }

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }


}
