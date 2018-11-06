public class Config{
	private String charset;
	public Config(){
		this.charset = "UTF-8";
	}
	public void setCharset(String charset){
		this.charset = charset;
	}
	public String getCharset(){
		return this.charset;
	}
}