package input_shit;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class InputController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addCarBtn;

    @FXML
    private Button deleteCarBtn;

    @FXML
    private TableView<CarInput> carsTable;

    @FXML
    private TableColumn<CarInput, Integer> sColumn;

    @FXML
    private TableColumn<CarInput, Integer>  eColumn;

    private ObservableList<CarInput> observableList;

    @FXML
    void initialize() {
        observableList = FXCollections.observableArrayList();
        Callback<TableColumn<CarInput, Integer>, TableCell<CarInput, Integer>> cellFactory = p -> new EditingCell();
        sColumn.setCellValueFactory(cellData -> cellData.getValue().startProperty().asObject());
        sColumn.setCellFactory(cellFactory);
        sColumn.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setStart(t.getNewValue()));
        eColumn.setCellValueFactory(cellData -> cellData.getValue().finishProperty().asObject());
        eColumn.setCellFactory(cellFactory);
        eColumn.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setFinish(t.getNewValue()));
        addCarBtn.setOnAction(event -> {
            observableList.add(new CarInput(0, 0));
            carsTable.setItems(observableList);
        });
        deleteCarBtn.setOnAction(event -> carsTable.getItems().removeAll(carsTable.getSelectionModel().getSelectedItems()));
    }

    class CarInput{
        private IntegerProperty start, finish;

        public CarInput(int start, int finish) {
            this.start = new SimpleIntegerProperty(start);
            this.finish = new SimpleIntegerProperty(finish);
        }

        public IntegerProperty startProperty() {
            return start;
        }

        public void setStart(int start) {
            this.start.set(start);
        }

        public IntegerProperty finishProperty() {
            return finish;
        }

        public void setFinish(int finish) {
            this.finish.set(finish);
        }
    }

    class EditingCell extends TableCell<CarInput, Integer> {

        private TextField textField;

        public EditingCell() {}

        @Override
        public void startEdit() {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }

            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(String.valueOf(getItem()));
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        @Override
        public void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(Integer.parseInt(textField.getText()));
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
}
