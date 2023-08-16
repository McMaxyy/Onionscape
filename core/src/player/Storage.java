package player;

public class Storage {
	private static Storage instance = null;
	
	public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }
	
	// Load abilities
	public Abilities swing = new Swing();
	public Abilities rend = new Rend();
	public Abilities whirlwind = new Whirlwind();
	public Abilities groundBreaker = new GroundBreaker();
	public Abilities bash = new Bash();
	public Abilities barrier = new Barrier();
	public Abilities harden = new Harden();
}
