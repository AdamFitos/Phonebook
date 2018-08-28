package phonebook;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import static javafx.print.PrintColor.COLOR;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;

public class FXMLDocumentController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="variable">
    private final String MENU_CONTACTS = "Contact";
    private final String MENU_LIST = "List";
    private final String MENU_EXPORT = "Exportation";
    private final String MENU_ESCAPE = "Escape";
    private final String MENU_SEARCH = "Searching";

    DB db = new DB();
    DropShadow ds = new DropShadow();
    private String[] enablevalues = {"somthing", "anything"};
    private String searchedName = "";
    TableColumn lastNamecol = new TableColumn("Lastname");
    TableColumn firstNamecol = new TableColumn("Firstname");
    TableColumn emailCol = new TableColumn("E-mail");
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="fxml-ek">
    @FXML
    private Label label;
    @FXML
    private StackPane menuPane;
    @FXML
    private Pane contactPane;
    @FXML
    private Pane searchingPane;
    @FXML
    private Pane exportPane;
    @FXML
    private TableView table;
    @FXML
    private TextField inPutLastName;
    @FXML
    private TextField inputFirstName;
    @FXML
    private TextField inPutEmail;
    @FXML
    private Button newContentButtonEmail;
    @FXML
    private Button savePDF;
    @FXML
    private TextField inputExport;

    @FXML
    private TextField searchinput;

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private SplitPane mainSplit;

    @FXML
    private ChoiceBox choice;
    @FXML
    private TableView tableResult;
    @FXML
    private ImageView magnifyingGlass;

    @FXML
    private Pane startPane;

    //</editor-fold>
    public final ObservableList<Person> data = FXCollections.observableArrayList();
//            new Person("Fitos", "Ádám", "fitos.adam94@gmail.com"),
//            new Person("Nagy", "Gabriella", "nagy.gabi@safemail.com"),
//            new Person("Kis", "Gábor", "kis.gabor@yahoo.com"));

    public final ObservableList<Person> data2 = FXCollections.observableArrayList();

    @FXML
    private void addContact(ActionEvent event) {
        ArrayList<Person> result = new ArrayList<Person>();
        result = db.showAllContacts();
        String email = inPutEmail.getText();
        System.out.println(" Contact hozzáadva");
//        if(result.contains(inPutEmail)){
//        alert("The e-mail is already being");
//        }
        if (email.contains(".") && email.contains("@") && email.length() > 6 && !result.contains(email)) {
            Person newperson = new Person(inputFirstName.getText(), inPutLastName.getText(), email);
            data.add(newperson);
            db.addContacts(newperson);
            inPutLastName.clear();
            inputFirstName.clear();
            inPutEmail.clear();
        } else {
            alert("You didn't give properly format of the e-mail address.");
        }

    }

    @FXML
    private void addContactWithEnter(KeyEvent k) {
        String email = inPutEmail.getText();

        if (k.getCode() == KeyCode.ENTER) {
            if (email.contains(".") && email.contains("@") && email.length() > 6) {
                Person newperson = new Person(inputFirstName.getText(), inPutLastName.getText(), email);
                data.add(newperson);
                db.addContacts(newperson);
                System.out.println(" Contact hozzáadva");
                inPutLastName.clear();
                inputFirstName.clear();
                inPutEmail.clear();
            } else {
                alert("You didn't give properly format of the e-mail address.\n\n\n\n\n");
            }
        }
    }

    public void setTableData() {

        TableColumn lastNamecol = new TableColumn("Lastname");
        lastNamecol.setMinWidth(120);
        /**
         * beállítjuk a setCellFactory - val, hogy milyen adattípusok lesznek az
         * oszlopban de ez csak egy cellára vonatkozik ezért kell utána
         * belerakni a TextFiledTableCell.forTableColumn ot mert ez biztosítja
         * hogy az egész oszlopra vonatkozzon
         */
        lastNamecol.setCellFactory(TextFieldTableCell.forTableColumn());
        /**
         * Beállítjuk a setCellValueFactory - val hogy honnan keressen(person)
         * és milyen adattípust(String) valamint legutoljára megadjuk hogy mi a
         * neve amit keresünk.
         */
        lastNamecol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));

        TableColumn firstNamecol = new TableColumn("Firstname");
        firstNamecol.setMinWidth(120);
        firstNamecol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNamecol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

        TableColumn emailCol = new TableColumn("E-mail");
        emailCol.setMinWidth(200);
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("emailAddress"));

        TableColumn removeCol = new TableColumn("Delete");
        removeCol.setMinWidth(60);

        Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory
                = new Callback<TableColumn<Person, String>, TableCell<Person, String>>() {
            @Override
            public TableCell call(final TableColumn<Person, String> param) {

                final TableCell<Person, String> cell = new TableCell<Person, String>() {

                    final Button btn = new Button("Delete");

                    @Override
                    public void updateItem(String item, boolean empty) {

                        super.updateItem(item, empty);
                        if (empty) {

                            setGraphic(null);
                            setText(null);

                        } else {

                            btn.setOnAction((ActionEvent) -> {

                                Person person = getTableView().getItems().get(getIndex());
                                data.remove(person);
                                data2.remove(person);
                                
                                db.removeContact(person);

                            });

                            btn.setLineSpacing(50.0);
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        removeCol.setCellFactory(cellFactory);

        table.getColumns().addAll(firstNamecol, lastNamecol, emailCol, removeCol);
        data.addAll(db.showAllContacts());
       
//        table.refresh();
        table.setItems(data);

        /**
         * így oldhatjuk meg hogy a táblázatunk szerkeszthető (Tableview nél
         * állítani hogy enable=true) és ezzel elérhetjük hogy a megadott
         * adatunk ne vészen el ha elkattintunk
         *
         */
        lastNamecol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualperson = ((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow()));
                actualperson.setLastName(t.getNewValue());
                db.updateContacts(actualperson);

            }
        });

        firstNamecol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> a) {

                Person actualPerson = ((Person) a.getTableView().getItems().get(
                        a.getTablePosition().getRow()));
                actualPerson.setFirstName(a.getNewValue());
                db.updateContacts(actualPerson);

            }
        });

        emailCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> b) {

                Person actualPerson = ((Person) b.getTableView().getItems().get(b.getTablePosition().getRow()));
                actualPerson.setEmailAddress(
                        b.getNewValue());
                db.updateContacts(actualPerson);

            }

        }
        );

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setTableData();
        setMenuData();
        setSearchTable();
