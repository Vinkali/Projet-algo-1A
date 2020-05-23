




/* 
 * 
 * demander au prof :
 * si il faut écrire les @see get et set dans la javadoc
 * si on peut éviter le crash quand l'utilisateur tape autre chose que ce que le scanner cherche (comme une lettre au lieu d'un int)
 */




import java.util.Scanner;
public class Jeu{
    
    public static void main(String [] args){
        Personnage j1=new Personnage("J1");
        Personnage j2=new Personnage("J2");
        
        System.out.println(regles());
        System.out.println();
        System.out.println(descriptionPersos());
        System.out.println();
        
        j1 = choixPerso(j1);
        j2 = choixPerso(j2);
        
        String [][] plateau = creationPlateau();
        placementJoueurAlea(plateau, j1, j2); //Place les joueurs de maniere aléatoire sur le plateau
        creationForet(plateau, j1, j2);
        
        affichePlateau(plateau);
        
        afficheVie(j1);
        afficheVie(j2);
        
        if(j1.getNom()==j2.getNom()){
            System.out.println("Quelle est cette sorcellerie ? "+j1.getNom()+" affronte son double !");
            System.out.println();
        }
        deterAleaPremier(j1,j2);
        
	
        while(finPartie(j1,j2)==false){
            if(j1.getJoueurActif()){
                deroulement(j1,j2, plateau);
                j1.setJoueurActif(false);
                j2.setJoueurActif(true);
            }else{
                deroulement(j2, j1, plateau);
                j1.setJoueurActif(true);
                j2.setJoueurActif(false);
            }
        }
    }
    
    /** Cette méthode stocke le texte des règles
     */
    public static String regles(){
        return "à chaque tour les joueurs se déplacent puis attaquent leur adversaire si ils sont assez près";
    }
    
    /** Cette méthode stocke le texte de description des personnages
     */
    public static String descriptionPersos(){
        return "1: Hector | 2: Merlin | 3: Inspecteur gadget | 4: Gimli | 5: Ichigo | 6: Zhivago";
    }
    
