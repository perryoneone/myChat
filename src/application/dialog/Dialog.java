package application.dialog;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Dialog extends Stage
{
	private boolean result = false;
	protected Scene scene;
	
	public Dialog()
	{	
		initDialog();
	}
	
	public Dialog(Window owner)
	{	
		initOwner(owner);
		initDialog();
	}
	
	public Dialog(Node anchor)
	{
		initOwner( anchor.getScene().getWindow());
		initDialog();
	}
	
	private void initDialog()
	{
		//this.initStyle(StageStyle.UNDECORATED);
		this.initModality(Modality.WINDOW_MODAL);
	}
	
	public void setContentView(Parent root)
	{
		scene = new Scene(root);
		setScene(scene); 
		sizeToScene();		
	}
	
	///////////////////////////////
	// final: 子类不可以override 此方法
	public final void accept()
	{
		result = true;
		close();
	}
	
	public final void cancel()
	{
		result = false;
		close();
	}
	
	public boolean exec()
	{
		showAndWait(); // 卡住
		return result;
	}
	

	
}
