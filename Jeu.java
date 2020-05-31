import java.util.Scanner;
public class Jeu{
    Personnage j1;
    Personnage j2;
    Plateau plateau;
    
    public Jeu(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvenue dans l'arène !");
        System.out.println();
        System.out.println("Quel est ton nom, ô valeureux stratège ?");
        this.j1 = new Personnage(sc.nextLine());
        System.out.println();
        System.out.println("Et toi, ô brillant tacticien ?");
        this.j2 = new Personnage(sc.nextLine());
        
        this.regles();
        System.out.println();
        this.descriptionPersos();
        System.out.println();
        
        this.j1 = this.choixPerso(this.j1); // a changer
        this.j2 = this.choixPerso(this.j2); // a changer
        
        this.deterAleaPremier();
        
        this.placementJoueurAlea(); //Place les joueurs de maniere aléatoire sur le plateau
        
        this.plateau = new Plateau(this.j1, this.j2);
        
        if(this.j1.getNom()==this.j2.getNom()){
            System.out.println("Quelle est cette sorcellerie ? "+this.j1.getNom()+" affronte son double !");
            System.out.println();
        }
        
	
        while(this.finPartie()==false){
            if(this.j1.getJoueurActif()){
                this.deroulement(this.j1,this.j2); // a changer
                this.j1.setJoueurActif(false);
                this.j2.setJoueurActif(true);
            }else{
                this.deroulement(this.j2, this.j1); // a changer
                this.j1.setJoueurActif(true);
                this.j2.setJoueurActif(false);
            }
        }
    }
    
    /** Cette méthode stocke le texte des règles
     */
    private void regles(){
        System.out.println("à chaque tour les joueurs se déplacent puis attaquent leur adversaire si ils sont assez près");
    }
    
    /** Cette méthode stocke le texte de description des personnages
     */
    private void descriptionPersos(){
        System.out.println("1: Hector | 2: Merlin | 3: Inspecteur gadget | 4: Gimli | 5: Ichigo | 6: Zhivago");
    }
    
    /** Cette méthode initialise les caractéristique d'un personnage à partir d'une liste de modèles prédéfinis
     * 
     * @param j le personnage du joueur, qui n'a pas encore de caractéristiques
     * @return j le personnage du joueur initialisé d'après un modèle
     */
    public Personnage choixPerso(Personnage j){
        Scanner sc = new Scanner(System.in);
        boolean confirmation = false;
        
        while(!confirmation || j.getNom() == null){
            System.out.println(j.getJoueur()+", entre le numéro du personnage que tu mèneras au combat");
            
            int i = sc.nextInt();
            j = new Personnage(j.getJoueur(),i);
            
            if(j.getNom() != null){
                System.out.println("Tu veux incarner "+j.getNom()+"? Ce combatant a "+j.getPV()+"PV et dispose de "+j.getVitesse()+" points de vitesse. \nEntre 1 si oui et 2 si non");
                if(sc.nextInt()==1){
                    confirmation=true;
                }else{
                    System.out.println("Non ? D'accord");
                }
            }
        }
        System.out.println("Entre dans l'arène, ô "+j.getNom()+" !");
        System.out.println();
        return j;
    }
    
    /** Cette méthode détermine aléatoirement quel joueur commence
     * et stocke cette information dans un attribut de la classe Personnage
     * sauf si un des joueurs a trouvé l'easter egg, auquel cas il commence en premier d'office
     * @param p1 le personnage du joueur 1
     * @param p2 le personnage du joueur 2
     */
    public void deterAleaPremier(){
        if(this.j1.getJoueurActif() == this.j2.getJoueurActif()){
            double i = Math.random();
            if(i<0.5){
                this.j1.setJoueurActif(true);
                this.j2.setJoueurActif(false);
                this.j1.setSymbole("J1");
                this.j2.setSymbole("J2");
                System.out.println(this.j1.getNom()+" prend de vitesse son adversaire !");
            }else{
                this.j2.setJoueurActif(true);
                this.j1.setJoueurActif(false);
                this.j2.setSymbole("J1");
                this.j1.setSymbole("J2");
                System.out.println(this.j2.getNom()+" prend de vitesse son adversaire !");
            }
        }else{
            System.out.println("L'Être Suprême prend de vitesse le misérable cafard qui ose se tenir sur son chemin !"); //Ce cas n'a lieu que si un des joueurs a trouvé l'easter egg
        }
        System.out.println();
    }
    
    /** Cette méthode place les joueurs de manière aléatoire au début du jeu
     */
    public void placementJoueurAlea(){
        while(this.distanceSecurite()==false){
            this.j1.setX((int)(Math.random()*15+2));
            this.j1.setY((int)(Math.random()*15+2));
            this.j2.setX((int)(Math.random()*15+2));
            this.j2.setY((int)(Math.random()*15+2));
        }
    }
    
