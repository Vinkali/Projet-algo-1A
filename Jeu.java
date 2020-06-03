import java.util.Scanner;

/**
 * C'est dans cette classe qu'est géré l'essentiel du jeu.
 * contient les personnages et le plateau en attributs
 * et des méthodes pour mettre en place le jeu, le faire se dérouler et afficher le gagnant
 */
public class Jeu{
    /**
     * personnage du joueur 1, qui stocke ses statistiques, ses attaques et sa position
     * @see Personnage
     */
    private Personnage j1;
    
    /**
     * personnage du joueur 2, qui stocke ses statistiques, ses attaques et sa position
     * @see Personnage
     */
    private Personnage j2;
    
    /**
     * plateau de jeu, avec un affichage par défaut, et l'emplacement des forêts et des bonus.
     * C'est aussi là que se trouve la méthode qui gère le déplacement des personnages
     * @see Plateau
     */
    private Plateau plateau;
    
    /**
     * Constructeur de Jeu.
     * Il initialise les personnages et le plateau
     * et lance le déroulement de la partie
     * @see regles
     * @see choixPerso
     * @see deterAleaPremier
     * @see placementJoueurAlea
     * @see deroulement
     * @see finPartie
     * @see Plateau
     * @see Personnage
     */
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
        
        this.j1 = this.choixPerso(this.j1);
        this.j2 = this.choixPerso(this.j2);
        
        this.deterAleaPremier();
        
        this.placementJoueurAlea();
        
        this.plateau = new Plateau(this.j1, this.j2);
        