//        searchPossibility();
    }

    @FXML
    private void searchClick(MouseEvent e) {
        searchPossibility();
    }

    private void setMenuData() {
        /**
         * Ezzel állítottuk be a menüt, hogy egymásból lenyíló legyen(TreeItem
         * eket kell létrehozni és rárakni egy Treeviewra).
         */
        TreeItem<String> rootTreeItem = new TreeItem<>("Menu");
        TreeView<String> treeView = new TreeView<>(rootTreeItem);
        treeView.setShowRoot(false);//eltűnteti az alap kötelező gyökér elemet

        //nodeEsc
        TreeItem<String> treeItemCont = new TreeItem<>(MENU_CONTACTS);
        TreeItem<String> treeItemEsc = new TreeItem<>(MENU_ESCAPE);
        //<editor-fold defaultstate="collapsed" desc="search_change">
        TreeItem<String> treeItemSearching = new TreeItem<>(MENU_SEARCH);

//</editor-fold>
        rootTreeItem.getChildren().addAll(treeItemSearching, treeItemCont, treeItemEsc);//itt adjuk hozzá a gyökér-hez a többi Itemet

        treeItemCont.setExpanded(true);

        Node nodeList = new ImageView(new Image(getClass().getResourceAsStream("/contactImage.png")));
        Node nodeListMain = new ImageView(new Image(getClass().getResourceAsStream("/pdfgeneratoriconmini.png")));
        Node nodeExp = new ImageView(new Image(getClass().getResourceAsStream("/ExportImage.png")));
        Node nodeEsc = new ImageView(new Image(getClass().getResourceAsStream("/exit.png")));
        Node nodeSearch = new ImageView(new Image(getClass().getResourceAsStream("/magnifyingGlassSearchIcon.png")));

        //kép létrehozása és elérhetőség megadása
        treeItemSearching.setGraphic(nodeSearch);
        treeItemEsc.setGraphic(nodeEsc);
        treeItemCont.setGraphic(nodeListMain);
        TreeItem<String> treeItemList = new TreeItem<>(MENU_LIST, nodeList);
        TreeItem<String> treeItemExp = new TreeItem<>(MENU_EXPORT, nodeExp);

        treeItemCont.getChildren().addAll(treeItemList, treeItemExp);//lenyiló elemek hozzáadása
        menuPane.getChildren().add(treeView);// az egész megjelenítése a menupane-en    

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();
                System.out.println(selectedMenu);

                if (selectedMenu != null) {
                    switch (selectedMenu) {
                        case MENU_CONTACTS:
                            try {
                                selectedItem.setExpanded(true);
                            } catch (Exception e) {
                            }
                            break;

                        case MENU_LIST:

                            contactPane.setVisible(false);
                            exportPane.setVisible(true);
                            searchingPane.setVisible(false);
                            startPane.setVisible(false);

                            break;

                        case MENU_EXPORT:

                            contactPane.setVisible(true);
                            exportPane.setVisible(false);
                            searchingPane.setVisible(false);
                            startPane.setVisible(false);

                            break;

                        case MENU_SEARCH:
                            contactPane.setVisible(false);
                            exportPane.setVisible(false);
                            searchingPane.setVisible(true);
                            startPane.setVisible(false);
                            break;

                        case MENU_ESCAPE:
                            try {
                                System.exit(0);
                            } catch (Exception e) {
                            }
                            break;

                    }
                }

            }

        });
    }

    @FXML
    private void addPDF(ActionEvent event) {
        String fileName = inputExport.getText();
        fileName = fileName.replaceAll("\\s+", "");
        if (!fileName.equals("")) {
            pdfGenerator pdf = new pdfGenerator();
            pdf.pdfGenerator(fileName, "", data);
            inputExport.clear();
        } else {
            alert("You didn't give properly format of the pdf name.\n\n\n\n\n");
        }
    }

    @FXML
    private void enterPDF(KeyEvent k) {

        String fileName = inputExport.getText();
        fileName = fileName.replaceAll("\\s+", "");
        if (k.getCode() == KeyCode.ENTER) {

            if (fileName != null && !fileName.equals("")) {
                pdfGenerator pdf = new pdfGenerator();
                pdf.pdfGenerator(fileName, "", data);
                inputExport.clear();

            } else {
                alert("You didn't give properly format of the pdf name.\n\n\n\n\n");
            }
        }

    }

    public void alert(String text) {
        mainSplit.setDisable(true);
        mainSplit.setOpacity(0.4);

        Label label = new Label(text);
        label.setTextFill(Color.web("#fcfcfc"));
        label.setFont(new Font(14));

        Button alertButton = new Button("OK");
//        alertButton.setStyle(Color.web("#2e4e82"));
        alertButton.setTextFill(Color.web("#2e4e82"));
        alertButton.setStyle("-fx-base:#fcfcfc");
        VBox vbox1 = new VBox(label);
//        vbox1.setSpacing(10);
        VBox vbox2 = new VBox(alertButton);
        vbox1.setAlignment(Pos.CENTER);
        vbox2.setAlignment(Pos.CENTER);

        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent a) {

                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                vbox1.setVisible(false);
                vbox2.setVisible(false);

            }
        });
        alertButton.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent a) {
                alertButton.setEffect(ds);
            }
        });

        alertButton.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {

                    mainSplit.setDisable(false);
                    mainSplit.setOpacity(1);
                    vbox1.setVisible(false);
                    vbox2.setVisible(false);

                }

            }
        });
        AnchorPane.getChildren().addAll(vbox1, vbox2);
        AnchorPane.setTopAnchor(vbox1, 300.0);
        AnchorPane.setTopAnchor(vbox2, 350.0);
        AnchorPane.setLeftAnchor(vbox1, 200.0);
        AnchorPane.setLeftAnchor(vbox2, 325.0);
    }

    //<editor-fold defaultstate="collapsed" desc="choisebox">
    ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
            "FirstName", "LastName", "emailAdress")
    );

