import java.util.Scanner;
public class Plateau{
    private String[][] defaut;
    private boolean[][] bonus;
    private boolean[][] foret;
    
    public Plateau(Personnage j1, Personnage j2){
        
        this.defaut = new String[19][19];
        creationDefaut();
        this.foret = new boolean[19][19];
        creationForet(j1, j2);
        this.bonus = new boolean[19][19];
        creationBonus(j1, j2);
        
        
    }
    
    public void creationDefaut(){ //remplissage du plateau de base
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
    
    /** Cette méthode crée 3 forêts
     * placées aléatoirement sur le plateau
     * @param les joueurs j1 et j2
     */
    private void creationForet(Personnage j1, Personnage j2){
        int nbForet = 0;
        while(nbForet< 3){
            int a = (int) (Math.random()*13+3);
            int b = (int) (Math.random()*13+3);
            boolean touchePerso = false;
            for(int u= a-1; u<=a+1; u++){
				for(int v=b-1; v<=b+1; v++){
					if((u==j1.getX() && v==j1.getY()) || (v==j2.getY() && u==j2.getX())){
						touchePerso=true;
					}
				}
			}
            if(touchePerso==false){
                for(int u= a-1; u<=a+1; u++){
					for(int v=b-1; v<=b+1; v++){ 
						this.foret[u][v] = true;
					}
				}
				nbForet++;
            }
        }
    }
    
    /**Cette méthode place sur le plateau 
     * des bonus qui rendent de la vie aux joueurs
     * @param j1 le joueur 1
     * @param j2 le joueur 2
     */
    public void creationBonus(Personnage j1, Personnage j2){
        while(this.nbBonus() < 3){
            int a = (int) (Math.random()*15+2);
            int b = (int) (Math.random()*15+2);
            boolean caseOccupee = false;
            for(int u= a-1; u<=a+1; u++){
				for(int v=b-1; v<=b+1; v++){
					if((u==j1.getX() && v==j1.getY()) || (u==j2.getX() && v==j2.getY())){
						caseOccupee=true;
					}
				}
			}
            if(this.foret[a][b] == true || this.bonus[a][b] == true){
                caseOccupee=true;
            }
            
            if(caseOccupee==false){
                this.bonus[a][b]=true;
            }
        }
    }
    
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
    
    /** Cette méthode affiche l'état actuel du plateau de jeu
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
    
    /** cette méthode déplace un personnage
     * vers des coordonnées précises
     * @param p le joueur qui se déplace
     * @param autre l'autre joueur
     */
    public void deplacement(Personnage p, Personnage autre){
        Scanner sc = new Scanner(System.in);
        boolean deplValide = false;
        int i=0;
        int j=0;
        
        while(deplValide==false){
            System.out.println("sur quelle colonne veux-tu déplacer ton héros ?");
            j = sc.nextInt()+1;
            System.out.println("sur quelle ligne veux-tu déplacer ton héros ?");
            i = sc.nextInt()+1;
            
            if((1<i && i<17) && (1<j && j<17) && (i!=autre.getX() || j!=autre.getY())){
                
                if(Math.abs(i-p.getX())==Math.abs(j-p.getY()) && Math.abs(i-p.getX())<=p.getVitesse()){ //déplacement en diagonale
                    deplValide=true;
                    p.setMouvRestant(p.getMouvRestant() - (Math.abs(i-p.getX())));
                    p.setX(i);
                    p.setY(j);
                    
                }else if(Math.abs(i-p.getX())==0 && Math.abs(j-p.getY())<=p.getVitesse()){ //déplacement en ligne droite suivant y
                    deplValide=true;
                    p.setMouvRestant(p.getMouvRestant() - (Math.abs(j-p.getY())));
                    p.setY(j);
                    
                }else if(Math.abs(j-p.getY())==0 && Math.abs(i-p.getX())<=p.getVitesse()){ //déplacement en ligne droite selon x
                    deplValide=true;
                    p.setMouvRestant(p.getMouvRestant() - (Math.abs(i-p.getX())));
                    p.setX(i);
                    
                }else if(Math.abs(j-p.getY())<Math.abs(i-p.getX()) && Math.abs(i-p.getX())<=p.getVitesse()){ //déplacement "hybride" qui va plus loin en x qu'en y
                    deplValide=true;
                    p.setMouvRestant(p.getMouvRestant() - (Math.abs(i-p.getX())));
                    p.setX(i);
                    p.setY(j);
                    
                }else if(Math.abs(i-p.getX())<Math.abs(j-p.getY()) && (Math.abs(j-p.getY())<=p.getVitesse())){ //déplacement "hybride" qui va plus loin en y qu'en x
                    deplValide=true;
                    p.setMouvRestant(p.getMouvRestant() - (Math.abs(j-p.getY())));
                    p.setX(i);
                    p.setY(j);
                    
                }else{
                    System.out.println("Ton héros n'est pas assez rapide, il ne peut se déplacer que de "+p.getVitesse()+" cases");
                }
            }else{
                System.out.println("Une de ces coordonnées dépasse du plateau ou ton adversaire occupe déjà cette case");
            }
        }
        
        this.affichage(p, autre);
        System.out.println();
		if(this.foret[p.getX()][p.getY()]==true){	
			System.out.println(p.getNom()+" s'enfonce dans une étrange forêt ...");
            System.out.println();
		}
        if(this.bonus[p.getX()][p.getY()]==true){
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
