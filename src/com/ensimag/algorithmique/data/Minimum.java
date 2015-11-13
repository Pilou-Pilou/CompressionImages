package com.ensimag.algorithmique.data;

public class Minimum {
	private int _indice;
	private int _value;
	
	public Minimum(int value, int indice){
		setValue(value);
		setIndice(indice);
	}

	public int getIndice() {
		return _indice;
	}

	public void setIndice(int _indice) {
		this._indice = _indice;
	}

	public int getValue() {
		return _value;
	}

	public void setValue(int _value) {
		this._value = _value;
	}
}
