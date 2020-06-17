package application;

import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class GotoController {

    @FXML
    private Button cancel_button;

    @FXML
    private TextField hanghao;

    @FXML
    private Button goto_button;
    
    @FXML
    private GridPane goto_root;
    
    private boolean is_ok;
    
    

    @FXML
    void on_goto_button(ActionEvent event) {
    	is_ok=true;//表示确定转到
    	Stage now_stage=(Stage)goto_root.getScene().getWindow();
    	now_stage.close();//关闭界面
    }

    @FXML
    void on_cancel_button(ActionEvent event) {
    	Stage now_stage=(Stage)goto_root.getScene().getWindow();
    	now_stage.close();//关闭界面
    }

    boolean get_status()//得到状态信息
    {
    	return is_ok;
    }
    /*得到行数返回值*/
    int get_return_value()
    {
    	
    	int ret=-1;
    	try
    	{
    		ret=Integer.parseInt(hanghao.getText());
    	}
    	catch (Exception e) {
			// TODO: handle exception
			Alert alert1 = new Alert(AlertType.INFORMATION);
        	alert1.setTitle("记事本 - 跳行");
        	alert1.setHeaderText("行数有误");
        	alert1.setContentText("请检查行数输入！");
        	 
        	alert1.showAndWait();
		}
    	return ret;
    }
    
    public void initialize()
    {
    	/*以下代码段用于只输入数字*/
    	hanghao.setTextFormatter(new TextFormatter<>(input -> {
            if (input.isContentChange()) {
            	//长度为0直接返回，让get_return_value方法接收
                if (input.getControlNewText().length() == 0) {
                    return input;
                }
                try {
                    Integer.parseInt(input.getControlNewText());
                    return input;
                } catch (NumberFormatException e) {
                }
                //null表示不输入
                return null;

            }
            return input;
        }));
    	
    	
    	is_ok=false;//初始化，表示未确定
    	
    	
    }
    
     
}
