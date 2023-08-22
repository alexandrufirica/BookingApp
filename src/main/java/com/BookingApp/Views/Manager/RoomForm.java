package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Entity.Status;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class RoomForm extends FormLayout {
    TextField roomType = new TextField("Room Type");
    NumberField numberOfRooms = new NumberField("Number of rooms");
    NumberField capacity = new NumberField("Capacity");
    NumberField pricePerNight = new NumberField("Price per Night");
    ComboBox<Status> availability = new ComboBox<>("Availability");
    TextArea roomDesciption = new TextArea("Room Description");
    Button saveButton =new Button("Save");
    Button deleteButton =new Button("Delete");
    Button canceButton =new Button("Cancel");

    public RoomForm(List<Status> statuses) {
        addClassName("room-form");

        availability.setItems(statuses);
        availability.setItemLabelGenerator(Status::getName);

        add(
                roomType,
                numberOfRooms,
                capacity,
                pricePerNight,
                availability,
                roomDesciption,
                createButtonLayout()
        );
    }

    private Component createButtonLayout() {
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        canceButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        saveButton.addClickShortcut(Key.ENTER);
        canceButton.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(saveButton,deleteButton,canceButton);
    }
}
