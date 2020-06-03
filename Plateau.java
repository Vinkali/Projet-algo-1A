import java.util.Scanner;

/**
 * stocke l'état du champ de bataille.
 * contient des tableaux 2D qui enregistrent l'état par défaut, l'emplacement des bonus et celui des forêts
 */
public class Plateau{
    
    /**
     * stocke l'apparence du plateau "vide"
     */
    private String[][] defaut;
    
    /**
     * stocke l'emplacement des bonus qui rendent de la vie
     */
    private boolean[][] bonus;
    
    /**
     * stocke l'emplacement des forêts dans lesquelles les joueurs peuvent se cacher
     */
    private boolean[][] foret;
    
    /**
     * constructeur qui initialise la taille des tableaux et lance des méthodes qui les remplissent
     * @param j1 le joueur 1
     * @param j2 le joueur 2
     * @see creationDefaut
     * @see creationForet
     * @see creationBonus
     */
    public Plateau(Personnage j1, Personnage j2){
        
        this.defaut = new String[19][19];
        creationDefaut();
        this.foret = new boolean[19][19];
        creationForet(j1, j2);
        this.bonus = new boolean[19][19];
        creationBonus(j1, j2);
    }
    
    /**
     * sert à créer le plateau par défaut. sa surface "utile" est de 15x15 cases
     * entourées par des cases affichant les numéros de ligne et colonne
     */
    public void creationDefaut(){ 
        for(int i=0; i<this.defaut.length; i++){ 
            for (int j=0; j<this.defaut[0].length; j++){
                
                if((i==0 || i==18) && j>1 && j<11){
                    this.defaut[i][j]= " " + String.valueOf(j-1) +" "; //on note le numéro des lignes et des colonnes
                }else if((i==0 || i==18) && j>10 && j<17){
                    this.defaut[i][j]= String.valueOf(j-1) +" ";
                }else if((j==0 || j==18) && i>1 && i<11){
                    this.defaut[i][j]= " " + String.valueOf(i-1) +" ";
                }else if((j==0 || j==18) && i>10 && i<17){
                    this.defaut[i][j]= String.valueOf(i-1) +" ";
                }else if((j==0 || j==18) && (i==0 || i==18)){
                    this.defaut[i][j]= " X ";
                    
                }else if((i==1)||(i==17)){
                    this.defaut[i][j]="---"; //On délimite le plateau pour qu'il soit bien visible par les joueurs
                }else if((j==1)){
                    this.defaut[i][j]="|  ";
                }else if((j==17)){
                    this.defaut[i][j]="  |";
                }else{
                    this.defaut[i][j]=" . "; //L'intérieur du plateau est rempli de points pour bien distinguer les différentes positions, et les distances
                }
            }
        }
    }
    
    /**
     * crée 3 forêts de taille 3x3 placées aléatoirement sur le plateau.
     * les joueurs peuvent se cacher dans les forêts, 
     * on s'assure avant des les créer que les coordonnées générées ne sont pas déja occupées par un joueur
     * @param j1 le joueur 1 
     * @param j2 le joueur 2
     * @see Joueur
     */
    private void creationForet(Personnage j1, Personnage j2){
        int nbForet = 0;
        while(nbForet< 3){
            int a = (int) (Math.random()*13+3); //les forêts sont placées sur la surface utile du plateau
            int b = (int) (Math.random()*13+3); //le centre est à au moins une case du bord puisqu'elles sont de taille 3x3
            boolean touchePerso = false;// vérifie si la forêt recouvre l'emplacement d'un personnage
            for(int u= a-1; u<=a+1; u++){
				for(int v=b-1; v<=b+1; v++){
					if((u==j1.getX() && v==j1.getY()) || (v==j2.getY() && u==j2.getX())){
						touchePerso=true;
					}
				}
			}
            if(touchePerso==false){//si l'emplacement est libre, on crée une forêt de taille 3x3
                for(int u= a-1; u<=a+1; u++){
					for(int v=b-1; v<=b+1; v++){ 
						this.foret[u][v] = true;
					}
				}
				nbForet++;
            }
        }
    }
    
    /**Cette méthode place sur le plateau des bonus qui rendent de la vie aux joueurs.
     * on en place 3 à la fois max et on s'assure que la case ne soit pas déjà occupée par un joueur, une forêt ou un autre bonus 
     * @param j1 le joueur 1
     * @param j2 le joueur 2
     * @see nBonus
     * @see joueur
     */
    public void creationBonus(Personnage j1, Personnage j2){
        while(this.nbBonus() < 3){//il n'y a pas plus de 3 bonus à la fois sur le plateau
            int a = (int) (Math.random()*15+2);// les bonus sont placés dans la zone "utile" du plateau
            int b = (int) (Math.random()*15+2);
            boolean caseOccupee = false;
            for(int u= a-1; u<=a+1; u++){// on vérifie que les coordonnées générées ne soient pas déjà occupées par un joueur...
				for(int v=b-1; v<=b+1; v++){
					if((u==j1.getX() && v==j1.getY()) || (u==j2.getX() && v==j2.getY())){
						caseOccupee=true;
					}
				}
			}
            if(this.foret[a][b] == true || this.bonus[a][b] == true){// ou par une forêt ou un autre bonus
                caseOccupee=true;
            }
            
            if(caseOccupee==false){//si l'emplacement est libre on y crée un nouveau bonus
                this.bonus[a][b]=true;
            }
        }
    }
    
