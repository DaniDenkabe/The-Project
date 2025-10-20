

public class Sample {
	
	private boolean[] onOff;
	private int[][] intVariables;
	private float[][] floatVariables;
	private double[][] doubleVariables;
	private boolean[] booleanVariables;
	
	/* intVariables:
	 * 		0: Wave
	 * 		1: BitDepth
	 * floatVariables:
	 * 		0:pre
	 * 		1:post
	 * 		2:gain
	 * 		3:clip
	 * 		4:pitch
	 * 		5:humGain
	 * 		6:popsGain
	 * 		7:clicksGain
	 * 		8:rumbleGain
	 * 		9:hpFrequency
	 * 		10:lpFrequency
	 * 		11:cracklesGain
	 * 		12:vinylNoiseGain
	 * doubleVariables:
	 * 		0:ratio
	 * 		1:decay        ==
	 * 		2:waveFreq
	 * 		3:threshold
	 * 		4:noiseGain
	 * 		5:echoLength   ==
	 * 		6:sampleRate
	 * 		7:attackCoeff
	 * 		8:releaseCoeff
	 * 		9:scaleParameter
	 * 		10:makeupGainLinear
	 * 		11:loopSize
	 * booleanVariables:
	 * 		0:anti-alias
	 * 		1:flutterOn
	 * 		2:looperOn
	 */
	public Sample() {
		intVariables = new int[2][3];
		
		floatVariables = new float[12][3];
		
		doubleVariables = new double[12][3];
		
		booleanVariables =  new boolean[3];
		
		intVariables[0][0] = 0;
		intVariables[0][1] = 0;
		intVariables[0][2] = 2;
		
		intVariables[1][0] = 16;
		intVariables[1][1] = 1;
		intVariables[1][2] = 16;
		
		floatVariables[0][0] = 50000;
		floatVariables[0][1] = 0;
		floatVariables[0][2] = 50000;
		
		floatVariables[1][0] = 50000;
		floatVariables[1][1] = 0;
		floatVariables[1][2] = 50000;
		
		floatVariables[2][0] = 1;
		floatVariables[2][1] = 0;
		floatVariables[2][2] = 3;
		
		floatVariables[3][0] = 1;
		floatVariables[3][1] = 0;
		floatVariables[3][2] = 1;
		
		floatVariables[4][0] = 1;
		floatVariables[4][1] = 0.1f;
		floatVariables[4][2] = 2;
		
		floatVariables[5][0] = 1;
		floatVariables[5][1] = 0;
		floatVariables[5][2] = 3;
		
		floatVariables[6][0] = 1;
		floatVariables[6][1] = 0;
		floatVariables[6][2] = 3;
		
		floatVariables[7][0] = 1;
		floatVariables[7][1] = 0;
		floatVariables[7][2] = 3;
		
		floatVariables[8][0] = 1;
		floatVariables[8][1] = 0;
		floatVariables[8][2] = 3;
		
		floatVariables[9][0] = 0;
		floatVariables[9][1] = 0;
		floatVariables[9][2] = 4000;
		
		floatVariables[10][0] = 4000;
		floatVariables[10][1] = 0;
		floatVariables[10][2] = 4000;
		
		floatVariables[11][0] = 1;
		floatVariables[11][1] = 0;
		floatVariables[11][2] = 3;
		
		
		doubleVariables[0][0] = 1;
		doubleVariables[0][1] = 1;
		doubleVariables[0][2] = 20;
		
		doubleVariables[1][0] = 0;
		doubleVariables[1][1] = 0;
		doubleVariables[1][2] = 1;
		
		doubleVariables[2][0] = 0;
		doubleVariables[2][1] = 0;
		doubleVariables[2][2] = 0.15;
		
		doubleVariables[3][0] = -5;
		doubleVariables[3][1] = -20;
		doubleVariables[3][2] = -5;
		
		doubleVariables[4][0] = 0;
		doubleVariables[4][1] = 0;
		doubleVariables[4][2] = 0.05;
		
		doubleVariables[5][0] = 0.05;
		doubleVariables[5][1] = 0;
		doubleVariables[5][2] = 100;
		
		doubleVariables[6][0] = 44100;
		doubleVariables[6][1] = 0;
		doubleVariables[6][2] = 44100;
		
		doubleVariables[7][0] = 0.01;
		doubleVariables[7][1] = 0.01;
		doubleVariables[7][2] = 10;
		
		doubleVariables[8][0] = 0.01;
		doubleVariables[8][1] = 0.01;
		doubleVariables[8][2] = 10;
		
		doubleVariables[9][0] = 0;
		doubleVariables[9][1] = 0;
		doubleVariables[9][2] = 1;
		
		doubleVariables[10][0] = 1;
		doubleVariables[10][1] = 0;
		doubleVariables[10][2] = 4;
		
		doubleVariables[11][0] = 0.5;
		doubleVariables[11][1] = 0.05;
		doubleVariables[11][2] = 5;
		
		booleanVariables[0] = false;
		booleanVariables[1] = false;
		booleanVariables[2] = false;
		
//		ON & OFF-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		onOff = new boolean[5];
		onOff[0] = true;
		onOff[1] = true;
		onOff[2] = true;
		onOff[3] = true;
		onOff[4] = false;
		
	}
	
	public double getVariable(int index, int minMax) {
		if (index < intVariables.length) {
			return intVariables[index][minMax];
		} else if (index >= intVariables.length && index < intVariables.length + floatVariables.length) {
    		return floatVariables[index - intVariables.length][minMax];
    	}
		
		return doubleVariables[index - (intVariables.length + floatVariables.length)][minMax];
	}
	
	public void setVariable(int index, double newValue) {
		if (index < intVariables.length) {
			intVariables[index][0] = (int) newValue;
		} else if (index >= intVariables.length && index < intVariables.length + floatVariables.length) {
    		floatVariables[index - intVariables.length][0] = (float) newValue;
    	} else {
    		doubleVariables[index - (intVariables.length + floatVariables.length)][0] = newValue;
    	}
	}
	
	public boolean getBooleanVariable(int index) {
		return booleanVariables[index - (intVariables.length + floatVariables.length + doubleVariables.length)];
	}
	
	public void setVariable(int index, boolean newValue) {
		booleanVariables[index - (intVariables.length + floatVariables.length + doubleVariables.length)] = newValue;
	}

	public boolean[] getOnOff() {
		return onOff;
	}

	public void setOnOff(boolean[] onOff) {
		this.onOff = onOff;
	}
	

}
