package university.frontend;

import university.backend.services.CustomQuery;
import university.backend.services.Service;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.List;

public class UpdateOnFocus<T> implements WindowFocusListener {
    private final Service<T> backendService;
    private final DefaultListModel<T> listModel;
    private final CustomQuery<T> query;

    public UpdateOnFocus(Service<T> backendService, DefaultListModel<T> listModel) {
        query = Service::findAll;
        this.backendService = backendService;
        this.listModel = listModel;
    }
    public UpdateOnFocus(CustomQuery<T> query, DefaultListModel<T> listModel) {
        this.query = query;
        this.backendService = null;
        this.listModel = listModel;
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        List<T> all = query.query(backendService);
        listModel.clear();
        listModel.addAll(all);
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        System.out.println("Focus lost");
    }
}