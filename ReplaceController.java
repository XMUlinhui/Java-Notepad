package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ReplaceController {

    @FXML
    private Button quanbutihuan;

    @FXML
    private Button tihuan;

    @FXML
    private TextField chazhaoneirong;

    @FXML
    private CheckBox qufendaxiaoxie;

    @FXML
    private CheckBox xunhuan;

    @FXML
    private TextField tihuanwei;

    @FXML
    private Button chazhaoxiayige;
    
    @FXML
    private Pane pane;

    @FXML
    private Button quxiao;
    private MainController maincontroller;
    private String find_word;//查找文本
    private String replace_word;//查找文本
    private boolean find_case;//大小写
    private boolean find_cycle;//循环
    private boolean is_input=false;//确定了搜索
    private boolean is_replace=false;//确定了替换
    private int word_count=1;

    
    public ReplaceController()
    {
    	
    }
    
    public ReplaceController(MainController t,boolean is,boolean replace,String word,String replaceword,boolean both_upper_lower,boolean cycle)
    {
    	//获得maincontroller
    	maincontroller=t;
    	if(is)
    	{
    		is_input=true;
    	}
    	if(replace)
    	{
    		is_replace=true;
    	}
    	find_word=word;
		find_case=both_upper_lower;
		find_cycle=cycle;
    	replace_word=replaceword;
    }
    
    
    
    //查找下一个
    @FXML
    void find_next_word(ActionEvent event) {
    	
    	//调用查找方法
    	maincontroller.find_all_words_next(chazhaoneirong.getText(), 
    			true, !qufendaxiaoxie.isSelected(), xunhuan.isSelected());
    	
    }

    //替换
    @FXML
    void exchange_word(ActionEvent event) {
    	//如果没有选中或选择错误，则先查找
    	if(!maincontroller.replace_function(chazhaoneirong.getText(), tihuanwei.getText()))
    	//调用查找方法
    	maincontroller.find_all_words_next(chazhaoneirong.getText(), 
    			true, !qufendaxiaoxie.isSelected(), xunhuan.isSelected());
    }

    //替换全部
    @FXML
    void replace_all_word(ActionEvent event) {
    	maincontroller.replace_all(chazhaoneirong.getText(), tihuanwei.getText(),!qufendaxiaoxie.isSelected());
    }

    //取消
    @FXML
    void onButtonCancel(ActionEvent event) {
    	pane.getScene().getWindow().hide();
    	
    }
    
    //初始化
    public void initialize()
    {
    	//如果已经设置过，则将缓存写入，避免二次输入的麻烦
    	if(is_input)
    	{
    		chazhaoneirong.setText(find_word);
    		xunhuan.setSelected(find_cycle);
    		qufendaxiaoxie.setSelected(!find_case);
    	}
    	if(is_replace)
    	{
    		tihuanwei.setText(replace_word);
    	}
    
    	
    	/*初始化查找下一个按钮*/
    	if(chazhaoneirong.getText().isEmpty())
		{
			chazhaoxiayige.setDisable(true);
			tihuan.setDisable(true);
			quanbutihuan.setDisable(true);
		}
    	
    	chazhaoneirong.textProperty().addListener(c->
    	{
    		if(chazhaoneirong.getText().isEmpty())
    		{
    			chazhaoxiayige.setDisable(true);
    			tihuan.setDisable(true);
    			quanbutihuan.setDisable(true);
    		}
    		else
    		{
    			chazhaoxiayige.setDisable(false);
    			tihuan.setDisable(false);
    			quanbutihuan.setDisable(false);
    		}
    		
    	});

    }

}
