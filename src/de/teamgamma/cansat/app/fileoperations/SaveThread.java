package de.teamgamma.cansat.app.fileoperations;

/**
 * @author Teamgamma
 * 
 *This class implements Runnable to save the data parallel in an own Thread.
 * 
 * 
 */


public class SaveThread implements Runnable {
	private Save save = Save.getInstance();
	private Double[][] data;

	@Override
	public void run() {
		save.saveAll(data);
	}
	public void setData(Double[][] data){
		this.data = data;
		
	}

}
