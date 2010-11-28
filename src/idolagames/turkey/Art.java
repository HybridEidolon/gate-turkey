package idolagames.turkey;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Art {
	private static Map<String,Image> images = new HashMap<String,Image>();
	
	public static Image getImage(String name) {
		// check if the image exists in our map
		if (images.containsKey(name)) {
			return images.get(name);
		} else {
			Image img = null;
			try {
				img = loadImage(name);
			} catch (IOException e) {
				System.err.println("Art error: Could not load "+name+".png");
				e.printStackTrace(System.err);
				return img;
			}
			images.put(name, img);
			return img;
		}
	}
	
	private static Image loadImage(String imagename) throws IOException {
		return ImageIO.read(Art.class.getResource("/img/"+imagename+".png"));
	}
	
	public static void unloadImage(String name) {
		images.remove(name);
	}
}