        if(this.j1.getNom()==this.j2.getNom()){
            System.out.println("Quelle est cette sorcellerie ? "+this.j1.getNom()+" affronte son double !");
            System.out.println();
        }
        
	
        while(this.finPartie()==false){
            if(this.j1.getJoueurActif()){
                this.deroulement(this.j1,this.j2);
                this.j1.setJoueurActif(false);
                this.j2.setJoueurActif(true);
            }else{
                this.deroulement(this.j2, this.j1);
                this.j1.setJoueurActif(true);
                this.j2.setJoueurActif(false);
            }
        }
    }
    
    /**
     * affiche le texte des règles
     */
    private void regles(){
        System.out.println("Votre duel va commencer ! \n\nA chaque tour les joueurs peuvent se déplacer et attaquer leur adversaire.\nDes surprises vous attendent sur le terrain du combat...");
    }
    
    /**
     * permet au joueur de choisir son personnage.
     * affiche la liste des personnages disponible et lui demande le numéro de celui qu'il veut jouer.
     * fournit ce numéro au constructeur du personnage, qui l'utilise pour l'initialiser à partir d'une liste de modèles.
     * affiche ensuite quelques caractéristiques du personnage, et demande au joueur de confirmer son choix.
     * @param j le personnage du joueur, qui n'a pas encore de caractéristiques
     * @return j le personnage du joueur initialisé d'après un modèle
     * @see descriptionPersos
     * @see Personnage
     */
    public Personnage choixPerso(Personnage j){
        this.descriptionPersos();
        System.out.println();
        Scanner sc = new Scanner(System.in);
        boolean confirmation = false; //permet de demander au joueur de valider son choix
        
        while(!confirmation || j.getNom() == null){ // le processus de choix se répète tant que soit le joueur n'a pas confirmé, soit il a entré un numéro non valide
            System.out.println(j.getJoueur()+", entre le numéro du personnage que tu mèneras au combat");
            
            int i = sc.nextInt();
            j = new Personnage(j.getJoueur(),i);
            
            if(j.getNom() != null){ // si le numéro est valide on donne au joueur des infos sur son personnage et on lui demande de valider son choix
                System.out.println("Tu veux incarner "+j.getNom()+"? Ce combatant a "+j.getPV()+"PV et dispose de "+j.getVitesse()+" points de vitesse. \nEntre 1 si oui et 2 si non");
                if(sc.nextInt()==1){ //si le joueur valide son choix, on sort du while
                    confirmation=true;
                }else{ // sinon on recommence
                    System.out.println("Non ? D'accord");
                }
            }
        }
        System.out.println("Entre dans l'arène, ô "+j.getNom()+" !");
        System.out.println();
        return j;
    }
    
    /**
     * affiche le texte de description des personnages
     * @see choixPersos
     */
    private void descriptionPersos(){
        System.out.println("Il est temps de choisir votre héros ! \n1: Hector | 2: Merlin | 3: Inspecteur gadget | 4: Gimli | 5: Ichigo | 6: Zhivago | 7: Erik");
    }
    
    /** détermine aléatoirement quel joueur commence
     * et stocke cette information dans un attribut de la classe Personnage
     * sauf si un des joueurs a trouvé l'easter egg, auquel cas il commence en premier d'office
     * @see Personnage
     */
    public void deterAleaPremier(){
        if(this.j1.getJoueurActif() == this.j2.getJoueurActif()){ //si aucun des joueurs n'a trouvé l'easter egg, ou si les deux l'ont trouvé, on détermine aléatoirement qui commence
            double i = Math.random();
            if(i<0.5){
                this.j1.setJoueurActif(true);
                this.j2.setJoueurActif(false);
                this.j1.setSymbole("J1"); //les symboles sont affichés sur le plateau, ils sont associés à l'ordre de jeu...
                this.j2.setSymbole("J2");
                System.out.println(this.j1.getNom()+" prend de vitesse son adversaire !");
            }else{
                this.j2.setJoueurActif(true);
                this.j1.setJoueurActif(false);
                this.j2.setSymbole("J1"); // ...et pas aux noms des variables, c'est pourquoi j2 a le symbole J1 dans ce cas
                this.j1.setSymbole("J2");
                System.out.println(this.j2.getNom()+" prend de vitesse son adversaire !");
            }
        }else{ //Ce cas n'a lieu que si un seul des joueurs a trouvé l'easter egg, auquel cas son personnage est déjà initialisé comme jouant en premier
            System.out.println("L'Être Suprême prend de vitesse le misérable cafard qui ose se tenir sur son chemin !");
        }
        System.out.println();
    }
    
    /**
     * place les joueurs de manière aléatoire au début du jeu.
     * Les joueurs sont placés dans la zone "utile" du plateau,
     * c'est à dire un carré de 15x15 à 2 cases de distance de chaque bord du tableau de taille 19x19 qui représente le plateau.
     * Les coordonnées ne sont validées que si une distance d'au moins 4 cases entre les joueurs est respectée
     * @see distanceSecurite
     * @see Personnage
     */
    public void placementJoueurAlea(){
        while(this.distanceSecurite()==false){ //on choisit aléatoirement les coordonnées des joueurs et on recommence tant qu'ils sont trop proches
            this.j1.setX((int)(Math.random()*15+2));
            this.j1.setY((int)(Math.random()*15+2));
            this.j2.setX((int)(Math.random()*15+2));
            this.j2.setY((int)(Math.random()*15+2));
        }
    }
    
    /**
     * vérfie si les deux joueurs ne sont pas 
     * trop proches pour le début de partie 
     * et renvoie l'info sous forme de booléen
     * @return b le booléen qui détermine si la distance est correcte
     * @see placementJoueurAlea
     * @see Personnage
     */
    public boolean distanceSecurite(){
        boolean b = true;
        for(int i = this.j1.getX() - 4; i<= this.j1.getX() + 4; i++){
            for(int j = this.j1.getY() - 4; j<= this.j1.getY() + 4; j++){ 
                if((i==this.j2.getX())&&(j==this.j2.getY())){ // on s'assure qu'il y ait au moins 4 cases d'écart entre les joueurs
                    b= false;
                }
            }
        }
        return b;
    }
    
    /**
     * gère la structure du tour.
     * permet au joueur de choisir l'ordre de ses actions, et déclenche d'autres méthodes en conséquence.
     * affiche le plateau et les points de vie des joueurs entre chaque étape, et génère de nouveaux bonus à la fin du tour
     * 
     * @param joueur le joueur dont c'est le tour
     * @param adversaire son adversaire
     * @see Plateau
     * @see Personnage
     * @see Plateau.affichage
     * @see afficheVie
     * @see Plateau.deplacement
     * @see phaseAttaque
     * @see Plateau.creationBonus
     */
    public void deroulement(Personnage joueur, Personnage adversaire){
        Scanner sc = new Scanner(System.in);
        this.plateau.affichage(joueur,adversaire);
        
        joueur.afficheVie();
        adversaire.afficheVie();
        
        System.out.println(joueur.getJoueur()+", c'est ton tour!");
        
        if(joueur.getCooldown()>0){ // à chaque tour, le délai de recharge de l'attaque 3 diminue
            joueur.setCooldown(joueur.getCooldown()-1);
        }
        joueur.setMouvRestant(joueur.getVitesse());
        
        System.out.println("Veux-tu commencer par te déplacer (entre 1) ou attaquer (entre 2) ?");
        int i = sc.nextInt();
        while(i!=1 && i!=2){
            System.out.println("Non, tu dois entrer 1 ou 2!");
            i = sc.nextInt();
        }
        
        if(i==1){ // si le joueur veut commencer par se déplacer, il le fait, puis il attaque, puis il peut se déplacer à nouveau si il n'a pas utilisé toute sa vitesse
            this.plateau.deplacement(joueur, adversaire);
            joueur.afficheVie();
            adversaire.afficheVie();
	
            this.phaseAttaque(joueur, adversaire);
            System.out.println();
            joueur.afficheVie();
            adversaire.afficheVie();
            
            if(joueur.getMouvRestant()>0){ 
                this.plateau.affichage(joueur, adversaire);
                System.out.println("Tu as encore la possibilité de te déplacer de "+joueur.getMouvRestant()+" cases.");
                this.plateau.deplacement(joueur, adversaire);
                joueur.afficheVie();
                adversaire.afficheVie();
            }
        
        }else{// si le joueur choisit de commencer par attaquer, il le fait, puis peut se déplacer
            this.phaseAttaque(joueur, adversaire);
            
            System.out.println();
            this.plateau.affichage(joueur, adversaire);
            joueur.afficheVie();
            adversaire.afficheVie();
            
            this.plateau.deplacement(joueur, adversaire);
            joueur.afficheVie();
            adversaire.afficheVie();
        }
        this.plateau.creationBonus(joueur, adversaire); // à la fin du tour, de nouveaux bonus sont créés pour remplacer ceux qui ont été récupérés
        this.pressEnter();
        System.out.println();
        System.out.println();
	}
    
    /**
     * gère la phase d'attaque du tour. 
     * vérifie que le joueur est en mesure d'utiliser l'attaque qu'il choisit dans choixAttaque
     * et en applique les conséquences
     * @param joueur le joueur dont c'est le tour
     * @param adversaire son adversaire
     * @see Attaque
     * @see Personnage
     * @see Plateau
     * @see deroulement
     * @see choixAttaque
     * @see testPortee
     * @see Attaque.baisseDegats
     */
    public void phaseAttaque(Personnage joueur, Personnage adversaire){
        Attaque A = this.choixAttaque(joueur);
        System.out.println();
        
        if(this.plateau.getForet()[joueur.getX()][joueur.getY()] != this.plateau.getForet()[adversaire.getX()][adversaire.getY()]){ //si un joueur est dans la forêt et l'autre non, ils ne peuvent pas s'attaquer
            System.out.println(joueur.getNom() + " tente d'attaquer son adversaire ... mais celui-ci semble s'être volatilisé !");
        }else{
            
            while((this.testPortee(A.getPortee())==false)&&(A.getNom()!="attaque nulle")){ //le choix recommence tant que le joueur n'a pas choisi une attaque qui a assez de portée pour toucher son adversaire ou choisi de passer son attaque du tour
                System.out.println();
                System.out.println("Tu es trop loin de ton adversaire pour lancer cette attaque, choisis en une autre, ou écris 0 si tu ne peux pas jouer!");
                System.out.println();
                A = this.choixAttaque(joueur);
            }
        
            if(A.getNom()=="attaque nulle"){// si le joueur choisit de ne pas attaquer, rien ne se passe
                System.out.println(joueur.getNom()+" épargne son adversaire pour ce tour");
                
            }else if(A.getNom() == "Régénération bestiale"){ // cette attaque particulière soigne son utilisateur mais n'inflige pas de dégats
                System.out.println(joueur.getNom()+ " se soigne de "+ A.getDegats()+ " PV !");
                joueur.setPV(joueur.getPV() + A.getDegats());
                A.baisseDegats();
                
            }else if(A.getNom()== "Aspi-PV"){ // cette attaque particulière soigne son utlisateur d'autant de PV qu'elle inflige de dégats
                System.out.println(joueur.getNom()+" lance son attaque "+A.getNom()+" et arrache "+A.getDegats()+" PV à "+adversaire.getNom()+" et se soigne d'autant de PV !");
                joueur.setPV(joueur.getPV() + A.getDegats());
                adversaire.setPV(adversaire.getPV() - A.getDegats());
                A.baisseDegats();
                
            }else{// dans les autres cas, l'attaque inflige des dégats normalement
                System.out.println(joueur.getNom()+" lance son attaque "+A.getNom()+" et arrache "+A.getDegats()+" PV à "+adversaire.getNom()+" !");
                adversaire.setPV(adversaire.getPV() - A.getDegats());
                A.baisseDegats();
            }
        }
    }
    
    /**
     * permet au joueur de choisir une attaque.
     * affiche la liste des attaques du personnage et s'assure que le choix est valide
     * @param joueur le joueur dont c'est le tour
     * @return l'attaque choisie
     * @see Attaque
     * @see Personnage
     * @see phaseAttaque
     * 
     */
	public Attaque choixAttaque(Personnage joueur){
		Scanner sc = new Scanner(System.in);
 		Attaque nulle= new Attaque(); //utlisée si le joueur ne veut pas attaquer
		
		System.out.println("Voici les attaques de ton personnage:");
		for(int i = 1; i<4; i++){
			if(i==3 && joueur.getNom() =="Zhivago"){// cette attaque particulière soigne son utilisateur mais n'inflige pas de dégats
                System.out.println(i+": "+joueur.getAttaque(i).getNom()+" qui n'inflige pas de dégats mais te rends "+joueur.getAttaque(i).getDegats()+" PV");
                
            }else if(i==2 && joueur.getNom() =="Erik"){// cette attaque particulière soigne son utlisateur d'autant de PV qu'elle inflige de dégats
                System.out.println(i+": "+joueur.getAttaque(i).getNom()+" qui inflige "+joueur.getAttaque(i).getDegats()+" dégâts, te rend autant de PV et a une portée de "+joueur.getAttaque(i).getPortee()+" cases");
            }
            else{// pour les attaques normales, on affiche leurs dégats et leur portée
                System.out.println(i+": "+joueur.getAttaque(i).getNom()+" qui inflige "+joueur.getAttaque(i).getDegats()+" dégâts et a une portée de "+joueur.getAttaque(i).getPortee()+" cases");
            }
		}
        System.out.println("0 : ne pas attaquer");
        System.out.println();
		System.out.print("Entre le numéro associé à l'attaque que tu veux utiliser: ");
		int a = sc.nextInt();
		while((a<0)||(a>3)){// on s'assure que le joueur entre un numéro valide
			System.out.print("Tu te trompes jeune guerrier, ceci n'est pas une attaque disponible, entre à nouveau un nombre: ");
			a= sc.nextInt();
		}
        
        while(a==3 && joueur.getCooldown()!=0){// si le joueur choisit l'attaque 3 alors qu'elle n'est pas encore disponible, on l'en informe
            System.out.print("Cette attaque ne sera disponible que dans "+joueur.getCooldown()+" tours, entre à nouveau un nombre: ");
			a= sc.nextInt();
        }
        
		if((a==1)||(a==2)){//sinon on renvoie l'attaque correspondant à son choix
			return joueur.getAttaque(a);
		}else if(a==3 && joueur.getCooldown()==0){
            joueur.setCooldown(3);
            return joueur.getAttaque(a);
        }else{
			return nulle;
		}
	}
    
    /**
     * vérifie que la cible d'une attaque est à protée de l'attaque
     * @param portee la portée de l'attaque
     * @see Personnage
     * @see phaseAttaque
     */
    public boolean testPortee(int portee){
        boolean aPortee = false;
        for(int u = this.j1.getX() - portee; u<= this.j1.getX() + portee; u++){
            for(int v = this.j1.getY() - portee; v<= this.j1.getY() + portee; v++){
                if((u==this.j2.getX())&&(v==this.j2.getY())){
                    aPortee = true;
                }
            }
        }
        return aPortee;
    }
    
    /**
     * sert à faire une pause dans le programme avant le changement de joueur.
     * utilise la classe scanner pour interrompre le programme jusqu'à ce que le joueur appuie sur entrée
     * @see deroulement
     */
    public void pressEnter(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Appuie sur entrée pour finir ton tour");
        sc.nextLine();
    }
    
    /**
     * détermine si la partie est finie.
     * vérifie si les points de vie des joueurs sont au-dessus de 0
     * @return fini le boolean qui détermine si la partie est finie
     */
    public boolean finPartie(){
        boolean fini = false;
        if(this.j1.getPV()<=0){
            System.out.println(this.j2.getNom() + " remporte ce combat !");
            System.out.println();
            fini = true;
        }else if(this.j2.getPV()<=0){
            System.out.println(this.j1.getNom() + " remporte ce combat !");
            System.out.println();
            fini = true; 
        }else{
            fini = false;
        }
        return fini;
    }
}
