public class Attaque{
  private String nom;
  private int portee;
  private int degats;
  
  public Attaque(String nom, int p, int d){ // Quand on crée une attaque on entre directement nom, portée et dégâts
    this.nom = nom;
    this.portee = p;
    this.degats = d;
  }
  
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
  
  public void baisseDegats(){
  	this.degats= (int) Math.ceil(this.degats * 0.8);
  }
  
}

