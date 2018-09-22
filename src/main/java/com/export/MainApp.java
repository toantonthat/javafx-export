package com.export;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = {JmxAutoConfiguration.class})
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class MainApp extends Application {
	protected ConfigurableApplicationContext springContext;
	private double xOffset = 0;
    private double yOffset = 0;
    
	public static void main(final String[] args) {
		Application.launch(args);
	}

	@Override
	public void init() throws Exception {
		springContext = springBootApplicationContext();
	}

	@Override
	public void start(Stage stage) throws Exception {
		/*Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root);
        stage.setTitle("Export File:: Version 1.0");
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();*/
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml")); // DMMMMM dau ..
        Parent root = (Parent) fxmlLoader.load();
        //Image image = new Image(String.valueOf(this.getClass().getResource("/images/product.png.png")));
        //stage.getIcons().add(image);
        stage.setTitle("Export File:: Version 1.0");
        stage.setScene(new Scene(root));
        stage.show();
	}

	@Override
	public void stop() throws Exception {
		springContext.close();
	}

	private ConfigurableApplicationContext springBootApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(MainApp.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
	}
}
