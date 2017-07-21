package Map;
public class Coordinate {
	public int x;
	public int y;
	
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object other){
		if(!(other instanceof Coordinate)){
			return false;
		}
		else{
			return x == ((Coordinate)other).x && y == ((Coordinate)other).y;
		}
	}
	
	@Override
	public int hashCode(){
		return x * 7 + y * 13;
	}
	
	@Override
	public String toString(){
		return "(" + x + ", " + y + ")";
	}
}
