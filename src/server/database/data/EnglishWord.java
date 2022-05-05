package server.database.data;

/**
 * @author: HMX
 * @className: EnglishWord
 * @description: 用于存放数据库english_word_list表的数据实体类
 * @date: 2022-04-07 16:52
 */
public class EnglishWord
{
    private String word;
    private String wordDes;

    public EnglishWord(String word, String wordDes)
    {
        this.word = word;
        this.wordDes = wordDes;
    }

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public String getWordDes()
    {
        return wordDes;
    }

    public void setWordDes(String wordDes)
    {
        this.wordDes = wordDes;
    }

}
