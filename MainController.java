package application;

import java.awt.Desktop;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoManager;

import com.sun.javafx.print.PrintHelper.PrintAccessor;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainController extends Main{

    @FXML
    private BorderPane borderpane;
	
    @FXML
    private MenuItem shanchu;

    @FXML
    private Menu bianji;

    @FXML
    private MenuItem sousuo;
    
    @FXML
    private MenuItem chexiao;

    @FXML
    private MenuItem tihuan;

    @FXML
    private MenuItem fasongfankui;

    @FXML
    private TextArea textarea;

    @FXML
    private MenuItem suoxiao;

    @FXML
    private Menu geshi;

    @FXML
    private MenuItem yemianshezhi;

    @FXML
    private MenuItem guanyujishiben;

    @FXML
    private MenuItem xinjian;

    @FXML
    private MenuItem lingcunwei;

    @FXML
    private MenuItem quanxuan;

    @FXML
    private Menu wenjian;

    @FXML
    private CheckMenuItem zidonghuanhang;

    @FXML
    private MenuItem chakanbangzhu;

    @FXML
    private MenuItem zhuandao;

    @FXML
    private MenuItem dakai;

    @FXML
    private MenuItem fuzhi;

    @FXML
    private MenuItem chazhaoxiayige;

    @FXML
    private MenuItem ziti;

    @FXML
    private CheckMenuItem zhuangtailan;

    @FXML
    private Menu suofang;

    @FXML
    private MenuItem chazhaoshangyige;

    @FXML
    private MenuItem chazhao;

    @FXML
    private MenuItem baocun;

    @FXML
    private MenuItem fangda;

    @FXML
    private Menu chakan;

    @FXML
    private MenuItem jianqie;

    @FXML
    private MenuItem dayin;

    @FXML
    private MenuItem zhantie;

    @FXML
    private MenuBar menubar;

    @FXML
    private MenuItem xinchuangkou;

    @FXML
    private MenuItem tuichu;

    @FXML
    private MenuItem shijian;

    @FXML
    private MenuItem huifumorensuofang;

    @FXML
    private Pane pane;

    @FXML
    private GridPane gridpane;
    
    @FXML
    private Label weizhi; 
    
    @FXML
    private Label suofangbili;
    

    @FXML
    private Label bianma;

    @FXML
    private GridPane zhuangtai;
    private ContextMenu context=new ContextMenu();
    private MenuItem item1=new MenuItem("撤销(U)");
    private MenuItem item2=new MenuItem("重做(R)");
    private SeparatorMenuItem sitem1=new SeparatorMenuItem();
    private MenuItem item3=new MenuItem("剪切(T)");
    private MenuItem item4=new MenuItem("复制(C)");
    private MenuItem item5=new MenuItem("粘贴(P)");
    private MenuItem item6=new MenuItem("删除(D)");
    private SeparatorMenuItem sitem2=new SeparatorMenuItem();
    private MenuItem item7=new MenuItem("全选(A)");
    private SeparatorMenuItem sitem3=new SeparatorMenuItem();
    private MenuItem item8=new MenuItem("使用 Bing 搜索...");
    

    
    @FXML
    private MenuItem chongzuo;
    
    private boolean is_define_find_word=false;//是否确定了寻找的字符串
    private boolean is_define_replace_word=false;//是否确定了替换的字符串
    private String define_find_word;//确定寻找的字符串
    private String define_replace_word;//确定替换的字符串
    private boolean define_find_dir;//查找方向
    private boolean define_find_cycle;//是否循环
    private boolean define_find_allcase;//是否区分大小写
    private double zoom=1;//放大缩小比例
    private double font_size=12;
    
 
    
	/*判断文本是否修改过*/
	public boolean is_txt_edited()
	{
		if(textarea.getText().equals(origin))
		{
			return false;
		}
		else
			return true;
	}
    
    
    @FXML
    /*打开文件*/
    void open_file(ActionEvent event) throws IOException {

    	
    	FileChooser filechooser = new FileChooser();
    	filechooser.setTitle("打开");
    	filechooser.getExtensionFilters().addAll(new ExtensionFilter("文本文档","*txt"));
    	File selectedfile=filechooser.showOpenDialog(getStage());
    	
    	if(selectedfile!=null)//选择了文件
    	{
    		FileInputStream fin= new FileInputStream(selectedfile);
    		InputStreamReader reader=new InputStreamReader(fin);
    		BufferedReader buff=new BufferedReader(reader);
    		String out="";
    		String temp="";
    		while((temp=buff.readLine())!=null)
    		{
    			out+=temp;
    			out+="\n";//换行
    		}
    		out=out.substring(0,out.length()-1);
    		textarea.setText(out);//显示文本
    		setOrigin(out);;//设置初始对比文本为out
    		getStage().setTitle(selectedfile.getName()+" - 记事本");//设置标题
    		setFile_path(selectedfile.getAbsolutePath());//设置路径
    		setFile_name(selectedfile.getName());//设置文件名

    	}
    }
    
    /*获取Stage*/
	public Stage getStage()
	{
		Stage stage=(Stage)gridpane.getScene().getWindow();
		return stage;
	}
	
    @FXML
    /*保存*/
    boolean save_file(ActionEvent event) throws IOException {

    	if(getFile_path().equals("无标题"))//无标题则另存为
    	{	
    		return save_as(new ActionEvent());
    	}
    	else//否则保存
    	{
    		  BufferedWriter bufferedwriter=new BufferedWriter(new FileWriter(getFile_path()));
              bufferedwriter.write(textarea.getText());
              bufferedwriter.close();
              setOrigin(textarea.getText());
              return true;
    	}
    	
    }
	

    @FXML
    /*页面设置*/
    void page_set(ActionEvent event) {
    	PageFormat pf = new PageFormat();
    	java.awt.print.PrinterJob.getPrinterJob().pageDialog(pf);//打开页面设置
    	
    }
	
	
    
	/*另存为*/
    @FXML
    boolean save_as(ActionEvent event) throws IOException {

    	FileChooser fileChooser1 = new FileChooser();
        fileChooser1.setTitle("另存为");
        /*判断名称，提供默认名称*/
        if(!getFile_path().equals("无标题"))
        {
        	fileChooser1.initialFileNameProperty().setValue(getFile_name());
        }
        else
        {
        	fileChooser1.initialFileNameProperty().setValue("*.txt");
        }
        fileChooser1.getExtensionFilters().addAll(new ExtensionFilter("文本文档","*txt"));//过滤类型
        File file = fileChooser1.showSaveDialog(getStage());
        if (file != null) {
        	String output=file.getAbsolutePath();
        	if(!output.endsWith(".txt")) {output+=".txt";}//提供添加后缀功能
        	
            BufferedWriter bufferedwriter=new BufferedWriter(new FileWriter(output));
            bufferedwriter.write(textarea.getText());
            bufferedwriter.close();
            if(file.getName().endsWith(".txt"))//判断名称问题
            open_after_saveas(file.getName(), output);//自动变为新的文本
            else
            {
            	open_after_saveas(file.getName()+".txt", output);
            }
            return true;
        }
        return false;
    }
	
	
    @FXML
    /*退出程序*/
    void exit_main(ActionEvent event) throws IOException {
    	if(is_txt_edited())//判断如果文本被编辑过则确认退出
    	
    		{confirm_save();}
    	else//未被编辑过则直接退出
    	{
        	Stage origin_stage=(Stage)gridpane.getScene().getWindow();
        	origin_stage.close();
    	}
    	
    }
   
    @FXML
    /*打印文件*/
    void print_txt(ActionEvent event) {
    	PrintRequestAttributeSet printrequest = new HashPrintRequestAttributeSet();
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        PrintService[] printservices = PrintServiceLookup.lookupPrintServices(flavor, printrequest);
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        PrintService service = ServiceUI.printDialog(null, 200, 200, printservices,
                defaultService, flavor, printrequest);
        if (service != null) {
            try {
                DocPrintJob printjob = service.createPrintJob();
                printrequest.add(MediaSizeName.ISO_A4);
                if(is_txt_edited())//判断是否需要先保存文本
                {
                	
                	Alert alert = new Alert(AlertType.CONFIRMATION);
            	    alert.setTitle("打印");
            	    alert.setHeaderText("在打印之前需要先保存文本，是否继续？");
            	    
            	    /*设置按钮*/
            	    ButtonType buttonTypeSave = new ButtonType("保存(S)");
      
            	    ButtonType buttonTypeCancel = new ButtonType("取消",ButtonData.CANCEL_CLOSE);
            	
            	    alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeCancel);
            	
            	    Optional<ButtonType> result = alert.showAndWait();
            	    if (result.get() == buttonTypeSave){
            	       if(save_file(null))//判断保存成功，则继续打印
            	       {
            	    	   
            	       }
            	       else
            	       {
            	    	   return;
            	       }
            	    }
            
            	    else 
            	    {
            	    	return;
            	    }
                
                	
                	
                }
                FileInputStream fileinput = new FileInputStream(getFile_path());//打开选择的文件流
                DocAttributeSet docattributeset = new HashDocAttributeSet();

            	
                Doc doc = new SimpleDoc(fileinput, flavor, docattributeset);
                printjob.print(doc, printrequest);//开始打印
                Thread.sleep(10 * 1000);
            } catch (Exception e) {
            	Alert alert1 = new Alert(AlertType.ERROR);
            	alert1.setTitle("打印");
            	alert1.setHeaderText("打印失败");
            	alert1.setContentText("请检查打印机或相关软件是否正常运行！");
            	 
            	alert1.showAndWait();
            	
            }
        } 
        
}   
    
    
    
    /*另存为后默认打开新的文件*/
    void open_after_saveas(String name,String path) throws IOException {

		getStage().setTitle(name+" - 记事本");//设置标题
		setFile_path(path);//设置路径
		setFile_name(name);//设置文件
    	setOrigin(textarea.getText());//设置初始文本
		
    }
    
    //放大
    @FXML
    void zoom_in(ActionEvent event)
    {
    	if(zoom<4.9)
    	{
    		zoom+=0.1;
    	}
    	String out=String.format("缩放比例：%.0f",zoom*100);
    	out+="%";
    	suofangbili.setText(out);
    	
    	Font now_font=textarea.getFont();
    	//放大文字
    	switch(now_font.getStyle())
		{
		case "Regular":textarea.setFont(Font.font(now_font.getName(),FontPosture.REGULAR, font_size*zoom));break;
		case "Italic":textarea.setFont(Font.font(now_font.getName(),FontPosture.ITALIC, font_size*zoom));break;
		case "Bold":textarea.setFont(Font.font(now_font.getName(),FontWeight.BOLD, font_size*zoom));break;
		case "Bold Italic":	textarea.setFont(Font.font(now_font.getName(),FontWeight.BOLD,FontPosture.ITALIC, font_size*zoom));break;
		}
  
    	
    }
    
    //缩小
    @FXML
    void zoom_out(ActionEvent event)
    {
    	if(zoom>0.2)
    	{
    		zoom-=0.1;
    	}
    	String out=String.format("缩放比例：%.0f",zoom*100);
    	out+="%";
    	suofangbili.setText(out);
    	Font now_font=textarea.getFont();
    	//缩小文字
    	switch(now_font.getStyle())
		{
		case "Regular":textarea.setFont(Font.font(now_font.getName(),FontPosture.REGULAR, font_size*zoom));break;
		case "Italic":textarea.setFont(Font.font(now_font.getName(),FontPosture.ITALIC, font_size*zoom));break;
		case "Bold":textarea.setFont(Font.font(now_font.getName(),FontWeight.BOLD, font_size*zoom));break;
		case "Bold Italic":	textarea.setFont(Font.font(now_font.getName(),FontWeight.BOLD,FontPosture.ITALIC, font_size*zoom));break;
		}
    }
    
     @FXML
    /*恢复缩放比例*/
    void turn_zoom_to_one(ActionEvent event)
    {
    	zoom=1;
    	String out=String.format("缩放比例：%.0f",zoom*100);
    	out+="%";
    	suofangbili.setText(out);
    	Font now_font=textarea.getFont();
    	//恢复文字
    	switch(now_font.getStyle())
		{
		case "Regular":textarea.setFont(Font.font(now_font.getName(),FontPosture.REGULAR, font_size*zoom));break;
		case "Italic":textarea.setFont(Font.font(now_font.getName(),FontPosture.ITALIC, font_size*zoom));break;
		case "Bold":textarea.setFont(Font.font(now_font.getName(),FontWeight.BOLD, font_size*zoom));break;
		case "Bold Italic":	textarea.setFont(Font.font(now_font.getName(),FontWeight.BOLD,FontPosture.ITALIC, font_size*zoom));break;
		}
    }
     
     
     
    @FXML
    /*新建*/
    void new_txt(ActionEvent event) throws IOException {
    
    	getStage().close();//原窗口关闭
    	/*创建新窗口*/
    	Stage primaryStage=new Stage();
    	Main new_main=new Main();
    	
    	new_main.start(primaryStage);
    	
    }
   
    
    
    @FXML
    /*新窗口*/
    void new_window(ActionEvent event) throws IOException {
    
    	Stage primaryStage=new Stage();
    	Main new_main=new Main();
    	
    	new_main.start(primaryStage);
    }
    

    /*退出时确认是否保存*/
    void confirm_save() throws IOException
    {
    	
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("记事本");
	    alert.setHeaderText("你想将更改保存到 "+getFile_path()+" 吗？");
	    
	    /*设置按钮*/
	    ButtonType buttonTypeSave = new ButtonType("保存(S)");
	    ButtonType buttonTypeNotSave = new ButtonType("不保存(N)");
	    ButtonType buttonTypeCancel = new ButtonType("取消",ButtonData.CANCEL_CLOSE);
	
	    alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeNotSave, buttonTypeCancel);
	
	    Optional<ButtonType> result = alert.showAndWait();
	    if (result.get() == buttonTypeSave){
	       if(save_file(null))//判断保存成功退出
	       getStage().close();
	       else
	       {
	    	   
	       }
	    	
	    } else if (result.get() == buttonTypeNotSave) {
	    	getStage().close();
	   
	    } 
	    else 
	    {
	    
	    }
    
    }
    //设置查找缓存
    void set_define_find_word(String word,boolean findnext,boolean both_upper_lower,boolean cycle)
    {
    	is_define_find_word=true;
    	define_find_word=word;
    	define_find_allcase=both_upper_lower;
    	define_find_cycle=cycle;
    	define_find_dir=findnext;
    	
    }
    
    
    //获取查找缓存
    String get_define_find_word()
    {
    	return define_find_word;
    }

    //查找功能   findnext：是否向下寻找 both_upper_lower：不区分大小写 cycle：循环
    void find_all_words_next(String key,boolean findnext,boolean both_upper_lower,boolean cycle)
    {	
    	set_define_find_word(key,findnext,both_upper_lower,cycle);
    	String a;//原文本
    	int start_index;
    	
    	if(findnext)//向下查找
    		a=textarea.getText(textarea.getCaretPosition(), textarea.getLength());
    	else//向上查找
    		{
    		a=textarea.getText(0, textarea.getCaretPosition());
    		}
    	start_index=textarea.getCaretPosition();
    	if(both_upper_lower)//不区分大小写
    	{
    		a=a.toLowerCase();
    		
    		key=key.toLowerCase();
    	}
    	if(findnext)//向下查找
    	{
    		if(a.indexOf(key)==-1)
    		{
    			if(cycle)//循环
    			{
    				a=textarea.getText(0, textarea.getLength());//获取全部文本
    				start_index=0;//开始位置为0
    		    	if(both_upper_lower)//不区分大小写
    		    	{
    		    		a=a.toLowerCase();
    		    		
    		    		key=key.toLowerCase();
    		    	}
    				if(a.indexOf(key)==-1)//仍然搜不到则显示没有退出
    				{
    					Alert alert1 = new Alert(AlertType.INFORMATION);
    	    			alert1.setTitle("记事本");
    	    			alert1.setHeaderText("查找结束");
    	    			alert1.setContentText("找不到"+"\""+get_define_find_word()+"\"");
    	    			alert1.showAndWait();
    	    			return;
    				}
    			}
    			else
    			{
    			Alert alert1 = new Alert(AlertType.INFORMATION);
    			alert1.setTitle("记事本");
    			alert1.setHeaderText("查找结束");
    			alert1.setContentText("找不到"+"\""+get_define_find_word()+"\"");
    			alert1.showAndWait();
    			return;
    			}
    		}
    		start_index+=a.indexOf(key);
    		int end_index=start_index+key.length();
    		/*避免切换向上向下时出现重复选择*/
    		//判断选择的范围与目前已有选择相同，则继续向下找
    		if(textarea.getSelection().getStart()==start_index&&textarea.getSelection().getEnd()==end_index)
    		{
    			textarea.positionCaret(end_index);
    			find_all_words_next(key, findnext, both_upper_lower, cycle);
    		}
    		else
    		textarea.selectRange(start_index, end_index);
    	
    	}
    	else//向上查找
    	{
    		if(a.lastIndexOf(key)==-1)//没有找到
    		{
    			if(cycle)//循环
    			{
    				a=textarea.getText(0, textarea.getLength());//获取全部文本
    		    	if(both_upper_lower)//不区分大小写
    		    	{
    		    		a=a.toLowerCase();
    		    		
    		    		key=key.toLowerCase();
    		    	}
    				if(a.lastIndexOf(key)==-1)//仍然搜不到则显示没有退出
    				{
    					Alert alert1 = new Alert(AlertType.INFORMATION);
    	    			alert1.setTitle("记事本");
    	    			alert1.setHeaderText("查找结束");
    	    			alert1.setContentText("找不到"+"\""+get_define_find_word()+"\"");
    	    			alert1.showAndWait();
    	    			return;
    				}
    			}
    			else {
    			Alert alert1 = new Alert(AlertType.INFORMATION);
    			alert1.setTitle("记事本");
    			alert1.setHeaderText("查找结束");
    			alert1.setContentText("找不到"+"\""+get_define_find_word()+"\"");
    			alert1.showAndWait();
    			return;
    			}
    		}
    		start_index=a.lastIndexOf(key);//获取最后一个对应字符串
    		int end_index=start_index+key.length();
    		/*避免切换向上向下时出现重复选择*/
    		//判断选择的范围与目前已有选择相同，则继续向上找
    		if(textarea.getSelection().getStart()==start_index&&textarea.getSelection().getEnd()==end_index)
    		{
    			textarea.positionCaret(start_index);
    			find_all_words_next(key, findnext, both_upper_lower, cycle);
    		}
    		else
    		textarea.selectRange(end_index, start_index);
   
  
    	}
    }

    
    @FXML
    /*查找下一个*/
    void find_next_word(ActionEvent event) throws IOException {
    	if(is_define_find_word)//已经确认查找内容
    	{
    		find_all_words_next(define_find_word, true, define_find_allcase, define_find_cycle);
    	}
    	else
    	{
    		find_word(null);
    	}
    }
    
    @FXML
    /*查找上一个*/
    void find_prev_word(ActionEvent event) throws IOException {
    	if(is_define_find_word)//已经确认查找内容
    	{
    		find_all_words_next(define_find_word, false, define_find_allcase, define_find_cycle);
    	}
    	else
    	{
    		find_word(null);
    	}
    }
    
    //替换功能
    boolean replace_function(String key,String replace)
    {
    	
    	if(textarea.getSelectedText().isEmpty()||!textarea.getSelectedText().equals(key))
    	{
    		
    		return false;
    	}
    	if(textarea.getSelectedText().equals(key))
    	{
    		textarea.replaceSelection(replace);
    	
    		return true;
    	}
		return false;
    	
    }
    
    //替换全部
    void replace_all(String key,String replace,boolean both_upper_lower)
    {
    	textarea.deselect();
    	String a=textarea.getText();//获取全部文本
    	int index=0;
    	if(both_upper_lower)//不区分大小写
		{
		    a=a.toLowerCase();	
		    key=key.toLowerCase();
		}
    	if(a.lastIndexOf(key)==-1)//没有找到
		{
		    return;
		}
    	//进入寻找和替换循环
    	while(true)
    	{
		int start_index;
		start_index=a.lastIndexOf(key);//获取最后一个对应字符串
		if(start_index==-1)return;//找不到相应字符串则停止
		int end_index=start_index+key.length();
		textarea.selectRange(end_index, start_index);
		textarea.replaceSelection(replace);
		a=textarea.getText(0,start_index);
		if(both_upper_lower)//不区分大小写
		{
		    a=a.toLowerCase();	
		    key=key.toLowerCase();
		}
    	}
    }
    
    
    
    
    /*打开替换界面*/
    @FXML
    void replace_scene(ActionEvent event) throws IOException
    {
    	FXMLLoader fxmlloader=new FXMLLoader(getClass().getResource("ReplaceScene.fxml"));
		fxmlloader.setLocation(getClass().getResource("ReplaceScene.fxml"));
		fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
		fxmlloader.setController(new ReplaceController(this,is_define_find_word,is_define_replace_word,define_find_word,define_replace_word,define_find_allcase,define_find_cycle));
		Parent root = fxmlloader.load();
		Scene scene = new Scene(root,430,200);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setTitle("替换");
		primaryStage.getIcons().add(new Image("file:icon.jpg"));
		primaryStage.setResizable(false);
		primaryStage.show();
    }
    
    
    @FXML
    /*撤销*/
    void undo_button(ActionEvent event) {
    	textarea.undo();
    }
    
    @FXML
    /*重做*/
    void redo_button(ActionEvent event) {
    	textarea.redo();
    }

    @FXML
    /*剪切*/
    void cut_button(ActionEvent event) {
    	textarea.cut();
    }

    @FXML
    /*复制*/
    void copy_button(ActionEvent event) {
    	textarea.copy();
    }

    @FXML
    /*粘贴*/
    void paste_button(ActionEvent event) {
    	textarea.paste();
    }

    @FXML
    /*删除*/
    void del_button(ActionEvent event) {
    	textarea.deleteText(textarea.getSelection());
    }
    
    
    @FXML
    /*选择字体*/
    void choose_font(ActionEvent event) throws IOException {
    	FXMLLoader fxmlloader=new FXMLLoader(getClass().getResource("FontScene.fxml"));
		fxmlloader.setLocation(getClass().getResource("FontScene.fxml"));
		fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
		fxmlloader.setController(new FontController(this,textarea.getFont(),font_size));
		Parent root = fxmlloader.load();
		Scene scene = new Scene(root,550,500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setTitle("字体");
		primaryStage.getIcons().add(new Image("file:icon.jpg"));
		primaryStage.setResizable(false);
		
		
		primaryStage.show();
    }
    
    void set_text_Font(Font now_font)
    {
    	font_size=now_font.getSize();//得到设置的字号
    	//按缩放比例进行放大缩小
    	switch(now_font.getStyle())
		{
		case "Regular":textarea.setFont(Font.font(now_font.getName(),FontPosture.REGULAR, font_size*zoom));break;
		case "Italic":textarea.setFont(Font.font(now_font.getName(),FontPosture.ITALIC, font_size*zoom));break;
		case "Bold":textarea.setFont(Font.font(now_font.getName(),FontWeight.BOLD, font_size*zoom));break;
		case "Bold Italic":	textarea.setFont(Font.font(now_font.getName(),FontWeight.BOLD,FontPosture.ITALIC, font_size*zoom));break;
		}
    }
    
    
    
    @FXML
    /*查找*/
    void find_word(ActionEvent event) throws IOException  {
    	FXMLLoader fxmlloader=new FXMLLoader(getClass().getResource("FindScene.fxml"));
		fxmlloader.setLocation(getClass().getResource("FindScene.fxml"));
		fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
		fxmlloader.setController(new FindController(this,is_define_find_word,define_find_word,define_find_dir,define_find_allcase,define_find_cycle));
		Parent root = fxmlloader.load();
		Scene scene = new Scene(root,430,160);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setTitle("查找");
		primaryStage.getIcons().add(new Image("file:icon.jpg"));
		primaryStage.setResizable(false);
		
		
		primaryStage.show();
		
	}
    
  
    
    
    /*判断能否粘贴，剪切板是否是字符串*/
    void can_paste() throws UnsupportedFlavorException, IOException
    {
    	
    	Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
    	//获取剪切板内容
    	Transferable trans=clipboard.getContents(null);
    	if(trans!=null)
    	{
    		if(trans.isDataFlavorSupported(DataFlavor.stringFlavor))
    		{
    			zhantie.setDisable(false);
    			item5.setDisable(false);
    			return;
    		}
    	}
    	zhantie.setDisable(true);
    	item5.setDisable(true);
    	
    }
    
    /*全选*/
    @FXML
    void select_all(ActionEvent event) {
    	
    	textarea.selectAll();
    }
    
    /*增加时间*/
    @FXML
    void add_time(ActionEvent event) {

    	Date day=new Date();
    	SimpleDateFormat sdf=new SimpleDateFormat("HH:mm yyyy/MM/dd");//设置格式
    	int temp=textarea.getCaretPosition();//获得当前位置
    	textarea.insertText(temp, sdf.format(day).toString());//增加时间字符串
    	
    	
    }
    
    /*寻找帮助*/
    @FXML
    void look_for_help(ActionEvent event) throws IOException {
    	if(Desktop.isDesktopSupported())
    	{	
    		java.net.URI uri;
    		/*打开搜索网页*/
    		uri=java.net.URI.create("https://cn.bing.com/search?q=获取有关windows10中的记事本的帮助");
    		Desktop dt=Desktop.getDesktop();
    		if(dt.isSupported(Desktop.Action.BROWSE))//判断是否支持浏览器
    		{
    			dt.browse(uri);
    		}
    		else
    		{
    			Alert alert1 = new Alert(AlertType.ERROR);
            	alert1.setTitle("搜索失败");
            	alert1.setHeaderText("打开浏览器失败");
            	alert1.setContentText("请检查浏览器是否正确设置！");
            	 
            	alert1.showAndWait();
    		}
    		
    	}
    	else
    	{
    		Alert alert1 = new Alert(AlertType.ERROR);
        	alert1.setTitle("搜索失败");
        	alert1.setHeaderText("打开浏览器失败");
        	alert1.setContentText("请检查浏览器是否正确设置！");
        	 
        	alert1.showAndWait();
    	}
    
    	
    }
    
    /*发送反馈*/
    @FXML
    void feedback_way(ActionEvent event) throws IOException
    {
    	FXMLLoader fxmlloader=new FXMLLoader(getClass().getResource("feedback.fxml"));
		fxmlloader.setLocation(getClass().getResource("feedback.fxml"));
		fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root = fxmlloader.load();
		Scene scene = new Scene(root,500,500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setTitle("反馈方式");
		primaryStage.getIcons().add(new Image("file:icon.jpg"));
		primaryStage.setResizable(false);
		primaryStage.show();
    }
    
    /*关于记事本*/
    @FXML
    void about_software(ActionEvent event) throws IOException
    {
    	FXMLLoader fxmlloader=new FXMLLoader(getClass().getResource("about.fxml"));
		fxmlloader.setLocation(getClass().getResource("about.fxml"));
		fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root = fxmlloader.load();
		Scene scene = new Scene(root,500,500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setTitle("关于\"记事本\"");
		primaryStage.getIcons().add(new Image("file:icon.jpg"));
		primaryStage.setResizable(false);
		primaryStage.show();
    }
    
    
    /*转到*/
    @FXML
    void transfer_to(ActionEvent event) throws IOException {
    	
    	//得到总行数
    	String a=textarea.getText();
    	int row=1;
		int max=a.length();
		//统计行数
		for(int i=0;i<max;i++)
		{
			if(a.charAt(i)=='\n')
				{row++;
				}
		}
    	
    	FXMLLoader fxmlloader=new FXMLLoader(getClass().getResource("GotoScene.fxml"));
		fxmlloader.setLocation(getClass().getResource("GotoScene.fxml"));
		fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root = fxmlloader.load();
		Scene scene = new Scene(root,300,160);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setTitle("转到指定行");
		primaryStage.getIcons().add(new Image("file:icon.jpg"));
		primaryStage.setResizable(false);
		primaryStage.initModality(Modality.APPLICATION_MODAL);
	
		goto_controller=fxmlloader.getController();//获得控制器
		
		primaryStage.showAndWait();
		if(goto_controller.get_status())
		{
			if(goto_controller.get_return_value()<=row)//行数小于总行数
			{
				int low=goto_controller.get_return_value();//需要转到的行数
				int pos=0;
				int temprow=0;
				int tempcol=0;
			//循环至对应行停止
				for(int j=0;j<max;j++)
				{
					if(temprow==low-1)break;//结束标志
					if(a.charAt(j)=='\n')//换行
					{
						tempcol++;
						temprow++;
						pos+=tempcol;
						tempcol=0;
					}
					else
					{
						tempcol++;
					}
					
			}
				
				textarea.positionCaret(pos);//转移到对应行
			}
			else//行数大于总行数
			{
				Alert alert1 = new Alert(AlertType.INFORMATION);
	        	alert1.setTitle("记事本 - 跳行");
	        	alert1.setHeaderText("行数有误");
	        	alert1.setContentText("行数超过了总行数");
	        	alert1.showAndWait();
			}
		}
    }
    
    /*使用网页搜索*/
    @FXML
    void search_internet(ActionEvent event) throws IOException {
    	if(Desktop.isDesktopSupported())
    	{	
    		java.net.URI uri;
    		/*打开搜索网页*/
    		if(textarea.getSelectedText().isEmpty())
    		{
    			String a="https://cn.bing.com/search?q=";
        		//防止中文编码导致的搜索错误
        		a+=URLEncoder.encode(textarea.getText(),"UTF-8");
         		//防止异常情况发生，如果异常则直接打开搜索原始界面
         		try {
         		uri=java.net.URI.create(a);
         		}
         		catch(Exception e)
         		{
         			uri=java.net.URI.create("https://cn.bing.com/search?q=");
             		
         		}
    		}
    		 else
    		{
    
    		String a="https://cn.bing.com/search?q=";
    		//防止中文编码导致的搜索错误
    		a+=URLEncoder.encode(textarea.getSelectedText(),"UTF-8");
     		//防止异常情况发生，如果异常则直接打开搜索原始界面
     		try {
     		uri=java.net.URI.create(a);
     		}
     		catch(Exception e)
     		{
     			uri=java.net.URI.create("https://cn.bing.com/search?q=");
         		
     		}
     		}
    		Desktop dt=Desktop.getDesktop();
    		if(dt.isSupported(Desktop.Action.BROWSE))//判断是否支持浏览器
    		{
    			dt.browse(uri);
    		}
    		else
    		{
    			Alert alert1 = new Alert(AlertType.ERROR);
            	alert1.setTitle("搜索失败");
            	alert1.setHeaderText("打开浏览器失败");
            	alert1.setContentText("请检查浏览器是否正确设置！");
            	 
            	alert1.showAndWait();
    		}
    		
    	}
    	else
    	{
    		Alert alert1 = new Alert(AlertType.ERROR);
        	alert1.setTitle("搜索失败");
        	alert1.setHeaderText("打开浏览器失败");
        	alert1.setContentText("请检查浏览器是否正确设置！");
        	 
        	alert1.showAndWait();
    	}
    }
    
    
    
    public void initialize() throws UnsupportedFlavorException, IOException
    {
    	
    	/*初始化按钮状态*/
    	can_paste();
    	chexiao.setDisable(true);
    	chongzuo.setDisable(true);
    	
    	
    	zhuangtailan.setSelected(true);
    	
    	/*以下代码段用于使得界面控件自适应*/
    	borderpane.prefWidthProperty().bind(gridpane.widthProperty());
    	borderpane.prefHeightProperty().bind(gridpane.heightProperty());
    	menubar.prefWidthProperty().bind(borderpane.widthProperty());
    	textarea.prefWidthProperty().bind(borderpane.widthProperty());
    	zhuangtai.prefWidthProperty().bind(borderpane.widthProperty());
    	
    	
    	/*以下代码段用于初始化按钮*/
    	if(textarea.getSelectedText().isEmpty())
		{
			fuzhi.setDisable(true);
			jianqie.setDisable(true);
			shanchu.setDisable(true);
			item3.setDisable(true);
			item4.setDisable(true);
			item6.setDisable(true);
		}
		else
		{
			fuzhi.setDisable(false);
			jianqie.setDisable(false);
			shanchu.setDisable(false);
			item3.setDisable(false);
			item4.setDisable(false);
			item6.setDisable(false);
		}
    	
    	/*以下代码段用于设置右键菜单*/
 
    	item1.setDisable(true);
    	item2.setDisable(true);
    	context.getItems().addAll(item1,item2,sitem1,item3,item4,item5,item6,sitem2,
    			item7,sitem3,item8);
    	
    	textarea.setContextMenu(context);
    	//撤销
    	item1.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
    		undo_button(null);
    	
			}
		});
    	//重做
    	item2.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
    		redo_button(null);
    	
			}
		});
    	//剪切
    	item3.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
    		cut_button(null);
    	
			}
		});
    	
    	//复制
    	item4.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
    		copy_button(null);
    	
			}
		});
    	
    	//粘贴
    	item5.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
    		paste_button(null);
    	
			}
		});
    	//删除
    	item6.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
    		del_button(null);
    	
			}
		});
    	//全选
    	item7.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			textarea.selectAll();
    	
			}
		});
    	//使用Bing搜索
    	item8.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				try {
					search_internet(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	
			}
		});
    	
    	
    	/*以下代码段用于绑定快捷键*/
    	xinjian.setAccelerator(KeyCombination.valueOf("CTRL+N"));
    	xinchuangkou.setAccelerator(KeyCombination.valueOf("CTRL+SHIFT+N"));
    	dakai.setAccelerator(KeyCombination.valueOf("CTRL+O"));
    	baocun.setAccelerator(KeyCombination.valueOf("CTRL+S"));
    	lingcunwei.setAccelerator(KeyCombination.valueOf("CTRL+SHIFT+S"));
    	dayin.setAccelerator(KeyCombination.valueOf("CTRL+P"));
    	chexiao.setAccelerator(KeyCombination.valueOf("CTRL+Z"));
    	jianqie.setAccelerator(KeyCombination.valueOf("CTRL+X"));
    	fuzhi.setAccelerator(KeyCombination.valueOf("CTRL+C"));
    	zhantie.setAccelerator(KeyCombination.valueOf("CTRL+V"));
    	quanxuan.setAccelerator(KeyCombination.valueOf("CTRL+A"));
    	shanchu.setAccelerator(KeyCombination.valueOf("DEL"));
    	sousuo.setAccelerator(KeyCombination.valueOf("CTRL+E"));
    	chazhao.setAccelerator(KeyCombination.valueOf("CTRL+F"));
    	chazhaoshangyige.setAccelerator(KeyCombination.valueOf("SHIFT+F3"));
    	chazhaoxiayige.setAccelerator(KeyCombination.valueOf("F3"));
    	fangda.setAccelerator(KeyCombination.valueOf("CTRL+加号"));
    	suoxiao.setAccelerator(KeyCombination.valueOf("CTRL+减号"));
    	
    	//监听F3和F5敲击事件
    	textarea.setOnKeyPressed(event->
    	{
    		//放大缩小
    		if(event.isControlDown())
    		{
    			if(event.getCode().equals(KeyCode.ADD))
    			{
    				zoom_in(null);
    			}
    			else if((event.getCode().equals(KeyCode.MINUS)))
    			{
    				zoom_out(null);
    			}
    				
    		}
    		if(!event.isShiftDown())
    		{
    		if(event.getCode().equals(KeyCode.F3))
    		{
    			try {
					find_next_word(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		else if(event.getCode().equals(KeyCode.F5))
    		{
    			add_time(null);
    		}
    		}
    	
    	});
   
    	
    	
    	tihuan.setAccelerator(KeyCombination.valueOf("CTRL+L"));
    	zhuandao.setAccelerator(KeyCombination.valueOf("CTRL+G"));
    	shijian.setAccelerator(KeyCombination.keyCombination("F5"));
    	chongzuo.setAccelerator(KeyCombination.valueOf("CTRL+Y"));
    	
    	/*以下代码段为自动换行*/
    	zidonghuanhang.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(newValue==true)//打开自动换行
				{
					textarea.setWrapText(true);
					zhuandao.setDisable(true);
				}
				else//关闭自动换行
				{
					textarea.setWrapText(false);
					zhuandao.setDisable(false);
				}
			}
		});
    	
    	/*以下代码段为状态栏显示与关闭*/
    	zhuangtailan.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(newValue==true)//打开状态栏
				{
					zhuangtai.setManaged(true);
					zhuangtai.setVisible(true);
					
				}
				else//关闭状态栏
				{
					zhuangtai.setManaged(false);
					zhuangtai.setVisible(false);
				}
			}
		});
    	
    	/*以下代码段为状态栏文字改变*/
    	textarea.caretPositionProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				if(oldValue==newValue)
				{
					System.out.println("equal");
				}
				String a=textarea.getText(0,textarea.getCaretPosition());
				int row=1;
				int col=1;
				int max=a.length();
				//统计行数和列数
				for(int i=0;i<max;i++)
				{
					if(a.charAt(i)=='\n')
						{row++;col=1;}
					else
					{
						col++;
					}
				}
				weizhi.setText("第 "+row+" 行，第 "+col+" 列");
			}
    		
		});
    
    	
    	/*以下代码段为撤销按钮状态*/
    	textarea.undoableProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(newValue==true)
				{
					chexiao.setDisable(false);
					item1.setDisable(false);
				}
				else
				{
					chexiao.setDisable(true);
					item1.setDisable(true);
				}
			
			}
		});
    	
    	/*以下代码段为重做按钮状态*/
    	textarea.redoableProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(newValue==true)
				{
					chongzuo.setDisable(false);
					item2.setDisable(false);
				}
				else
				{
					chongzuo.setDisable(true);
					item2.setDisable(true);
				}
			}
		});
    	
    	
    	 
    	/*以下代码段为复制、剪切、删除按钮状态*/
    	
    	textarea.selectionProperty().addListener((temp,oldValue,newValue) ->{

				
				if(textarea.getSelectedText().isEmpty())
					{
						fuzhi.setDisable(true);
						jianqie.setDisable(true);
						shanchu.setDisable(true);
						item3.setDisable(true);
						item4.setDisable(true);
						item6.setDisable(true);
					}
					else
					{
						jianqie.setDisable(false);
						fuzhi.setDisable(false);
						shanchu.setDisable(false);
						item3.setDisable(false);
						item4.setDisable(false);
						item6.setDisable(false);
					}
		
    	
			
		});
    
    	
    	/*判断能否粘贴*/
    	bianji.setOnShown(event->{
    		try {
				can_paste();
			} catch (UnsupportedFlavorException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    	
    }
}
