package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FindController {

    @FXML
    private Button cancel_button;

    @FXML
    private TextField chazhaoneirong;

    @FXML
    private CheckBox qufendaxiaoxie;

    @FXML
    private RadioButton xiangxia;

    @FXML
    private CheckBox xunhuan;

    @FXML
    private RadioButton xiangshang;

    @FXML
    private Button chazhaoxiayige;

    @FXML
    private ToggleGroup direction;
    
    @FXML
    private GridPane gridpane;
    
    private MainController maincontroller;
    
    private String find_word;//查找文本
    private boolean find_dir;//查找方向
    private boolean find_case;//大小写
    private boolean find_cycle;//循环
    private boolean is_input=false;



    
    public FindController()
    {
    	
    }
    
    public FindController(MainController t,boolean is,String word,boolean findnext,boolean both_upper_lower,boolean cycle)
    {
    	//获得maincontroller
    	maincontroller=t;
    	if(is)
    	{
    		find_word=word;
    		is_input=true;
    		find_dir=findnext;
    		find_case=both_upper_lower;
    		find_cycle=cycle;
    	}
    	
    }

    @FXML
    void find_next(ActionEvent event) throws IOException {
    	
    	
    	boolean findnext=false;
    	boolean upper_lower=true;
    	boolean cycle=false;
    	if(xiangxia.isSelected())//向下查找
    	{
    		findnext=true;
    	}
    	if(qufendaxiaoxie.isSelected())//区分大小写
    	{
    		upper_lower=false;
    	}
    	if(xunhuan.isSelected())//循环
    	{
    		cycle=true;
    	}
    	
    	
    	maincontroller.find_all_words_next(chazhaoneirong.getText(),findnext,upper_lower,cycle);
    	
    
    }
    

    @FXML
    void on_cancel_button(ActionEvent event) {
    	Stage now_stage = (Stage)gridpane.getScene().getWindow();
    	now_stage.close();
    	
    }
    
    public void initialize()
    {
    	//如果已经设置过，则将缓存写入，避免二次输入的麻烦
    	if(is_input)
    	{
    		chazhaoneirong.setText(find_word);
    		xunhuan.setSelected(find_cycle);
    		qufendaxiaoxie.setSelected(!find_case);
    		xiangxia.setSelected(find_dir);
    		xiangshang.setSelected(!find_dir);
    	}
    
    	
    	/*初始化查找下一个按钮*/
    	if(chazhaoneirong.getText().isEmpty())
		{
			chazhaoxiayige.setDisable(true);
		}
    	
    	chazhaoneirong.textProperty().addListener(c->
    	{
    		if(chazhaoneirong.getText().isEmpty())
    		{
    			chazhaoxiayige.setDisable(true);
    		}
    		else
    		{
    			chazhaoxiayige.setDisable(false);
    		}
    		
    	});

    }

}
