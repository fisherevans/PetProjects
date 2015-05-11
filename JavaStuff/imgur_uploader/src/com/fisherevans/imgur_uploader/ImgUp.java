package info.fisherevans.imguruploader;

public class ImgUp
{
	public static Menu menu;
	public static Uploader uploader;
	
	public static void main(String[] args)
	{
		uploader = new Uploader();
		menu = new Menu();
	}
}
