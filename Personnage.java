

public class Personnage{
    private String joueur;
    private String nom;
    private String symbole;
    private boolean joueurActif;
    private int pV;
    private int vitesse;
    private int mouvRestant;
    private Attaque attaque1;
    private Attaque attaque2;
    private Attaque attaque3;
    private int cooldownAtk3;
    private int x;
    private int y;
    
    
    public Personnage(){ //Constructeur par défaut, on pourra le supprimer quand on aura défini les persos
        this.nom = "personne";
        this.pV = 1;
        this.vitesse = 1;
        this.attaque1 = new Attaque("rien", 1, 1);
        this.attaque2 = new Attaque("rien", 1, 1);
        this.attaque3 = new Attaque("rien", 1, 1);
        this.x= 1;
        this.y=1;
    }
    
    public Personnage(String joueur){
        this.joueur = joueur;
    }
    
    public Personnage(String joueur, int i){
        this.joueur = joueur;
        this.cooldownAtk3 = 4;
        switch(i){
		case 1:
			this.nom = "Hector"; 
			this.pV = 50;
            this.vitesse = 5;
			this.attaque1 = new Attaque("Tir arc", 10, 10);
			this.attaque2 = new Attaque("Coup de poing", 1, 20);
            this.attaque3 = new Attaque("Uppercut", 1, 30);
			break;
		case 2:
			this.nom = "Merlin l'enchanteur"; 
			this.pV = 50;
            this.vitesse = 3;
			this.attaque1 = new Attaque("Jet de potion", 10, 10);
			this.attaque2 = new Attaque("Coup de baton", 2, 20);
            this.attaque3 = new Attaque("Boule de feu", 20, 20);
			break;
        case 3:
			this.nom = "Inspecteur Gadget"; 
			this.pV = 50;
            this.vitesse = 2;
			this.attaque1 = new Attaque("GOGO GADGET COUP DE POING AMERICAIN", 3, 25);
			this.attaque2 = new Attaque("GOGO GADGET FLECHETTE", 11, 8);
			this.attaque3 = new Attaque("GOGO GADGET MISSILE", 11, 20);
			break;
        case 4:
            this.nom = "Gimli fils de Glóin";
            this.pV = 100;
            this.vitesse = 1;
            this.attaque1 = new Attaque("Coup de hache", 1, 40);
            this.attaque2 = new Attaque("Lancer de hache", 2, 25);
            this.attaque3 = new Attaque("Charge du nain", 3, 50);
            break;
		case 5:
			this.nom = "Ichigo";
			this.pV= 50;
			this.vitesse= 5;
			this.attaque1 = new Attaque("Lancer de shuriken",20,5);
			this.attaque2 = new Attaque("Découpage au sabre",1,20);
			this.attaque3 = new Attaque("Lame du dragon",5,35);
			break;
		case 6:
			this.nom = "Zhivago";
			this.pV= 40;
			this.vitesse=10;
			this.attaque1 = new Attaque("Tranchant",2,10);
			this.attaque2 = new Attaque("Griffe acérée",2,20);
			this.attaque3 = new Attaque("Régénération bestiale",2147483647,30);
			break;
		/*case ...*/
		case 42:
			//ceci est un easter egg
			this.nom = "L'Être Suprême";
			this.joueurActif = true;
			this.pV = 4242;
            this.vitesse = 42;
            this.cooldownAtk3=1;
			this.attaque1 = new Attaque("Pluie de salade nicoise", 42, 42);
			this.attaque2 = new Attaque("Lancer de noix de coco", 4242, 424242);
			this.attaque3 = new Attaque("Explosion de rollers nucléaires", 424242, 42424242);
            System.out.println();
			System.out.println("    <(^_^)>   bravo tu es cheaté.e   <(^_^)>");
			System.out.println();
			break;
		default:
            System.out.println("erreur ! entre un numéro valide");
        }
        this.x= 1;
        this.y=1;
    }
    
    /** Cette méthode affiche le nombre de points de vie restants du personnage
     */
    public void afficheVie(){
        System.out.println("PV "+this.getJoueur()+" :");
        for(int i=0; i<this.getPV(); i++){
            System.out.print("*");
        }
        System.out.println();
        System.out.println();
    }
    
    public String getNom(){
        return this.nom;
    }
    
    public int getPV(){
        return this.pV;
    }
    
    public void setPV(int i){
        this.pV=i;
    }
    
    public String getJoueur(){
        return this.joueur;
    }
    
    public Attaque getAttaque(int i){
		if(i==1){
            return this.attaque1;
		}else if(i==2){
            return this.attaque2;	
		}else{
            return this.attaque3;
        }
	}
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public void setX(int x){
        this.x = x;
    } 
    
    
    public void setY(int y){
        this.y = y;
    } 
    
    public void setJoueurActif(boolean b){
        this.joueurActif=b;
    }
    
    public boolean getJoueurActif(){
        return this.joueurActif;
    }
    
    public int getVitesse(){
        return this.vitesse;
    }
    
    public int getCooldown(){
        return this.cooldownAtk3;
    }
    
    public void setCooldown(int i){
        this.cooldownAtk3=i;
    }
    
    public void degat(int degat){
        this.pV = this.pV - degat;
    }
    
    public void soin (int degat){
		this.pV= this.pV + degat;
	}
    
    public String getSymbole(){
        return this.symbole;
    }
    
    public void setSymbole(String s){
        this.symbole = s;
    }
    
        public int getmouvRestant(){
        return this.mouvRestant;
    }

    public void setmouvRestant(int i){
        this.mouvRestant = i;
    }
}



