public class Attaque{
  private String nom;
  private int portee;
  private int degats;
  
  public Attaque(String nom, int p, int d){ // Quand on crée une attaque on entre directement nom, portée et dégâts
    this.nom = nom;
    this.portee = p;
    this.degats = d;
  }
  
  public Attaque(){ // Cette méthode permet au joueur de décider de rien faire en sélectionnant l'attaque 0 qui n'a aucun effet
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
  
  /** Cette méthode réduit les dégats de chaque attaque après utilisation pour décourager le joueur d'utiliser tout le temps la même capacité
   * et qu'il y ait ainsi plus de dynamisme dans la partie
   */
  public void baisseDegats(){  
  	this.degats= (int) Math.ceil(this.degats * 0.8);
  }
  
}

