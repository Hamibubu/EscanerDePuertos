import com.opencsv.exceptions.CsvValidationException;
import iteso.scanner.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.concurrent.Task;
import java.util.Date;


public class ScannerPuertosPOO extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Creacion de ventana
        Image icono = new Image("radar.png");
        primaryStage.getIcons().add(icono);
        primaryStage.setTitle("Scanner de Puertos POO");
        primaryStage.setWidth(400);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);

        Rectangle background = new Rectangle(370, 130);
        background.setFill(Color.web("#1f2628"));
        background.setArcWidth(20); // Redondear los bordes horizontalmente
        background.setArcHeight(20); // Redondear los bordes verticalmente
        background.setTranslateY(-95);

        Rectangle background2 = new Rectangle(370, 225);
        background2.setFill(Color.web("#1f2628"));
        background2.setArcWidth(20); // Redondear los bordes horizontalmente
        background2.setArcHeight(20); // Redondear los bordes verticalmente
        background2.setTranslateY(90);



        //Objetos en ventana
        Image image = new Image("LOGO.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setFitHeight(70);
        imageView.setTranslateX(20);
        imageView.setLayoutY(-200);


        Font font = new Font("Roboto", 14);
        Label label = new Label("IPv4 Target:   ");
        label.setFont(font);
        label.setTextFill(Color.WHITE);
        Label labelResultados = new Label("Resultados:");
        labelResultados.setFont(font);
        labelResultados.setTextFill(Color.WHITE);
        TextField textField = new TextField();                   //Caja input de IP
        textField.setText("127.0.0.1");
        textField.setPrefWidth(215);
        textField.setFont(font);
        textField.setStyle("-fx-control-inner-background: #2c3639;-fx-text-fill: white;");

        Button exportar = new Button("Exportar");
        exportar.setStyle("-fx-background-color: #2c3639; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;");
        exportar.setOnMouseEntered(e -> exportar.setStyle("-fx-background-color: #666666; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        exportar.setOnMouseExited(e -> exportar.setStyle("-fx-background-color: #2c3639; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        exportar.setOnMousePressed(e -> exportar.setStyle("-fx-background-color: #999999; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        Button cancelar = new Button("Cancelar");
        Button btnLimpiar = new Button("Limpiar");
        cancelar.setStyle("-fx-background-color: #2c3639; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;");
        cancelar.setOnMouseEntered(e -> cancelar.setStyle("-fx-background-color: #666666; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        cancelar.setOnMouseExited(e -> cancelar.setStyle("-fx-background-color: #2c3639; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        cancelar.setOnMousePressed(e -> cancelar.setStyle("-fx-background-color: #999999; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        btnLimpiar.setStyle("-fx-background-color: #2c3639; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;");
        btnLimpiar.setOnMouseEntered(e -> btnLimpiar.setStyle("-fx-background-color: #666666; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        btnLimpiar.setOnMouseExited(e -> btnLimpiar.setStyle("-fx-background-color: #2c3639; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        btnLimpiar.setOnMousePressed(e -> btnLimpiar.setStyle("-fx-background-color: #999999; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        Button btnCopiar = new Button("Copiar");
        btnCopiar.setStyle("-fx-background-color: #2c3639; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;");
        btnCopiar.setOnMouseEntered(e -> btnCopiar.setStyle("-fx-background-color: #666666; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        btnCopiar.setOnMouseExited(e -> btnCopiar.setStyle("-fx-background-color: #2c3639; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        btnCopiar.setOnMousePressed(e -> btnCopiar.setStyle("-fx-background-color: #999999; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        Label labelPuertoInicio = new Label("Puerto inicio:");
        labelPuertoInicio.setFont(font);
        labelPuertoInicio.setTextFill(Color.WHITE);
        Label labelPuertoFinal = new Label("Puerto final:");
        labelPuertoFinal.setFont(font);
        labelPuertoFinal.setTextFill(Color.WHITE);
        Button btnScan = new Button("Scan");
        btnScan.setStyle("-fx-background-color: #4da6ff; -fx-text-fill: white; -fx-font-family: Roboto; -fx-font-size: 14px;");
        btnScan.setOnMouseEntered(e -> btnScan.setStyle("-fx-background-color: #80f3ff; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        btnScan.setOnMouseExited(e -> btnScan.setStyle("-fx-background-color: #4da6ff; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        btnScan.setOnMousePressed(e -> btnScan.setStyle("-fx-background-color: #80f3ff; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        Label labelnombrearch = new Label("Nombre del archivo");
        labelnombrearch.setFont(font);
        labelnombrearch.setTextFill(Color.WHITE);

        TextField textFieldPuertoInicio = new TextField();
        textFieldPuertoInicio.setPrefWidth(60);
        textFieldPuertoInicio.setText("1");
        textFieldPuertoInicio.setFont(font);
        textFieldPuertoInicio.setStyle("-fx-control-inner-background: #2c3639;-fx-text-fill: white;");
        TextField textFieldPuertoFinal = new TextField();
        textFieldPuertoFinal.setText("1000");
        textFieldPuertoFinal.setPrefWidth(60);
        textFieldPuertoFinal.setFont(font);
        textFieldPuertoFinal.setStyle("-fx-control-inner-background: #2c3639;-fx-text-fill: white;");
        TextField textFieldExportar = new TextField();
        textFieldExportar.setText("Ejemplo.txt");
        textFieldExportar.setPrefWidth(150);
        textFieldExportar.setFont(font);
        textFieldExportar.setStyle("-fx-control-inner-background: #2c3639;-fx-text-fill: white;");

        //Caja de resultados
        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(150);
        resultado.setPrefWidth(300);
        resultado.setFont(font);
        resultado.setStyle("-fx-control-inner-background: #2c3639; -fx-text-fill: white;");

        //Acomodo en ventana
        String[] opciones = {"TCP", "UDP", "TCP/UDP","TCP/extra","UDP/extra"};
        ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(opciones));
        choiceBox.setStyle("-fx-color: black; -fx-border-radius: 4;-fx-border-color: white; -fx-font-family: Roboto;  -fx-background-color: #2c3639; -fx-control-inner-background: #2c3639; -fx-font-size: 14px;-fx-text-fill: white;");
        choiceBox.setValue("TCP");
        choiceBox.setPrefWidth(83);

        HBox scan = new HBox(imageView);
        HBox hboxIP= new HBox(label, textField);
        HBox hboxExport = new HBox(labelnombrearch,textFieldExportar);
        hboxExport.setSpacing(8);
        hboxIP.setSpacing(8);
        HBox hbox0 = new HBox(labelPuertoInicio, textFieldPuertoInicio, labelPuertoFinal, textFieldPuertoFinal);
        hbox0.setSpacing(10);
        HBox hbox = new HBox(choiceBox, btnScan);
        hbox.setSpacing(10);
        Region espacio = new Region();
        Region espacio2 = new Region();
        espacio2.setPrefHeight(10);
        espacio.setPrefHeight(15);
        VBox vbox2 = new VBox(espacio);
        VBox vbox3 = new VBox(labelResultados, resultado);
        HBox hbox2 = new HBox(btnLimpiar, btnCopiar, cancelar,exportar);
        hbox2.setSpacing(10);

        VBox vboxT = new VBox(scan,espacio, hboxIP, hbox0, hbox, vbox2, vbox3, hbox2,espacio2,hboxExport);
        vboxT.setSpacing(10);
        vboxT.setPadding(new Insets(20)); //Crear margenes

        ArrayList<HilosTCP> apuntaTCP= new ArrayList<>();

        //Al presionar boton...
        ArrayList<Task> tasks = new ArrayList<>();
        AtomicReference<HashMap<Integer, String>> mapaTCP = new AtomicReference<>(new HashMap<>());
        AtomicReference<HashMap<Integer, String>> mapaUDP = new AtomicReference<>(new HashMap<>());
        ArrayList<ArrayList<Integer>> puertos = new ArrayList<>();

        btnScan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        if (choiceBox.getValue().equals("TCP")) {
                            resultado.appendText("[-] El escaneo TCP está comenzando ...\n");
                            int puertoInicio = Integer.parseInt(textFieldPuertoInicio.getText());
                            int puertoFinal = Integer.parseInt(textFieldPuertoFinal.getText());
                            String DirecionIP = textField.getText();
                            HilosTCP TCP = new HilosTCP(DirecionIP, puertoInicio, puertoFinal);
                            apuntaTCP.add(TCP);
                            ArrayList<Integer> puertosabiertos = TCP.scan();
                            puertos.add(puertosabiertos);
                            Platform.runLater(() -> {
                                resultado.appendText("[-] El escaneo TCP ha finalizado los resultados son ...\n");
                                for (int puerto : puertosabiertos) {
                                    resultado.appendText("El puerto:  " + puerto + "/TCP  está abierto en " + DirecionIP + "\n");
                                }
                            });
                        } else if (choiceBox.getValue().equals("UDP")) {
                            resultado.appendText("[-] El escaneo UDP está comenzando ...\n");
                            resultado.appendText("[i] Escanear UDP es más tardado por su funcionamiento\n");
                            int puertoInicio = Integer.parseInt(textFieldPuertoInicio.getText());
                            int puertoFinal = Integer.parseInt(textFieldPuertoFinal.getText());
                            String DirecionIP = textField.getText();
                            HilosUDP UDP = new HilosUDP(DirecionIP, puertoInicio, puertoFinal);
                            ArrayList<Integer> puertosUDP = UDP.escanea();
                            puertos.add(puertosUDP);
                            Platform.runLater(() -> {
                                resultado.appendText("[-] El escaneo UDP ha finalizado los resultados son ...\n");
                                for (int puerto : puertosUDP) {
                                    resultado.appendText("El puerto:  " + puerto + "/UDP  está abierto en "+DirecionIP+ "\n");
                                }
                            });
                        } else if (choiceBox.getValue().equals("TCP/UDP")) {
                            resultado.appendText("[-] El escaneo TCP está comenzando ...\n");
                            int puertoInicio = Integer.parseInt(textFieldPuertoInicio.getText());
                            int puertoFinal = Integer.parseInt(textFieldPuertoFinal.getText());
                            String DirecionIP = textField.getText();
                            HilosTCP TCP = new HilosTCP(DirecionIP, puertoInicio, puertoFinal);
                            ArrayList<Integer> puertosabiertos = TCP.scan();
                            puertos.add(puertosabiertos);
                            Platform.runLater(() -> {
                                resultado.appendText("[-] El escaneo TCP ha finalizado los resultados son ...\n");
                                for (int puerto : puertosabiertos) {
                                    resultado.appendText("El puerto:  " + puerto + "/TCP  está abierto en " + DirecionIP + "\n");
                                }
                            });
                            resultado.appendText("[-] El escaneo UDP está comenzando ...\n");
                            resultado.appendText("[i] Escanear UDP es más tardado por su funcionamiento\n");
                            HilosUDP UDP = new HilosUDP(DirecionIP, puertoInicio, puertoFinal);
                            ArrayList<Integer> puertosUDP = UDP.escanea();
                            puertos.add(puertosUDP);
                            Platform.runLater(() -> {
                                resultado.appendText("[-] El escaneo UDP ha finalizado los resultados son ...\n");
                                for (int puerto : puertosUDP) {
                                    resultado.appendText("El puerto:  " + puerto + "/UDP  está abierto en "+DirecionIP+ "\n");
                                }
                            });
                        } else if (choiceBox.getValue().equals("TCP/extra")){
                            resultado.appendText("[-] El escaneo TCP con servicios está comenzando ...\n");
                            int puertoInicio = Integer.parseInt(textFieldPuertoInicio.getText());
                            int puertoFinal = Integer.parseInt(textFieldPuertoFinal.getText());
                            String DirecionIP = textField.getText();
                            HilosTCP TCP = new HilosTCP(DirecionIP, puertoInicio, puertoFinal);
                            ArrayList<Integer> puertosabiertos = TCP.scan();
                            Platform.runLater(() -> {
                                try {
                                    IanaDatabase baseTCP = new IanaDatabase();
                                    mapaTCP.set(baseTCP.leeTCP(puertosabiertos));
                                    resultado.appendText("[-] El escaneo TCP ha finalizado los resultados son ...\n");
                                    for (int port : puertosabiertos){
                                        resultado.appendText("El puerto "+port+"/TCP "+ mapaTCP.get().get(port)+" está abierto en "+ DirecionIP+"\n");
                                    }
                                } catch (CsvValidationException | IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }
                        else if (choiceBox.getValue().equals("UDP/extra")){
                            int puertoInicio = Integer.parseInt(textFieldPuertoInicio.getText());
                            int puertoFinal = Integer.parseInt(textFieldPuertoFinal.getText());
                            resultado.appendText("[-] El escaneo UDP con servicios está comenzando ...\n");
                            resultado.appendText("[i] Escanear UDP es más tardado por su funcionamiento\n");
                            String DirecionIP = textField.getText();
                            HilosUDP UDP = new HilosUDP(DirecionIP, puertoInicio, puertoFinal);
                            ArrayList<Integer> puertosabiertos = UDP.escanea();
                            Platform.runLater(() -> {
                                try {
                                    IanaDatabase baseUDP = new IanaDatabase();
                                    mapaUDP.set(baseUDP.leeUDP(puertosabiertos));
                                    resultado.appendText("[-] El escaneo UDP ha finalizado los resultados son ...\n");
                                    for (int port : puertosabiertos){
                                        resultado.appendText("El puerto "+port+"/UDP "+mapaTCP.get()+" está abierto en "+ DirecionIP+"\n");
                                    }
                                } catch (CsvValidationException | IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }
                        return null;
                    }
                };
                new Thread(task).start();
                tasks.add(task);
                // Agregar botón de cancelar
            }
        });

        btnLimpiar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Limpia el contenido del TextArea
                resultado.clear();
                mapaUDP.get().clear();
                mapaTCP.get().clear();
                puertos.clear();
            }
        });

        exportar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!mapaUDP.get().isEmpty() || !mapaTCP.get().isEmpty()) {
                    if (!mapaUDP.get().isEmpty()) {
                        try {
                            Export.exportaJSONDescripcion(mapaUDP.get(),textFieldExportar.getText(),textField.getText());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }if (!mapaTCP.get().isEmpty()) {
                        try {
                            Export.exportaJSONDescripcion(mapaTCP.get(),textFieldExportar.getText(),textField.getText());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }else{
                    for(ArrayList<Integer> arr : puertos){
                        try {
                            Export.exportaJSONSolo(arr,textFieldExportar.getText(),textField.getText());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

            }
        });

        btnCopiar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String contenido = resultado.getText();
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(contenido);
                clipboard.setContent(content);
            }
        });

        cancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(int i=0;i<tasks.size();i++){
                    tasks.remove(i).cancel();
                    System.out.println(tasks.isEmpty());
                }
            }
        });

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #2c3639;");
        root.getChildren().addAll( background, background2, vboxT);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}