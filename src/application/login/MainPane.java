package application.login;
/**
 * 主界面布局容器
 */
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MainPane extends Pane{

	Stage stage;
	public MainPane() {
		StackPane stack = new StackPane();
        stack.setPrefSize(360,139);
        stack.getStyleClass().add("top-window");

        Pane pane = new Pane();
        pane.setPrefSize(360,139);
        stack.getChildren().add(pane);

        //qq_icon图标
        ImageView icon = new ImageView(new Image("/application/res/qq_icon.png"));
        icon.setFitHeight(20);
        icon.setFitWidth(20);
        icon.setLayoutX(10);
        icon.setLayoutY(10);

        //勋章奖励图标
        ImageView medal = new ImageView(new Image("/application/res/medal.png"));
        medal.setFitHeight(20);
        medal.setFitWidth(20);
        medal.setLayoutX(100);
        medal.setLayoutY(10);

        //皮肤图标
        ImageView qq_skin = new ImageView(new Image("/application/res/skin.png"));
        qq_skin.setFitHeight(20);
        qq_skin.setFitWidth(20);
        qq_skin.setLayoutX(130);
        qq_skin.setLayoutY(10);

        //qq空间分享图标
        ImageView qq_share = new ImageView(new Image("/application/res/sq-share-qqzone.png"));
        qq_share.setFitHeight(20);
        qq_share.setFitWidth(20);
        qq_share.setLayoutX(160);
        qq_share.setLayoutY(10);

        //qq邮箱图标
        ImageView qq_email = new ImageView(new Image("/application/res/email.png"));
        qq_email.setFitHeight(20);
        qq_email.setFitWidth(20);
        qq_email.setLayoutX(190);
        qq_email.setLayoutY(10);

        //qq会员图标
        ImageView qq_member = new ImageView(new Image("/application/res/member.png"));
        qq_member.setFitHeight(20);
        qq_member.setFitWidth(20);
        qq_member.setLayoutX(220);
        qq_member.setLayoutY(10);

        //qq钱包图标
        ImageView qq_purse = new ImageView(new Image("/application/res/purse.png"));
        qq_purse.setFitHeight(20);
        qq_purse.setFitWidth(20);
        qq_purse.setLayoutX(250);
        qq_purse.setLayoutY(10);

        //三点式菜单图标
        ImageView three_dot = new ImageView(new Image("/application/res/three-dot.png"));
        three_dot.setFitHeight(20);
        three_dot.setFitWidth(20);
        three_dot.setLayoutX(280);
        three_dot.setLayoutY(10);

        //最小化窗口图标
        Button minimizeWindow = new Button();
        minimizeWindow.setPrefSize(25, 25);
        minimizeWindow.setId("minimizeWindow");
        minimizeWindow.setLayoutX(305);
        minimizeWindow.setLayoutY(8);
        /**
         * 最小化窗口点击事件
         */
        minimizeWindow.setOnAction(event -> {
        	stage = (Stage) minimizeWindow.getScene().getWindow();
        	stage.setIconified(true);
        });

        //关闭窗口图标
        Button closeWindow = new Button();
        closeWindow.setPrefSize(25, 25);
        closeWindow.setId("closeWindow");
        closeWindow.setLayoutX(330);
        closeWindow.setLayoutY(8);
        /**
         * 点击关闭窗口事件
         */
        closeWindow.setOnAction(event -> {
        	stage = (Stage) closeWindow.getScene().getWindow();
        	stage.close();
        });

        //用户头像
        ImageView avator = new ImageView(new Image("/application/res/avator.jpg"));
        avator.setFitWidth(50);
        avator.setFitHeight(50);
        Circle circle = new Circle();
        circle.setFill(Paint.valueOf("aqua"));
        circle.setCenterX(25);
        circle.setCenterY(25);
        circle.setRadius(25);
        avator.setClip(circle);
        avator.setLayoutX(10);
        avator.setLayoutY(45);

        //用户名
        Label userName = new Label("Promise");
        userName.setLayoutX(75);
        userName.setLayoutY(45);
        userName.getStyleClass().add("userName");

        //用户签名
        Label sign = new Label("Learning to change you!");
        sign.setLayoutX(75);
        sign.setLayoutY(75);
        sign.setTextFill(Paint.valueOf("white"));

        //天气
        ImageView weather = new ImageView(new Image("/application/res/weather.png"));
        weather.setLayoutX(280);
        weather.setLayoutY(45);
        weather.setFitHeight(50);
        weather.setFitWidth(70);

        //搜索框
        TextField search = new TextField();
        search.setLayoutY(109);
        search.setPrefSize(360,30);
        search.setPromptText("搜索");
        search.setPadding(new Insets(0,0,0,38));
        search.setOpacity(0.3);
        search.getStyleClass().add("search");

        //搜索框图标
        ImageView search_icon = new ImageView(new Image("/application/res/search.png"));
        search_icon.setLayoutX(15);
        search_icon.setLayoutY(116);
        search_icon.setFitWidth(15);
        search_icon.setFitHeight(15);

        pane.getChildren().addAll(icon,medal,qq_skin,qq_share,qq_email,qq_member,qq_purse,three_dot,minimizeWindow,closeWindow,avator,userName,sign,weather,search,search_icon);

        
        
        //Tab切换面板
        TabPane tabPane = new TabPane();
        tabPane.setLayoutY(139);
        tabPane.setPrefSize(360,542);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle("-fx-background-color: #ffa07a");

        Tab msg = new Tab("消息");

        Tab contact = new Tab("联系人");
        Tab dynamic = new Tab("动态");

        tabPane.getTabs().addAll(msg,contact,dynamic);
        this.getChildren().addAll(stack,tabPane);
	}
}
