module com.memorycards {
    requires javafx.controls;
    requires javafx.fxml;
    requires AnimateFX;

    opens com.memorycards to javafx.fxml;
    exports com.memorycards;
}