    /** Cette méthode vérfie si les deux joueurs ne sont pas 
     * trop proches pour le début de partie 
     * et renvoie l'info sous forme de booléen
     * @return b le booléen
     */
    public boolean distanceSecurite(){
        boolean b = true;
        for(int i = this.j1.getX() - 4; i<= this.j1.getX() + 4; i++){
            for(int j = this.j1.getY() - 4; j<= this.j1.getY() + 4; j++){
                if((i==this.j2.getX())&&(j==this.j2.getY())){
                    b= false;
                }
            }
        }
        return b;
    }
    
    public void deroulement(Personnage joueur, Personnage victime){
        Scanner sc = new Scanner(System.in);
        this.plateau.affichage(joueur,victime);
        
        joueur.afficheVie();
        victime.afficheVie();
        
        
        System.out.println(joueur.getJoueur()+", c'est ton tour!");
        
        if(joueur.getCooldown()>0){
            joueur.setCooldown(joueur.getCooldown()-1);
        }
        
        this.plateau.deplacement(joueur, victime);
        joueur.afficheVie();
        victime.afficheVie();
		
        Attaque A = this.choixAttaque(joueur); // a changer
        System.out.println();
        while((this.testPortee(A.getPortee(), joueur, victime)==false)&&(A.getNom()!="attaque nulle")){ // a changer
            System.out.println();
            System.out.println("Tu es trop loin de ton adversaire pour lancer cette attaque, choisis en une autre, ou écris 0 si tu ne peux pas jouer!");
            System.out.println();
            A = this.choixAttaque(joueur);
        }
        
        System.out.println();
        if(A.getNom()=="attaque nulle"){
            System.out.println(joueur.getNom()+" épargne son adversaire pour ce tour");
        }else{
            System.out.println(joueur.getNom()+" lance son attaque "+A.getNom()+" et arrache "+A.getDegats()+" PV à "+victime.getNom());
            victime.degat(A.getDegats());
	    A.baisseDegats();
        }
        System.out.println();
        joueur.afficheVie();
        victime.afficheVie();
        this.plateau.creationBonus(joueur, victime);
        this.pressEnter();
        System.out.println();
        System.out.println();
	}
    
	public Attaque choixAttaque(Personnage joueur){
		Scanner sc = new Scanner(System.in);
 		Attaque nulle= new Attaque();
		
		System.out.println("Voici les attaques de ton personnage:");
		for(int i = 1; i<4; i++){
			if(i==3 && joueur.getNom() =="Zhivago"){
                System.out.println(i+": "+joueur.getAttaque(i).getNom()+" qui n'inflige pas de dégats mais te rends "+joueur.getAttaque(i).getDegats()+" PV");
            }else{
                System.out.println(i+": "+joueur.getAttaque(i).getNom()+" qui inflige "+joueur.getAttaque(i).getDegats()+" dégâts et a une portée de "+joueur.getAttaque(i).getPortee()+" cases");
            }
		}
		System.out.print("Entre le numéro associé à l'attaque que tu veux utiliser: ");
		int a = sc.nextInt();
		while((a<0)||(a>3)){
			System.out.print("Tu te trompes jeune guerrier, ceci n'est pas une attaque disponible, entre à nouveau un nombre: ");
			a= sc.nextInt();
		}
        
        while(a==3 && joueur.getCooldown()!=0){
            System.out.print("Cette attaque ne sera disponible que dans "+joueur.getCooldown()+" tours, entre à nouveau un nombre: ");
			a= sc.nextInt();
        }
        
		if((a==1)||(a==2)){
			return joueur.getAttaque(a);
		}else if(a==3 && joueur.getCooldown()==0){
            joueur.setCooldown(3);
            return joueur.getAttaque(a);
        }else{
			return nulle;
		}
	}
    
    public boolean testPortee(int portee, Personnage j, Personnage v){
        boolean b= false;
        for(int i = j.getX() - portee; i<= j.getX() + portee; i++){
            for(int u = j.getY() - portee; u<= j.getY() + portee; u++){
                if((i==v.getX())&&(u==v.getY())){
                    b= true;
                }
            }
        }
        return b;
    }
    
    public void pressEnter(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Appuie sur entrée pour finir ton tour");
        sc.nextLine();
    }
    
    public boolean finPartie(){
        if(this.j1.getPV()<=0){
            System.out.println(this.j2.getNom() + " remporte ce combat !");
            System.out.println();
            return true;
        }else if(this.j2.getPV()<=0){
            System.out.println(this.j1.getNom() + " remporte ce combat !");
            System.out.println();
            return true; 
        }else{
            return false;
        }
    }
}
