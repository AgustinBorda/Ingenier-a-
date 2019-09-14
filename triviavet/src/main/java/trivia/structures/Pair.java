package trivia.structures;

public class Pair <J,K> {
	private J first;
	private K second;
	
	public Pair (J fst,K snd){
		first = fst;
		second = snd;
	}

	public J getFirst() {
		return first;
	}
	
	public K getSecond() {
		return second;
	}
}
