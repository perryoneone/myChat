package application.dialog;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class StyleDialog extends Dialog
{
	private LayoutPane root = new LayoutPane();
	
	public StyleDialog()
	{	
		initDialog();
	}
	
	public StyleDialog(Window owner)
	{	
		super(owner);
		initDialog();
	}
	
	public StyleDialog(Node anchor)
	{	
		super(anchor);
		initDialog();
	}
	
	//////////////////////////
	
	private void initDialog()
	{
		initStyle(StageStyle.UNDECORATED);			
		initDragSupport();
		
		root.closeButton.setOnAction((e)->{
			cancel();
		});
	}
	
	public void setDialogTitle(String title)
	{
		root.title.setText(title);
	}
	
	@Override
	public void setContentView(Parent content)
	{	
		root.content.setCenter(content);
		super.setContentView(root);
	}

	////////////// 布局 ///////////////////
	private class LayoutPane extends Pane
	{
		Label title = new Label();	
		Button closeButton = new Button();			
		BorderPane content = new BorderPane();	
		
		public LayoutPane()
		{		
			// 右上角关闭按钮的图片
			Image image = new Image(StyleDialog.class.getResource("ic_close.png").toExternalForm());
			closeButton.setGraphic(new ImageView(image));
			closeButton.setCursor(Cursor.HAND);
			
			getChildren().addAll(title, closeButton, content);
			
			// css
			this.getStylesheets().add( StyleDialog.class.getResource("style-dialog.css").toExternalForm());
			this.getStyleClass().add("style-dialog");
			this.setStyle("-fx-background-color: lightblue;");
			closeButton.getStyleClass().add("style-dialog-close");
			title.getStyleClass().add("style-dialog-title");
			content.getStyleClass().add("style-dialog-content");
		}

		@Override
		protected void layoutChildren()
		{
			double w = getWidth();
			double h = getHeight();
			if(w <= 0 || h<= 0) return;
			
			double x = 0, y = 0;
			Insets insets = this.getInsets();
			if(insets != null)
			{
				x += insets.getLeft();
				y += insets.getTop();
				w -= ( insets.getLeft() + insets.getRight());
				h -= ( insets.getTop() + insets.getBottom());
			}
			
			double iconW = 30;
			double iconH = 30;
			title.resizeRelocate(x, y, w-iconW, iconH);
			closeButton.resizeRelocate(x + w-iconW, y, iconW, iconH);
			content.resizeRelocate(x, y + iconH, w, h - iconH);
		}
		
	}
	
	////////////// 拖动支持 ///////////////
	private boolean dragging = false;
	private double windowX = 0, windowY = 0;
	private double startX, startY;
	
	private void initDragSupport()
	{
		root.setOnMousePressed((MouseEvent e)->{
			windowX = StyleDialog.this.getX();
			windowY = StyleDialog.this.getY();
			startX = e.getScreenX();
			startY = e.getScreenY();
		});
		
		root.setOnDragDetected((MouseEvent e)->{
			if(e.getButton() == MouseButton.PRIMARY) 
			{
				dragging = true;
			}			
		});
		
		root.setOnMouseDragged((MouseEvent e)->{
			if(!dragging) return;
			double dx = e.getScreenX() - startX;
			double dy = e.getScreenY() - startY;
			setX ( windowX + dx);
			setY ( windowY + dy);
		});
		
		root.setOnMouseReleased((MouseEvent e)->{
			dragging = false;
		});
	}
		
}
