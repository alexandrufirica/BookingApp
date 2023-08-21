package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Entity.Status;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class RoomForm extends FormLayout {
    TextField roomType = new TextField("Room Type");
    NumberField numberOfRooms = new NumberField("Number of rooms");
    NumberField capacity = new NumberField("Capacity");
    NumberField pricePerNight = new NumberField("Price per Night");
    ComboBox<Status> availability = new ComboBox<>("Availability");
    TextArea roomDesciption = new TextArea("Room Description");

}
