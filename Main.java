package application;
	
import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	MainController controller;//获得controller
	GotoController goto_controller;//获得controller
	
		
	protected String origin="";
	
	protected String file_name="无标题";
	
	protected String file_path="无标题";
	

	public String getOrigin()
	{
		return origin;
	}
	public String getFile_path()
	{
		return file_path;
	}
	public String getFile_name()
	{
		return file_name;
	}
	
	public void setOrigin(String a)
	{
		origin=a;
	}
	public void setFile_path(String a)
	{
		file_path=a;
	}
	public void setFile_name(String a)
	{
		file_name=a;
	}
	
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			FXMLLoader fxmlloader=new FXMLLoader(getClass().getResource("Main.fxml"));
			fxmlloader.setLocation(getClass().getResource("Main.fxml"));
			fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
			Parent root = fxmlloader.load();
			Scene scene = new Scene(root,1200,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("无标题 - 记事本");
			primaryStage.getIcons().add(new Image("file:icon.jpg"));
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent arg0) {
					// TODO Auto-generated method stub
					if(controller.is_txt_edited())
						try {
							confirm_save(arg0);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

				}
			});
			controller=fxmlloader.getController();//获得控制器
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	   /*退出时确认是否保存*/
	public void confirm_save(WindowEvent we) throws IOException
    {
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("记事本");
	    
	    
	    alert.setHeaderText("你想将更改保存到 "+controller.getFile_path()+" 吗？");
	
	    ButtonType buttonTypeSave = new ButtonType("保存(S)");
	    ButtonType buttonTypeNotSave = new ButtonType("不保存(N)");
	    ButtonType buttonTypeCancel = new ButtonType("取消",ButtonData.CANCEL_CLOSE);
	
	    alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeNotSave, buttonTypeCancel);
	
	    Optional<ButtonType> result = alert.showAndWait();
	    if (result.get() == buttonTypeSave){
	    	
		       if(controller.save_file(null))//保存成功则退出
		       controller.getStage().close();
		       else
		       {
		    	   we.consume();//否则取消关闭
		       }
	    	
	    } else if (result.get() == buttonTypeNotSave) {
	    
	   
	    } else if (result.get() == buttonTypeCancel) {
	    	we.consume();
	    }
    
    }

	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
