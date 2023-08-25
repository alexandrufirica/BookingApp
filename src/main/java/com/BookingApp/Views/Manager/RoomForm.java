package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Data.Entity.Status;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class RoomForm extends FormLayout {

//    Binder<Room> binder = new BeanValidationBinder<>(Room.class);
    TextField roomType = new TextField("Room Type");
    NumberField numberOfRooms = new NumberField("Number of rooms");
    NumberField capacity = new NumberField("Capacity");
    NumberField pricePerNight = new NumberField("Price per Night");
    ComboBox<Status> availability = new ComboBox<>("Availability");
    TextArea roomDesciption = new TextArea("Room Description");
    Button saveButton =new Button("Save");
    Button deleteButton =new Button("Delete");
    Button canceButton =new Button("Cancel");
    private Room room;

    public RoomForm(List<Status> statuses) {
        addClassName("room-form");
//        binder.bindInstanceFields(this);

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

    public void setRoom(Room room){
        this.room = room;
//        binder.readBean(room);
    }

    private Component createButtonLayout() {
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        canceButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

//        saveButton.addClickListener( e -> validatateAndSave());
//        deleteButton.addClickListener(e -> fireEvent(new DeleteEvent( this, room)));
//        canceButton.addClickListener(e -> fireEvent(new CloseEvent(this)));

        saveButton.addClickShortcut(Key.ENTER);
        canceButton.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(saveButton,deleteButton,canceButton);
    }

//    private void validatateAndSave() {
//        try{
//            binder.writeBean(room);
//            fireEvent(new SaveEvent(this,room));
//        }catch (ValidationException e){
//            e.printStackTrace();
//        }
//    }


//    public static abstract class RoomFormEvent extends ComponentEvent<RoomForm> {
//        private Room room;
//
//        protected RoomFormEvent(RoomForm source, Room contact) {
//            super(source, false);
//            this.room = contact;
//        }
//
//        public Room getContact() {
//            return room;
//        }
//    }

//    public static class SaveEvent extends RoomFormEvent {
//        SaveEvent(RoomForm source, Room contact) {
//            super(source, contact);
//        }
//    }
//
//    public static class DeleteEvent extends RoomFormEvent {
//        DeleteEvent(RoomForm source, Room contact) {
//            super(source, contact);
//        }
//
//    }
//
//    public static class CloseEvent extends RoomFormEvent {
//        CloseEvent(RoomForm source) {
//            super(source, null);
//        }
//    }
//
//    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
//        return addListener(DeleteEvent.class, listener);
//    }
//
//    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
//        return addListener(SaveEvent.class, listener);
//    }
//    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
//        return addListener(CloseEvent.class, listener);
//    }
}