//</editor-fold>
    public void setSearchTable() {

        TableColumn lastNamecol = new TableColumn("Lastname");
        lastNamecol.setMinWidth(120);
        lastNamecol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNamecol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));

        TableColumn firstNamecol = new TableColumn("Firstname");
        firstNamecol.setMinWidth(120);
        firstNamecol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNamecol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

        TableColumn emailCol = new TableColumn("E-mail");
        emailCol.setMinWidth(200);
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("emailAddress"));

        tableResult.getColumns().addAll(firstNamecol, lastNamecol, emailCol);
//        data2.addAll(db.searching(searchinput.getText()));
//        tableResult.setItems(data2);

    }

    @FXML
    private void searchInputKeyev(KeyEvent k) {
        String search = searchinput.getText();
        search = search.replaceAll("\\s+", "");
        if (k.getCode() == KeyCode.ENTER) {
            if (!search.equals("")) {
                // db.searching(searchinput.getText());

                data2.addAll(db.searching(search));
                tableResult.setItems(data2);
                //setSearchData();
                if (tableResult.equals(null)) {

                    alert("I cannot found neither any compare");
                }
                System.out.println("Searching");
                searchinput.clear();
            } else {
                alert("I cannot found neither any compare");
                searchinput.clear();
            }
            ArrayList<Person> result = new ArrayList<Person>();
            result = db.searching(search);
            if (result.isEmpty() == true) {

                alert("I cannot found neither any compare");
            }
        }
    }

    @FXML
    private void magnifyingiconclick() {

        magnifyingGlass.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("Tile pressed ");
                event.consume();

            }
        });

        String search = searchinput.getText();
        search = search.replaceAll("\\s+", "");

        if (!search.equals("")) {
            // db.searching(searchinput.getText());
            
            data2.addAll(db.searching(search));
            tableResult.setItems(data2);
            //setSearchData();
            if (tableResult.equals(null)) {

                alert("I cannot found neither any compare");
            }
            System.out.println(" Searching");
            searchinput.clear();
        } else {
            alert("I cannot found neither any compare");
            searchinput.clear();
        }
        ArrayList<Person> result = new ArrayList<Person>();
        result = db.searching(search);
        if (result.isEmpty() == true) {

            alert("I cannot found neither any compare");
        }
    }

    private void searchPossibility() {

        ArrayList<Person> result = new ArrayList<Person>();
        ArrayList<String> ab = new ArrayList<>();
        if(searchinput.isEditable() == true){
        result = db.showAllContacts();
        
//          Person person = new Person();
        for (Person t : result) {
            System.out.println(t.getFirstName() + " " + t.getLastName());
            ab.add(t.getFirstName());
            ab.add(t.getLastName());
            ab.add(t.getEmailAddress());
        }

//        ab.add(person.getFirstName());
//        ab.add(person.getLastName());
//        ab.add(person.getEmailAddress());
//        
//        ab.forEach((op) -> {System.out.println(op);
//        TextFields.bindAutoCompletion(searchinput,op );
//                }
//        
//        
//        );
        

    }
        TextFields.bindAutoCompletion(searchinput, ab);
    }
}
//class Tasker extends Thread{
//@Override
//public void run() {
//  
//ArrayList<Person> result = new ArrayList<Person>();
//        ArrayList<String> ab = new ArrayList<>();
//        if(searchinput.isEditable() == true){
//        result = db.showAllContacts();
//        }
////          Person person = new Person();
//        for (Person t : result) {
//            System.out.println(t.getFirstName() + " " + t.getLastName());
//            ab.add(t.getFirstName());
//            ab.add(t.getLastName());
//            ab.add(t.getEmailAddress());
//        }
//
////        ab.add(person.getFirstName());
////        ab.add(person.getLastName());
////        ab.add(person.getEmailAddress());
////        
////        ab.forEach((op) -> {System.out.println(op);
////        TextFields.bindAutoCompletion(searchinput,op );
////                }
////        
////        
////        );
//        TextFields.bindAutoCompletion(searchinput, ab);
//
//}
//
//}