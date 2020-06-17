package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class FontController {

    @FXML
    private Button change_font;

    @FXML
    private TextField xuanzeziti;

    @FXML
    private TextField xuanzezixing;

    @FXML
    private Label shili;

    @FXML
    private ListView<String> zixing;

    @FXML
    private ListView<String> ziti;

    @FXML
    private TextField xuanzedaxiao;

    @FXML
    private ListView<String> daxiao;
    

    @FXML
    private Button quxiao;
    @FXML
    private GridPane gridpane;
    

    private MainController maincontroller;
    private Font origin_font;
    private double origin_size;
    
    public FontController() {
		
	}
    public FontController(MainController t,Font f,double d) {
  		maincontroller=t;
  		origin_font=f;
  		origin_size=d;
  	}
    
    @FXML
    void OnButtonChange(ActionEvent event) {
    	Font f = null;
    	switch(xuanzezixing.getText())
		{
		case "Regular":f=Font.font(xuanzeziti.getText(),FontPosture.REGULAR, Double.parseDouble(xuanzedaxiao.getText()));break;
		case "Italic":f=Font.font(xuanzeziti.getText(),FontPosture.ITALIC, Double.parseDouble(xuanzedaxiao.getText()));break;
		case "Bold":f=Font.font(xuanzeziti.getText(),FontWeight.BOLD, Double.parseDouble(xuanzedaxiao.getText()));break;
		case "Bold Italic":	f=Font.font(xuanzeziti.getText(),FontWeight.BOLD,FontPosture.ITALIC, Double.parseDouble(xuanzedaxiao.getText()));break;
		}
    	maincontroller.set_text_Font(f);
    	gridpane.getScene().getWindow().hide();
    }

    @FXML
    void OnButtonCancel(ActionEvent event) {
    	gridpane.getScene().getWindow().hide();
    }
    
    public void initialize()
    {
    
    	//设置初始值
    	xuanzeziti.setText(origin_font.getName());
		xuanzedaxiao.setText(String.valueOf(origin_size));
		xuanzezixing.setText(origin_font.getStyle());
		shili.setFont(origin_font);
		//字号
		ObservableList<String> size_list=FXCollections.observableArrayList("4","8","9"
				,"10","11","12","14","16","18","20","22","24","26","28","36","42","48","63","72");
		//字形
		ObservableList<String> style_list=FXCollections.observableArrayList("Regular","Bold","Italic","Bold Italic");
		//字体
		ObservableList<String> font_list=FXCollections.observableArrayList(Font.getFontNames());
		ziti.setItems(font_list);//添加字体表
		zixing.setItems(style_list);//添加字形表
		daxiao.setItems(size_list);//添加大小
		//默认选中
		ziti.getSelectionModel().select(font_list.indexOf(origin_font.getName()));
		zixing.getSelectionModel().select(style_list.indexOf(origin_font.getStyle()));
		int index = 0;
		/*遍历选中字体大小*/
		for(String size:size_list)
		{
			if(Double.parseDouble(size)==origin_size)
			{
				index=size_list.indexOf(size);
				break;
			}
		}
		daxiao.getSelectionModel().select(index);
		
		//选择字体时，改变示例
		ziti.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						// TODO Auto-generated method stub	
						xuanzeziti.setText(newValue);
						switch(xuanzezixing.getText())
						{
						case "Regular":shili.setFont(Font.font(xuanzeziti.getText(), FontPosture.REGULAR,Double.parseDouble(xuanzedaxiao.getText())));break;
						case "Italic":shili.setFont(Font.font(xuanzeziti.getText(),FontPosture.ITALIC, Double.parseDouble(xuanzedaxiao.getText())));break;
						case "Bold":shili.setFont(Font.font(xuanzeziti.getText(),FontWeight.BOLD, Double.parseDouble(xuanzedaxiao.getText())));break;
						case "Bold Italic":	shili.setFont(Font.font(xuanzeziti.getText(),FontWeight.BOLD,FontPosture.ITALIC, Double.parseDouble(xuanzedaxiao.getText())));break;
						}
					
						
					}
				}
		);
		
		//改变大小时，改变示例
		daxiao.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						// TODO Auto-generated method stub
						xuanzedaxiao.setText(newValue);
						switch(xuanzezixing.getText())
						{
						case "Regular":shili.setFont(Font.font(xuanzeziti.getText(),FontPosture.REGULAR, Double.parseDouble(xuanzedaxiao.getText())));break;
						case "Italic":shili.setFont(Font.font(xuanzeziti.getText(),FontPosture.ITALIC, Double.parseDouble(xuanzedaxiao.getText())));break;
						case "Bold":shili.setFont(Font.font(xuanzeziti.getText(),FontWeight.BOLD, Double.parseDouble(xuanzedaxiao.getText())));break;
						case "Bold Italic":	shili.setFont(Font.font(xuanzeziti.getText(),FontWeight.BOLD,FontPosture.ITALIC, Double.parseDouble(xuanzedaxiao.getText())));break;
						}
						
					}
				}
		);
		
		//改变字形时，改变示例
		zixing.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						// TODO Auto-generated method stub
						xuanzezixing.setText(newValue);
						switch(newValue)
						{
						case "Regular":shili.setFont(Font.font(xuanzeziti.getText(), FontPosture.REGULAR,Double.parseDouble(xuanzedaxiao.getText())));break;
						case "Italic":shili.setFont(Font.font(xuanzeziti.getText(),FontPosture.ITALIC, Double.parseDouble(xuanzedaxiao.getText())));break;
						case "Bold":shili.setFont(Font.font(xuanzeziti.getText(),FontWeight.BOLD, Double.parseDouble(xuanzedaxiao.getText())));break;
						case "Bold Italic":	shili.setFont(Font.font(xuanzeziti.getText(),FontWeight.BOLD,FontPosture.ITALIC, Double.parseDouble(xuanzedaxiao.getText())));break;
						}
					}
				}
		);
    }

}
