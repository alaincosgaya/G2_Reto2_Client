/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;


 /** Clase para poder utilizar un DatePicker dentro de una tabla.
 *
 * @author Alain Cosgaya
 */
public class DateEditingCell extends TableCell<ContratoEntity, Date> {

    private DatePicker datePicker;

    public DateEditingCell() {

    }

    /**
     * Control para cuando se inicia la edicion.
     */

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createDatePicker();
            setText(null);
            setGraphic(datePicker);
        }
    }

    /**
     * Control para cuando se cancela la edicion.
     */

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getDate().toString());
        setGraphic(null);
    }

    /**
     * Control para la actualizacion de los elementos de la tabla.
     *
     * @param item Elemento de tipo Date en la tabla.
     * @param empty Booleano para controlar si tiene contenido.
     */
    @Override
    public void updateItem(Date item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(getDate());
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                setText(dateFormat.format(item));
                setGraphic(null);
            }
        }
    }


    /**
     * Metodo para la creacion de un DatePicker.
     */
    private void createDatePicker() {
        datePicker = new DatePicker(getDate());
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        datePicker.setOnAction((e) -> {
            
            commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.of("GMT")).toInstant()));

        });
    }

    /**
     * Metodo para le recuperacion de fecha.
     * @return Devuelve la fecha actual. 
     */

    private LocalDate getDate() {
        return LocalDate.now();
    }

}

