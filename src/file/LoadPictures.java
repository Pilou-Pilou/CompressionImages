package file;

/**
 * <b>File :</b> LoadPictures.java<br>
 * <b>Creation date :</b> 26/10/15<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class LoadPictures {

	public String get(String path){
		return this.getClass().getClassLoader().getResource(path).getFile();
	}
	
	public String getUserFile(){
		return this.getClass().getClassLoader().getResource(Browser.askFile(null)).getFile();
	}
	

    /**
     *
     * @return the path of the picture face.
     */
    public String getPng(){
        return this.getClass().getClassLoader().getResource("face.png").getFile();
    }
	
    /**
     *
     * @return the path of the picture Baboon.
     */
    public String getBaboon(){
        return this.getClass().getClassLoader().getResource("Baboon.raw").getFile();
    }

    /**
    *
    * @return the path of the picture Barbara.
    */
    public String getBarbara(){
        return this.getClass().getClassLoader().getResource("Barbara.raw").getFile();
    }

    /**
    *
    * @return the path of the picture Goldhill.
    */
    public String getGoldhill(){
        return this.getClass().getClassLoader().getResource("Goldhill.raw").getFile();
    }

    /**
    *
    * @return the path of the picture Lena.
    */
    public String getLena(){
        return this.getClass().getClassLoader().getResource("Lena.raw").getFile();
    }

    /**
    *
    * @return the path of the picture Peppers.
    */
    public String getPeppers(){
        return this.getClass().getClassLoader().getResource("Peppers.raw").getFile();
    }
}
