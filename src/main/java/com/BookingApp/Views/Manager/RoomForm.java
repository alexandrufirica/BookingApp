package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Entity.Accommodation;
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
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class RoomForm extends FormLayout {

    Binder<Room> binder = new BeanValidationBinder<>(Room.class);
    TextField roomType = new TextField("Room Type");
    TextField numberOfRooms = new TextField("Number of rooms");
    TextField capacity = new TextField("Capacity");
    TextField pricePerNight = new TextField("Price per Night");
    ComboBox<Status> status = new ComboBox<>("Availability");
    TextArea roomDescription = new TextArea("Room Description");
    Button saveButton =new Button("Save");
    Button deleteButton =new Button("Delete");
    Button canceButton =new Button("Cancel");
    private Room room;

    public RoomForm(List<Accommodation> accommodations, List<Status> statuses) {
        addClassName("room-form");
        binder.bindInstanceFields(this);
        status.setItems(statuses);
        status.setItemLabelGenerator(Status::getName);

        add(
                roomType,
                numberOfRooms,
                capacity,
                pricePerNight,
                status,
                roomDescription,
                createButtonLayout()
        );
    }

    public void setRoom(Room room){
        this.room = room;
        binder.readBean(room);
    }


    private Component createButtonLayout() {
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        canceButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        saveButton.addClickListener(e -> validatateAndSave());
        deleteButton.addClickListener(e -> fireEvent(new DeleteEvent( this, room)));
        canceButton.addClickListener(e -> fireEvent(new CloseEvent(this)));

        saveButton.addClickShortcut(Key.ENTER);
        canceButton.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(saveButton,deleteButton,canceButton);
    }

    private void validatateAndSave() {
        try{
            binder.writeBean(room);
            fireEvent(new SaveEvent(this,room));
        }catch (ValidationException e){
            e.printStackTrace();
        }
    }


    public static abstract class RoomFormEvent extends ComponentEvent<RoomForm> {
        private Room room;

        protected RoomFormEvent(RoomForm source, Room room) {
            super(source, false);
            this.room = room;
        }

        public Room getRoom() {
            return room;
        }
    }

    public static class SaveEvent extends RoomFormEvent {
        SaveEvent(RoomForm source, Room room) {
            super(source, room);
        }
    }

    public static class DeleteEvent extends RoomFormEvent {
        DeleteEvent(RoomForm source, Room room) {
            super(source, room);
        }

    }

    public static class CloseEvent extends RoomFormEvent {
        CloseEvent(RoomForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                 ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType,listener);
    }

}
