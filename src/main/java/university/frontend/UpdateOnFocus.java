package university.frontend;

import university.backend.services.Service;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.List;

public class UpdateOnFocus<T> implements WindowFocusListener {
    private final Service<T> backendService;
    private final DefaultListModel<T> listModel;

    public UpdateOnFocus(Service<T> backendService, DefaultListModel<T> listModel) {
        this.backendService = backendService;
        this.listModel = listModel;
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        System.out.println("Focus on "+ backendService.getClass().getSimpleName());
        List<T> all = this.backendService.findAll();
        listModel.clear();
        listModel.addAll(all);
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        System.out.println("Focus lost");
    }
}