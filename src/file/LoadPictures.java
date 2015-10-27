package file;

/**
 * <b>File :</b> file.LoadPictures.java<br>
 * <b>Creation date :</b> 26/10/15<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class LoadPictures {

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