    /** Cette méthode initialise les caractéristique d'un personnage à partir d'une liste de modèles prédéfinis
     * 
     * @param j le personnage du joueur, qui n'a pas encore de caractéristiques
     * @return j le personnage du joueur initialisé d'après un modèle
     */
    public static Personnage choixPerso(Personnage j){
        Scanner sc = new Scanner(System.in);
        boolean confirmation = false;
        
        while(!confirmation || j.getNom() == null){
            System.out.println(j.getJoueur()+", entre le numéro du personnage que tu mèneras au combat");
            
            int i = sc.nextInt();
            j = new Personnage(j.getJoueur(),i);
            
            if(j.getNom() != null){
                String newLine= System.getProperty("line.separator");
                System.out.println("Tu veux incarner "+j.getNom()+"? Ce combatant a "+j.getPV()+"PV et dispose de "+j.getVitesse()+" points de vitesse."
                +newLine+"Entre 1 si oui et 2 si non");
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
    
    public static String [][] creationPlateau(){
        String [][] p= new String[19][19];
        for(int i=0; i<p.length; i++){
            for (int j=0; j<p[0].length; j++){
                
                if((i==0 || i==18) && j>1 && j<11){
                    p[i][j]= " " + String.valueOf(j-1) +" "; //on affiche le numéro des lignes et des colonnes
                }else if((i==0 || i==18) && j>10 && j<17){
                    p[i][j]= " " + String.valueOf(j-1);
                }else if((j==0 || j==18) && i>1 && i<11){
                    p[i][j]= " " + String.valueOf(i-1) +" ";
                }else if((j==0 || j==18) && i>10 && i<17){
                    p[i][j]= " " + String.valueOf(i-1);
                }else if((j==0 || j==18) && (i==0 || i==18)){
                    p[i][j]= " X ";
                    
                }else if((i==1)||(i==17)){
                    p[i][j]="---"; //On délimite le plateau pour qu'il soit bien visible par les joueurs
                }else if((j==1)){
                    p[i][j]="|  ";
                }else if((j==17)){
                    p[i][j]="  |";
                }else{
                    p[i][j]=" . "; //L'intérieur du plateau est rempli de points pour bien distinguer les différentes positions, et les distances
                }
            System.out.println();
            }
        }
    return p;
    }
    
    /** Cette méthode place les joueurs de manière aléatoire au début du jeu
     * @param le plateau de jeu "vide", les joueurs J1 et J2  
     */
    public static void placementJoueurAlea(String[][] plateau, Personnage j1, Personnage j2){
        while(distanceSecurite(j1, j2)==false){
            j1.setX((int)(Math.random()*14+2));
            j1.setY((int)(Math.random()*14+2));
            j2.setX((int)(Math.random()*14+2));
            j2.setY((int)(Math.random()*14+2));
        }
		plateau[j1.getX()][j1.getY()] = "J1 ";
		plateau[j2.getX()][j2.getY()] = "J2 ";
    }
    
    /** Cette méthode vérfie si les deux joueurs ne sont pas 
     * trop proches pour le début de partie 
     * et renvoie l'info sous forme de booléen
     * @param les joueurs j1 et j2
     * @return b le booléen
     */
    public static boolean distanceSecurite(Personnage j1, Personnage j2){
        boolean b = true;
        for(int i = j1.getX() - 4; i<= j1.getX() + 4; i++){
            for(int j = j1.getY() - 4; j<= j1.getY() + 4; j++){
                if((i==j2.getX())&&(j==j2.getY())){
                    b= false;
                }
            }
        }
        return b;
    }
    
    /** Cette méthode crée 3 forêts
     * placées aléatoirement sur le plateau
     * @param p le plateau, les joueurs j1 et j2
     */
    public static void creationForet(String[][] p, Personnage j1, Personnage j2){
        int foret=1;
        while(foret <= 3){
            int a = (int) (Math.random()*12+3);
            int b = (int) (Math.random()*12+3);
            boolean touchePerso= false;
            for(int u= a-1; u<=a+1; u++){
				for(int v=b-1; v<=b+1; v++){
					if(((u==j1.getX() && v==j1.getY()) || (v==j2.getY() && u==j2.getX())) || u<2 || u>17 || v<2 || v>17){
						touchePerso=true;
					}
				}
			}
            if(touchePerso==false){
                for(int u= a-1; u<=a+1; u++){
					for(int v=b-1; v<=b+1; v++){ 
						p[u][v] = " # ";
					}
				}
				foret ++;
            }
        }
    }
    
    /** Cette méthode affiche l'état actuel du plateau de jeu
     * @param p le plateau à afficher
     */
    public static void affichePlateau(String[][] p){
        for(int i=0; i<p.length; i++){
            for(int j=0; j<p[0].length; j++){
                System.out.print(p[i][j]);
            }
            System.out.println();
        }
    }
    
    /** Cette méthode affiche le nombre de points de vie restants d'un personnage
     * @param p le personnage dont on veut afficher les pV
     */
    public static void afficheVie(Personnage p){
        System.out.println("PV "+p.getJoueur()+" :");
        for(int i=0; i<p.getPV(); i++){
            System.out.print("*");
        }
        System.out.println();
        System.out.println();
    }
    
    /** Cette méthode détermine aléatoirement quel joueur commence
     * et stocke cette information dans un attribut de la classe Personnage
     * sauf si un des joueurs a trouvé l'easter egg, auquel cas il commence en premier d'office
     * @param p1 le personnage du joueur 1
     * @param p2 le personnage du joueur 2
     */
    public static void deterAleaPremier(Personnage p1,Personnage p2){
        if(p1.getJoueurActif() == p2.getJoueurActif()){
            double i = Math.random();
            if(i<0.5){
                p1.setJoueurActif(true);
                p2.setJoueurActif(false);
                System.out.println(p1.getNom()+" prend de vitesse son adversaire !");
            }else{
                p2.setJoueurActif(true);
                p1.setJoueurActif(false);
                System.out.println(p2.getNom()+" prend de vitesse son adversaire !");
            }
        }else{
            System.out.println("L'Être Suprême prend de vitesse le misérable cafard qui ose se tenir sur son chemin !");
        }
        System.out.println();
    }
    
    public static void deroulement(Personnage joueur, Personnage victime, String[][] plateau){
        System.out.println(joueur.getNom()+", c'est ton tour!");
        
        if(joueur.getCooldown()>0){
            joueur.setCooldown(joueur.getCooldown()-1);
        }
        
        deplacement(joueur, victime, plateau);
        affichePlateau(plateau);
        afficheVie(joueur);
        afficheVie(victime);
		
        Attaque A = choixAttaque(joueur);
        System.out.println();
        while((testPortee(A.getPortee(), joueur, victime)==false)&&(A.getNom()!="attaque nulle")){
            System.out.println();
            System.out.println("Tu es trop loin de ton adversaire pour lancer cette attaque, choisis en une autre, ou écris 0 si tu ne peux pas jouer!");
            System.out.println();
            A = choixAttaque(joueur);
        }
	
        if(A.getNom()=="attaque nulle"){
            System.out.println(joueur.getNom()+" épargne son adversaire pour ce tour");
        }else{
            System.out.println(joueur.getNom()+" lance son attaque "+A.getNom()+" et arrache "+A.getDegats()+" PV à "+victime.getNom());
            victime.degat(A.getDegats());	
        }
        System.out.println();
        afficheVie(victime);
        afficheVie(joueur);
    
	}
	
    /** cette méthode déplace un personnage
     * vers des coordonnées précises
     * @param p le joueur qui se déplace
     * @param plateau le plateau de jeu
     */
    public static String[][] deplacement(Personnage p, Personnage autre, String[][] plateau){
        Scanner sc = new Scanner(System.in);
        boolean deplValide = false;
        if(plateau[p.getX()][p.getY()] != " # "){
			plateau[p.getX()][p.getY()] = " . ";
		}
        int i=0;
        int j=0;
        
        while(deplValide==false){
            System.out.println("sur quelle colonne veut-tu déplacer ton héros ?");
            j = sc.nextInt()+1;
            System.out.println("sur quelle ligne veut-tu déplacer ton héros ?");
            i = sc.nextInt()+1;
            if((1<i && i<17) && (1<j && j<17) && (i!=autre.getX() || j!=autre.getY())){
                
                if(Math.abs(i-p.getX())==Math.abs(j-p.getY()) && Math.abs(i-p.getX())<=p.getVitesse()){
                    deplValide=true;
                    p.setX(i);
                    p.setY(j);
                }else if(Math.abs(i-p.getX())==0 && Math.abs(j-p.getY())<=p.getVitesse()){
                    deplValide=true;
                    p.setY(j);
                }else if(Math.abs(j-p.getY())==0 && Math.abs(i-p.getX())<=p.getVitesse()){
                    deplValide=true;
                    p.setX(i);
                }else if(Math.abs(j-p.getY())<Math.abs(i-p.getX()) && (Math.abs(j-p.getY())+(Math.abs(i-p.getX())- Math.abs(j-p.getY())))<=p.getVitesse()){
                    deplValide=true;
                    p.setX(i);
                    p.setY(j);
                }else if(Math.abs(i-p.getX())<Math.abs(j-p.getY()) && (Math.abs(i-p.getX())+(Math.abs(j-p.getY())- Math.abs(i-p.getX())))<=p.getVitesse()){
                    deplValide=true;
                    p.setX(i);
                    p.setY(j);
                }else{
                    System.out.println("Ton héros n'est pas assez rapide, il ne peut se déplacer que de "+p.getVitesse()+" cases");
                }
            }else{
                System.out.println("Une de ces coordonnées dépasse du plateau ou ton adversaire occupe déjà cette case");
            }
        }
		if(plateau[p.getX()][p.getY()]!=" # "){	
			plateau[p.getX()][p.getY()]=p.getJoueur()+" ";
		}
        return plateau;
    }
    
	public static Attaque choixAttaque(Personnage joueur){
		Scanner sc = new Scanner(System.in);
		Attaque nulle= new Attaque();
		
		System.out.println("Voici les attaques de ton personnage:");
		for(int i = 1; i<3; i++){
			System.out.println(i+": "+joueur.getAttaque(i).getNom()+" qui inflige "+joueur.getAttaque(i).getDegats()+" dégâts et a une portée de "+joueur.getAttaque(i).getPortee()+" cases");
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
    
    public static boolean testPortee(int portee, Personnage j, Personnage v){
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
    
    public static boolean finPartie(Personnage j1, Personnage j2){
        if(j1.getPV()<=0){
            System.out.println(j2.getNom() + " remporte ce combat !");
            System.out.println();
            return true;
        }else if(j2.getPV()<=0){
            System.out.println(j1.getNom() + " remporte ce combat !");
            System.out.println();
            return true; 
        }else{
            return false;
        }
    }
}
