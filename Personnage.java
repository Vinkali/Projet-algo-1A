/**
 * Stocke les caractéristiques du personnage.
 * contient aussi une méthde pour l'affichage des points de vie
 */
public class Personnage{
    
    /**
     * le nom du joueur
     */
    private String joueur;
    
    /**
     * le nom du personnage
     */
    private String nom;
    
    /**
     * le symbole qui s'affiche sur le plateau
     */
    private String symbole;
    
    /**
     * pour savoir si c'est le tour du joueur ou de son adversaire
     */
    private boolean joueurActif;
    
    /**
     * le nombre de points de vie du joueur
     */
    private int pV;
    
    
    /**
     * la vitesse du joueur, c'est à dire le nombre de case duquel il peut se déplacer à chauqe tour
     */
    private int vitesse;
    
    /**
     * le nombre de cases de déplacement restantes pour le tour
     */
    private int mouvRestant;
    
    /**
     * la première attaque du joueur
     */
    private Attaque attaque1;
    
    /**
     * la deuxième attaque du joueur
     */
    private Attaque attaque2;
    
    /**
     * la troisième attaque du joueur, utilisable seulement après un temps de recharge
     */
    private Attaque attaque3;
    
    /**
     * le nombre de tours restant avant de pouvoir réutiliser l'attaque 3
     */
    private int cooldownAtk3;
    
    
    /**
     * l'abscisse du joueur sur le plateau
     */
    private int x;
    
    /**
     * l'ordonnée du joueur sur le plateau
     */
    private int y;
    
    /**
     * constructeur utilisé dans un premier temps, qui ne stocke que le nom du joueur
     * @param joueur le nom du joueur
     */
    public Personnage(String joueur){
        this.joueur = joueur;
    }
    
    /** 
     * constructeur plus complexe qui permet de créer les personnages en spécifiant toutes leurs caractéristiques et attaques.
     * initialise les caractéristique d'un personnage à partir d'une liste de modèles prédéfinis
     * @param joueur le nom du joueur
     * @param i le numéro du modèle sélectionné
     */
    public Personnage(String joueur, int i){
        this.joueur = joueur;
        this.cooldownAtk3 = 4;
        this.x = 1;
        this.y = 1;
        
        switch(i){
		case 1:
			this.nom = "Hector"; 
			this.pV = 50;
            this.vitesse = 5;
			this.attaque1 = new Attaque("Tir à l'arc", 10, 10);
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
        case 7:
            this.nom = "Erik";
			this.pV= 30;
			this.vitesse=20;
			this.attaque1 = new Attaque("Coup de dague",1,15);
			this.attaque2 = new Attaque("Aspi-PV",1,7);
			this.attaque3 = new Attaque("Frappe de l'assassin",1,40);
			break;
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
    }
    
    /** 
     * affiche le nombre de points de vie restants du personnage sous forme de "barre de vie"
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
    
    public String getSymbole(){
        return this.symbole;
    }
    
    public void setSymbole(String s){
        this.symbole = s;
    }
    
        public int getMouvRestant(){
        return this.mouvRestant;
    }

    public void setMouvRestant(int i){
        this.mouvRestant = i;
    }
}



