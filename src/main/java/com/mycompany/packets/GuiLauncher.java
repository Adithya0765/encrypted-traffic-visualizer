package com.mycompany.packets;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GuiLauncher extends Application {

    private final ObservableList<Flow> flowData = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Encrypted Traffic Classifier - SOC Dashboard");

        TableView<Flow> table = new TableView<>(flowData);

        addColumn(table, "Destination", "dstIP", 150);
        addColumn(table, "Port", "dstPort", 60);
        addColumn(table, "ASN", "asn", 160);
        addColumn(table, "Org", "org", 220);
        addColumn(table, "Country", "country", 100);
        addColumn(table, "Category", "category", 200);
        addColumn(table, "Packets", "packetCount", 80);
        addColumn(table, "Bytes", "totalBytes", 80);

        table.setRowFactory(tv -> new TableRow<Flow>() {
            @Override
            protected void updateItem(Flow f, boolean empty) {
                super.updateItem(f, empty);

                if (empty || f == null) {
                    setStyle("");
                    return;
                }

                switch (f.getCategory()) {
                    case "VPN Tunnel":
                        setStyle("-fx-background-color: #5c1f1f; -fx-text-fill: white;");
                        break;
                    case "Browser Traffic":
                        setStyle("-fx-background-color: #1f3d5c; -fx-text-fill: white;");
                        break;
                    case "DoH Candidate":
                        setStyle("-fx-background-color: #5c4a1f; -fx-text-fill: white;");
                        break;
                    case "Enterprise (Teams/Office)":
                        setStyle("-fx-background-color: #1f5c2a; -fx-text-fill: white;");
                        break;
                    case "Cloud Service":
                        setStyle("-fx-background-color: #4b1f5c; -fx-text-fill: white;");
                        break;
                    case "Local":
                        setStyle("-fx-background-color: #333333; -fx-text-fill: #d0d0d0;");
                        break;
                    default:
                        setStyle("-fx-background-color: #1d1d1d; -fx-text-fill: #cfcfcf;");
                }
            }
        });

        BorderPane root = new BorderPane(table);
        Scene scene = new Scene(root, 1200, 650);
        scene.getStylesheets().add(getClass().getResource("/dark-theme.css").toExternalForm());

        stage.setScene(scene);
        stage.show();

        Thread snifferThread = new Thread(() -> {
            FlowSniffer sniffer = new FlowSniffer();
            sniffer.startCapturing(flows -> {
                Platform.runLater(() -> {
                    flowData.setAll(flows.values());
                });
            });
        });
        snifferThread.setDaemon(true);
        snifferThread.start();
    }

    private <T> void addColumn(TableView<Flow> table, String title, String property, int width) {
        TableColumn<Flow, T> col = new TableColumn<>(title);
        col.setCellValueFactory(new PropertyValueFactory<>(property));
        col.setPrefWidth(width);
        table.getColumns().add(col);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
