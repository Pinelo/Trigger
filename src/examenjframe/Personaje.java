/*
	Personaje
	Todas las entidades que se mueven y se mueren(menos algunas excepciones)
	pertenecen a esta clase, incluyendo al jugador.

	@author Hugo Gonzalez
	@version 1.00
*/

public class Personaje extends Base {

	private Boolean killable;		//determina si se puede morir o no
	private Boolean alive;			//determina si esta vivo o muerto
	private Boolean timeLock;		//determina si sus acciones se pueden alterar por el personaje o no
	private Boolean interrupt;		//alerta cuando se debe de interrumpir la accion del personaje
	private int maxHealth;			//determina la vida maxima del personaje
	private int curHealth;			//determina la vida actual del personaje
	private int curSpeed;			//determina la velocidad actual del personaje
	private int maxMana;			//determina la mana maxima del personaje
	private int curMana;			//determina la mana actual del personaje
	private int curDir;				//determina la direccion actual del personaje
	private int curStrike;			//determina el ataque actual del personaje
	private LinkedList listScript;	//indica las acciones esta por tomar el personaje
	private LinkedList listItems;	//determina los objetos que estan en posesion del personaje
	private LinkedList listSpells;	//determina los poderes que tiene el personaje
	private LinkedList moveList;	//determina los ataques que puede hacer el personaje

	public Personaje(int posX, int posY, Image imaImage, Boolean killable, Boolean alive, Boolean timeLock, int maxHealth, int curHealth, int curSpeed, 
						int maxMana, int curMana, int curDir, int curStrike, LinkedList listScript, LinkedList listItems, 
						LinkedList listSpells, LinkedList moveList ) {

		//Inicializa la clase de tipo base
		super(posX, posY, imaImage);

		//es establecen todos los valores del personaje
		this.killable = killable;
		this.alive = alive;
		this.timeLock = timeLock;
		this.maxHealth = maxHealth;
		this.curHealth = curHealth;
		this.curSpeed = curSpeed;
		this.maxMana = maxMana;
		this.curMana = curMana;
		this.curDir = curDir;
		this.curStrike = curStrike;
		this.listScript = listScript;
		this.listItems = listItems;
		this.listScript = listSpells;
		this.moveList = moveList;
	}

	public Boolean getKillable() {
		return killable;
	}
	public boolean getAlive() {
		return alive;
	}
	public Boolean getTimeLock() {
		return timeLock;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public int getCurHealth() {
		return curHealth;
	}
	public int getCurSpeed() {
		return curSpeed;
	}
	public int getMaxMana() {
		return maxMana;
	}
	public int getCurMana() {
		return curMana;
	}
	public int getCurDir() {
		return curDir;
	}
	public int getCurStrike() {
		return curStrike;
	}
	public LinkedList getListScript() {
		return listScript;
	}
	public LinkedList getListItems() {
		return listItems;
	}
	public LinkedList getListSpells() {
		return listSpells;
	}
	public LinkedList getMoveList() {
		return moveList;
	}
	
	
	
	public void setKillable(Boolean killable) {
		this.killable = killable;
	}
	public void setAlive(Boolean alive) {
		this.alive = alive;
	}
	public void setTimeLock(Boolean timeLock) {
		this.timeLock = timeLock;
	}
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	public void setCurHealth(int curHealth) {
		this.curHealth = curHealth;
	}
	public void setCurSpeed(int curSpeed) {
		this.curSpeed = curSpeed;
	}
	public void setmaxMana(int maxMana) {
		this.maxMana = maxMana;
	}
	public void setCurMana(int curMana) {
		this.curMana = curMana;
	}
	public void setCurDir(int curDir) {
		this.curDir = curDir;
	}
	public void setCurStrike(int curStrike) {
		this.curStrike = curStrike;
	}
	public void setListScript(LinkedList listScript) {
		this.listScript = listScript;
	}
	public void setListItems(LinkedList listItems) {
		this.listItems = listItems;
	}
	public void setListSpells(LinkedList listSpells) {
		this.listSpells = listSpells;
	}
	public void setMoveList(LinkedList moveList) {
		this.moveList = moveList;
	}
	

}
