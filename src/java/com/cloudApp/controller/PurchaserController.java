package com.cloudApp.controller;

import com.cloudApp.model.Purchaser;
import java.io.Serializable;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class PurchaserController implements Serializable{
    
    @Inject
    private Purchaser purchaser;
 
    public Purchaser getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(Purchaser purchaser) {
        this.purchaser = purchaser;
    }
    
    public String collectInput() {
        /* Ako ovako definisemo return String onda ce se confirmation.xhtml stranica traziti u istom
        folderu u kome je i stranica sa koje je ovaj metod pozvan. Ako se confirmation.xhtml nalazi
        u folderu WebPages/confirm/confirmation.xhtml onda bi String definisali kao "/confirm/confirmation".
        1. forward slash (/) predstavlja root - WebPages.
        */
        return "confirmation";
    }

}
