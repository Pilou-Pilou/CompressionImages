package algo;

public class Cout {
	public final static int CREATION = 0;
	public final static int AJOUT_imoins1 = 1;
	public final static int AJOUT_iplus1 = 2;
	
	private int _cout;
	private int _decision; //equals to CREATION or AJOUT
	private int _nbBits; //nb bits used with this decision
	private int _nbPixels; //nbre de pixels dans le segment
	private int _numSegment; //num of the segment wich owns this
	
	public Cout(int decision, int cout, int nbBits, int nbPixels, int numSegment) throws Exception{
		setDecision(decision);
		setCout(cout);
		setNbBits(nbBits);
		setNbPixels(nbPixels);
		setNumSegment(numSegment);
	}

	public void setDecision(int decision) throws Exception{
		if(decision != CREATION && decision != AJOUT_imoins1  && decision != AJOUT_iplus1)
			throw new Exception("Decision must be equal to CREATION or AJOUT");
		
		_decision = decision;
	}
	
	public void setCout(int cout) throws Exception{
		if(cout<0)
			throw new Exception("Cout can't be negative");
		
		_cout = cout;
	}
	
	public int getDecision(){
		return _decision;
	}
	
	public int getCout(){
		return _cout;
	}

	public int getNbBits() {
		return _nbBits;
	}

	public void setNbBits(int nbBits) throws Exception {
		if(nbBits<1 && nbBits>8)
			throw new Exception("The number of used bits must be between 1 and 8");
			
		this._nbBits = nbBits;
	}

	public int getNbPixels() {
		return _nbPixels;
	}

	public void setNbPixels(int _nbPixels) {
		this._nbPixels = _nbPixels;
	}

	public int getNumSegment() {
		return _numSegment;
	}

	public void setNumSegment(int _numSegment) {
		this._numSegment = _numSegment;
	}
}
