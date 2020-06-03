/**
 * stocke les caractéristiques des attaques
 * contient aussi une méthode qui baisse les dégats d'une attaque
 */
public class Attaque{
    
    /**
     * le nom de l'attaque 
     */
    private String nom;
    
    /**
     * la portée de l'attaque
     */
    private int portee;
    
    /**
     * les dégats de l'attaque
     */
    private int degats;
  
    /**
     * Constructeur par défaut.
     * initialise le nom, la portée et les dégats de l'attaque
     */
    public Attaque(String nom, int p, int d){
        this.nom = nom;
        this.portee = p;
        this.degats = d;
    }
  
    /**
     * Constructeur alternatif.
     * permet au joueur de décider de rien faire en sélectionnant l'attaque 0 qui n'a aucun effet
     */
    public Attaque(){
        this.nom = "attaque nulle";
        this.portee = 0;
        this.degats = 0;
    }
  
    public String getNom(){
        return this.nom;  
    }
  
    public int getPortee(){
        return this.portee;
    }
  
    public int getDegats(){
        return this.degats;
    }
  
    /** réduit les dégats de l'attaque.
     * appelée après chaque attaque pour décourager le joueur d'utiliser tout le temps la même capacité
     * et qu'il y ait ainsi plus de dynamisme dans la partie
     * @see Jeu.phaseAttaque
     */
    public void baisseDegats(){  
        this.degats= (int) Math.ceil(this.degats * 0.8);
    }
  
}