    /**
     * compte le nombre de bonus présents sur le plateau
     * @see creationBonus
     */
    private int nbBonus(){
        int compte=0;
        for(int i=0; i<this.bonus.length; i++){
            for(int j=0; j<this.bonus[0].length; j++){
                if(this.bonus[i][j]==true){
                    compte++;
                }
            }
        }
        return compte;
    }
    
    /**
     * affiche l'état actuel du plateau de jeu.
     * affiche l'emplacement des joueurs, des forêts et des bonus et le plateau par défaut aux autres endroits
     */
    public void affichage(Personnage j1, Personnage j2){
        
        for(int i=0; i<this.defaut.length; i++){
            for(int j=0; j<this.defaut[0].length; j++){
                
                if(this.foret[i][j]==true){
                    System.out.print(" # ");
                
                }else if(i==j1.getX() && j==j1.getY()){
                    System.out.print(j1.getSymbole()+" ");
                
                }else if(i==j2.getX() && j==j2.getY()){
                    System.out.print(j2.getSymbole()+" ");
                
                }else if(this.bonus[i][j]==true){
                    System.out.print("(+)");
                
                }else{
                    System.out.print(this.defaut[i][j]);
                }
            }
            System.out.println();
        }
    }
    
    /** cette méthode déplace un personnage.
     * le joueur entre les coordonnées ou il veut aller, on s'assure que ces coordonnées sont valides et qu'il a assez de vitesse
     * on exécute l'effet de la récupération éventuelle de bonus
     * @param p le joueur qui se déplace
     * @param autre l'autre joueur
     * @see affichage
     */
    public void deplacement(Personnage p, Personnage autre){
        Scanner sc = new Scanner(System.in);
        boolean deplValide = false;
        int i=0;
        int j=0;
        
        while(deplValide==false){
            System.out.println("Tu es actuellement en ("+(p.getY()-1)+" ; "+(p.getX()-1)+")");
            System.out.println("sur quelle colonne veux-tu déplacer ton héros ?");
            j = sc.nextInt()+1;
            System.out.println("sur quelle ligne veux-tu déplacer ton héros ?");
            i = sc.nextInt()+1;
            
            if((1<i && i<17) && (1<j && j<17) && (i!=autre.getX() || j!=autre.getY())){//on vérifie que le déplacement reste dans les limites du plateau et n'arrive pas sur la case déjà occupée par l'autre joueur
                
                if(Math.abs(i-p.getX())==Math.abs(j-p.getY()) && Math.abs(i-p.getX())<=p.getVitesse()){ //déplacement en diagonale, on vérifie que la vitesse est suffisante
                    deplValide=true;
                    p.setMouvRestant(p.getMouvRestant() - (Math.abs(i-p.getX())));
                    p.setX(i);
                    p.setY(j);
                    
                }else if(Math.abs(i-p.getX())==0 && Math.abs(j-p.getY())<=p.getVitesse()){ //déplacement en ligne droite suivant y, on vérifie que la vitesse est suffisante
                    deplValide=true;
                    p.setMouvRestant(p.getMouvRestant() - (Math.abs(j-p.getY())));
                    p.setY(j);
                    
                }else if(Math.abs(j-p.getY())==0 && Math.abs(i-p.getX())<=p.getVitesse()){ //déplacement en ligne droite selon x, on vérifie que la vitesse est suffisante
                    deplValide=true;
                    p.setMouvRestant(p.getMouvRestant() - (Math.abs(i-p.getX())));
                    p.setX(i);
                    
                }else if(Math.abs(j-p.getY())<Math.abs(i-p.getX()) && Math.abs(i-p.getX())<=p.getVitesse()){ //déplacement "hybride" qui va plus loin en x qu'en y, on vérifie que la vitesse est suffisante
                    deplValide=true;
                    p.setMouvRestant(p.getMouvRestant() - (Math.abs(i-p.getX())));
                    p.setX(i);
                    p.setY(j);
                    
                }else if(Math.abs(i-p.getX())<Math.abs(j-p.getY()) && (Math.abs(j-p.getY())<=p.getVitesse())){ //déplacement "hybride" qui va plus loin en y qu'en x, on vérifie que la vitesse est suffisante
                    deplValide=true;
                    p.setMouvRestant(p.getMouvRestant() - (Math.abs(j-p.getY())));
                    p.setX(i);
                    p.setY(j);
                    
                }else{//si la vitesse est insuffisante
                    System.out.println("Ton héros n'est pas assez rapide, il ne peut se déplacer que de "+p.getVitesse()+" cases");
                }
            }else{//si les coordonnées ne sont pas valides
                System.out.println("Une de ces coordonnées dépasse du plateau ou ton adversaire occupe déjà cette case");
            }
        }
        
        this.affichage(p, autre);
        System.out.println();
		if(this.foret[p.getX()][p.getY()]==true){//si le joueur arrive sur une case de forêt
			System.out.println(p.getNom()+" s'enfonce dans une étrange forêt ...");
            System.out.println();
		}
        if(this.bonus[p.getX()][p.getY()]==true){//si le joueur arrive sur un bonus
            p.setPV(p.getPV()+10);
            this.bonus[p.getX()][p.getY()] = false;
            System.out.println(p.getNom()+" récupère un bonus et regagne 10 PV !");
            System.out.println();
        }
    }
    
    public boolean[][] getBonus(){
        return this.bonus;
    }
    
    public void setBonus(int i, int j, boolean b){
        this.bonus[i][j]=b;
    }
    
    public boolean[][] getForet(){
        return this.foret;
    }
}
