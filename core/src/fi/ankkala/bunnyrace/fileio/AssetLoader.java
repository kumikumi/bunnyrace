package fi.ankkala.bunnyrace.fileio;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class AssetLoader {
	//private static final String[] directories = {"libre/", "thirdparty/", "proprietary/"};
	private static final String[] directories = {"libre/", "thirdparty/"};
	//private static final String[] directories = {"proprietary/", "thirdparty/", "libre/"};

	public static FileHandle load(String path) {
		FileHandle file = null;
		for (String directory: directories) {
			file = Gdx.files.internal(directory+path);
			if (file.exists()) {
				return file;
			}
		}
		return Gdx.files.internal("notfound/"+path);
	}
}
