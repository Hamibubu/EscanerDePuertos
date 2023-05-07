import com.opencsv.exceptions.CsvValidationException;
import iteso.scanner.*;
// Importamos nuestra librería de de escáneres de iteso
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
// Agregamos las demás librerías necesarias
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class ScannerPuertosPOO extends Application {

    public static void main(String[] args) {
        // Encapsulamos todo en en launch
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Creacion de ventana
        Image icono = new Image("radar.png");
        // Agregamos el ícono de radar
        primaryStage.getIcons().add(icono);
        primaryStage.setTitle("JAVASCAN");
        // Nombre del programa
        primaryStage.setWidth(430);
        primaryStage.setHeight(750);
        // Tamaños
        primaryStage.setResizable(false);

        //Rectangulo titulo
        Rectangle backgroundTitulo = new Rectangle(440, 90);
        backgroundTitulo.setFill(Color.web("#1f2628"));
        backgroundTitulo.setTranslateY(-288);

        //Rectangulo arriba detras de los objetos
        Rectangle background = new Rectangle(390, 130);
        background.setFill(Color.web("#1f2628"));
        background.setArcWidth(20); // Redondear los bordes horizontalmente
        background.setArcHeight(20); // Redondear los bordes verticalmente
        background.setTranslateY(-137);

        //Rectangulo abajo detras de los objetos
        Rectangle background2 = new Rectangle(390, 315);
        background2.setFill(Color.web("#1f2628"));
        background2.setArcWidth(20); // Redondear los bordes horizontalmente
        background2.setArcHeight(20); // Redondear los bordes verticalmente
        background2.setTranslateY(95);

        // Separadores para acomodar los botones
        Separator separator = new Separator();
        Separator separator2 = new Separator();

        //Objetos en ventana
        //imagen javascan
        Image image = new Image("LOGO.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setFitHeight(70);
        imageView.setTranslateX(32);

        //Fuente
        Font font = new Font("Roboto", 14);

        //Label IP
        Label label = new Label("IPv4 Target:   ");
        label.setFont(font);
        label.setTextFill(Color.WHITE);

        //Label resultados
        Label labelResultados = new Label("Resultados:");
        labelResultados.setFont(font);
        labelResultados.setTextFill(Color.WHITE);

        //textfield IP
        TextField textField = new TextField();                   //Caja input de IP
        textField.setText("127.0.0.1");
        textField.setPrefWidth(215);
        textField.setFont(font);
        textField.setStyle("-fx-control-inner-background: #2c3639;-fx-text-fill: white;");

        //boton exportar
        Button exportar = new Button("Exportar");
        exportar.setStyle("-fx-background-color: #4da6ff; -fx-text-fill: white; -fx-font-family: Roboto; -fx-font-size: 14px;");
        exportar.setOnMouseEntered(e -> exportar.setStyle("-fx-background-color: #80f3ff; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        exportar.setOnMouseExited(e -> exportar.setStyle("-fx-background-color: #4da6ff; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        exportar.setOnMousePressed(e -> exportar.setStyle("-fx-background-color: #80f3ff; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        exportar.setVisible(false);

        //boton cancelar
        Button cancelar = new Button("Cancelar");
        cancelar.setStyle("-fx-background-color: #2c3639; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;");
        cancelar.setOnMouseEntered(e -> cancelar.setStyle("-fx-background-color: #666666; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        cancelar.setOnMouseExited(e -> cancelar.setStyle("-fx-background-color: #2c3639; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        cancelar.setOnMousePressed(e -> cancelar.setStyle("-fx-background-color: #999999; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        cancelar.setVisible(false);

        //boton limpiar
        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setStyle("-fx-background-color: #2c3639; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;");
        btnLimpiar.setOnMouseEntered(e -> btnLimpiar.setStyle("-fx-background-color: #666666; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        btnLimpiar.setOnMouseExited(e -> btnLimpiar.setStyle("-fx-background-color: #2c3639; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        btnLimpiar.setOnMousePressed(e -> btnLimpiar.setStyle("-fx-background-color: #999999; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        btnLimpiar.setVisible(false);

        //boton copiar
        Button btnCopiar = new Button("Copiar");
        btnCopiar.setStyle("-fx-background-color: #2c3639; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;");
        btnCopiar.setOnMouseEntered(e -> btnCopiar.setStyle("-fx-background-color: #666666; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        btnCopiar.setOnMouseExited(e -> btnCopiar.setStyle("-fx-background-color: #2c3639; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        btnCopiar.setOnMousePressed(e -> btnCopiar.setStyle("-fx-background-color: #999999; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        btnCopiar.setVisible(false);

        //Label puerto inicio
        Label labelPuertoInicio = new Label("Puerto inicio:");
        labelPuertoInicio.setFont(font);
        labelPuertoInicio.setTextFill(Color.WHITE);

        //Label puerto final
        Label labelPuertoFinal = new Label("Puerto final:");
        labelPuertoFinal.setFont(font);
        labelPuertoFinal.setTextFill(Color.WHITE);

        //boton escanear
        Button btnScan = new Button("Scan");
        btnScan.setStyle("-fx-background-color: #28982c; -fx-text-fill: white; -fx-font-family: Roboto; -fx-font-size: 14px;");
        btnScan.setOnMouseEntered(e -> btnScan.setStyle("-fx-background-color: #1cda34; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        btnScan.setOnMouseExited(e -> btnScan.setStyle("-fx-background-color: #28982c; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));
        btnScan.setOnMousePressed(e -> btnScan.setStyle("-fx-background-color: #1cda34; -fx-text-fill: white;-fx-font-family: Roboto; -fx-font-size: 14px;"));

        //Label nombre archivo
        Label labelnombrearch = new Label("Nombre del archivo:");
        labelnombrearch.setFont(font);
        labelnombrearch.setTextFill(Color.WHITE);
        labelnombrearch.setVisible(false);

        //textfield Exportar
        TextField textFieldExportar = new TextField();
        textFieldExportar.setText("Ejemplo.txt");
        textFieldExportar.setPrefWidth(150);
        textFieldExportar.setFont(font);
        textFieldExportar.setStyle("-fx-control-inner-background: #2c3639;-fx-text-fill: white;");
        textFieldExportar.setVisible(false);

        //textfield puerto inicio
        TextField textFieldPuertoInicio = new TextField();
        textFieldPuertoInicio.setPrefWidth(60);
        textFieldPuertoInicio.setText("1");
        textFieldPuertoInicio.setFont(font);
        textFieldPuertoInicio.setStyle("-fx-control-inner-background: #2c3639;-fx-text-fill: white;");

        //textfield puerto final
        TextField textFieldPuertoFinal = new TextField();
        textFieldPuertoFinal.setText("1000");
        textFieldPuertoFinal.setPrefWidth(60);
        textFieldPuertoFinal.setFont(font);
        textFieldPuertoFinal.setStyle("-fx-control-inner-background: #2c3639;-fx-text-fill: white;");

        //Caja de resultados
        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(225);
        resultado.setPrefWidth(300);
        resultado.setFont(font);
        resultado.setStyle("-fx-control-inner-background: #2c3639; -fx-text-fill: white;");
        String css = this.getClass().getResource("estilos.css").toExternalForm();
        resultado.getStylesheets().add(css);


        //ChoiceBox Protocolos
        String[] opciones = {"TCP", "UDP", "TCP/UDP","TCP/extra","UDP/extra"};
        ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(opciones));
        choiceBox.setStyle("-fx-color: black; -fx-border-radius: 4;-fx-border-color: white; -fx-font-family: Roboto;  -fx-background-color: #2c3639; -fx-control-inner-background: #2c3639; -fx-font-size: 14px;-fx-text-fill: white;");
        choiceBox.setValue("TCP");
        choiceBox.setPrefWidth(83);



        //Acomodo en ventana
        HBox hboxIP= new HBox(label, textField);
        hboxIP.setSpacing(8);
        HBox hboxExport = new HBox(labelnombrearch,textFieldExportar);
        hboxExport.setSpacing(8);
        HBox hbox0 = new HBox(labelPuertoInicio, textFieldPuertoInicio, labelPuertoFinal, textFieldPuertoFinal);
        hbox0.setSpacing(10);
        HBox hboxT = new HBox(choiceBox, btnScan, cancelar);
        hboxT.setSpacing(10);
        Region espacio = new Region();
        espacio.setPrefHeight(30);
        Region espacio2 = new Region();
        espacio2.setPrefHeight(10);
        VBox vbox2 = new VBox(espacio);
        Region espacio3 = new Region();
        espacio3.setPrefHeight(0);
        VBox vbox3 = new VBox(labelResultados, resultado);
        HBox hbox2 = new HBox(exportar, btnCopiar, btnLimpiar);
        hbox2.setSpacing(10);

        //Box completa
        StackPane.setAlignment(imageView, javafx.geometry.Pos.CENTER);
        VBox vboxT = new VBox(separator2, imageView, separator, espacio, hboxIP, hbox0, hboxT, vbox2, espacio3, vbox3, hbox2, espacio2, hboxExport);
        vboxT.setSpacing(10);
        vboxT.setPadding(new Insets(20)); //Crear margenes

        //Hilos
        ArrayList<HilosTCP> apuntaTCP= new ArrayList<>();
        AtomicReference<HashMap<Integer, String>> mapaTCP = new AtomicReference<>(new HashMap<>());
        AtomicReference<HashMap<Integer, String>> mapaUDP = new AtomicReference<>(new HashMap<>());
        ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<ArrayList<Integer>> puertos = new ArrayList<>();

        //Al presionar boton escanear con el tipo seleccionado...
        btnScan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Encapsulamos todo en tasks para poderlo cancelar
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        btnCopiar.setVisible(true);
                        btnLimpiar.setVisible(true);
                        exportar.setVisible(true);
                        labelnombrearch.setVisible(true);
                        textFieldExportar.setVisible(true);
                        // Si se escoge TCP
                        if (choiceBox.getValue().equals("TCP")) {
                            cancelar.setVisible(true);
                            resultado.appendText("\n[-] El escaneo TCP está comenzando ...\n");
                            int puertoInicio = Integer.parseInt(textFieldPuertoInicio.getText());
                            int puertoFinal = Integer.parseInt(textFieldPuertoFinal.getText());
                            String DirecionIP = textField.getText();
                            HilosTCP TCP = new HilosTCP(DirecionIP, puertoInicio, puertoFinal);
                            apuntaTCP.add(TCP);
                            ArrayList<Integer> puertosabiertos = TCP.scan();
                            puertos.add(puertosabiertos);
                            Platform.runLater(() -> {
                                resultado.appendText("[-] El escaneo TCP ha finalizado los resultados son: \n");
                                for (int puerto : puertosabiertos) {
                                    resultado.appendText("    El puerto:  " + puerto + "/TCP  está abierto en " + DirecionIP + "\n");
                                }
                                cancelar.setVisible(false);
                            });
                            // Si se escoge UDP
                        } else if (choiceBox.getValue().equals("UDP")) {
                            cancelar.setVisible(true);
                            resultado.appendText("\n[-] El escaneo UDP está comenzando ...\n");
                            resultado.appendText("[i] Escanear UDP es más tardado por su funcionamiento\n");
                            int puertoInicio = Integer.parseInt(textFieldPuertoInicio.getText());
                            int puertoFinal = Integer.parseInt(textFieldPuertoFinal.getText());
                            String DirecionIP = textField.getText();
                            HilosUDP UDP = new HilosUDP(DirecionIP, puertoInicio, puertoFinal);
                            ArrayList<Integer> puertosUDP = UDP.escanea();
                            puertos.add(puertosUDP);
                            Platform.runLater(() -> {
                                resultado.appendText("[-] El escaneo UDP ha finalizado los resultados son: \n");
                                for (int puerto : puertosUDP) {
                                    resultado.appendText("    El puerto:  " + puerto + "/UDP  está abierto en "+DirecionIP+ "\n");
                                }
                                cancelar.setVisible(false);
                            });
                            // Si se quiere ambos
                        } else if (choiceBox.getValue().equals("TCP/UDP")) {
                            cancelar.setVisible(true);
                            resultado.appendText("\n[-] El escaneo TCP está comenzando ...\n");
                            int puertoInicio = Integer.parseInt(textFieldPuertoInicio.getText());
                            int puertoFinal = Integer.parseInt(textFieldPuertoFinal.getText());
                            String DirecionIP = textField.getText();
                            HilosTCP TCP = new HilosTCP(DirecionIP, puertoInicio, puertoFinal);
                            ArrayList<Integer> puertosabiertos = TCP.scan();
                            puertos.add(puertosabiertos);
                            Platform.runLater(() -> {
                                resultado.appendText("[-] El escaneo TCP ha finalizado los resultados son: \n");
                                for (int puerto : puertosabiertos) {
                                    resultado.appendText("    El puerto:  " + puerto + "/TCP  está abierto en " + DirecionIP + "\n");
                                }
                            });
                            resultado.appendText("[-] El escaneo UDP está comenzando ...\n");
                            resultado.appendText("[i] Escanear UDP es más tardado por su funcionamiento\n");
                            HilosUDP UDP = new HilosUDP(DirecionIP, puertoInicio, puertoFinal);
                            ArrayList<Integer> puertosUDP = UDP.escanea();
                            puertos.add(puertosUDP);
                            Platform.runLater(() -> {
                                resultado.appendText("[-] El escaneo UDP ha finalizado los resultados son: \n");
                                for (int puerto : puertosUDP) {
                                    resultado.appendText("    El puerto:  " + puerto + "/UDP  está abierto en "+DirecionIP+ "\n");
                                }
                                cancelar.setVisible(false);
                            });
                            // TCP con interpretación de servicios
                        } else if (choiceBox.getValue().equals("TCP/extra")){
                            cancelar.setVisible(true);
                            resultado.appendText("\n[-] El escaneo TCP con servicios está comenzando ...\n");
                            int puertoInicio = Integer.parseInt(textFieldPuertoInicio.getText());
                            int puertoFinal = Integer.parseInt(textFieldPuertoFinal.getText());
                            String DirecionIP = textField.getText();
                            HilosTCP TCP = new HilosTCP(DirecionIP, puertoInicio, puertoFinal);
                            ArrayList<Integer> puertosabiertos = TCP.scan();
                            Platform.runLater(() -> {
                                try {
                                    IanaDatabase baseTCP = new IanaDatabase();
                                    mapaTCP.set(baseTCP.leeTCP(puertosabiertos));
                                    resultado.appendText("\n[-] El escaneo TCP ha finalizado los resultados son: \n");
                                    for (int port : puertosabiertos){
                                        resultado.appendText("    El puerto "+port+"/TCP "+ mapaTCP.get().get(port)+" está abierto en "+ DirecionIP+"\n");
                                    }
                                } catch (CsvValidationException | IOException e) {
                                    throw new RuntimeException(e);
                                }
                                cancelar.setVisible(false);
                            });
                        }
                        // UDP con interpretación de servicios
                        else if (choiceBox.getValue().equals("UDP/extra")){
                            cancelar.setVisible(true);
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
                                    resultado.appendText("[-] El escaneo UDP ha finalizado los resultados son: \n");
                                    for (int port : puertosabiertos){
                                        resultado.appendText("    El puerto "+port+"/UDP "+mapaTCP.get()+" está abierto en "+ DirecionIP+"\n");
                                    }
                                } catch (CsvValidationException | IOException e) {
                                    throw new RuntimeException(e);
                                }
                                cancelar.setVisible(false);
                            });
                        }
                        return null;
                    }
                };
                // Corremos el task en otro hilo separado a la interfaz
                new Thread(task).start();
                tasks.add(task);
                // Agregar botón de cancelar
            }
        });
        // Acciones para limpiar
        btnLimpiar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Limpia el contenido del TextArea
                resultado.clear();
                // Limpiamos los contenedores
                mapaUDP.get().clear();
                mapaTCP.get().clear();
                puertos.clear();
            }
        });
        // Definimos lo que pasa al exportar
        exportar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Si los mapas no están vacíos
                if(!mapaUDP.get().isEmpty() || !mapaTCP.get().isEmpty()) {
                    // UDP no vacío
                    if (!mapaUDP.get().isEmpty()) {
                        try {
                            Export.exportaJSONDescripcion(mapaUDP.get(),textFieldExportar.getText(),textField.getText());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        // TCP no vacío
                    }if (!mapaTCP.get().isEmpty()) {
                        try {
                            Export.exportaJSONDescripcion(mapaTCP.get(),textFieldExportar.getText(),textField.getText());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    // Exportamos escaneos simples, si la lista no está vacía, es if sin else if
                    // por si el usuario lleno ambas cosas
                }if(!puertos.isEmpty()){
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
        // Al copiar
        btnCopiar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Obtenemos el texto y lo pasamos al clipboard
                String contenido = resultado.getText();
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(contenido);
                clipboard.setContent(content);
            }
        });
        // Al cancelar
        cancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Quitamos la visibilidad mientras no hay escaneos en curso
                cancelar.setVisible(false);
                // Finalizamos los tasks de la lista de tasks
                for(int i=0;i<tasks.size();i++){
                    tasks.remove(i).cancel();
                    System.out.println(tasks.isEmpty());
                }
                resultado.appendText("\n[-] Escaneo detenido.\n");
            }
        });

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #2c3639;");

        root.getChildren().addAll(backgroundTitulo, background, background2, vboxT);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
