package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Security.CustomUserDetailsService;
import com.BookingApp.Service.AccommodationService;
import com.BookingApp.Views.Manager.ManagerNavBar;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@PageTitle(value = "Picture Upload")
@Route(value = "/pictureUpload")
@RolesAllowed({"MANAGER","ADMIN"})
public class PictureUpload extends VerticalLayout {

    ManagerNavBar navBar = new ManagerNavBar();
    private final Accommodation accommodation;
    private final AccommodationService accommodationService;
    private byte[] picture;

    public PictureUpload(AccommodationService accommodationService){
        this.accommodationService = accommodationService;

        accommodation = CustomUserDetailsService.accommodation;

        H1 label = new H1(accommodation.getName() + " Picture Upload");

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setMaxFileSize(2048576);

        upload.addStartedListener( e -> {
            String fileName = e.getFileName();
            InputStream inputStream = buffer.getInputStream();
            String mimeType = e.getMIMEType();

            try {
                picture = inputStream.readAllBytes();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            uploadPicture();
            saveAccommodation();
        });

        add(
                navBar,
                label,
                upload
        );

    }

    public void uploadPicture(){
        accommodation.setProfilePicture(picture);
    }

    public void saveAccommodation(){
        accommodationService.saveAccommodation(accommodation);
    }
}
