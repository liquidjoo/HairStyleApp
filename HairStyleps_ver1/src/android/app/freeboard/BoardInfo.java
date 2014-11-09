package android.app.freeboard;

/**
 * Ʈ���� �˻� ����� ������ Ŭ����
 * 
 * @author croute
 * @since 2011.05.04
 */
public class BoardInfo
{

	private String email;
	private String comment;
	private String time;
	private int index;

	public int getIndex()
	{
		return index;
	}
	public void setIndex(int index)
	{
		this.index = index;		
	}
	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time=time;
	}

	
}
